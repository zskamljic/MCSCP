package connection.rcon;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import util.Util;

public class RCONIO
{
	public static final int TYPE_EXEC = 2;
	public static final int TYPE_AUTH = 3;
	public static final int TYPE_RESP = 0;
	public static final int TYPE_AUTH_RESP = 2;

	Socket sock;

	InputStream in;
	OutputStream out;

	public RCONIO(String addr, short port, String pass) throws Exception
	{
		try
		{
			sock = new Socket(addr, port);
			in = sock.getInputStream();
			out = sock.getOutputStream();
			sock.setSoTimeout(2000);
		} catch (Exception e)
		{
			Util.printToConsole("Could not connect to server!");
			e.printStackTrace();
			return;
		}
		if (!auth_packet(pass))
		{
			throw new Exception("Wrong password!");
		} else
			Util.printToConsole("Connected.");
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					in.close();
					out.close();
					sock.close();
				} catch (IOException e)
				{
				}
			}
		});
	}

	public byte[] constructPacket(int id, int cmdType, String cmd)
	{
		ByteBuffer buf = ByteBuffer.allocate(cmd.length() + 16);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		// Length
		buf.putInt(cmd.length() + 12);
		// Id
		buf.putInt(id);
		// cmdType
		buf.putInt(cmdType);
		// cmd
		buf.put(cmd.getBytes());
		// null term.
		buf.put((byte) 0x00);
		buf.put((byte) 0x00);
		// null str
		buf.put((byte) 0x00);
		buf.put((byte) 0x00);

		return buf.array();
	}

	public String sendCommandPacket(String cmd)
	{
		if(sock==null)
			return null;
		ByteBuffer[] packets;
		String resp="";
		try
		{
			packets = sendCommand(cmd);
			resp=assemblePackets(packets);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resp;
	}

	private boolean auth_packet(String pass)
	{
		if(sock==null)
			return false;
		byte[] req = constructPacket(108, TYPE_AUTH, pass);

		ByteBuffer response = ByteBuffer.allocate(64);

		try
		{
			out.write(req);
			response = recvPacket();
			// System.out.println(response.getInt(8));

			if (response.getInt(4) == 108
					&& response.getInt(8) == TYPE_AUTH_RESP)
				return true;
		} catch (Exception e)
		{
			Util.printToConsole("Error connecting!");
			e.printStackTrace();
		}
		return false;
	}

	private ByteBuffer recvPacket() throws IOException
	{
		if(sock==null)
			return null;
		ByteBuffer buf = ByteBuffer.allocate(4096);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		byte[] len = new byte[4];

		if (in.read(len, 0, 4) != 4)
			return null;
		buf.put(len);
		int i = 0;
		while (i < buf.getInt(0))
		{
			buf.put((byte) in.read());
			i++;
		}
		return buf;
	}

	private ByteBuffer[] sendCommand(String cmd) throws IOException
	{
		if(sock==null)
			return null;
		byte[] req = constructPacket(108, TYPE_EXEC, cmd);

		ByteBuffer[] resp = new ByteBuffer[128];
		int i = 0;
		out.write(req);
		resp[i] = recvPacket();
		try
		{
			sock.setSoTimeout(500);
			while (true)
				resp[++i] = recvPacket();
		} catch (SocketTimeoutException e)
		{
			return resp;
		}
	}

	private String assemblePackets(ByteBuffer[] packets)
	{
		if(sock==null)
			return null;
		String resp = "";

		for (int i = 0; i < packets.length; i++)
		{
			if (packets[i] != null)
				resp += new String(packets[i].array(), 12,
						packets[i].position() - 14);
		}
		return resp;
	}
	public boolean isOK()
	{
		return sock!=null;
	}
}

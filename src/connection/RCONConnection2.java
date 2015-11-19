package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import util.Util;

public class RCONConnection extends Connection
{
	final int buffsize = 10240;

	final int RCON_EXEC_COMMAND = 2;
	final int RCON_AUTHENTICATE = 3;
	final int RCON_RESPONSEVALUE = 0;
	final int RCON_AUTH_RESPONSE = 2;
	final int RCON_PID = 42;

	private int port = 25575;
	private String server;
	private String pass;

	DataInputStream reader;
	DataOutputStream writer;

	Socket sock;

	private class Packet
	{
		int size;
		int id;
		int cmd;
		byte data[] = new byte[10240];

		Packet()
		{
			size = id = cmd = 0;
		}

		Packet(int id, int cmd, String msg)
		{
			size = 8 + msg.getBytes().length + 2;
			this.id = id;
			this.cmd = cmd;
			data = msg.getBytes();
		}
	}

	public RCONConnection(String constr)
	{
		String[] info = constr.split(":");
		server = info[0];
		if (info[1] != "")
			port = Integer.parseInt(info[1]);
		if (info[2] != "")
			pass = info[2];
		try
		{
			sock = new Socket(server, port);
			reader = new DataInputStream(sock.getInputStream());
			writer = new DataOutputStream(sock.getOutputStream());
		} catch (Exception e)
		{
			Util.printToConsole(e.getMessage());
			return;
		}

		Packet p = new Packet(RCON_PID, RCON_AUTHENTICATE, pass);
		sendPacket(p);
		p = recvPacket();
		if (p != null)
			if (p.id == -1)
			{
				Util.printToConsole("Auth revoked!");
				return;
			}
	}

	boolean sendPacket(Packet p)
	{
		try
		{
			writer.writeInt(p.size);
			writer.writeInt(p.id);
			writer.writeInt(p.cmd);

			writer.write(p.data);
			writer.flush();
		} catch (Exception e)
		{
			Util.printToConsole(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	void clean_inc(int size)
	{
		try
		{
			if (size == 0)
				while (reader.read() != -1)
					;
			else
				for (int i = 0; i < size; i++)
					reader.read();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	Packet recvPacket()
	{
		Packet p = new Packet();
		try
		{
			p.size = reader.readInt();
			if (p.size < 10 || p.size > buffsize)
			{
				clean_inc(p.size);
				throw new Exception("Invalid packet size.");
			}
			p.id = reader.readInt();
			p.cmd = reader.readInt();
			p.data = new byte[p.size - 8];
			for (int i = 0; i < p.data.length; i++)
				p.data[i] = reader.readByte();
			clean_inc(0);
		} catch (Exception e)
		{
			Util.printToConsole(e.getMessage());
			return null;
		}
		return p;
	}

	@Override
	public void send(String command)
	{
		Packet p = new Packet(RCON_PID, RCON_EXEC_COMMAND, command);
		sendPacket(p);
		p = recvPacket();
		Util.printToConsole(p.toString());
	}
}

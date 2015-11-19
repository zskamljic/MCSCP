package util;

import connection.Connection;
import connection.DirectConnection;
import connection.RCONConnection;

public class Server
{
	// True: rcon, false: file
	boolean net = false;
	String domain = "localhost";

	Connection connection;

	public Server()
	{
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				if (!net)
					sendCmd("stop");
			}
		});
	}

	@Override
	public String toString()
	{
		String ret = domain;
		return ret;
	}

	public void setType(boolean b)
	{
		net = b;
	}

	public boolean getType()
	{
		return net;
	}

	public void setConnStr(String cstr)
	{
		if (net)
		{
			domain = cstr;
			connection = new RCONConnection(cstr);
		} else
		{
			domain = "localhost";
			connection = new DirectConnection(cstr);
		}
	}

	public void sendCmd(String cmd)
	{
		if (connection != null)
			connection.send(cmd);
	}

	public void sendCmd(String cmd,boolean show)
	{
		if (connection != null)
			connection.send(cmd);
		Util.printToConsole(cmd);
	}
	
	public void getPlayers()
	{
		if(connection!=null)
			connection.send("list");
	}
}

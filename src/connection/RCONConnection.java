package connection;

import javax.swing.DefaultListModel;

import util.Util;
import connection.rcon.RCONIO;

public class RCONConnection extends Connection
{
	RCONIO io;

	public RCONConnection(String connstr)
	{
		String con_arr[] = connstr.split(":");
		String pass = "";
		if (con_arr.length == 3)
			pass = con_arr[2];
		try
		{
			io = new RCONIO(con_arr[0], Short.parseShort(con_arr[1]), pass);
		} catch (Exception e)
		{
			Util.printToConsole(e.getMessage());
			return;
		}
		new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(60 * 1000);
					} catch (InterruptedException e)
					{
					}
					setPlayers();
				}
			}
		}.start();
	}

	void setPlayers()
	{
		String resp = io.sendCommandPacket("list");
		resp = resp.substring(resp.indexOf("ne:") + 3);
		// System.out.println(resp);
		String[] players = resp.split(",\\s");
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (String s : players)
			if (s != "")
				model.addElement(s);
		Util.players.setModel(model);
	}

	@Override
	public void send(String cmd)
	{
		if (io == null || !io.isOK())
			return;
		String resp = io.sendCommandPacket(cmd);
		if (Util.has(resp, Util.rules))
			Util.messages.add(resp);
		else if (resp.contains("players online:"))
		{
			resp = resp.substring(resp.indexOf(":") + 1);
			String players[] = resp.split(",\\s");
			DefaultListModel<String> model = new DefaultListModel<String>();
			for (String s : players)
				if (s != "")
					model.addElement(s);
			Util.players.setModel(model);
		} else
		{
			StringBuilder sb = new StringBuilder(resp);

			int i = 0;
			while (i + 80 < sb.length()
					&& (i = sb.lastIndexOf(" ", i + 80)) != -1)
			{
				sb.replace(i, i + 1, "\n");
			}
			Util.printToConsole(sb.toString());
		}
		if (cmd.contains("kick"))
			setPlayers();
	}

}

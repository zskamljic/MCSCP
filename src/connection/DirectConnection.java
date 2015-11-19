package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import util.Util;

public class DirectConnection extends Connection
{
	Process process;
	BufferedReader error;
	BufferedReader reader;
	BufferedWriter writer;

	DefaultListModel<String> model;

	public DirectConnection(String path)
	{
		if (!path.contains(".jar"))
			return;
		path = path.replace("\\", "/");

		List<String> params = new ArrayList<String>();
		params.add("java");
		params.add("-Xmx" + Util.mx + "M");
		params.add("-Xms" + Util.ms + "M");
		if (Util.jextra.length() != 0)
		{
			String[] jxp = Util.jextra.split("\\s+");
			for (String s : jxp)
				params.add(s);
		}
		params.add("-jar");
		params.add(path);
		params.add("nogui");
		if (Util.sextra.length() != 0)
		{
			String[] jxp = Util.sextra.split("\\s+");
			for (String s : jxp)
				params.add(s);
		}
		ProcessBuilder builder = new ProcessBuilder(
				(String[]) params.toArray(new String[0]));
		builder.redirectErrorStream(true);
		int last = path.lastIndexOf("/");
		if (last == -1)
			last = path.length();
		builder.directory(new File(path.substring(0, last)));
		try
		{
			process = builder.start();
			reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(
					process.getOutputStream()));
			error = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
		} catch (Exception e)
		{
			Util.printToConsole(e.getMessage());
		}
		new Thread()
		{
			@Override
			public void run()
			{
				logger();
			}
		}.start();
	}

	private void logger()
	{
		String line;
		while (true)
		{
			try
			{
				while ((line = reader.readLine()) != null)
				{
					if (line.contains("[Server thread/INFO]:")
							&& Util.has(line, Util.rules))
						Util.messages.add(line);
					else if (line.contains("[Server thread/INFO]:")
							&& line.contains("players online:"))
					{
						line = reader.readLine();
						line = line.substring(line.indexOf("]: ") + 3);
						String players[] = line.split(",\\s");
						model = new DefaultListModel<String>();
						for (String s : players)
							if (s != "")
								model.addElement(s);
						Util.players.setModel(model);
					} else if (line.contains("joined the game"))
					{
						Util.printToConsole(line);
						if (model == null)
							continue;
						line = line.substring(line.indexOf("]: " + 3));
						line = line.substring(line.indexOf(" joined the game"));
						model.addElement(line);
						Util.players.setModel(model);
					} else if (line.contains("left the game"))
					{
						Util.printToConsole(line);
						if (model == null)
							continue;
						line = line.substring(line.indexOf("]: " + 3));
						line = line.substring(line.indexOf(" left the game"));
						model.removeElement(line);
						Util.players.setModel(model);
					} else if (line.contains("Done ("))
					{
						Util.messages.add(line);
						Util.printToConsole(line);
					} else
						Util.printToConsole(line);
				}
				while ((line = error.readLine()) != null)
					Util.printToConsole(line);
			} catch (Exception e)
			{
				e.printStackTrace();
				Util.printToConsole(e.getMessage());
			}
		}
	}

	@Override
	public void send(String command)
	{
		if (writer == null)
			return;
		try
		{
			writer.write(command + '\n');
			writer.flush();
		} catch (IOException e)
		{
			Util.printToConsole(e.getMessage());
		}
	}

}

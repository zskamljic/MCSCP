package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings
{
	private static Properties props;
	private final static String propFile = "config.cfg";

	public static void loadPref()
	{
		props = new Properties();
		String propFile = "config.cfg";

		try
		{
			InputStream is = new FileInputStream(propFile);
			props.load(is);
		} catch (IOException e)
		{
		}

		Util.mx = new Integer(props.getProperty("max-memory", "1024"));
		Util.ms = new Integer(props.getProperty("min-memory", "1024"));
		Util.jextra = props.getProperty("java-param", "-XX:MaxPermSize=64M");
		Util.sextra = props.getProperty("server-param", "");
		Util.defConnStr = props.getProperty("server-ip", "server") + ":"
				+ props.getProperty("server-port", "port") + ":"
				+ props.getProperty("rcon-password", "password");
		Util.defFileStr = props.getProperty("server-jar", "");

		try
		{
			OutputStream out = new FileOutputStream(propFile);
			props.store(out, null);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void setPref(String key, String value)
	{
		props.setProperty(key, value);
		try
		{
			OutputStream out = new FileOutputStream(propFile);
			props.store(out, null);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void setDefaultPref()
	{
		String[] propstr =
		{ "max-memory", "min-memory", "java-param", "server-param",
				"server-ip","server-port","rcon-password", "server-jar" };
		String[] defval =
		{ "1024", "1024", "-XX:MaxPermSize=64M", "", "server","port","password", "" };

		boolean upd = false;

		for (int i = 0; i < propstr.length; i++)
		{
			if (props.getProperty(propstr[i]) == null)
			{
				if (!upd)
					upd = !upd;
				props.setProperty(propstr[i], defval[i]);
			}
		}
		if (upd)
			try
			{
				OutputStream out = new FileOutputStream(propFile);
				props.store(out, null);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
	}
}

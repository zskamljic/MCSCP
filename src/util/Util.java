package util;

import java.io.File;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Util
{
	private static JTextArea console;
	public static int mx = 4096;
	public static int ms = 4096;
	public static String jextra = "-XX:MaxPermSize=128M";
	public static String sextra = "";
	public static String defConnStr = "server:port:password";
	public static String defFileStr="";

	public static Queue<String> messages = new LinkedList<String>();
	public static JList<String> players;

	public static final String[] rules =
	{ "doDaylightCycle", "doFireTick", "doMobLoot", "doTileDrops",
			"keepInventory", "mobGriefing", "naturalRegeneration",
			"sendCommandFeedback", "showDeathMessages" };

	public static void printUIKeysFor(String... in)
	{
		Set<Entry<Object, Object>> entries = UIManager.getLookAndFeelDefaults()
				.entrySet();
		outer: for (Entry<Object, Object> entry : entries)
		{
			for (String el : in)
				if (!entry.toString().contains(el))
					continue outer;

			System.out.println(entry.toString());
		}
	}

	public static void setConsole(JTextArea console)
	{
		Util.console = console;
	}

	public static void printToConsole(String msg)
	{
		if (msg == "")
			return;
		if (console != null)
		{
			console.append(msg + '\n');
		} else
			System.out.println(msg);
	}

	public static void consoleClear()
	{
		console.setText("");
	}

	public static boolean setCurrentDir(String dir)
	{
		boolean result = false;
		File directory;
		directory = new File(dir).getAbsoluteFile();
		if (directory.exists() || directory.mkdirs())
			result = (System.setProperty("user.dir",
					directory.getAbsolutePath()) != null);
		return result;
	}

	public static boolean getMsgBool()
	{
		String front = messages.remove();
		front = front.substring(front.lastIndexOf("= ") + 1);
		return Boolean.parseBoolean(front);
	}

	public static boolean isDone()
	{
		if (messages.peek() == null)
			return false;
		if (messages.peek().contains("Done ("))
		{
			messages.remove();
			return true;
		}
		return false;
	}

	public static boolean has(String what, String[] toHave)
	{
		if(what==null)
			return false;
		for (String s : toHave)
			if (what.contains(s))
				return true;
		return false;
	}

	public static boolean getMsgValid()
	{
		if (messages.peek().contains("= true")
				|| messages.peek().contains("= false"))
			return true;
		return false;
	}

	public static void discardMsg()
	{
		messages.remove();
	}

	public static int findAt(Object what, Object[] where)
	{
		for (int i = 0; i < where.length; i++)
			if (what == where[i])
				return i;
		return -1;
	}

	public static int byteToInt(byte[] arr)
	{
		return arr[0] << 24 | (arr[1] & 0xff) << 16 | (arr[2] & 0xff) << 8
				| (arr[3] & 0xff);
	}

	public static byte[] intToByte(int in)
	{
		return new byte[]
		{ (byte) (in >> 24), (byte) (in >> 16), (byte) (in >> 8), (byte) in };
	}
}

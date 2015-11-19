package gui;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.UIManager;

import util.Settings;

@SuppressWarnings("serial")
public class FrameMain extends JFrame
{
	private final String version = "1.0.0";

	private FrameMain()
	{
		/*
		 * try {
		 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 * UIManager.put("Slider.focus", UIManager.get("Slider.background")); }
		 * catch (Exception e) { e.printStackTrace(); }//
		 */

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("tofiffe's Server Control Panel " + version);
		add(new Panel());

		pack();
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args)
	{
		Settings.loadPref();
		Settings.setDefaultPref();
		
		UIManager.put("ToggleButton.select", Color.pink);
		UIManager.put("ToggleButton.background", new Color(152, 251, 152));
		UIManager.put("ToggleButton.margin", new Insets(0, 0, 0, 0));
		UIManager.put("Button.margin", new Insets(0, 0, 0, 0));
		UIManager.put("TextArea.inactiveForeground", Color.black);
		UIManager.put("Button.background", new Color(238, 238, 238));
		UIManager.put("Button.rollover", false);
				
		new FrameMain();
	}

}

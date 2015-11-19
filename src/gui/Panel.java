package gui;

import gui.dlg.ServDialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;

import util.Server;
import util.Util;

@SuppressWarnings("serial")
public class Panel extends JPanel implements ActionListener, ChangeListener,
		KeyListener
{
	// Column 1
	private JTextArea console;
	private JTextField query;
	private JButton send;

	// Column 2
	private JList<String> players;
	private JButton kick;
	private JButton ban;
	private JButton banip;
	private JButton op;
	private JButton deop;
	private JButton kill;
	private JComboBox<String> pgamemode;

	// Column 3
	private JButton restart;
	private JButton save;
	private JButton stop;
	private JComboBox<String> sgamemode;
	private JButton sgset;
	private JComboBox<String> diffc;
	private JButton sdset;
	private JButton wclear;
	private JButton wrain;
	private JButton wthunder;
	private JSlider timer;
	private JButton stime;
	private JLabel tval;

	// Column 4
	private JToggleButton daylight;
	private JToggleButton firetick;
	private JToggleButton mobloot;
	private JToggleButton drops;
	private JToggleButton inven;
	private JToggleButton grief;
	private JToggleButton regen;
	private JToggleButton cmdfeed;
	private JToggleButton deathmsg;

	// Server stuff
	Server server;
	JButton servm;
	ServDialog sdialog;

	JToggleButton[] rules;

	final String[] gamemodes =
	{ "Survival", "Creative", "Adventure", "Spectator" };
	final String[] difficulties =
	{ "Peaceful", "Easy", "Normal", "Hard" };

	Panel()
	{
		setPreferredSize(new Dimension(800, 400));
		setLayout(null);

		server = new Server();
		sdialog = new ServDialog(server);

		// Column 1
		console = new JTextArea();
		console.setEditable(false);
		// console.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(console);
		scroll.setLocation(5, 5);
		scroll.setSize(400, 380);
		((DefaultCaret) console.getCaret())
				.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		add(scroll);
		query = new JTextField();
		query.setSize(330, 21);
		query.setLocation(5, 385);
		query.addKeyListener(this);
		add(query);
		send = new JButton("Send");
		send.setSize(69, 20);
		send.setLocation(335, 385);
		add(send);
		Util.setConsole(console);

		// Column 2
		players = new JList<String>();
		Util.players = players;
		scroll = new JScrollPane(players);
		scroll.setSize(150, 280);
		scroll.setLocation(410, 5);
		add(scroll);
		kick = new JButton("Kick");
		kick.setSize(150, 20);
		kick.setLocation(410, 285);
		add(kick);
		ban = new JButton("Ban");
		ban.setSize(150, 20);
		ban.setLocation(410, 305);
		add(ban);
		banip = new JButton("Ban IP");
		banip.setSize(150, 20);
		banip.setLocation(410, 325);
		add(banip);
		op = new JButton("OP");
		op.setSize(74, 20);
		op.setLocation(410, 345);
		add(op);
		deop = new JButton("DEOP");
		deop.setSize(74, 20);
		deop.setLocation(485, 345);
		add(deop);
		kill = new JButton("Kill");
		kill.setSize(150, 20);
		kill.setLocation(410, 365);
		add(kill);
		pgamemode = new JComboBox<String>(gamemodes);
		pgamemode.setSelectedIndex(0);
		pgamemode.setSize(150, 20);
		pgamemode.setLocation(410, 385);
		pgamemode.addActionListener(this);
		add(pgamemode);

		// Column 3
		restart = new JButton("Restart");
		restart.setSize(123, 20);
		restart.setLocation(565, 5);
		//add(restart);
		save = new JButton("Save");
		save.setSize(123, 20);
		save.setLocation(565, 5);
		add(save);
		stop = new JButton("Stop");
		stop.setSize(123, 20);
		stop.setLocation(565, 30);
		add(stop);
		JLabel tmp = new JLabel("Default Gamemode:");
		tmp.setSize(123, 20);
		tmp.setLocation(565, 90);
		add(tmp);
		sgamemode = new JComboBox<String>(new String[]
		{ gamemodes[0], gamemodes[1], gamemodes[2] });
		sgamemode.setSize(123, 20);
		sgamemode.setLocation(565, 110);
		add(sgamemode);
		sgset = new JButton("Set Gamemode");
		sgset.setSize(123, 20);
		sgset.setLocation(565, 130);
		add(sgset);
		tmp = new JLabel("Difficulty:");
		tmp.setSize(123, 20);
		tmp.setLocation(565, 170);
		add(tmp);
		diffc = new JComboBox<String>(difficulties);
		diffc.setSize(123, 20);
		diffc.setLocation(565, 190);
		add(diffc);
		sdset = new JButton("Set difficulty");
		sdset.setSize(123, 20);
		sdset.setLocation(565, 210);
		add(sdset);
		tmp = new JLabel("Weather:");
		tmp.setSize(123, 20);
		tmp.setLocation(565, 245);
		add(tmp);
		wclear = new JButton("Clear");
		wclear.setSize(41, 20);
		wclear.setLocation(565, 265);
		add(wclear);
		wrain = new JButton("Rain");
		wrain.setSize(41, 20);
		wrain.setLocation(606, 265);
		add(wrain);
		wthunder = new JButton("Storm");
		wthunder.setSize(41, 20);
		wthunder.setLocation(647, 265);
		add(wthunder);
		tmp = new JLabel("Set time:");
		tmp.setSize(123, 20);
		tmp.setLocation(565, 310);
		add(tmp);
		tval = new JLabel("0", JLabel.RIGHT);
		tval.setSize(61, 20);
		tval.setLocation(627, 310);
		add(tval);
		timer = new JSlider(0, 240);
		timer.setSize(123, 22);
		timer.setLocation(565, 332);
		timer.setValue(0);
		timer.addChangeListener(this);
		timer.setMajorTickSpacing(100);
		add(timer);
		stime = new JButton("Set time");
		stime.setSize(123, 20);
		stime.setLocation(565, 355);
		add(stime);

		// Column 4 x=693, w=113
		daylight = new JToggleButton("Daylight cycle");
		daylight.setSize(113, 20);
		daylight.setLocation(693, 5);
		add(daylight);
		firetick = new JToggleButton("Infinite fire");
		firetick.setSize(113, 20);
		firetick.setLocation(693, 30);
		add(firetick);
		mobloot = new JToggleButton("Mob loot");
		mobloot.setSize(113, 20);
		mobloot.setLocation(693, 55);
		add(mobloot);
		drops = new JToggleButton("Block drops");
		drops.setSize(113, 20);
		drops.setLocation(693, 80);
		add(drops);
		inven = new JToggleButton("Keep Inventory");
		inven.setSize(113, 20);
		inven.setLocation(693, 105);
		add(inven);
		grief = new JToggleButton("Mob griefing");
		grief.setSize(113, 20);
		grief.setLocation(693, 130);
		add(grief);
		regen = new JToggleButton("Health regen");
		regen.setSize(113, 20);
		regen.setLocation(693, 155);
		add(regen);
		cmdfeed = new JToggleButton("Command feed");
		cmdfeed.setSize(113, 20);
		cmdfeed.setLocation(693, 180);
		add(cmdfeed);
		deathmsg = new JToggleButton("Announce Deaths");
		deathmsg.setSize(113, 20);
		deathmsg.setLocation(693, 205);
		add(deathmsg);

		// Server stuff
		servm = new JButton("Manage");
		servm.setSize(113, 20);
		servm.setLocation(693, 385);
		add(servm);

		setButtonAL();
		rules = new JToggleButton[]
		{ daylight, firetick, mobloot, drops, inven, grief, regen, cmdfeed,
				deathmsg };
	}

	void setButtonAL()
	{
		for (Component c : getComponents())
		{
			if (c instanceof AbstractButton)
			{
				((AbstractButton) c).setFocusPainted(false);
				((AbstractButton) c).addActionListener(this);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == send)
		{
			server.sendCmd(query.getText());
			query.setText("");
		} else if (e.getSource() == kick && players.getSelectedValue() != null)
		{
			server.sendCmd("kick " + players.getSelectedValue());
		} else if (e.getSource() == ban && players.getSelectedValue() != null)
		{
			server.sendCmd("ban " + players.getSelectedValue());
		} else if (e.getSource() == banip && players.getSelectedValue() != null)
		{
			server.sendCmd("ban-ip " + players.getSelectedValue());
		} else if (e.getSource() == op && players.getSelectedValue() != null)
		{
			server.sendCmd("op " + players.getSelectedValue());
		} else if (e.getSource() == deop && players.getSelectedValue() != null)
		{
			server.sendCmd("deop " + players.getSelectedValue());
		} else if (e.getSource() == kill && players.getSelectedValue() != null)
		{
			server.sendCmd("kill " + players.getSelectedValue());
		} else if (e.getSource() == pgamemode
				&& players.getSelectedValue() != null)
		{
			server.sendCmd("gamemode " + players.getSelectedValue() + " "
					+ pgamemode.getSelectedIndex());
		} else if (e.getSource() == restart)
		{
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to restart the server?", "Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				server.sendCmd("restart");
		} else if (e.getSource() == save)
		{
			server.sendCmd("save-all");
		} else if (e.getSource() == stop)
		{
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to stop the server?", "Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				server.sendCmd("stop");
		} else if (e.getSource() == sgset)
		{
			server.sendCmd("defaultgamemode " + sgamemode.getSelectedIndex());
		} else if (e.getSource() == wclear)
		{
			server.sendCmd("weather clear");
		} else if (e.getSource() == wrain)
		{
			server.sendCmd("weather rain");
		} else if (e.getSource() == wthunder)
		{
			server.sendCmd("weather thunder");
		} else if (e.getSource() == stime)
		{
			server.sendCmd("time set " + timer.getValue() * 100);
		} else if (e.getSource() == sdset)
		{
			int dif = diffc.getSelectedIndex();
			server.sendCmd("difficulty " + (dif >= 4 ? 3 : dif));
		} else if (Util.findAt(e.getSource(), rules) != -1)
		{
			server.sendCmd("gamerule "
					+ Util.rules[Util.findAt(e.getSource(), rules)] + " "
					+ !((JToggleButton) e.getSource()).isSelected());
		} else if (e.getSource() == servm)
		{
			sdialog.setVisible(true);
			if (!server.getType())
			{
				new Thread()
				{
					@Override
					public void run()
					{
						while (true)
						{
							try
							{
								Thread.sleep(1000);
							} catch (InterruptedException e)
							{
							}
							if (Util.isDone())
								break;
						}
						setFromServer();
					}
				}.start();
			} else
			{
				setFromServer();
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		tval.setText(timer.getValue() * 100 + "");
	}

	public void setFromServer()
	{
		Util.messages.clear();
		for (String s : Util.rules)
			server.sendCmd("gamerule " + s);

		server.getPlayers();

		new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{
					if (Util.messages.size() != 9)
					{
						try
						{
							Thread.sleep(1000);
						} catch (InterruptedException e)
						{
						}
						continue;
					} else
						break;
				}
				for (JToggleButton b : rules)
				{
					if (Util.getMsgValid())
					{
						b.setSelected(Util.getMsgBool());
						b.setEnabled(true);
					} else
					{
						b.setEnabled(false);
						Util.discardMsg();
					}
				}
			}
		}.start();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			server.sendCmd(query.getText());
			query.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}
}

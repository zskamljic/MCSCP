package gui.dlg;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.Server;
import util.Settings;
import util.Util;

@SuppressWarnings("serial")
class SDPanel extends JPanel implements ActionListener, MouseListener
{
	// If you don't know what this is, go to bed.
	Server server;

	// Components
	JRadioButton net = new JRadioButton("Use rcon");
	JRadioButton file = new JRadioButton("Use direct access");
	JTextArea sinfo = new JTextArea("Click here...");
	JButton confirm = new JButton("Confirm");

	JDialog dialog;

	public SDPanel(Server serv, JDialog dialog)
	{
		this.dialog = dialog;
		server = serv;
		Dimension dim = new Dimension(190, 20);
		setLayout(null);
		setPreferredSize(new Dimension(200, 200));
		JLabel tmp = new JLabel("Server type:", JLabel.CENTER);
		tmp.setSize(dim);
		tmp.setLocation(5, 5);
		add(tmp);
		net.setLocation(5, 25);
		net.setSize(dim);
		add(net);
		file.setLocation(5, 45);
		file.setSize(dim);
		add(file);

		ButtonGroup grp = new ButtonGroup();
		grp.add(net);
		grp.add(file);

		JSeparator sep = new JSeparator();
		sep.setSize(190, 1);
		sep.setLocation(5, 65);
		add(sep);
		tmp = new JLabel("Server location:", JLabel.CENTER);
		tmp.setSize(dim);
		tmp.setLocation(5, 65);
		add(tmp);
		sinfo.setSize(dim);
		sinfo.setLocation(5, 85);
		sinfo.setBorder(BorderFactory.createLineBorder(Color.black));
		sinfo.addMouseListener(this);
		sinfo.setBackground(null);
		sinfo.setEnabled(false);
		add(sinfo);
		confirm.setSize(dim);
		confirm.setLocation(5, 175);
		add(confirm);

		addAL();

		if (server.getType())
			net.setSelected(true);
		else
			file.setSelected(true);
	}

	private void addAL()
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

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() instanceof JRadioButton)
		{
			sinfo.setEnabled(false);
			if (e.getSource() == net)
				sinfo.setText(Util.defConnStr);
			else
				sinfo.setText(Util.defFileStr);
		}
		if (e.getSource() == confirm)
		{
			server.setType(net.isSelected());
			if (sinfo.getText().contains("Click here"))
				sinfo.setText(net.isSelected() ? Util.defConnStr
						: Util.defFileStr);
			server.setConnStr(sinfo.getText());
			if (net.isSelected())
			{
				String[] spl = sinfo.getText().split(":");
				if (spl.length > 0)
					Settings.setPref("server-ip", spl[0]);
				if(spl.length>=2)
					Settings.setPref("server-port", spl[1]);
				if(spl.length>2)
					Settings.setPref("rcon-password", spl[2]);
			} else
				Settings.setPref("server-jar", sinfo.getText());
			dialog.setVisible(false);
			Util.consoleClear();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (net.isSelected())
		{
			sinfo.setEnabled(true);
			sinfo.setText(Util.defConnStr);
			sinfo.setToolTipText(null);
		} else
		{
			final JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"jar files", "jar");
			fc.setFileFilter(filter);
			if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				sinfo.setText(fc.getSelectedFile().getAbsolutePath());
				sinfo.setToolTipText(fc.getSelectedFile().getAbsolutePath());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}
}
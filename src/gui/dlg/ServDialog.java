package gui.dlg;

import javax.swing.JDialog;

import util.Server;

@SuppressWarnings("serial")
public class ServDialog extends JDialog
{
	Server server;

	public ServDialog(Server serv)
	{
		setTitle("Management");
		setModal(true);
		setResizable(false);
		
		server = serv;

		add(new SDPanel(serv,this));
		pack();

		setLocationRelativeTo(null);
	}
}

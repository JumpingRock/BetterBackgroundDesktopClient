package com.betterbackground.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.json.simple.JSONObject;

import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;

import com.betterbackground.userhandler.UserHandler;
import com.betterbackground.userhandler.Interfaces.LoginListener;

public class Login extends JFrame implements LoginListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel labelUsername = new JLabel("Enter username: ");
	private JLabel labelPassword = new JLabel("Enter password: ");
	private JTextField textUsername = new JTextField(20);
	private JPasswordField fieldPassword = new JPasswordField(20);
	public JButton buttonLogin = new JButton("Login");
	private static Login instance;

	private JLabel status = new JLabel(" Please Log in");
	JPanel newPanel;

	public static Login getInstance(){
		if(instance == null){
			instance = new Login();
		}
		return instance;
	}
	
	public Login() {
		super("Better Background");
		
		// create a new panel w. GridBagLayout
		newPanel = new JPanel(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		// add components 
		constraints.gridx = 0;
		constraints.gridy = 0;
		newPanel.add(labelUsername, constraints);

		constraints.gridx = 1;
		newPanel.add(textUsername, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		newPanel.add(labelPassword, constraints);

		constraints.gridx = 1;
		newPanel.add(fieldPassword, constraints);

		status.setForeground(Color.black);
		add("South", status);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;

		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fieldPassword.getPassword() == null || textUsername.getText().length() == 0) {
					status.setText("Username and Password fields cannot be empty");
					newPanel.revalidate();
					newPanel.repaint();
				}
				String password = new String(fieldPassword.getPassword());
				Initialize.userHandler.login(textUsername.getText(), password);
			}
		});
		newPanel.add(buttonLogin, constraints);

		// set border 
		newPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Login Panel"));

		// add the panel 
		getContentPane().add(newPanel);

		pack();
		setLocationRelativeTo(null);
	}
	
	public void updateStatus(String newStatus){
		status.setText(newStatus);
	}

	public void createLoginUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVisible(true);
			}
		});
	}

	// Login result is returned. Handle the case accordingly
	@Override
	public void loginResult(boolean result) {
		if(result) {
			//destroy login
			destroy();
			createMainUI();
		} else {
			status.setText(" Login failed, please try again.");
		}
	}
	
	public void destroy() {
		dispose();
		revalidate();
	}
	
	@SuppressWarnings("static-access")
	public void createMainUI() {
		MainUI mainUI = MainUI.getInstance();
		mainUI.createMainUI();
		mainUI.setDefaultCloseOperation(mainUI.DISPOSE_ON_CLOSE);
		mainUI.setDefaultCloseOperation(mainUI.EXIT_ON_CLOSE);
	}
}
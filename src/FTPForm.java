import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.webkit.ContextMenu.ShowContext;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FTPForm {

	private JFrame frmFtpclient;
	private JTextField textFieldUser;
	private JTextField textFieldPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FTPForm window = new FTPForm();
					window.frmFtpclient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FTPForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFtpclient = new JFrame();
		frmFtpclient.setTitle("FTPClient");
		frmFtpclient.setBounds(100, 100, 288, 173);
		frmFtpclient.setLocationRelativeTo(null);
		frmFtpclient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFtpclient.getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(51, 38, 70, 14);
		frmFtpclient.getContentPane().add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(51, 63, 70, 14);
		frmFtpclient.getContentPane().add(lblPassword);
		
		textFieldUser = new JTextField();
		textFieldUser.setBounds(131, 35, 86, 20);
		frmFtpclient.getContentPane().add(textFieldUser);
		textFieldUser.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FTPCon.FTPConnect(textFieldUser.getText(), textFieldPass.getText());
				
			}
		});
		btnLogin.setBounds(51, 91, 75, 23);
		frmFtpclient.getContentPane().add(btnLogin);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmFtpclient.dispose();
			}
		});
		btnExit.setBounds(141, 91, 76, 23);
		frmFtpclient.getContentPane().add(btnExit);
		
		textFieldPass = new JTextField();
		textFieldPass.setBounds(131, 60, 86, 20);
		frmFtpclient.getContentPane().add(textFieldPass);
		textFieldPass.setColumns(10);
	}
}

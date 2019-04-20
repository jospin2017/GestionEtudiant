package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;





import dao.ConnexionMysql;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentification extends JFrame {

	private JFrame frame;
	private JTextField usernameField;
	private JTextField passwordField;
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Authentification window = new Authentification();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Authentification() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		cnx = ConnexionMysql.ConnectToDb();
		
		usernameField = new JTextField();
		usernameField.setBounds(286, 91, 255, 31);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setBounds(286, 146, 255, 31);
		frame.getContentPane().add(passwordField);
		passwordField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setBounds(128, 98, 84, 16);
		frame.getContentPane().add(lblUsername);
		
		JButton btnNewButton = new JButton("Se connecter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*String username = usernameField.getText().toString();
				String password = passwordField.getText().toString();
				
				if(!username.equals("") || !password.equals("")){
					if(username.equals("admin") && password.equals("admin")){
						JOptionPane.showMessageDialog(null, "you are welcome");
						Accueil accueil = new Accueil();
						accueil.setVisible(true);
						
					}else{
						JOptionPane.showMessageDialog(null, "Paramètres incorrectes !");
					}
				}else{
					JOptionPane.showMessageDialog(null, "Entrez vos parametres !");
				}*/
				
				String sql = "select username,password from utilisateurs";
				try {
					
					String username = usernameField.getText().toString();
					String password = passwordField.getText().toString();
					
					prepared = cnx.prepareStatement(sql);
					resultat = prepared.executeQuery();
					
					int i = 0;
					if(username.equals("") || password.equals("")){
						JOptionPane.showMessageDialog(null, "Veuillez remplir les champs vide");
					}else{
						while(resultat.next()){
							
							String username1 = resultat.getString("username");
							String password1 = resultat.getString("password");
							
							if(username1.equals(username) && password1.equals(password1)){
								JOptionPane.showMessageDialog(null, "you are welcome");
								i = 1;
							}
						}
						
						if(i==0){
							JOptionPane.showMessageDialog(null, "Paramètres érronés !");
						}
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(286, 208, 256, 37);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(128, 153, 84, 16);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Speedy\\Desktop\\back.png"));
		lblNewLabel.setBounds(0, 0, 982, 453);
		frame.getContentPane().add(lblNewLabel);
	}
}

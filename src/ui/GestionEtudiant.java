package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSplitPane;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JButton;

import dao.ConnexionMysql;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;

import model.Etudiant;
import net.proteanit.sql.DbUtils;

import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

public class GestionEtudiant {

	private JFrame frame;
	private JTextField matriculeField;
	private JTextField nomField;
	private JTextField txtRechercherParMat;
	JComboBox filiereComboBox;
	JComboBox niveauComboBox;
	
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionEtudiant window = new GestionEtudiant();
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
	public GestionEtudiant() {
		initialize();
		findEtudiant();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1048, 596);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		cnx = ConnexionMysql.ConnectToDb();
		
		
		JLabel lblMatricule = new JLabel("Matricule :");
		lblMatricule.setBounds(12, 146, 78, 16);
		frame.getContentPane().add(lblMatricule);
		
		matriculeField = new JTextField();
		matriculeField.setBounds(102, 139, 234, 30);
		frame.getContentPane().add(matriculeField);
		matriculeField.setColumns(10);
		
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setBounds(12, 215, 56, 16);
		frame.getContentPane().add(lblNom);
		
		nomField = new JTextField();
		nomField.setBounds(102, 208, 234, 30);
		frame.getContentPane().add(nomField);
		nomField.setColumns(10);
		
		JLabel lblFilire = new JLabel("Fili\u00E8re : ");
		lblFilire.setBounds(12, 289, 56, 16);
		frame.getContentPane().add(lblFilire);
		
		filiereComboBox= new JComboBox();
		filiereComboBox.setBounds(102, 282, 234, 30);
		frame.getContentPane().add(filiereComboBox);
		remplirCombobox();
		
		JLabel lblNiveau = new JLabel("Niveau :");
		lblNiveau.setBounds(12, 376, 56, 16);
		frame.getContentPane().add(lblNiveau);
		
		niveauComboBox= new JComboBox();
		niveauComboBox.setBounds(102, 369, 234, 30);
		frame.getContentPane().add(niveauComboBox);
		remplirComboboxNiveau();
		
		JButton btnAjouter = new JButton("AJOUTER");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filiereComboBox.setSelectedIndex(1);
				niveauComboBox.setSelectedIndex(1);
				int filiere = filiereComboBox.getSelectedIndex();
				int niveau = niveauComboBox.getSelectedIndex();
				String matricule = matriculeField.getText().toString();
				String nom = nomField.getText().toString();
				JOptionPane.showMessageDialog(null, filiere);
				JOptionPane.showMessageDialog(null, niveau);
				
				String sql = "Insert into etudiants(matricule,nom,filieres_idfilieres,Niveau_idNiveau) values(?,?,?,?)";
				try {
					
					if(!matricule.equals("") && !nom.equals("")){
						prepared = cnx.prepareStatement(sql);
						prepared.setString(1, matricule);
						prepared.setString(2, nom);
						prepared.setInt(3, filiere);
						prepared.setInt(4, niveau);
						
						prepared.execute();
						
						matriculeField.setText("");
						nomField.setText("");
						JOptionPane.showMessageDialog(null, "été enregistrer !");
						Update();
					}else{
						JOptionPane.showMessageDialog(null, "n'a pas été enregistrer !");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAjouter.setBounds(12, 498, 97, 38);
		frame.getContentPane().add(btnAjouter);
		
		JButton btnModifier = new JButton("MODIFIER");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int ligne = table.getSelectedRow();
				String id = table.getModel().getValueAt(ligne,0).toString();
				
				String sql = "update etudiants set matricule = ?, nom = ? where id='"+id+"'";
				try {
					prepared = cnx.prepareStatement(sql);
					prepared.setString(1, matriculeField.getText().toString());
					prepared.setString(2, nomField.getText().toString());
					
					prepared.execute();
					JOptionPane.showMessageDialog(null, "Etudiant update!");
					Update();
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnModifier.setBounds(239, 498, 97, 38);
		frame.getContentPane().add(btnModifier);
		
		JButton btnSupprimer = new JButton("SUPPRIMER");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ligne = table.getSelectedRow();
				String id = table.getModel().getValueAt(ligne,0).toString();
				
				String sql = "delete from etudiants where id='"+id+"'";
				try {
					prepared = cnx.prepareStatement(sql);
					prepared.execute();
					JOptionPane.showMessageDialog(null, "Etudiant supprimé !");
					Update();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSupprimer.setBounds(114, 498, 113, 38);
		frame.getContentPane().add(btnSupprimer);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(361, 104, 152, 29);
		frame.getContentPane().add(comboBox_2);
		
		txtRechercherParMat = new JTextField();
		txtRechercherParMat.setText("Rechercher par matricule");
		txtRechercherParMat.setToolTipText("");
		txtRechercherParMat.setBounds(525, 104, 167, 29);
		frame.getContentPane().add(txtRechercherParMat);
		txtRechercherParMat.setColumns(10);
		
		JButton btnNewButton = new JButton("Actualiser");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Update();
			}
		});
		btnNewButton.setBounds(921, 104, 97, 29);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(361, 139, 657, 397);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int ligne = table.getSelectedRow();
				String id = table.getModel().getValueAt(ligne,0).toString();
				
				String sql = "select * from etudiants where id='"+id+"'";
				try {
					prepared = cnx.prepareStatement(sql);
					resultat = prepared.executeQuery();
					
					while(resultat.next()){
						matriculeField.setText(resultat.getString("matricule"));
						nomField.setText(resultat.getString("nom"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		scrollPane.setColumnHeaderView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane_1.setBounds(12, 65, 1006, -50);
		frame.getContentPane().add(scrollPane_1);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.LIGHT_GRAY);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBounds(12, 13, 1006, 55);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("GESTION DES ETUDIANTS \r\n Fait par BORIS NANA");
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 30));
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				findEtudiant();
			}
		});
		btnSearch.setBounds(704, 105, 105, 27);
		frame.getContentPane().add(btnSearch);
	}
	public void findEtudiant(){
		ArrayList<Etudiant> etudiants = ListEtudiant(txtRechercherParMat.getText()); 
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[]{"ID","MAT","NOM","FILIERE","NIVEAU"});
		Object[] row = new Object[5];
		
		for(int i=0;i<etudiants.size();i++){
			row[0] = etudiants.get(i).getId();
			row[1] = etudiants.get(i).getMatricule();
			row[2] = etudiants.get(i).getNom();
			row[3] = etudiants.get(i).getFiliere();
			row[4] = etudiants.get(i).getNiveau();
			model.addRow(row);
		}
		table.setModel(model);
	}
	public ArrayList<Etudiant> ListEtudiant(String valToSearch){
		ArrayList<Etudiant> etudiantList = new ArrayList<Etudiant>();
		Statement st;
		ResultSet rs;
		String searchQuery = "select * from `etudiants` where concat(`id`,`matricule`,`nom`,`filieres_idfilieres`,`Niveau_idNiveau`) LIKE '%"+valToSearch+"%'";
		
		try{
			prepared = cnx.prepareStatement(searchQuery);
			resultat = prepared.executeQuery();
			
			Etudiant etudiant;
			
			while (resultat.next()) {
				etudiant = new Etudiant(
						resultat.getInt("id"),
						resultat.getString("matricule"),
						resultat.getString("nom"),
						resultat.getInt("filieres_idfilieres"),
						resultat.getInt("Niveau_idNiveau")
						);
				
				etudiantList.add(etudiant);
				
			}

			table.setModel(DbUtils.resultSetToTableModel(resultat));
		}catch(Exception ex){
			
		}
		
		return etudiantList;
	}
	public void Update(){
		String sql = "select * from etudiants";
		
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultat));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void remplirCombobox(){
		String sql = "select * from filieres";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			
			while(resultat.next()){
				String nom = resultat.getString("designationFiliere");
				filiereComboBox.addItem(nom);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void remplirComboboxNiveau(){
		String sql = "select * from niveau";
		try {
			prepared = cnx.prepareStatement(sql);
			resultat = prepared.executeQuery();
			
			while(resultat.next()){
				String nom = resultat.getString("libelleNiveau");
				niveauComboBox.addItem(nom);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

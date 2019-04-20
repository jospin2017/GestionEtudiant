package model;

public class Etudiant {
	private int id;
	private String matricule;
	private String nom;
	private int filiere;
	private int niveau;
	
	public Etudiant(int id, String matricule, String nom, int filiere,int niveau) {
		super();
		this.id = id;
		this.matricule = matricule;
		this.nom = nom;
		this.filiere = filiere;
		this.niveau = niveau;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getFiliere() {
		return filiere;
	}

	public void setFiliere(int filiere) {
		this.filiere = filiere;
	}

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}
	
	
	
}

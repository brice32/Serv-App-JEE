package contacts.jsf.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class Personne implements Serializable {


	// Champs
	
	private int				id;
	private String			nom;
	private String			prenom;
	private Categorie		categorie;
	private List<Telephone>	telephones = new ArrayList<>();

	
	// Constructeurs
	
	public Personne() {
	}

	public Personne(int id, String nom, String prenom, Categorie categorie) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.categorie = categorie;
	}
	
	
	// Getters & setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<Telephone> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}
	

}

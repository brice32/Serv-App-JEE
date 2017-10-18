package contacts.jsf.data;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Telephone implements Serializable {

	
	// Champs

	private int				id;
	private String			libelle;
	private String			numero;

	
	// Constructeurs
	
	public Telephone() {
	}

	public Telephone(int id, String libelle, String numero) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.numero = numero;
	}
	

	// Getters & setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}

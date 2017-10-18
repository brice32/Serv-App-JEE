/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contacts.commun.dto;

import java.io.Serializable;


@SuppressWarnings("serial")
public class DtoCategorie implements Serializable {
    
    // Champs
    
    private int         id;
    private String      libelle;
	
	
	// Constructeurs
    
    public DtoCategorie() {
		super();
	}
    
    public DtoCategorie(int id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
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
    
    
    // toString()
//    @Override
//    public String toString() {
//    	return libelle;
//    }

    
    // hashCode() & equals()

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DtoCategorie other = (DtoCategorie) obj;
		if (id != other.id)
			return false;
		return true;
	}
}

package contacts.emb.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Categorie")
public class Categorie {

	
    // Champs
    @Id
    @GeneratedValue( strategy=GenerationType.IDENTITY )
    @Column(name="IdCategorie")
    private int         	id;
    
    @Column(name="Libelle")
    private String      	libelle;
	
	
	// Constructeurs

	public Categorie() {
	}
    
    public Categorie(int id, String libelle) {
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
    
    
    // soString()
    @Override
    public String toString() {
    	return libelle;
    }

    
	// equals() et hashcode()

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Categorie other = (Categorie) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}

package contacts.emb.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="compte")
public class Compte  {

	
	// Champs
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="IdCompte")
	private int			id;
	
	@Column(name="Pseudo")
	private String		pseudo;
	
	@Column(name="MotDePasse")
	private String		motDePasse;
	
	@Column(name="Email")
	private String		email;
	
	@ElementCollection
	@CollectionTable( name="role", joinColumns=@JoinColumn( name="IdCompte" ))
	@Column( name="Role" )
	private List<String> roles = new ArrayList<>();	
	
	
	// Constructeurs
	
	public Compte() {
	}

	public Compte(int id, String pseudo, String motDePasse, String email) {
		this.id = id;
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
		this.email = email;
	}
	
		
	// Getters & setters
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

    
	// equals() et hashcode()

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
		Compte other = (Compte) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}

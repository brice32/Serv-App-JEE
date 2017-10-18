package contacts.jsf.model.mock;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.jsf.data.Personne;


//@RequestScoped
@SuppressWarnings("serial")
@ViewScoped
@Named
public class ModelPersonneListe implements Serializable {

	
	// Champs
	
	private List<Personne>		personnes;
	
	@Inject
	private Donnees				données;

	
	// Getters & Setters
	
	public List<Personne> getPersonnes() {
		return personnes;
	}
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		personnes = données.getPersonnes();
	}
	
	public String initialiserListe() {
		return null;
	}
	
	
	// Actions
	
	public String supprimer( Personne personne ) {
		données.personneSupprimer( personne.getId() );
		personnes.remove(personne);
		return null;
	}
	
	
}

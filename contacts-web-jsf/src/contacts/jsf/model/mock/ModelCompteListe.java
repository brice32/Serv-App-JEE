package contacts.jsf.model.mock;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.jsf.data.Compte;


@RequestScoped
@Named
public class ModelCompteListe {

	
	// Champs
	
	private List<Compte>	comptes;
	
	@Inject
	private Donnees			données;

	
	// Getters & Setters
	
	public List<Compte> getComptes() {
		return comptes;
	}
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		comptes = données.getComptes();
	}
	
	
	// Actions
	
	public String supprimer( Compte compte ) {
		données.compteSupprimer( compte.getId() );
		comptes.remove(compte);
		return null;
	}
	
	
}

package contacts.jsf.model.mock;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.jsf.data.Compte;
import contacts.jsf.util.UtilJsf;


@RequestScoped
@Named
public class ModelCompteForm {

	
	// Champs
	
	private Compte			compte = new Compte();
	
	@Inject
	private Donnees			données;

	
	// Getters & Setters

	public Compte getCompte() {
		return compte;
	}
	
	
	// Initialisaitons
	
	public String retrouverCompte() {
		if ( compte.getId() != 0 ) {
			compte = données.compteRetrouver( compte.getId() );
			if ( compte == null ) {
				UtilJsf.genererMessageErreur( "Le compte demandé n'existe pas" );
				return "test/liste";
			}
		}
		return null;
	}
	
	
	// Actions
	
	public String validerMiseAJour() {
		if ( compte.getId() == 0) {
			données.compteAjouter(compte);
		} else {
			données.compteModifier(compte);
		}
		return "liste";
	}
	
	
	
}

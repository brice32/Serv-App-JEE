package contacts.jsf.model.mock;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.jsf.data.Personne;
import contacts.jsf.data.Telephone;
import contacts.jsf.util.UtilJsf;


@SuppressWarnings("serial")
@ViewScoped
@Named
public class ModelPersonneForm implements Serializable {

	
	// Champs
	
	private Personne			personne = new Personne();
	
	@Inject
	private Donnees				données;

	
	// Getters & Setters

	public Personne getPersonne() {
		return personne;
	}
	
	
	// Initialisaitons
	
	public String retrouverPersonne() {
		if ( personne.getId() != 0 ) {
			personne = données.personneRetrouver( personne.getId() ); 
			if ( personne == null ) {
				UtilJsf.genererMessageErreur( "La personne demandée n'existe pas" );
				return "test/liste";
			}
		}
		return null;
	}
	
	
	// Actions
	
	public String validerMiseAJour() {
		if ( personne.getId() == 0) {
			données.personneAjouter(personne);
		} else {
			données.personneModifier(personne);
		}
		return "liste";
	}
	
	
	public String ajouterTelephone() {
		personne.getTelephones().add( new Telephone() );
		return null;
	}
	
	
	public String supprimerTelephone( Telephone telephone ) {
		personne.getTelephones().remove( telephone );
		return null;
	}
	
}

package contacts.jsf.model.standard;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceConnexion;
import contacts.jsf.cdi.CompteConnecté;
import contacts.jsf.data.Compte;
import contacts.jsf.util.UtilJsf;


@RequestScoped
@Named
public class ModelConnexion {

	// Champs

	private Compte			compte		= new Compte();

	@Inject
	private ModelInfo		modelInfo;

	@Inject
	private CompteConnecté	compteConnecté;

	@Inject
	private IServiceConnexion serviceConnexion;


	// Getters & Setters
	
	public Compte getCompte() {
		return compte;
	}

	
	// Actons
	
	public String connect() {
	    
	    DtoCompte dto = serviceConnexion.sessionUtilisateurOuvrir( compte.getPseudo(), compte.getMotDePasse() );
	    
	    if ( dto != null ){

	        compteConnecté.setPseudo( dto.getPseudo() );
	        compteConnecté.setRoles( dto.getRoles() );
	        
	    	modelInfo.setTitre( "Bienvenue" );
	    	modelInfo.setTexte( "Vous êtes connecté en tant que '" + compte.getPseudo() +"'.");
		    return "info";

	    } else {
	        UtilJsf.genererMessageErreur( "Pseudo ou mot de passe invalide." );
	    	return null;
	    }
	}	
	
	public String disconnect() {

		serviceConnexion.sessionUtilisateurFermer();

        compteConnecté.setPseudo( null );
        compteConnecté.getRoles().clear();

	    return "connexion";
	}
}

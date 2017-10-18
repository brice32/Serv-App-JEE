package contacts.jsf.model.mock;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.jsf.cdi.CompteConnecté;
import contacts.jsf.data.Compte;
import contacts.jsf.util.UtilJsf;


@RequestScoped
@Named
public class ModelConnexion {

	// Champs

	private Compte		compte		= new Compte();

	@Inject
	private ModelInfo	modelInfo;

	@Inject
	private CompteConnecté	compteConnecté;

	@Inject
	private Donnees		données;


	// Getters & Setters
	
	public Compte getCompte() {
		return compte;
	}

	
	// Actons
	
	public String connect() {

		Compte compteRetrouvé = null;
		for ( Compte courant : données.getComptes() ) {
			if ( courant.getPseudo().equals( compte.getPseudo() )
					&& courant.getMotDePasse().equals( compte.getMotDePasse()) ) {
				compteRetrouvé = courant;
				break;
			}
		}
	    
	    if ( compteRetrouvé != null ){

	        compteConnecté.setPseudo( compteRetrouvé.getPseudo() );
	        compteConnecté.setRoles( compteRetrouvé.getRoles() );
	        
	    	modelInfo.setTitre( "Bienvenue" );
	    	modelInfo.setTexte( "Vous êtes connecté en tant que '" + compte.getPseudo() +"'.");
		    return "info";

	    } else {
	        UtilJsf.genererMessageErreur( "Pseudo ou mot de passe invalide." );
	    	return null;
	    }
	}	
	
	public String disconnect() {

        compteConnecté.setPseudo( null );
        compteConnecté.getRoles().clear();

	    return "connexion";
	}
}

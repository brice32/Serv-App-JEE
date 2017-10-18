package contacts.emb.service.mock;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceConnexion;
import contacts.commun.util.ExceptionAnomalie;
import contacts.emb.util.securite.IManagerSecurite;

public class ServiceConnexion implements IServiceConnexion {

	
	// Logger
	private static final Logger	logger = Logger.getLogger(ServiceConnexion.class.getName());
	
	
	// Champs 

	private IManagerSecurite	managerSecurite;

	private  Map<Integer, DtoCompte>  mapComptes;
	
	
	// Injecteurs
	
	public void setManagerSecurite( IManagerSecurite managerSecurite ) {
		this.managerSecurite = managerSecurite;
	}
	
	public void setDonnees( Donnees donnees ) {
		mapComptes = donnees.getMapComptes();
	}
	
	
	// Actions

	@Override
	public DtoCompte sessionUtilisateurOuvrir( String pseudo, String motDePasse ) {
		try {
			DtoCompte	compteConnecté = null;
	        for (DtoCompte compte : mapComptes.values()) {
	            if (compte.getPseudo().equals(pseudo)) {
	                if (compte.getMotDePasse().equals(motDePasse)) {
	                	compteConnecté = compte;
	                }
	                break;
	            }
	        }
			managerSecurite.setCompteConnecté( compteConnecté );
			return compteConnecté;
		} catch ( RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}


	@Override
	public void sessionUtilisateurFermer() {
		try {
			managerSecurite.setCompteConnecté( null );
		} catch ( RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}
	

}

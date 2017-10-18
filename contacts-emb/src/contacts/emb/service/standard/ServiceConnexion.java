package contacts.emb.service.standard;

import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceConnexion;
import contacts.commun.util.ExceptionAnomalie;
import contacts.emb.dao.IDaoCompte;
import contacts.emb.data.mapper.IMapper;
import contacts.emb.util.securite.IManagerSecurite;

public class ServiceConnexion implements IServiceConnexion {

	
	// Logger
	private static final Logger	logger = Logger.getLogger(ServiceConnexion.class.getName());
	
	
	// Champs 

	private IMapper				mapper;
	private IDaoCompte			daoCompte;
	private IManagerSecurite	 managerSecurite;
	
	
	// Injecteurs
	
	public void setMapper( IMapper mapper ) {
		this.mapper = mapper;
	}

	public void setDaoCompte( IDaoCompte daoCompte ) {
		this.daoCompte = daoCompte;
	}
	
	public void setManagerSecurite( IManagerSecurite managerSecurite ) {
		this.managerSecurite = managerSecurite;
	}
	
	
	// Actions

	@Override
	public DtoCompte sessionUtilisateurOuvrir( String pseudo, String motDePasse ) {
		try {
			DtoCompte compte = mapper.map( daoCompte.validerAuthentification(pseudo, motDePasse) );
			managerSecurite.setCompteConnecté( compte );
			return compte;
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

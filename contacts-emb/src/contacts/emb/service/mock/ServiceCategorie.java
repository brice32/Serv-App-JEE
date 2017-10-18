package contacts.emb.service.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoCategorie;
import contacts.commun.dto.DtoPersonne;
import contacts.commun.service.IServiceCategorie;
import contacts.commun.service.IServicePersonne;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionValidation;
import contacts.emb.util.securite.IManagerSecurite;


public class ServiceCategorie implements IServiceCategorie {


	// Logger
	private static final Logger logger = Logger.getLogger( ServiceCategorie.class.getName() );

	
	// Champs 
	
	private Map<Integer, DtoCategorie>	mapCategories;
	
	private IManagerSecurite			managerSecurite;
	private IServicePersonne			servicePersonne;
	
	
	// Injecteurs
	
	public void setDonnees( Donnees donnees ) {
		mapCategories = donnees.getMapCategories();
	}
	
	public void setManagerSecurite( IManagerSecurite managerSecurite ) {
		this.managerSecurite = managerSecurite;
	}

	public void setServicePersonne( IServicePersonne servicePersonne ) {
		this.servicePersonne = servicePersonne;
	}
	

	// Actions 

	@Override
	public int inserer( DtoCategorie dtoCategorie ) throws ExceptionValidation {
		try {
			managerSecurite.verifierAutorisationAdmin();
			verifierValiditeDonnees(dtoCategorie);
			if ( mapCategories.isEmpty() ) {
				dtoCategorie.setId( 1 );
			} else {
				dtoCategorie.setId( Collections.max( mapCategories.keySet() ) + 1 );
			}
			mapCategories.put( dtoCategorie.getId(), dtoCategorie );
			return dtoCategorie.getId();
		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public void modifier( DtoCategorie dtoCategorie ) throws ExceptionValidation {
		try {
			managerSecurite.verifierAutorisationAdmin();
			verifierValiditeDonnees(dtoCategorie);
			mapCategories.replace( dtoCategorie.getId(), dtoCategorie );
		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public void supprimer( int idCategorie ) throws ExceptionValidation {
		try {
			managerSecurite.verifierAutorisationAdmin();
			for( DtoPersonne personne : servicePersonne.listerTout() ) {
				if ( personne.getCategorie().getId() == idCategorie ) {
	                throw new ExceptionValidation( "La catégorie n'est pas vide" );
				}
			}
			mapCategories.remove( idCategorie );
		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public DtoCategorie retrouver( int idCategorie ) {
		try {
			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
			return mapCategories.get( idCategorie );
		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public List<DtoCategorie> listerTout() {
		try {
			managerSecurite.verifierAutorisationUtilisateurOuAdmin();;
			return trierParLibelle( new ArrayList<>(mapCategories.values()) );
		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}
	
	
	// Méthodes auxiliaires
	
	private void verifierValiditeDonnees( DtoCategorie dtoCategorie ) throws ExceptionValidation {
		
		StringBuilder message = new StringBuilder();
		
		if ( dtoCategorie.getLibelle() == null || dtoCategorie.getLibelle().isEmpty() ) {
			message.append( "\nLe libellé est absent." );
		} else 	if ( dtoCategorie.getLibelle().length() < 3 ) {
			message.append( "\nLe libellé est trop court." );
		} else 	if ( dtoCategorie.getLibelle().length() > 25 ) {
			message.append( "\nLe libellé est trop long." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
	}
    
	
    private List<DtoCategorie> trierParLibelle( List<DtoCategorie> liste ) {
		Collections.sort( liste,
	            (Comparator<DtoCategorie>) ( item1, item2) -> {
	                return ( item1.getLibelle().toUpperCase().compareTo( item2.getLibelle().toUpperCase() ) );
			});
    	return liste;
    }

}

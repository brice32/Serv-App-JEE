package contacts.emb.service.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoPersonne;
import contacts.commun.dto.DtoTelephone;
import contacts.commun.service.IServicePersonne;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionValidation;
import contacts.emb.dao.IDaoPersonne;
import contacts.emb.dao.IManagerTransaction;
import contacts.emb.data.Personne;
import contacts.emb.data.mapper.IMapper;
import contacts.emb.util.securite.IManagerSecurite;

public class ServicePersonne implements IServicePersonne {


	// Logger
	private static final Logger logger = Logger.getLogger( ServicePersonne.class.getName() );

	
	// Champs 
	
	private IManagerSecurite		managerSecurite;
    private	IManagerTransaction		managerTransaction;
	private IMapper			mapper;
	private IDaoPersonne			daoPersonne;
	
	
	// Injecteurs
	
	public void setManagerSecurite( IManagerSecurite managerSecurite ) {
		this.managerSecurite = managerSecurite;
	}
	
	public void setManagerTransaction( IManagerTransaction managerTransaction ) {
		this.managerTransaction = managerTransaction;
	}
	
	public void setMapper( IMapper mapper ) {
		this.mapper = mapper;
	}

	public void setDaoPersonne( IDaoPersonne daoPersonne ) {
		this.daoPersonne = daoPersonne;
	}
	

	// Actions 

	@Override
	public int inserer( DtoPersonne dtoPersonne ) throws ExceptionValidation {
		try {
			
			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
			verifierValiditeDonnees( dtoPersonne );
	
			managerTransaction.begin();
			try {
				int id = daoPersonne.inserer( mapper.map( dtoPersonne ) );
				managerTransaction.commit();
				return id;
			} catch (Exception e) {
				managerTransaction.rollback();
				throw e;
			}
	
		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public void modifier( DtoPersonne dtoPersonne ) throws ExceptionValidation {
		try  {
		
			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
			verifierValiditeDonnees( dtoPersonne );
	
			managerTransaction.begin();
			try {
				daoPersonne.modifier( mapper.map( dtoPersonne ) );
				managerTransaction.commit();
			} catch (Exception e) {
				managerTransaction.rollback();
				throw e;
			}
	
		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public void supprimer( int idPersonne ) throws ExceptionValidation {
		try {
			
			managerSecurite.verifierAutorisationUtilisateurOuAdmin();

			managerTransaction.begin();
			try {
				daoPersonne.supprimer(idPersonne);
				managerTransaction.commit();
			} catch (Exception e) {
				managerTransaction.rollback();
				throw e;
			}

		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public DtoPersonne retrouver( int idPersonne ) {
		try {
			
			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
			return mapper.map( daoPersonne.retrouver(idPersonne) );

		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public List<DtoPersonne> listerTout() {
		try {

			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
			List<DtoPersonne> liste = new ArrayList<>();
			for( Personne personne : daoPersonne.listerTout() ) {
				liste.add( mapper.map( personne ) );
			}
			return liste;

		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	
	
	// Méthodes auxiliaires
	
	private void verifierValiditeDonnees( DtoPersonne dtoPersonne ) throws ExceptionValidation {
		
		StringBuilder message = new StringBuilder();
		
		if ( dtoPersonne.getNom() == null || dtoPersonne.getNom().isEmpty() ) {
			message.append( "\nLe nom est absent." );
		} else 	if ( dtoPersonne.getNom().length() > 25 ) {
			message.append( "\nLe nom est trop long." );
		}

		if ( dtoPersonne.getPrenom() == null || dtoPersonne.getPrenom().isEmpty() ) {
			message.append( "\nLe prénom est absent." );
		} else 	if ( dtoPersonne.getPrenom().length() > 25 ) {
			message.append( "\nLe prénom est trop long." );
		}
		
		for( DtoTelephone telephoe : dtoPersonne.getTelephones() ) {
			if ( telephoe.getLibelle() == null || telephoe.getLibelle().isEmpty() ) {
				message.append( "\nLlibellé absent pour le téléphone : " + telephoe.getNumero() );
			} else 	if ( telephoe.getLibelle().length() > 25 ) {
				message.append( "\nLe libellé du téléphone est trop long : " + telephoe.getLibelle() );
			}
  			
			if ( telephoe.getNumero() == null || telephoe.getNumero().isEmpty() ) {
				message.append( "\nNuméro absent pour le téléphone : " + telephoe.getLibelle() );
			} else 	if ( telephoe.getNumero().length() > 25 ) {
				message.append( "\nLe numéro du téléphone est trop long : " + telephoe.getNumero() );
			}
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
	} 

}

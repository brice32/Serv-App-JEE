package contacts.javafx.model.mock;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.util.ExceptionValidation;
import contacts.javafx.data.Compte;
import contacts.javafx.model.IModelConnexion;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


public class ModelConnexion implements IModelConnexion {
	
	
	// Logger
	private static final Logger logger = Logger.getLogger( ModelConnexion.class.getName() );
	
	
	// Données observables 
	private final Compte         compteVue = new Compte();

	// Compte connecté
	private final ObjectProperty<Compte>	compteConnecte = new SimpleObjectProperty<>();

	
	// Autres champs
    private  Map<Integer, Compte>	mapComptes;
	

	// Getters
	
	@Override
	public Compte getCompteVue() {
		return compteVue;
	}
	
	@Override
	public ObjectProperty<Compte> compteConnecteProperty() {
		return compteConnecte;
	}
	
	@Override
	public Compte getCompteConnecte() {
		return compteConnecte.get();
	}
	
	
	// Injecteurs
	
	public void setDonnees(Donnees donnees) {
		mapComptes = donnees.getMapComptes();

		compteVue.pseudoProperty().set( "geek" );
		compteVue.motDePasseProperty().set( "geek" );
	}
	
	
	// Actions


	@Override
	public void ouvrirSessionUtilisateur() throws ExceptionValidation  {

		compteConnecte.set( null ) ;
		for ( Compte compte : mapComptes.values() ) {
			if ( compte.getPseudo().equals(compteVue.getPseudo() )
					&& compte.getMotDePasse().equals( compteVue.getMotDePasse()) ) {
				compteConnecte.set( compte ) ;
			}
		}
		
		// Message de log
		String logMessage;
		if( compteConnecte.get() == null ) {
			logMessage = "Pseudo ou mot de passe invalide : " + compteVue.pseudoProperty().get() + " / " + compteVue.motDePasseProperty().get();
		} else {
			logMessage = "\n    Utilisateur connecté : " + compteConnecte.get().getId() +  " " + compteConnecte.get().getPseudo();
			logMessage += "\n    Roles :";
			for( String role : compteConnecte.get().getRoles() ) {
				logMessage += " " + role;
			}
		}
		logger.log( Level.CONFIG, logMessage );
		
		if( compteConnecte.get() == null ) {
			throw new ExceptionValidation( "Pseudo ou mot de passe invalide." );
		}
	}
	

	@Override
	public void fermerSessionUtilisateur()  {
		compteConnecte.set( null );
	}

}

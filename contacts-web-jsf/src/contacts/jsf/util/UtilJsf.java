package contacts.jsf.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


public class UtilJsf {

	// Logger
	private static final Logger logger = Logger.getLogger( UtilJsf.class.getName() );

	
	// Messages d'erreur
	
	public static void genererMessageErreur( String message ) {
		genererMessageErreur( message, null );
	}

	public static void genererMessageErreur( Exception exception) {
		genererMessageErreur( null, exception );
	}

	public static void genererMessageErreur( String message, Exception exception) {

		String messageDefaut = null;
		
		if ( exception != null ) {

			if ( exception.getClass().getName().equals( "contacts.commun.util.ExceptionValidation") ) { 
				messageDefaut = exception.getMessage();
			} else if ( exception.getClass().getName().equals( "contacts.commun.util.ExceptionAutorisation") ) { 
				messageDefaut = "Action non autoriésé !";
				logger.log(Level.FINEST, exception.getMessage(), exception );
			} else if ( exception.getClass().getName().equals( "javax.ejb.EJBAccessException") ) {
				messageDefaut = "EJB : Action non autoriésé !";
				logger.log(Level.FINEST, exception.getMessage(), exception );
			} else if ( exception.getClass().getName().equals( "contacts.commun.util.ExceptionAnomalie") ) { 
				messageDefaut = "Echec du traitement demandé";
				logger.log(Level.FINEST, exception.getMessage(), exception );
			} else if ( exception.getClass().getName().equals( "javax.ejb.EJBException") 
					|| exception.getClass().getName().equals( "javax.ejb.EJBTransactionRolledbackException") ) {
				messageDefaut = "EJB : Echec du traitement demandé";
				logger.log(Level.FINEST, exception.getMessage(), exception );
			} else if ( exception instanceof RuntimeException ) {
				logger.log(Level.SEVERE, exception.getMessage(), exception );
			} else {
				messageDefaut = exception.getMessage();
				logger.log(Level.SEVERE, exception.getMessage(), exception );
			}

			if (message == null ) {
				if ( messageDefaut == null ) {
					message = "Ecec du traitement demandé.";
				} else {
					message = messageDefaut;
				}
			}
		}

		FacesContext fc = FacesContext.getCurrentInstance();
		String[] lignes = message.split("\\n");
	    for ( String ligne : lignes ) {
	 		fc.addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR, ligne, null ) );
	    }
	}
	
	
	public static void traiterMessageErreur() {
		String message = UtilJsf.getRequestAttribute( "messageErreur" );
		if( message != null ) {
			UtilJsf.genererMessageErreur(message);
		}
	}
	
	// Session
	
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttribute(String name) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	  	return (T) ec.getSessionMap().get( name );
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T removeSessionAttribute(String name) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	  	return (T) ec.getSessionMap().remove( name );
	}
	
	public static <T> void setSessionAttribute(String name, T value ) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	  	ec.getSessionMap().put( name, value );
	}
	
	public static void sessionInvalidate() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		if( session != null ) {
			session.invalidate();
		}
		
	}

	
	// Requête
	
	@SuppressWarnings("unchecked")
	public static <T> T getRequestAttribute(String name) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	  	return (T) ec.getRequestMap().get( name );
	}
	
	public static void forward(String uri) throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.dispatch(uri);
	}
	
	
	public static void navigate( String outcome ) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getApplication().getNavigationHandler().handleNavigation( fc, null, outcome );
	}	
		
	
	
	
	
	
	// Constructeur privé pour empêcher l'instanciaiton de la classe
	private UtilJsf() {
	}
}

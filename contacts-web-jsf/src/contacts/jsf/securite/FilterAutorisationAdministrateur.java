package contacts.jsf.securite;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import contacts.jsf.cdi.CompteConnecté;

@WebFilter(
		dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD
		}
					, 
		urlPatterns = { 
				"/jsf/categorie/*", 
				"/jsf/compte/*"
		})
public class FilterAutorisationAdministrateur implements Filter {

	
	// Champs
	
	@Inject
	private CompteConnecté		compteConnecté;
	

	public void destroy() {
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if ( compteConnecté.isInRole( "ADMINISTRATEUR") ) {
	        // si OK, on traite l'URL normalement
	        chain.doFilter(request, response);
	    } else {
	        // sinon on affiche la page d'erreur avec un message
		    request.setAttribute( "javax.servlet.error.message", "Vous n'êtes pas autorisé à effectuer l'action demandée." );
	        request.getRequestDispatcher( "/erreur.xhtml" ).forward(request, response);
	    }
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
	}

}

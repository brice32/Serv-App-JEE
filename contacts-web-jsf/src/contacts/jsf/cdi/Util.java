package contacts.jsf.cdi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.LogManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.servlet.ServletContext;

import contacts.commun.util.Roles;


@ApplicationScoped
@Named
public class Util {
	
	
	@Produces
	@ApplicationScoped
	@Named( "roles" )
	public Collection<String> getRoles() {
		return Roles.getRoles();
	}

	public String getLibelléDeRole( String role )  {
		return Roles.getLibellé( role );
	}
	
	
	
	// Evènements du cycle de vie
	
	public void applicationInit( @Observes @Initialized( ApplicationScoped.class )  ServletContext serletContext ) throws SecurityException, IOException {
    	InputStream in = this.getClass().getResourceAsStream("/META-INF/logging.properties");
    	if (in != null ) {
        	LogManager logManager = LogManager.getLogManager();
			logManager.readConfiguration( in );
			in.close();
    	}
	}

}

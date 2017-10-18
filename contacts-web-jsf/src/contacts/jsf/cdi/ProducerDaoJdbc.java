package contacts.jsf.cdi;

import java.io.IOException;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import contacts.emb.dao.IContextDao;
import contacts.emb.util.jdbc.DataSourceSingleConnection;


@ApplicationScoped
public class ProducerDaoJdbc {

	
	// Champs
	
	@Resource( name="jdbc/contacts" )
	private DataSource			pool;
	
	
	// Instanciation des composants
	
	@Produces
	@RequestScoped
	public DataSourceSingleConnection getDataSourcee() throws IOException {
//		return new DataSourceSingleConnection( this.getClass().getResourceAsStream( "/META-INF/jdbc.properties" ) );
//		return new DataSourceSingleConnection( "java:comp/env/jdbc/contacts" );
		return new DataSourceSingleConnection( pool );
	}
	
	public void close( @Disposes DataSourceSingleConnection dataSource ) {
		dataSource.closeConnection();
	}
	
	
	@Produces
	@ApplicationScoped
	public IContextDao getContextDaot( DataSource dataSource ) {
		return new contacts.emb.dao.jdbc.ContextDao( dataSource );
	}
}

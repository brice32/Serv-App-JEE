package contacts.jsf.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;

import contacts.commun.service.IContextService;
import contacts.commun.service.IServiceCategorie;
import contacts.commun.service.IServiceCompte;
import contacts.commun.service.IServiceConnexion;
import contacts.commun.service.IServicePersonne;
import contacts.emb.dao.IContextDao;
import contacts.emb.util.securite.IManagerSecurite;
import contacts.emb.util.securite.ManagerSecurite;


@ApplicationScoped
public class ProducerService {
	

	@Produces
	@ApplicationScoped
	public IContextService getContextService( IContextDao contextDao, IManagerSecurite managerSecurite ) {
		return new contacts.emb.service.standard.ContextService(contextDao, managerSecurite);
	}
	
	
	@Produces
	@SessionScoped
	public IManagerSecurite getManagerSecurite() {
		return new ManagerSecurite();
	}
	
	@Produces
	@ApplicationScoped
	public IServiceCategorie getServiceCategorie( IContextService contextService ) {
		return contextService.getService( IServiceCategorie.class );
	}
	
	@Produces
	@ApplicationScoped
	public IServiceCompte getServiceCompte( IContextService contextService ) {
		return contextService.getService( IServiceCompte.class );
	}
	
	@Produces
	@ApplicationScoped
	public IServiceConnexion getServiceConnexion( IContextService contextService ) {
		return contextService.getService( IServiceConnexion.class );
	}
	
	@Produces
	@ApplicationScoped
	public IServicePersonne getServicePersonne( IContextService contextService ) {
		return contextService.getService( IServicePersonne.class );
	}
	
}

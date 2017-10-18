package contacts.jsf.model.standard;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceCompte;
import contacts.commun.util.ExceptionValidation;
import contacts.jsf.data.Compte;
import contacts.jsf.data.mapper.IMapper;
import contacts.jsf.util.UtilJsf;


@RequestScoped
@Named
public class ModelCompteListe {

	
	// Champs
	
	private List<Compte>		comptes;
	
	@Inject
	private IServiceCompte		serviceCompte;
	
	@Inject
	private IMapper				mapper;

	
	// Getters & Setters
	
	public List<Compte> getComptes() {
		return comptes;
	}
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		comptes = new ArrayList<>();
		for ( DtoCompte dto : serviceCompte.listerTout() ) {
			comptes.add( mapper.map( dto ) );
		}
	}
	
	
	// Actions
	
	public String supprimer( Compte compte ) {
		try {
			serviceCompte.supprimer( compte.getId() );
			comptes.remove(compte);
		} catch (ExceptionValidation e) {
			UtilJsf.genererMessageErreur( e ); 
		}
		return null;
	}
	
	
}

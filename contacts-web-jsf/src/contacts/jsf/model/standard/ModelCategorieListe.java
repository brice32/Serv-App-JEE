package contacts.jsf.model.standard;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.commun.dto.DtoCategorie;
import contacts.commun.service.IServiceCategorie;
import contacts.commun.util.ExceptionValidation;
import contacts.jsf.data.Categorie;
import contacts.jsf.data.mapper.IMapper;
import contacts.jsf.util.UtilJsf;


@RequestScoped
@Named
public class ModelCategorieListe {

	
	// Champs
	
	private List<Categorie>		categories;
	
	@Inject
	private IServiceCategorie	serviceCategorie;
	
	@Inject
	private IMapper				mapper;

	
	// Getters & Setters
	
	public List<Categorie> getCategories() {
		return categories;
	}
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		categories = new ArrayList<>();
		for ( DtoCategorie dto : serviceCategorie.listerTout() ) {
			categories.add( mapper.map( dto ) );
		}
	}
	
	
	// Actions
	
	public String supprimer( Categorie categorie ) {
		try {
			serviceCategorie.supprimer( categorie.getId() );
			categories.remove(categorie);
		} catch (ExceptionValidation e) {
			UtilJsf.genererMessageErreur( e ); 
		}
		return null;
	}
	
	
}

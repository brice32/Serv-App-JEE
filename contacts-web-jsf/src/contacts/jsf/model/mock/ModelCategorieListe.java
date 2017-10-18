package contacts.jsf.model.mock;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.jsf.data.Categorie;


@RequestScoped
@Named
public class ModelCategorieListe {

	
	// Champs
	
	private List<Categorie>		categories;
	
	@Inject
	private Donnees				données;
	
	
	// Getters & Setters
	
	public List<Categorie> getCategories() {
		return categories;
	}
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		categories = données.getCategories();
	}
	
	
	// Actions
	
	public String supprimer( Categorie categorie ) {
		données.categorieSupprimer( categorie.getId() );
		categories.remove(categorie);
		return null;
	}
	
	
}

package contacts.jsf.model.mock;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.jsf.data.Categorie;
import contacts.jsf.util.UtilJsf;


@RequestScoped
@Named
public class ModelCategorieForm {

	
	// Champs
	
	private Categorie	categorie = new Categorie();
	
	@Inject
	private Donnees		données;

	
	// Getters & Setters

	public Categorie getCategorie() {
		return categorie;
	}
	
	
	// Initialisaitons
	
	public String retrouverCategorie() {
		if ( categorie.getId() != 0 ) {
			categorie = données.categorieRetrouver( categorie.getId() );
			if ( categorie == null ) {
				UtilJsf.genererMessageErreur( "La catégorie demandée n'existe pas" );
				return "test/liste";
			}
		}
		return null;
	}
	
	
	// Actions
	
	public String validerMiseAJour() {
		if ( categorie.getId() == 0) {
			données.categorieAjouter(categorie);
		} else {
			données.categorieModifier(categorie);
		}
		return "liste";
	}
	
}

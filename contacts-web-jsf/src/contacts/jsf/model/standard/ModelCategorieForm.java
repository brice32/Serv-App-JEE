package contacts.jsf.model.standard;

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
public class ModelCategorieForm {

	
	// Champs
	
	@Inject
	private IServiceCategorie	serviceCategorie;
	
	@Inject
	private IMapper				mapper;
	
	private Categorie			categorie = new Categorie();

	
	// Getters & Setters

	public Categorie getCategorie() {
		return categorie;
	}
	
	
	// Initialisaitons
	
	public String retrouverCategorie() {
		if ( categorie.getId() != 0 ) {
			DtoCategorie dto = serviceCategorie.retrouver( categorie.getId() ); 
			if ( dto == null ) {
				UtilJsf.genererMessageErreur( "La catégorie demandée n'existe pas" );
				return "test/liste";
			} else {
				categorie = mapper.map( dto );
			}
		}
		return null;
	}
	
	
	// Actions
	
	public String validerMiseAJour() {
		try {
			if ( categorie.getId() == 0) {
				serviceCategorie.inserer( mapper.map(categorie) );
			} else {
				serviceCategorie.modifier( mapper.map(categorie) );
			}
			return "liste";
		} catch (ExceptionValidation e) {
			UtilJsf.genererMessageErreur(e);
			return null;
		}
	}
	
	
	
}

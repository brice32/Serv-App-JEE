package contacts.javafx.model.standard;

import static contacts.javafx.model.EnumModeVue.CREER;
import static contacts.javafx.model.EnumModeVue.MODIFIER;

import java.util.Comparator;

import contacts.commun.dto.DtoCategorie;
import contacts.commun.service.IServiceCategorie;
import contacts.commun.util.ExceptionValidation;
import contacts.javafx.data.Categorie;
import contacts.javafx.data.mapper.IMapper;
import contacts.javafx.model.EnumModeVue;
import contacts.javafx.model.IModelCategorie;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ModelCategorie implements IModelCategorie  {
	
	
	// Données observables 
	
	private final ObservableList<Categorie> categories = FXCollections.observableArrayList( 
			g ->  new Observable[] { g.libelleProperty()  } 
		);
	
	private final Categorie	categorieVue = new Categorie();
	
	
	// Objet courant

	private Categorie			categorieCourant;
    private EnumModeVue			modeVue;

	
	// Autres champs
	private IMapper		mapper;
	private IServiceCategorie	serviceCategorie;
	
	
	// Getters 
	
	@Override
	public ObservableList<Categorie> getCategories() {
		return categories;
	}
	
	@Override
	public Categorie getCategorieVue() {
		return categorieVue;
	}
	
	
	// Injecteurs
	
	public void setMapper(IMapper mapper) {
		this.mapper = mapper;
	}
	
	public void setServiceCategorie(IServiceCategorie serviceCategorie) {
		this.serviceCategorie = serviceCategorie;
	}
	
	
	// Actualisations
	
	@Override
	public void actualiserListe() {
		
		// Prépare la récupération de l'objet courant
		int idCourant = 0;
		if( categorieCourant != null ) {
			idCourant = categorieCourant.getId();
		}

		// Actualise la liste
		categories.clear();
		for( DtoCategorie dto : serviceCategorie.listerTout() ) {
			Categorie categorie = mapper.map( dto );
			categories.add( categorie );
			if( categorie.getId() == idCourant ) {
				categorieCourant = categorie;
			}
		}
 	}


	// Actions
	
	@Override
	public void preparerAjouter() {
        modeVue = CREER;
		mapper.update( new Categorie(), categorieVue );		
	}
	
	@Override
	public void preparerModifier( Categorie categorie ) {
        modeVue = MODIFIER;
		categorieCourant = categorie;
		mapper.update( categorie, categorieVue );		
	}
	
	
	@Override
	public void validerMiseAJour() throws ExceptionValidation  {
		
		// Crée un objet contenant le données pour la mise à jour
		DtoCategorie dto = mapper.map( categorieVue );
		
		// Effectue la mise à jour
		switch( modeVue) {
		case CREER :
			int id = serviceCategorie.inserer( dto );
			categorieVue.setId(id);
			categorieCourant = mapper.update(categorieVue, new Categorie());
			categories.add(categorieCourant);
			break;
		case MODIFIER :
			serviceCategorie.modifier( dto );
			mapper.update( categorieVue, categorieCourant );		
			break;
		default:
			break;
		}
		trierListe();
	}
	
	
	@Override
	public void supprimer( Categorie categorie ) throws ExceptionValidation {
		serviceCategorie.supprimer( categorie.getId() );
		categories.remove(categorie);
	}
	
	
	// Initialisaiton
	
	public void refresh() {
		actualiserListe();
	}

	
	// Méthodes auxiliaires
    
    private void trierListe() {
		FXCollections.sort( categories,
	            (Comparator<Categorie>) ( item1, item2) -> {
	                return ( item1.getLibelle().toUpperCase().compareTo( item2.getLibelle().toUpperCase() ) );
			});
    }
}

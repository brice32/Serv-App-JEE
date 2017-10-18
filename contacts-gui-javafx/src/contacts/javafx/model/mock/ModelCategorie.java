package contacts.javafx.model.mock;

import static contacts.javafx.model.EnumModeVue.CREER;
import static contacts.javafx.model.EnumModeVue.MODIFIER;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

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
    private  Map<Integer, Categorie>	mapCategories;
	private IMapper		mapper;
	
	
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
	
	public void setDonnees(Donnees donnees) {
		mapCategories = donnees.getMapCategories();
	}
	
	public void setMapper(IMapper mapper) {
		this.mapper = mapper;
	}
	
	
	// Actualisations
	
	
	// Actualise la liste des catégories
	@Override
	public void actualiserListe()  {
        categories.clear();
        for ( Categorie categorie : mapCategories.values() ) {
        	categories.add( categorie );
        }
        trierListe();
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
	public void validerMiseAJour() {
		
        if ( modeVue == CREER ) {
            if (mapCategories.isEmpty()) {
            	categorieVue.setId(1);
            } else {
            	categorieVue.setId(Collections.max(mapCategories.keySet()) + 1);
            }
			categorieCourant = mapper.update( categorieVue, new Categorie() );
			mapCategories.put( categorieCourant.getId(), categorieCourant );
			categories.add(categorieCourant);

        } else if ( modeVue == MODIFIER ) {
			mapper.update( categorieVue, categorieCourant );		
		}
		
		trierListe();
	}
	
	
	@Override
	public void supprimer( Categorie categorie )  {
		mapCategories.remove( categorie.getId() );
		categories.remove(categorie);
	}
	
	
	// Initialisaiton
	
	public void refresh()  {
		actualiserListe();;
	}

	
	// Méthodes auxiliaires
    
    private void trierListe() {
		FXCollections.sort( categories,
	            (Comparator<Categorie>) ( item1, item2) -> {
	                return ( item1.getLibelle().toUpperCase().compareTo( item2.getLibelle().toUpperCase() ) );
			});
    }
	
}

package contacts.emb.dao.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import contacts.emb.dao.IDaoCategorie;
import contacts.emb.data.Categorie;


public class DaoCategorie implements IDaoCategorie {

	
	// Champs
	
    private Map<Integer, Categorie> 	mapCategories;

	
	// Injecteurs
	
	public void setDonnees( Donnees donnees ) {
		mapCategories = donnees.getMapCategories();
	}
	
	
	// Actions
	
	@Override
	public int inserer(Categorie categorie) {
		if ( mapCategories.isEmpty() ) {
			categorie.setId( 1 );
		} else {
			categorie.setId( Collections.max( mapCategories.keySet() ) + 1 );
		}
		mapCategories.put( categorie.getId(), categorie );
		return categorie.getId();
	}

	@Override
	public void modifier(Categorie categorie) {
		mapCategories.replace( categorie.getId(), categorie );
	}

	@Override
	public void supprimer(int idCategorie) {
		mapCategories.remove( idCategorie );
	}

	@Override
	public Categorie retrouver(int idCategorie) {
		return mapCategories.get( idCategorie );
	}

	@Override
	public List<Categorie> listerTout() {
		return trierParLibelle( new ArrayList<>(mapCategories.values() ) );
	}

	
	// Méthodes auxiliaires
    
    private List<Categorie> trierParLibelle( List<Categorie> liste ) {
		Collections.sort( liste,
	            (Comparator<Categorie>) ( item1, item2) -> {
	                return ( item1.getLibelle().toUpperCase().compareTo( item2.getLibelle().toUpperCase() ) );
			});
    	return liste;
    }
	
}

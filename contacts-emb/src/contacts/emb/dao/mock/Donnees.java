package contacts.emb.dao.mock;

import java.util.HashMap;
import java.util.Map;

import contacts.commun.util.Roles;
import contacts.emb.data.Categorie;
import contacts.emb.data.Compte;
import contacts.emb.data.Personne;
import contacts.emb.data.Telephone;


public class Donnees {

	
    // Champs 

    private final Map<Integer, Compte>  		mapComptes 		= new HashMap<>();
	private final Map<Integer, Categorie>	mapCategories 	= new HashMap<>();
	private final Map<Integer, Personne>		mapPersonnes	= new HashMap<>();

	private int 	dernierIdTelephone;

	
	// Getters
    
	public Map<Integer, Compte> getMapComptes() {
		return mapComptes;
	}

	public Map<Integer, Categorie> getMapCategories() {
		return mapCategories;
	}

	public Map<Integer, Personne> getMapPersonnes() {
		return mapPersonnes;
	}
	
	public int getProchainIdTelephone() {
		return ++dernierIdTelephone;
	}
	
	
	// Constructeur
	
	public Donnees() {
		initialiserDonnees();
	}
	
	
	// Méthodes auxiliaires

	private void initialiserDonnees() {
		
		
		// Comptes
		
		Compte compte;
		compte = new Compte( 1, "geek", "geek", "geek@3il.fr" );
		compte.getRoles().add( Roles.ADMINISTRATEUR  );
		compte.getRoles().add( Roles.UTILISATEUR  );
		mapComptes.put( compte.getId(), compte );

		compte = new Compte(2, "chef", "chef", "chef@3il.fr");
		compte.getRoles().add( Roles.UTILISATEUR  );
		mapComptes.put( compte.getId(), compte );
		
		compte = new Compte( 3, "job", "job", "job@3il.fr" );
		compte.getRoles().add( Roles.UTILISATEUR  );
		mapComptes.put( compte.getId(), compte );

		compte = new Compte(4, "_" + this.getClass().getPackage().getName(), "xxx", "xxx@3il.fr");
		compte.getRoles().add( Roles.UTILISATEUR  );
		mapComptes.put( compte.getId(), compte );

		
		// Catégories
	
		Categorie categorie;
		categorie =  new Categorie( 1, "Ecrivains" );
    	mapCategories.put( categorie.getId(), categorie );
    	categorie =  new Categorie( 2, "Peintres" );
    	mapCategories.put( categorie.getId(), categorie );

    	
    	// Personnes
    	
    	Categorie categorie1 = mapCategories.get(1);
    	Categorie categorie2 = mapCategories.get(2);

    	Personne personne; 
		
		personne = new Personne( 1, "VALADON", "Suzanne", categorie2 );
		personne.ajouterTelephone(new Telephone( 11, personne, "Portable", "06 11 11 11 11" ) );
		personne.ajouterTelephone(new Telephone( 12, personne, "Domicile", "05 55 11 11 11" ) );
		personne.ajouterTelephone(new Telephone( 13, personne, "Travail", "05 55 99 11 11" ) );
		mapPersonnes.put( personne.getId(), personne );
		
		personne = new Personne( 2, "DICKINSON", "Emily", categorie1 );
		personne.ajouterTelephone(new Telephone( 21, personne, "Portable", "06 22 22 22 22" ) );
		personne.ajouterTelephone(new Telephone( 22, personne, "Domicile", "05 55 22 22 22" ) );
		personne.ajouterTelephone(new Telephone( 23, personne, "Travail", "05 55 99 22 22" ) );
		mapPersonnes.put( personne.getId(), personne );
		
		personne = new Personne( 3, "DUMAS", "Alexandre", categorie1 );
		personne.ajouterTelephone(new Telephone( 31, personne, "Portable", "06 33 33 33 33" ) );
		personne.ajouterTelephone(new Telephone( 32, personne, "Domicile", "05 55 33 33 33" ) );
		personne.ajouterTelephone(new Telephone( 33, personne, "Travail", "05 55 99 33 33" ) );
		mapPersonnes.put( personne.getId(), personne );
        
        dernierIdTelephone = 100;
	
	}
	
	
}

package contacts.javafx.model.mock;

import static contacts.javafx.model.EnumModeVue.CREER;
import static contacts.javafx.model.EnumModeVue.MODIFIER;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import contacts.commun.util.ExceptionValidation;
import contacts.javafx.data.Personne;
import contacts.javafx.data.Telephone;
import contacts.javafx.data.mapper.IMapper;
import contacts.javafx.model.EnumModeVue;
import contacts.javafx.model.IModelPersonne;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ModelPersonne implements IModelPersonne {
	
	
	// Données observables 
	
	private final ObservableList<Personne> personnes = FXCollections.observableArrayList( 
			p ->  new Observable[] { p.nomProperty(), p.prenomProperty() } 
		);
	
	private final Personne	personneVue = new Personne();
	
	
	// Objet courant
	
	private Personne     		personneCourant;
    private EnumModeVue         modeVue;
	
	
	// Autres champs
	
	private Donnees						donnees;
    private  Map<Integer, Personne>	mapPersonnes;
	private IMapper				mapper;
	
	
	// Getters
	
	@Override
	public ObservableList<Personne> getPersonnes() {
		return personnes;
	}
	
	@Override
	public Personne getPersonneVue() {
		return personneVue;
	}
	
	
	// Injecteurs
	
	public void setDonnees(Donnees donnees) {
		this.donnees = donnees;
		mapPersonnes = donnees.getMapPersonnes();
	}
	
	public void setMapper(IMapper mapper) {
		this.mapper = mapper;
	}

	
	// Actualisations
	
	@Override
	public void actualiserListe()  {
        personnes.clear();
        for ( Personne personne : mapPersonnes.values() ) {
        	personnes.add( personne );
        }
        trierListe();
	}

	
	// Actions
	
	@Override
	public void preparerAjouter() {
        modeVue = CREER;
		personneCourant = new Personne();
		mapper.update( personneCourant, personneVue );	
	}
	
	@Override
	public void preparerModifier( Personne personne ) {
        modeVue = MODIFIER;
		personneCourant = personne;
		mapper.update( personneCourant, personneVue );	
	}
	
	@Override
	public void validerMiseAJour() throws ExceptionValidation {
		
		String nom = personneVue.nomProperty().get();
		String prenom = personneVue.prenomProperty().get();
		
		StringBuilder message = new StringBuilder();
		if( nom == null || nom.isEmpty() ) {
			message.append( "\nLe nom ne doit pas être vide." );
		} else  if ( nom.length()> 25 ) {
			message.append( "\nLe nom est trop long." );
		}
		if( prenom == null || prenom.isEmpty() ) {
			message.append( "\nLe prénom ne doit pas être vide." );
		} else if ( prenom.length()> 25 ) {
			message.append( "\nLe prénom est trop long." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		// Test si c'est un ajout ou une modificaiton
        if ( modeVue == CREER ) {
            if (mapPersonnes.isEmpty()) {
            	personneVue.setId(1);
            } else {
            	personneVue.setId(Collections.max(mapPersonnes.keySet()) + 1);
            }
        	affecterIdTelephones(personneVue);
        	mapper.update(personneVue, personneCourant);
        	mapPersonnes.put( personneCourant.getId(), personneCourant );
			personnes.add(personneCourant);
		}
        if ( modeVue == MODIFIER ) {
        	affecterIdTelephones(personneVue);
			mapper.update( personneVue, personneCourant );		
		}

        // Trie la liste
        trierListe();
	}
	
	@Override
	public void supprimer( Personne personne )  {
		mapPersonnes.remove( personne.getId() );
		personnes.remove(personne);
	}
	
	@Override
	public void ajouterTelephone() {
		personneVue.getTelephones().add( new Telephone() );
	}
	
	@Override
	public void supprimerTelephone( Telephone telephone )  {
		personneVue.getTelephones().remove( telephone );
	}
	
	
	// Initialisaiton
	
	public void refresh()  {
		actualiserListe();;
	}
    
    
    // Méthodes auxiliaires
    
    private void trierListe() {
		FXCollections.sort( personnes,
            (Comparator<Personne>) ( item1, item2) -> {
                int resultat = item1.getNom().toUpperCase().compareTo(item2.getNom().toUpperCase());
                return (resultat != 0 ? resultat : item1.getPrenom().toUpperCase().compareTo(item2.getPrenom().toUpperCase()));
		});
    }
	
	private void affecterIdTelephones( Personne personne ) {
		for( Telephone t : personne.getTelephones() ) {
			if ( t.getId() == 0 ) {
				t.setId( donnees.getProchainIdTelephone() );
			}
		}
	}
}

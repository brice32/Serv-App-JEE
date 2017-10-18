package contacts.javafx.model.standard;

import static contacts.javafx.model.EnumModeVue.CREER;
import static contacts.javafx.model.EnumModeVue.MODIFIER;

import java.util.Comparator;

import contacts.commun.dto.DtoPersonne;
import contacts.commun.service.IServicePersonne;
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
	
    private EnumModeVue   modeVue;
	private Personne     		personneCourant;
	
	
	// Autres champs
	
	private IMapper		        mapper;
	private IServicePersonne	servicePersonne;
	
	
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
	
	public void setMapper(IMapper mapper) {
		this.mapper = mapper;
	}
	
	public void setServicePersonne(IServicePersonne servicePersonne) {
		this.servicePersonne = servicePersonne;
	}

	
	// Actualisations
	
	@Override
	public void actualiserListe() {

		// Prépare la récupération de l'objet courant
		int idCourant = 0;
		if( personneCourant != null ) {
			idCourant = personneCourant.getId();
		}
		
		// Actualise la liste
		personnes.clear();
		for( DtoPersonne dto : servicePersonne.listerTout() ) {
            Personne personne = mapper.map( dto );
			personnes.add( personne );
			if( personne.getId() == idCourant ) {
				personneCourant = personne;
			}
		}
        trierListe();
	}

	
	// Actions
	
	@Override
	public void preparerAjouter() {
        modeVue = CREER;
		personneCourant = null;
		mapper.update( new Personne(), personneVue );		
	}
	
	@Override
	public void preparerModifier( Personne personne ) {
        modeVue = MODIFIER;
		personneCourant = personne;
		mapper.update( personne, personneVue );
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
		
		// Crée un objet contenant le données pour la mise à jour
		DtoPersonne dto = mapper.map( personneVue );
		
		// Test si c'est un ajout ou une modificaiton
        if ( modeVue == CREER ) {
			int id = servicePersonne.inserer( dto );
			// Récupère les id des téléphones
			dto = servicePersonne.retrouver(id);
            personneCourant = mapper.map( dto );
			personnes.add(personneCourant);
		}
        if ( modeVue == MODIFIER ) {
			servicePersonne.modifier( dto );
			// Récupère les id des téléphones
			dto = servicePersonne.retrouver(dto.getId());
//			mapper.update( dto, personneCourant );
			mapper.update(mapper.map(dto), personneCourant);
		}

        // Trie la liste
        trierListe();
	}
	
	@Override
	public void supprimer( Personne personne ) throws ExceptionValidation {
		servicePersonne.supprimer( personne.getId() );
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
	
	public void refresh() {
		actualiserListe();
	}
    
    
    // Méthodes auxiliaires
    
    private void trierListe() {
		FXCollections.sort( personnes,
            (Comparator<Personne>) ( p1, p2) -> {
                int lastCmp = p1.nomProperty().get().toUpperCase().compareTo(p2.nomProperty().get().toUpperCase());
                return (lastCmp != 0 ? lastCmp : p1.prenomProperty().get().toUpperCase().compareTo(p2.prenomProperty().get()));
		});
    }
	
}

package contacts.javafx.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import contacts.commun.dto.DtoCategorie;
import contacts.commun.dto.DtoCompte;
import contacts.commun.dto.DtoPersonne;
import contacts.commun.dto.DtoTelephone;
import contacts.javafx.data.Categorie;
import contacts.javafx.data.Compte;
import contacts.javafx.data.Personne;
import contacts.javafx.data.Telephone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
   

@Mapper( uses=IMapper.FactoryObsservableList.class  )
public interface IMapper { 
	
	IMapper INSTANCE = Mappers.getMapper( IMapper.class );
	
	
	// Compte
	
	Compte map( DtoCompte source );
	
	DtoCompte map( Compte source );
	
	Compte update( Compte source, @MappingTarget Compte target );
	
	
	// Categorie
	
	Categorie map( DtoCategorie source );
	
	DtoCategorie map( Categorie source );
	
	Categorie update( Categorie source, @MappingTarget Categorie target );
	
	
	// Personne
	
    Personne map( DtoPersonne source );
	
	DtoPersonne map( Personne source );
	
    @Mapping( target="categorie", expression="java( source.getCategorie() )" )
    Personne update( Personne source, @MappingTarget Personne target );
	
	
	// Telephone
	
    Telephone map( DtoTelephone source );
	
    DtoTelephone map( Telephone source );

    // Méthodes nécessaire pour update( FXPersonne )
    Telephone duplicate( Telephone source );
    ObservableList<Telephone> duplicate( ObservableList<Telephone> source );
    
	
	
    // Classe auxiliaire
    
    public static class FactoryObsservableList {

    	ObservableList<Telephone> createObsListFXTelephone() {
    		return FXCollections.observableArrayList();
    	}

    	ObservableList<String> createObsListString() {
    		return FXCollections.observableArrayList();
    	}

    }
    
}

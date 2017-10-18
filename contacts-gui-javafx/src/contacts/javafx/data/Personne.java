package contacts.javafx.data;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Personne {


	// Données observables
	
	private final IntegerProperty	id			= new SimpleIntegerProperty();
	private final StringProperty	nom	 		= new SimpleStringProperty();
	private final StringProperty	prenom	= new SimpleStringProperty();
	private final ObjectProperty<Categorie>	categorie	= new SimpleObjectProperty<>();
	private final ObservableList<Telephone>	telephones	= FXCollections.observableArrayList(
			t ->  new Observable[] { t.libelleProperty(), t.numeroProperty() } 
		);
	
	
	// Getters & setters

	public final IntegerProperty idProperty() {
		return this.id;
	}

	public final int getId() {
		return this.idProperty().get();
	}

	public final void setId(final int id) {
		this.idProperty().set(id);
	}
	
	public final StringProperty nomProperty() {
		return this.nom;
	}
	
	public final java.lang.String getNom() {
		return this.nomProperty().get();
	}
	
	public final void setNom(final java.lang.String nom) {
		this.nomProperty().set(nom);
	}
	
	public final StringProperty prenomProperty() {
		return this.prenom;
	}
	
	public final java.lang.String getPrenom() {
		return this.prenomProperty().get();
	}
	
	public final void setPrenom(final java.lang.String prenom) {
		this.prenomProperty().set(prenom);
	}

	public final ObjectProperty<Categorie> categorieProperty() {
		return this.categorie;
	}

	public final contacts.javafx.data.Categorie getCategorie() {
		return this.categorieProperty().get();
	}

	public final void setCategorie(final contacts.javafx.data.Categorie categorie) {
		this.categorieProperty().set(categorie);
	}

	public ObservableList<Telephone> getTelephones() {
		return telephones;
	}

	
	// toString()
	
	@Override
	public String toString() {
		return getNom() + " " + getPrenom();
	}
	
	
	// Constructeurs
	
	public Personne() {
	}
	
	public Personne( int id, String nom, String prenom, Categorie categorie ) {
		setId(id);
		setNom(nom);
		setPrenom(prenom);
		setCategorie(categorie);
	}
	
}

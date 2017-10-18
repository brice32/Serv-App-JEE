package contacts.javafx.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Categorie  {
	

	// Données observables
	
	private final IntegerProperty	id	 = new SimpleIntegerProperty();
	private final StringProperty	libelle	 = new SimpleStringProperty();
	
	
	// Getters et Setters

	public final IntegerProperty idProperty() {
		return this.id;
	}

	public final Integer getId() {
		return this.idProperty().get();
	}

	public final void setId(final Integer id) {
		this.idProperty().set(id);
	}

	public final StringProperty libelleProperty() {
		return this.libelle;
	}

	public final String getLibelle() {
		return this.libelleProperty().get();
	}

	public final void setLibelle(final String libelle) {
		this.libelleProperty().set(libelle);
	}

	
	// toString()
	
	@Override
	public String toString() {
		return getLibelle();
	}
	
	
	// Constructeurs
	
	public Categorie() {
	}

	public Categorie( final int id, final String libelle ) {
		setId(id);
		setLibelle(libelle);
	}


}


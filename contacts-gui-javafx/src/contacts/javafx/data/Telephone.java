package contacts.javafx.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Telephone {
	

	// Données observables
	
	private final IntegerProperty	id			= new SimpleIntegerProperty();
	private final StringProperty	libelle		= new SimpleStringProperty();
	private final StringProperty	numero		= new SimpleStringProperty();
	
	
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

	
	public final StringProperty libelleProperty() {
		return this.libelle;
	}

	public final java.lang.String getLibelle() {
		return this.libelleProperty().get();
	}

	public final void setLibelle(final java.lang.String libelle) {
		this.libelleProperty().set(libelle);
	}
	

	public final StringProperty numeroProperty() {
		return this.numero;
	}

	public final java.lang.String getNumero() {
		return this.numeroProperty().get();
	}

	public final void setNumero(final java.lang.String numero) {
		this.numeroProperty().set(numero);
	}

	
	// toString()
	
	@Override
	public String toString() {
		return getLibelle();
	}
	
	
	// Constructeurs
	
	public Telephone() {
	}
	
	public Telephone( int id, String libelle, String numero ) {
		setId(id);
		setLibelle(libelle);
		setNumero(numero);
	}

}

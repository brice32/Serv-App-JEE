package contacts.javafx.model;

import contacts.commun.util.ExceptionValidation;
import contacts.javafx.data.Categorie;
import javafx.collections.ObservableList;


public interface IModelCategorie {

	ObservableList<Categorie> getCategories();

	Categorie getCategorieVue();

	void actualiserListe();

	void preparerAjouter();

	void preparerModifier(Categorie categorie);

	void validerMiseAJour() throws ExceptionValidation;

	void supprimer(Categorie categorie) throws ExceptionValidation;

}
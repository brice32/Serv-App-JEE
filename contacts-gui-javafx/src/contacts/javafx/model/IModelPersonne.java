package contacts.javafx.model;

import contacts.commun.util.ExceptionValidation;
import contacts.javafx.data.Personne;
import contacts.javafx.data.Telephone;
import javafx.collections.ObservableList;


public interface IModelPersonne {

	ObservableList<Personne> getPersonnes();

	Personne getPersonneVue();

	void actualiserListe();

	void preparerAjouter();

	void preparerModifier(Personne personne);

	void validerMiseAJour() throws ExceptionValidation;

	void supprimer(Personne personne) throws ExceptionValidation;

	void ajouterTelephone();

	void supprimerTelephone(Telephone telephone);

}
package contacts.javafx.view.personne;

import contacts.javafx.data.Categorie;
import contacts.javafx.model.IModelCategorie;
import contacts.javafx.view.EnumView;
import contacts.javafx.view.IManagerGui;
import contacts.javafx.view.util.ListenerListViewSetFocus;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class ControllerCategorieListe {
	
	
	// Composants de la vue

	@FXML
	private ListView<Categorie> listView;
	@FXML
	private Button				buttonModifier;
	@FXML
	private Button				buttonSupprimer;


	// Autres champs
	
	private IManagerGui			managerGui;
	private IModelCategorie		modelCategorie;
	
	
	// Injecteurs
	
	public void setManagerGui(IManagerGui managerGui) {
		this.managerGui = managerGui;
	}
	
	public void setModelCategorie(IModelCategorie modelCategorie) {
		this.modelCategorie = modelCategorie;
	}
	
	
	// Initialisation du Controller

	public void init() {
		
		// Configuration de l'objet ListView

		// Data binding
		listView.setItems( modelCategorie.getCategories() );
		
		// Configuraiton des boutons
		listView.getSelectionModel().getSelectedItems().addListener( 
		        (ListChangeListener<Categorie>) (c) -> {
		        	configurerBoutons();
		        });
		configurerBoutons();					
		
		// Comportement si changement du contenu
		listView.getItems().addListener( new ListenerListViewSetFocus<>(listView));
		
	}

	
	// Actions

	@FXML
	private void doActualiser()  {
		try {
			modelCategorie.actualiserListe();
			listView.getSelectionModel().clearSelection();
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}
	
	@FXML
	private void doAjouter() {
		try {
			modelCategorie.preparerAjouter();;
			managerGui.showView( EnumView.CategorieForm );
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}

	@FXML
	private void doModifier() {
		try {
			Categorie c = listView.getSelectionModel().getSelectedItem();
			modelCategorie.preparerModifier(c);
			managerGui.showView( EnumView.CategorieForm );
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}

	@FXML
	private void doSupprimer() {
		try {
			boolean reponse = managerGui.demanderConfirmation( "Confirmez-vous la suppresion ?" );
			if ( reponse ) {
				Categorie c = listView.getSelectionModel().getSelectedItem();
				modelCategorie.supprimer(c);
			}
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}
	
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				doModifier();
			}
		}
	}

	
	// Méthodes auxiliaires
	
	private void configurerBoutons() {
    	if( listView.getSelectionModel().getSelectedItem() == null ) {
    		buttonModifier.setDisable(true);
    		buttonSupprimer.setDisable(true);
    	} else {
    		buttonModifier.setDisable(false);
    		buttonSupprimer.setDisable(false);
    	}
	}

}

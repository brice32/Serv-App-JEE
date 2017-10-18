package contacts.javafx.view.personne;

import contacts.javafx.data.Personne;
import contacts.javafx.model.IModelPersonne;
import contacts.javafx.view.EnumView;
import contacts.javafx.view.IManagerGui;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class ControllerPersonneListe  {
	
	
	// Composants de la vue
	
	@FXML
	private ListView<Personne> listView;
	@FXML
	private Button				buttonModfier;
	@FXML
	private Button				buttonSupprimer;
	
	
	// Autres champs
	
	private IManagerGui			managerGui;
	private IModelPersonne		modelPersonne;
	
	
	// Injecteurs
	
	public void setManagerGui(IManagerGui managerGui) {
		this.managerGui = managerGui;
	}
	
	public void setModelPersonne(IModelPersonne modelPersonne) {
		this.modelPersonne = modelPersonne;
	}
	
	
	// Initialisation du controller

	public void init() {
		
		// Data binding
		listView.setItems( modelPersonne.getPersonnes() );
		
		// Configuraiton des boutons
		listView.getSelectionModel().getSelectedItems().addListener( 
		        (ListChangeListener<Personne>) (c) -> {
		        	configurerBoutons();
		});
    	configurerBoutons();
		
		// Comportement si changement du contenu
		listView.getItems().addListener( (ListChangeListener<Personne>) (c) -> {
			c.next();
			// Après insertion d'un élément, le sélectionne
			// Après suppression d'un élément, sélectionne le suivant
			if ( c.wasAdded() || c.wasRemoved() ) {
				listView.getSelectionModel().clearSelection();
				listView.getSelectionModel().select( c.getFrom());
				listView.getFocusModel().focus(c.getFrom());
				listView.scrollTo( c.getFrom());
				listView.requestFocus();
			}
		});
	}
	
	
	// Actions
	
	@FXML
	private void doActualiser() {
		try {
			modelPersonne.actualiserListe();
			listView.getSelectionModel().clearSelection();;
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}
	
	@FXML
	private void doAjouter() {
		try {
			managerGui.showView( EnumView.PersonneForm );
			modelPersonne.preparerAjouter();
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}
	
	@FXML
	private void doModifier() {
		try {
			managerGui.showView( EnumView.PersonneForm );
			Personne p = listView.getSelectionModel().getSelectedItem();
			modelPersonne.preparerModifier(p);
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}
	
	@FXML
	private void doSupprimer() {
		try {
			if ( managerGui.demanderConfirmation("Etes-vous sûr de voulir supprimer cette personne ?" ) ) {
				Personne p = listView.getSelectionModel().getSelectedItem();
				modelPersonne.supprimer(p);
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
    		buttonModfier.setDisable(true);
    		buttonSupprimer.setDisable(true);
    	} else {
    		buttonModfier.setDisable(false);
    		buttonSupprimer.setDisable(false);
    	}
	}
	
}

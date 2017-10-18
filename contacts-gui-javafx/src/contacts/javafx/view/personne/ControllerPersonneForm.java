package contacts.javafx.view.personne;

import contacts.javafx.data.Categorie;
import contacts.javafx.data.Personne;
import contacts.javafx.data.Telephone;
import contacts.javafx.model.IModelCategorie;
import contacts.javafx.model.IModelPersonne;
import contacts.javafx.view.EnumView;
import contacts.javafx.view.IManagerGui;
import contacts.javafx.view.util.EditingCell;
import contacts.javafx.view.util.StringBindingId;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class ControllerPersonneForm  {
	
	
	// Composants de la vue
	
	@FXML
	private TextField			textFieldId;
	@FXML
	private TextField			textFieldNom;
	@FXML	
	private TextField			textFieldPrenom;
    @FXML
    private ComboBox<Categorie>	comboBoxCategorie;
	@FXML
	private TableView<Telephone>	tableViewTelphones;
	@FXML
	private TableColumn<Telephone, Number> columnId;
	@FXML
	private TableColumn<Telephone, String> columnLibelle;
	@FXML
	private TableColumn<Telephone, String> columnNumero;

	
	// Autres champs
	private IManagerGui			managerGui;
	private IModelPersonne		modelPersonne;
    private IModelCategorie 	modelCategorie;

    
    // Injecteurs
    
    public void setManagerGui(IManagerGui managerGui) {
		this.managerGui = managerGui;
	}
    
    public void setModelCategorie(IModelCategorie modelCategorie) {
		this.modelCategorie = modelCategorie;
	}
    
    public void setModelPersonne(IModelPersonne modelPersonne) {
		this.modelPersonne = modelPersonne;
	}
    
	
	// Initialisation du controller
	
	public void init() {
		
		// Champs simples
		Personne personneVue = modelPersonne.getPersonneVue();
		textFieldId.textProperty().bind( new StringBindingId( personneVue.idProperty() ));
		textFieldNom.textProperty().bindBidirectional( personneVue.nomProperty() );
		textFieldPrenom.textProperty().bindBidirectional( personneVue.prenomProperty() );

        
		// Configuration de la combo box

		// Data binding
        comboBoxCategorie.valueProperty().bindBidirectional( personneVue.categorieProperty() );
		comboBoxCategorie.setItems( modelCategorie.getCategories() );
 		
		
		// Configuration du TableView

		// Data binding
		tableViewTelphones.setItems(  modelPersonne.getPersonneVue().getTelephones() );
		
//		columnId.setCellValueFactory( new PropertyValueFactory<FxPersonne, Integer>("id"));
		columnId.setCellValueFactory( t -> t.getValue().idProperty() );
		columnLibelle.setCellValueFactory( t -> t.getValue().libelleProperty() );
		columnNumero.setCellValueFactory( t -> t.getValue().numeroProperty() );

		columnLibelle.setCellFactory(  p -> new EditingCell<>() );
		columnNumero.setCellFactory(  p -> new EditingCell<>() );		
	
	}
	
	
	// Actions
	
	@FXML
	private void doValider() {
		try {
			modelPersonne.validerMiseAJour();
			managerGui.showView( EnumView.PersonneListe );
		} catch (Exception e) {
			managerGui.afficherErreur( e );
		}
	}
	
	@FXML
	private void doAnnuler() {
		try {
			managerGui.showView( EnumView.PersonneListe );
		} catch (Exception e) {
			managerGui.afficherErreur( e );
		}
	}
	
	@FXML
	private void doAjouterTelephone() {
		try {
			modelPersonne.ajouterTelephone();
		} catch (Exception e) {
			managerGui.afficherErreur( e );
		}
	}
	
	
	@FXML
	private void doiSupprimerTelephone() {
		try {
			Telephone telephone = tableViewTelphones.getSelectionModel().getSelectedItem();
			modelPersonne.supprimerTelephone(telephone);
		} catch (Exception e) {
			managerGui.afficherErreur( e );
		}
	}
    
}

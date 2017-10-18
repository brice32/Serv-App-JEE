package contacts.javafx.view.personne;

import contacts.javafx.data.Categorie;
import contacts.javafx.model.IModelCategorie;
import contacts.javafx.view.EnumView;
import contacts.javafx.view.IManagerGui;
import contacts.javafx.view.util.StringBindingId;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class ControllerCategorieForm {

	
	// Composants de la vue
	
	@FXML
	private TextField			textFieldId;
	@FXML
	private TextField			textFieldLibelle;


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

		// Data binding
		
		Categorie categorieVue = modelCategorie.getCategorieVue();
		textFieldId.textProperty().bind( new StringBindingId( categorieVue.idProperty() ) );
		textFieldLibelle.textProperty().bindBidirectional( categorieVue.libelleProperty()  );
		
	}
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		try {
			managerGui.showView( EnumView.CategorieListe );
		} catch (Exception e) {
			managerGui.afficherErreur( e );
		}
	}
	
	@FXML
	private void doValider()  {
		try {
			modelCategorie.validerMiseAJour();
			managerGui.showView( EnumView.CategorieListe );
		} catch (Exception e) {
			managerGui.afficherErreur( e );
		}
	}

}

package contacts.javafx.view.systeme;

import contacts.commun.util.Roles;
import contacts.javafx.model.IModelConnexion;
import contacts.javafx.view.EnumView;
import contacts.javafx.view.IManagerGui;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;


public class ControllerPrincipal  {
 	

	// Composants de la vue
	
	@FXML
	private MenuItem	menuItemSeDeconnecter;
	@FXML
	private Menu 		menuDonnees;
	@FXML
	private MenuItem	menuItemCategories;
	@FXML
	private MenuItem	menuItemComptes;

	
	// Autres champs
	
	private IManagerGui		managerGui;
	private IModelConnexion	modelConnexion;

	
	// Injecteurs
	
	public void setManagerGui(IManagerGui managerGui) {
		this.managerGui = managerGui;
	}
	
	public void setModelConnexion(IModelConnexion modelConnexion) {
		this.modelConnexion = modelConnexion;
	}
	
	
	// Initialisation du Controller
	
	public void init() {
		
		// Le changement du compte connecté modifie automatiquement le menu
		modelConnexion.compteConnecteProperty().addListener(
				( ov, oldValue, newValue) -> {
					configurerMenu();
				}
			); 

		// Adapte le menu
		configurerMenu();
	}
	

	// Actions
	
	@FXML
	public void doSeDeconnecter() {
		try {
			modelConnexion.fermerSessionUtilisateur();
			managerGui.reinit();
			setManagerGui(managerGui);
			managerGui.showView( EnumView.Connexion );
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}
	
	@FXML
	public void doQuitter() {
		try {
			managerGui.close();
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}
	
	@FXML
	public void doAfficherListeComptes() {
		try {
			managerGui.showView( EnumView.CompteListe );;
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}
	
	@FXML
	public void doAfficherListePersonnes() {
		try {
			managerGui.showView( EnumView.PersonneListe );;
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}
	
	@FXML
	public void doAfficherListeCategories() {
		try {
			managerGui.showView( EnumView.CategorieListe );;
		} catch (Exception e) {
			managerGui.afficherErreur(e);
		}
	}
	

	// Méthodes auxiliaires
	
	private void configurerMenu() {

		menuItemSeDeconnecter.setDisable(true);
		
		menuDonnees.setVisible(false);
		menuItemCategories.setVisible(false);
		menuItemComptes.setVisible(false);
		
		if( modelConnexion.getCompteConnecte() != null ) {
			menuItemSeDeconnecter.setDisable(false);
			if( modelConnexion.getCompteConnecte().isInRole( Roles.UTILISATEUR) ) {
				menuDonnees.setVisible(true);
			}
			if( modelConnexion.getCompteConnecte().isInRole( Roles.ADMINISTRATEUR ) ) {
				menuDonnees.setVisible(true);
				menuItemCategories.setVisible(true);
				menuItemComptes.setVisible(true);
			}
		}
	}

}

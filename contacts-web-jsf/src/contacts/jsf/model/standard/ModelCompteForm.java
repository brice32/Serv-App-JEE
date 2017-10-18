package contacts.jsf.model.standard;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceCompte;
import contacts.commun.util.ExceptionValidation;
import contacts.jsf.data.Compte;
import contacts.jsf.data.mapper.IMapper;
import contacts.jsf.util.UtilJsf;


@RequestScoped
@Named
public class ModelCompteForm {

	
	// Champs
	
	@Inject
	private IServiceCompte	serviceCompte;
	
	@Inject
	private IMapper			mapper;
	
	private Compte			compte = new Compte();

	
	// Getters & Setters

	public Compte getCompte() {
		return compte;
	}
	
	
	// Initialisaitons
	
	public String retrouverCompte() {
		if ( compte.getId() != 0 ) {
			DtoCompte dto = serviceCompte.retrouver( compte.getId() ); 
			if ( dto == null ) {
				UtilJsf.genererMessageErreur( "Le compte demandé n'existe pas" );
				return "test/liste";
			} else {
				compte = mapper.map( dto );
			}
		}
		return null;
	}
	
	
	// Actions
	
	public String validerMiseAJour() {
		try {
			if ( compte.getId() == 0) {
				serviceCompte.inserer( mapper.map(compte) );
			} else {
				serviceCompte.modifier( mapper.map(compte) );
			}
			return "liste";
		} catch (ExceptionValidation e) {
			UtilJsf.genererMessageErreur(e);
			return null;
		}
	}
	
	
	
}

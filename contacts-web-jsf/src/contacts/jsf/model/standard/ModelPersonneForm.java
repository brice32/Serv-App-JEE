package contacts.jsf.model.standard;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.commun.dto.DtoPersonne;
import contacts.commun.service.IServicePersonne;
import contacts.commun.util.ExceptionValidation;
import contacts.jsf.data.Personne;
import contacts.jsf.data.Telephone;
import contacts.jsf.data.mapper.IMapper;
import contacts.jsf.util.UtilJsf;


@SuppressWarnings("serial")
@ViewScoped
@Named
public class ModelPersonneForm implements Serializable {

	
	// Champs
	
	@Inject
	private IServicePersonne	servicePersonne;

	@Inject
	private IMapper				mapper;
	
	private Personne			personne = new Personne();

	
	// Getters & Setters

	public Personne getPersonne() {
		return personne;
	}
	
	
	// Initialisaitons
	
	public String retrouverPersonne() {
		if ( personne.getId() != 0 ) {
			DtoPersonne dto = servicePersonne.retrouver( personne.getId() ); 
			if ( dto == null ) {
				UtilJsf.genererMessageErreur( "La personne demandée n'existe pas" );
				return "test/liste";
			} else {
				personne = mapper.map( dto );
			}
		}
		return null;
	}
	
	
	// Actions
	
	public String validerMiseAJour() {
		try {
			if ( personne.getId() == 0) {
				servicePersonne.inserer( mapper.map(personne) );
			} else {
				servicePersonne.modifier( mapper.map(personne) );
			}
			return "liste";
		} catch (ExceptionValidation e) {
			UtilJsf.genererMessageErreur(e);
			return null;
		}
	}
	
	
	public String ajouterTelephone() {
		personne.getTelephones().add( new Telephone() );
		return null;
	}
	
	
	public String supprimerTelephone( Telephone telephone ) {
		personne.getTelephones().remove( telephone );
		return null;
	}
	
}

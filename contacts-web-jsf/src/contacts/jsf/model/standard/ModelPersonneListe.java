package contacts.jsf.model.standard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import contacts.commun.dto.DtoPersonne;
import contacts.commun.service.IServicePersonne;
import contacts.commun.util.ExceptionValidation;
import contacts.jsf.data.Personne;
import contacts.jsf.data.mapper.IMapper;
import contacts.jsf.util.UtilJsf;


//@RequestScoped
@SuppressWarnings("serial")
@ViewScoped
@Named
public class ModelPersonneListe implements Serializable {

	
	// Champs
	
	private List<Personne>		personnes;
	
	@Inject
	private IServicePersonne	servicePersonne;
	
	@Inject
	private IMapper				mapper;

	
	// Getters & Setters
	
	public List<Personne> getPersonnes() {
		return personnes;
	}
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		personnes = new ArrayList<>();
		for ( DtoPersonne dto : servicePersonne.listerTout() ) {
			personnes.add( mapper.map( dto ) );
		}
	}
	
	public String initialiserListe() {
		return null;
	}
	
	
	// Actions
	
	public String supprimer( Personne personne ) {
		try {
			servicePersonne.supprimer( personne.getId() );
			personnes.remove(personne);
		} catch (ExceptionValidation e) {
			UtilJsf.genererMessageErreur( e ); 
		}
		return null;
	}
	
	
}

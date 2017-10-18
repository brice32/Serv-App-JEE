package contacts.jsf.cdi;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import contacts.jsf.data.Compte;

@SuppressWarnings("serial")
@SessionScoped
@Named
public class CompteConnecté extends Compte {

}

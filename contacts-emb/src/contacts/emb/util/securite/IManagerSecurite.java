package contacts.emb.util.securite;

import contacts.commun.dto.DtoCompte;
import contacts.commun.util.ExceptionAutorisation;


public interface IManagerSecurite {

	int		getIdCompteConnecte();
	
	void 	verifierAutorisationUtilisateurOuAdmin() throws ExceptionAutorisation;
	
	void 	verifierAutorisationAdmin() throws ExceptionAutorisation;
	
	void 	verifierAutorisationAdminOuCompteConnecte( int idCompte ) throws ExceptionAutorisation;

	void	setCompteConnecté( DtoCompte compteConnecté );

}
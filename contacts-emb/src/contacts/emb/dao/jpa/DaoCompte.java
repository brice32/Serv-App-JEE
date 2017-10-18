package contacts.emb.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import contacts.emb.dao.IDaoCompte;
import contacts.emb.data.Compte;


public class DaoCompte implements IDaoCompte {

	
	// Champs
	
	private EntityManager 				em;
	
	// Injecteurs

	public void setEntityManager( EntityManager em ) {
		this.em=em;
	}
	
	// Actions
	
	@Override
	public int inserer(Compte compte) {
        em.persist(compte);
        em.flush();
        return compte.getId();
	}

	@Override
	public void modifier(Compte compte) {
		em.merge(compte);
	}

	@Override
	public void supprimer(int idCompte) {
		Compte compte = retrouver(idCompte);
		em.remove(compte);
	}

	@Override
	public Compte retrouver(int idCompte) {
		return em.find(Compte.class, idCompte);
	}

	@Override
	public List<Compte> listerTout() {
		String jpql="SELECT c FROM Compte c ORDER BY c.pseudo";
		TypedQuery<Compte> query = em.createQuery( jpql , Compte.class);
		return query.getResultList();
	}


	@Override
	public Compte validerAuthentification( String pseudo, String motDePasse )  {

		for ( Compte compte : listerTout() ) {
			if ( compte.getPseudo().equals(pseudo) ) {
				if ( compte.getMotDePasse().equals(motDePasse) ) {
					return compte;
				}
				break;
			}
		}
		return null;
	}


	@Override
	public boolean verifierUnicitePseudo( String pseudo, int idCompte )  {
		
		for ( Compte compte : listerTout() ) {
			if ( compte.getPseudo().equals(pseudo) ) {
				if ( compte.getId() != idCompte  ) {
					return false;
				}
			}
		}
		return true;
	}
	
}

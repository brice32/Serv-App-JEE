package contacts.emb.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import contacts.emb.dao.IDaoCategorie;
import contacts.emb.data.Categorie;


public class DaoCategorie implements IDaoCategorie {

	
	// Champs

    private EntityManager em;
	
	// Injecteurs
	
	public void setEntityManager( EntityManager em ) {
		this.em = em;
	}
	
	// Actions
	
	@Override
	public int inserer(Categorie categorie) {
		em.persist(categorie);
		em.flush();
		return categorie.getId();
	}

	@Override
	public void modifier(Categorie categorie) {
		em.merge(categorie);
	}

	@Override
	public void supprimer(int idCategorie) {
		Categorie categorie = retrouver(idCategorie);
		em.remove(categorie);
	}

	@Override
	public Categorie retrouver(int idCategorie) {
		return em.find(Categorie.class, idCategorie);
	}

	@Override
	public List<Categorie> listerTout() {
		String jpql = "SELECT c FROM Categorie c" ;
		TypedQuery<Categorie> query = em.createQuery( jpql, Categorie.class );
		return query.getResultList();
	}
	
}

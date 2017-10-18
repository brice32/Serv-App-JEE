package contacts.emb.dao.jpa;

import java.util.logging.Logger;

import javax.persistence.EntityManager;

import contacts.emb.dao.IManagerTransaction;


public class ManagerTransaction implements IManagerTransaction {
	
	
	// Logger
	private static final Logger logger = Logger.getLogger(ManagerTransaction.class.getName());
	
	private EntityManager em;
	
	// Actions

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public void begin() {
		em.getTransaction().begin();
		logger.finer("Transaction BEGIN");
	}

	@Override
	public void commit() {
		em.getTransaction().commit();
		logger.finer("Transaction COMMIT");
	}

	@Override
	public void rollback() {
		if( em.getTransaction().isActive() ) {
			em.getTransaction().rollback() ;
			}
		logger.finer("Transaction ROLLBACK");
	}



}

package contacts.emb.service.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceCompte;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionValidation;
import contacts.emb.util.securite.IManagerSecurite;


public class ServiceCompte implements IServiceCompte {

    
    // Logger
    
    private static final Logger logger = Logger.getLogger(ServiceCompte.class.getName());

	
    // Champs 

    private  Map<Integer, DtoCompte>  mapComptes;
	
	private IManagerSecurite	managerSecurite;
	
	
	// Injecteurs
	
	public void setDonnees( Donnees donnees ) {
		mapComptes = donnees.getMapComptes();
	}
	
	public void setManagerSecurite(IManagerSecurite managerSecurite) {
		this.managerSecurite = managerSecurite;
	}

    
    // Actions 
    
    @Override
    public int inserer( DtoCompte dtoCompte ) throws ExceptionValidation {

        try {
            managerSecurite.verifierAutorisationAdmin();
            verifierValiditeDonnees(dtoCompte);
            if (mapComptes.isEmpty()) {
                dtoCompte.setId(1);
            } else {
                dtoCompte.setId(Collections.max(mapComptes.keySet()) + 1);
            }
            mapComptes.put(dtoCompte.getId(), dtoCompte);
            return dtoCompte.getId();
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ExceptionAnomalie(e);
        }
    }

    @Override
    public void modifier( DtoCompte dtoCompte ) throws ExceptionValidation {
        try {
            managerSecurite.verifierAutorisationAdmin();
            verifierValiditeDonnees(dtoCompte);
            mapComptes.replace(dtoCompte.getId(), dtoCompte);
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ExceptionAnomalie(e);
        }
    }

    @Override
    public void supprimer( int idCompte ) throws ExceptionValidation {
        try {
            managerSecurite.verifierAutorisationAdmin();
            if (managerSecurite.getIdCompteConnecte() == idCompte) {
                throw new ExceptionValidation("Vous ne pouvez pas supprimer le compte avec lequel vous êtes connecté !");
            }

            mapComptes.remove(idCompte);
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ExceptionAnomalie(e);
        }
    }

    @Override
    public DtoCompte retrouver( int idCompte ) {
        try {
            managerSecurite.verifierAutorisationAdmin();
            return mapComptes.get(idCompte);
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ExceptionAnomalie(e);
        }
    }

    @Override
    public List<DtoCompte> listerTout() {
        try {
            managerSecurite.verifierAutorisationAdmin();
            return trierParPseudo( new ArrayList<>(mapComptes.values()) );
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ExceptionAnomalie(e);
        }
    }

    
    // Méthodes auxiliaires
    
    private void verifierValiditeDonnees( DtoCompte dtoCompte ) throws ExceptionValidation {

        StringBuilder message = new StringBuilder();

        if (dtoCompte.getPseudo() == null || dtoCompte.getPseudo().isEmpty()) {
            message.append("\nLe pseudo est absent.");
        } else if (dtoCompte.getPseudo().length() < 3) {
            message.append("\nLe pseudo est trop court.");
        } else if (dtoCompte.getPseudo().length() > 25) {
            message.append("\nLe pseudo est trop long.");
        } else {
            boolean pseudoDejaPresent = false;
            for (DtoCompte c : mapComptes.values()) {
                if (c.getPseudo().equals(dtoCompte.getPseudo()) && c.getId() != dtoCompte.getId()) {
                    pseudoDejaPresent = true;
                    break;
                }
            }
            if (pseudoDejaPresent) {
                message.append("\nLe pseudo " + dtoCompte.getPseudo() + " est déjà utilisé.");
            }
        }

        if (dtoCompte.getMotDePasse() == null || dtoCompte.getMotDePasse().isEmpty()) {
            message.append("\nLe mot de passe est absent.");
        } else if (dtoCompte.getMotDePasse().length() < 3) {
            message.append("\nLe mot de passe est trop court.");
        } else if (dtoCompte.getMotDePasse().length() > 25) {
            message.append("\nLe mot de passe est trop long.");
        }

        if (dtoCompte.getEmail() == null || dtoCompte.getEmail().isEmpty()) {
            message.append("\nL'adresse e-mail est absente.");
        }

        if (message.length() > 0) {
            throw new ExceptionValidation(message.toString().substring(1));
        }
    }

    
    private List<DtoCompte> trierParPseudo( List<DtoCompte> liste ) {
		Collections.sort( liste,
	            (Comparator<DtoCompte>) ( item1, item2) -> {
	                return ( item1.getPseudo().toUpperCase().compareTo( item2.getPseudo().toUpperCase() ) );
			});
    	return liste;
    }

}

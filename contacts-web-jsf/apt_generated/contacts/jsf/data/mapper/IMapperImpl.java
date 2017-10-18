package contacts.jsf.data.mapper;

import contacts.commun.dto.DtoCategorie;
import contacts.commun.dto.DtoCompte;
import contacts.commun.dto.DtoPersonne;
import contacts.commun.dto.DtoTelephone;
import contacts.jsf.data.Categorie;
import contacts.jsf.data.Compte;
import contacts.jsf.data.Personne;
import contacts.jsf.data.Telephone;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-10-16T14:20:41+0200",
    comments = "version: 1.1.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_101 (Oracle Corporation)"
)
public class IMapperImpl implements IMapper {

    @Override
    public Compte map(DtoCompte source) {
        if ( source == null ) {
            return null;
        }

        Compte compte = new Compte();

        compte.setId( source.getId() );
        compte.setPseudo( source.getPseudo() );
        compte.setMotDePasse( source.getMotDePasse() );
        compte.setEmail( source.getEmail() );
        List<String> list = source.getRoles();
        if ( list != null ) {
            compte.setRoles(       new ArrayList<String>( list )
            );
        }

        return compte;
    }

    @Override
    public DtoCompte map(Compte source) {
        if ( source == null ) {
            return null;
        }

        DtoCompte dtoCompte = new DtoCompte();

        dtoCompte.setEmail( source.getEmail() );
        dtoCompte.setId( source.getId() );
        dtoCompte.setMotDePasse( source.getMotDePasse() );
        dtoCompte.setPseudo( source.getPseudo() );
        List<String> list = source.getRoles();
        if ( list != null ) {
            dtoCompte.setRoles(       new ArrayList<String>( list )
            );
        }

        return dtoCompte;
    }

    @Override
    public Compte update(Compte source, Compte target) {
        if ( source == null ) {
            return null;
        }

        target.setId( source.getId() );
        target.setPseudo( source.getPseudo() );
        target.setMotDePasse( source.getMotDePasse() );
        target.setEmail( source.getEmail() );
        if ( target.getRoles() != null ) {
            List<String> list = source.getRoles();
            if ( list != null ) {
                target.getRoles().clear();
                target.getRoles().addAll( list );
            }
            else {
                target.setRoles( null );
            }
        }
        else {
            List<String> list = source.getRoles();
            if ( list != null ) {
                target.setRoles(       new ArrayList<String>( list )
                );
            }
        }

        return target;
    }

    @Override
    public Compte duplicate(Compte source) {
        if ( source == null ) {
            return null;
        }

        Compte compte = new Compte();

        compte.setId( source.getId() );
        compte.setPseudo( source.getPseudo() );
        compte.setMotDePasse( source.getMotDePasse() );
        compte.setEmail( source.getEmail() );
        List<String> list = source.getRoles();
        if ( list != null ) {
            compte.setRoles(       new ArrayList<String>( list )
            );
        }

        return compte;
    }

    @Override
    public Categorie map(DtoCategorie source) {
        if ( source == null ) {
            return null;
        }

        Categorie categorie = new Categorie();

        categorie.setId( source.getId() );
        categorie.setLibelle( source.getLibelle() );

        return categorie;
    }

    @Override
    public DtoCategorie map(Categorie source) {
        if ( source == null ) {
            return null;
        }

        DtoCategorie dtoCategorie = new DtoCategorie();

        dtoCategorie.setId( source.getId() );
        dtoCategorie.setLibelle( source.getLibelle() );

        return dtoCategorie;
    }

    @Override
    public Categorie update(Categorie source, Categorie target) {
        if ( source == null ) {
            return null;
        }

        target.setId( source.getId() );
        target.setLibelle( source.getLibelle() );

        return target;
    }

    @Override
    public Categorie duplicate(Categorie source) {
        if ( source == null ) {
            return null;
        }

        Categorie categorie = new Categorie();

        categorie.setId( source.getId() );
        categorie.setLibelle( source.getLibelle() );

        return categorie;
    }

    @Override
    public Personne map(DtoPersonne source) {
        if ( source == null ) {
            return null;
        }

        Personne personne = new Personne();

        personne.setId( source.getId() );
        personne.setNom( source.getNom() );
        personne.setPrenom( source.getPrenom() );
        personne.setCategorie( map( source.getCategorie() ) );
        List<Telephone> list = dtoTelephoneListToTelephoneList( source.getTelephones() );
        if ( list != null ) {
            personne.setTelephones( list );
        }

        return personne;
    }

    @Override
    public DtoPersonne map(Personne source) {
        if ( source == null ) {
            return null;
        }

        DtoPersonne dtoPersonne = new DtoPersonne();

        dtoPersonne.setCategorie( map( source.getCategorie() ) );
        dtoPersonne.setId( source.getId() );
        dtoPersonne.setNom( source.getNom() );
        dtoPersonne.setPrenom( source.getPrenom() );
        List<DtoTelephone> list = telephoneListToDtoTelephoneList( source.getTelephones() );
        if ( list != null ) {
            dtoPersonne.setTelephones( list );
        }

        return dtoPersonne;
    }

    @Override
    public Personne update(Personne source, Personne target) {
        if ( source == null ) {
            return null;
        }

        target.setId( source.getId() );
        target.setNom( source.getNom() );
        target.setPrenom( source.getPrenom() );
        if ( target.getTelephones() != null ) {
            List<Telephone> list = duplicate( source.getTelephones() );
            if ( list != null ) {
                target.getTelephones().clear();
                target.getTelephones().addAll( list );
            }
            else {
                target.setTelephones( null );
            }
        }
        else {
            List<Telephone> list = duplicate( source.getTelephones() );
            if ( list != null ) {
                target.setTelephones( list );
            }
        }

        target.setCategorie( source.getCategorie() );

        return target;
    }

    @Override
    public Personne duplicate(Personne source) {
        if ( source == null ) {
            return null;
        }

        Personne personne = new Personne();

        personne.setId( source.getId() );
        personne.setNom( source.getNom() );
        personne.setPrenom( source.getPrenom() );
        personne.setCategorie( duplicate( source.getCategorie() ) );
        List<Telephone> list = duplicate( source.getTelephones() );
        if ( list != null ) {
            personne.setTelephones( list );
        }

        return personne;
    }

    @Override
    public Telephone map(DtoTelephone source) {
        if ( source == null ) {
            return null;
        }

        Telephone telephone_ = new Telephone();

        telephone_.setId( source.getId() );
        telephone_.setLibelle( source.getLibelle() );
        telephone_.setNumero( source.getNumero() );

        return telephone_;
    }

    @Override
    public DtoTelephone map(Telephone source) {
        if ( source == null ) {
            return null;
        }

        DtoTelephone dtoTelephone_ = new DtoTelephone();

        dtoTelephone_.setId( source.getId() );
        dtoTelephone_.setLibelle( source.getLibelle() );
        dtoTelephone_.setNumero( source.getNumero() );

        return dtoTelephone_;
    }

    @Override
    public Telephone duplicate(Telephone source) {
        if ( source == null ) {
            return null;
        }

        Telephone telephone = new Telephone();

        telephone.setId( source.getId() );
        telephone.setLibelle( source.getLibelle() );
        telephone.setNumero( source.getNumero() );

        return telephone;
    }

    @Override
    public List<Telephone> duplicate(List<Telephone> source) {
        if ( source == null ) {
            return null;
        }

        List<Telephone> list_____ = new ArrayList<Telephone>();
        for ( Telephone telephone : source ) {
            list_____.add( duplicate( telephone ) );
        }

        return list_____;
    }

    protected List<Telephone> dtoTelephoneListToTelephoneList(List<DtoTelephone> list) {
        if ( list == null ) {
            return null;
        }

        List<Telephone> list_ = new ArrayList<Telephone>();
        for ( DtoTelephone dtoTelephone : list ) {
            list_.add( map( dtoTelephone ) );
        }

        return list_;
    }

    protected List<DtoTelephone> telephoneListToDtoTelephoneList(List<Telephone> list) {
        if ( list == null ) {
            return null;
        }

        List<DtoTelephone> list_ = new ArrayList<DtoTelephone>();
        for ( Telephone telephone : list ) {
            list_.add( map( telephone ) );
        }

        return list_;
    }
}

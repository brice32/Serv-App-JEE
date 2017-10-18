package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import contacts.emb.dao.IDaoCategorie;
import contacts.emb.dao.IDaoPersonne;
import contacts.emb.dao.IDaoTelephone;
import contacts.emb.data.Personne;


public class DaoPersonne implements IDaoPersonne {

	
	// Champs

	private DataSource		dataSource;
	private IDaoTelephone	daoTelephone;
	private IDaoCategorie	daoCategorie;

	
	// Injecteurs
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setDaoTelephone(IDaoTelephone daoTelephone) {
		this.daoTelephone = daoTelephone;
	}
	
	public void setDaoCategorie(IDaoCategorie daoCategorie) {
		this.daoCategorie = daoCategorie;
	}

	
	// Actions
	
	@Override
	public int inserer(Personne personne)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le personne
			sql = "INSERT INTO personne ( IdCategorie, Nom, Prenom ) VALUES ( ?, ?, ? )";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setInt(	1, personne.getCategorie().getId() );
			stmt.setString(	2, personne.getNom() );
			stmt.setString(	3, personne.getPrenom() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			personne.setId( rs.getInt(1) );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}

		// Insère les telephones
		daoTelephone.insererPourPersonne( personne );
		
		// Retourne l'identifiant
		return personne.getId();
	}

	
	@Override
	public void modifier(Personne personne)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "UPDATE personne SET IdCategorie = ?, Nom = ?, Prenom = ? WHERE IdPersonne =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setInt(	1, personne.getCategorie().getId() );
			stmt.setString(	2, personne.getNom() );
			stmt.setString(	3, personne.getPrenom() );
			stmt.setInt(	4, personne.getId() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}

		// Modifie les telephones
		daoTelephone.modifierPourPersonne( personne );
	}

	
	@Override
	public void supprimer(int idPersonne)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		// Supprime les telephones
		daoTelephone.supprimerPourPersonne( idPersonne );

		try {
			cn = dataSource.getConnection();

			// Supprime le personne
			sql = "DELETE FROM personne WHERE IdPersonne = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setInt( 1, idPersonne );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

	
	@Override
	public Personne retrouver(int idPersonne)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM personne WHERE IdPersonne = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setInt(1, idPersonne);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construirePersonne(rs);
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

	
	@Override
	public List<Personne> listerTout()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM personne ORDER BY Nom, Prenom";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			List<Personne> personnes = new ArrayList<>();
			while (rs.next()) {
				personnes.add( construirePersonne(rs) );
			}
			return personnes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

    
    @Override
    public int compterourCategorie(int idCategorie) {
    	
		Connection			cn		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM personne WHERE IdCategorie = ?";
            stmt = cn.prepareStatement( sql );
            stmt.setInt( 1, idCategorie );
            rs = stmt.executeQuery();

            rs.next();
            return rs.getInt( 1 );

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
    }
	
	
	// Méthodes auxiliaires
	
	private Personne construirePersonne( ResultSet rs ) throws SQLException {
		Personne personne = new Personne();
		personne.setId(rs.getInt( "IdPersonne" ));
		personne.setCategorie( daoCategorie.retrouver( rs.getInt( "idCategorie" ) ) );
		personne.setNom(rs.getString( "Nom" ));
		personne.setPrenom(rs.getString( "Prenom" ));
		personne.setTelephones( daoTelephone.listerPourPersonne( personne ) );
		return personne;
	}
	
}

package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import contacts.emb.dao.IDaoCategorie;
import contacts.emb.data.Categorie;


public class DaoCategorie implements IDaoCategorie {

	
	// Champs

	private DataSource		dataSource;

	
	// Injecteurs
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	// Actions

	@Override
	public int inserer(Categorie categorie) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO categorie ( Libelle ) VALUES ( ? )";
			stmt = cn.prepareStatement(sql,
					new String[] { "ICategorie" } );
			stmt.setString(	1, categorie.getLibelle() );
			stmt.executeUpdate();

			rs = stmt.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			categorie.setId( id );
			return id;
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

	@Override
	public void modifier(Categorie categorie) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE categorie SET Libelle = ? WHERE IdCategorie =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setString(	1, categorie.getLibelle() );
			stmt.setInt(	2, categorie.getId() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

	@Override
	public void supprimer(int idCategorie) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM categorie WHERE IdCategorie = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setInt( 1, idCategorie );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

	@Override
	public Categorie retrouver(int idCategorie) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM categorie WHERE IdCategorie = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setInt(1, idCategorie);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireCategorie(rs);
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
	public List<Categorie> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM categorie ORDER BY Libelle";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			List<Categorie> categories = new ArrayList<>();
			while (rs.next()) {
				categories.add( construireCategorie(rs) );
			}
			return categories;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}
	
	
	// Méthodes auxiliaires
	
	private Categorie construireCategorie( ResultSet rs ) throws SQLException {
		Categorie categorie = new Categorie();
		categorie.setId(rs.getInt("IdCategorie"));
		categorie.setLibelle(rs.getString("Libelle"));
		return categorie;
	}

}

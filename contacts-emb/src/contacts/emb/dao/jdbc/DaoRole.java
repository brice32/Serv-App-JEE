package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import contacts.emb.dao.IDaoRole;
import contacts.emb.data.Compte;

public class DaoRole implements IDaoRole {

	
	// Champs

	private DataSource		dataSource;

	
	// Injecteurs
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	// Actions

	@Override
	public void insererPourCompte( Compte compte )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO role (IdCompte, Role) VALUES (?,?)";
			stmt = cn.prepareStatement( sql );
			for( String role : compte.getRoles() ) {
				stmt.setInt(	1, compte.getId() );
				stmt.setString(	2, role );
				stmt.executeUpdate();
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
	public void modifierPourCompte( Compte compte )  {

		Connection			cn			= null;
		PreparedStatement	stmtDelete	= null;
		PreparedStatement	stmtInsert	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			sql = "DELETE FROM role WHERE IdCompte = ?";
			stmtDelete = cn.prepareStatement( sql );
			stmtDelete.setInt(	1, compte.getId() );
			stmtDelete.executeUpdate();

			sql = "INSERT INTO role (IdCompte, Role) VALUES (?,?)";
			stmtInsert = cn.prepareStatement( sql );
			for( String role : compte.getRoles() ) {
				stmtInsert.setInt(	1, compte.getId() );
				stmtInsert.setString(	2, role );
				stmtInsert.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmtDelete != null) stmtDelete.close();} catch (SQLException e) {}
			try { if (stmtInsert != null) stmtInsert.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}


	@Override
	public void supprimerPourCompte(int idCompte) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime les rôles
			sql = "DELETE FROM role  WHERE IdCompte = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setInt( 1, idCompte );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}


	@Override
	public List<String> listerPourCompte(int idCompte) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM role WHERE IdCompte = ? ORDER BY Role";
			stmt = cn.prepareStatement(sql);
			stmt.setInt( 1, idCompte );
			rs = stmt.executeQuery();

			List<String> roles = new ArrayList<>();
			while (rs.next()) {
				roles.add( rs.getString("Role") );
			}
			return roles;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

}

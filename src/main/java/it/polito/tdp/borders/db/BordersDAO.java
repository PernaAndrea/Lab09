package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
			//	System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				Country c = new Country(rs.getString("StateAbb"),rs.getInt("ccode"),rs.getString("StateNme"));
				result.add(c);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno,Map<Integer,Country> idMap) {

		String sql = "SELECT  cg.state1no , cg.state2no , cg.year "
				+ "FROM contiguity cg "
				+ "WHERE cg.year <= ? AND cg.conttype=1";
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c1 = idMap.get(rs.getInt("cg.state1no"));
				Country c2 = idMap.get(rs.getInt("cg.state2no"));
				result.add(new Border(c1,c2,rs.getInt("cg.year")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	//	System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
	//	return new ArrayList<Border>();
	}
	
	public List<Country> loadVertex(int anno) {

		String sql = "SELECT  ct.ccode ,ct.StateAbb , ct.StateNme "
				+ "FROM country ct , contiguity cg "
				+ "WHERE (ct.`CCode`=cg.`state1no` OR ct.`CCode`=cg.`state2no`) AND cg.year <= ? AND cg.conttype=1 "
				+ "GROUP BY ct.`CCode`";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c = new Country(rs.getString("StateAbb"),rs.getInt("ccode"),rs.getString("StateNme"));
				result.add(c);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public void loadMap(Map<Integer,Country> idMap) {

		String sql = "SELECT * FROM country";
	
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getInt("ccode"))) {
				Country c = new Country(rs.getString("StateAbb"),rs.getInt("ccode"),rs.getString("StateNme"));
				idMap.put(rs.getInt("ccode"),c);
				}
			}
			
			conn.close();
		

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public void loadCorrispondenze(int anno,Map<Integer,Country> idMap) {

		String sql = "SELECT  cg.state1no,  COUNT(*) AS tot "
				+ "FROM contiguity cg, country c "
				+ "WHERE cg.year <= 1990 AND cg.conttype=1 AND c.ccode=cg.state1no "
				+ "GROUP BY cg.state1no";
	
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(idMap.containsKey(rs.getInt("cg.state1no"))) {
					idMap.get(rs.getInt("cg.state1no")).setnConfini(rs.getInt("tot"));
				}
			}
			
			conn.close();
		

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public void loadMap(int anno, Map<Integer, Country> idMap) {
		// TODO Auto-generated method stub
		String sql = "SELECT  DISTINCT StateAbb, ct.ccode, StateNme "
				+ "FROM contiguity cg, country ct "
				+ "WHERE cg.year <= ? AND cg.conttype=1 AND ct.ccode=cg.state1no "
				+ "GROUP BY ct.ccode";
	
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getInt("ccode"))) {
				Country c = new Country(rs.getString("StateAbb"),rs.getInt("ccode"),rs.getString("StateNme"));
				idMap.put(rs.getInt("ccode"),c);
				}
			}
			
			conn.close();
		

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}

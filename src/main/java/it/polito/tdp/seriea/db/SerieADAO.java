package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.seriea.model.Adiacenza;
import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<String> listTeams() {
		String sql = "SELECT team FROM teams";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("team"));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Adiacenza> getAdiacenze() {
		
		final String sql = "SELECT m.HomeTeam AS h, m.AwayTeam AS a, 2*COUNT(*) AS peso " + 
				"FROM matches m WHERE m.HomeTeam < m.AwayTeam " + 
				"GROUP BY m.HomeTeam, m.AwayTeam";
		List<Adiacenza> result = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.add(new Adiacenza(rs.getString("h"), rs.getString("a"), rs.getDouble("peso")));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Match> getMatches(Season stagione) {
		
		final String sql = "SELECT * " + 
				"FROM matches " + 
				"WHERE season = ?";
		List<Match> result = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, stagione.getSeason());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Match m = new Match(rs.getInt("match_id"), stagione, rs.getString("Div"), 
						rs.getDate("Date").toLocalDate(), rs.getString("HomeTeam"), 
						rs.getString("AwayTeam"), rs.getInt("FTHG"),  
						rs.getInt("FTAG"), rs.getString("FTR"));
				result.add(m);
			}
			conn.close();
			return result;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public Set<String> getTeams(Season stagione) {
		
		final String sql = "SELECT DISTINCT HomeTeam " + 
				"FROM matches " + 
				"WHERE season = ?";
		Set<String> result = new HashSet<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, stagione.getSeason());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getString("HomeTeam"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}


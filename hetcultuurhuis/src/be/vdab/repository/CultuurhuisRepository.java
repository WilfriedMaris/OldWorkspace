package be.vdab.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import be.vdab.entities.Genre;
import be.vdab.entities.Klant;
import be.vdab.entities.Reservatie;
import be.vdab.entities.Voorstelling;

public class CultuurhuisRepository extends AbstractRepository {
	private static final String FIND_ALL_GENRES = "SELECT * FROM genres ORDER BY Naam ASC";
	private static final String FIND_GENRE= "SELECT * FROM genres WHERE GenreNr = ?";
	private static final String FIND_ALL_VOORSTELLINGEN = "SELECT * FROM voorstellingen WHERE GenreNr = ? AND Datum > ? ORDER BY Datum";
	private static final String FIND_VOORSTELLING= "SELECT * FROM voorstellingen WHERE VoorstellingsNr = ?";
	private static final String FIND_KLANT="SELECT * FROM klanten WHERE GebruikersNaam = ? AND Paswoord = ?";
	private static final String FIND_GEBRUIKERSNAAM = "SELECT * FROM klanten WHERE GebruikersNaam = ?";
	private static final String MAKE_KLANT = "INSERT INTO klanten (Voornaam,Familienaam,Straat,HuisNr,Postcode,Gemeente,GebruikersNaam,Paswoord) "
			+ "Values (?,?,?,?,?,?,?,?)";
	private static final String MAKE_RESERVATIE = "INSERT INTO reservaties(KlantNr, VoorstellingsNr, Plaatsen) VALUES (?,?,?)";
	private static final String PLAATSEN_AANPASSEN = "UPDATE voorstellingen SET VrijePlaatsen = VrijePlaatsen - ? WHERE VoorstellingsNr = ?";
	
	public List<Genre> findAllGenres(){
		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(FIND_ALL_GENRES)){
			List<Genre> genres = new ArrayList<>();
			while(resultSet.next()){
				genres.add(maakGenre(resultSet));
			}
			return genres;
		}catch (SQLException ex){
			throw new RepositoryException(ex);
		}
	}
	
	private Genre maakGenre(ResultSet resultSet) throws SQLException{
		return new Genre(resultSet.getString("Naam"), resultSet.getInt("GenreNr"));
	}
	
	public Genre findGenre(String id){
		int genreNr = Integer.parseInt(id);
		try(Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_GENRE)){
			statement.setInt(1, genreNr);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return maakGenre(resultSet);
		}catch(SQLException ex){
			throw new RepositoryException(ex);
		}			
	}
	
	public List<Voorstelling> findAllVoorstellingen(String id){
		int genreNr = Integer.parseInt(id);
		Date datum = new Date();
		List<Voorstelling> voorstellingen = new ArrayList<>();
		try(Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_ALL_VOORSTELLINGEN)){
			statement.setInt(1, genreNr);
			statement.setTimestamp(2, new Timestamp(datum.getTime()));
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				voorstellingen.add(maakVoorstelling(resultSet));
			}
			return voorstellingen;
		}catch(SQLException ex){
			throw new RepositoryException(ex);
		}
	}
	
	public Voorstelling findVoorstelling(int voorstellingsNr){
		try(Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_VOORSTELLING)){
			statement.setInt(1, voorstellingsNr);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return maakVoorstelling(resultSet);
		}catch(SQLException ex){
			throw new RepositoryException(ex);
		}			
	}
	
	private Voorstelling maakVoorstelling(ResultSet resultSet) throws SQLException{
		return new Voorstelling(resultSet.getInt("VoorstellingsNr"),
				resultSet.getString("Titel"), resultSet.getString("Uitvoerders"),
				resultSet.getTimestamp("Datum"), resultSet.getInt("GenreNr"),
				resultSet.getBigDecimal("prijs"), resultSet.getInt("VrijePlaatsen"));
	}
	
	public Optional<Klant> findKlant(String gebruikersNaam, String paswoord){
		try(Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_KLANT)){
			statement.setString(1, gebruikersNaam);
			statement.setString(2, paswoord);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				return Optional.of(maakKlant(resultSet));
			}else{
				return Optional.empty();
			}
		}catch(SQLException ex){
			throw new RepositoryException(ex);
		}
	}
	
	private Klant maakKlant(ResultSet resultSet) throws SQLException{
		return new Klant(resultSet.getInt("KlantNr"),
				resultSet.getString("Voornaam"), resultSet.getString("Familienaam"),
				resultSet.getString("Straat"), resultSet.getString("HuisNr"),
				resultSet.getString("Postcode"), resultSet.getString("Gemeente"),
				resultSet.getString("GebruikersNaam"), resultSet.getString("Paswoord"));
	}
	
	public boolean existGebruikersnaam(String gebruikersnaam){
		try(Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_GEBRUIKERSNAAM)){
			statement.setString(1, gebruikersnaam);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				return true;
			}else{
				return false;
			}
		}catch(SQLException ex){
			throw new RepositoryException(ex);
		}
	}
	
	public void makeKlant(Map<String,String> input){
		try(Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(MAKE_KLANT)){
			int teller = 1;
			input.remove("Herhaal paswoord");
				for(String entry : input.values()){
					statement.setString(teller, entry);
					teller++;
				}
			statement.executeUpdate();
		}catch(SQLException ex){
			throw new RepositoryException(ex);
		}
	}
	
	public void addReservatie(Reservatie reservatie){
		try(Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(PLAATSEN_AANPASSEN);
				PreparedStatement statement2 = connection.prepareStatement(MAKE_RESERVATIE)){
			statement.setInt(1, reservatie.getPlaatsen());
			statement.setInt(2, reservatie.getVoorstellingsNr());
			statement2.setInt(1, reservatie.getKlantNr());
			statement2.setInt(2, reservatie.getVoorstellingsNr());
			statement2.setInt(3, reservatie.getPlaatsen());
			connection.setAutoCommit(false);
			statement.executeUpdate();
			statement2.executeUpdate();
			connection.commit();;
		}catch(SQLException ex){
			throw new RepositoryException(ex);
		}
	}
}

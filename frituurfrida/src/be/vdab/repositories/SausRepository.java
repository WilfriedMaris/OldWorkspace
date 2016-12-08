
package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import be.vdab.entities.Saus;

public class SausRepository extends AbstractRepository {
	private static final String FIND_ALL = "select sauzen.id, sauzen.naam as sausnaam,"
			+ " ingredienten.naam as ingredientnaam" + " from sauzen left join sauzeningredienten"
			+ " on sauzen.id=sauzeningredienten.sausid" + " left join ingredienten "
			+ " on sauzeningredienten.ingredientid=ingredienten.id" + " order by sauzen.naam";
	private static final String FIND_BY_INGREDIENT = "select sauzen.id, sauzen.naam as sausnaam"
			+ " from sauzen inner join sauzeningredienten " + " on sauzen.id=sauzeningredienten.sausid"
			+ " inner join ingredienten" + " on sauzeningredienten.ingredientid=ingredienten.id"
			+ " where ingredienten.naam = ?" + " order by sauzen.naam";
	private static final String DELETE = "delete from sauzen where id in (";

	public List<Saus> findAll() {
		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
			List<Saus> sauzen = new ArrayList<>();
			for (long vorigeId = 0; resultSet.next();) {
				long id = resultSet.getLong("id");
				if (id != vorigeId) {
					sauzen.add(resultSetRijNaarSausZonderIngredienten(resultSet));
					vorigeId = id;
				}
				sauzen.get(sauzen.size() - 1).addIngredient(resultSet.getString("ingredientnaam"));
			}
			return sauzen;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}

	public List<Saus> findByIngredient(String ingredient) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_BY_INGREDIENT)) {
			statement.setString(1, ingredient);
			List<Saus> sauzen = new ArrayList<>();
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					sauzen.add(resultSetRijNaarSausZonderIngredienten(resultSet));
				}
				return sauzen;
			}
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}

	private Saus resultSetRijNaarSausZonderIngredienten(ResultSet resultSet) throws SQLException {
		return new Saus(resultSet.getLong("id"), resultSet.getString("sausnaam"), new ArrayList<String>());
	}

	public void delete(Set<Long> ids) {
		StringBuilder sql = new StringBuilder(DELETE);
		ids.stream().forEach(id -> sql.append("?,"));
		sql.deleteCharAt(sql.length() - 1);
		sql.append(')');
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql.toString())) {
			int index = 1;
			for (long id : ids) {
				statement.setLong(index++, id);
			}
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
}
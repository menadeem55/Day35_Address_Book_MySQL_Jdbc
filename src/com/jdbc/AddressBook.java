package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBook {
	Connection connection;

	private static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Drivers loaded!!");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?", "root",
					"rootroot");
			System.out.println("connection Established!!");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}


	public List<Contacts> retrieveData() {
		ResultSet resultSet = null;
		List<Contacts> addressBookList = new ArrayList<Contacts>();
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from addressbook_table;");
			int count = 0;
			while (resultSet.next()) {
				Contacts contactInfo = new Contacts();
				contactInfo.setFirstName(resultSet.getString("firstName"));
				contactInfo.setLastName(resultSet.getString("lastname"));
				contactInfo.setAddress(resultSet.getString("address"));
				contactInfo.setCity(resultSet.getString("city"));
				contactInfo.setState(resultSet.getString("state"));
				contactInfo.setZip(resultSet.getInt("zip"));
				contactInfo.setPhoneNumber(resultSet.getString("phoneNumber"));
				contactInfo.setEmailId(resultSet.getString("email"));
				contactInfo.setDateAdded(resultSet.getDate("dateadded").toLocalDate());
				contactInfo.setBookName(resultSet.getString("bookNamed"));

				addressBookList.add(contactInfo);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return addressBookList;

	}

	public void updateCityByZip(String address, String city, String state, int zip, int id) {
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			String query = "Update addressbook_table set address=" + "'" + address + "'" + ", " + "city=" + "'" + city
					+ "'" + ", " + "state=" + "'" + state + "'" + ", " + "zipcode=" + zip + " where id=" + id + ";";
			int result = statement.executeUpdate(query);
			System.out.println(result);
			if (result > 0) {
				System.out.println("Address Updated Successfully");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Contacts> findAllForParticularDate(LocalDate date) {
		ResultSet resultSet = null;
		List<Contacts> addressBookList = new ArrayList<Contacts>();
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			String sql = "select * from addressbook_table where dateadded between cast(' " + date + "'"
					+ " as date)  and date(now());";
			resultSet = statement.executeQuery(sql);
			int count = 0;
			while (resultSet.next()) {
				Contacts contactInfo = new Contacts();
				contactInfo.setFirstName(resultSet.getString("firstName"));
				contactInfo.setLastName(resultSet.getString("lastname"));
				contactInfo.setAddress(resultSet.getString("address"));
				contactInfo.setCity(resultSet.getString("city"));
				contactInfo.setState(resultSet.getString("state"));
				contactInfo.setZip(resultSet.getInt("zip"));
				contactInfo.setPhoneNumber(resultSet.getString("phoneNumber"));
				contactInfo.setEmailId(resultSet.getString("email"));
				contactInfo.setBookName(resultSet.getString("booknamed"));
				contactInfo.setDateAdded(resultSet.getDate("dateadded").toLocalDate());

				addressBookList.add(contactInfo);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return addressBookList;
	}

	public int countByCity(String city) {
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			String sql = "select count(firstname) from addressbook_table where city=" + "'" + city + "';";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			int count = result.getInt(1);

			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int countByState(String state) {
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			String sql = "select count(firstname) from addressbook_table where city=" + "'" + state + "';";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			int count = result.getInt(1);
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
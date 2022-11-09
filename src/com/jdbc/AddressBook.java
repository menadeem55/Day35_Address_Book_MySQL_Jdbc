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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_service?", "root",
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
			resultSet = statement.executeQuery("select * from address_book_db;");
			int count = 0;
			while (resultSet.next()) {
				Contacts contactInfo = new Contacts();
				contactInfo.setFirstName(resultSet.getString("firstName"));
				contactInfo.setLastName(resultSet.getString("lastname"));
				contactInfo.setAddress(resultSet.getString("address"));
				contactInfo.setCity(resultSet.getString("city"));
				contactInfo.setState(resultSet.getString("state"));
				contactInfo.setZip(resultSet.getInt("zipcode"));
				contactInfo.setPhoneNumber(resultSet.getString("mobilenum"));
				contactInfo.setEmailId(resultSet.getString("email"));
				contactInfo.setDateAdded(resultSet.getDate("dateadded").toLocalDate());
				contactInfo.setBookName(resultSet.getString("bookName"));

				addressBookList.add(contactInfo);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return addressBookList;

	}

	public static void insertData(Contacts add) throws SQLException {
		Connection connection = getConnection();
		try {
			if (connection != null) {
				connection.setAutoCommit(false);
				Statement statement = connection.createStatement();
				String sql = "insert into address_book_db(firstname,lastname,address,city,state,zipcode,mobilenum,email,bookname,dateadded)"
						+ "values('" + add.getFirstName() + "','" + add.getLastName() + "','" + add.getAddress() + "','"
						+ add.getCity() + "','" + add.getState() + "','" + add.getZip() + "','" + add.getPhoneNumber()
						+ "','" + add.getEmailId() + "','" + add.getBookName() + "','" + add.getDateAdded() + "');";
				int result = statement.executeUpdate(sql);
				connection.commit();
				if (result > 0) {
					System.out.println("Contact Inserted");
				}
				connection.setAutoCommit(true);
			}
		} catch (SQLException sqlException) {
			System.out.println("Insertion Rollbacked");
			connection.rollback();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public void updateCityByZip(String address, String city, String state, int zip, int id) {
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			String query = "Update address_book_db set address=" + "'" + address + "'" + ", " + "city=" + "'" + city
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
			String sql = "select * from address_book_db where dateadded between cast(' " + date + "'"
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
				contactInfo.setZip(resultSet.getInt("zipcode"));
				contactInfo.setPhoneNumber(resultSet.getString("mobilenum"));
				contactInfo.setEmailId(resultSet.getString("email"));
				contactInfo.setBookName(resultSet.getString("bookname"));
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
			String sql = "select count(firstname) from address_book_db where city=" + "'" + city + "';";
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
			String sql = "select count(firstname) from address_book_db where city=" + "'" + state + "';";
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
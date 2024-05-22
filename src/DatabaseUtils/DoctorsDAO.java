package DatabaseUtils;

import Main.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorsDAO {

    public ObservableList<Doctor> getAllDocteurs() throws SQLException {
        ObservableList<Doctor> doctors = FXCollections.observableArrayList();
        String query = "SELECT * FROM doctor";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id =  resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                String phone_number = resultSet.getString("phone_number");
                doctors.add(new Doctor(id, name,specialization,phone_number));

            }
        }
        return doctors;
    }


    public void saveDocteurToDatabase(String name, String specialization, String phone_numbe) {
        String query = "INSERT INTO doctor (name, specialization, phone_number) VALUES (?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, specialization);
            statement.setString(3, phone_numbe);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Doctor getDocteurById(int docId) throws SQLException {
        String query = "SELECT * FROM doctor WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, docId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id =  resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String specialization = resultSet.getString("specialization");
                    String phone_number = resultSet.getString("phone_number");

                    return new Doctor(id, name,specialization,phone_number);
                } else {
                    return null;
                }
            }
        }
    }

}

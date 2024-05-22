package DatabaseUtils;

import Main.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import models.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RDVsDAO {

    public ObservableList<Appointment> getAllRDVs() throws SQLException {
        ObservableList<Appointment> rdvs = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointment ORDER BY date_time DESC";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id_rdv =  resultSet.getInt("id");
                    String date_time = resultSet.getString("date_time");
                    int id_patient = resultSet.getInt("patient_id");
                    int id_doctor = resultSet.getInt("doctor_id");
                    String state = resultSet.getString("state");
                    String time = resultSet.getString("time");

                    rdvs.add(new Appointment(id_rdv,date_time,state,id_patient,id_doctor, time));
                }
            }
        }

        return rdvs;
    }

    public void saveRDVToDatabase(String date_time, int patient_id, int doctor_id, String state, String time) {
        String query = "INSERT INTO appointment (date_time, patient_id, doctor_id, state, time) VALUES (?, ?, ?, ?, ?)";

//        System.out.println(date_time+" "+ patient_id+" "+ doctor_id+" "+ state+" "+ time);
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, date_time);
            statement.setInt(2, patient_id);
            statement.setInt(3, doctor_id);
            statement.setString(4, state);
            statement.setString(5, time);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }


    public ObservableList<Appointment> getRDVByPatientId(int patientId) throws SQLException {

        ObservableList<Appointment> rdvs = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointment WHERE patient_id = ? ORDER BY date_time DESC";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id_rdv =  resultSet.getInt("id");
                    String date_time = resultSet.getString("date_time");
                    int id_patient = resultSet.getInt("patient_id");
                    int id_doctor = resultSet.getInt("doctor_id");
                    String state = resultSet.getString("state");
                    String time = resultSet.getString("time");

                    rdvs.add(new Appointment(id_rdv,date_time,state,id_patient,id_doctor, time));
                }
            }
        }
        return rdvs;
    }

    public ObservableList<Appointment> getRDVByDocteurId(int docteurId) throws SQLException {

        ObservableList<Appointment> rdvs = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointment WHERE doctor_id = ? ORDER BY date_time DESC";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, docteurId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id_rdv =  resultSet.getInt("id");
                    String date_time = resultSet.getString("date_time");
                    int id_patient = resultSet.getInt("patient_id");
                    int id_doctor = resultSet.getInt("doctor_id");
                    String state = resultSet.getString("state");
                    String time = resultSet.getString("time");

                    rdvs.add(new Appointment(id_rdv,date_time,state,id_patient,id_doctor, time));
                }
            }
        }
        return rdvs;
    }



}

package DatabaseUtils;

import Main.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;
import models.Consultation;
import models.Patient;
import models.Prescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDAO {


    public ObservableList<Patient> getAllPatients() throws SQLException {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        String query = "SELECT * FROM patient";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id =  resultSet.getInt("id");
                String name = resultSet.getString("name");
                String dob = resultSet.getString("date_of_birth");
                String gender = resultSet.getString("gender");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phone_number");
                patients.add(new Patient(id, name, dob, gender, address, phoneNumber));
            }
        }
        return patients;
    }

    public Patient getPatientById(int patientId) throws SQLException {
        String query = "SELECT * FROM patient WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String dob = resultSet.getString("date_of_birth");
                    String gender = resultSet.getString("gender");
                    String address = resultSet.getString("address");
                    String phoneNumber = resultSet.getString("phone_number");
                    return new Patient(id, name, dob, gender, address, phoneNumber);
                } else {
                    return null;
                }
            }
        }
    }

    public void deletePatient(int patientId) throws SQLException {
        String query = "DELETE FROM Patient WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patientId);
            statement.executeUpdate();
        }
    }

    public void savePatientToDatabase(String name, String dateOfBirth, String gender, String phoneNumber, String adresse) {
        String query = "INSERT INTO patient (name, date_of_birth, gender, phone_number, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, dateOfBirth);
            statement.setString(3, gender);
            statement.setString(4, phoneNumber);
            statement.setString(5, adresse);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public void updatePatientToDatabase(String name, String dateOfBirth, String gender, String phoneNumber, String adresse, int id) {
        String query = "UPDATE Patient SET name = ?, date_of_birth = ?, gender = ?, phone_number = ?, address = ?  WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, dateOfBirth);
            statement.setString(3, gender);
            statement.setString(4, phoneNumber);
            statement.setString(5, adresse);
            statement.setInt(6, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAppointment(int appointment_id){
        String query = "UPDATE appointment SET state = ?  where id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "fait");
            statement.setInt(2, appointment_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Appointment> getAllPatientsRDV(int id) throws SQLException {
        ObservableList<Appointment> rdvs = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointment where patient_id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
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


    public ObservableList<Consultation> getAllPatientsConsulations(int id) throws SQLException {
        ObservableList<Consultation> rdvs = FXCollections.observableArrayList();
        String query = "SELECT * FROM consultation where patient_id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id_cons =  resultSet.getInt("id");
                    int doctor_id = resultSet.getInt("doctor_id");
                    int id_patient = resultSet.getInt("patient_id");
                    String cons_date = resultSet.getString("date_time");
                    String notes = resultSet.getString("notes");

                    rdvs.add(new Consultation(id_cons,doctor_id,id_patient, cons_date,notes));
                }
            }
        }

        return rdvs;
    }

    public void saveRDVToDatabase(String date_time, int patient_id, int doctor_id, String state, String time) {
        String query = "INSERT INTO appointment (date_time, patient_id, doctor_id, state, time) VALUES (?, ?, ?, ?, ?)";

        System.out.println(date_time+" "+ patient_id+" "+ doctor_id+" "+ state+" "+ time);
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
        }
    }

    public void saveConsultationToDatabase(int doctor_id, int patient_id, String date_time, String notes) {
        String query = "INSERT INTO consultation (doctor_id,patient_id,date_time,notes) VALUES (?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, doctor_id);
            statement.setInt(2, patient_id);
            statement.setString(3, date_time);
            statement.setString(4, notes);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMedicamentToDatabase(int doctor_id, int patient_id, String medication,String dosage,String duration){
        String query = "SELECT * FROM consultation WHERE doctor_id = ? and patient_id = ? order by id DESC limit 1";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, doctor_id);
            statement.setInt(2, patient_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int consultation_id = resultSet.getInt("id");

                    query = "INSERT INTO prescription (consultation_id,medication,dosage,duration) VALUES (?, ?, ?, ?)";

                    try (Connection connection2 = DbConnection.getConnection();
                         PreparedStatement statement2 = connection2.prepareStatement(query)) {
                        statement2.setInt(1, consultation_id);
                        statement2.setString(2, medication);
                        statement2.setString(3, dosage);
                        statement2.setString(4, duration);
                        statement2.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Prescription> getAllMedicamentPerscription(int doctor_id, int patient_id) throws SQLException {
        ObservableList<Prescription> prcs = FXCollections.observableArrayList();
        String query = "SELECT * FROM consultation WHERE doctor_id = ? and patient_id = ? order by id DESC limit 1";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, doctor_id);
            statement.setInt(2, patient_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int consultation_id = resultSet.getInt("id");
                    System.out.println(consultation_id);


                    query = "SELECT * FROM prescription where consultation_id = ?";
                    try (Connection connection2 = DbConnection.getConnection();
                         PreparedStatement statement2 = connection2.prepareStatement(query)) {
                        statement2.setInt(1, consultation_id);

                        try (ResultSet resultSet2 = statement2.executeQuery()) {
                            while (resultSet2.next()) {
                                int id = resultSet2.getInt("id");
                                String medication =  resultSet2.getString("medication");
                                String dosage = resultSet2.getString("dosage");
                                String duration = resultSet2.getString("duration");
                                prcs.add(new Prescription(id, consultation_id, medication, dosage, duration));
                            }
                        }
                    }
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return prcs;
    }


    public ObservableList<Prescription> getAllPatientsPrescription(int consultation_id) throws SQLException {
        ObservableList<Prescription> prcs = FXCollections.observableArrayList();
        String query = "SELECT * FROM prescription where consultation_id = ?";
                    try (Connection connection2 = DbConnection.getConnection();
                         PreparedStatement statement2 = connection2.prepareStatement(query)) {
                        statement2.setInt(1, consultation_id);

                        try (ResultSet resultSet2 = statement2.executeQuery()) {
                            while (resultSet2.next()) {
                                int id = resultSet2.getInt("id");
                                String medication =  resultSet2.getString("medication");
                                String dosage = resultSet2.getString("dosage");
                                String duration = resultSet2.getString("duration");
                                prcs.add(new Prescription(id, consultation_id, medication, dosage, duration));
                            }
                        }
                    }


        catch (Exception e) {
            e.printStackTrace();
        }
        return prcs;
    }
}

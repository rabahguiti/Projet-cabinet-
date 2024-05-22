package Controllers.Patients;

import DatabaseUtils.PatientDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRDVFromPatientProfile implements Initializable {
    @FXML
    private DatePicker input_date;

    @FXML
    private TextField input_heure;

    @FXML
    private TextField input_doctor;
    PatientDAO patientDAO;
    private int patient_id ;


    private PatientProfilFrame patientProfilFrame;
    public void setPatientProfileFrame(PatientProfilFrame patientProfilFrame) {
        this.patientProfilFrame = patientProfilFrame;
    }
    public void setPatient_id(int patient_id){
        this.patient_id = patient_id;
    }


    @FXML
    private void handleSubmitButton() {
        String heure = input_heure.getText();
        String date = input_date.getValue().toString();
        int doctor = Integer.parseInt(input_doctor.getText());



        patientDAO.saveRDVToDatabase(date,patient_id, doctor, "A venir", heure);

        patientProfilFrame.refreshTableData();


        Stage stage = (Stage) input_date.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientDAO = new PatientDAO();
        // later , init here the combobox of doctors
    }
}

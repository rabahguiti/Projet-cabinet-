package Controllers.Patients;

import DatabaseUtils.PatientDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Prescription;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddConsultationForm implements Initializable {
    private int id;
    private int doctor_id;
    private PatientProfilFrame patientProfilFrame;

    @FXML
    private TextField input_doctor_id;

    @FXML
    private TextArea input_notes;

    @FXML
    private DatePicker input_date;

    @FXML
    private TextField input_medicament;

    @FXML
    private TextField input_dosage;

    @FXML
    private TextField input_period;

    @FXML
    private TableView<Prescription> table_medicaments;

    @FXML
    private TableColumn<Prescription, String> col_medicament;

    @FXML
    private TableColumn<Prescription, String> col_dosage;

    @FXML
    private TableColumn<Prescription, String> col_period;

    @FXML
    private Button cons_valider_btn;

    PatientDAO patientDAO;



    public void setPatientProfileFrame(PatientProfilFrame patientProfilFrame) {
        this.patientProfilFrame = patientProfilFrame;
    }

    public void setPatient_id(int id) {
        this.id = id;
    }

    public void setDoctor_id(int id) {
        this.doctor_id = id;
    }




    @FXML
    private void handleSubmitButton() {
        int doctor_id = Integer.parseInt(input_doctor_id.getText());
        setDoctor_id(doctor_id);
        String date = input_date.getValue().toString();
        String notes = input_notes.getText();
        input_doctor_id.setDisable(true);
        input_date.setDisable(true);
        input_notes.setDisable(true);
        cons_valider_btn.setDisable(true);

//        System.out.println(doctor_id +" "+ id+" "+ date+" "+ notes);
        patientDAO.saveConsultationToDatabase(doctor_id,id, date, notes);
        patientProfilFrame.refreshTableData();
        showAlert(Alert.AlertType.INFORMATION, "Consultation", "consultation créée avec succès");

    }

    @FXML
    private void handleAddMedicinButton() {
        String medicament = input_medicament.getText();
        String dosage = input_dosage.getText();
        String period = input_period.getText();
        if(medicament.isEmpty() || dosage.isEmpty() || period.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Médicament", "Vous devez remplir tous les champs du formulaire");
        }else{
            input_medicament.setText("");
            input_dosage.setText("");
            input_period.setText("");

            patientDAO.saveMedicamentToDatabase(doctor_id,id, medicament, dosage,period);
            refreshTableData(doctor_id,id);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void refreshTableData(int doctor_id, int patient_id) {
        try {
            table_medicaments.setItems(patientDAO.getAllMedicamentPerscription(doctor_id, patient_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientDAO = new PatientDAO();

        col_medicament.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedications()));
        col_dosage.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDosage()));
        col_period.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuration()));
    }
}

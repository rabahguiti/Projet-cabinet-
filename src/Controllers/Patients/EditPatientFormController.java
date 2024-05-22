package Controllers.Patients;

import DatabaseUtils.PatientDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Patient;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditPatientFormController implements Initializable {

    private PatientsFrame patientsFrame;
    private Patient patient;
    public void setPatientsFrame(PatientsFrame patientsFrame) {
        this.patientsFrame = patientsFrame;
    }
    public void setPatient(Patient patient){
        this.patient = patient;
    }

    @FXML
    private TextField inputName;

    @FXML
    private DatePicker inputDate;

    @FXML
    private ComboBox<String> inputSexe;

    @FXML
    private TextField inputNumber;

    @FXML
    private TextField inputAdresse;

    @FXML
    private Button bttnSubmit;

    PatientDAO patientDAO;
    int id = 0;


    @FXML
    private void handleSubmitButton() {
        // Get values from input fields
        String name = inputName.getText();
        String dateOfBirth = inputDate.getValue().toString();
        String gender = inputSexe.getValue();
        String phoneNumber = inputNumber.getText();
        String addresse = inputAdresse.getText();
//        System.out.println(name+ dateOfBirth+ gender+ phoneNumber+ addresse+" "+ id);

        patientDAO.updatePatientToDatabase(name, dateOfBirth, gender, phoneNumber, addresse, id);




        Stage stage = (Stage) bttnSubmit.getScene().getWindow();
        stage.close();
    }

    public void initializeFields() {
        if (patient != null) {
            inputName.setText(patient.getName());
            inputDate.setValue(LocalDate.parse(patient.getDateOfBirth()));
            inputSexe.setValue(patient.getGender());
            inputNumber.setText(patient.getPhoneNumber());
            inputAdresse.setText(patient.getAddress());
            id = patient.getId();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientDAO = new PatientDAO();
        inputSexe.setItems(FXCollections.observableArrayList("Male", "Female"));
    }
}

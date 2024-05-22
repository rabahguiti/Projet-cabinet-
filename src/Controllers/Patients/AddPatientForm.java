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

import java.net.URL;
import java.util.ResourceBundle;

public class AddPatientForm implements Initializable {

    private PatientsFrame patientsFrame;
    public void setPatientsFrame(PatientsFrame patientsFrame) {
        this.patientsFrame = patientsFrame;
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


    @FXML
    private void handleSubmitButton() {
        // Get values from input fields
        String name = inputName.getText();
        String dateOfBirth = inputDate.getValue().toString();
        String gender = inputSexe.getValue();
        String phoneNumber = inputNumber.getText();
        String addresse = inputAdresse.getText();


        patientDAO.savePatientToDatabase(name, dateOfBirth, gender, phoneNumber, addresse);

        patientsFrame.refreshTableData();


        Stage stage = (Stage) bttnSubmit.getScene().getWindow();
        stage.close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientDAO = new PatientDAO();
        inputSexe.setItems(FXCollections.observableArrayList("Male", "Female"));
    }


}

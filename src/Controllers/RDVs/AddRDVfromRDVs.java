package Controllers.RDVs;

import DatabaseUtils.RDVsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRDVfromRDVs implements Initializable {

    @FXML
    private DatePicker input_date;

    @FXML
    private TextField input_heure;

    @FXML
    private TextField input_doctor;

    @FXML
    private TextField input_patient;

    RDVFrame rdVsFrame;
    RDVsDAO rdVsDAO;

    @FXML
    void handleSubmitButton(ActionEvent event) {
        String heure = input_heure.getText();
        String date = input_date.getValue().toString();
        int doctor = Integer.parseInt(input_doctor.getText());
        int patient = Integer.parseInt(input_patient.getText());



        rdVsDAO.saveRDVToDatabase(date,patient, doctor, "A venir", heure);

        rdVsFrame.refreshTableData();


        Stage stage = (Stage) input_date.getScene().getWindow();
        stage.close();
    }

    public void setRDVsFrame(RDVFrame rdVsFrame){
        this.rdVsFrame = rdVsFrame;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rdVsDAO = new RDVsDAO();
    }
}

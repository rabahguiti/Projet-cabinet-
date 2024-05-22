package Controllers.Patients;

import DatabaseUtils.PatientDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import models.Consultation;
import models.Prescription;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ConsultationDetails implements Initializable {
    PatientDAO patientDAO;
    private Consultation consultation;
    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }


    @FXML
    private TextField input_doctor_id;

    @FXML
    private TextArea input_notes;

    @FXML
    private DatePicker input_date;

    @FXML
    private Button printButton;

    @FXML
    private TableView<Prescription> table_medicaments;

    @FXML
    private TableColumn<Prescription, String> col_medicament;

    @FXML
    private TableColumn<Prescription, String> col_dosage;

    @FXML
    private TableColumn<Prescription, String> col_period;

    public void initializeFields() {
        patientDAO = new PatientDAO();
        if (consultation != null) {
            input_doctor_id.setText(String.valueOf(consultation.getDoctorId()));
            input_doctor_id.setEditable(false);
            input_date.setValue(LocalDate.parse(consultation.getDateTime()));
            input_date.setEditable(false);
            input_notes.setText(consultation.getNotes());
            input_notes.setEditable(false);
            col_medicament.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedications()));
            col_dosage.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDosage()));
            col_period.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuration()));
            try {
                table_medicaments.setItems(patientDAO.getAllPatientsPrescription(consultation.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void handlePrintButton(ActionEvent event) {
        Scene scene = printButton.getScene();

        Node node = scene.getRoot();

        // Create a printer job
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null && printerJob.showPrintDialog(scene.getWindow())) {
            if (printerJob.printPage(node)) {
                printerJob.endJob();
            } else {
                System.out.println("Failed to print");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientDAO = new PatientDAO();

    }
}

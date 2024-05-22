package Controllers.RDVs;

import Controllers.Patients.AddPatientForm;
import DatabaseUtils.RDVsDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Appointment;
import models.Patient;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RDVFrame implements Initializable {

    @FXML
    private TableView<Appointment> rdvsTable;

    @FXML
    private TableColumn<Appointment, Integer> col_id;

    @FXML
    private TableColumn<Appointment, String> col_date;

    @FXML
    private TableColumn<Appointment, String> col_heure;

    @FXML
    private TableColumn<Appointment, Integer> col_patient;

    @FXML
    private TableColumn<Appointment, Integer> col_docotor;

    @FXML
    private TableColumn<Appointment, String> col_state;

    @FXML
    private TextField search_bar;



    RDVsDAO rdVsDAO;

    public void refreshTableData() {
        try {
            rdvsTable.setItems(rdVsDAO.getAllRDVs());
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void handleAddRDVButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../Views/RDVs/addRDVfromRDVs.fxml"));
            Parent root = fxmlLoader.load();
            Stage addRDVStage = new Stage();
            addRDVStage.initModality(Modality.APPLICATION_MODAL);
            addRDVStage.setTitle("Ajouter un nouveau rendez-vous");
            addRDVStage.setScene(new Scene(root));
            AddRDVfromRDVs addRDVfromRDVs = fxmlLoader.getController();
            addRDVfromRDVs.setRDVsFrame(this);
            addRDVStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    @FXML
    private void handleSearchButton() {
        String searchText = search_bar.getText().trim();
        if (!searchText.isEmpty()) {
            try {
                int patientId = Integer.parseInt(searchText);
                ObservableList<Appointment> appointment = rdVsDAO.getRDVByPatientId(patientId);
                if (appointment.size() > 0) {
                    rdvsTable.getItems().clear();
                    rdvsTable.setItems(appointment);
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "Résultat de la recherche", "Aucun patient trouvé avec le ID :" + patientId);
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Entrée invalide", "Veuillez saisir un identifiant valide.");
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

            try {
                rdvsTable.setItems(rdVsDAO.getAllRDVs());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rdVsDAO = new RDVsDAO();

        col_id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        col_date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTime()));
        col_heure.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));
        col_patient.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPatientId()).asObject());
        col_docotor.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDoctorId()).asObject());
        col_state.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getState()));
        try {
            rdvsTable.setItems(rdVsDAO.getAllRDVs());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

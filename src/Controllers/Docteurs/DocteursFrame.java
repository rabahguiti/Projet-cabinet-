package Controllers.Docteurs;

import Controllers.Patients.AddPatientForm;
import DatabaseUtils.DoctorsDAO;
import DatabaseUtils.RDVsDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Appointment;
import models.Doctor;
import models.Patient;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DocteursFrame implements Initializable {

    @FXML
    private TextField search_bar;

    @FXML
    private Button chercherButton;

    @FXML
    private Button ajouterPatienButton;

    @FXML
    private TableView<Doctor> docteurs_table;

    @FXML
    private TableColumn<Doctor, Integer> col_id_docteur;

    @FXML
    private TableColumn<Doctor, String> col_nom;

    @FXML
    private TableColumn<Doctor, String> col_numero;

    @FXML
    private TableColumn<Doctor, String> col_specialization;

    @FXML
    private TableView<Appointment> rdvs_table;

    @FXML
    private TableColumn<Appointment, Integer> col_id_rdv;

    @FXML
    private TableColumn<Appointment, String> col_date;

    @FXML
    private TableColumn<Appointment, String> col_heur;

    @FXML
    private TableColumn<Appointment, String> col_etat;

    RDVsDAO rdVsDAO;
    DoctorsDAO doctorsDAO;




    public void handleAddDocteurButton(ActionEvent actionEvent) {
    }



    public void handleSearchOnChange(InputMethodEvent inputMethodEvent) {
    }

    public void handleAddDoctorButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../Views/Doctors/AddDocteur.fxml"));
            Parent root = fxmlLoader.load();
            Stage addPatientStage = new Stage();
            addPatientStage.initModality(Modality.APPLICATION_MODAL);
            addPatientStage.setTitle("Ajouter un nouveau docteur");
            addPatientStage.setScene(new Scene(root));
            AddDocteur addDocteur = fxmlLoader.getController();
            addDocteur.setDoctorsFrame(this);
            addPatientStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void handleSearchButton() {
        String searchText = search_bar.getText().trim();
        if (!searchText.isEmpty()) {
            try {
                int docId = Integer.parseInt(searchText);
                Doctor patient = doctorsDAO.getDocteurById(docId);
                if (patient != null) {
                    docteurs_table.getItems().clear();
                    docteurs_table.getItems().add(patient);
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "Résultat de la recherche", "Aucun docteur trouvé avec le ID :" + docId);
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Entrée invalide", "Veuillez saisir un identifiant valide.");
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

            try {
                docteurs_table.setItems(doctorsDAO.getAllDocteurs());
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exception
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

    public void refreshTableData (){
        try {
            docteurs_table.setItems(doctorsDAO.getAllDocteurs());
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    @FXML
    private void handleSearchOnChange() {
        String searchText = search_bar.getText().trim();
        if (searchText.isEmpty()) {
            try {
                docteurs_table.setItems(doctorsDAO.getAllDocteurs());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rdVsDAO = new RDVsDAO();
        doctorsDAO = new DoctorsDAO();

        col_id_docteur.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        col_nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        col_numero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        col_specialization.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecialization()));

        col_id_rdv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        col_date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTime()));
        col_heur.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));
        col_etat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getState()));

        try {
            docteurs_table.setItems(doctorsDAO.getAllDocteurs());

            docteurs_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {

                    Doctor selectedDoctor = newSelection;

                    try {
                        ObservableList<Appointment> rdvs = rdVsDAO.getRDVByDocteurId(selectedDoctor.getId());

                        rdvs_table.setItems(rdvs);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

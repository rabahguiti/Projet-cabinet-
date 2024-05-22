package Controllers.Patients;

import Controllers.Patients.AddPatientForm;
import Controllers.Patients.EditPatientFormController;
import Controllers.Patients.PatientProfilFrame;
import DatabaseUtils.PatientDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Patient;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientsFrame implements Initializable {

    @FXML
    private StackPane patients_root_stack;


    @FXML
    private TableView<Patient> patientsTable;

    @FXML
    private TableColumn<Patient, Integer> idCol;

    @FXML
    private TableColumn<Patient, String> nameCol;

    @FXML
    private TableColumn<Patient, String> dobCol;

    @FXML
    private TableColumn<Patient, String> genderCol;

    @FXML
    private TableColumn<Patient, String> numberCol;

    @FXML
    private TableColumn<Patient, String> adresseCol;

    @FXML
    private TableColumn<Patient, Void> actionCol;

    @FXML
    private Button ajouterPatienButton;

    @FXML
    private TextField search_bar;

    @FXML
    private Button chercherButton;

    PatientDAO patientDAO;


    public void refreshTableData() {
        try {
            patientsTable.setItems(patientDAO.getAllPatients());
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    @FXML
    private void handleAddPatientButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../Views/Patients/AddPatientForm.fxml"));
            Parent root = fxmlLoader.load();


            Stage addPatientStage = new Stage();
            addPatientStage.initModality(Modality.APPLICATION_MODAL);
            addPatientStage.setTitle("Ajouter un nouveau patient");
            addPatientStage.setScene(new Scene(root));

            AddPatientForm addPatientFormController = fxmlLoader.getController();

            addPatientFormController.setPatientsFrame(this);



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
                int patientId = Integer.parseInt(searchText);
                Patient patient = patientDAO.getPatientById(patientId);
                if (patient != null) {
                    patientsTable.getItems().clear();
                    patientsTable.getItems().add(patient);
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
                patientsTable.setItems(patientDAO.getAllPatients());
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


    @FXML
    private void handleSearchOnChange() {
        String searchText = search_bar.getText().trim();
        if (searchText.isEmpty()) {
            try {
                patientsTable.setItems(patientDAO.getAllPatients());
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exception
            }
        }
    }


    private Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> getActionCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button();
                    private final Button editButton = new Button();
                    private final Button infoButton = new Button();
                    double wh = 10;

                    {
                        Image trashbinImage = new Image(getClass().getResourceAsStream("../../assets/rubbish.png"));
                        ImageView trashbinImageView = new ImageView(trashbinImage);
                        trashbinImageView.setFitWidth(wh);
                        trashbinImageView.setFitHeight(wh);
                        deleteButton.setGraphic(trashbinImageView);

                        Image penImage = new Image(getClass().getResourceAsStream("../../assets/pen.png"));
                        ImageView penImageView = new ImageView(penImage);
                        penImageView.setFitWidth(wh);
                        penImageView.setFitHeight(wh);
                        editButton.setGraphic(penImageView);

                        Image infoImage = new Image(getClass().getResourceAsStream("../../assets/info.png"));
                        ImageView infoImageView = new ImageView(infoImage);
                        infoImageView.setFitWidth(wh);
                        infoImageView.setFitHeight(wh);
                        infoButton.setGraphic(infoImageView);
                        deleteButton.setOnAction(event -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            try {
                                patientDAO.deletePatient(patient.getId());
                                getTableView().getItems().remove(patient);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });

                        editButton.setOnAction(event -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Views/Patients/EditPatientForm.fxml"));
                                Parent root = loader.load();

                                // Get the controller for the edit patient form
                                EditPatientFormController editPatientFormController = loader.getController();

                                // Pass the selected patient to the edit patient form controller
                                editPatientFormController.setPatient(patient);
                                editPatientFormController.initializeFields();


                                // Create a new stage for the edit patient form
                                Stage editPatientStage = new Stage();
                                editPatientStage.setTitle("Edit Patient");
                                editPatientStage.setScene(new Scene(root));

                                // Show the edit patient form
                                editPatientStage.showAndWait();
                                refreshTableData();
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle exception
                            }
                        });

                        infoButton.setOnAction(event -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Views/Patients/PatientProfilFrame.fxml"));
                                Parent root = loader.load();

                                // Get the controller for the patient profile view
                                PatientProfilFrame patientProfile = loader.getController();

                                // Initialize patient profile view with patient data
                                patientProfile.setPatient(patient);
                                patientProfile.initializeFields(patients_root_stack);

//                                borderPane.setCenter(root);
                                patients_root_stack.getChildren().add(root);

                            } catch (IOException e) {
                                e.printStackTrace();
                                // Handle exception
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(deleteButton, editButton, infoButton);
                            buttons.setSpacing(5);
                            setGraphic(buttons);
                        }
                    }
                };
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientDAO = new PatientDAO();
        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        dobCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth()));
        genderCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        numberCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        adresseCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        actionCol.setCellFactory(getActionCellFactory());
        try {
            patientsTable.setItems(patientDAO.getAllPatients());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

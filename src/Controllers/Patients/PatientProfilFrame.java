package Controllers.Patients;

import DatabaseUtils.PatientDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Appointment;
import models.Consultation;
import models.Patient;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PatientProfilFrame implements Initializable {
    PatientDAO patientDAO;
    private Patient patient;



    private StackPane patients_root_stack;

    @FXML
    private TableView<Appointment> rdvsTable;

    @FXML
    private TableColumn<Appointment, String> rdvDate;

    @FXML
    private TableColumn<Appointment, Integer> rdvDoctor;

    @FXML
    private TableColumn<Appointment, String> rdvState;

    @FXML
    private TableColumn<Appointment, String> rdvTime;
    @FXML
    private TableColumn<Appointment, Void> rdvAction;

    @FXML
    private Text text_id;

    @FXML
    private Text text_nom;

    @FXML
    private Text text_gender;

    @FXML
    private Text text_addresse;

    @FXML
    private Text text_dob;

    @FXML
    private Text text_number;

    @FXML
    private TableView<Consultation> consultations_table;

    @FXML
    private TableColumn<Consultation, String> cons_date_col;

    @FXML
    private TableColumn<Consultation, Integer> cons_doctor_col;

    @FXML
    private TableColumn<Consultation, String> cons_note_col;

    @FXML
    private TableColumn<Consultation, Void> cons_action_col;



    public void setPatient(Patient patient){
        this.patient = patient;
    }

    public void initializeFields(StackPane stackPane) {
        patientDAO = new PatientDAO();
        if (patient != null) {
            text_nom.setText(patient.getName());
            text_dob.setText(String.valueOf(LocalDate.parse(patient.getDateOfBirth())));
            text_gender.setText(patient.getGender());
            text_number.setText(patient.getPhoneNumber());
            text_addresse.setText(patient.getAddress());
            text_id.setText(String.valueOf(patient.getId()));
            patients_root_stack = stackPane;
            
            rdvDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTime()));
            rdvDoctor.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDoctorId()).asObject());
            rdvState.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getState()));
            rdvTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));
            rdvAction.setCellFactory(getActionCellFactory());

            cons_date_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTime()));
            cons_doctor_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDoctorId()).asObject());
            cons_note_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNotes()));
            cons_action_col.setCellFactory(getActionCellFactoryConsultation());

            try {
                rdvsTable.setItems(patientDAO.getAllPatientsRDV(patient.getId()));
                consultations_table.setItems(patientDAO.getAllPatientsConsulations(patient.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleReturnButton(ActionEvent event) {
        patients_root_stack.getChildren().remove(patients_root_stack.getChildren().size() - 1);
    }

    @FXML
    private void handleAddRDVButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../Views/Patients/AddRDVFromPatientProfile.fxml"));
            Parent root = fxmlLoader.load();


            Stage addPatientStage = new Stage();
            addPatientStage.initModality(Modality.APPLICATION_MODAL);
            addPatientStage.setTitle("Ajouter un nouveau RDV");
            addPatientStage.setScene(new Scene(root));

            AddRDVFromPatientProfile addRDVFromPatientProfile = fxmlLoader.getController();

            addRDVFromPatientProfile.setPatientProfileFrame(this);
            addRDVFromPatientProfile.setPatient_id(patient.getId());



            addPatientStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void handleAddConsultationButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../Views/Patients/AddConsultationForm.fxml"));
            Parent root = fxmlLoader.load();


            Stage addConsultationStage = new Stage();
            addConsultationStage.initModality(Modality.APPLICATION_MODAL);
            addConsultationStage.setTitle("Ajouter une nouvelle consultation");
            addConsultationStage.setScene(new Scene(root));

            AddConsultationForm addConsultationForm = fxmlLoader.getController();

            addConsultationForm.setPatientProfileFrame(this);
            addConsultationForm.setPatient_id(patient.getId());

            addConsultationStage.showAndWait();
            refreshTableData();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }



    private Callback<TableColumn<Appointment, Void>, TableCell<Appointment, Void>> getActionCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Appointment, Void> call(final TableColumn<Appointment, Void> param) {
                return new TableCell<>() {
                    private final Button checkButton = new Button();

                    double wh = 10;

                    {
                        Image trashbinImage = new Image(getClass().getResourceAsStream("../../assets/check.png"));
                        ImageView trashbinImageView = new ImageView(trashbinImage);
                        trashbinImageView.setFitWidth(wh);
                        trashbinImageView.setFitHeight(wh);
                        checkButton.setGraphic(trashbinImageView);

                        checkButton.setOnAction(event -> {
                            Appointment appointment = getTableView().getItems().get(getIndex());
                            try {
                                patientDAO.updateAppointment(appointment.getId());
                                refreshTableData();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(checkButton);
                            buttons.setSpacing(5);
                            setGraphic(buttons);
                        }
                    }
                };
            }
        };
    }

    private Callback<TableColumn<Consultation, Void>, TableCell<Consultation, Void>> getActionCellFactoryConsultation() {
        return new Callback<>() {
            @Override
            public TableCell<Consultation, Void> call(final TableColumn<Consultation, Void> param) {
                return new TableCell<>() {
                    private final Button infoButton = new Button();

                    double wh = 10;

                    {
                        Image trashbinImage = new Image(getClass().getResourceAsStream("../../assets/info.png"));
                        ImageView trashbinImageView = new ImageView(trashbinImage);
                        trashbinImageView.setFitWidth(wh);
                        trashbinImageView.setFitHeight(wh);
                        infoButton.setGraphic(trashbinImageView);

                        infoButton.setOnAction(event -> {
                            Consultation consultation = getTableView().getItems().get(getIndex());

                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../Views/Patients/ConsultationDetails.fxml"));
                                Parent root = fxmlLoader.load();


                                Stage consultationDetailsStage = new Stage();
                                consultationDetailsStage.initModality(Modality.APPLICATION_MODAL);
                                consultationDetailsStage.setTitle("Déatils sur la consultation");
                                consultationDetailsStage.setScene(new Scene(root));

                                ConsultationDetails consultationDetails = fxmlLoader.getController();


                                consultationDetails.setConsultation(consultation);
                                consultationDetails.initializeFields();

                                consultationDetailsStage.showAndWait();
                                refreshTableData();
                            } catch (Exception e) {
                                e.printStackTrace();

                            }




//                            try {
//                                patientDAO.updateAppointment(appointment.getId());
//                                refreshTableData();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                        });

                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(infoButton);
                            buttons.setSpacing(5);
                            setGraphic(buttons);
                        }
                    }
                };
            }
        };
    }

    public void refreshTableData() {
        try {
            rdvsTable.setItems(patientDAO.getAllPatientsRDV(patient.getId()));
            consultations_table.setItems(patientDAO.getAllPatientsConsulations(patient.getId()));
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patientDAO = new PatientDAO();
    }
}

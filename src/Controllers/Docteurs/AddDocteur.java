package Controllers.Docteurs;

import DatabaseUtils.DoctorsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDocteur implements Initializable {
    @FXML
    private TextField input_nom;

    @FXML
    private TextField input_numero;

    @FXML
    private TextField input_spes;

    DoctorsDAO doctorsDAO;
    DocteursFrame doctorsFrame;

    @FXML
    void handleSubmitButton(ActionEvent event) {
        String nom = input_nom.getText();
        String numero = input_numero.getText();
        String spes = input_spes.getText();


        doctorsDAO.saveDocteurToDatabase(nom,spes, numero);

        doctorsFrame.refreshTableData();


        Stage stage = (Stage) input_spes.getScene().getWindow();
        stage.close();
    }

    public void setDoctorsFrame(DocteursFrame rdVsFrame){
        this.doctorsFrame = rdVsFrame;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        doctorsDAO = new DoctorsDAO();
    }
}

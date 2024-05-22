package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import javafx.event.ActionEvent ;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private StackPane contentRegion;

    @FXML
    void loadScreenPatients(ActionEvent event) {
        loadContent("../Views/Patients/PatientsFrame.fxml");
    }

    @FXML
    void loadScreenDoctors(ActionEvent event) {
        loadContent("../Views/Doctors/DocteursFrame.fxml");
    }

    @FXML
    void loadScreenRDV() {
        loadContent("../Views/RDVs/RDVFrame.fxml");
    }

    private void loadContent(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent content = loader.load();
            contentRegion.getChildren().clear();
            contentRegion.getChildren().add(content);
            StackPane.setMargin(content, new Insets(10));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadScreenRDV();
    }
}

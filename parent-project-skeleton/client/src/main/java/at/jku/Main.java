package at.jku;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//--module-path "C:\Studium\Wirtschaftsinformatik\PR_Software_Engineering\DigitalRoom_Test3\client\lib" --add-modules javafx.controls,javafx.fxml

public class Main extends Application {

    private Stage stage;

    @Override
    public void start (Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("StartScene.fxml"));

        this.stage = stage;

        Scene scene = new Scene(root);

        stage.setTitle("Smart Room Applikation");
        stage.setScene(scene);
        stage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}



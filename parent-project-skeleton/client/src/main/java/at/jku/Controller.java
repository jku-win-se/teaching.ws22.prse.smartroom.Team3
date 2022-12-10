package at.jku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;


    //Views

    @FXML
    private ListView<String> roomsListView;
    @FXML
    private Label label;
    @FXML
    private Label fanLabel;
    @FXML
    private Label windowLabel;
    @FXML
    private Label doorLabel;
    @FXML
    private Label lightLabel;
    @FXML
    private Label deletedRoom;
    @FXML
    private Label createdRoom;
    @FXML
    private Label rooms;
    @FXML
    private TextField roomName;
    @FXML
    private TextField roomSize;
    @FXML
    private TextField roomId;
    @FXML
    private TextField measurementUnit;
    @FXML
    private TextField lightName;
    @FXML
    private TextField fanName;
    @FXML
    private TextField roomIdForDeleting;
    @FXML
    private TextArea lights;
    @FXML
    private Label fans;


    Client client = new Client();


    @FXML
    public void switchToStartScene(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getClassLoader().getResource("StartScene.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }


    @FXML
    public void switchToRoomScene(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getClassLoader().getResource("RoomScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }


    @FXML
    public void switchToCreateRoomScene(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getClassLoader().getResource("CreateRoomScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void switchToDeleteRoomScene(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getClassLoader().getResource("DeleteRoomScene.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void getRooms() throws IOException {

        //System.out.println(client.getRooms().body());

/*
        final JSONArray jsonArray = new JSONArray((String ) (client.getRooms().body()));

        for ( int i = 0; i < jsonArray.length(); i++ ) {
            rooms[i].setText(
                    "\nRaumname: " + jsonArray.getJSONObject(i).get("room_id")
                            + "\nRaum ID: " + jsonArray.getJSONObject(i).get("id")
                            +"\nRaumgroesse: " + jsonArray.getJSONObject(i).get("room_size")
                            + "\nEinheit: " + jsonArray.getJSONObject(i).get("measurement_unit"));
        }
*/

        rooms.setText(String.valueOf(client.getRooms()));

    }


    @FXML
    public String getLights() throws IOException {

        String name = lights.getText();
        //System.out.println(name);

        return name;
    }

    @FXML
    public String getFans() throws IOException {

        String name = fans.getText();
        //System.out.println(name);

        return name;
    }



    @FXML
    public String getLightName() throws IOException {

        String name = lightName.getText();
        //System.out.println(name);

        return name;
    }

    @FXML
    public String getFanName() throws IOException {

        String name = fanName.getText();
        //System.out.println(name);

        return name;
    }


    @FXML
    public void addLight(ActionEvent event) throws IOException {

        String light1 = getLightName();

        lights.setText(light1);
    }

    @FXML
    public void addFan(ActionEvent event) throws IOException {

        String fan1 = getFanName();

        fans.setText(fan1);
    }

    /*
    @FXML
    public void handleChangeText(){

        String text = roomName.getText();
        System.out.println(text);
        label.setText(text);

    } */

    @FXML
    public String getRoomName() throws IOException {

        String name = roomName.getText();
        //System.out.println(name);

        return name;
    }





    @FXML
    public void createNewRoom() throws IOException {

        /*
        String roomName = getRoomName();
        double roomSize = getRoomSize();
        String light = getLights();
        String fan = getFans();
         */

        client.addRoom(getRoomId(), getRoomSize(), getMeasurementUnit());

        createdRoom.setText(getRoomId() + " wurde erstellt.");

        /*
        System.out.println("Raumname: " + roomName);
        System.out.println("Raumgroesse: " + roomSize);
        System.out.println("Licht: " + light);
        System.out.println("Ventilator: " + fan);
        */
    }

    @FXML
    public void deleteRoom() throws IOException {

        String room = roomIdForDeleting.getText();
        deletedRoom.setText(room + " wurde geloescht!");
        System.out.println(room);
        client.deleteRoom(room);




    }

    @FXML
    public double getRoomSize() throws IOException {

        double size = Double.parseDouble(roomSize.getText());
        //System.out.println(size);

        return size;
    }

    @FXML
    public String getRoomId () {
        String room = roomId.getText();

        return room;
    }

    @FXML
    public String getMeasurementUnit () {
        String unit = measurementUnit.getText();

        return unit;
    }

    /*
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        roomsListView.getItems().addAll(allRooms);

        roomsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                currentRoom = roomsListView.getSelectionModel().getSelectedItem();

            }
        });
    }
    */
}

/*
    @FXML
    public void doorButton(ActionEvent event) throws IOException {
        doorLabel.setText("offen");
    }
    @FXML
    public void windowButton(ActionEvent event) throws IOException {
        windowLabel.setText("offen");
    }
    @FXML
    public void fanButton(ActionEvent event) throws IOException {
        fanLabel.setText("an");
    }
    @FXML
    public void lightButton(ActionEvent event) throws IOException {
        lightLabel.setText("an");
    }
*/


/*
    @FXML
    public void startScene() throws IOException {


            FXMLLoader loader = new FXMLLoader(Main.class.getResource("StartScene.fxml"));
            AnchorPane pane = loader.load();

            scene = new Scene(pane);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Smart Room Applikation");
            primaryStage.show();

    }   */

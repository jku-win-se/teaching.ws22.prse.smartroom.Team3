package at.jku;

import at.jku.objects.Lights_Object;
import at.jku.objects.Room_Object;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    //Views

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
    private Label roomNameLabel;
    @FXML
    private Label roomSizeLabel;
    @FXML
    private Label deviceLabel1;
    @FXML
    private Label createdRoom;
    @FXML
    private Label peopleInRoomLabel;
    @FXML
    private Label roomIdLabelInUpdateRoom;

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
    private TextField lightId;
    @FXML
    private TextField fanName;
    @FXML
    private TextField roomIdForDeleting;
    @FXML
    private TextArea lights;
    @FXML
    private Label fans;
    @FXML
    private ListView<Room_Object> roomListView = new ListView<>();
    @FXML
    private TableView<Room_Object> roomTableView = new TableView<>();
    @FXML
    private TableView<Lights_Object> detailTableView = new TableView<Lights_Object>();
    @FXML
    private TableColumn<Lights_Object, Integer> device = new TableColumn<>();;
    @FXML
    private TableColumn<Lights_Object, Integer> device_id = new TableColumn<>();;
    @FXML
    private TableColumn<Room_Object, Integer> size = new TableColumn<>();
    @FXML
    private TableColumn<Room_Object, Integer> name = new TableColumn<>();
    @FXML
    private TableColumn<Room_Object, Integer> unit;


    public Client client = new Client();

    ObservableList<Room_Object> rooms = FXCollections.observableArrayList(client.getRooms());
    ObservableList<Lights_Object> details = FXCollections.observableArrayList();

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
    public void switchToUpdateRoomScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("UpdateRoomScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name.setCellValueFactory(new PropertyValueFactory<Room_Object, Integer>("room_id"));
        size.setCellValueFactory(new PropertyValueFactory<Room_Object, Integer>("room_size"));
        //size.setCellValueFactory(new PropertyValueFactory<Room_Object, Integer>("measurement_unit"));

        device.setCellValueFactory(new PropertyValueFactory<Lights_Object, Integer>("name"));
        device_id.setCellValueFactory(new PropertyValueFactory<Lights_Object, Integer>("light_id"));

        detailTableView.setItems(details);


        getRooms();
    }

    public void showRoom(){

        details.clear();

        Room_Object room = roomTableView.getSelectionModel().getSelectedItem();
        String roomId = room.getRoom_id();


        roomNameLabel.setText(room.getRoom_id());
        roomSizeLabel.setText("Size: " + room.getRoom_size() + " " + room.getMeasurement_unit());
        peopleInRoomLabel.setText("People in Room: " + client.getPeopleCount(roomId).getPeople_count());
        //deviceLabel1.setText(details.);
        //Test
        details.addAll(client.getAllLights(roomId));

        for ( int i = 0; i < details.size(); i++ ) {
            detailTableView.setItems(details);
        }

    }

    @FXML
    public void showDetails(){


        details.clear();

        Room_Object room = roomTableView.getSelectionModel().getSelectedItem();
        String roomId = room.getRoom_id();
        System.out.println(roomId);

        details.addAll(client.getRoomLight(roomId, "Light10"));

        System.out.println(client.getRoomLight(roomId, "Light10"));
        System.out.println(client.getPeopleCount(roomId));


        for ( int i = 0; i < details.size(); i++ ) {
            detailTableView.setItems(details);
        }

        peopleInRoomLabel.setText("People in Room: " + client.getPeopleCount(roomId).getPeople_count());
    }


    public void getRooms() {

        ObservableList<Room_Object> rooms = FXCollections.observableArrayList(client.getRooms());

        for ( int i = 0; i < rooms.size(); i++ ) {
            roomTableView.setItems(rooms);
        }
    }

    @FXML
    public void deleteRoom() throws IOException {

        Room_Object room_object = roomTableView.getSelectionModel().getSelectedItem();

        String room = room_object.getRoom_id();
        System.out.println(room);
        client.deleteRoom(room);

        rooms.clear();

        getRooms();
    }


    @FXML
    public String getLights() throws IOException {

        String name = lights.getText();

        return name;
    }

    @FXML
    public String getFans() throws IOException {

        String name = fans.getText();

        return name;
    }



    @FXML
    public String getLightName() throws IOException {

        String name = lightName.getText();

        return name;
    }

    @FXML
    public String getFanName() throws IOException {

        String name = fanName.getText();

        return name;
    }


    @FXML
    public void addLight(ActionEvent event) throws IOException {

        String lightName = getNameOfLight();
        String lightId = getLightId();

        client.addLight(getRoomId(),lightId,lightName);

    }

    @FXML
    public void addFan(ActionEvent event) throws IOException {

        String fan1 = getFanName();

        fans.setText(fan1);
    }


    @FXML
    public void createNewRoom() throws IOException {

        client.addRoom(getRoomId(), getRoomSize(), getMeasurementUnit());

        createdRoom.setText(getRoomId() + " wurde erstellt.");
    }

    @FXML
    public void updateRoom() throws IOException {

        client.updateRoom(getRoomId(), getRoomSize(), getMeasurementUnit());
    }

    @FXML
    public void addLight() throws IOException {

        client.addLight(getRoomId(), getLightId(), getLightName());
    }

    @FXML
    public double getRoomSize() throws IOException {
        double size = Double.parseDouble(roomSize.getText());

        return size;
    }

    @FXML
    public String getRoomId () {
        String room = roomId.getText();

        //Room_Object room = roomTableView.getSelectionModel().getSelectedItem();
        return room;
    }

    @FXML
    public String getMeasurementUnit () {
        String unit = measurementUnit.getText();

        return unit;
    }

    @FXML
    public String getLightId () {
        String unit = lightId.getText();

        return unit;
    }

    @FXML
    public String getNameOfLight () {
        String unit = lightName.getText();

        return unit;
    }

    @FXML
    public void changeStatus () {

    }








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

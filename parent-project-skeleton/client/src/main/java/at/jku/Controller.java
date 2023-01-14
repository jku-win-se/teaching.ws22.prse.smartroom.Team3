package at.jku;

import at.jku.objects.*;
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
import at.jku.clientObjects.*;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private TextField fanName;
    @FXML
    private TextField fanId1;
    @FXML
    private Label createdRoom;
    @FXML
    private Label peopleInRoomLabel;
    @FXML
    private Label createdLight;
    @FXML
    private Label roomUpdated;
    @FXML
    private TextField roomSize;
    @FXML
    private TextField roomName;
    @FXML
    private TextField roomId;
    @FXML
    private TextField measurementUnit;
    @FXML
    private TextField lightName;
    @FXML
    private TextField lightId;
    @FXML
    private TextField people;
    @FXML
    private TextField doorId;
    @FXML
    private TextField windowId;
    @FXML
    private TextField windowName;
    @FXML
    private TextField doorName;
    @FXML
    private TextArea lights;
    @FXML
    private Label fans;
    @FXML
    private ListView<Room_Object> roomListView = new ListView<>();
    @FXML
    private ComboBox<Room_Object> comboBox1 = new ComboBox<Room_Object>();
    @FXML
    private TableView<Room_Object> roomTableView = new TableView<>();
    @FXML
    private TableView<Component> detailTableView = new TableView<Component>();
    @FXML
    private TableColumn<Component, Integer> device = new TableColumn<>();
    @FXML
    private TableColumn<Component, Integer> type = new TableColumn<>();
    @FXML
    private TableColumn<Component, Integer> status = new TableColumn<>();
    @FXML
    private TableColumn<Room_Object, Integer> size = new TableColumn<>();
    @FXML
    private TableColumn<Room_Object, Integer> name = new TableColumn<>();
    @FXML
    private TableColumn<Room_Object, Integer> unit;


    public Client client = new Client();
    Room currentRoom = new Room();



    String globalMeasurementUnit = "m2";
    ObservableList<Room_Object> rooms = FXCollections.observableArrayList(client.getRooms());
    ObservableList<Component> details = FXCollections.observableArrayList();



    public Room getCompleteRoom(String room_id){
        Room_Object r = client.getRoomID(room_id);
        List<Component> components =  new ArrayList<>();

        List<Lights_Object> lights = client.getAllLights(room_id);

        for(Lights_Object l : lights)
        {
            components.add(new Component(l.getLight_id(),l.getName(),room_id,ComponentType.LIGHT,client.getCurrentLightStatus(room_id,l.getLight_id())));
        }
/*
        //client.getCurrentLightStatus(room_id,l.getLight_id()).isTurnon(); in status of for()

        List<Power_Plug_Object> fans = client.getAllVents(room_id);

        for(Power_Plug_Object p : fans)
        {
            components.add(new Component(p.getPlug_id(),p.getName(),room_id,ComponentType.FAN,false));
        }
        //client.getCurrentPowerPlugStatus(room_id,p.plug_id).isTurnon() in status of for()

        List<Window_Object> windows = client.getAllRoomWindows(room_id);

        for(Window_Object w : windows)
        {
            components.add(new Component(w.getWindow_id(),w.getName(),room_id,ComponentType.WINDOW,false));
        }
        //client.getWindowStatus(room_id,w.window_id) in status of for()

        List<Door_Object> doors = client.getAllRoomDoor(room_id);

        for(Door_Object d : doors)
        {
            components.add(new Component(d.getDoor_id(),d.getName(),room_id,ComponentType.DOOR,false));
        }
        //client.getDoorStatus(room_id,d.door_id). in status of for()

*/

        //alle airquality abfragen
        return new Room(room_id,(int)r.getRoom_size(),room_id,0,components);
    }
    @FXML
    public void switchToStartScene(ActionEvent event) throws IOException {
        //fetch rooms from server
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


    @FXML
    public void addLight(ActionEvent event) throws IOException {
        currentRoom = getCompleteRoom(comboBox1.getValue().getRoom_id());

        String lightName = getNameOfLight();
        String lightId = getLightId();

        currentRoom.addLight(lightId,lightName,false);
        createdLight.setText(lightName + " wurde hinzugefügt");
    }

    @FXML
    public void addFan(ActionEvent event) throws IOException {
        currentRoom = getCompleteRoom(comboBox1.getValue().getRoom_id());

        String fanName = getNameOfFan();
        String fanId = getFanId();


        currentRoom.addVentilator(fanId,fanName,false);
        createdLight.setText(fanName + " wurde hinzugefügt");
    }

    @FXML
    public void addDoor(ActionEvent event) throws IOException {
        currentRoom = getCompleteRoom(comboBox1.getValue().getRoom_id());

        String doorName = getNameOfDoor();
        String doorId = getDoorId();


        currentRoom.addDoor(doorId, doorName,false);
        createdLight.setText(doorName + " wurde hinzugefügt");
    }

    @FXML
    public void addWindow(ActionEvent event) throws IOException {
        currentRoom = getCompleteRoom(comboBox1.getValue().getRoom_id());

        String windowName = getNameOfWindow();
        String windowId = getWindowId();

        currentRoom.addWindow(windowName, windowId,false);
        createdLight.setText(windowName + " wurde hinzugefügt");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name.setCellValueFactory(new PropertyValueFactory<Room_Object, Integer>("room_id"));
        size.setCellValueFactory(new PropertyValueFactory<Room_Object, Integer>("room_size"));
        //size.setCellValueFactory(new PropertyValueFactory<Room_Object, Integer>("measurement_unit"));

        device.setCellValueFactory(new PropertyValueFactory<Component, Integer>("name"));
        type.setCellValueFactory(new PropertyValueFactory<Component, Integer>("type"));
        status.setCellValueFactory(new PropertyValueFactory<Component, Integer>("status"));


        detailTableView.setItems(details);

        comboBox1.setItems(getRooms());

        roomTableView.setItems(getRooms());
    }


    @FXML
    public void showDetails(){
        details.clear();

        Room_Object room = roomTableView.getSelectionModel().getSelectedItem();
        String roomId = room.getRoom_id();
        System.out.println(roomId);

        details.addAll((Collection<? extends Component>) client.getRoomLight(roomId, "Light10"));

        System.out.println(client.getRoomLight(roomId, "Light10"));
        System.out.println(client.getPeopleCount(roomId));


        for ( int i = 0; i < details.size(); i++ ) {
            detailTableView.setItems(details);
        }

        peopleInRoomLabel.setText("People in Room: " + client.getPeopleCount(roomId).getPeople_count());
    }

    @FXML
    public void saveToCsvData() throws IOException {
        File file = new File(System.getProperty("user.home") + "\\Desktop\\components.csv");
        Writer writer = new BufferedWriter(new FileWriter(file));
        try {
            writer.write("Room,Size,Component,Component Type,Status");
            writer.write("\n");
            for (Room_Object room_object : client.getRooms()) {

                Room room = getCompleteRoom(room_object.getRoom_id());
                for (Component component : room.getAllComponents()) {

                    String text = room_object.getRoom_id() + ","+ room_object.getRoom_size()+",";

                    if(component.getName() != null) {
                        text += component.getName() + "," + component.getType() + "," + component.getStatus() + "\n";
                    }

                    writer.write(text);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            writer.flush();
            writer.close();
        }

    }


    public ObservableList<Room_Object> getRooms() {

        ObservableList<Room_Object> rooms = FXCollections.observableArrayList(client.getRooms());

        return rooms;
    }

    public void showRoom(){

        details.clear();

        Room_Object room = roomTableView.getSelectionModel().getSelectedItem();
        String roomId = room.getRoom_id();
        if(roomId!=null) {
            currentRoom = getCompleteRoom(roomId);
        }

        roomNameLabel.setText(currentRoom.getRoom_id());
        roomSizeLabel.setText("Size: " + currentRoom.getSize() + " " + globalMeasurementUnit);
        peopleInRoomLabel.setText("People in Room: " + client.getPeopleCount(roomId).getPeople_count());
        //deviceLabel1.setText(details.);
        //Test

        details.addAll(currentRoom.getAllComponents());



        for ( int i = 0; i < details.size(); i++ ) {
            detailTableView.setItems(details);
        }

    }

    @FXML
    public void deleteRoom() throws IOException {

        Room_Object room_object = roomTableView.getSelectionModel().getSelectedItem();

        client.deleteRoom(room_object.getRoom_id());

        rooms.clear();

        roomTableView.setItems(getRooms());
    }

    @FXML
    public void deleteComponent () {

        Component component = detailTableView.getSelectionModel().getSelectedItem();

        client.deleteRoomLight(component.getRoom_id(), component.getId()); ////Muss noch umgeschrieben werden auf deleteComponent

        details.clear();

        showRoom();
    }
    @FXML
    public void changeStatus () {

        Component component = detailTableView.getSelectionModel().getSelectedItem();

        component.changeStatus();

        System.out.println(component.getStatus()); //zum testen

        details.clear();

        showRoom();

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
    public void addPeople(ActionEvent event) throws IOException {

        currentRoom = getCompleteRoom(comboBox1.getValue().getRoom_id());

        int people = getPeople();

        client.addPeopleRoom(currentRoom.getRoom_id(),getPeople());

    }



    @FXML
    public void createNewRoom() throws IOException {

       Room_Object r = client.addRoom(getRoomId(), getRoomSize(), globalMeasurementUnit);
        createdRoom.setText(getRoomId() + " wurde erstellt.");
    }

    @FXML
    public void updateRoom() throws IOException {

        currentRoom = getCompleteRoom(comboBox1.getValue().getRoom_id());

        client.updateRoom(currentRoom.getRoom_id(), getRoomSize(), globalMeasurementUnit);

        roomUpdated.setText(currentRoom.getRoom_id() + " wurde upgedated.");
    }

    @FXML
    public double getRoomSize() throws IOException {
        double size = Double.parseDouble(roomSize.getText());

        return size;
    }

    @FXML
    public String getRoomName() throws IOException {
        String name = roomName.getText();

        return name;
    }

    @FXML
    public String getRoomId () {
        String room = roomId.getText();

        return room;
    }


    @FXML
    public String getLightId () {
        String unit = lightId.getText();

        return unit;
    }

    @FXML
    public String getFanId () {
        String unit = fanName.getText();

        return unit;
    }

    @FXML
    public int getPeople () {

        int peoples = Integer.parseInt(people.getText());

        return peoples;
    }


    @FXML
    public String getNameOfLight () {
        String unit = lightName.getText();

        return unit;
    }

    @FXML
    public String getNameOfDoor () {
        String unit = doorName.getText();

        return unit;
    }

    @FXML
    public String getNameOfWindow () {
        String unit = windowName.getText();

        return unit;
    }

    @FXML
    public String getNameOfFan () {
        String unit = fanName.getText();

        return unit;
    }

    @FXML
    public String getDoorId () {
        String unit = doorId.getText();

        return unit;
    }

    @FXML
    public String getWindowId () {
        String unit = windowId.getText();

        return unit;
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

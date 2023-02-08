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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import at.jku.clientObjects.*;
import javafx.util.Callback;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;

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
    private LineChart co2Chart;
    @FXML
    private LineChart.Series<String,Integer> seriesCo2 = new LineChart.Series();
    @FXML
    private ObservableList<XYChart.Series<String,Integer>> co2ChartData = FXCollections.observableArrayList();

    @FXML
    private LineChart tempChart;
    @FXML
    private LineChart.Series<String,Integer> seriesTemp = new LineChart.Series();
    @FXML
    private ObservableList<XYChart.Series<String,Integer>> tempChartData = FXCollections.observableArrayList();

    @FXML
    private LineChart peopleChart;
    @FXML
    private LineChart.Series<String,Integer> seriesPeople = new LineChart.Series();
    @FXML
    private ObservableList<XYChart.Series<String,Integer>> peopleChartData = FXCollections.observableArrayList();

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

        List<Power_Plug_Object> fans = client.getAllVents(room_id);

        for(Power_Plug_Object p : fans)
        {
            components.add(new Component(p.getPlug_id(),p.getName(),room_id,ComponentType.FAN,client.getCurrentPowerPlugStatus(room_id,p.getPlug_id()).isTurnon()));
        }

        List<Door_Object> doors = client.getAllRoomDoor(room_id);

        for(Door_Object d : doors)
        {

            components.add(new Component(d.getDoor_id(),d.getName(),room_id,ComponentType.DOOR,client.getDoorStatus(room_id,d.getDoor_id()).isOpen()));
        }

        List<Window_Object> windows = client.getAllRoomWindows(room_id);

        for(Window_Object w : windows)
        {

           components.add(new Component(w.getWindow_id(),w.getName(),room_id,ComponentType.WINDOW,client.getWindowStatus(room_id,w.getWindow_id()).isOpen()));
        }



/*
        //client.getCurrentLightStatus(room_id,l.getLight_id()).isTurnon(); in status of for()


        //client.getCurrentPowerPlugStatus(room_id,p.plug_id).isTurnon() in status of for()


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
        if(comboBox1.getValue() == null) {
            createdLight.setText("Bitte Raum auswählen");
            return;
        }
        currentRoom = getCompleteRoom(comboBox1.getValue().getRoom_id());
        String lightName = getNameOfLight();
        String lightId = getLightId();

        currentRoom.addLight(lightId,lightName,false);
        createdLight.setText(lightName + " wurde hinzugefügt");
    }

    @FXML
    public void updateRoom() throws IOException {
        if(comboBox1.getValue() == null) {
            createdLight.setText("Bitte Raum auswählen");
            return;
        }

        currentRoom = getCompleteRoom(comboBox1.getValue().getRoom_id());

        client.updateRoom(currentRoom.getRoom_id(), getRoomSize(), globalMeasurementUnit);

        roomUpdated.setText(currentRoom.getRoom_id() + " wurde upgedated.");
    }

    @FXML
    public void addFan(ActionEvent event) throws IOException {

        if(comboBox1.getValue() == null) {
            createdLight.setText("Bitte Raum auswählen");
            return;
        }

        currentRoom = getCompleteRoom(comboBox1.getValue().getRoom_id());

        String fanName = getNameOfFan();
        String fanId = getFanId();


        currentRoom.addVentilator(fanId,fanName,false);
        createdLight.setText(fanName + " wurde hinzugefügt");
    }

    @FXML
    public void addDoor(ActionEvent event) throws IOException {

        if(comboBox1.getValue() == null) {
            createdLight.setText("Bitte Raum auswählen");
            return;
        }

        currentRoom = getCompleteRoom(comboBox1.getValue().getRoom_id());

        String doorName = getNameOfDoor();
        String doorId = getDoorId();


        currentRoom.addDoor(doorId, doorName,false);
        createdLight.setText(doorName + " wurde hinzugefügt");
    }

    @FXML
    public void addWindow(ActionEvent event) throws IOException {

        if(comboBox1.getValue() == null) {
            createdLight.setText("Bitte Raum auswählen");
            return;
        }

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

        comboBox1.setButtonCell(new ListCell<Room_Object>(){
            @Override
            protected void updateItem(Room_Object r, boolean bln) {
                super.updateItem(r, bln);
                if(r != null) {
                    setText(r.getRoom_id());
                }else{
                    setText(null);
                }
            }
        });

        comboBox1.setCellFactory(new Callback<ListView<Room_Object>, ListCell<Room_Object>>() {
            @Override
            public ListCell<Room_Object> call(ListView<Room_Object> room_objectListView) {
                return new ListCell<Room_Object>(){
                    @Override
                    protected void updateItem (Room_Object r, boolean bln) {
                        super.updateItem(r, bln);
                        if(r != null){
                            setText(r.getRoom_id());
                        }else{
                            setText(null);
                        }
                    }
                };
            }
        });

        comboBox1.setItems(getRooms());

        roomTableView.setItems(getRooms());
    }


    @FXML
    public void loadDataFromCsv() throws SQLException, IOException {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        List<String> lines = Files.readAllLines(selectedFile.toPath());

        for(int i = 1; i < lines.size(); i++) {

            String[] fields = lines.get(i).split(",");

            client.addRoom(fields[0], Double.parseDouble(fields[1]), globalMeasurementUnit);

            if (fields[3].equals(ComponentType.LIGHT.toString()))
                client.addLight(fields[0], fields[2], fields[2]);

            if (fields[3].equals(ComponentType.DOOR.toString()))
                client.addRoomDoor(fields[0], fields[2], fields[2]);

            if (fields[3].equals(ComponentType.FAN.toString()))
                client.addVentilator(fields[0], fields[2], fields[2]);

            if (fields[3].equals(ComponentType.WINDOW.toString()))
                client.addRoomWindow(fields[0], fields[2], fields[2]);
        }

        rooms.clear();
        roomTableView.setItems(getRooms());

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
        if(room == null)
            return;

        String roomId = room.getRoom_id();
        if(roomId!=null) {
            currentRoom = getCompleteRoom(roomId);
        }

        roomNameLabel.setText(currentRoom.getRoom_id());
        roomSizeLabel.setText("Size: " + currentRoom.getSize() + " " + globalMeasurementUnit);
        //peopleInRoomLabel.setText("People in Room: " + client.getPeopleCount(roomId).getPeople_count());
        //deviceLabel1.setText(details.);
        //Test

        //People Line Chart
        //peopleChart.getData().removeAll();
        //peopleSeries.getData().add(new XYChart.Data<String,Integer>("test", client.getPeopleCount(roomId).getPeople_count()));
        //peopleChartData.add(peopleSeries);
        //peopleChart.setData(peopleChartData);

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

        client.deleteRoomLight(component.getRoom_id(), component.getId());
        client.deleteVent(component.getRoom_id(), component.getId());
        client.deleteDoor(component.getRoom_id(), component.getId());
        client.deleteWindow(component.getRoom_id(),component.getId());

        details.clear();

        showRoom();
    }
    @FXML
    public void changeStatus () {

        Component component = detailTableView.getSelectionModel().getSelectedItem();


        component.changeStatus();

        //boolean status = component.getStatus();

        //component.setStatus( (!status) );

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
       //client.addPeopleRoom(getRoomId(), 0);

       createdRoom.setText(getRoomId() + " wurde erstellt.");
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

    @FXML
    public void enableAutoRules(){
        addRandomCo2();
        addRandomTemp();
        addRandomPeople();
    }

    public void addRandomPeople(){

        seriesPeople.getData().clear();
        for (int i = 0; i < 10; i++) {
            int random = (int)(Math.random()* 30);
            seriesPeople.getData().add(new XYChart.Data<String, Integer>(Integer.toString(i),random));
        }
        int random = (int)(Math.random()* 30);
        seriesPeople.getData().add(new XYChart.Data<String, Integer>(" ",random));


        peopleChartData.add(seriesPeople);
        peopleChart.setData(peopleChartData);

    }

    public void addRandomTemp(){

        seriesTemp.getData().clear();
        for (int i = 0; i < 10; i++) {
            int random = (int)(Math.random()*(100-20+1)+20);
            seriesTemp.getData().add(new XYChart.Data<String, Integer>(Integer.toString(i),random));
        }
        int random = (int)(Math.random()*(100-20+1)+20);
        seriesTemp.getData().add(new XYChart.Data<String, Integer>(" ",random));
        if (random > 70){
            //openAllDoors();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Temperature is above 70 degrees");
            alert.showAndWait();
        }

        tempChartData.add(seriesTemp);
        tempChart.setData(tempChartData);

    }

    @FXML
    public void addRandomCo2(){

        seriesCo2.getData().clear();

        for (int i = 0; i < 10; i++) {
            int random = (int)(Math.random()*(1400-500+1)+500);
            seriesCo2.getData().add(new XYChart.Data<String, Integer>(Integer.toString(i),random));
        }
        int random = (int)(Math.random()*(1400-500+1)+500);
        seriesCo2.getData().add(new XYChart.Data<String, Integer>(" ",random));

        if(random < 800){
            roomTableView.setStyle("-fx-selection-bar: green;");
        }else if(random > 800 && random < 1000){
            roomTableView.setStyle("-fx-selection-bar: yellow;");
        }else {
            roomTableView.setStyle("-fx-selection-bar: red;");
            //openAllWindows();
            //switchAllVentilatorsOn();
        }

        co2ChartData.add(seriesCo2);
        co2Chart.setData(co2ChartData);
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

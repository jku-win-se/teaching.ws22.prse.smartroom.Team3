import at.jku.digitalTwin.PostgreSQLJDBC;
import at.jku.digitalTwin.RESTController;
import at.jku.digitalTwin.objects.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ServerTests {

    @Test
    public void testGetRooms_checkIfReturnTypeIsCorrect() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        List<Room_Object> expectedRooms = new ArrayList<>();
        when(postgreSQLJDBC.getAllRooms(any(), anyString())).thenReturn(expectedRooms);

        // Act
        ResponseEntity<List<Room_Object>> actualResponse = restController.getRooms();

        // Assert
        assertEquals(expectedRooms, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testAddRoom_checkIfRoomIsAdded() throws SQLException {
        // Arrange
        Room_Object expectedRoom = new Room_Object("testroom", 100.0, "m2");
        Connection c = mock(Connection.class);
        RESTController restController = new RESTController();
        Statement statement = mock(Statement.class);

        when(c.createStatement()).thenReturn(statement);

        // Act
        ResponseEntity<Room_Object> actualResponse = restController.addRoom(expectedRoom);

        // Assert
        assertEquals(expectedRoom, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testGetRoomById_checkIfReturnTypeIsCorrect() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        Room_Object expectedRoom = new Room_Object("testroom", 100.0, "m2");
        when(postgreSQLJDBC.getRoomById(any(Connection.class), anyString())).thenReturn(expectedRoom);

        // Act
        ResponseEntity<Room_Object> actualResponse = restController.getRoomId("testroom");

        // Assert
        assertEquals(expectedRoom, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testGetRoomById_checkIfNotFound() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        when(postgreSQLJDBC.getRoomById(any(Connection.class), anyString())).thenReturn(null);

        // Act
        ResponseEntity<Room_Object> actualResponse = restController.getRoomId("testroom");

        // Assert
        assertNull(actualResponse.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    public void testDeleteRoom_checkIfRoomIsDeleted() throws SQLException {
        // Arrange
        RESTController restController = new RESTController();

        // Act
        ResponseEntity<Room_Object> actualResponse = restController.deleteRoom("testroom");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    public void testUpdateRoom_checkIfReturnTypeIsCorrect() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        Room_Object expectedRoom = new Room_Object("testroom", 100.0, "m2");
        Update_RoomObject update_roomObject = new Update_RoomObject(200.0, "m2");
        when(postgreSQLJDBC.updateRoomById(any(Connection.class), anyString(), any(Update_RoomObject.class))).thenReturn(expectedRoom);

        // Act
        ResponseEntity<Update_RoomObject> actualResponse = restController.updateRoom("testroom", update_roomObject);

        // Assert
        assertEquals(update_roomObject, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testGetPeopleInRoom_checkIfReturnTypeIsCorrect() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        PeopleInRoomObject expectedPeopleInRoom = new PeopleInRoomObject("testroom", 100, Timestamp.valueOf(LocalDateTime.now()));
        when(postgreSQLJDBC.getPeopleInRoomById(any(Connection.class), anyString())).thenReturn(expectedPeopleInRoom);

        // Act
        ResponseEntity<PeopleInRoomObject> actualResponse = restController.getPeopleInRoom("testroom");

        // Assert
        assertEquals(expectedPeopleInRoom, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testAddPeopleInRoom_checkIfPeopleInRoomIsAdded() throws SQLException {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.db = postgreSQLJDBC;
        PeopleInRoomObject expectedPeopleInRoom = new PeopleInRoomObject("testroom", 100, Timestamp.valueOf("1983-07-12 21:30:55.888"));
        when(postgreSQLJDBC.addPeopleInRoomById(any(), eq("testroom"), eq(expectedPeopleInRoom))).thenReturn(expectedPeopleInRoom);

        // Act
        ResponseEntity<PeopleInRoomObject> actualResponse = restController.addPeopleInRoom("testroom", expectedPeopleInRoom);

        // Assert
        assertEquals(expectedPeopleInRoom, actualResponse.getBody());
        assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
    }

//Lights

    @Test
    public void testGetAllLights_CheckIfReturnTypeIsCorrect() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "room_id";
        List<Lights_Object> expectedLights = new ArrayList<>();
        when(postgreSQLJDBC.getLightsByRoomId(any(), eq(room_id))).thenReturn(expectedLights);

        // Act
        ResponseEntity<List<Lights_Object>> actualResponse = restController.getAllLights(room_id);

        // Assert
        assertEquals(expectedLights, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testAddLight_checkIfLightIsAdded() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        Lights_Object expectedLight = new Lights_Object("testlight", "testlight");
        when(postgreSQLJDBC.addLight(any(), anyString(), any(Lights_Object.class))).thenReturn(expectedLight);

        // Act
        ResponseEntity<Lights_Object> actualResponse = restController.addLights("testroom", new Lights_Object());

        // Assert
        assertEquals(expectedLight, actualResponse.getBody());
        assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
    }


    @Test
    public void testGetRoomLight_returnsLight() {
        // Arrange
        Lights_Object expectedLight = new Lights_Object("testlight", "testlight");
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.getLightById(any(Connection.class), anyString(), anyString())).thenReturn(expectedLight);
        RESTController restController = new RESTController();
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String light_id = "testlight";

        // Act
        ResponseEntity<Lights_Object> actualResponse = restController.getRoomLight(room_id, light_id);

        // Assert
        assertEquals(expectedLight, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testGetRoomLight_returnsNotFound() {
        // Arrange
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.getLightById(any(Connection.class), anyString(), anyString())).thenReturn(null);
        RESTController restController = new RESTController();
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String light_id = "testlight";

        // Act
        ResponseEntity<Lights_Object> actualResponse = restController.getRoomLight(room_id, light_id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    public void testDeleteLight_returnsOk() {
        // Arrange
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.deleteLightById(any(Connection.class), anyString(), anyString())).thenReturn(true);
        RESTController restController = new RESTController();
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String light_id = "testlight";

        // Act
        ResponseEntity<String> actualResponse = restController.deleteLight(room_id, light_id);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testUpdateLight_returnsOk() {
        // Arrange
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        Lights_Object updatedLight = new Lights_Object();
        updatedLight.setLight_id("testlight");
        updatedLight.setName("testname");
        when(postgreSQLJDBC.updateLightById(any(Connection.class), anyString(), anyString(), any(Update_LightObject.class))).thenReturn(updatedLight);
        RESTController restController = new RESTController();
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String light_id = "testlight";
        Update_LightObject update_LightObject = new Update_LightObject();
        update_LightObject.setName("testname");

        // Act
        ResponseEntity<Update_LightObject> actualResponse = restController.updateLight(room_id, light_id, update_LightObject);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(update_LightObject, actualResponse.getBody());
    }

    @Test
    public void testGetLightOperations_returnsOk() {
        // Arrange
        Connection c = mock(Connection.class);
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.getLightOperations(eq(c), anyString(), anyString()))
                .thenReturn(Arrays.asList(new Light_Operation_Return_Object()));
        RESTController restController = new RESTController();
        restController.c = c;
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String light_id = "testlight";

        // Act
        ResponseEntity<List<Light_Operation_Return_Object>> actualResponse =
                restController.getLightOperations(room_id, light_id);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        List<Light_Operation_Return_Object> lightOperations = actualResponse.getBody();
        assertNotNull(lightOperations);
        assertEquals(1, lightOperations.size());
    }

    @Test
    public void testActivateLight() throws Exception {
        // Arrange
        Connection c = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        when(c.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();

        Light_Activation_Object light_activation_object = new Light_Activation_Object();
        light_activation_object.setTurnon(true);

        // Act
        boolean result = postgreSQLJDBC.activateLight(c, "testroom", "testlight", light_activation_object);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testSetColor_checkIfResponseIsOK() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        Light_Operation_Object lightOperationObject = new Light_Operation_Object();
        when(postgreSQLJDBC.setColor(any(), anyString(), anyString(), any(Light_Operation_Object.class))).thenReturn(lightOperationObject);

        // Act
        ResponseEntity<Light_Operation_Object> actualResponse = restController.SetColor("room_id", "light_id", lightOperationObject);

        // Assert
        assertEquals(lightOperationObject, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    //Ventilators

    @Test
    public void testGetAllFans_CheckIfReturnTypeIsCorrect() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "room_id";
        List<Power_Plug_Object> expectedFans = new ArrayList<>();
        when(postgreSQLJDBC.getVentilators(any(), eq(room_id))).thenReturn(expectedFans);

        // Act
        ResponseEntity<List<Power_Plug_Object>> actualResponse = restController.getVentilators(room_id);

        // Assert
        assertEquals(expectedFans, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testAddFan_checkIfFanIsAdded() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        Power_Plug_Object expectedFan = new Power_Plug_Object("testfan", "testfan");
        when(postgreSQLJDBC.addVentilator(any(), anyString(), any(Power_Plug_Object.class))).thenReturn(expectedFan);

        // Act
        ResponseEntity<Power_Plug_Object> actualResponse = restController.addVentilator("testroom", new Power_Plug_Object());

        // Assert
        assertEquals(expectedFan, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testGetRoomFans_returnsFan() {
        // Arrange
        Power_Plug_Object expectedFan = new Power_Plug_Object("testfan", "testfan");
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.getVentilator(any(Connection.class), anyString())).thenReturn(expectedFan);
        RESTController restController = new RESTController();
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String plug_id = "testfan";

        // Act
        ResponseEntity<Power_Plug_Object> actualResponse = restController.getRoomVentilator(room_id, plug_id);

        // Assert
        assertEquals(expectedFan, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testDeleteFan_returnsOk() {
        // Arrange
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.deleteVentilator(any(Connection.class), anyString())).thenReturn(true);
        RESTController restController = new RESTController();
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String plug_id = "testfan";

        // Act
        ResponseEntity<String> actualResponse = restController.deleteVentilator(room_id, plug_id);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testUpdateFan_returnsOk() {
        // Arrange
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.updateVentilator(any(Connection.class), anyString(), any(Update_PlugObject.class))).thenReturn(true);
        RESTController restController = new RESTController();
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String plug_id = "testfan";
        Update_PlugObject update_plugObject = new Update_PlugObject();
        update_plugObject.setName("testfan");

        // Act
        ResponseEntity<Update_PlugObject> actualResponse = restController.updateVentilator(room_id, plug_id, update_plugObject);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(update_plugObject, actualResponse.getBody());
    }

    @Test
    public void testActivateFan() throws Exception {
        // Arrange
        Connection c = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        when(c.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();

        Power_Plug_Storing_Object plug_storing_object = new Power_Plug_Storing_Object();
        plug_storing_object.setTurnon(true);

        // Act
        boolean result = postgreSQLJDBC.activateVentilator(c, "testfan", plug_storing_object);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testGetFanOperations_returnsOk() {
        // Arrange
        Connection c = mock(Connection.class);
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.getVentilatorOperations(eq(c), anyString(), anyString()))
                .thenReturn(Arrays.asList(new Power_Plug_Operation_Object()));
        RESTController restController = new RESTController();
        restController.c = c;
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String plug_id = "testfan";

        // Act
        ResponseEntity<List<Power_Plug_Operation_Object>> actualResponse =
                restController.getVentilatorOperations(room_id, plug_id);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        List<Power_Plug_Operation_Object> plugOperations = actualResponse.getBody();
        assertNotNull(plugOperations);
        assertEquals(1, plugOperations.size());
    }

    //Door

    @Test
    public void testGetAllDoors_CheckIfReturnTypeIsCorrect() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "room_id";
        List<Door_Object> expectedDoors = new ArrayList<>();
        when(postgreSQLJDBC.getDoors(any(), eq(room_id))).thenReturn(expectedDoors);

        // Act
        ResponseEntity<List<Door_Object>> actualResponse = restController.getDoors(room_id);

        // Assert
        assertEquals(expectedDoors, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testAddDoor_checkIfDoorIsAdded() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        Door_Object expectedDoor = new Door_Object("testdoor", "testdoor");
        when(postgreSQLJDBC.addDoor(any(), anyString(), any(Door_Object.class))).thenReturn(expectedDoor);

        // Act
        ResponseEntity<Door_Object> actualResponse = restController.addDoors("testroom", new Door_Object());

        // Assert
        assertEquals(expectedDoor, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testGetRoomDoors_returnsDoor() {
        // Arrange
        Door_Object expectedDoor = new Door_Object("testdoor", "testdoor");
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.getDoor(any(Connection.class), anyString())).thenReturn(expectedDoor);
        RESTController restController = new RESTController();
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String door_id = "testdoor";

        // Act
        ResponseEntity<Door_Object> actualResponse = restController.getRoomDoor(room_id, door_id);

        // Assert
        assertEquals(expectedDoor, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testDeleteDoor_returnsOk() {
        // Arrange
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.deleteDoor(any(Connection.class), anyString())).thenReturn(true);
        RESTController restController = new RESTController();
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String door_id = "testdoor";

        // Act
        ResponseEntity<String> actualResponse = restController.deleteDoor(room_id, door_id);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testActivateDoor() throws Exception {
        // Arrange
        Connection c = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        when(c.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();

        Open_Door_Object open_door_object = new Open_Door_Object();
        open_door_object.setOpen(true);

        // Act
        boolean result = postgreSQLJDBC.openDoor(c, "testdoor", open_door_object);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testGetDoorOperations_returnsOk() {
        // Arrange
        Connection c = mock(Connection.class);
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.getDoorOperations(eq(c), anyString(), anyString()))
                .thenReturn(Arrays.asList(new Open_Door_Operation_Object()));
        RESTController restController = new RESTController();
        restController.c = c;
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String door_id = "testdoor";

        // Act
        ResponseEntity<List<Open_Door_Operation_Object>> actualResponse =
                restController.getDoorOperations(room_id, door_id);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        List<Open_Door_Operation_Object> doorOperations = actualResponse.getBody();
        assertNotNull(doorOperations);
        assertEquals(1, doorOperations.size());
    }

    //Windows

    @Test
    public void testGetAllWindows_CheckIfReturnTypeIsCorrect() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "room_id";
        List<Window_Object> expectedWindows = new ArrayList<>();
        when(postgreSQLJDBC.getWindows(any(), eq(room_id))).thenReturn(expectedWindows);

        // Act
        ResponseEntity<List<Window_Object>> actualResponse = restController.getWindows(room_id);

        // Assert
        assertEquals(expectedWindows, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testAddWindow_checkIfWindowIsAdded() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        Window_Object expectedWindow = new Window_Object("testwindow", "testwindow");
        when(postgreSQLJDBC.addWindow(any(), anyString(), any(Window_Object.class))).thenReturn(expectedWindow);

        // Act
        ResponseEntity<Window_Object> actualResponse = restController.addWindows("testroom", new Window_Object());

        // Assert
        assertEquals(expectedWindow, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testGetRoomWindows_returnsWindow() {
        // Arrange
        Window_Object expectedWindow = new Window_Object("testwindow", "testwindow");
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.getWindow(any(Connection.class), anyString())).thenReturn(expectedWindow);
        RESTController restController = new RESTController();
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String window_id = "testwindow";

        // Act
        ResponseEntity<Window_Object> actualResponse = restController.getRoomWindow(room_id, window_id);

        // Assert
        assertEquals(expectedWindow, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testDeleteWindow_returnsOk() {
        // Arrange
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.deleteWindow(any(Connection.class), anyString())).thenReturn(true);
        RESTController restController = new RESTController();
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String window_id = "testwindow";

        // Act
        ResponseEntity<String> actualResponse = restController.deleteWindow(room_id, window_id);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testActivateWindow() throws Exception {
        // Arrange
        Connection c = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        when(c.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();

        Open_Window_Object open_window_object = new Open_Window_Object();
        open_window_object.setOpen(true);

        // Act
        boolean result = postgreSQLJDBC.openWindow(c, "testwindow", open_window_object);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testGetWindowOperations_returnsOk() {
        // Arrange
        Connection c = mock(Connection.class);
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.getWindowOperations(eq(c), anyString(), anyString()))
                .thenReturn(Arrays.asList(new Open_Window_Operation_Object()));
        RESTController restController = new RESTController();
        restController.c = c;
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String room_id = "testroom";
        String window_id = "testwindow";

        // Act
        ResponseEntity<List<Open_Window_Operation_Object>> actualResponse =
                restController.getWindowOperations(room_id, window_id);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        List<Open_Window_Operation_Object> windowOperations = actualResponse.getBody();
        assertNotNull(windowOperations);
        assertEquals(1, windowOperations.size());
    }

    //AirQuality

    @Test
    public void addAirQuality_ValidAirQualityObject_ReturnsOkResponse() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        AirQuality_Properties_Object airQualityPropertiesObject = new AirQuality_Properties_Object("testroom","testdevice","testventilator",1000,"ppm",20,"C",50,"%","2022-01-01 12:00:00");
        when(postgreSQLJDBC.addAirQualityProperties(any(), eq(airQualityPropertiesObject))).thenReturn(airQualityPropertiesObject);

        // Act
        ResponseEntity<AirQuality_Properties_Object> response = restController.AddAirQuality(airQualityPropertiesObject);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(airQualityPropertiesObject, response.getBody());
    }

    @Test
    public void getAirQuality_ExistingRoomId_ReturnsOkResponse() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String roomId = "testroom";
        AirQuality_Properties_Object airQualityPropertiesObject = new AirQuality_Properties_Object("testroom","testdevice","testventilator",1000,"ppm",20,"C",50,"%","2022-01-01 12:00:00");
        when(postgreSQLJDBC.getAirQualityProperties(any(), eq(roomId))).thenReturn(airQualityPropertiesObject);

        // Act
        ResponseEntity<AirQuality_Properties_Object> response = restController.getAirQuality(roomId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(airQualityPropertiesObject, response.getBody());
    }

    @Test
    public void getAirQualityTemperature_ValidRoomId_ReturnsOkResponse() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String roomId = "testroom";
        AirQuality_Temperature_Object expectedAirQuality = new AirQuality_Temperature_Object(roomId, "testfan", 20, "C", "2022-01-01 00:00:00");
        when(postgreSQLJDBC.getAirQualityTemperature(any(), eq(roomId))).thenReturn(expectedAirQuality);

        // Act
        ResponseEntity<AirQuality_Temperature_Object> response = restController.getAirQualityTemperature(roomId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAirQuality, response.getBody());
    }

    @Test
    public void getAirQualityHumidity_ValidRoomId_ReturnsOkResponse() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String roomId = "testroom";
        AirQuality_Humidity_Object expectedAirQuality = new AirQuality_Humidity_Object(roomId, "testfan", 50, "%", "2022-01-01 00:00:00");
        when(postgreSQLJDBC.getAirQualityHumidity(any(), eq(roomId))).thenReturn(expectedAirQuality);

        // Act
        ResponseEntity<AirQuality_Humidity_Object> response = restController.getAirQualityHumidity(roomId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAirQuality, response.getBody());
    }

    @Test
    public void getAirQualityCo2_ValidRoomId_ReturnsOkResponse() {
        // Arrange
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        restController.postgreSQLJDBC = postgreSQLJDBC;
        String roomId = "testroom";
        AirQuality_Co2_Object expectedAirQuality = new AirQuality_Co2_Object(roomId, "testfan", 1000, "ppm", "2022-01-01 00:00:00");
        when(postgreSQLJDBC.getAirQualityCo2(any(), eq(roomId))).thenReturn(expectedAirQuality);

        // Act
        ResponseEntity<AirQuality_Co2_Object> response = restController.getAirQualityCo2(roomId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAirQuality, response.getBody());
    }
}
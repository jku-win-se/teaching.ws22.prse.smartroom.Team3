import at.jku.digitalTwin.PostgreSQLJDBC;
import at.jku.digitalTwin.RESTController;
import at.jku.digitalTwin.objects.Lights_Object;
import at.jku.digitalTwin.objects.PeopleInRoomObject;
import at.jku.digitalTwin.objects.Room_Object;

import at.jku.digitalTwin.objects.Update_RoomObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
        System.out.println("This works");
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
        Room_Object expectedRoom = new Room_Object("testroom", 100.0, "m2");
        RESTController restController = new RESTController();

        // Act
        ResponseEntity<Room_Object> actualResponse = restController.deleteRoom("testroom");

        // Assert
        if (expectedRoom == null) {
            assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        } else {
            assertEquals(expectedRoom, actualResponse.getBody());
            assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        }
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
    public void testAddPeopleInRoom_checkIfPeopleInRoomIsAdded() {
        // Arrange
        PeopleInRoomObject expectedPeopleInRoom = new PeopleInRoomObject("testroom", 100, Timestamp.valueOf(LocalDateTime.now()));
        Connection c = mock(Connection.class);
        RESTController restController = new RESTController();
        PostgreSQLJDBC postgreSQLJDBC = mock(PostgreSQLJDBC.class);
        when(postgreSQLJDBC.connect_to_db(anyString(), anyString(), anyString())).thenReturn(c);
        when(postgreSQLJDBC.addPeopleInRoomById(c, "testroom", expectedPeopleInRoom)).thenReturn(expectedPeopleInRoom);
        restController.db = postgreSQLJDBC;

        // Act
        ResponseEntity<PeopleInRoomObject> actualResponse = restController.addPeopleInRoom("testroom", expectedPeopleInRoom);

        // Assert
        assertEquals(expectedPeopleInRoom, actualResponse.getBody());
        assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
    }

    @Test
    public void testGetAllLights_returnsLights() throws SQLException {
        // Arrange
        Lights_Object expectedLight = new Lights_Object("testlight", "testname");
        Connection c = mock(Connection.class);
        RESTController restController = new RESTController();
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        List<Lights_Object> expectedLights = new ArrayList<>();
        expectedLights.add(expectedLight);

        when(c.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("lightid")).thenReturn(expectedLight.getLight_id());
        when(resultSet.getString("lightname")).thenReturn(expectedLight.getName());

        // Act
        ResponseEntity<List<Lights_Object>> actualResponse = restController.getAllLights("testroom");

        // Assert
        assertEquals(expectedLights, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void testAddLight_checkIfLightIsAdded() throws SQLException {
        // Arrange
        Lights_Object expectedLight = new Lights_Object("testlight", "testlight");
        RESTController restController = new RESTController();

        // Act
        ResponseEntity<Lights_Object> actualResponse = restController.addLights("testroom", expectedLight);

        // Assert
        if (expectedLight == null) {
            assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        } else {
            assertEquals(expectedLight, actualResponse.getBody());
            assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        }
    }


}
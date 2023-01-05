package at.jku.digitalTwin;

import at.jku.digitalTwin.Repositories.RoomRepository;
import at.jku.digitalTwin.objects.Room_Object;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RestApplicationTests {

	private Room_Object room_object;

	@Autowired
	private RoomRepository roomRepository;


	@BeforeEach
	void setUp() {
		room_object = new Room_Object("room1", 5.5, "m2");
	}

	@AfterEach
	void deleteRep () {
		roomRepository.deleteAll();
	}

//	@Test
//	void addRoom() {
//		RESTController restController = new RESTController(roomRepository, peopleInRoomRepository);
//
//		assertEquals(String.valueOf(room_object),
//				String.valueOf(restController.
//				addRoom(new Room_Object("room1",5.5, "m2")).
//				getBody()));
//
//		assertNotEquals(String.valueOf(room_object),
//				String.valueOf(restController.
//				addRoom(new Room_Object("room2",5.5, "m2")).
//				getBody()));
//
//		assertEquals(String.valueOf(room_object),
//				String.valueOf(restController.
//						getRoomId("room1").getBody()));
//	}
//
//	@Test
//	void getRoom() {
//		RESTController restController = new RESTController(roomRepository, peopleInRoomRepository);
//
//		Room_Object[] roomTest = new Room_Object[1];
//		roomTest[0] = new Room_Object("room1", 5.5, "m2");
//
//		assertNotEquals(Arrays.toString(roomTest),
//				String.valueOf(restController.getRooms().getBody()));
//
//		restController.addRoom(room_object);
//		assertEquals(Arrays.toString(roomTest),
//				String.valueOf(restController.getRooms().getBody()));
//	}
//
//	@Test
//	void updateRoom() {
//		RESTController restController = new RESTController(roomRepository, peopleInRoomRepository);
//		Room_Object roomTest = new Room_Object("room1",10, "m2");
//
//
//		assertEquals(String.valueOf(room_object),
//				String.valueOf(restController.
//						addRoom(new Room_Object("room1",5.5, "m2")).
//						getBody()));
//
//		Update_RoomObject update_roomObject = new Update_RoomObject(750, "dm2");
//		assertEquals(String.valueOf(update_roomObject),
//				String.valueOf(restController.updateRoom(roomTest.getRoom_id(),
//						new Update_RoomObject(750, "dm2"))
//						.getBody()));
//
//		assertNotEquals(String.valueOf(update_roomObject),
//				String.valueOf(restController.updateRoom(roomTest.getRoom_id(),
//						new Update_RoomObject(850, "dm2"))
//						.getBody()));
//
//		Room_Object updatedRoom = new Room_Object("room1", 850, "dm2");
//		assertEquals(String.valueOf(updatedRoom),
//				String.valueOf(restController.
//						getRoomId("room1").getBody()));
//
//	}
//
//
//	@Test
//	void deleteRoom () {
//		RESTController restController = new RESTController(roomRepository, peopleInRoomRepository);
//
//		restController.addRoom(new Room_Object("room1",5.5, "m2"));
//
//		assertEquals(String.valueOf(room_object),
//				String.valueOf(restController.getRoomId("room1").getBody()));
//
//		restController.deleteRoom("room1");
//
//		assertNull(restController.getRoomId("room1").getBody());
//	}

}

package at.jku.digitalTwin;

import at.jku.digitalTwin.objects.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RESTController {
    private final RoomRepository roomRepository;

    public RESTController(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/Rooms")
    ResponseEntity<List<Room_Object>> getRooms(){ return ResponseEntity.ok(roomRepository.findAll());}


    @PostMapping("/Rooms")
    ResponseEntity<Room_Object> addRoom(@RequestBody Room_Object room){
        roomRepository.save(room);
        return ResponseEntity.ok(room);
    }


    @GetMapping("/Rooms/{room_id}")
    ResponseEntity<Room_Object> getRoomId (@PathVariable String room_id){
        return ResponseEntity.ok(roomRepository.findById(room_id).orElse(null));
    }


    @PutMapping("Rooms/{room_id}")
    ResponseEntity<Room_Object> updateRoom(@PathVariable String room_id, @RequestBody Update_RoomObject update_roomObject){

        Room_Object room = roomRepository.findById(room_id).orElse(null);
        room.setRoom_size(update_roomObject.getRoom_size());
        room.setMeasurement_unit(update_roomObject.getMeasurement_unit());

        roomRepository.save(room);
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/Rooms/{room_id}")
    ResponseEntity<Room_Object> deleteRoom (@PathVariable String room_id){
        roomRepository.deleteById(room_id);
        return ResponseEntity.ok(null);
    }
    @GetMapping("/Rooms/{room_id}/PeopleInRoom")
    ResponseEntity<PeopleInRoomObject> getPeopleInRoom (@PathVariable String room_id){
        //search for room_id in DB and return People
        PeopleInRoomObject peopleInRoomObject = new PeopleInRoomObject(room_id,22);
        return ResponseEntity.ok(peopleInRoomObject);
    }
    @PostMapping("/Rooms/{room_id}/PeopleInRoom")
    ResponseEntity<PeopleInRoomObject> addRoom(@PathVariable String room_id, @RequestBody PeopleInRoomObject peopleInRoomObject){
        //set people count to db
        return ResponseEntity.ok(peopleInRoomObject);
    }
    @GetMapping("/Rooms/{room_id}/Lights")
    ResponseEntity<List<Lights_Object>> getLights(@PathVariable String room_id){
        //db lights with room_id
        List<Lights_Object> lights = new ArrayList<>();
        lights.add(new Lights_Object("Light1","TischlampeFROMSERVER"));
        return ResponseEntity.ok(lights);
    }
    @PostMapping("/Rooms/{room_id}/Lights")
    ResponseEntity<Lights_Object> getLights(@PathVariable String room_id,@RequestBody Lights_Object lights_object){
        //add light to db
        lights_object.setName("TischlampeFROMSERVER");
        return ResponseEntity.ok(lights_object);
    }
    @GetMapping("/Rooms/{room_id}/Lights/{light_id}")
    ResponseEntity<Lights_Object> getLight(@PathVariable String room_id,@PathVariable String light_id){
        //db light with id with room_id
        Lights_Object lights_object = new Lights_Object(light_id ,"LampeFROMSERVER");
        return ResponseEntity.ok(lights_object);
    }
}

package at.jku.digitalTwin;

import at.jku.digitalTwin.objects.Room_Object;
import at.jku.digitalTwin.objects.Update_RoomObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping(value = "/Rooms/{room_id}")
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

    @DeleteMapping(value = "/Rooms/{room_id}")
    ResponseEntity<Room_Object> deleteRoom (@PathVariable String room_id){
        roomRepository.deleteById(room_id);
        return ResponseEntity.ok(null);
    }
}

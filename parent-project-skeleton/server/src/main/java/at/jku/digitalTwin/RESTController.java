package at.jku.digitalTwin;

import at.jku.digitalTwin.objects.Room_Object;
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


    @PostMapping("Rooms")
    ResponseEntity<Room_Object> addRoom(String room_id, double room_size, String measurement_unit){

        Room_Object room = new Room_Object(room_id, room_size, measurement_unit);
        roomRepository.save(room);
        return ResponseEntity.ok(room);
    }


    @RequestMapping(value = "/Rooms/{room_id:.*}", method = RequestMethod.GET)
    ResponseEntity<Room_Object> getRoomId (@PathVariable String room_id){
        return ResponseEntity.ok(roomRepository.findById(room_id).orElse(null));
    }


    @PutMapping("Rooms/{room_id}")
    ResponseEntity<Room_Object> updateRoom(@PathVariable String room_id,
                                        @RequestParam double room_size,
                                        @RequestParam String measurement_unit){
        Room_Object room = roomRepository.findById(room_id).orElse(null);
        room.setRoom_size(room_size);
        room.setMeasurement_unit(measurement_unit);

        roomRepository.save(room);
        return ResponseEntity.ok(room);
    }

    @RequestMapping(value = "/Rooms/{room_id:.*}", method = RequestMethod.DELETE)
    ResponseEntity<Room_Object> deleteRoom (@PathVariable String room_id){
        roomRepository.deleteById(room_id);
        return ResponseEntity.ok(null);
    }
}

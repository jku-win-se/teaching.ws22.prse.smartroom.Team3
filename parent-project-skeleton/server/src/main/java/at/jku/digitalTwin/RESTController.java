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


    //#########Rooms

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
    ResponseEntity<PeopleInRoomObject> addPeopleInRoom(@PathVariable String room_id, @RequestBody PeopleInRoomObject peopleInRoomObject){
        //set people count to db
        return ResponseEntity.ok(peopleInRoomObject);
    }


    //#########Lights

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
    @DeleteMapping("/Rooms/{room_id}/Lights/{light_id}")
    ResponseEntity<String> deleteLight(@PathVariable String room_id,@PathVariable String light_id) {
        //db call to delete light
        return ResponseEntity.ok("Success");
    }
    @PutMapping("/Rooms/{room_id}/Lights/{light_id}")
    ResponseEntity<Update_LightObject> updateLight(@PathVariable String room_id,@PathVariable String light_id, @RequestBody Update_LightObject update_LightObject)
    {
        //update db
        update_LightObject.setName("FROMSERVER");
        return ResponseEntity.ok(update_LightObject);
    }
    @GetMapping("/Rooms/{room_id}/Lights/{light_id}/Activation")
    ResponseEntity<Light_Operation_Return_Object> activateLight(@PathVariable String room_id,@PathVariable String light_id){
        //db light with id with room_id
        Light_Operation_Return_Object light_operation_return_object = new Light_Operation_Return_Object();
        return ResponseEntity.ok(light_operation_return_object);
    }
    @PostMapping("/Rooms/{room_id}/Lights/{light_id}/Activation")
    ResponseEntity<String> activateLightPost(@PathVariable String room_id,@PathVariable String light_id, @RequestBody Light_Activation_Object light_activation_object) {
        //change in db
        return ResponseEntity.ok("Success");
    }
    @PostMapping("/Rooms/{room_id}/Lights/{light_id}/SetColor")
    ResponseEntity<String> ComplexactivateLight(@PathVariable String room_id,@PathVariable String light_id,@RequestBody Light_Operation_Object light_operation_object) {
        //db
        return ResponseEntity.ok("Success");
    }


    //#########Ventilator

    @GetMapping("Rooms/{room_id}/Ventilators")
    ResponseEntity<List<Power_Plug_Object>> getVentilators(@PathVariable String room_id){
        //db
        List<Power_Plug_Object> list = new ArrayList<>();
        list.add(new Power_Plug_Object("plug1","Deckenvent"));
        return ResponseEntity.ok(list);
    }

}

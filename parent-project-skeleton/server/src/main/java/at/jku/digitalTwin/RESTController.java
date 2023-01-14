package at.jku.digitalTwin;

import at.jku.digitalTwin.Repositories.LightRepository;
import at.jku.digitalTwin.Repositories.RoomRepository;
import at.jku.digitalTwin.objects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RESTController {
    private final RoomRepository roomRepository;
    private final LightRepository lightRepository;

    @Autowired
    public RESTController(RoomRepository roomRepository, LightRepository lightRepository) {
        this.roomRepository = roomRepository;
        this.lightRepository = lightRepository;
    }



    //#########Rooms

    @GetMapping("/Rooms")
    ResponseEntity<List<Room_Object>> getRooms(){ return ResponseEntity.ok(roomRepository.findAll());}


    @PostMapping("/Rooms")
    ResponseEntity<Room_Object> addRoom(@RequestBody Room_Object room){
        Room_Object room_object = new Room_Object(room.getRoom_id(), room.getRoom_size(), room.getMeasurement_unit());
//        room_object.setRoom_id(room.getRoom_id());
//        room_object.setRoom_size(room.getRoom_size());
//        room_object.setMeasurement_unit(room.getMeasurement_unit());
        roomRepository.save(room_object);
        return ResponseEntity.ok(room);
    }


    @GetMapping("/Rooms/{room_id}")
    ResponseEntity<Room_Object> getRoomId (@PathVariable String room_id){
        if (roomRepository.findById(room_id).isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(roomRepository.findById(room_id).orElse(null));
    }


    @PutMapping("Rooms/{room_id}")
    ResponseEntity<Update_RoomObject> updateRoom(@PathVariable String room_id, @RequestBody Update_RoomObject update_roomObject){

        if (roomRepository.findById(room_id).isEmpty())
            return ResponseEntity.notFound().build();

        Room_Object room = roomRepository.findById(room_id).orElse(null);
        room.setRoom_size(update_roomObject.getRoom_size());
        room.setMeasurement_unit(update_roomObject.getMeasurement_unit());

        roomRepository.save(room);
        return ResponseEntity.ok(update_roomObject);
    }

    @DeleteMapping("/Rooms/{room_id}")
    ResponseEntity<Room_Object> deleteRoom (@PathVariable String room_id){
        if (roomRepository.findById(room_id).isEmpty())
            return ResponseEntity.notFound().build();

        System.out.println(roomRepository.findById(room_id));
        lightRepository.deleteAllInBatch(lightRepository.findByRoom(roomRepository.findById(room_id).get()));
        roomRepository.deleteById(room_id);

        return ResponseEntity.ok(null);
    }
    @GetMapping("/Rooms/{room_id}/PeopleInRoom")
    ResponseEntity<PeopleInRoomObject> getPeopleInRoom (@PathVariable String room_id){
        if (roomRepository.findById(room_id).isEmpty())
            return ResponseEntity.notFound().build();

        Room_Object room = roomRepository.findById(room_id).orElse(null);

        return ResponseEntity.ok(new PeopleInRoomObject(room.getRoom_id(), room.getPeople()));
    }

    @PostMapping("/Rooms/{room_id}/PeopleInRoom")
    ResponseEntity<PeopleInRoomObject> addPeopleInRoom(@PathVariable String room_id, @RequestBody PeopleInRoomObject peopleInRoomObject){
        if (roomRepository.findById(room_id).isEmpty())
            return ResponseEntity.notFound().build();

        Room_Object room = roomRepository.findById(room_id).orElse(null);
        room.setPeople(peopleInRoomObject.getPeople_count());
        roomRepository.save(room);

        return ResponseEntity.ok(peopleInRoomObject);
    }


    //#########Lights
    //noch keine Überprüfung der Werte, ob valide oder Room/Light vorhanden

    @GetMapping("/Rooms/{room_id}/Lights")
    ResponseEntity<List<Lights_Object>> getAllLights(@PathVariable String room_id){


        Room_Object room = roomRepository.findById(room_id).orElse(null);

        List<Lights_Object> lights = lightRepository.findByRoom(room);
        System.out.println("getAllLights: " + lights);

        return ResponseEntity.ok(lights);
    }
    @PostMapping("/Rooms/{room_id}/Lights")
    ResponseEntity<Lights_Object> addLights(@PathVariable String room_id,@RequestBody Lights_Object lights_object){

        Room_Object room = roomRepository.findById(room_id).orElse(null);

        lights_object.setRoom(room);

        System.out.println("lights_object: " + lights_object);
        lightRepository.save(lights_object);

        return ResponseEntity.ok(lights_object);
    }

    @GetMapping("/Rooms/{room_id}/Lights/{light_id}")
    ResponseEntity<Lights_Object> getRoomLight(@PathVariable String room_id,@PathVariable String light_id){

        Room_Object room = roomRepository.findById(room_id).orElse(null);
        Lights_Object light = lightRepository.findByIdAndRoom(light_id, room);

        System.out.println("getRoomLight: " + light);
        return ResponseEntity.ok(light);
    }



    @Transactional
    @DeleteMapping("/Rooms/{room_id}/Lights/{light_id}")
    ResponseEntity<String> deleteLight(@PathVariable String room_id,@PathVariable String light_id) {

        Room_Object room = roomRepository.findById(room_id).orElse(null);
        lightRepository.deleteByIdAndRoom(light_id, room);

        return ResponseEntity.ok("Success");
    }


    @PutMapping("/Rooms/{room_id}/Lights/{light_id}")
    ResponseEntity<Update_LightObject> updateLight(@PathVariable String room_id,@PathVariable String light_id, @RequestBody Update_LightObject update_LightObject)
    {
        Room_Object room = roomRepository.findById(room_id).orElse(null);
        Lights_Object light = lightRepository.findByIdAndRoom(light_id, room);

        light.setName(update_LightObject.getName());
        lightRepository.save(light);

        return ResponseEntity.ok(new Update_LightObject(light.getName()));
    }

    @PostMapping("/Rooms/{room_id}/Lights/{light_id}/Activation")
    ResponseEntity<Light_Activation_Object> activateLight(@PathVariable String room_id,@PathVariable String light_id, @RequestBody Light_Activation_Object light_activation_object) {

        Room_Object room = roomRepository.findById(room_id).orElse(null);
        Lights_Object light = lightRepository.findByIdAndRoom(light_id, room);

        light.setActive(light_activation_object.isTurnon());

        lightRepository.save(light);
        return ResponseEntity.ok(new Light_Activation_Object(light.isActive()));
    }


    @PostMapping("/Rooms/{room_id}/Lights/{light_id}/SetColor")
    ResponseEntity<Light_Operation_Object> SetColor(@PathVariable String room_id,@PathVariable String light_id,@RequestBody Light_Operation_Object light_operation_object) {

        Room_Object room = roomRepository.findById(room_id).orElse(null);
        Lights_Object light = lightRepository.findByIdAndRoom(light_id, room);
        light.setActive(light_operation_object.isTurnon());

        light.addLightOperation(light_operation_object, LocalDateTime.now());

        lightRepository.save(light);
        return ResponseEntity.ok(light_operation_object);
    }

    @GetMapping("/Rooms/{room_id}/Lights/{light_id}/Activation")
    ResponseEntity<List<Light_Operation_Return_Object>> getLightOperations(@PathVariable String room_id,@PathVariable String light_id){

        Room_Object room = roomRepository.findById(room_id).orElse(null);
        Lights_Object light = lightRepository.findByIdAndRoom(light_id, room);

        System.out.println(light.getLight_operations());

        return ResponseEntity.ok(light.getLight_operations());
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

package at.jku.digitalTwin;

import at.jku.digitalTwin.objects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.List;

@RestController
public class RESTController {

    PostgreSQLJDBC db = new PostgreSQLJDBC();
    Connection c = db.connect_to_db("SmartroomGruppe3", "postgres", "smartroom3");

    @Autowired
    public PostgreSQLJDBC postgreSQLJDBC;

    //#########Rooms

    @GetMapping("/Rooms")
    ResponseEntity<List<Room_Object>> getRooms() {
        List<Room_Object> allRooms = postgreSQLJDBC.getAllRooms(c, "room");
        db.getAllRooms(c, "room");
        return new ResponseEntity<>(allRooms, HttpStatus.OK);
    }

    @PostMapping("/Rooms")
    ResponseEntity<Room_Object> addRoom(@RequestBody Room_Object room) {
        Room_Object room_object = new Room_Object(room.getRoom_id(), room.getRoom_size(), room.getMeasurement_unit());
        room_object.setRoom_id(room.getRoom_id());
        room_object.setRoom_size(room.getRoom_size());
        room_object.setMeasurement_unit(room.getMeasurement_unit());
        db.add_room(c, "room", room.getRoom_id(), room.getRoom_size(), room.getMeasurement_unit());
        return ResponseEntity.ok(room);
    }

    @GetMapping("/Rooms/{room_id}")
    ResponseEntity<Room_Object> getRoomId(@PathVariable String room_id) {
        Room_Object room = postgreSQLJDBC.getRoomById(c, room_id);
        if (room == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/Rooms/{room_id}")
    ResponseEntity<Room_Object> deleteRoom(@PathVariable String room_id) {
        Room_Object deletedRoom = postgreSQLJDBC.deleteRoomById(c, room_id);
        if (deletedRoom == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedRoom, HttpStatus.OK);
    }

    @PatchMapping("Rooms/{room_id}")
    ResponseEntity<Update_RoomObject> updateRoom(@PathVariable String room_id, @RequestBody Update_RoomObject update_roomObject) {

        Room_Object updatedRoom = postgreSQLJDBC.updateRoomById(c, room_id, update_roomObject);
        if (updatedRoom == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(update_roomObject, HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/PeopleInRoom")
    ResponseEntity<PeopleInRoomObject> getPeopleInRoom(@PathVariable String room_id) {
        PeopleInRoomObject peopleInRoom = postgreSQLJDBC.getPeopleInRoomById(c, room_id);
        if (peopleInRoom == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(peopleInRoom, HttpStatus.OK);
    }


    @PostMapping("/Rooms/{room_id}/PeopleInRoom")
    ResponseEntity<PeopleInRoomObject> addPeopleInRoom(@PathVariable String room_id, @RequestBody PeopleInRoomObject peopleInRoomObject) {
        PeopleInRoomObject addedPeopleInRoom = postgreSQLJDBC.addPeopleInRoomById(c, room_id, peopleInRoomObject);
        if (addedPeopleInRoom == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(addedPeopleInRoom, HttpStatus.CREATED);
    }

    //#########Lights

    @GetMapping("/Rooms/{room_id}/Lights")
    public ResponseEntity<List<Lights_Object>> getAllLights(@PathVariable String room_id) {
        List<Lights_Object> lights = postgreSQLJDBC.getLightsByRoomId(c, room_id);
        if (lights == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lights, HttpStatus.OK);
    }

    @PostMapping("/Rooms/{room_id}/Lights")
    public ResponseEntity<Lights_Object> addLights(@PathVariable String room_id, @RequestBody Lights_Object lights_object) {
        lights_object.setRoom_id(room_id);
        Lights_Object addedLight = postgreSQLJDBC.addLight(c, lights_object);
        if (addedLight == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(addedLight, HttpStatus.CREATED);
    }

    @GetMapping("/Rooms/{room_id}/Lights/{light_id}")
    public ResponseEntity<Lights_Object> getRoomLight(@PathVariable String room_id, @PathVariable String light_id) {
        Lights_Object light = postgreSQLJDBC.getLightById(c, light_id);
        if (light == null || !light.getRoom_id().equals(room_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(light, HttpStatus.OK);
    }

    @DeleteMapping("/Rooms/{room_id}/Lights/{light_id}")
    public ResponseEntity<String> deleteLight(@PathVariable String room_id, @PathVariable String light_id) {
        boolean isDeleted = postgreSQLJDBC.deleteLightById(c, light_id);
        if (!isDeleted) {
            return new ResponseEntity<>("Failed to delete light with id: " + light_id, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Light with id: " + light_id + " is deleted successfully", HttpStatus.OK);
    }

    @PatchMapping ("/Rooms/{room_id}/Lights/{light_id}")
    public ResponseEntity<Update_LightObject> updateLight(@PathVariable String room_id, @PathVariable String light_id, @RequestBody Update_LightObject update_LightObject) {
        Lights_Object updatedLight = postgreSQLJDBC.updateLightById(c, light_id, update_LightObject);
        if (updatedLight == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (!updatedLight.getRoom_id().equals(room_id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(update_LightObject, HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/Lights/{light_id}/Activation")
    public ResponseEntity<List<Light_Operation_Return_Object>> getLightOperations(@PathVariable String room_id, @PathVariable String light_id) {
        List<Light_Operation_Return_Object> lightOperations = postgreSQLJDBC.getLightOperations(c, room_id, light_id);
        if (lightOperations == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lightOperations, HttpStatus.OK);
    }

    @PostMapping("/Rooms/{room_id}/Lights/{light_id}/Activation")
    public ResponseEntity<String> activateLight(@PathVariable String room_id, @PathVariable String light_id, @RequestBody Light_Activation_Object light_activation_object) {
        boolean isActivated = postgreSQLJDBC.activateLight(c, room_id, light_id, light_activation_object);
        if (!isActivated) {
            return new ResponseEntity<>("Light activation failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Light activation successful", HttpStatus.OK);
    }

    @PostMapping("/Rooms/{room_id}/Lights/{light_id}/SetColor")
    public ResponseEntity<Light_Operation_Object> SetColor(@PathVariable String room_id, @PathVariable String light_id, @RequestBody Light_Operation_Object light_operation_object) {
        Light_Operation_Object setColor = postgreSQLJDBC.setColor(c, room_id, light_id, light_operation_object);
        if (setColor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(setColor, HttpStatus.OK);
    }

    //#########Ventilator

    @GetMapping("Rooms/{room_id}/Ventilators")
    public ResponseEntity<List<Power_Plug_Object>> getVentilators(@PathVariable String room_id) {
        List<Power_Plug_Object> ventilators = postgreSQLJDBC.getVentilators(c, room_id);
        if (ventilators == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ventilators, HttpStatus.OK);
    }

    @PostMapping("Rooms/{room_id}/Ventilators")
    public ResponseEntity<List<Power_Plug_Object>> addVentilator(@PathVariable String room_id, @RequestBody Power_Plug_Object powerPlugObject) {
        List<Power_Plug_Object> ventilators = postgreSQLJDBC.addVentilator(c, room_id, powerPlugObject);
        if (ventilators == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ventilators, HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/Ventilators/{plug_id}")
    public ResponseEntity<Power_Plug_Object> getRoomVentilator(@PathVariable String room_id, @PathVariable String plug_id) {
        Power_Plug_Object ventilator = postgreSQLJDBC.getVentilator(c, plug_id);
        if (ventilator == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ventilator, HttpStatus.OK);
    }

    @DeleteMapping("/Rooms/{room_id}/Ventilators/{plug_id}")
    public ResponseEntity<String> deleteVentilator(@PathVariable String room_id, @PathVariable String plug_id) {
        boolean isDeleted = postgreSQLJDBC.deleteVentilator(c, plug_id);
        if (isDeleted) {
            return new ResponseEntity<>("Ventilator with id " + plug_id + " has been deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Ventilator with id " + plug_id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping ("/Rooms/{room_id}/Ventilators/{plug_id}")
    public ResponseEntity<Update_PlugObject> updateVentilator(@PathVariable String room_id, @PathVariable String plug_id, @RequestBody Update_PlugObject update_plugObject) {
        boolean isUpdated = postgreSQLJDBC.updateVentilator(c, plug_id, update_plugObject);
        if (isUpdated) {
            return new ResponseEntity<>(update_plugObject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/Rooms/{room_id}/Ventilators/{plug_id}/Activation")
    public ResponseEntity<String> activateVentilator(@PathVariable String room_id, @PathVariable String plug_id, @RequestBody Plug_Activation_Object plug_activation_object) {
        boolean isActivated = postgreSQLJDBC.activateVentilator(c, room_id, plug_id);
        if (!isActivated) {
            return new ResponseEntity<>("Ventilator activation failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Ventilator activation successful", HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/Ventilators/{plug_id}/Activation")
    public ResponseEntity<List<Power_Plug_Operation_Object>> getVentilatorOperations(@PathVariable String room_id, @PathVariable String plug_id) {
        List<Power_Plug_Operation_Object> ventilatorOperations = postgreSQLJDBC.getVentilatorOperations(c, room_id, plug_id);
        if (ventilatorOperations == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ventilatorOperations, HttpStatus.OK);
    }
}

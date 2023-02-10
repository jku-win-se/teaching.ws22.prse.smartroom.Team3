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

    public PostgreSQLJDBC db = new PostgreSQLJDBC();
    public Connection c = db.connect_to_db("SmartroomGruppe3", "postgres", "smartroom3");

    @Autowired
    public PostgreSQLJDBC postgreSQLJDBC;

    //#########Rooms

    @GetMapping("/Rooms")
    public ResponseEntity<List<Room_Object>> getRooms() {
        List<Room_Object> allRooms = postgreSQLJDBC.getAllRooms(c, "room");
        db.getAllRooms(c, "room");
        return new ResponseEntity<>(allRooms, HttpStatus.OK);
    }

    @PostMapping("/Rooms")
    public ResponseEntity<Room_Object> addRoom(@RequestBody Room_Object room) {
        Room_Object room_object = new Room_Object(room.getRoom_id(), room.getRoom_size(), room.getMeasurement_unit());
//        room_object.setRoom_id(room.getRoom_id());
//        room_object.setRoom_size(room.getRoom_size());
//        room_object.setMeasurement_unit(room.getMeasurement_unit());
        db.add_room(c, "room", room.getRoom_id(), room.getRoom_size(), room.getMeasurement_unit());
        return ResponseEntity.ok(room);
    }

    @GetMapping("/Rooms/{room_id}")
    public ResponseEntity<Room_Object> getRoomId(@PathVariable String room_id) {
        Room_Object room = postgreSQLJDBC.getRoomById(c, room_id);
        if (room == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/Rooms/{room_id}")
    public ResponseEntity<Room_Object> deleteRoom(@PathVariable String room_id) {
        Room_Object deletedRoom = db.deleteRoomById(c, room_id);
        if (deletedRoom == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedRoom, HttpStatus.OK);
    }

    @PutMapping("Rooms/{room_id}")
    public ResponseEntity<Update_RoomObject> updateRoom(@PathVariable String room_id, @RequestBody Update_RoomObject update_roomObject) {

        Room_Object updatedRoom = postgreSQLJDBC.updateRoomById(c, room_id, update_roomObject);
        if (updatedRoom == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(update_roomObject, HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/PeopleInRoom")
    public ResponseEntity<PeopleInRoomObject> getPeopleInRoom(@PathVariable String room_id) {
        PeopleInRoomObject peopleInRoom = postgreSQLJDBC.getPeopleInRoomById(c, room_id);
        if (peopleInRoom == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(peopleInRoom, HttpStatus.OK);
    }


    @PostMapping("/Rooms/{room_id}/PeopleInRoom")
    public ResponseEntity<PeopleInRoomObject> addPeopleInRoom(@PathVariable String room_id, @RequestBody PeopleInRoomObject peopleInRoomObject) {
        PeopleInRoomObject addedPeopleInRoom = db.addPeopleInRoomById(c, room_id, peopleInRoomObject);
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
        Lights_Object addedLight = postgreSQLJDBC.addLight(c, room_id, lights_object);
        if (addedLight == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(addedLight, HttpStatus.CREATED);
    }

    @GetMapping("/Rooms/{room_id}/Lights/{light_id}")
    public ResponseEntity<Lights_Object> getRoomLight(@PathVariable String room_id, @PathVariable String light_id) {
        Lights_Object light = postgreSQLJDBC.getLightById(c, room_id, light_id);
        if (light == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(light, HttpStatus.OK);
    }

    @DeleteMapping("/Rooms/{room_id}/Lights/{light_id}")
    public ResponseEntity<String> deleteLight(@PathVariable String room_id, @PathVariable String light_id) {
        boolean isDeleted = postgreSQLJDBC.deleteLightById(c, room_id, light_id);
        if (!isDeleted) {
            return new ResponseEntity<>("Failed to delete light with id: " + light_id, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Light with id: " + light_id + " is deleted successfully", HttpStatus.OK);
    }

    @PatchMapping ("/Rooms/{room_id}/Lights/{light_id}")
    public ResponseEntity<Update_LightObject> updateLight(@PathVariable String room_id, @PathVariable String light_id, @RequestBody Update_LightObject update_LightObject) {
        Lights_Object updatedLight = postgreSQLJDBC.updateLightById(c, room_id, light_id, update_LightObject);
        if (updatedLight == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Power_Plug_Object> addVentilator(@PathVariable String room_id, @RequestBody Power_Plug_Object powerPlugObject) {
        Power_Plug_Object ventilators = postgreSQLJDBC.addVentilator(c, room_id, powerPlugObject);
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
    public ResponseEntity<String> activateVentilator(@PathVariable String room_id, @PathVariable String plug_id, @RequestBody Power_Plug_Storing_Object powerPlug_storing_object) {
        System.out.println("rest activate vent " + powerPlug_storing_object.isTurnon());
        boolean isActivated = postgreSQLJDBC.activateVentilator(c, plug_id, powerPlug_storing_object);
        if (!isActivated) {
            return new ResponseEntity<>("Ventilator activation failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Ventilator activation successful", HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/Ventilators/{plug_id}/Activation")
    public ResponseEntity<List<Power_Plug_Operation_Object>> getVentilatorOperations(@PathVariable String room_id, @PathVariable String plug_id) {
        List<Power_Plug_Operation_Object> ventilatorOperations = postgreSQLJDBC.getVentilatorOperations(c, room_id, plug_id);
        System.out.println("List:"+ventilatorOperations.toString());
        if (ventilatorOperations == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ventilatorOperations, HttpStatus.OK);
    }

    //#########Door

    @GetMapping("Rooms/{room_id}/Doors")
    public ResponseEntity<List<Door_Object>> getDoors(@PathVariable String room_id) {
        List<Door_Object> doors = postgreSQLJDBC.getDoors(c, room_id);
        if (doors == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doors, HttpStatus.OK);
    }

    @PostMapping("Rooms/{room_id}/Doors")
    public ResponseEntity<Door_Object> addDoors(@PathVariable String room_id, @RequestBody Door_Object doorObject) {
        Door_Object doors = postgreSQLJDBC.addDoor(c, room_id, doorObject);
        if (doors == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doors, HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/Doors/{door_id}")
    public ResponseEntity<Door_Object> getRoomDoor(@PathVariable String room_id, @PathVariable String door_id) {
        Door_Object door = postgreSQLJDBC.getDoor(c, door_id);
        if (door == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(door, HttpStatus.OK);
    }

    @DeleteMapping("/Rooms/{room_id}/Doors/{door_id}")
    public ResponseEntity<String> deleteDoor(@PathVariable String room_id, @PathVariable String door_id) {
        boolean isDeleted = postgreSQLJDBC.deleteDoor(c, door_id);
        if (isDeleted) {
            return new ResponseEntity<>("Door with id " + door_id + " has been deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Door with id " + door_id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    //Rest von Door

    @PostMapping("/Rooms/{room_id}/Doors/{door_id}/Open")
    public ResponseEntity<String> openDoor (@PathVariable String room_id, @PathVariable String door_id, @RequestBody Open_Door_Object openDoorObject) {
        System.out.println("rest door door " + openDoorObject.isOpen());
        boolean succeeded = postgreSQLJDBC.openDoor(c, door_id, openDoorObject);
        if (!succeeded) {
            return new ResponseEntity<>("Ventilator activation failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Ventilator activation successful", HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/Doors/{door_id}/Open")
    public ResponseEntity<List<Open_Door_Operation_Object>> getDoorOperations(@PathVariable String room_id, @PathVariable String door_id) {
        List<Open_Door_Operation_Object> doorOperations = postgreSQLJDBC.getDoorOperations(c, room_id, door_id);
        if (doorOperations == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doorOperations, HttpStatus.OK);
    }









    //#########Window

    @GetMapping("Rooms/{room_id}/Windows")
    public ResponseEntity<List<Window_Object>> getWindows(@PathVariable String room_id) {
        List<Window_Object> windows = postgreSQLJDBC.getWindows(c, room_id);
        if (windows == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(windows, HttpStatus.OK);
    }

    @PostMapping("Rooms/{room_id}/Windows")
    public ResponseEntity<Window_Object> addWindows(@PathVariable String room_id, @RequestBody Window_Object windowObject) {
        Window_Object windows = postgreSQLJDBC.addWindow(c, room_id, windowObject);
        if (windows == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(windows, HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/Windows/{window_id}")
    public ResponseEntity<Window_Object> getRoomWindow(@PathVariable String room_id, @PathVariable String window_id) {
        Window_Object window = postgreSQLJDBC.getWindow(c, window_id);
        if (window == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(window, HttpStatus.OK);
    }

    @DeleteMapping("/Rooms/{room_id}/Windows/{window_id}")
    public ResponseEntity<String> deleteWindow(@PathVariable String room_id, @PathVariable String window_id) {
        boolean isDeleted = postgreSQLJDBC.deleteWindow(c, window_id);
        if (isDeleted) {
            return new ResponseEntity<>("Door with id " + window_id + " has been deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Door with id " + window_id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/Rooms/{room_id}/Windows/{window_id}/Open")
    public ResponseEntity<String> openWindow (@PathVariable String room_id, @PathVariable String window_id, @RequestBody Open_Window_Object openWindowObject) {
        System.out.println("rest open window " + openWindowObject.isOpen());
        boolean succeeded = postgreSQLJDBC.openWindow(c, window_id, openWindowObject);
        if (!succeeded) {
            return new ResponseEntity<>("Ventilator activation failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Ventilator activation successful", HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/Windows/{window_id}/Open")
    public ResponseEntity<List<Open_Window_Operation_Object>> getWindowOperations(@PathVariable String room_id, @PathVariable String window_id) {
        List<Open_Window_Operation_Object> windowOperations = postgreSQLJDBC.getWindowOperations(c, room_id, window_id);
        if (windowOperations == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(windowOperations, HttpStatus.OK);
    }


    //AirQuality
    @PostMapping("/Room/AirQuality/")
    public ResponseEntity<AirQuality_Properties_Object> AddAirQuality (@RequestBody AirQuality_Properties_Object airQualityPropertiesObject) {
        AirQuality_Properties_Object airQuality = postgreSQLJDBC.addAirQualityProperties(c, airQualityPropertiesObject);
        if (airQuality == null) {
            return new ResponseEntity<>(airQuality, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(airQuality, HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/AirQuality")
    public ResponseEntity<AirQuality_Properties_Object> getAirQuality (@PathVariable String room_id) {
        AirQuality_Properties_Object airQuality = postgreSQLJDBC.getAirQualityProperties(c, room_id);
        if (airQuality == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(airQuality, HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/AirQuality/temperature/")
    public ResponseEntity<AirQuality_Temperature_Object> getAirQualityTemperature (@PathVariable String room_id) {
        AirQuality_Temperature_Object airQuality = postgreSQLJDBC.getAirQualityTemperature(c, room_id);
        if (airQuality == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(airQuality, HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/AirQuality/humidity/")
    public ResponseEntity<AirQuality_Humidity_Object> getAirQualityHumidity (@PathVariable String room_id) {
        AirQuality_Humidity_Object airQuality = postgreSQLJDBC.getAirQualityHumidity(c, room_id);
        if (airQuality == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(airQuality, HttpStatus.OK);
    }

    @GetMapping("/Rooms/{room_id}/AirQuality/co2/")
    public ResponseEntity<AirQuality_Co2_Object> getAirQualityCo2 (@PathVariable String room_id) {
        AirQuality_Co2_Object airQuality = postgreSQLJDBC.getAirQualityCo2(c, room_id);
        if (airQuality == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(airQuality, HttpStatus.OK);
    }


}

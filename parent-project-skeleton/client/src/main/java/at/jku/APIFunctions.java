package at.jku;

import at.jku.objects.*;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public interface APIFunctions {

    //Rooms
    public List<Room_Object> getRooms();
    public Room_Object addRoom(String room_id, double room_size, String measurment_unit);
    public Room_Object getRoomID (String id);
    public Update_RoomObject updateRoom (String id, double room_size, String measurment_unit);
    public HttpResponse deleteRoom (String id);
    public PeopleInRoomObject getPeopleCount (String id);
    public PeopleInRoomObject addPeopleRoom (String id,int peopleCount);

//    Lights
    public List<Lights_Object> getAllLights (String roomId) throws IOException, InterruptedException;
    public Lights_Object addLight (String roomId, String light_id, String name);
    public Lights_Object getRoomLight (String roomId, String lightId);
    public Update_LightObject updateLight (String roomId, String lightId, String name);
    public boolean deleteRoomLight (String roomId, String lightId);
    public boolean getCurrentLightStatus(String roomId,String lightId);
    public Light_Activation_Object activateLight(String roomId, String lightId, Boolean turnon) ;
    public Light_Operation_Object setColor(String roomId, String lightId, Boolean turnon, int bright, String hex);

// Ventilators
        public List<Power_Plug_Object> getAllVents(String roomId);
        public Power_Plug_Object addVent(String roomId, String plug_id, String name);
        public Power_Plug_Object getSpecificVent(String roomId, String plug_id) ;
    public Power_Plug_Update_Object updateVent (String roomId, String plug_id, String name);
    public boolean deleteVent(String roomId, String plug_id);
    public boolean activateVent(String roomId, String plug_id, Boolean turnon);
    public Power_Plug_Operation_Object getCurrentPowerPlugStatus(String roomId,String plug_id);
   public boolean activateVent(String roomId, String plug_id);


//    doors
    public List<Door_Object> getAllRoomDoor(String roomId);
    public Door_Object addRoomDoor(String roomId,String door_id,String name);
    public Door_Object getRoomDoor(String roomId, String doorId);
    public Door_Object updateDoor (String roomId, String door_id, String name);
    public boolean getDoorStatus(String room_id, String door_id);
    public boolean changeDoorStatus(String room_id, String door_id,Boolean isOpen);


//    windows
    public List<Window_Object> getAllRoomWindows(String roomId);
    public Window_Object addRoomWindow(String roomId,String window_id,String name);
    public Window_Object getRoomWindow(String roomId, String window_id);
    public Window_Object updateWindow (String roomId, String window_id, String name);
    public boolean getWindowStatus(String room_id, String window_id);
    public boolean changeWindowStatus(String room_id, String window_id,Boolean isOpen);


//    AirQuality
public AirQuality_Properties_Object getAirQ(String room_id);
    public AirQuality_Co2_Object getAirQCo2(String room_id);
    public AirQuality_Temperature_Object getAirQTemperature(String room_id);
//    public HttpResponse getRoomAQHum (String roomId);
//    public HttpResponse getRoomAQCo2 (String roomId);
}

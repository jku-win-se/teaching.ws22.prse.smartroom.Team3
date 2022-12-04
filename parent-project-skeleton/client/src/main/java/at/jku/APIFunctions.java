package at.jku;

import java.net.http.HttpResponse;

public interface APIFunctions {

    //Rooms
    public HttpResponse getRooms();
    public HttpResponse addRoom(String room_id, double room_size, String measurment_unit);
    public HttpResponse getRoomID (String id);
    public HttpResponse updateRoom (String id, double room_size, String measurment_unit);
    public HttpResponse deleteRoom (String id);
//    public HttpResponse getPeopleCount (String id);
//    public HttpResponse addPeopleRoom (String id);
//
//
//    //Lights
//    public HttpResponse getAllLights (String roomId);
//    public HttpResponse addLight (String roomId);
//    public HttpResponse getRoomLight (String roomId, String lightId);
//    public HttpResponse updateRoomLight (String roomId, String lightId);
//    public HttpResponse deleteRoomLight (String roomId, String lightId);
//    public HttpResponse getRoomLightStatus (String roomId, String lightId);
//    public HttpResponse activateRoomLight (String roomId, String lightId);
//    public HttpResponse setRoomLightColor (String roomId, String lightId);
//
//
//    //Ventilators
//    public HttpResponse getAllRoomVents (String roomId);
//    public HttpResponse addVent (String roomId);
//    public HttpResponse getRoomVent (String roomId, String plugId);
//    public HttpResponse updateRoomPlug (String roomId, String plugId);
//    public HttpResponse deleteRoomPlug (String roomId, String plugId);
//    public HttpResponse operateRoomPlug (String roomId, String plugId);
//    public HttpResponse getVentDetails (String roomId, String plugId);
//    public HttpResponse activateRoomPlug (String roomId, String plugId);
//
//
//    //doors
//    public HttpResponse getAllRoomDoor (String roomId);
//    public HttpResponse addRoomDoor (String roomId);
//    public HttpResponse getRoomDoor (String roomId, String doorId);
//    public HttpResponse updateRoomDoor (String roomId, String doorId);
//    public HttpResponse getOpenRoomDoor (String roomId, String doorId);
//    public HttpResponse openRoomDoor (String roomId, String doorId);
//
//
//    //windows
//    public HttpResponse getAllRoomWindow (String roomId);
//    public HttpResponse addRoomWindow (String roomId);
//    public HttpResponse getRoomWindow (String roomId, String doorId);
//    public HttpResponse updateRoomWindow (String roomId, String doorId);
//    public HttpResponse getOpenRoomWindow (String roomId, String doorId);
//    public HttpResponse openRoomWindow (String roomId, String doorId);
//
//
//    //AirQuality
//    public HttpResponse addRoomAQ ();
//    public HttpResponse getRoomAQ (String roomId);
//    public HttpResponse getRoomAQTemp (String roomId);
//    public HttpResponse getRoomAQHum (String roomId);
//    public HttpResponse getRoomAQCo2 (String roomId);
}

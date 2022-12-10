package at.jku;

import at.jku.objects.Lights_Object;
import at.jku.objects.PeopleInRoomObject;
import at.jku.objects.Room_Object;
import at.jku.objects.Update_RoomObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Client implements APIFunctions{
    static final String startURI = "http://localhost:8080";
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Room_Object> getRooms() {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms")).GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Room_Object> room_objects = objectMapper.readValue(response.body(), new TypeReference<List<Room_Object>>(){});
            return room_objects;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Room_Object addRoom(String room_id, int room_size, String measurement_unit) {
    Room_Object room = new Room_Object();
    room.setRoom_id(room_id);
    room.setRoom_size(room_size);
    room.setMeasurement_unit(measurement_unit);
        String body = "";
        try {
            body = objectMapper.writeValueAsString(room);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms")).
                         header("Content-Type", "application/json").
                        POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Room_Object room_object = objectMapper.readValue(response.body(), new TypeReference<Room_Object>(){});
            return room_object;//or true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public HttpResponse getRoomID(String room_id) {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id)).GET().build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpResponse updateRoom(String room_id, int room_size, String measurement_unit) {
        Update_RoomObject update_roomObject = new Update_RoomObject(room_size,measurement_unit);
        String body="";
        try {
            body = objectMapper.writeValueAsString(update_roomObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id )).
                        header("Content-Type", "application/json").
                        PUT(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpResponse deleteRoom(String room_id) {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id)).DELETE().build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpResponse getPeopleCount(String room_id) {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id + "/PeopleInRoom")).GET().build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpResponse addPeopleRoom(String room_id, int peopleCount) {
        PeopleInRoomObject peopleInRoomObject = new PeopleInRoomObject(room_id,peopleCount);
        String body = "";
        try {
            body = objectMapper.writeValueAsString(peopleInRoomObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id + "/PeopleInRoom")).
                        header("Content-Type", "application/json").
                        POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public HttpResponse getAllLights(String roomId) {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/"+roomId+"/Lights")). header("Content-Type", "application/json").GET().build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpResponse addLight(String roomId, String light_id, String name) {
        Lights_Object lights_object = new Lights_Object(light_id,name);
        String body = "";
        try {
            body = objectMapper.writeValueAsString(lights_object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/"+roomId+"/Lights")).
                        header("Content-Type", "application/json").
                        POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public HttpResponse getRoomLight(String roomId, String lightId) {

            HttpRequest request =
                    HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Lights/"+lightId)).header("Content-Type", "application/json").GET().build();

            try {
                return client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
    }


}

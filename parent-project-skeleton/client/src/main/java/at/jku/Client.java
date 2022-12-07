package at.jku;

import at.jku.objects.Room_Object;
import at.jku.objects.Update_RoomObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client implements APIFunctions{
    static final String startURI = "http://localhost:8080";
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public HttpResponse getRooms() {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms")).GET().build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpResponse addRoom(String room_id, int room_size, String measurement_unit) {
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
            return client.send(request, HttpResponse.BodyHandlers.ofString());
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
}

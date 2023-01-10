package at.jku;

import at.jku.clientObjects.*;
import at.jku.objects.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
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
            return objectMapper.readValue(response.body(), new TypeReference<List<Room_Object>>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Room_Object addRoom(String room_id, double room_size, String measurement_unit) {

        Room_Object room = new Room_Object(room_id, room_size, measurement_unit);
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
    public Room_Object getRoomID(String room_id) {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id)).GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<Room_Object>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Update_RoomObject updateRoom(String room_id, double room_size, String measurement_unit) {
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
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            Update_RoomObject room_object = objectMapper.readValue(response.body(), new TypeReference<Update_RoomObject>(){});
            return room_object;
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
    public PeopleInRoomObject getPeopleCount(String room_id) {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id + "/PeopleInRoom")).GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<PeopleInRoomObject>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PeopleInRoomObject addPeopleRoom(String room_id, int peopleCount) {
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
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<PeopleInRoomObject>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Lights_Object> getAllLights(String roomId) {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(startURI + "/Rooms/"+roomId+"/Lights"))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();

//        HttpRequest request =
//                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/"+roomId+"/Lights")).GET().build();
        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            ObjectMapper mapper = new ObjectMapper();
            List<Lights_Object> lights = mapper.readValue(body, new TypeReference<List<Lights_Object>>()
            {});
            return lights;
            //return objectMapper.readValue(response.body(), new TypeReference<List<Lights_Object>>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Lights_Object addLight(String roomId, String light_id, String name) {
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
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public Lights_Object getRoomLight(String roomId, String lightId) {

            HttpRequest request =
                    HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Lights/"+lightId)).header("Content-Type", "application/json").GET().build();

            try {
                HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
                return objectMapper.readValue(response.body(), new TypeReference<Lights_Object>(){});
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
    }


    @Override
    public HttpResponse deleteRoomLight(String roomId, String lightId) {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Lights/"+lightId)).DELETE().build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Update_LightObject updateLight (String roomId, String lightId, String name) {
        Update_LightObject update_lightObject = new Update_LightObject(name);
        String body="";
        try {
            body = objectMapper.writeValueAsString(update_lightObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Lights/"+lightId )).
                        header("Content-Type", "application/json").
                        PUT(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            Update_LightObject light_object = objectMapper.readValue(response.body(), new TypeReference<>(){});
            return light_object;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Light_Activation_Object activateLight(String roomId, String lightId, Boolean turnon) {
        Light_Activation_Object activation = new Light_Activation_Object(turnon);
        String body = "";
        try {
            body = objectMapper.writeValueAsString(activation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Lights/"+lightId + "/Activation")).
                        header("Content-Type", "application/json").
                        POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
    public Light_Operation_Object getCurrentLightStatus(String roomId,String lightId){
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Lights/"+lightId+"/Activation")).GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Light_Operation_Object> l= objectMapper.readValue(response.body(), new TypeReference<>() {});
            return l.get(l.size());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Light_Operation_Object setColor(String roomId, String lightId, Boolean turnon, int bright, String hex) {
        Light_Operation_Object activation = new Light_Operation_Object(turnon, bright, hex);
        String body = "";
        try {
            body = objectMapper.writeValueAsString(activation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Lights/"+lightId + "/SetColor")).
                        header("Content-Type", "application/json").
                        POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

/*

Folgende Methoden sind in diesem Kommentarblock:


//    public List<Doors_Object> getAllRoomDoor (String roomId) throws IOException, InterruptedException;
//    public Doors_Object addRoomDoor (String roomId);
//    public Doors_Object getRoomDoor (String roomId, String doorId);
//    public List<Windows_Object> getAllRoomWindow (String roomId) throws IOException, InterruptedException;
//    public Windows_Object addRoomWindow (String roomId);
//    public Windows_Object getRoomWindow (String roomId, String windowId);

    @Override
    public List<Doors_Object> getAllRoomDoor(String roomId) {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(startURI + "/Rooms/"+roomId+"/Doors"))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();

//        HttpRequest request =
//                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/"+roomId+"/Doors")).GET().build();
        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            ObjectMapper mapper = new ObjectMapper();
            List<Doors_Object> doors = mapper.readValue(body, new TypeReference<List<Doors_Object>>()
            {});
            return doors;
            //return objectMapper.readValue(response.body(), new TypeReference<List<Doors_Object>>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Doors_Object addRoomDoor(String roomId) {
        Doors_Object doors_object = new Doors_Object();
        String body = "";
        try {
            body = objectMapper.writeValueAsString(doors_object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/"+roomId+"/Doors")).
                        header("Content-Type", "application/json").
                        POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Doors_Object getRoomDoor(String roomId, String doorId) {

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Doors/"+doorId)).header("Content-Type", "application/json").GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<Doors_Object>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Windows_Object> getAllRoomWindow(String roomId) {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(startURI + "/Rooms/"+roomId+"/Windows"))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();

//        HttpRequest request =
//                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/"+roomId+"/Windows")).GET().build();
        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            ObjectMapper mapper = new ObjectMapper();
            List<Windows_Object> windows = mapper.readValue(body, new TypeReference<List<Windows_Object>>()
            {});
            return windows;
            //return objectMapper.readValue(response.body(), new TypeReference<List<Windows_Object>>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Windows_Object addRoomWindow(String roomId) {
        Windows_Object windows_object = new Windows_Object();
        String body = "";
        try {
            body = objectMapper.writeValueAsString(windows_object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/"+roomId+"/Windows")).
                        header("Content-Type", "application/json").
                        POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Windows_Object getRoomWindow(String roomId, String windowId) {

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Windows/"+windowId)).header("Content-Type", "application/json").GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<Windows_Object>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
*/

}

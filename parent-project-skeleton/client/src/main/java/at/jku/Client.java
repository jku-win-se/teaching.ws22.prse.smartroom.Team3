package at.jku;

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

/**
 * Implementation of API functions. Implements all Server calls
 */
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
    public boolean deleteRoomLight(String roomId, String lightId) {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Lights/"+lightId)).DELETE().build();


        try {
            HttpResponse<String> r =  client.send(request, HttpResponse.BodyHandlers.ofString());
            if(r.statusCode()==200) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
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
            HttpResponse<String> r =  client.send(request, HttpResponse.BodyHandlers.ofString());
            if(r.statusCode()==200) {
                return activation;
            }

            else
            {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        activation.setTurnon(!turnon);
        return activation;
    }


    public boolean getCurrentLightStatus(String roomId,String lightId){
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Lights/"+lightId+"/Activation")).GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Light_Operation_Object> l= objectMapper.readValue(response.body(), new TypeReference<>() {});
            if(l.size()>0) {
                return l.get(l.size() - 1).isTurnon();
            }
            return false;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
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


    @Override
    public List<Power_Plug_Object> getAllVents(String roomId) {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(startURI + "/Rooms/"+roomId+"/Ventilators"))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();
        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            ObjectMapper mapper = new ObjectMapper();
            List<Power_Plug_Object> vent = mapper.readValue(body, new TypeReference<List<Power_Plug_Object>>()
            {});
            return vent;
            //return objectMapper.readValue(response.body(), new TypeReference<List<Lights_Object>>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Power_Plug_Object addVentilator(String roomId, String plug_id, String name) {
        Power_Plug_Object power_plug_object = new Power_Plug_Object(plug_id,name);
        String body = "";
        try {
            body = objectMapper.writeValueAsString(power_plug_object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/"+roomId+"/Ventilators")).
                        header("Content-Type", "application/json").
                        POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>(){});
            //return power_plug_object1;//or true;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Power_Plug_Object getSpecificVent(String roomId, String plug_id) {

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Ventilaotr/"+plug_id)).header("Content-Type", "application/json").GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<Power_Plug_Object>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteVent(String roomId, String plug_id) {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Ventilators/"+plug_id)).DELETE().build();


        try {
            HttpResponse<String> r =  client.send(request, HttpResponse.BodyHandlers.ofString());
            if(r.statusCode()==200) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Power_Plug_Update_Object updateVent (String roomId, String plug_id, String name) {
        Power_Plug_Update_Object power_plug_update_object = new Power_Plug_Update_Object(name);
        String body="";
        try {
            body = objectMapper.writeValueAsString(power_plug_update_object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Ventilators/"+plug_id )).
                        header("Content-Type", "application/json").
                        PUT(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            Power_Plug_Update_Object power_plug_update_object1 = objectMapper.readValue(response.body(), new TypeReference<>(){});
            return power_plug_update_object;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Power_Plug_Storing_Object activateVent(String roomId, String plug_id, Boolean turnon) {
        Power_Plug_Storing_Object activation = new Power_Plug_Storing_Object(turnon);
        String body = "";
        try {
            body = objectMapper.writeValueAsString(activation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("request: " + turnon);
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Ventilators/"+plug_id + "/Activation")).
                        header("Content-Type", "application/json").
                        POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body() + " - " + response.statusCode());
            if(response.statusCode()==200)
                return activation;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return activation;
    }

    public Power_Plug_Operation_Object getCurrentPowerPlugStatus(String roomId,String plug_id){
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Ventilators/"+plug_id+"/Activation")).GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Power_Plug_Operation_Object> l= objectMapper.readValue(response.body(), new TypeReference<>() {});
            return l.get(l.size()-1);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }




    public List<Door_Object> getAllRoomDoor(String roomId) {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(startURI + "/Rooms/"+roomId+"/Doors"))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            ObjectMapper mapper = new ObjectMapper();
            List<Door_Object> doors = mapper.readValue(body, new TypeReference<List<Door_Object>>()
            {});
            return doors;
            //return objectMapper.readValue(response.body(), new TypeReference<List<Doors_Object>>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Door_Object addRoomDoor(String roomId,String door_id,String name) {
        Door_Object door_object = new Door_Object(door_id,name);
        String body = "";
        try {
            body = objectMapper.writeValueAsString(door_object);
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


    public Door_Object getRoomDoor(String roomId, String doorId) {

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Doors/"+doorId)).header("Content-Type", "application/json").GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<Door_Object>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteDoor(String roomId, String door_id) {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Doors/"+door_id)).DELETE().build();

        try {
            HttpResponse<String> r =  client.send(request, HttpResponse.BodyHandlers.ofString());
            if(r.statusCode()==200) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Door_Object updateDoor (String roomId, String door_id, String name) {
        Door_Object door_object = new Door_Object(door_id,name);
        String body="";
        try {
            body = objectMapper.writeValueAsString(door_object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Doors/"+door_id )).
                        header("Content-Type", "application/json").
                        PUT(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            Door_Object door_object1 = objectMapper.readValue(response.body(), new TypeReference<>(){});
            return  door_object1;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Open_Door_Operation_Object getDoorStatus(String room_id, String door_id)
    {

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id+"/Doors/"+door_id+ "/Open")).
                        header("Content-Type", "application/json").
                        GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            List<Open_Door_Operation_Object> l= objectMapper.readValue(response.body(), new TypeReference<>() {});
            return l.get(l.size()-1);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean changeDoorStatus(String room_id, String door_id,Boolean isOpen){
        String body = isOpen.toString();
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id+"/Doors/"+door_id + "/Open")).
                        header("Content-Type", "application/json").
                        POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200)
                return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Window_Object> getAllRoomWindows(String roomId) {
        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(startURI + "/Rooms/"+roomId+"/Windows"))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            ObjectMapper mapper = new ObjectMapper();
            List<Window_Object> windows = mapper.readValue(body, new TypeReference<List<Window_Object>>()
            {});
            return windows;
            //return objectMapper.readValue(response.body(), new TypeReference<List<Doors_Object>>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Window_Object addRoomWindow(String roomId,String window_id,String name) {
        Window_Object window_object = new Window_Object(window_id,name);
        String body = "";
        try {
            body = objectMapper.writeValueAsString(window_object);
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


    public Window_Object getRoomWindow(String roomId, String window_id) {

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Windows/"+window_id)).header("Content-Type", "application/json").GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<Window_Object>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteWindow(String roomId, String window_id) {
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Windows/"+window_id)).DELETE().build();

        try {
            HttpResponse<String> r =  client.send(request, HttpResponse.BodyHandlers.ofString());
            if(r.statusCode()==200) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Window_Object updateWindow (String roomId, String window_id, String name) {
        Window_Object window_object = new Window_Object(window_id,name);
        String body="";
        try {
            body = objectMapper.writeValueAsString(window_object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + roomId+"/Windows/"+window_id )).
                        header("Content-Type", "application/json").
                        PUT(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            Window_Object window_object1 = objectMapper.readValue(response.body(), new TypeReference<>(){});
            return  window_object1;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Open_Window_Operation_Object getWindowStatus(String room_id, String window_id)
    {

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id+"/Windows/"+window_id+ "/Open")).
                        header("Content-Type", "application/json").
                        GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            List<Open_Window_Operation_Object> l= objectMapper.readValue(response.body(), new TypeReference<>() {});
            return l.get(l.size()-1);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean changeWindowStatus(String room_id, String window_id,Boolean isOpen){
        String body = isOpen.toString();
        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id+"/Windows/"+window_id + "/Open")).
                        header("Content-Type", "application/json").
                        POST(HttpRequest.BodyPublishers.ofString(body)).build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200)
                return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public AirQuality_Properties_Object getAirQ(String room_id)
    {

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id+"/AirQuality")).
                        header("Content-Type", "application/json").
                        GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), new TypeReference<>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public AirQuality_Co2_Object getAirQCo2(String room_id)
    {

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id+"/AirQuality/co2")).
                        header("Content-Type", "application/json").
                        GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), new TypeReference<>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
    public AirQuality_Temperature_Object getAirQTemperature(String room_id)
    {

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id+"/AirQuality/temperature")).
                        header("Content-Type", "application/json").
                        GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), new TypeReference<>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
    public AirQuality_Humidity_Object getAirQHumidity(String room_id)
    {

        HttpRequest request =
                HttpRequest.newBuilder().uri(URI.create(startURI + "/Rooms/" + room_id+"/AirQuality/humidity")).
                        header("Content-Type", "application/json").
                        GET().build();

        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), new TypeReference<>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }





}

package at.jku;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {


        Client client = new Client();

        System.out.println(client.addRoom("room1", 5, "m2"));
        System.out.println(client.addRoom("room2", 6, "m2"));
        System.out.println(client.getRooms());
        System.out.println(client.updateRoom("room1", 10, "m2").body());
        System.out.println(client.getRoomID("room2").body());
        System.out.println(client.getRooms());
        System.out.println(client.deleteRoom("room1"));
        System.out.println(client.getRoomID("room1").body());
        System.out.println(client.getRoomID("room2").body());
        System.out.println(client.getPeopleCount("room1").body());
        System.out.println(client.addPeopleRoom("room1",11).body());
        System.out.println(client.getAllLights("room1").body());
        System.out.println(client.addLight("room1","Light2","Tisch").body());
        System.out.println(client.getRoomLight("room1","Light10").body());
    }
}

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

        System.out.println("1: " + client.addRoom("room1", 5, "m2"));
        System.out.println("2: " + client.addRoom("room2", 6, "m2"));
        System.out.println("3: " + client.getRooms());
        System.out.println("4: " + client.updateRoom("room1", 10, "m2"));
        System.out.println("5: " + client.getRoomID("room2"));
        System.out.println("6: " + client.getRooms());
        System.out.println("7: " + client.deleteRoom("room1"));
        System.out.println("8: " + client.getRoomID("room1"));
        System.out.println("9: " + client.getRoomID("room2"));
        System.out.println("10: " + client.getPeopleCount("room1"));
        System.out.println("11: " + client.addPeopleRoom("room1",11));
        System.out.println("12: " + client.getAllLights("room1")); //Verbindung zwischen Room ID und Light ID fehlt noch
        System.out.println("13: " + client.addLight("room1","Light2","Tisch"));
        System.out.println("14: " + client.getRoomLight("room1","Light10"));
        System.out.println("15: " + client.getAllLights("room1")); //Verbindung zwischen Room ID und Light ID fehlt noch
    }
}

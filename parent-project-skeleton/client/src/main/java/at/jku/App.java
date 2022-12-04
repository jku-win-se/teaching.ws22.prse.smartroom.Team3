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

        System.out.println(client.addRoom("room1", 5.12, "m2").body());
        System.out.println(client.getRooms().body());
        System.out.println(client.updateRoom("room1", 10.00, "m2").body());
        System.out.println(client.getRoomID("room1").body());
        System.out.println(client.deleteRoom("room1"));


    }
}

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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws IOException, InterruptedException {


        Client client = new Client();


        System.out.println("add 1: " + client.addRoom("room1", 5, "m2"));
//        System.out.println("add 2: " + client.addRoom("room2", 6, "m2"));
        System.out.println("get 3: " + client.getRooms());
//        System.out.println("update 4: " + client.updateRoom("room1", 10, "m2"));
//        System.out.println("get 5: " + client.getRoomID("room2"));
//        System.out.println("get 6: " + client.getRooms());
//        System.out.println("del 7: " + client.deleteRoom("room2"));
        System.out.println("get 8: " + client.getRoomID("room1"));
        //System.out.println("get 9: " + client.getRoomID("room2"));
        System.out.println("add 10: " + client.addPeopleRoom("room1",11));
        System.out.println("get 11: " + client.getPeopleCount("room1"));
 //       System.out.println("get 12: " + client.getPeopleCount("room2"));
//        System.out.println("get 12: " + client.getAllLights("room1")); //Verbindung zwischen Room ID und Light ID fehlt noch
        System.out.println("add 13: " + client.addLight("room1","Light2","Tisch"));
        System.out.println("add 13.1: " + client.addLight("room1","Light3","Fenster"));
//        System.out.println("get 14: " + client.getRoomLight("room1","Light10"));
        System.out.println("get 15: " + client.getAllLights("room1")); //Verbindung zwischen Room ID und Light ID fehlt noch
        System.out.println("get 16: " + client.getRoomLight("room1", "Light2"));
        System.out.println("get 16.1: " + client.getRoomLight("room1", "Light3"));
        System.out.println("del 17: " + client.deleteRoomLight("room1", "Light2"));
        System.out.println("get 18: " + client.getAllLights("room1"));
        System.out.println("upd 19: " + client.updateLight("room1", "Light3", "FensterNeu"));
        System.out.println("get 20: " + client.getAllLights("room1"));
        System.out.println("act 21: " + client.activateLight("room1", "Light3", true));
        System.out.println("actC 22: " + client.setColor("room1", "Light3", true, 213, "hexhex"));
    }
}

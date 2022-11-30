package at.jku;

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
    public static void main(String[] args) throws IOException, InterruptedException {

        String uri = "http://localhost:8080/";
        boolean b = true;


        while (b) {

            System.out.println("Aktionen:");
            System.out.println("1 --> Create Room");
            System.out.println("2 --> Read Room");
            System.out.println("3 --> Update Room");
            System.out.println("4 --> Delete Room");
            System.out.println("5 --> Beenden");
            System.out.println("------------------------------------------------");
            System.out.println("Aktion auswaehlen:");
            Scanner choice = new Scanner(System.in);
            String c = choice.nextLine();



            if (c.equals("5")) b = false;

            final HttpClient client = HttpClient.newHttpClient();



            if (c.equals("1")) {

                //Create Room
                System.out.println();
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Neuen Raum anlegen");

                System.out.println("Room Name: ");
                final Scanner n = new Scanner(System.in);
                String name = n.nextLine();

                System.out.println("Size: ");
                final Scanner s = new Scanner(System.in);
                String size = s.nextLine();

                System.out.println("Doors: ");
                final Scanner d = new Scanner(System.in);
                String doors = d.nextLine();

                System.out.println("Windows: ");
                final Scanner w = new Scanner(System.in);
                String windows = w.nextLine();

                System.out.println("Lights: ");
                final Scanner l = new Scanner(System.in);
                String lights = l.nextLine();

                System.out.println("Fans: ");
                final Scanner f = new Scanner(System.in);
                String fans = f.nextLine();

                HttpRequest request =
                        HttpRequest.newBuilder().uri(URI.create(uri + "createRoom" + "?name=" + name + "&" +
                                        "size=" + size + "&" +
                                        "doors=" + doors + "&" +
                                        "windows=" + windows + "&" +
                                        "lights=" + lights + "&" +
                                        "fans=" + fans)).GET().build();

                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

                //Ausgabe der Response
                System.out.println(response);
                System.out.println(response.body());

            }

            if (c.equals("2")) {

                // Read CSV file

            }

            if (c.equals("3")) {

                //Create Room
                System.out.println();
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Update Room");

                System.out.println("Room ID: ");
                final Scanner id = new Scanner(System.in);

                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Update");
                System.out.println("1: Name");
                System.out.println("2: Size");
                System.out.println("3: Doors");
                System.out.println("4: Windows");
                System.out.println("5: Lights");
                System.out.println("6: Fans:");

                Scanner choiceU = new Scanner(System.in);
                String a = choiceU.nextLine();

                if (a.equals("1")) {
                    System.out.println("new Room Name: ");
                    final Scanner name = new Scanner(System.in);

                    HttpRequest request =
                            HttpRequest.newBuilder().uri(URI.create(uri + "updateRoom" + "?id=" + id.nextLine() + "&" +
                                    "name=" + name.nextLine())).GET().build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    //Ausgabe der Response
                    System.out.println(response);
                    System.out.println(response.body());

                }

                if (a.equals("2")) {
                    System.out.println("new Room Size: ");
                    final Scanner u = new Scanner(System.in);

                    HttpRequest request =
                            HttpRequest.newBuilder().uri(URI.create(uri + "updateRoom" + "?id=" + id.nextLine() + "&" +
                                    "size=" + u.nextLine())).GET().build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    //Ausgabe der Response
                    System.out.println(response);
                    System.out.println(response.body());

                }

                if (a.equals("3")) {
                    System.out.println("new Doors: ");
                    final Scanner u = new Scanner(System.in);

                    HttpRequest request =
                            HttpRequest.newBuilder().uri(URI.create(uri + "updateRoom" + "?id=" + id.nextLine() + "&" +
                                    "doors=" + u.nextLine())).GET().build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    //Ausgabe der Response
                    System.out.println(response);
                    System.out.println(response.body());

                }

                if (a.equals("4")) {
                    System.out.println("new Windows: ");
                    final Scanner u = new Scanner(System.in);

                    HttpRequest request =
                            HttpRequest.newBuilder().uri(URI.create(uri + "updateRoom" + "?id=" + id.nextLine() + "&" +
                                    "windows=" + u.nextLine())).GET().build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    //Ausgabe der Response
                    System.out.println(response);
                    System.out.println(response.body());

                }

                if (a.equals("5")) {
                    System.out.println("new Lights: ");
                    final Scanner u = new Scanner(System.in);

                    HttpRequest request =
                            HttpRequest.newBuilder().uri(URI.create(uri + "updateRoom" + "?id=" + id.nextLine() + "&" +
                                    "lights=" + u.nextLine())).GET().build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    //Ausgabe der Response
                    System.out.println(response);
                    System.out.println(response.body());

                }

                if (a.equals("6")) {
                    System.out.println("new Fans: ");
                    final Scanner u = new Scanner(System.in);

                    HttpRequest request =
                            HttpRequest.newBuilder().uri(URI.create(uri + "updateRoom" + "?id=" + id.nextLine() + "&" +
                                    "fans=" + u.nextLine())).GET().build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    //Ausgabe der Response
                    System.out.println(response);
                    System.out.println(response.body());

                }

            }


            if (c.equals("4")) {
                //Delete Room
                System.out.println();
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Delete Room");

                System.out.println("Room ID: ");
                final Scanner id = new Scanner(System.in);

                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(
                        uri + "deleteRoom" + "?id=" + id.nextLine())).GET().build();
                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println(response);

            }
        }

    }
}

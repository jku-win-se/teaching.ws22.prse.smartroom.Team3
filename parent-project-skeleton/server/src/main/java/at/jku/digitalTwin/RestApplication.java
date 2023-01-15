package at.jku.digitalTwin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;

@SpringBootApplication
public class RestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);

		PostgreSQLJDBC db = new PostgreSQLJDBC();
		Connection c=db.connect_to_db("SmartroomGruppe3","postgres","smartroom3");

		//insert room
		db.add_room(c,"room","Wohnzimmer", 33);
		db.add_room(c,"room","Bad", 8);

		//read room
		db.read_room(c,"room");

		//delete room
		db.delete_room(c,"room","Wohnzimmer");
		db.delete_room(c,"room","Bad");
	}

}

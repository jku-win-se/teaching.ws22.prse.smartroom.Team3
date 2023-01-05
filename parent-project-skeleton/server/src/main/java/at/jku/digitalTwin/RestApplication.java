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

		//delete room
		db.delete_row(c,"room","Wohnzimmer2");

		//insert room
		db.insert_row(c,"room","Wohnzimmer2", 33);
	}

}

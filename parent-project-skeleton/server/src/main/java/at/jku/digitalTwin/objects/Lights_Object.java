package at.jku.digitalTwin.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lights_Object {
    String id;
    String name;



    public Lights_Object(String light_id, String name) {
        this.id = light_id;
        this.name = name;
    }

    public Lights_Object() {
    }

    private Room_Object room;
    private String roomid;


    public String getLight_id() {
        return id;
    }

    public void setLight_id(String light_id) {
        this.id = light_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room_Object getRoom() {
        return room;
    }

    public void setRoom(Room_Object room) {
        this.room = room;
    }

    public String getRoom_id() {
        return roomid;
    }

    public void setRoom_id(String roomid) {
        this.roomid = roomid;
    }

    @Override
    public String toString() {
        return "{" +
                "light_id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

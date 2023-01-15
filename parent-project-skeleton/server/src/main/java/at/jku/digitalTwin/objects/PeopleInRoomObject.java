package at.jku.digitalTwin.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "peopleinroom")
public class PeopleInRoomObject {

    private String room_id;
    private int people_count;
    @Id
    private Timestamp people_timestamp;

    @ManyToOne
    @JoinColumn(name = "peopleroomId")
    private Room_Object room;

    public PeopleInRoomObject(String room_id, int people_count, Timestamp people_timestamp) {
        this.room_id = room_id;
        this.people_count = people_count;
        this.people_timestamp = people_timestamp;
    }

    public PeopleInRoomObject() {
    }

    public Room_Object getRoom() {
        return room;
    }

    public void setRoom(Room_Object room) {
        this.room = room;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public void setPeople_timestamp(Timestamp people_timestamp) {
        this.people_timestamp = people_timestamp;
    }

    public Timestamp getPeople_timestamp() {
        return people_timestamp;
    }

    public int getPeople_count() {
        return people_count;
    }

    public void setPeople_count(int people_count) {
        this.people_count = people_count;
    }

    @Override
    public String toString() {
        return "{" +
                "room_id='" + room_id + '\'' +
                ", people_count=" + people_count +
                '}';
    }


}

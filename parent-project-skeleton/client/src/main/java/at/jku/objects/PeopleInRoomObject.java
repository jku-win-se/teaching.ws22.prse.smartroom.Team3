package at.jku.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeopleInRoomObject {
    String room_id;
    int people_count;

    public PeopleInRoomObject(String room_id, int people_count) {
        this.room_id = room_id;
        this.people_count = people_count;
    }

    public PeopleInRoomObject() {
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
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

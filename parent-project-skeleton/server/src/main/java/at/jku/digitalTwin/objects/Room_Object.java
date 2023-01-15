package at.jku.digitalTwin.objects;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
public class Room_Object {

    private double room_size;
    private String measurement_unit;
    private int people;
    @Id
    //@JoinColumn(name = "room_id")
    private String room_id;

//    @OneToOne
//    @JoinColumn(name = "room_id")
    //private PeopleInRoomObject peopleInRoomObject;

    //@OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToMany(cascade = CascadeType.ALL)
    private List<Lights_Object> lights_object;

    public Room_Object(String room_id, double room_size, String measurement_unit) {
        this.room_id = room_id;
        this.room_size = room_size;
        this.measurement_unit = measurement_unit;
    }

    public Room_Object() {
    }

    /*public PeopleInRoomObject getPeopleInRoomObject() {
        return peopleInRoomObject;
    }

    public void setPeopleInRoomObject(PeopleInRoomObject peopleInRoomObject) {
        this.peopleInRoomObject = peopleInRoomObject;
    }*/


    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public List<Lights_Object> getLights_object() {
        return lights_object;
    }

    public void setLights_object(List<Lights_Object> lights_object) {
        this.lights_object = lights_object;
    }

    public double getRoom_size() {
        return room_size;
    }

    public void setRoom_size(double room_size) {
        this.room_size = room_size;
    }

    public String getMeasurement_unit() {
        return measurement_unit;
    }

    public void setMeasurement_unit(String measurement_unit) {
        this.measurement_unit = measurement_unit;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    @Override
    public String toString() {
        return "{" +
                "room_size=" + room_size +
                ", measurement_unit=" + measurement_unit +
                ", room_id=" + room_id +
                "}";
    }

}

package at.jku.digitalTwin.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Room_Object {

    private Double room_size;
    private String measurement_unit;
    private String room_id;


    public Room_Object(String room_id, double room_size, String measurement_unit) {
        this.room_id = room_id;
        this.room_size = room_size;
        this.measurement_unit = measurement_unit;
    }

    public Room_Object() {
    }

    public Double getRoom_size() {
        return room_size;
    }

    public void setRoom_size(Double room_size) {
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

    public void setId(String id) {
        this.room_id = id;
    }

    @Id
    public String getId() {
        return room_id;
    }
}

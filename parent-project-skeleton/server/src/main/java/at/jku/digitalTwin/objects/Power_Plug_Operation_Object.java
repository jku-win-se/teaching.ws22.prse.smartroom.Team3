package at.jku.digitalTwin.objects;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "plug_Operation")
public class Power_Plug_Operation_Object {

    boolean turnon;
    LocalDateTime time;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Power_Plug_Operation_Object(boolean turnon, LocalDateTime time) {
        this.turnon = turnon;
        this.time = time;
    }

    public Power_Plug_Operation_Object(Power_Plug_Operation_Object operation, LocalDateTime time) {
        this.turnon = operation.isTurnon();
        this.time = time;
    }

    public Power_Plug_Operation_Object() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTurnon() {
        return turnon;
    }

    public void setTurnon(boolean turnon) {
        this.turnon = turnon;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{" +
                "turnon=" + turnon +
                ", time='" + time + '\'' +
                '}';
    }}
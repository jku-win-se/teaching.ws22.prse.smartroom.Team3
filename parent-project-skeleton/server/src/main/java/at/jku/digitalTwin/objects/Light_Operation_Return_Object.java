package at.jku.digitalTwin.objects;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lights_Operation")
public class Light_Operation_Return_Object {

    boolean turnon;
    int brightness;
    String hex;

    LocalDateTime time;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Light_Operation_Return_Object(boolean turnon, int brightness, String hex, LocalDateTime time) {
        this.turnon = turnon;
        this.brightness = brightness;
        this.hex = hex;
        this.time = time;
    }

    public Light_Operation_Return_Object(Light_Operation_Object operation, LocalDateTime time) {
        this.turnon = operation.isTurnon();
        this.brightness = operation.getBrightness();
        this.hex = operation.getHex();
        this.time = time;
    }

    public Light_Operation_Return_Object() {
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

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
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
                ", brightness=" + brightness +
                ", hex='" + hex + '\'' +
                ", time='" + time + '\'' +
                '}';
    }


}

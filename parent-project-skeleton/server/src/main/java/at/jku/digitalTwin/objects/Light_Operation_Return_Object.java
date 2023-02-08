package at.jku.digitalTwin.objects;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Light_Operation_Return_Object {

    boolean turnon;
    int brightness;
    String hex;
    Timestamp time;



    public Light_Operation_Return_Object(boolean turnon, int brightness, String hex, Timestamp time) {
        this.turnon = turnon;
        this.brightness = brightness;
        this.hex = hex;
        this.time = time;
    }

    public Light_Operation_Return_Object(Light_Operation_Object operation, Timestamp time) {
        this.turnon = operation.isTurnon();
        this.brightness = operation.getBrightness();
        this.hex = operation.getHex();
        this.time = time;
    }

    public Light_Operation_Return_Object() {
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
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

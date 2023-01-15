package at.jku.digitalTwin.objects;

import javax.persistence.Id;
import java.sql.Timestamp;

public class Light_Operation_Object {
    boolean turnon;
    int brightness;
    String hex;
    @Id
    private Timestamp light_timestamp;

    public Light_Operation_Object(boolean turnon, int brightness, String hex) {
        this.turnon = turnon;
        this.brightness = brightness;
        this.hex = hex;
    }

    public Light_Operation_Object() {
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

    @Override
    public String toString() {
        return "Light_Operation_Object{" +
                "turnon=" + turnon +
                ", brightness=" + brightness +
                ", hex='" + hex + '\'' +
                '}';
    }
}

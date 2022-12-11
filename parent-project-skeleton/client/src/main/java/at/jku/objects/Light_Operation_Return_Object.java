package at.jku.objects;

public class Light_Operation_Return_Object {
    boolean turnon;
    int brightness;
    String hex;
    String time;

    public Light_Operation_Return_Object(boolean turnon, int brightness, String hex, String time) {
        this.turnon = turnon;
        this.brightness = brightness;
        this.hex = hex;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

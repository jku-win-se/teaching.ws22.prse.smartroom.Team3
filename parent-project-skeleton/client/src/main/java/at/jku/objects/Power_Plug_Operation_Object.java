package at.jku.objects;

public class Power_Plug_Operation_Object {
    String turnon;
    String time;

    public Power_Plug_Operation_Object(String turnon, String time) {
        this.turnon = turnon;
        this.time = time;
    }

    public Power_Plug_Operation_Object() {
    }

    public String getTurnon() {
        return turnon;
    }

    public void setTurnon(String turnon) {
        this.turnon = turnon;
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
                "turnon='" + turnon + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

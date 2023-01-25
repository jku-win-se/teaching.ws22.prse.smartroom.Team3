package at.jku.objects;

public class Power_Plug_Operation_Object {
    boolean turnon;
    String time;

    public Power_Plug_Operation_Object(boolean turnon, String time) {
        this.turnon = turnon;
        this.time = time;
    }

    public Power_Plug_Operation_Object() {
    }

    public boolean isTurnon() {
        return turnon;
    }

    public void setTurnon(boolean turnon) {
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

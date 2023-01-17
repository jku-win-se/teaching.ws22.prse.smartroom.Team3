package at.jku.objects;

public class Power_Plug_Storing_Object {
    boolean turnon;

    public Power_Plug_Storing_Object(boolean turnon) {
        this.turnon = turnon;
    }

    public Power_Plug_Storing_Object() {
    }

    public boolean isTurnon() {
        return turnon;
    }

    public void setTurnon(boolean turnon) {
        this.turnon = turnon;
    }

    @Override
    public String toString() {
        return "{" +
                "turnon=" + turnon +
                '}';
    }
}

package at.jku.objects;

public class Light_Activation_Object {
    boolean turnon;

    public Light_Activation_Object(boolean turnon) {
        this.turnon = turnon;
    }

    public Light_Activation_Object() {
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

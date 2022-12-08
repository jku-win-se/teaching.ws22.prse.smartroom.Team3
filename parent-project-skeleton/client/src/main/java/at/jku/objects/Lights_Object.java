package at.jku.objects;

public class Lights_Object {
    String light_id;
    String name;

    public Lights_Object(String light_id, String name) {
        this.light_id = light_id;
        this.name = name;
    }

    public Lights_Object() {
    }

    public String getLight_id() {
        return light_id;
    }

    public void setLight_id(String light_id) {
        this.light_id = light_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "light_id='" + light_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

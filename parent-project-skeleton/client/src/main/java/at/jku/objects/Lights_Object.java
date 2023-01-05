package at.jku.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lights_Object {
    String id;
    String name;



    public Lights_Object(String light_id, String name) {
        this.id = light_id;
        this.name = name;
    }

    public Lights_Object() {
    }




    public String getLight_id() {
        return id;
    }

    public void setLight_id(String light_id) {
        this.id = light_id;
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
                "light_id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

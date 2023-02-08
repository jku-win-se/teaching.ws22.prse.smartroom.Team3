package at.jku.digitalTwin.objects;

public class Door_Object {
    String door_id;
    String name;

    public Door_Object() {
    }

    @Override
    public String toString() {
        return "{" +
                "door_id='" + door_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getDoor_id() {
        return door_id;
    }

    public void setDoor_id(String door_id) {
        this.door_id = door_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Door_Object(String door_id, String name) {
        this.door_id = door_id;
        this.name = name;
    }
}
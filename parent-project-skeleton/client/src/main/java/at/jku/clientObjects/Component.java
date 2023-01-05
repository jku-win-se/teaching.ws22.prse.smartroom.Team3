package at.jku.clientObjects;

public class Component {
    String id;
    String name;
    String room_id;
    ComponentType type;
    Boolean status;

    public Component(String id,String name, String room_id, ComponentType type, Boolean status) {
        this.id = id;
        this.name = name;
        this.room_id = room_id;
        this.type = type;
        this.status = status;
    }

    public Component() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }


    public String getRoom_id() {
        return room_id;
    }


    public ComponentType getType() {
        return type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;

    }
}

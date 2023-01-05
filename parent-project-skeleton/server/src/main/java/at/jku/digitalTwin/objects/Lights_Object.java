package at.jku.digitalTwin.objects;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "lights")
public class Lights_Object {

    @Id
    //@Column(name = "light_id")
    String id;
    String name;

    @ManyToOne
//    @JoinColumn(name = "roomId")
    private Room_Object room;

    private boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Light_Operation_Return_Object> light_operations;

    public Lights_Object(String light_id, String name) {
        this.id = light_id;
        this.name = name;
    }

    public Lights_Object() {
    }

    public void addLightOperation(Light_Operation_Object operation, LocalDateTime timestamp) {
        light_operations.add(new Light_Operation_Return_Object(operation, timestamp));
    }

    public List<Light_Operation_Return_Object> getLight_operations() {
        return light_operations;
    }

    public void setLight_operations(List<Light_Operation_Return_Object> light_operations) {
        this.light_operations = light_operations;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Room_Object getRoom() {
        return room;
    }

    public void setRoom(Room_Object room) {
        this.room = room;
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

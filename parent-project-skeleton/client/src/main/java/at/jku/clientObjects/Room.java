package at.jku.clientObjects;

import at.jku.Client;
import at.jku.objects.Lights_Object;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Room {
    String name;
    int size;
    String room_id;
    int noPeopleInRoom;
    List<Component> components = new ArrayList<>();
    List<Airquality> airqualities;
    private Client client;

    public Room(String name, int size, String room_id, int noPeopleInRoom) {
        this.name = name;
        this.size = size;
        this.room_id = room_id;
        this.noPeopleInRoom = noPeopleInRoom;
        this.client = new Client();
    }
    public Room(String name, int size, String room_id, int noPeopleInRoom,List<Component> components) {
        this.name = name;
        this.size = size;
        this.room_id = room_id;
        this.noPeopleInRoom = noPeopleInRoom;
        this.client = new Client();
        this.components = components;
    }

    public Room() {
        client = new Client();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public int getNoPeopleInRoom() {
        return noPeopleInRoom;
    }

    public void setNoPeopleInRoom(int noPeopleInRoom) {
        this.noPeopleInRoom = noPeopleInRoom;
    }
    public List<Component> getAllComponents()
    {
        return this.components;
    }
    public List<Component> getAllLights()
    {
        return this.components.stream().filter(c -> c.getType()==ComponentType.LIGHT).collect(Collectors.toList());
    }
    public List<Component> getAllWindows()
    {
        return this.components.stream().filter(c -> c.getType()==ComponentType.WINDOW).collect(Collectors.toList());
    }
    public List<Component> getAllDoors()
    {
        return this.components.stream().filter(c -> c.getType()==ComponentType.DOOR).collect(Collectors.toList());
    }
    public List<Component> getAllVentilators()
    {
        return this.components.stream().filter(c -> c.getType()==ComponentType.FAN).collect(Collectors.toList());
    }

    public boolean addLight(String light_id,String name, Boolean status){
        client.addLight(this.room_id,light_id,name);
        client.activateLight(room_id,light_id,status);
        components.add(new Component(light_id,name,this.room_id,ComponentType.LIGHT,status));
        return true;
    }
    public boolean addWindow(String window_id,String name, Boolean status){
        client.addRoomWindow(this.room_id,window_id,name);
        client.changeWindowStatus(this.room_id,window_id,status);
        components.add(new Component(window_id,name,this.room_id,ComponentType.WINDOW,status));
        return true;
    }
    public boolean addDoor(String door_id,String name, Boolean status){
        client.addRoomDoor(this.room_id,door_id,name);
        components.add(new Component(door_id,name,this.room_id,ComponentType.DOOR,status));
        return true;
    }
    public boolean addVentilator(String ventilator_id,String name, Boolean status){
        client.addVentilator(this.room_id,ventilator_id,name);
        components.add(new Component(ventilator_id,name,this.room_id,ComponentType.FAN,status));
        return true;
    }


}

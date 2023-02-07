package at.jku.clientObjects;

import at.jku.Client;
import at.jku.objects.Light_Activation_Object;

public class Component {
    String id;
    String name;
    String room_id;
    ComponentType type;
    Boolean status;
    private Client client;

    public Component(String id,String name, String room_id, ComponentType type, Boolean status) {
        this.id = id;
        this.name = name;
        this.room_id = room_id;
        this.type = type;
        this.status = status;
        this.client = new Client();
    }

    public Component() {
        this.client = new Client();
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

    public boolean changeStatus()
    {
        Boolean succeeded =false;
        if(type==ComponentType.DOOR)
        {
            succeeded = client.changeDoorStatus(this.room_id,this.id,!status);
        }
        if(type==ComponentType.WINDOW)
        {
            succeeded = client.changeWindowStatus(this.room_id,this.id,!status);
        }
        if(type==ComponentType.FAN)
        {
            succeeded = client.activateVent(this.room_id,this.id,!status);
        }
        if(type==ComponentType.LIGHT)
        {
            Light_Activation_Object l = client.activateLight(this.room_id,this.id,!status);
            if(l.isTurnon()!=status)
                succeeded = true;
        }
        if(succeeded)
        {
            status = !status;
        }
        return succeeded;
    }
    public boolean setStatus(boolean newStatus)
    {
        Boolean succeeded =false;
        if(type==ComponentType.DOOR)
        {
            succeeded = client.changeDoorStatus(this.room_id,this.id,newStatus);
        }
        if(type==ComponentType.WINDOW)
        {
            succeeded = client.changeWindowStatus(this.room_id,this.id,newStatus);
        }
        if(type==ComponentType.FAN)
        {
            succeeded = client.activateVent(this.room_id,this.id,newStatus);
        }
        if(type==ComponentType.LIGHT)
        {
            Light_Activation_Object l = client.activateLight(this.room_id,this.id,newStatus);
            if(l.isTurnon()==newStatus)
                return true;
        }
        if(succeeded)
        {
            status = !status;
        }
        return succeeded;
    }
    public boolean update(String name)
    {
        if(type==ComponentType.DOOR)
        {
            client.updateDoor(this.room_id,this.id,name);
        }
        if(type==ComponentType.WINDOW)
        {
            client.updateWindow(this.room_id,this.id,name);
        }
        if(type==ComponentType.FAN)
        {
            client.updateVent(this.room_id,this.id,name);
        }
        if(type==ComponentType.LIGHT)
        {
            client.updateLight(this.room_id,this.id,name);
        }

        return true;
    }
}

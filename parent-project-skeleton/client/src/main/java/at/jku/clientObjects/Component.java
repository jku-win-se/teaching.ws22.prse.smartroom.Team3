package at.jku.clientObjects;

import at.jku.Client;
import at.jku.objects.Light_Activation_Object;
import at.jku.objects.Power_Plug_Storing_Object;

/**
 * A Component is used to store a part of a room
 * The type is dependent on the enum type
 * The change Methods of a Component also calls the server function for each change
 */
public class Component {
    String id;
    String name;
    String room_id;
    ComponentType type;
    Boolean status;
    private Client client;

    /**
     * Class to save a Component of a room
     * @param id id of the comonent
     * @param name name of the component
     * @param room_id id of the room which the component belongs to
     * @param type enum for different types of Components (door, window, fan, light)
     * @param status the current status of the component
     */

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

    /**
     * changes the Status of the component based on the previous status
     * @return returns if the status change was successful
     */

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
            Power_Plug_Storing_Object p = client.activateVent(this.room_id,this.id,!status);
            if (p.isTurnon() != status)
                succeeded = true;
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

    /**
     * changes the Status of the component based on the input value
     * @param newStatus the new status to set the component.
     * @return returns if the status change was successful
     */
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
            Power_Plug_Storing_Object p = client.activateVent(this.room_id,this.id,newStatus);
            if (p.isTurnon() == newStatus)
                return true;
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

    /**
     * can update the component to a new name on the server
     * @param name the new name of the Component
     * @return returns if the update was successful
     */
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

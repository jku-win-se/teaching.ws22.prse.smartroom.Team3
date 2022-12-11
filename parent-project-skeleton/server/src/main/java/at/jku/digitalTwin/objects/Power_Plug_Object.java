package at.jku.digitalTwin.objects;

public class Power_Plug_Object {
    String plug_id;
    String name;
    public Power_Plug_Object(){
    }

    public Power_Plug_Object(String plug_id, String name) {
        this.plug_id = plug_id;
        this.name = name;
    }

    public String getPlug_id() {
        return plug_id;
    }

    public void setPlug_id(String plug_id) {
        this.plug_id = plug_id;
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
                "plug_id='" + plug_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

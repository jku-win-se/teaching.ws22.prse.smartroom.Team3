package at.jku.objects;

public class Power_Plug_Update_Object {
    String name;

    public Power_Plug_Update_Object(String name) {
        this.name = name;
    }

    public Power_Plug_Update_Object() {
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
                "name='" + name + '\'' +
                '}';
    }
}

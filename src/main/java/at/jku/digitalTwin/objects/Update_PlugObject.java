package at.jku.digitalTwin.objects;

public class Update_PlugObject {

    String name;

    public Update_PlugObject(String name) {
        this.name = name;
    }

    public Update_PlugObject() {
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

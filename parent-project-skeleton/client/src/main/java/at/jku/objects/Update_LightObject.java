package at.jku.objects;

public class Update_LightObject {
    String name;

    public Update_LightObject(String name) {
        this.name = name;
    }

    public Update_LightObject() {
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

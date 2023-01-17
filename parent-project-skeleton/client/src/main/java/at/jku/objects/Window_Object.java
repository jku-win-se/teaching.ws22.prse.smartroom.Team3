package at.jku.objects;

public class Window_Object {
    String window_id;
    String name;

    @Override
    public String toString() {
        return "{" +
                "window_id='" + window_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getWindow_id() {
        return window_id;
    }

    public void setWindow_id(String window_id) {
        this.window_id = window_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Window_Object() {
    }

    public Window_Object(String window_id, String name) {
        this.window_id = window_id;
        this.name = name;
    }
}

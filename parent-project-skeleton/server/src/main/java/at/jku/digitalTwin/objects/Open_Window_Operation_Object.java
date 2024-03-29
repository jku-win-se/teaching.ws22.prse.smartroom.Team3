package at.jku.digitalTwin.objects;

public class Open_Window_Operation_Object {
    boolean open;
    String time;

    public Open_Window_Operation_Object(boolean turnon, String time) {
        this.open = turnon;
        this.time = time;
    }

    public Open_Window_Operation_Object() {
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean turnon) {
        this.open = turnon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{" +
                "turnon='" + open + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

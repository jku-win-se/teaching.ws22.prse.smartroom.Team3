package at.jku.digitalTwin.objects;

public class Open_Window_Object {

    boolean isOpen;

    public Open_Window_Object(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Open_Window_Object() {
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return "{" +
                "isOpen=" + isOpen +
                '}';
    }
}

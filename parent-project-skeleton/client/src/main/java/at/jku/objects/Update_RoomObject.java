package at.jku.objects;

public class Update_RoomObject {
    double room_size;
    String measurement_unit;

    public Update_RoomObject(double room_size, String measurement_unit) {
        this.room_size = room_size;
        this.measurement_unit = measurement_unit;
    }
    public Update_RoomObject(){
    }

    public double getRoom_size() {
        return room_size;
    }

    public void setRoom_size(double room_size) {
        this.room_size = room_size;
    }

    public String getMeasurement_unit() {
        return measurement_unit;
    }

    public void setMeasurement_unit(String measurement_unit) {
        this.measurement_unit = measurement_unit;
    }

    @Override
    public String toString() {
        return "{" +
                "room_size=" + room_size +
                ", measurement_unit='" + measurement_unit + '\'' +
                '}';
    }
}
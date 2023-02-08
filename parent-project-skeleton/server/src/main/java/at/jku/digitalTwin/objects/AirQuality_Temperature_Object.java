package at.jku.digitalTwin.objects;

public class AirQuality_Temperature_Object {

    String room_id;
    String ventilator;
    int temperature;
    String temperaturemeasurementunit;
    String time;

    public AirQuality_Temperature_Object() {
    }

    public AirQuality_Temperature_Object(String room_id, String ventilator, int temperature, String temperaturemeasurementunit, String time) {
        this.room_id = room_id;
        this.ventilator = ventilator;
        this.temperature = temperature;
        this.temperaturemeasurementunit = temperaturemeasurementunit;
        this.time = time;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getVentilator() {
        return ventilator;
    }

    public void setVentilator(String ventilator) {
        this.ventilator = ventilator;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getTemperaturemeasurementunit() {
        return temperaturemeasurementunit;
    }

    public void setTemperaturemeasurementunit(String temperaturemeasurementunit) {
        this.temperaturemeasurementunit = temperaturemeasurementunit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AirQuality_Temperature_Object{" +
                "room_id='" + room_id + '\'' +
                ", ventilator='" + ventilator + '\'' +
                ", temperature=" + temperature +
                ", temperaturemeasurementunit='" + temperaturemeasurementunit + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

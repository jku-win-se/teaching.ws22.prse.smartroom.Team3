package at.jku.digitalTwin.objects;

public class AirQuality_Humidity_Object {

    String room_id;
    String ventilator;
    int humidity;
    String humiditymeasurementunit;
    String time;

    public AirQuality_Humidity_Object(String room_id, String ventilator, int humidity, String humiditymeasurementunit, String time) {
        this.room_id = room_id;
        this.ventilator = ventilator;
        this.humidity = humidity;
        this.humiditymeasurementunit = humiditymeasurementunit;
        this.time = time;
    }

    public AirQuality_Humidity_Object() {
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

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getHumiditymeasurementunit() {
        return humiditymeasurementunit;
    }

    public void setHumiditymeasurementunit(String humiditymeasurementunit) {
        this.humiditymeasurementunit = humiditymeasurementunit;
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
                "room_id='" + room_id + '\'' +
                ", ventilator='" + ventilator + '\'' +
                ", humidity=" + humidity +
                ", humiditymeasurementunit='" + humiditymeasurementunit + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

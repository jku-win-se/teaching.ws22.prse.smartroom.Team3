package at.jku.digitalTwin.objects;

public class AirQuality_Co2_Object {

    String room_id;
    String ventilator;
    int co2;
    String co2measurementunit;
    String time;

    public AirQuality_Co2_Object(String room_id, String ventilator, int co2, String co2measurementunit, String time) {
        this.room_id = room_id;
        this.ventilator = ventilator;
        this.co2 = co2;
        this.co2measurementunit = co2measurementunit;
        this.time = time;
    }

    public AirQuality_Co2_Object() {
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

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public String getCo2measurementunit() {
        return co2measurementunit;
    }

    public void setCo2measurementunit(String co2measurementunit) {
        this.co2measurementunit = co2measurementunit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AirQuality_Co2_Object{" +
                "room_id='" + room_id + '\'' +
                ", ventilator='" + ventilator + '\'' +
                ", co2=" + co2 +
                ", co2measurementunit='" + co2measurementunit + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}


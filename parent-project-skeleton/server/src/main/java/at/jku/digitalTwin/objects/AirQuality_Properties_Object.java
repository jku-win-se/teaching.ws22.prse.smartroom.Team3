package at.jku.digitalTwin.objects;

public class AirQuality_Properties_Object {
    String room_id;
    String device_id;
    String ventilator;
    int co2;
    String co2measurementunit;
    int temperature;
    String temperaturemeasurementunit;
    int humidity;
    String humiditymeasurementunit;
    String time;

    public AirQuality_Properties_Object(String room_id, String device_id, String ventilator, int co2, String co2measurementunit, int temperature, String temperaturemeasurementunit, int humidity, String humiditymeasurementunit, String time) {
        this.room_id = room_id;
        this.device_id = device_id;
        this.ventilator = ventilator;
        this.co2 = co2;
        this.co2measurementunit = co2measurementunit;
        this.temperature = temperature;
        this.temperaturemeasurementunit = temperaturemeasurementunit;
        this.humidity = humidity;
        this.humiditymeasurementunit = humiditymeasurementunit;
        this.time = time;
    }

    public AirQuality_Properties_Object(String room_id, int co2, int temperature, int humidity) {
        this.room_id = room_id;
        this.co2 = co2;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AirQuality_Properties_Object() {
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
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

    @Override
    public String toString() {
        return "{" +
                "room_id='" + room_id + '\'' +
                ", device_id='" + device_id + '\'' +
                ", ventilator='" + ventilator + '\'' +
                ", co2=" + co2 +
                ", co2measurementunit='" + co2measurementunit + '\'' +
                ", temperature=" + temperature +
                ", temperaturemeasurementunit='" + temperaturemeasurementunit + '\'' +
                ", humidity=" + humidity +
                ", humiditymeasurementunit='" + humiditymeasurementunit + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

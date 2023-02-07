package at.jku.clientObjects;

public class Airquality {
int temperature;
int humidity;
int co2;
String timeStamp;

    public Airquality(int temperature, int humidity, int co2, String timeStamp) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
        this.timeStamp = timeStamp;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCo2() {
        return co2;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}

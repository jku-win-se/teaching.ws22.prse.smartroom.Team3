package at.jku.clientObjects;

public class Airquality {
int temperature;
int humidity;
int co2;
String timeStamp;

    /**
     * Class to save an airquility Object
     * @param temperature temperature at given time
     * @param humidity humidtiy at given time
     * @param co2 co2 level at given time
     * @param timeStamp time on which the values were created values
     */

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

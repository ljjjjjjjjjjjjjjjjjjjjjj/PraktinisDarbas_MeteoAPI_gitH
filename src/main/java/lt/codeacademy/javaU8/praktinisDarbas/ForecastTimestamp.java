package lt.codeacademy.javaU8.praktinisDarbas;

public class ForecastTimestamp {
    public String forecastTimeUtc;
    public double airTemperature;
    public double feelsLikeTemperature;
    public int windSpeed;
    public double windGust;
    public int windDirection;
    public int cloudCover;
    public double seaLevelPressure;
    public int relativeHumidity;
    public int totalPrecipitation;
    public String conditionCode;




    @Override
    public String toString () {
        return "Time: " + forecastTimeUtc +
                ", aT= " + airTemperature +
                ", fT= " + feelsLikeTemperature +
                ", windSpeed= " + windSpeed +
                ", windGust= " + windGust +
                ", windDirection= " + windDirection +
                ", cloudCover= " + cloudCover +
                ", relativeHumidity= " + relativeHumidity +
                ", totalPrecipitation= " + totalPrecipitation +
                ", condition: " + conditionCode;

    }


    public String toMySQL () {
        return "'" + forecastTimeUtc + "', " +
                "'" + airTemperature + "', " +
                "'" + feelsLikeTemperature + "', " +
                "'" + windSpeed + "', " +
                "'" + windGust + "', " +
                "'" + windDirection + "', " +
                "'" + cloudCover + "', " +
                "'" + relativeHumidity + "', " +
                "'" + totalPrecipitation + "', " +
                "'" + conditionCode + "'";

    }
}

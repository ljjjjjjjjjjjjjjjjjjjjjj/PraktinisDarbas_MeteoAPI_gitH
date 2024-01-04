package lt.codeacademy.javaU8.praktinisDarbas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ForecastTimestamp {
    public String forecastTimeUtc;
    public int airTemperature;
    public double feelsLikeTemperature;
    public int windSpeed;
    public int windGust;
    public int windDirection;
    public int cloudCover;
    public int seaLevelPressure;
    public int relativeHumidity;
    public double totalPrecipitation;
    public String conditionCode;

    public static DateTimeFormatter datosFormatasH = DateTimeFormatter.ofPattern("yyyy-LLLL-dd  (HH 'h') ", Locale.of("LT"));





    @Override
    public String toString () {

        //            item         =   condition          ?   True(blue) :   False (red)
        String colorA = (airTemperature < 0) ? "\u001B[34m" : "\u001B[31m";
        String colorF = (feelsLikeTemperature < 0) ? "\u001B[34m" : "\u001B[31m";

        String resetColor = "\u001B[0m";  // default colour

        return "Time: " + forecastTimeUtc.substring(0,16) +
                " - aT= " + colorA + airTemperature +  resetColor +
                ", fT= " + colorF + feelsLikeTemperature +  resetColor +
                ", windSpeed= " + windSpeed +
                ", windGust= " + windGust +
                ", windDirection= " + windDirection +
                ", cloudCover= " + cloudCover +
                ", relativeHumidity= " + relativeHumidity +
                ", totalPrecipitation= " + totalPrecipitation +
                ", condition: " + conditionCode;

    }


    // forecastTimeUtc, airTemperature, feelsLikeTemperature, windSpeed, windGust, windDirection, cloudCover, seaLevelPressure, relativeHumidity, totalPrecipitation, conditionCode, +  placeCode,


    public String toMySQL () {
        return  "'" + forecastTimeUtc.substring(0,16) + "', " +
                "'" + airTemperature + "', " +
                "'" + feelsLikeTemperature + "', " +
                "'" + windSpeed + "', " +
                "'" + windGust + "', " +
                "'" + windDirection + "', " +
                "'" + cloudCover + "', " +
                "'" + seaLevelPressure + "', " +
                "'" + relativeHumidity + "', " +
                "'" + totalPrecipitation + "', " +
                "'" + conditionCode + "', ";

    }

    // Getters

    public String getForecastTimeUtc() {
        return forecastTimeUtc;
    }

    public int getAirTemperature() {
        return airTemperature;
    }

    public double getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public String getConditionCode() {
        return conditionCode;
    }
}

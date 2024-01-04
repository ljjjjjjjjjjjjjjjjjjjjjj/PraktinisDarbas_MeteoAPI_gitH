package lt.codeacademy.javaU8.praktinisDarbas;

import java.util.ArrayList;

public class Root {
    public Place place;
    public String forecastType;
    public String forecastCreationTimeUtc;
    public ArrayList<ForecastTimestamp> forecastTimestamps;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder() ;



        if (forecastTimestamps == null || forecastTimestamps. isEmpty ()){
            return sb.toString();

        } else {
        sb.append(place);

        for (ForecastTimestamp frc : forecastTimestamps) {
            sb.append("\t" + frc + "\n");}

        return sb.toString();}
    }


    public String toMySQL(){
        StringBuilder sb = new StringBuilder() ;

        if (forecastTimestamps == null || forecastTimestamps. isEmpty ()){
            return sb.toString();
        } else {

            for (ForecastTimestamp forecast : forecastTimestamps) {
                sb.append("(" + forecast.toMySQL());
                sb.append(place.codeToMySQL() + "), ");
            }

            int lastIndexOfComma = sb.lastIndexOf(",");
            sb.replace(lastIndexOfComma, (lastIndexOfComma + 1), ";");

            return String.valueOf(sb);

        }

    }


}

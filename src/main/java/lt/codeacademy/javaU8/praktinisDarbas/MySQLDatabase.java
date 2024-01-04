package lt.codeacademy.javaU8.praktinisDarbas;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class MySQLDatabase {

    public static void saveDataToMySQL(Root root){

        String url = "jdbc:mysql://localhost:3306/orai";
        String user = "root";
        String password = "ManoMySQL2023*";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            if (connection != null) {
                System.out.println("Connection to DB established");

                String queryPlace = getPlaceString(root);
                String queryForecast = getForecastString(root);

                executeQuery(connection, queryPlace);
                executeQuery(connection, queryForecast);


            } else System.out.println("Connection to DB !NOT! established");

        } catch (Exception e) {
            System.out.println("Error saving forecast data to the database.");
        }
    }




    public static String getPlaceString(Root root) {
        //  If you are adding values for all the columns of the table, you do not need to specify the column names in the SQL query.
        //  However, make sure the order of the values is in the same order as the columns in the table.

        // INSERT INTO place
        // VALUES (placeCode, placeName, administrativeDivision, countryCode);

        if (root != null){
            String placeString ="INSERT INTO place VALUES (" + root.place.toMySQL() + ")";
            System.out.println(placeString);
            return placeString;

        } else  {
            return null;
        }

    }


    public static String getForecastString(Root root) {
        // "INSERT INTO forecast
        // (forecastTimeUtc, airTemperature, feelsLikeTemperature, windSpeed, windGust, windDirection, cloudCover, seaLevelPressure, relativeHumidity, totalPrecipitation, conditionCode, placeCode),
        // (forecastTimeUtc, airTemperature, feelsLikeTemperature, windSpeed, windGust, windDirection, cloudCover, seaLevelPressure, relativeHumidity, totalPrecipitation, conditionCode, placeCode);

        if (root != null) {
            String forecastString = "INSERT INTO forecast VALUES " + root.toMySQL();
            System.out.println(forecastString);
            return forecastString;

        } else {
            System.out.println("Klaida - 'saveForecastTimestampsToDatabase'");
            return null;
        }
    }


    public static void executeQuery(Connection connection, String query) {

        if (query != null) {

            try {
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(query);

                // executeQuery changed to executeUpdate
                // executeUpdate is used for statements that modify the database (INSERT, UPDATE, DELETE)

                System.out.println("Data saved to the database successfully.");

            } catch (Exception e) {
                System.out.println(e);
            }

        } else {
            System.out.println("query != null");
        }
    }

    public static void showDataFromMySQL(){

        String url = "jdbc:mysql://localhost:3306/orai";
        String user = "root";
        String password = "ManoMySQL2023*";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            if (connection != null) {
                System.out.println("Connection to DB established");

                Statement stmt = connection.createStatement();
                String query = "SELECT CONCAT('Location: ', placeCode, ' Time: ', forecastTimeUtc, ' , aT: ', airTemperature, ', fT: ', feelsLikeTemperature, ', windSpeed: ', windSpeed, ', conditionCode: ', conditionCode) AS WeatherInfo FROM forecast LIMIT 100";

                ResultSet rs = stmt.executeQuery(query);

                int i = 1;

                while ( rs.next() ){
                    String data = rs.getString("WeatherInfo");
                    System.out.print("[" + i++ + "] ");

                    System.out.println(data);}

            } else System.out.println("Connection to DB !NOT! established");

        } catch (Exception e) {
            System.out.println("Error showing forecast data from the database.");
        }
    }


}

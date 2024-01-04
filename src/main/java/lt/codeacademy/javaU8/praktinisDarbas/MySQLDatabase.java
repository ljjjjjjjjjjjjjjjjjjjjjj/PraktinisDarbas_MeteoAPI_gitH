package lt.codeacademy.javaU8.praktinisDarbas;

import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static lt.codeacademy.javaU8.praktinisDarbas.ForecastTimestamp.datosFormatasH;

public class MySQLDatabase {

    //<editor-fold desc="Connect info">
    private static final String url = "jdbc:mysql://localhost:3306/orai";
    private static final String user = "root";
    private static final String password = "ManoMySQL2023*";
    //</editor-fold>



    private static final String INSERT_ROOT_SQL =
            "INSERT INTO forecast (forecastTimeUtc, airTemperature, feelsLikeTemperature, windSpeed, windGust, windDirection, cloudCover, seaLevelPressure, relativeHumidity, totalPrecipitation, conditionCode, placeCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    // "INSERT INTO forecast
    // (forecastTimeUtc1, airTemperature2, feelsLikeTemperature3, windSpeed4, windGust5, windDirection6, cloudCover7, seaLevelPressure8, relativeHumidity9, totalPrecipitation10, conditionCode11, placeCode12),
    // (forecastTimeUtc, airTemperature, feelsLikeTemperature, windSpeed, windGust, windDirection, cloudCover, seaLevelPressure, relativeHumidity, totalPrecipitation, conditionCode, placeCode);

    public static void saveDataToMySQL(String duration, Optional<Root> root){

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            if (connection != null) {
                Ui.printConnectionEST();

                if(root.isPresent()){

                String queryPlace = getPlaceString(root);
                executeQuery(connection, queryPlace);

                // Old version:
                // String queryForecast = getForecastString(duration, root);
                // executeQuery(connection, queryForecast);

                // Batch version:
                saveBATCHdataToMySQL(connection, duration, root.get().place.code, root.get().forecastTimestamps);}


            } else Ui.printConnectionNOT();

        } catch (Exception e) {
            Ui.printSaveException();
        }
    }




    public static String getPlaceString(Optional<Root> root) {
        //  If you are adding values for all the columns of the table, you do not need to specify the column names in the SQL query.
        //  However, make sure the order of the values is in the same order as the columns in the table.

        // INSERT INTO place
        // VALUES (placeCode, placeName, administrativeDivision, countryCode);

        if (root.isPresent()){
            String placeString ="INSERT INTO place VALUES (" + root.get().place.toMySQL() + ")";
            Ui.printString(placeString.substring(18));
            return placeString;

        } else  {
            return null;
        }

    }


    public static String getForecastString(String duration, Optional<Root> root) {
        // "INSERT INTO forecast
        // (forecastTimeUtc, airTemperature, feelsLikeTemperature, windSpeed, windGust, windDirection, cloudCover, seaLevelPressure, relativeHumidity, totalPrecipitation, conditionCode, placeCode),
        // (forecastTimeUtc, airTemperature, feelsLikeTemperature, windSpeed, windGust, windDirection, cloudCover, seaLevelPressure, relativeHumidity, totalPrecipitation, conditionCode, placeCode);

        if (root.isPresent()) {
            String forecastString = "INSERT INTO forecast VALUES ";
            if (duration.equalsIgnoreCase("day")){
                forecastString += root.get().toMySQLday();
            } else {
                forecastString += root.get().toMySQL();
            }
            Ui.printString(forecastString);
            return forecastString;

        } else {
            Ui.printSaveException();
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

                Ui.printSavedLocationOK();

            } catch (Exception e) {
                Ui.printSaveException(e);
            }

        } else {
            Ui.printWrongInput();
        }
    }

    public static void showDataFromMySQL(){

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            if (connection != null) {
                Ui.printConnectionEST();

                Statement stmt = connection.createStatement();
                String query = "SELECT CONCAT('Location:', placeCode, ', Time:', forecastTimeUtc, ' , aT:', airTemperature, ', fT:', feelsLikeTemperature, ', windSpeed:', windSpeed, ', conditionCode: ', conditionCode) AS WeatherInfo FROM forecast LIMIT 100";

                ResultSet rs = stmt.executeQuery(query);

                int i = 1;

                while ( rs.next() ){
                    String data = rs.getString("WeatherInfo");
                    Ui.printStringSameLine("[" + i++ + "] ");
                    Ui.printString(data);}

            } else Ui.printConnectionNOT();

        } catch (Exception e) {
            Ui.printShowException();
        }
    }



     // (forecastTimeUtc1, airTemperature2, feelsLikeTemperature3, windSpeed4, windGust5, windDirection6, cloudCover7, seaLevelPressure8, relativeHumidity9, totalPrecipitation10, conditionCode11, placeCode12),
    public static void saveBATCHdataToMySQL(Connection connection, String duration, String placeCode, List<ForecastTimestamp> forecasts) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROOT_SQL);

            if (duration.equalsIgnoreCase("week")){

            for (ForecastTimestamp forecast : forecasts) {
                preparedStatement.setString(1, forecast.forecastTimeUtc.substring(0,16));
                preparedStatement.setDouble(2, forecast.airTemperature);
                preparedStatement.setDouble(3, forecast.feelsLikeTemperature);
                preparedStatement.setDouble(4, forecast.windSpeed);
                preparedStatement.setDouble(5, forecast.windGust);
                preparedStatement.setDouble(6, forecast.windDirection);
                preparedStatement.setDouble(7, forecast.cloudCover);
                preparedStatement.setDouble(8, forecast.seaLevelPressure);
                preparedStatement.setDouble(9, forecast.relativeHumidity);
                preparedStatement.setDouble(10, forecast.totalPrecipitation);
                preparedStatement.setString(11, forecast.conditionCode);
                preparedStatement.setString(12, placeCode);
                preparedStatement.addBatch();

                preparedStatement.executeBatch();
                Ui.printString(preparedStatement.toString().substring(250));

            } Ui.printSavedForecastOK();
            } else {

                for (int i = 0; i < 25; i++) {
                    ForecastTimestamp forecast = forecasts.get(i);
                    preparedStatement.setString(1, forecast.forecastTimeUtc.substring(0,16));
                    preparedStatement.setDouble(2, forecast.airTemperature);
                    preparedStatement.setDouble(3, forecast.feelsLikeTemperature);
                    preparedStatement.setDouble(4, forecast.windSpeed);
                    preparedStatement.setDouble(5, forecast.windGust);
                    preparedStatement.setDouble(6, forecast.windDirection);
                    preparedStatement.setDouble(7, forecast.cloudCover);
                    preparedStatement.setDouble(8, forecast.seaLevelPressure);
                    preparedStatement.setDouble(9, forecast.relativeHumidity);
                    preparedStatement.setDouble(10, forecast.totalPrecipitation);
                    preparedStatement.setString(11, forecast.conditionCode);
                    preparedStatement.setString(12, placeCode);
                    preparedStatement.addBatch();

                    preparedStatement.executeBatch();
                    Ui.printString(preparedStatement.toString().substring(250));
                }
            } Ui.printSavedForecastOK();


        } catch (Exception e) {
            Ui.printSaveExceptionBATCH();
        }
    }


    public static void deleteDataFromMySQL(){
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            if (connection != null) {
                Ui.printConnectionEST();

                Statement stmt = connection.createStatement();

                String updatePlace = "DELETE FROM place;";
                stmt.executeUpdate(updatePlace);

                String updateForecast = "DELETE FROM forecast;";
                stmt.executeUpdate(updateForecast);

                Ui.printDataDeleted();

            } else Ui.printConnectionNOT();

        } catch (Exception e) {
            Ui.printDeleteException();
            System.out.println(e);
        }

    }



}

package lt.codeacademy.javaU8.praktinisDarbas;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        Ui.printWelcome();

        //<editor-fold desc="Background info">
        /* Task:
        Sukurti JAVA console aplikaciją. Reikalavimai:
        ● Bendravimas su vartototoju per console.
        ● Meniu punktai.
        ● Vartotojo pasirinkimas naudojant Scanner.
        ● Prisijungimas prie DB (galima naudoti MySQL).
        ● Prisijungimas prie nutolusio API.
        ● Klaidingos informacijos apdorojimas.
        */

        // API source:
        // https://api.meteo.lt/

        // json converter:
        // https://json2csharp.com/code-converters/json-to-pojo

        // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
        // import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1

        // ObjectMapper om = new ObjectMapper();
        // Root root = om.readValue(myJsonString, Root.class);

     /*
        public class Coordinates{
            public double latitude;
            public double longitude;
        }

        public class ForecastTimestamp{
            public String forecastTimeUtc;
            public int airTemperature;
            public double feelsLikeTemperature;
            public int windSpeed;
            public int windGust;
            public int windDirection;
            public int cloudCover;
            public int seaLevelPressure;
            public int relativeHumidity;
            public int totalPrecipitation;
            public String conditionCode;
        }

        public class Place{
            public String code;
            public String name;
            public String administrativeDivision;
            public String country;
            public String countryCode;
            public Coordinates coordinates;
        }

        public class Root{
            public Place place;
            public String forecastType;
            public String forecastCreationTimeUtc;
            public ArrayList<ForecastTimestamp> forecastTimestamps;
        }
        */
        //</editor-fold>

        Meteo meteo = new Meteo(new Ui());
        meteo.start();






    }
}
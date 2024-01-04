package lt.codeacademy.javaU8.praktinisDarbas;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class Meteo {

    Ui ui;

    public Meteo(Ui ui) {
        this.ui = ui;
    }

    public void start() {
        boolean continueMenu = true;

        while (continueMenu) {
            ui.printMenu();
            String userMenuInput = ui.getUserMenuOption();
            continueMenu = processMenuOption(userMenuInput);

        }
        ui.scannerClose();
    }

    boolean processMenuOption(String userMenuInput) {
        if (userMenuInput.equalsIgnoreCase("q"))
            return false;

        switch (userMenuInput) {
            case "1" -> startWeekMenu();
            case "2" -> startDayMenu();
            case "3" -> showDataBaseForecast();
            case "4" -> deleteDataBaseForecast();
            default -> Ui.printWrongOption();
        }
        return true;
    }


    boolean processWeekMenuOption(String userMenuInput) {
        if (userMenuInput.equalsIgnoreCase("M"))
            return false;

        switch (userMenuInput) {
            case "1" -> showForecast("week", "Vilnius");
            case "2" -> showForecast("week", "Kaunas");
            case "3" -> showForecast("week", searchPlaceName());
            case "4" -> saveForecastToMySQL("week");
            default -> Ui.printWrongOption();
        }
        return true;
    }

    public void startDayMenu() {
        boolean continueDayMenu = true;

        while (continueDayMenu) {
            ui.printDayMenu();
            String userDayMenuInput = ui.getUserMenuOption();
            continueDayMenu = processDayMenuOption(userDayMenuInput);

        }

    }

    boolean processDayMenuOption(String userMenuInput) {
        if (userMenuInput.equalsIgnoreCase("M"))
            return false;

        switch (userMenuInput) {
            case "1" -> showForecast("day", "Vilnius");
            case "2" -> showForecast("day", "Kaunas");
            case "3" -> showForecast("day", searchPlaceName());
            case "4" -> saveForecastToMySQL("day");
            default -> Ui.printWrongOption();
        }
        return true;
    }

    public void startWeekMenu() {
        boolean continueWeekMenu = true;

        while (continueWeekMenu) {
            ui.printWeekMenu();
            String userWeekMenuInput = ui.getUserMenuOption();
            continueWeekMenu = processWeekMenuOption(userWeekMenuInput);

        }

    }


    private Optional<Root> getForecast(String placeCode) {

        if (placeCode.equals("error")) {
            Ui.printNoData();

        } else {

            String url = "https://api.meteo.lt/v1/places/" + placeCode + "/forecasts/long-term";
            return HttpData.getAllDataFromHttp(url);

        }
        return Optional.empty();
    }

    private void showForecast(String duration, String placeCode) {
        if (getForecast(placeCode).isPresent())
            Ui.printRoot(duration, getForecast(placeCode));
    }


    /*
    public static HashMap<String, String> createHmapPlaces () {
        Optional<RootPlace[]> opt = HttpData.getPlaceFromHttp();

        if (opt.isPresent()) {
            RootPlace[] rootPlaces = opt.get();

            HashMap<String, String> hmapPlaces = new HashMap<>();

            for (RootPlace rp : rootPlaces) {
                hmapPlaces.put(rp.name.toLowerCase(), rp.code);
            }
            return hmapPlaces;

        } else return null;
    }
    */


    private String searchPlaceName() {

        Ui.printSelectPlace();     // "Please enter the location: "
        String userInput = ui.getUserInput().toLowerCase(); // "user input"

        List<Place> list = HttpData.getPlacesFromApi();

        if (!list.isEmpty()) {
            for (Place place : list) {
                if (place.name.equalsIgnoreCase(userInput)) {
                    return place.code;
                }
            }
        }
        return "error";
    }


    public void saveForecastToMySQL(String duration) {
        Ui.printSaving();

        String placeCode = searchPlaceName();
        if (getForecast(placeCode).isPresent())
            MySQLDatabase.saveDataToMySQL(duration, getForecast(placeCode));


    }

    public static void showDataBaseForecast() {
        Ui.printShow();
        MySQLDatabase.showDataFromMySQL();

    }


    public static void deleteDataBaseForecast() {
        Ui.printDeleting();
        MySQLDatabase.deleteDataFromMySQL();
    }

    public static void processShortInfo(String duration, Optional<Root> root) {

        if (root.isPresent()) {

            Ui.printFancyLocation(root.get().place.name);


            if (duration.equalsIgnoreCase("week")){

                Map<String, List<ForecastTimestamp>> mapForecasts = root.get().forecastTimestamps.stream()
                        .collect(Collectors.groupingBy(date -> date.forecastTimeUtc.substring(0, 10)));  // eg 2024-01-04

                Map<String, List<ForecastTimestamp>> treeMapForecasts = new TreeMap<>(Comparator.naturalOrder());
                treeMapForecasts.putAll(mapForecasts);

            treeMapForecasts.forEach((date, dayForecastTimestamps) -> {
                double lowestAirTemperature = dayForecastTimestamps.stream()
                        .mapToDouble(forecast -> forecast.airTemperature)
                        .min()
                        .orElse(0);
                String lowestAirTemperatureF = String.format("%.1f", lowestAirTemperature);

                double highestAirTemperature = dayForecastTimestamps.stream()
                        .mapToDouble(forecast -> forecast.airTemperature)
                        .max()
                        .orElse(0);
                String highestAirTemperatureF = String.format("%.1f", highestAirTemperature);

                double averageFeelsLikeTemperature = dayForecastTimestamps.stream()
                        .mapToDouble(forecast -> forecast.feelsLikeTemperature)
                        .average()
                        .orElse(0);
                String averageFeelsLikeTemperatureF = String.format("%.1f", averageFeelsLikeTemperature);

                double averageWindSpeed = dayForecastTimestamps.stream()
                        .mapToDouble(forecast -> forecast.windSpeed)
                        .average()
                        .orElse(0);
                String averageWindSpeedF = String.format("%.1f", averageWindSpeed);

                Map<String, Long> conditionCodeCounts = dayForecastTimestamps.stream()
                        .collect(Collectors.groupingBy(forecast -> forecast.conditionCode, Collectors.counting()));

                String mostOccurringConditionCode = conditionCodeCounts.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse("Weather");

                LocalDate dateString = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
                String dayOfWeek = dateString.getDayOfWeek().toString();

                Ui.printShortInfo(date, dayOfWeek, highestAirTemperatureF, lowestAirTemperatureF, averageFeelsLikeTemperatureF, averageWindSpeedF, mostOccurringConditionCode);
            });


            } else {

                String date = root.get().forecastTimestamps.getFirst().forecastTimeUtc.substring(0, 10);

                List<ForecastTimestamp> firstDayForecastTimestamps = root.get().forecastTimestamps.stream()
                        .filter(timestamp -> timestamp.forecastTimeUtc.startsWith(date))
                        .toList();

                double lowestAirTemperature = firstDayForecastTimestamps.stream()
                        .mapToDouble(ForecastTimestamp::getAirTemperature)
                        .min()
                        .orElse(0);
                String lowestAirTemperatureF = String.format("%.1f", lowestAirTemperature);

                double highestAirTemperature = firstDayForecastTimestamps.stream()
                        .mapToDouble(ForecastTimestamp::getAirTemperature)
                        .max()
                        .orElse(0);
                String highestAirTemperatureF = String.format("%.1f", highestAirTemperature);

                double averageFeelsLikeTemperature = firstDayForecastTimestamps.stream()
                        .mapToDouble(ForecastTimestamp::getFeelsLikeTemperature)
                        .average()
                        .orElse(0);
                String averageFeelsLikeTemperatureF = String.format("%.1f", averageFeelsLikeTemperature);

                double averageWindSpeed = firstDayForecastTimestamps.stream()
                        .mapToDouble(ForecastTimestamp::getWindSpeed)
                        .average()
                        .orElse(0);
                String averageWindSpeedF = String.format("%.1f", averageWindSpeed);

                Map<String, Long> conditionCodeCounts = firstDayForecastTimestamps.stream()
                        .collect(Collectors.groupingBy(ForecastTimestamp::getConditionCode, Collectors.counting()));

                String mostOccurringConditionCode = conditionCodeCounts.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse("Weather");

                LocalDate dateString = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
                String dayOfWeek = dateString.getDayOfWeek().toString();

                Ui.printShortInfo(date, dayOfWeek, highestAirTemperatureF, lowestAirTemperatureF, averageFeelsLikeTemperatureF, averageWindSpeedF, mostOccurringConditionCode);


            }

        } else
            Ui.printWrongInput();

    }


    public static void processShortInfoDAY(String duration, Optional<Root> root) {
        if (root.isPresent()) {
            Ui.printFancyLocation(root.get().place.name);

            List<ForecastTimestamp> firstDayForecastTimestamps = root.get().forecastTimestamps.stream()
                    .filter(timestamp -> timestamp.forecastTimeUtc.startsWith(root.get().forecastTimestamps.get(0).forecastTimeUtc.substring(0, 10)))
                    .collect(Collectors.toList());

            double lowestAirTemperature = firstDayForecastTimestamps.stream()
                    .mapToDouble(ForecastTimestamp::getAirTemperature)
                    .min()
                    .orElse(0);
            String lowestAirTemperatureF = String.format("%.1f", lowestAirTemperature);

            double highestAirTemperature = firstDayForecastTimestamps.stream()
                    .mapToDouble(ForecastTimestamp::getAirTemperature)
                    .max()
                    .orElse(0);
            String highestAirTemperatureF = String.format("%.1f", highestAirTemperature);

            double averageFeelsLikeTemperature = firstDayForecastTimestamps.stream()
                    .mapToDouble(ForecastTimestamp::getFeelsLikeTemperature)
                    .average()
                    .orElse(0);
            String averageFeelsLikeTemperatureF = String.format("%.1f", averageFeelsLikeTemperature);

            double averageWindSpeed = firstDayForecastTimestamps.stream()
                    .mapToDouble(ForecastTimestamp::getWindSpeed)
                    .average()
                    .orElse(0);
            String averageWindSpeedF = String.format("%.1f", averageWindSpeed);

            Map<String, Long> conditionCodeCounts = firstDayForecastTimestamps.stream()
                    .collect(Collectors.groupingBy(ForecastTimestamp::getConditionCode, Collectors.counting()));

            String mostOccurringConditionCode = conditionCodeCounts.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("Weather");

            LocalDate dateString = LocalDate.parse(firstDayForecastTimestamps.get(0).forecastTimeUtc.substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE);
            String dayOfWeek = dateString.getDayOfWeek().toString();

            Ui.printShortInfo(
                    firstDayForecastTimestamps.get(0).forecastTimeUtc.substring(0, 10),
                    dayOfWeek,
                    highestAirTemperatureF,
                    lowestAirTemperatureF,
                    averageFeelsLikeTemperatureF,
                    averageWindSpeedF,
                    mostOccurringConditionCode
            );

        } else {
            Ui.printWrongInput();
        }
    }










} // class end

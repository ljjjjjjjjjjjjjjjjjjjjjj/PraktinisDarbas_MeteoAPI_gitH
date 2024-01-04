package lt.codeacademy.javaU8.praktinisDarbas;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


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

    boolean processMenuOption(String userMenuInput){
        if (userMenuInput.equalsIgnoreCase("q"))
            return false;

        switch (userMenuInput){
            case "1" -> startWeekMenu();
            case "2" -> startDayMenu();
            case "3" -> showDataBaseForecast();
            case "4" -> deleteDataBaseForecast();
            default -> Ui.printWrongOption();
        }
        return true;
    }


    boolean processWeekMenuOption(String userMenuInput){
        if (userMenuInput.equalsIgnoreCase("M"))
            return false;

        switch (userMenuInput){
            case "1" -> showForecast("week", "Vilnius");
            case "2" -> showForecast("week","Kaunas");
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

    boolean processDayMenuOption(String userMenuInput){
        if (userMenuInput.equalsIgnoreCase("M"))
            return false;

        switch (userMenuInput){
            case "1" -> showForecast("day", "Vilnius");
            case "2" -> showForecast("day","Kaunas");
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

    private String searchPlaceName() {

        Ui.printSelectPlace();     // "Please enter the location: "
        String userInput = ui.getUserInput().toLowerCase(); // "user input"

        HashMap<String, String> hmap = createHmapPlaces();


        if (hmap == null || !hmap.containsKey(userInput)) {
            System.out.println("No places were found");
            userInput = "error";

        } else {
            userInput = hmap.get(userInput);
        }


        return userInput;
    }
    */


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

        if (!list.isEmpty()){
        for (Place place : list) {
            if (place.name.equalsIgnoreCase(userInput)) {
                return place.code;
            }
        }
        } return "error";
    }




    public void saveForecastToMySQL(String duration){
        Ui.printSaving();

        String placeCode = searchPlaceName();
        if (getForecast(placeCode).isPresent())
        MySQLDatabase.saveDataToMySQL(duration, getForecast(placeCode));


    }
    public static void showDataBaseForecast(){
        Ui.printShow();
        MySQLDatabase.showDataFromMySQL();

    }




    public static void  deleteDataBaseForecast(){
        Ui.printDeleting();
        MySQLDatabase.deleteDataFromMySQL();
    }





} // class end

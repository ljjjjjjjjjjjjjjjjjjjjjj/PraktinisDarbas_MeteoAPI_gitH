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
            case "1" -> showForecast("Vilnius");
            case "2" -> showForecast("Kaunas");
            case "3" -> showForecast(searchPlaceName());
            case "4" -> saveForecastToMySQL();
            case "5" -> showDataBaseForecast();
            default -> Ui.printWrongOption();
        }
        return true;
    }


    private Root getForecast(String placeCode) {
        Root root = new Root();

        if (placeCode.equals("error")) {
            Ui.printNoData();
            root = null;
        } else {

            String url = "https://api.meteo.lt/v1/places/" + placeCode + "/forecasts/long-term";

            Optional<Root> meteoForecast = HttpData.getAllDataFromHttp(url);

            if (meteoForecast.isPresent()) {
                root = meteoForecast.get();
            }
        }
        return root;
    }

    private void showForecast(String placeCode) {
        Root root = getForecast(placeCode);
        Ui.printRoot(root);
    }




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

    public void saveForecastToMySQL(){
        System.out.println("Saving selected forecast to MySQL");

        String placeCode = searchPlaceName();
        Root root = getForecast(placeCode);

        MySQLDatabase.saveDataToMySQL(root);


    }
    public static void showDataBaseForecast(){
        System.out.println("Show saved forecasts from MySQL ");
        MySQLDatabase.showDataFromMySQL();

    }


    } // class end

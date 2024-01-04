package lt.codeacademy.javaU8.praktinisDarbas;

import java.util.Optional;
import java.util.Scanner;

public class Ui {
    Scanner sc = new Scanner(System.in);

    public void printMenu(){
        Menu:
        System.out.print("""      
                    ┌───────────────────────────────────────┐
                    │               Menu                    │
                    ├───────────────────────────────────────┤
                    │ 1 -  Show week forecast               │
                    │ 2 -  Show day forecast                │
                    │ 3 -  Show saved database              │
                    │ 4 -  Delete saved data from database  │
                    │                                       │
                    │ Q -  Quit                             │
                    └───────────────────────────────────────┘
                    ┌───────────────────────────────────────┐
                    │ Please enter your option from Menu:\s""");

    }

    public static void printWeekMenu() {

        weekForecastMenu:
        System.out.print("""
                                
                ┌───────────────────────────────────────┐
                │               WEEK menu:              │
                ├───────────────────────────────────────┤
                │                                       │
                │ 1 -  Show Vilnius forecast            │
                │ 2 -  Show Kaunas forecast             │
                │ 3 -  Show forecast of other location  │
                │ 4 -  Save week forecasts to database  │
                │                                       │
                │ M - Go back to the primary meniu      │
                │                                       │
                └───────────────────────────────────────┘
                ┌───────────────────────────────────────┐
                │Please select your option:\s""");

    }

    public static void printDayMenu() {

        dayForecastMenu:
        System.out.print("""
                                
                ┌───────────────────────────────────────┐
                │               DAY menu:               │
                ├───────────────────────────────────────┤
                │                                       │
                │ 1 -  Show Vilnius forecast            │
                │ 2 -  Show Kaunas forecast             │
                │ 3 -  Show forecast of other location  │
                │ 4 -  Save day forecasts to database   │
                │                                       │
                │ M - Go back to the primary meniu      │
                │                                       │
                └───────────────────────────────────────┘
                ┌───────────────────────────────────────┐
                │Please select your option:\s""");

    }

    public String getUserMenuOption(){
        String input = sc.nextLine();
        System.out.println("└───────────────────────────────────────┘");
        return input.substring(0,1);
    }

    public void scannerClose() {
        if (sc != null)
            sc.close();
    }

    public String getUserInput(){
        String input = sc.nextLine();
        return input;
    }


    public String getUserInputForAvailablePlaces(){
        String input = sc.nextLine();
        return input;
    }


    public static void printRoot(String duration, Optional<Root> root) {
        if (duration.equalsIgnoreCase("week")) {
            Meteo.processShortInfo("week", root);
            System.out.println(root.get());

        } else {
            Meteo.processShortInfo("day", root);
            System.out.println(root.get().toStringDay());
        }
    }



    public static void printSelectPlace(){
        System.out.print("Please enter the location (please use Lithuanian letters): ");
    }

    public static void printWrongInput(){

        System.out.println("No results found. Wrong selection");
    }

    public static void printWrongOption(){

        System.out.println("Please select from the listed options.");
    }

    public static void printNoData(){
        System.out.println("No data.");
    }

    public static void printWelcome(){
        System.out.println("\nWelcome to Meteo Forecast app\n");
    }

    public static void printSaveException(Exception e){
        System.out.println("Data was NOT saved due to exception (" + e + ")");
    }

    public static void printSaveException(){
        System.out.println("Data was NOT saved due to the exception.");
    }


    public static void printSaveExceptionBATCH(){
        System.out.println("Error saving BATCH data to the database.");
    }

    public static void printSavedForecastOK(){
        System.out.println("Forecast data was saved successfully.");
    }

    public static void printSavedLocationOK(){
        System.out.println("Place data was saved successfully.");
    }

    public static void printShowException(Exception e){
        System.out.println("Data cannot be shown, due to exception (" + e + ")");
    }

    public static void printShowException(){
        System.out.println("Data cannot be shown, due to exception.");
    }

    public static void printDeleteException(){
        System.out.println("Data cannot be deleted, due to exception.");
    }


    public static void printConnectionNOT(){
        System.out.println("Connection to DB was NOT established.");
    }

    public static void printConnectionEST(){
        System.out.println("Connection to DB established.");
    }


    public static void printString(String str){
        System.out.println(str);
    }
    public static void printStringSameLine(String str){
        System.out.print(str);
    }

    public static void printDataDeleted(){
        System.out.println("DB data was deleted.");
    }

    // Method lines:
    public static void printSaving(){
        System.out.println("Saving selected forecast to MySQL.");
    }
    public static void printShow(){
        System.out.println("Showing saved forecasts from MySQL.");
    }
    public static void printDeleting(){
        System.out.println("Deleting forecast data from MySQL.");
    }


    public static void printFancyLocation(String placeName) {

        System.out.printf("┌───────────────────────────────────────┐\n" +
                "│              " + "%-25s│\n" +
                "└───────────────────────────────────────┘\n",
                placeName);
    }



    public static void printShortInfo(String date, String dayOfWeek, String highestAirTemperature, String lowestAirTemperature, String averageFeelsLikeTemperature, String averageWindSpeed, String mostOccurringConditionCode){
        String completeDate = dayOfWeek + " ("+ date + ")";
        String fromTo =  "From " + lowestAirTemperature + " to " +  highestAirTemperature + "°C ";
        String feelsLike =  "Feels like " + averageFeelsLikeTemperature + "°C";
        String windStr = "Wind speed " + averageWindSpeed +"m/s";

        String colorA = (fromTo.chars().filter(ch -> ch == '-').count() == 2) ? "\u001B[34m" : "\u001B[31m";
        String colorF = (feelsLike.contains("-")) ? "\u001B[34m" : "\u001B[31m";
        String resetColor = "\u001B[0m";  // default colour




        System.out.printf(
                        "┌───────────────────────────────────────┐\n" +
                        "│          " + "%-29s" + "│\n" +
                        "├───────────────────────────────────────┤\n" +
                        "│         " + "%s %-29s" + "%s│\n"+
                        "│         " +  "%s %-29s" + "%s│\n"+
                        "│                                       │\n"+
                        "│          " +   "%-29s" + "│\n"+
                        "│          " +  "%-29s" + "│\n"+
                        "└───────────────────────────────────────┘\n",
                completeDate, colorA, fromTo, resetColor, colorF, feelsLike, resetColor, windStr, mostOccurringConditionCode);





/*

        System.out.println("Date: " + date + dayOfWeek );
        System.out.println("Highest Air Temperature: " + highestAirTemperature);
        System.out.println("Lowest Air Temperature: " + lowestAirTemperature);
        System.out.println("\"%.1f%n\",Average Feels Like Temperature: " + averageFeelsLikeTemperature);
        System.out.println("Average Wind Speed: " + averageWindSpeed);
        System.out.println("Most Occurring Condition Code: " + mostOccurringConditionCode);
        System.out.println();
*/




    }




}

package lt.codeacademy.javaU8.praktinisDarbas;

import java.util.List;
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
                    │ 1 -  Show Vilnius forecast            │
                    │ 2 -  Show Kaunas forecast             │
                    │ 3 -  Show forecast of other location  │
                    │ 4 -  Save forecasts to database       │
                    │ 5 -  Show saved database              │
                    │                                       │
                    │ Q -  Quit                             │
                    └───────────────────────────────────────┘
                    ┌───────────────────────────────────────┐
                    │ Iveskite savo pasirinkima:\s""");

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


    public static void printRoot(Root root){
        System.out.println(root);
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

    /*
    public static void printAvailablePlaces(Optional< List<Place> >list){
        System.out.println("There are these availbale locations. Please select list number to continue: ");
        System.out.println(list);
    }
    */

}

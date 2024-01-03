package lt.codeacademy.javaU8.praktinisDarbas;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class ReadValue_of_ObjectMapper {
    public static Optional<Root> readValueOfForecast(String jsonFromMeteo){

        ObjectMapper om = new ObjectMapper();

        try {

            return Optional.of(om.readValue(jsonFromMeteo, Root.class));

        }catch(Exception e){
            Ui.printWrongInput();
        }
        return Optional.empty();
    }


    public static Optional< RootPlace[]> readValueOfPlaces(String jsonFromMeteo) {

        ObjectMapper om = new ObjectMapper();


        try {
            RootPlace[] rootPlaces = om.readValue(jsonFromMeteo, RootPlace[].class);
            return Optional.of(rootPlaces);

        } catch (Exception e) {
            System.out.println("Klaida 'readValueOfPlaces' ");
            return Optional.empty();
        }
    }





}

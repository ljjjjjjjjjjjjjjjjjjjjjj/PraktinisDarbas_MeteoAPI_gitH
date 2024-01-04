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


    public static List<Place> readValueOfPlace(String jsonFromMeteo){
        ObjectMapper om = new ObjectMapper();

        try {
            return om.readValue(jsonFromMeteo, om.getTypeFactory().constructCollectionType(List.class, Place.class));

        }catch(Exception e){
            Ui.printWrongInput();
        }
        return Collections.emptyList();

    }




}

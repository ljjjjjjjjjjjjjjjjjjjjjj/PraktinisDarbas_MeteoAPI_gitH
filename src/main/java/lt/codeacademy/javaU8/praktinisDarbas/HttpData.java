package lt.codeacademy.javaU8.praktinisDarbas;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class HttpData {
    public static Optional<Root> getAllDataFromHttp(String url){

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri( URI.create(url) )
                .GET()
                .build();

        try {

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String jsonFromMeteo = response.body();

            return ReadValue_of_ObjectMapper.readValueOfForecast(jsonFromMeteo);

        } catch (IOException | InterruptedException e) {
            Ui.printWrongInput();
            return Optional.empty();
        }

    }


    public static Optional <RootPlace[]> getPlaceFromHttp() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri( URI.create("https://api.meteo.lt/v1/places") )
                .GET()
                .build();
        try {

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String jsonFromMeteo = response.body();


            //Optional <RootPlace[]> opt
            return  ReadValue_of_ObjectMapper.readValueOfPlaces(jsonFromMeteo);

        } catch (IOException | InterruptedException e) {
            //Ui.printWrongInput();
            System.out.println("Klaida - 'getPlaceMap'");
            return Optional.empty();
        }


    }



}


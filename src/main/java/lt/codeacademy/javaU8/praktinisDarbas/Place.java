package lt.codeacademy.javaU8.praktinisDarbas;

import java.util.HashMap;

public class Place {

    public String code;
    public String name;
    public String administrativeDivision;
    public String country;
    public String countryCode;
    public Coordinates coordinates;

   //

    @Override
    public String toString () {
        return  "Oru prognozes\n" +
                "-------------\n" +
                name +
                " ("+ administrativeDivision + ", " + countryCode + ")\n";
    }


    public String toMySQL () {
        return "'" + code + "', " +
                "'" + name + "', " +
                "'" + administrativeDivision + "', " +
                "'" + countryCode +"'";
    }

    public String codeToMySQL () {
        return "'" + code + "'";
    }
}

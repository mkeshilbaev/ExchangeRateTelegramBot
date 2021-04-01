package parser;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class ExchangeRateParser {


    //25babe024a4d1f09f7b8a36cfc00718e
    public static String getExchangeRate(String message, domain.ExchangeRate exchangeRate) throws IOException {
        URL url = new URL("https://api.exchangerate-api.com/v4/latest/" + message);

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while(in.hasNext()){
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        exchangeRate.setBase(object.getString("base"));
        exchangeRate.setDate(object.getString("date"));

        JSONObject rates = object.getJSONObject("rates");
        exchangeRate.setKzt(rates.getDouble("KZT"));

        return "Date: " + exchangeRate.getDate() + "\n" +
                "Base: " + exchangeRate.getBase() + "\n" +
                "KZT = " + exchangeRate.getKzt();
    }
}

package it.dev.cleto.locations.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BuosiUtils {
    public static final int LIMIT = 25;
    public static final String SPACE = " ";
    public static final String MINUS = "-";
    public static final String UNDERSCORE = "_";
    public static final String COMMA = ",";
    public static final String DOUBLE_QUOTES = "\"";
    public static final String LOCATION_SEPARATOR = "/";
    public static final String URL_SEPARATOR = ";";
    public static final String CSV_SEPARATOR = ";";
    public static final String X_RAPID_API_HOST = "x-rapidapi-host";
    public static final String X_RAPID_API_KEY = "x-rapidapi-key";
    public static final String API_KEY = "6da520f303msh448ab4db42673dfp1dcd36jsn333e931083f3";
    public static final String BASE_RESULT_URL = "https://www.google.it/maps/dir/";
    public static final String API_URL_GEO_CODING = "https://trueway-geocoding.p.rapidapi.com/Geocode?language=it&country=it&address=";
    public static final String API_URL_DIRECTION = "https://trueway-directions2.p.rapidapi.com/FindDrivingRoute?optimize=true&avoid_tolls=true&stops=";
    public static final String API_URL_SHORTIFY = "https://shorturl-sfy-cx.p.rapidapi.com/?url=";
    public static final String API_HOST_GEO_CODING = "trueway-geocoding.p.rapidapi.com";
    public static final String API_HOST_DIRECTION = "trueway-directions2.p.rapidapi.com";
    public static final String API_HOST_SHORTIFY = "shorturl-sfy-cx.p.rapidapi.com";
    public static final String CAP_21020 = "21020";
    public static final String CAP_21040 = "21040";
    public static final String CAP_21049 = "21049";
    public static final String CAP_21050 = "21050";
    public static final String CAP_22070 = "22070";
    public static final String PROV_VA = "VA";
    public static final String PROV_CO = "CO";

    public static String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    public static String convertToTitleCaseSplitting(String text, String separator) {
        if (text == null || text.isEmpty()) return "";
        return Arrays.stream(text.split(separator))
                .map(BuosiUtils::manageWord)
                .collect(Collectors.joining(separator));
    }

    private static String manageWord(String word) {
        return word.isEmpty() ? word : capitalized(word);
    }

    private static String capitalized(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
    }
}

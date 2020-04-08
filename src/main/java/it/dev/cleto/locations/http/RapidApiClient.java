package it.dev.cleto.locations.http;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import it.dev.cleto.locations.exceptions.ExceptionMissmatchedCity;
import it.dev.cleto.locations.exceptions.NoGeoCodingRouteAvilableException;
import it.dev.cleto.locations.exceptions.NoRouteAvilableException;
import it.dev.cleto.locations.model.GeoLocation;
import it.dev.cleto.locations.model.buosi.BuosiOrder;
import it.dev.cleto.locations.utils.Jsonizable;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.List;

import static it.dev.cleto.locations.utils.BuosiUtils.*;

@Log4j
public class RapidApiClient {

    public static GeoLocation invokeApiGeoCoding(String address, BuosiOrder buosiOrder) throws NoGeoCodingRouteAvilableException {
        final String url = API_URL_GEO_CODING + address;
        try {
            Response response = doGet(url, API_HOST_GEO_CODING);
            final String responseAsString = response.body().string();
            GeoCodingResponse geoCodingResponse = Jsonizable.fromJson(responseAsString, GeoCodingResponse.class);
            geoCodingResponse.check(buosiOrder);
            return geoCodingResponse.getResults().get(0).getLocation();
        } catch (IOException | ExceptionMissmatchedCity e) {
            throw new NoGeoCodingRouteAvilableException(buosiOrder.getCompleteAddress());
        }
    }

    public static List<Integer> invokeApiStopOrder(String stops) throws NoRouteAvilableException {
        final String url = API_URL_DIRECTION + stops;
        Response response;
        try {
            response = doGet(url, API_HOST_DIRECTION);
            String responseAsString = null;
            if (response.isSuccessful()) {
                responseAsString = response.body().string();
                DirectionsResponse directionsResponse = Jsonizable.fromJson(responseAsString, DirectionsResponse.class);
                return directionsResponse.getRoute().getWaypoints_order();
            }
            else throw new NoRouteAvilableException(responseAsString);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new NoRouteAvilableException();
        }
    }

    public static String shortUrl(String longUrl) {
        final String url = API_URL_SHORTIFY + longUrl;
        Response response;
        try {
            response = doGet(url, API_HOST_SHORTIFY);
            if (response.isSuccessful()) return response.body().string();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return longUrl;
    }

    private static Response doGet(String url, String headerHost) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(X_RAPIDAPI_HOST, headerHost)
                .addHeader(X_RAPIDAPI_KEY, API_KEY)
                .build();
        return client.newCall(request).execute();
    }
}

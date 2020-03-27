package it.dev.cleto.locations.google;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Log4j
public class GoogleLocations {


    public static void main(String[] args) {

        GoogleLocations geoLocation = new GoogleLocations();
        try {
            geoLocation.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String BASE = "https://maps.googleapis.com/maps/api/directions/json";
    String ORIGIN = "?origin=";
    String DESTINATION = "&destination=";
    String SEPARATOR = "|";
    String WAYPOINTS = "&waypoints==optimize:true";
    String KEY = "&key=AIzaSyAp2ndYejQWIPduM1RCeg9sD6jU2TZtYkQ";
    String START = "Via salvo d'Acquisto,2,21100,Varese,Va";
    String TO = "Via salvo d'Acquisto,2,21100,Varese,Va";

    private void run() throws IOException {
        final String stringUrl = createUrl();
        URL url = new URL(stringUrl);
        log.info("----------------");
        log.info(url);
        log.info("----------------");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        log.info("----------------");
        log.info(status);
        log.info("----------------");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        log.info("****************");
        log.info(content.toString());
        log.info("****************");
        in.close();
        con.disconnect();
    }

    // 0 casa          45.822725,8.819002
    // 1 laguna        45.810295,8.818293
    // 2 bogno         45.84036,8.658506
    // 3 sara          45.75033,8.813648
    // 4 cardi         45.841372,8.712551
    // 5 casa          45.822725,8.819002

    //45.822725,8.819002;45.810295,8.818293;45.84036,8.658506;45.75033,8.813648;45.841372,8.712551;45.822725,8.819002


    private List<String> createAddresses() {
        String laguna = "Via garoni,7,21100,Varese,Va";
        String guido = "Via gorizia,22,21023,Besozzo,Va";
        String sara = "Via Campo Dei Fiori, 32, 21040 Oggiona VA";
        String cardi = "Via Milano, 1, 21026 Gavirate VA";

        List<String> adresses = Lists.newLinkedList();
        adresses.add(laguna);
        adresses.add(guido);
        adresses.add(sara);
        adresses.add(cardi);
        return adresses;
    }

    private String createUrl() {
        List<String> adresses = createAddresses();
        String waipoints = createWaipointRequest(adresses);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BASE);
        stringBuilder.append(ORIGIN).append(purgeAddress(START));
        stringBuilder.append(WAYPOINTS).append(waipoints);
        stringBuilder.append(DESTINATION).append(purgeAddress(TO));
        stringBuilder.append(KEY);
        return stringBuilder.toString();
    }

    private String createWaipointRequest(List<String> adresses) {
        StringBuilder stringBuilder = new StringBuilder();
        adresses.forEach(s -> stringBuilder.append(SEPARATOR).append(purgeAddress(s)));
        return stringBuilder.toString();
    }

    private String purgeAddress(String address) {
        return address.replaceAll(" ", "+");
    }

}

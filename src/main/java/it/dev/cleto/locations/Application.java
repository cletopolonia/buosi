package it.dev.cleto.locations;

import it.dev.cleto.locations.exceptions.NoGeoCodingRouteAvilableException;
import it.dev.cleto.locations.exceptions.NoRouteAvilableException;
import it.dev.cleto.locations.model.GeoLocation;
import it.dev.cleto.locations.model.buosi.BuosiOrder;
import it.dev.cleto.locations.model.buosi.BuosiResults;
import it.dev.cleto.locations.report.FileManager;
import lombok.extern.log4j.Log4j;

import javax.naming.SizeLimitExceededException;
import java.io.IOException;
import java.util.List;

import static it.dev.cleto.locations.http.RapidApiClient.invokeApiGeoCoding;
import static it.dev.cleto.locations.http.RapidApiClient.invokeApiStopOrder;

@Log4j
public class Application {

    public static void main(String[] args) throws NoRouteAvilableException {
        Application application = new Application();
        for (int i = 1; true; i++) {
            try {
                application.calculateRoute(i);
            } catch (NullPointerException e) {
                log.info(e.getMessage());
            } catch (SizeLimitExceededException | IOException | NoGeoCodingRouteAvilableException e) {
                log.error(e.getMessage(), e);
            }
            System.exit(1);
        }
    }

    private void calculateRoute(int n) throws SizeLimitExceededException, NoGeoCodingRouteAvilableException, NoRouteAvilableException, IOException {
        BuosiResults buosiResults = new BuosiResults();
        List<BuosiOrder> orders = FileManager.readOrdersFromCSV(n);
        for (BuosiOrder order : orders) {
            GeoLocation geo = invokeApiGeoCoding(order.getPurgeAddress(), order);
            order.setLocation(geo);
        }
        buosiResults.setOrders(orders);
        // lista degli stops concatenati
        String stops = buosiResults.generateLocationsString();
//        log.error(buosiResults.printLocationsString());
        // recupero l'ordine degli stop
        List<Integer> stepOrder = invokeApiStopOrder(stops);
        buosiResults.setAllStepOrder(stepOrder);
        log.info(buosiResults.getRoute());
        FileManager.writeOrdersInCsv(n, buosiResults);
    }
}

package it.dev.cleto.locations.model.buosi;

import it.dev.cleto.locations.utils.BuosiUtils;
import lombok.Data;

import javax.naming.SizeLimitExceededException;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

import static it.dev.cleto.locations.http.RapidApiClient.shortUrl;
import static it.dev.cleto.locations.utils.BuosiUtils.*;

@Data
public class BuosiResults {
    private List<BuosiOrder> orders;
    private String route;

    public String generateLocationsString() throws SizeLimitExceededException {
        if (orders.size() > LIMIT) throw new SizeLimitExceededException();
        StringJoiner sb = new StringJoiner(URL_SEPARATOR);
        for (BuosiOrder order : orders)
            sb.add(order.getLocation().getGeoLocation());
        return BuosiUtils.encode(sb.toString());
    }

    public void setAllStepOrder(List<Integer> stepOrder) {
        StringBuilder sb2 = new StringBuilder();
        sb2.append(BASE_RESULT_URL);
        final int[] index = {0};
        stepOrder.forEach(val -> {
            orders.get(val).setStopOrder(index[0]++);
            sb2.append((orders.get(val).getPurgeAddress())).append(LOCATION_SEPARATOR);
        });
        orders.sort(Comparator.comparing(BuosiOrder::getId));
        this.route = getShortifyUrl(sb2.toString());
    }

    private String getShortifyUrl(String sb2) {
        return shortUrl(encode(sb2)).split(DOUBLE_QUOTES)[1];
    }

}

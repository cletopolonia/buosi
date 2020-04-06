package it.dev.cleto.locations.model.buosi;

import com.google.common.collect.Maps;
import it.dev.cleto.locations.model.GeoLocation;
import it.dev.cleto.locations.utils.BuosiUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import static it.dev.cleto.locations.utils.BuosiUtils.COMMA;
import static it.dev.cleto.locations.utils.BuosiUtils.SPACE;

@Data
@NoArgsConstructor
public class BuosiOrder {

    private String id;
    private String name;
    private String surname;
    private String address;
    private String city;
    private String cap;
    private String prov = "VA";
    private GeoLocation location;
    private int stopOrder;

    private static Map<String, String> capMap = Maps.newHashMap();

    private static final String CAP_21040 = "21040";

    static {
        capMap.put("castiglione olona", "21043");
        capMap.put("malnate", "21046");
        capMap.put("tradate", "21049");
        capMap.put("varese", "21100");
        capMap.put("luvinate", "21020");
        capMap.put("casciago", "21020");
        capMap.put("lozza", CAP_21040);
        capMap.put("vedano olona", CAP_21040);
        capMap.put("venegono inferiore", CAP_21040);
        capMap.put("venegono superiore", CAP_21040);
    }

    public static BuosiOrder from(String[] attributes) {
        BuosiOrder buosiOrder = new BuosiOrder();
        buosiOrder.setId(attributes[0].trim());
        buosiOrder.setName(attributes[1].trim().toLowerCase());
        buosiOrder.setSurname(attributes[2].trim().toLowerCase());
        buosiOrder.setAddress(attributes[3].trim().toLowerCase());
        final String city = attributes[4].trim().toLowerCase();
        buosiOrder.setCity(city);
        buosiOrder.setCap(capMap.get(city));
        return buosiOrder;
    }

    public String getCompleteAddress() {
        StringBuilder sb = new StringBuilder(this.address);
        sb.append(COMMA).append(SPACE);
        sb.append(this.cap);
        sb.append(COMMA).append(SPACE);
        sb.append(this.city);
        sb.append(COMMA).append(SPACE);
        sb.append(this.prov);
        return sb.toString();
    }

    public String getPurgeAddress() {
        return BuosiUtils.encode(BuosiUtils.convertToTitleCaseSplitting(BuosiUtils.convertToTitleCaseSplitting(getCompleteAddress(), SPACE), COMMA));
    }
}
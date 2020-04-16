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
    private String prov;
    private GeoLocation location;
    private int stopOrder;

    private static Map<String, String> capMap = Maps.newHashMap();

    public static final String CAP_21020 = "21020";
    private static final String CAP_21040 = "21040";
    public static final String CAP_21049 = "21049";
    private static final String CAP_21050 = "21050";
    public static final String CAP_22070 = "22070";


    static {
        capMap.put("azzate", "210022");
        capMap.put("castiglione olona", "21043");
        capMap.put("malnate", "21046");
        capMap.put("induno olona", "21056");
        capMap.put("varese", "21100");
        capMap.put("uggiate trevano", "22029");
        capMap.put("bodio lomnago", CAP_21020);
        capMap.put("buguggiate", CAP_21020);
        capMap.put("casale litta", CAP_21020);
        capMap.put("casciago", CAP_21020);
        capMap.put("daverio", CAP_21020);
        capMap.put("inarzo", CAP_21020);
        capMap.put("luvinate", CAP_21020);
        capMap.put("castronno", CAP_21040);
        capMap.put("gornate-olona", CAP_21040);
        capMap.put("lozza", CAP_21040);
        capMap.put("vedano olona", CAP_21040);
        capMap.put("venegono inferiore", CAP_21040);
        capMap.put("venegono superiore", CAP_21040);
        capMap.put("abbiate guazzone", CAP_21049);
        capMap.put("tradate", CAP_21049);
        capMap.put("cairate", CAP_21050);
        capMap.put("lonate ceppino", CAP_21050);
        capMap.put("albiolo", CAP_22070);
        capMap.put("appiano gentile", CAP_22070);
        capMap.put("binago", CAP_22070);
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
        buosiOrder.setProvFromCity(city);
        return buosiOrder;
    }

    private void setProvFromCity(String city) {
        switch (city) {
            case "albiolo":
            case "appiano gentile":
            case "binago":
            case "uggiate trevano":
                this.prov = "CO";
                break;
            default:
                this.prov = "VA";
                break;
        }
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
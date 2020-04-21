package it.dev.cleto.locations.model.buosi;

import it.dev.cleto.locations.model.GeoLocation;
import it.dev.cleto.locations.utils.BuosiUtils;
import it.dev.cleto.locations.utils.CityCapProv;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import static it.dev.cleto.locations.utils.BuosiUtils.*;

@Data
@NoArgsConstructor
@Log4j
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

    public static BuosiOrder from(String[] attributes) {
        log.info(attributes[0]);
        BuosiOrder buosiOrder = new BuosiOrder();
        buosiOrder.setId(attributes[0].trim());
        buosiOrder.setName(attributes[1].trim().toLowerCase());
        buosiOrder.setSurname(attributes[2].trim().toLowerCase());
        buosiOrder.setAddress(attributes[3].trim().toLowerCase());
        final String city = attributes[4].trim().toLowerCase();
        buosiOrder.setCity(city);
        buosiOrder.setCap(getCapFromCity(city));
        buosiOrder.setProv(getProvFromCity(city));
        return buosiOrder;
    }

    protected static String getCapFromCity(String city) {
        return CityCapProv.valueOf(purgeCity(city)).getCap();
    }

    protected static String getProvFromCity(String city) {
        return CityCapProv.valueOf(purgeCity(city)).getProv();
    }

    protected static String purgeCity(String city) {
        return city.toUpperCase().replace(SPACE, UNDERSCORE).replace(MINUS, UNDERSCORE);
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
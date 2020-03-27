package it.dev.cleto.locations;

import it.dev.cleto.locations.model.buosi.BuosiOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuosiOrderTest {

    private BuosiOrder order;

    @BeforeEach
    void setup() {
        order = BuosiOrder.from(new String[]{"sede", "Francesco", "Buosi", "Via Baracca 18", "Venegono Superiore", "21040", "VA"});
    }

    @Test
    void getCompleteAddress() {
        assertEquals("Via Baracca 18, 21040, Venegono Superiore, VA", order.getCompleteAddress());
    }

    @Test
    void getPurgeAddress() {
        assertEquals("Via+baracca+18%2C+21040%2C+venegono+superiore%2C+va", order.getPurgeAddress());
    }
}
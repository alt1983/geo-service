import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeoServiceImplTest {
    @Test
    void test_GeoServiceImpl_byIp_validArgument() {
        GeoService geoService = new GeoServiceImpl();
        GeoServiceImpl geo = new GeoServiceImpl();
        Location lMoscowLenina = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        Location lNew10th = new Location("New York", Country.USA, " 10th Avenue", 32);

        String[] ips = {geo.MOSCOW_IP, geo.NEW_YORK_IP, "172.", "96.", geo.LOCALHOST};
        Country[] countries = {lMoscowLenina.getCountry(), lNew10th.getCountry(), Country.RUSSIA, Country.USA, null};

        for (int i = 0; i < countries.length; i++) {
            Assertions.assertEquals(countries[i], geoService.byIp(ips[i]).getCountry());
        }

    }

    @Test
    public void test_GeoServiceImpl_byIp_nullArgument_throwsException() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        assertThrows(NullPointerException.class, () -> {
            geoService.byIp(null);
        });
    }
}

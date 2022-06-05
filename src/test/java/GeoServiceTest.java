import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.startsWith;

public class GeoServiceTest {

    @Test
    void test_GeoService_usa() {
        String exp = "Welcome";
        String ipAddress = "96.123.12.19";
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("96."))).thenReturn(new Location("New York", Country.USA, null, 0));
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn(exp);
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAddress);
        Assertions.assertEquals(exp, messageSender.send(headers));
    }

    @Test
    void test_GeoService_rus() {
        String exp = "Добро пожаловать";
        String ipAddress = "172.0.32.11";
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("172."))).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn(exp);
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAddress);
        Assertions.assertEquals(exp, messageSender.send(headers));
    }

    @Test
    void test_GeoServiceImpl_byIp_validArgument() {
        GeoService geoService = new GeoServiceImpl();
        Location lMoscowLenina = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        Location lNew10th = new Location("New York", Country.USA, " 10th Avenue", 32);
        String MOSCOW_IP = "172.0.32.11";
        String NEW_YORK_IP = "96.44.183.149";
        Assertions.assertEquals(lMoscowLenina.getCountry(), geoService.byIp(MOSCOW_IP).getCountry());
        Assertions.assertEquals(lNew10th.getCountry(), geoService.byIp(NEW_YORK_IP).getCountry());
    }

    @Test
    public void test_GeoServiceImpl_byIp_nullArgument_throwsException() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        assertThrows(NullPointerException.class, () -> {
            geoService.byIp(null);
        });
    }

    @Test
    void test_LocalizationServiceImpl_locale_validArgument() {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String expRus = "Добро пожаловать";
        String expUsa = "Welcome";
        Assertions.assertEquals(expRus, localizationService.locale(Country.RUSSIA));
        Assertions.assertEquals(expUsa, localizationService.locale(Country.USA));
    }

    @Test
    public void test_LocalizationServiceImpl_locale_throwsException() {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        assertThrows(NullPointerException.class, () -> {
            localizationService.locale(null);
        });
    }
    
}

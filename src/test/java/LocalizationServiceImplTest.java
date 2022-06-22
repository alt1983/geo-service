import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocalizationServiceImplTest {

    @Test
    void test_LocalizationServiceImpl_locale_validArgument() {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String expRus = "Добро пожаловать";
        String expFor = "Welcome";
        Country[] foreign = {Country.USA, Country.GERMANY, Country.BRAZIL};
        Assertions.assertEquals(expRus, localizationService.locale(Country.RUSSIA));
        for (int i = 0; i < foreign.length; i++) {
            Assertions.assertEquals(expFor, localizationService.locale(foreign[i]));
        }
    }

    @Test
    public void test_LocalizationServiceImpl_locale_throwsException() {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        assertThrows(NullPointerException.class, () -> {
            localizationService.locale(null);
        });
    }
}

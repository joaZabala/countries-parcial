package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDto;
import ar.edu.utn.frc.tup.lciii.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Provider;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class CountryServiceTest {
    @SpyBean
    CountryService countryService;
    @MockBean
    RestTemplate restTemplate;


    @Test
    void getCountriesDto() {

        Country country = new Country();
        country.setName("Argentina");
        country.setCode("ARG");

        List<Country> list = new ArrayList<>();
        list.add(country);

        List<Map<String, Object>> response = new ArrayList<>();
        Map<String, Object> country1 = new HashMap<>();
        country1.put("name", Collections.singletonMap("common", "España"));
        country1.put("cca3", "ESP");
        country1.put("population", 46);
        country1.put("area", 50);
        country1.put("region", "Europe");
        country1.put("languages", Collections.singletonMap("spa", "Spanish"));
        country1.put("borders", Arrays.asList("AND", "FRA", "GIB", "PRT"));
        response.add(country1);
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(response);

        when(countryService.getAllCountries()).thenReturn(list);

        List<CountryDto> countryDtos = countryService.getCountriesDto("" , "");

        assertEquals(1, countryDtos.size());
        assertEquals("Argentina", list.get(0).getName());
        assertEquals("ARG", list.get(0).getCode());
    }

    @Test
    public void testGetAllCountries() throws Exception {

        List<Map<String, Object>> response = new ArrayList<>();
        Map<String, Object> country1 = new HashMap<>();
        country1.put("name", Collections.singletonMap("common", "España"));
        country1.put("cca3", "ESP");
        country1.put("population", 46);
        country1.put("area", 50);
        country1.put("region", "Europe");
        country1.put("languages", Collections.singletonMap("spa", "Spanish"));
        country1.put("borders", Arrays.asList("AND", "FRA", "GIB", "PRT"));
        response.add(country1);

        Map<String, Object> country2 = new HashMap<>();
        country2.put("name", Collections.singletonMap("common", "Francia"));
        country2.put("cca3", "FRA");
        country2.put("population", 67);
        country2.put("area", 50);
        country2.put("region", "Europe");
        country2.put("languages", Collections.singletonMap("fra", "French"));
        country2.put("borders", Arrays.asList("BEL", "ESP", "ITA", "LUX", "MCO", "DEU"));
        response.add(country2);

        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(response);


        List<Country> countries = countryService.getAllCountries();

        // Verificar la respuesta
        assertNotNull(countries);
        assertEquals(2, countries.size());
        assertEquals("España", countries.get(0).getName());
        assertEquals("ESP", countries.get(0).getCode());
        assertEquals(46, countries.get(0).getPopulation());
        assertEquals("Europe", countries.get(0).getRegion());
        assertEquals(Collections.singletonMap("spa", "Spanish"), countries.get(0).getLanguages());

        assertEquals("Francia", countries.get(1).getName());
        assertEquals("FRA", countries.get(1).getCode());
        assertEquals(67, countries.get(1).getPopulation());
        assertEquals(50, countries.get(1).getArea());
        assertEquals("Europe", countries.get(1).getRegion());
        assertEquals(Collections.singletonMap("fra", "French"), countries.get(1).getLanguages());
    }

    @Test
    public void testMapToCountry() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Crear un mapa de datos de país
        Map<String, Object> countryData = new HashMap<>();
        countryData.put("name", Collections.singletonMap("common", "España"));
        countryData.put("cca3", "ESP");
        countryData.put("population", 46);
        countryData.put("area", 50);
        countryData.put("region", "Europe");
        countryData.put("languages", Collections.singletonMap("spa", "Spanish"));

        Method method = CountryService.class.getDeclaredMethod("mapToCountry", Map.class);
        method.setAccessible(true);

        Country country = (Country) method.invoke(countryService, countryData);

        // Verificar la respuesta
        assertNotNull(country);
        assertEquals("España", country.getName());
        assertEquals("ESP", country.getCode());
        assertEquals(46, country.getPopulation());
        assertEquals(50, country.getArea());
        assertEquals("Europe", country.getRegion());
        assertEquals(Collections.singletonMap("spa", "Spanish"), country.getLanguages());

    }
}
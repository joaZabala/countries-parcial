package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestCountry;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryService {
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<Country> getAllCountries() {
        String url = "https://restcountries.com/v3.1/all";
        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
        return response.stream().map(this::mapToCountry).collect(Collectors.toList());
    }


    private Country mapToCountry(Map<String, Object> countryData) {
        Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
        return Country.builder()
                .name((String) nameData.get("common"))
                .code((String) countryData.get("cca3"))
                .population(((Number) countryData.get("population")).longValue())
                .area(((Number) countryData.get("area")).doubleValue())
                .region((String) countryData.get("region"))
                .languages((Map<String, String>) countryData.get("languages"))
                .continent((List<String>) countryData.get("continents"))
                .borders((List<String>) countryData.get("borders"))
                .build();
    }


    public List<CountryDto> getCountriesDto(String name, String code) {

        List<CountryDto> countryDtos = new ArrayList<>();
        List<Country> countryList = getAllCountries();

        if (Objects.nonNull(name) && !name.isEmpty()) {
            countryList = countryList.stream().filter(c -> c.getName().equalsIgnoreCase(name)).toList();
        } else if (Objects.nonNull(code) && !code.isEmpty()) {
            countryList = countryList.stream().filter(c -> c.getCode().equalsIgnoreCase(code)).toList();
        }

        for (Country c : countryList) {
            CountryDto countryDto = mapToDTO(c);
            countryDtos.add(countryDto);
        }
        return countryDtos;
    }

    public List<CountryDto> getCountriesByContinent(String continent) {

        List<CountryDto> countryDtos = new ArrayList<>();
        List<Country> countryList = getAllCountries();

        for (Country c : countryList) {
            if(c.getContinent().get(0).equalsIgnoreCase(continent)){

                CountryDto countryDto = mapToDTO(c);
                countryDtos.add(countryDto);
            }
        }
        return countryDtos;
    }

    public List<CountryDto> getByLanguague(String language) {

       return getAllCountries().stream().filter( country -> country.getLanguages() != null &&
                       country.getLanguages().values().stream()
                .anyMatch(lang -> lang.equalsIgnoreCase(language)))
               .map(this:: mapToDTO)
                .collect(Collectors.toList());
    }

    //TODO : una forma de obtener el mayor
    public CountryDto CountryWithMostBorders(){

        Country countryy = getAllCountries().stream().max(Comparator.comparingInt(country ->
                        country.getBorders() != null ? country.getBorders().size() : 0 )).get() ;

        int border = countryy.getBorders().size();

        return mapToDTO(countryy);
    }

    public CountryDto mostBorders(){

        Country most = new Country();
        boolean firts = true;

        for (Country c : getAllCountries()){

            if(firts){
                most = c;
                firts=false;
            }

            int mostActual = most.getBorders() != null ? most.getBorders().size() : 0;
            int actualBorders = c.getBorders() != null ? c.getBorders().size() : 0;

            if(mostActual < actualBorders){
                most = c ;
            }
        }
        return mapToDTO(most);
    }


    public List<CountryDto> post(RequestCountry requestCountry){

        List<Country> countryList = getAllCountries();
        Collections.shuffle(countryList);

        List<CountryDto> limitList = countryList.stream().
                limit(requestCountry.getAmountOfCountryToSave())
                .map(this::mapToDTO).toList();
        return  limitList;
    }

    private CountryDto mapToDTO(Country country) {
        return new CountryDto(country.getCode(), country.getName());
    }


}
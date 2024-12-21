package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestCountry;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDto>> getCountries(@RequestParam(required = false) String name ,
                                                         @RequestParam(required = false) String code) {
        return ResponseEntity.ok(countryService.getCountriesDto(name , code));
    }

    @GetMapping("/countries/{continent}/continent")
    public ResponseEntity<List<CountryDto>> getCountriesByContinent(@PathVariable String continent) {
        return ResponseEntity.ok(countryService.getCountriesByContinent(continent));
    }


    @GetMapping("/countries/{language}/language")
    public ResponseEntity<List<CountryDto>> getByLanguage(@PathVariable String language){
        return ResponseEntity.ok(countryService.getByLanguague(language));

    }

    @GetMapping("/countries/most-borders")
    public ResponseEntity<CountryDto> getWithMostBorders(){
        return ResponseEntity.ok(countryService.mostBorders());

    }

    @PostMapping("/countries")
    public  ResponseEntity<List<CountryDto>> post(@RequestBody RequestCountry requestCountry){

        if(requestCountry.getAmountOfCountryToSave() > 10){
           return ResponseEntity.status(400).body(new ArrayList<>());
        }else {
            return ResponseEntity.ok(countryService.post(requestCountry));
        }

    }

}
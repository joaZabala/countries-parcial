package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDto;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDto>> getCountries(@RequestParam(required = false) String name ,
                                                         @RequestParam(required = false) String code            ) {
        return ResponseEntity.ok(countryService.getCountriesDto(name , code));
    }

    @GetMapping("/countries/{continent}/continent")
    public ResponseEntity<List<CountryDto>> getCountriesByContinent(@PathVariable String continent) {
        return ResponseEntity.ok(countryService.getCountriesByContinent(continent));
    }


}
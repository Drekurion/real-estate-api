package pl.drekurion.realestateapi.estate;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.drekurion.realestateapi.estate.enums.EstateType;
import pl.drekurion.realestateapi.estate.enums.OfferType;
import pl.drekurion.realestateapi.estate.filters.EstatePage;
import pl.drekurion.realestateapi.estate.filters.EstateSearchCriteria;

import java.math.BigDecimal;

@RestController
@RequestMapping("/estates")
@AllArgsConstructor
public class EstateController
{
    private final EstateService estateService;

    @GetMapping
    public ResponseEntity<Page<Estate>> getEstates(EstatePage estatePage, EstateSearchCriteria estateSearchCriteria) {

        return new ResponseEntity<>(estateService.getEstates(estatePage, estateSearchCriteria), HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<Estate> addEstate(@RequestBody Estate estate) {

        return new ResponseEntity<>(estateService.addEstate(estate), HttpStatus.OK);
    }

    @PutMapping(path = "update/{estateId}")
    public void editEstate(
            @PathVariable(name = "estateId") Long estateId,
            @RequestParam(required = false) OfferType offerType,
            @RequestParam(required = false) EstateType estateType,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) BigDecimal area,
            @RequestParam(required = false) BigDecimal price)
    {
        estateService.updateEstate(estateId, offerType, estateType, number, area, price);
    }

    @DeleteMapping(path = "delete/{estateId}")
    public void deleteEstate(@PathVariable("estateId") Long estateId) {
        estateService.deleteEstate(estateId);
    }
}

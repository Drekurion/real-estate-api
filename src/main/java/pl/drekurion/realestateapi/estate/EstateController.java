package pl.drekurion.realestateapi.estate;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/list")
@AllArgsConstructor
public class EstateController
{
    private final EstateService estateService;

    @GetMapping
    public List<Estate> getAllEstates() {
        return estateService.getAllEstates();
    }

    @PostMapping
    public void addNewEstate(@RequestBody Estate estate) {
        estateService.addEstate(estate);
    }

    @DeleteMapping(path = "delete/{estateId}")
    public void deleteEstate(@PathVariable("estateId") Long estateId) {
        estateService.deleteEstate(estateId);
    }
}

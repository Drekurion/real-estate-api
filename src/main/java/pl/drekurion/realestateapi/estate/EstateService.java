package pl.drekurion.realestateapi.estate;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.drekurion.realestateapi.estate.enums.EstateType;
import pl.drekurion.realestateapi.estate.enums.OfferType;
import pl.drekurion.realestateapi.estate.filters.EstateCriteriaRepository;
import pl.drekurion.realestateapi.estate.filters.EstatePage;
import pl.drekurion.realestateapi.estate.filters.EstateSearchCriteria;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;

@Service
@AllArgsConstructor
public class EstateService
{
    private final EstateRepository estateRepository;
    private final EstateCriteriaRepository estateCriteriaRepository;

    public List<Estate> getAllEstates() {
        return estateRepository.findAll();
    }

    public Page<Estate> getEstates(EstatePage estatePage, EstateSearchCriteria estateSearchCriteria) {
        return estateCriteriaRepository.findAllWithFilters(estatePage, estateSearchCriteria);
    }

    public Estate getEstate(Long estateId) {
        return estateRepository.findById(estateId)
                .orElseThrow(() -> new IllegalStateException("Estate with id: " + estateId + " does not exist."));
    }

    public Estate addEstate(Estate estate) {
        return estateRepository.save(estate);
    }

    public void deleteEstate(Long estateId) {
        Estate estate = getEstate(estateId);
        estateRepository.delete(estate);
    }

    @Transactional
    public void updateEstate(Long estateId, OfferType offerType, EstateType estateType, String number, BigDecimal area, BigDecimal price)
    {
        Estate estate = getEstate(estateId);
        if(offerType != null && offerType != estate.getOfferType())
        {
            estate.setOfferType(offerType);
        }
        if(estateType != null && estateType != estate.getEstateType())
        {
            estate.setEstateType(estateType);
        }
        if(number != null && number.length() > 0 && !Objects.equals(estate.getNumber(), number))
        {
            estate.setNumber(number);
        }
        if(area != null && area.compareTo(ZERO) > 0 && area.compareTo(estate.getArea()) != 0)
        {
            estate.setArea(area);
        }
        if(price != null && price.compareTo(ZERO) > 0 && price.compareTo(estate.getPrice()) != 0)
        {
            estate.setPrice(price);
        }
    }
}

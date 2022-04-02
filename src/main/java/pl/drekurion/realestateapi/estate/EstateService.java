package pl.drekurion.realestateapi.estate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EstateService
{
    private final EstateRepository estateRepository;

    public List<Estate> getAllEstates() {
        return estateRepository.findAll();
    }

    public Estate getEstate(Long estateId) {
        return estateRepository.findById(estateId)
                .orElseThrow(() -> new IllegalStateException("Estate with id: " + estateId + " does not exist."));
    }

    public void addEstate(Estate estate) {
        estateRepository.save(estate);
    }

    public void deleteEstate(Long estateId) {
        Estate estate = getEstate(estateId);
        estateRepository.delete(estate);
    }
}

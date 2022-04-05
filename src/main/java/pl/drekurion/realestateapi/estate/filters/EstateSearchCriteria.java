package pl.drekurion.realestateapi.estate.filters;

import lombok.Getter;
import lombok.Setter;
import pl.drekurion.realestateapi.estate.enums.EstateType;
import pl.drekurion.realestateapi.estate.enums.OfferType;

import java.math.BigDecimal;

@Getter
@Setter
public class EstateSearchCriteria {
    private OfferType offerType;
    private EstateType estateType;
    private String number;
    private BigDecimal area;
    private BigDecimal price;
}

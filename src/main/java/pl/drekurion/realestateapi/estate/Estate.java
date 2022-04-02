package pl.drekurion.realestateapi.estate;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estate
{
    @Id
    @SequenceGenerator(
            name = "estate_generator",
            sequenceName = "estate_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "estate_generator"
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferType offerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstateType estateType;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Float area;

    @Column(nullable = false)
    private BigDecimal price;

    public Estate(OfferType offerType, EstateType estateType, String number, Float area, BigDecimal price)
    {
        this.offerType = offerType;
        this.estateType = estateType;
        this.number = number;
        this.area = area;
        this.price = price;
    }
}

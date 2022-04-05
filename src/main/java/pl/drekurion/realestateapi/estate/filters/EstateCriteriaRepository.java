package pl.drekurion.realestateapi.estate.filters;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import pl.drekurion.realestateapi.estate.Estate;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EstateCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public EstateCriteriaRepository(EntityManager entityManager)
    {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Estate> findAllWithFilters(EstatePage estatePage, EstateSearchCriteria estateSearchCriteria) {

        CriteriaQuery<Estate> criteriaQuery = criteriaBuilder.createQuery(Estate.class);
        Root<Estate> estateRoot = criteriaQuery.from(Estate.class);
        Predicate predicate = getPredicate(estateSearchCriteria, estateRoot);
        criteriaQuery.where(predicate);

        setOrder(estatePage, criteriaQuery, estateRoot);

        TypedQuery<Estate> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(estatePage.getPageNumber() * estatePage.getPageSize());
        typedQuery.setMaxResults(estatePage.getPageSize());

        Pageable pageable = getPageable(estatePage);

        long estatesCount = getEstatesCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, estatesCount);
    }

    private Predicate getPredicate(EstateSearchCriteria estateSearchCriteria, Root<Estate> estateRoot) {

        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(estateSearchCriteria.getOfferType())) {
            predicates.add(criteriaBuilder.equal(estateRoot.get("offerType"), estateSearchCriteria.getOfferType()));
        }
        if(Objects.nonNull(estateSearchCriteria.getEstateType())) {
            predicates.add(criteriaBuilder.equal(estateRoot.get("estateType"), estateSearchCriteria.getEstateType()));
        }
        if(Objects.nonNull(estateSearchCriteria.getNumber())) {
            predicates.add(criteriaBuilder.like(estateRoot.get("number"), "%" + estateSearchCriteria.getNumber() + "%"));
        }
        if(Objects.nonNull(estateSearchCriteria.getArea())) {
            predicates.add(criteriaBuilder.equal(estateRoot.get("area"), estateSearchCriteria.getArea()));
        }
        if(Objects.nonNull(estateSearchCriteria.getPrice())) {
            predicates.add(criteriaBuilder.equal(estateRoot.get("price"), estateSearchCriteria.getPrice()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(EstatePage estatePage, CriteriaQuery<Estate> criteriaQuery, Root<Estate> estateRoot) {

        if(estatePage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(estateRoot.get(estatePage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(estateRoot.get(estatePage.getSortBy())));
        }
    }

    private Pageable getPageable(EstatePage estatePage) {

        Sort sort = Sort.by(estatePage.getSortDirection(), estatePage.getSortBy());
        return PageRequest.of(estatePage.getPageNumber(), estatePage.getPageSize(), sort);
    }

    private long getEstatesCount(Predicate predicate) {

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Estate> countRoot = countQuery.from(Estate.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}

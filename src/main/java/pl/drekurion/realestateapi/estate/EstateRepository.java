package pl.drekurion.realestateapi.estate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstateRepository extends JpaRepository<Estate, Long>
{
}
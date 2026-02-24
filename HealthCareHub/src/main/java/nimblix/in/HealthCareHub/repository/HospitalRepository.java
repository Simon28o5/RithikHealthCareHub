package nimblix.in.HealthCareHub.repository;

import lombok.Data;
import nimblix.in.HealthCareHub.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Long> {
    Optional<Hospital> findByName(String name);
    List<Hospital> findTop5ByOrderByRatingDesc();
}

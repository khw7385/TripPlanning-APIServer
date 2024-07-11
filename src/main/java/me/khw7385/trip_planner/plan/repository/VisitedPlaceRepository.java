package me.khw7385.trip_planner.plan.repository;

import me.khw7385.trip_planner.plan.entity.VisitedPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitedPlaceRepository extends JpaRepository<VisitedPlace, Long> {
}

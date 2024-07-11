package me.khw7385.trip_planner.plan.repository;

import jakarta.persistence.EntityManager;
import me.khw7385.trip_planner.plan.entity.Plan;
import me.khw7385.trip_planner.plan.entity.VisitedPlace;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PlanRepositoryTest {
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private VisitedPlaceRepository visitedPlaceRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void findAllByUserId(){
        Plan plan1 = Plan.builder()
                .title("부산 여행")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .userId("khw7385")
                .build();

        Plan plan2 = Plan.builder().title("서울")
                .startDate(LocalDate.of(2024, 7, 12))
                .endDate(LocalDate.of(2024, 7, 15))
                .userId("khw7385")
                .build();

        planRepository.save(plan1);
        planRepository.save(plan2);

        List<Plan> findPlans = planRepository.findALLByUserId("khw7385");

        Assertions.assertThat(findPlans.size()).isEqualTo(2);
    }

}
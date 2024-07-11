package me.khw7385.trip_planner.plan.service.impl;

import me.khw7385.trip_planner.plan.dto.PlanDto;
import me.khw7385.trip_planner.plan.entity.Plan;
import me.khw7385.trip_planner.plan.entity.VisitedPlace;
import me.khw7385.trip_planner.plan.repository.PlanRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PlanServiceImplTest {
    @InjectMocks
    private PlanServiceImpl planService;

    @Mock
    private PlanRepository planRepository;

    @Test
    void getAllPlan(){
        //given
        Plan fakePlan1 = Plan.builder()
                .title("부산 여행")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .userId("khw7385")
                .build();

        Plan fakePlan2 = Plan.builder()
                .title("서울 여행")
                .startDate(LocalDate.of(2024, 7, 11))
                .endDate(LocalDate.now().plusDays(4))
                .userId("khw7385")
                .build();

        List<Plan> fakePlans = Arrays.asList(fakePlan1, fakePlan2);

        String userId = "khw7385";

        given(planRepository.findALLByUserId(userId)).willReturn(fakePlans); //mock
        //when
        PlanDto.Command command = PlanDto.Command.builder().userId("khw7385").build();
        List<PlanDto.ListResponse> singleResponse = planService.getAllPlan(command);

        //then
        Assertions.assertThat(singleResponse.size()).isEqualTo(fakePlans.size());
    }

    @Test
    void getPlan(){
        //given
        Long fakePlanId = 1L;

        VisitedPlace fakePlace = VisitedPlace.builder()
                .name("해운대")
                .latitude(12.12324)
                .longitude(124.1231)
                .visited(false)
                .build();

        Plan fakePlan = Plan.builder()
                .title("부산 여행")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .userId("khw7385")
                .visitedPlaces(Arrays.asList(fakePlace))
                .build();

        given(planRepository.findById(fakePlanId)).willReturn(Optional.of(fakePlan)); //mock

        //when
        PlanDto.Command command = PlanDto.Command.builder()
                .planId(1L)
                .build();

        PlanDto.SingleResponse singleResponse = planService.getPlan(command);

        //then
        Assertions.assertThat(singleResponse.title()).isEqualTo("부산 여행");
    }
}
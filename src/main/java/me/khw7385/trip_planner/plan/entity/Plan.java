package me.khw7385.trip_planner.plan.entity;

import ch.qos.logback.classic.ViewStatusMessagesServlet;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer memberNum;
    private LocalDate startDate;
    private LocalDate endDate;

    private String userId;

    @OneToMany(mappedBy = "plan")
    private List<VisitedPlace> visitedPlaces = new ArrayList<>();

    @Builder
    public Plan(String title, Integer memberNum, LocalDate startDate, LocalDate endDate, String userId, List<VisitedPlace> visitedPlaces) {
        this.title = title;
        this.memberNum = memberNum;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.visitedPlaces = visitedPlaces;
    }
}

package me.khw7385.trip_planner.plan.entity;

import ch.qos.logback.classic.ViewStatusMessagesServlet;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    private String userId;

    @OneToMany(mappedBy = "plan")
    private List<VisitedPlace> visitedPlaces = new ArrayList<>();
}

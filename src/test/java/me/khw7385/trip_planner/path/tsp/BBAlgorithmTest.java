package me.khw7385.trip_planner.path.tsp;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BBAlgorithmTest {
    private BBAlgorithm bbAlgorithm = new BBAlgorithm();

    @Test
    void test(){
        List<List<Integer>> input = Arrays.asList(Arrays.asList(0, 14, 4, 10, 20), Arrays.asList(14, 0, 7, 8 ,7), Arrays.asList(4, 5, 0, 7, 16), Arrays.asList(11, 7, 9, 0, 2), Arrays.asList(18, 7, 17, 4, 0));

        bbAlgorithm.solve(input, true);
    }

    @Test
    void test1(){
        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = a;

        System.out.println(a);
        System.out.println(b);
    }
}
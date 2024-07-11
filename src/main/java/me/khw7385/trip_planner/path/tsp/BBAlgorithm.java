package me.khw7385.trip_planner.path.tsp;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BBAlgorithm {
    private final int INF = 987654321;
    private int arrLength;
    private int[][] arr;
    private List<Integer> optTour;
    private PriorityQueue<Node> pq = new PriorityQueue<>(1, new NodeComparator());
    public List<Integer> solve(List<List<Integer>> input, boolean isReturnToStart){
        // 2차원 배열 생성
        arr =  convertTo2DArray(input);
        arrLength = input.size();

        if(isReturnToStart) {
            TSPReturnStart();
            optTour = optTour.subList(1, optTour.size() - 1);
        }
        else {
            TSPNotReturnStart();
            optTour = optTour.subList(1, optTour.size());
        }

        return optTour;
    }
    private void TSPReturnStart(){
        int minDistance = INF;

        Node node = new Node(0, boundReturnStart(new Node(0, 0, Arrays.asList(0))), Arrays.asList(0));
        pq.add(node);

        while(!pq.isEmpty()){
            Node v = pq.remove();

            if(v.bound  < minDistance){
                for (int idx = 0; idx < arrLength; idx++) {
                    if(v.path.contains(idx)) continue;
                    Node u = new Node();
                    u.level = v.level + 1;
                    u.path = new ArrayList<>(v.path);

                    u.path.add(idx);

                    if(u.level == arrLength - 2){
                        for (int i = 0; i < arrLength; i++) {
                            if(!u.path.contains(i)){
                                u.path.add(i); break;
                            }
                        }
                        u.path.add(0);

                        if(getDistance(u.path) < minDistance){
                            minDistance = getDistance(u.path);
                            optTour = u.path;
                        }
                    }else{
                        u.bound = boundReturnStart(u);
                        if(u.bound < minDistance) pq.add(u);
                    }
                }
            }
        }
    }
    private void TSPNotReturnStart(){
        int minDistance = INF;

        Node node = new Node(0, boundNotReturnStart(new Node(0, 0, Arrays.asList(0))), Arrays.asList(0));
        pq.add(node);

        while(!pq.isEmpty()){
            Node v = pq.remove();

            if(v.bound  < minDistance){

                for (int idx = 0; idx < arrLength; idx++) {
                    if(v.path.contains(idx)) continue;
                    Node u = new Node();
                    u.level = v.level + 1;
                    u.path = new ArrayList<>(v.path);

                    u.path.add(idx);

                    if(u.level == arrLength - 2){
                        for (int i = 0; i < arrLength; i++) {
                            if(!u.path.contains(i)){
                                u.path.add(i); break;
                            }
                        }

                        if(getDistance(u.path) < minDistance){
                            minDistance = getDistance(u.path);
                            optTour = u.path;
                        }
                    }else{
                        u.bound = boundNotReturnStart(u);
                        if(u.bound < minDistance) pq.add(u);
                    }
                }
            }
        }
    }
    private int[][] convertTo2DArray(List<List<Integer>> list){
        int[][] array = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            List<Integer> row = list.get(i);
            array[i] = new int[row.size()];
            for (int j = 0; j < row.size(); j++) {
                array[i][j] = row.get(j);
            }
        }
        return array;
    }
    private int boundReturnStart(Node n){
        int lower = getDistance(n.path);

        for (int i = 0; i < arrLength; i++) {
            if(hasOutGoing(i, n.path)) continue;

            int min = INF;

            for (int j = 0; j < arrLength; j++) {

                if(i == j) continue; // 자기 자신으로 가는 경우 제외시킴
                if(j == 0 && i == n.path.get(n.path.size() - 1)) continue;
                if(hasInComing(j, n.path)) continue;
                if(min > arr[i][j]) min = arr[i][j];
            }

            lower += min;
        }

        return lower;
    }

    private int boundNotReturnStart(Node n){
        int lower = getDistance(n.path);

        for (int i = 0; i < arrLength; i++) {
            if(hasOutGoing(i, n.path)) continue;

            int min = INF;

            for (int j = 0; j < arrLength; j++) {

                if(i == j) continue; // 자기 자신으로 가는 경우 제외시킴
                if(j == 0) continue; // 출발지로 돌아오는 것을 고려하지 않으니 제외시킴
                if(hasInComing(j, n.path)) continue;
                if(min > arr[i][j]) min = arr[i][j];
            }

            lower += min;
        }

        return lower;
    }

    // 이미 출발했던 장소는 제외해야 한다.
    private boolean hasOutGoing(int nowIdx, List<Integer> path){
        for (int i = 0; i < path.size() - 1; i++) {
            if(nowIdx == path.get(i)) return true;
        }
        return false;
    }
    // 이미 도착했던 장소는 제외해야 한다.
    private boolean hasInComing(int nextIdx, List<Integer> path){
        for (int i = 1; i < path.size() - 1; i++) {
            if(nextIdx == path.get(i)) return true;
        }
        return false;
    }
    private int getDistance(List<Integer> path){
        int distance = 0;

        for (int i = 0; i < path.size(); i++) {
            if(i != path.size() - 1) distance += arr[path.get(i)][path.get(i + 1)];
        }
        return distance;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class Node{
        private int level;
        private int bound;
        private List<Integer> path;
    }

    class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.bound - o2.bound;
        }
    }
}

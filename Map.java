import java.util.*;

class Map {
    private List<Waypoint> waypoints;
    private List<StreetSegment> streetSegments;
    
    public Map() {
        waypoints = new ArrayList<Waypoint>();
        streetSegments = new ArrayList<StreetSegment>();
    }
    
    public void addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
    }
    
    public void addStreetSegment(StreetSegment streetSegment) {
        streetSegments.add(streetSegment);
    }

    public void displayWaypoints() {
        System.out.println("ALL WAYPOINTS:");
        System.out.println("[ID]   [X]      [Y]");
        for (Waypoint waypoint : waypoints) {
            System.out.println(waypoint.getId() + "      " + waypoint.getX() + "      "+ waypoint.getY());
        }
    }
    
    public List<Waypoint> getWaypoints() {
        return waypoints;
    }
    
    public List<StreetSegment> getStreetSegments() {
        return streetSegments;
    }


    public Waypoint findWaypointById(int id) {
        for (Waypoint waypoint : waypoints) {
            if (waypoint.getId() == id) {
                return waypoint;
            }
        }
        return null;
    }





    public double computePossibilityDegree(StreetSegment segment) {
        // Initialize possibility degree to 1
        double possibilityDegree = 1;
        // Get the probability values and constraints for the current segment
        double p1 = segment.getP1();
        double p2 = segment.getP2();
        double c1 = segment.getC1();
        double c2 = segment.getC2();
        double c3 = segment.getC3();
        double c4 = segment.getC4();
        // Update possibility degree using the formula Poss(Ei) = min(Prob(p1,i),Prob(p2,i), 1 − Prob(c1,i), 1 − Prob(c2,i), 1 − Prob(c3,i), 1 − Prob(c4,i)).
        possibilityDegree = Math.min(possibilityDegree, Math.min(p1, p2));
        possibilityDegree = Math.min(possibilityDegree, 1 - c1);
        possibilityDegree = Math.min(possibilityDegree, 1 - c2);
        possibilityDegree = Math.min(possibilityDegree, 1 - c3);
        possibilityDegree = Math.min(possibilityDegree, 1 - c4);
        return possibilityDegree;
    }







    /*
    The algorithm uses a priority queue and a couple of HashMaps to keep track of the possibility degree and the previous waypoint of each waypoint in the optimal route. It starts by adding the starting waypoint to the priority queue with a possibility degree of 1.
    It then enters a while loop that continues until the priority queue is empty. In each iteration, it takes the waypoint with the highest possibility degree from the queue, and checks if it is the end waypoint. If it is, it uses the previous waypoints map to trace the optimal route from the end waypoint to the starting waypoint and return it.
    If the current waypoint is not the end waypoint, it loops through each street segment that leaves from it, and for each one of them, it calculates the possibility degree of the neighbor waypoint, and compares it with the one already stored in the possibilityDegrees map. If the current possibility is higher, it updates the possibility and previous waypoint of the neighbor, and adds it to the priority queue.
    If the end waypoint is not reached, it returns an empty list.
    

    public List<StreetSegment> findOptimalRoute(Waypoint start, Waypoint end) {
        // Create a priority queue and add the starting waypoint with a possibility degree of 1
        PriorityQueue<Waypoint> queue = new PriorityQueue<>(new PossibilityComparator());
        queue.add(start);

        // Create a HashMap to store the possibility degree of each waypoint
        HashMap<Waypoint, Double> possibilityDegrees = new HashMap<>();
        possibilityDegrees.put(start, 1.0);

        // Create a HashMap to store the previous waypoint of each waypoint in the optimal route
        HashMap<Waypoint, Waypoint> previousWaypoints = new HashMap<>();

        // While the queue is not empty
        while (!queue.isEmpty()) {
            // Dequeue the waypoint with the highest possibility degree
            Waypoint currentWaypoint = queue.poll();

            // If the current waypoint is the end waypoint, we have found the optimal route
            if (currentWaypoint.equals(end)) {
                // Use the previous waypoints map to trace the optimal route from end to start
                List<StreetSegment> optimalRoute = new ArrayList<>();
                Waypoint current = end;
                while (current != start) {
                    Waypoint previous = previousWaypoints.get(current);
                    for(StreetSegment segment: streetSegments) {
                        if(segment.getStart().equals(previous) && segment.getEnd().equals(current)) {
                            optimalRoute.add(segment);
                        }
                    }
                    current = previous;
                }
                // Reverse the list to get the optimal route from start to end
                Collections.reverse(optimalRoute);
                return optimalRoute;
            }

            // For each neighbor of the current waypoint
            for (StreetSegment segment : currentWaypoint.getStreetSegments()) {
                Waypoint neighbor = segment.getEnd();
                double possibility = computePossibilityDegree(currentWaypoint, neighbor);
                double currentPossibility = possibilityDegrees.getOrDefault(neighbor, 0.0);
                // If the possibility of the current route to the neighbor is higher than the previous possibility
                if (possibility > currentPossibility) {
                    // Update the possibility of the neighbor in the possibility map
                    possibilityDegrees.put(neighbor, possibility);
                    // Update the previous waypoint of the neighbor in the previous waypoints map
                    previousWaypoints.put(neighbor, currentWaypoint);
                    // Enqueue the neighbor with the updated possibility
                    queue.add(neighbor);
                }
            }
        }
        // If the end waypoint is not reached, return an empty list
        return new ArrayList<>();
    }


    */
}


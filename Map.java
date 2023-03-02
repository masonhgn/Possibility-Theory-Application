import java.util.*;

class Map {
    private ArrayList<Waypoint> waypoints;
    public ArrayList<StreetSegment> streetSegments;


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

    public void displaySegments() {
        for (StreetSegment s: streetSegments) {
            System.out.println(s.toString() + " (P: " + computePossibilityDegree(s) + ")");
        }
    }


    
    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }
    
    public ArrayList<StreetSegment> getStreetSegments() {
        return streetSegments;
    }

    public Waypoint findWaypointByXY(double x, double y) {
        for (Waypoint waypoint : waypoints) {
            if (waypoint.getX() == x && waypoint.getY() == y) {
                return waypoint;
            }
        }
        return null;
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


    public ArrayList<StreetSegment> minRoute(ArrayList<StreetSegment> a, ArrayList<StreetSegment> b) {
        if (a.size() == 0) return b;
        if (b.size() == 0) return a;
        double totalA = 0, totalB = 0;
        for (StreetSegment segment : a) {
            totalA += computePossibilityDegree(segment);
        }

        for (StreetSegment segment: b) {
            totalB += computePossibilityDegree(segment);
        }

        System.out.println("comparing " + totalA + " to " + totalB);

        if (totalA < totalB) return a;
        else return b;
    }

    public ArrayList<StreetSegment> addLists(ArrayList<StreetSegment> a, ArrayList<StreetSegment> b) {
        if (a.size() == 0) return b;
        if (b.size() == 0) return a;
        for (int i = 0; i < b.size(); ++i) a.add(b.get(i));
        return a;
    }

    public ArrayList<StreetSegment> addToList(ArrayList<StreetSegment> a, StreetSegment b) {
        a.add(b);
        return a;
    }

    public ArrayList<StreetSegment> possibleSegments(Waypoint start) {
        ArrayList<StreetSegment> temp = new ArrayList<StreetSegment>();
        for (StreetSegment curSegment : streetSegments) { //traverse all segments, find all that start with waypoint START.
            if (curSegment.getStart().getId() == start.getId()) { //if we find a segment starting with START:
                temp.add(curSegment);
            }
        }
        return temp;
    }


    public String printSegmentList(ArrayList<StreetSegment> list) {
        String result = "[";
        for (int i = 0; i < list.size(); ++i) {
            result += StreetSegment.alphabet(list.get(i).getStart().getId());
            result += "->";
            result += StreetSegment.alphabet(list.get(i).getEnd().getId());
            result += ",";
        }
        result += "]";
        return result;
    }


    public ArrayList<StreetSegment> findOptimalRoute(Waypoint start, Waypoint end, ArrayList<StreetSegment> curPath, ArrayList<StreetSegment> minPath) {
        System.out.println("findOptimalRoute(" + start.toString() + ","+end.toString()+"," + printSegmentList(curPath) + ")");
        if (start.getId() == end.getId()) {
            minPath = minRoute(minPath, curPath);
            return minPath;
        }

        ArrayList<StreetSegment> possible = possibleSegments(start); //this finds every segment that starts with the current start waypoint

        if (possible.size() == 0) { //if there are no segments starting with the current waypoint
            System.out.println("we're fucked.");
        } else { //there is at least one segment, update minimum path
            for (StreetSegment curSegment: possible) {
                minPath = findOptimalRoute(curSegment.getEnd(), end, addToList(curPath,curSegment), minPath);
                curPath = new ArrayList<StreetSegment>();
            }
            //minPath = minRoute(curPath, minPath);
        }
        return minPath;
    }

}



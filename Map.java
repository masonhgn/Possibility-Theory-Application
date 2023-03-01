import java.util.*;

class Map {
    private ArrayList<Waypoint> waypoints;
    private ArrayList<StreetSegment> streetSegments;

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
            System.out.println(s.toString() + " (P: " + computePossibilityDegree(s));
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
        int totalA = 0, totalB = 0;
        for (int i = 0; i < a.size(); ++i) {
            totalA += computePossibilityDegree(a.get(i));
        }

        for (int i = 0; i < a.size(); ++i) {
            totalB += computePossibilityDegree(b.get(i));
        }
        if (totalA > totalB) return a;
        else return b;
    }

    public ArrayList<StreetSegment> add(ArrayList<StreetSegment> a, ArrayList<StreetSegment> b) {
        if (a == null) return b;
        if (b == null) return a;
        for (int i = 0; i < b.size(); ++i) a.add(b.get(i));
        return a;
    }


public ArrayList<StreetSegment> findOptimalRoute(Waypoint start, Waypoint end, ArrayList<StreetSegment> curPath) {
    /*  
        1. if start == end, return {}
        2. traverse possible segments from given start waypoint, return minimum size segment list from start to end
    */

    if (start.getId() == end.getId()) {
        return new ArrayList<StreetSegment>();
    }

    ArrayList<StreetSegment> minPath = streetSegments;
    for (StreetSegment curSegment : streetSegments) {
        if (curSegment.getStart().getId() == start.getId()) { //if we find a segment leading from our current waypoint
            
            //create a new path for the recursive call
            ArrayList<StreetSegment> newPath = new ArrayList<StreetSegment>(curPath);
            //add the segment to our new path
            newPath.add(curSegment);

            //find minimum weighted route
            minPath = minRoute(minPath, add(newPath, findOptimalRoute(curSegment.getEnd(), end, newPath)));
        }
    }
    return minPath;
}


/*    public ArrayList<StreetSegment> findOptimalRoute(Waypoint start, Waypoint end, ArrayList<StreetSegment> curPath) {
        /*  
            1. if start == end, return {}
            2. traverse possible segments from given start waypoint, return minimum size segment list from start to end
        

        if (start.getId() == end.getId()) {
            return null;
        }

        ArrayList<StreetSegment> minPath = streetSegments;
        for (StreetSegment curSegment : streetSegments) {
            if (curSegment.getStart().getId() == start.getId()) { //if we find a segment leading from our current waypoint
                
                //add the segment to our curPath
                curPath.add(curSegment);

                //find minimum weighted route
                minPath = minRoute(minPath, add(curPath, findOptimalRoute(curSegment.getEnd(), end, curPath)));

                curPath.remove(curSegment);
            }
        }
        return minPath;
    }

}
*/


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Map map = new Map();
        
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int startId = Integer.parseInt(parts[0]);
                int endId = Integer.parseInt(parts[1]);
                double p1 = Double.parseDouble(parts[2]);
                double p2 = Double.parseDouble(parts[3]);
                double c1 = Double.parseDouble(parts[4]);
                double c2 = Double.parseDouble(parts[5]);
                double c3 = Double.parseDouble(parts[6]);
                double c4 = Double.parseDouble(parts[7]);
                Waypoint start = findWaypointById(startId, map.getWaypoints());
                Waypoint end = findWaypointById(endId, map.getWaypoints());
                if (start == null) {
                    start = new Waypoint(startId, 0, 0, 0);
                    map.addWaypoint(start);
                }
                if (end == null) {
                    end = new Waypoint(endId, 0, 0, 0);
                    map.addWaypoint(end);
                }
                StreetSegment segment = new StreetSegment(start, end, p1, p2, c1, c2, c3, c4);
                map.addStreetSegment(segment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the start waypoint ID: ");
        int startId = scanner.nextInt();
        System.out.println("Enter the end waypoint ID: ");
        int endId = scanner.nextInt();
        Waypoint start = map.findWaypointById(startId);
        Waypoint end = map.findWaypointById(endId);
        if (start == null || end == null) {
            System.out.println("Invalid waypoint IDs");
            return;
        }
        List<StreetSegment> bestRoute = map.findOptimalRoute(start, end);
        if (bestRoute.isEmpty()) {
            System.out.println("No route found between the given waypoints");
        } else {
            System.out.println("The best route between waypoints " + startId + " and " + endId + " is:");
            for (StreetSegment segment : bestRoute) {
                System.out.println(segment);
            }
        }
        scanner.close();
    }
}
    public static Waypoint findWaypointById(int id, List<Waypoint> waypoints) {
        for (Waypoint waypoint : waypoints) {
            if (waypoint.getId() == id) {
                return waypoint;
            }
        }
        return null;
    }


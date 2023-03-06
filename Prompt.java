import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Prompt {
    Map map;
    Scanner scanner;
    
    //singleton class instance
    private static Prompt single_instance = null;

    //default constructor
    private Prompt(Map m) {
        scanner = new Scanner(System.in);
        map = m;
    }

    //singleton class instance method
    public static Prompt getInstance(Map m) {
        if (single_instance == null) single_instance = new Prompt(m);
        return single_instance;
    }

    public void promptMenu() {
        System.out.println(" ____________________________________________");
        System.out.println("|                                            |");   
        System.out.println("|  [1] Load Segments File                    |");
        System.out.println("|  [2] List All Segments                     |");
        System.out.println("|  [3] Find Best Route Between 2 Waypoints   |");
        System.out.println("|  [4] Exit                                  |");
        System.out.println("|____________________________________________|");
        System.out.println("\nPlease select a menu option by number...");
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                System.out.println("Enter file name...");
                String fileName = scanner.next();
                parseSegmentFileData(fileName);
                break;
            case 2:
                map.displaySegments();
                break;
            case 3:
                promptForBestRoute();
                break;
            case 4:
                System.out.println("Have a nice day!");
                System.exit(0);
        }
    }

    public void promptForBestRoute() {
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
        
        ArrayList<StreetSegment> bestRoute = map.findOptimalRoute(start, end, new ArrayList<StreetSegment>(), map.streetSegments);
        if (bestRoute.isEmpty()) {
            System.out.println("No route found between the given waypoints");
        } else {
            System.out.println("The best route between waypoints " + startId + " and " + endId + " is:");
            for (StreetSegment segment : bestRoute) {
                System.out.println(segment.toString());
            }
        }
        
    }


    //parses a single line into a segment
    public void parseLine(String line) {
        String[] parts = line.split(" ");

        if (parts.length != 8) {
            System.out.println("INVALID FILE INPUT: LINE CONTAINS " + parts.length + " elements but 8 are required.");
            System.out.println(line);
            System.exit(0);
        }

        int startId = Integer.parseInt(parts[0]);
        int endId = Integer.parseInt(parts[1]);
        double p1 = Double.parseDouble(parts[2]);
        double p2 = Double.parseDouble(parts[3]);
        double c1 = Double.parseDouble(parts[4]);
        double c2 = Double.parseDouble(parts[5]);
        double c3 = Double.parseDouble(parts[6]);
        double c4 = Double.parseDouble(parts[7]);
        Waypoint start = map.findWaypointById(startId);
        Waypoint end = map.findWaypointById(endId);
        if (start == null) {
            start = new Waypoint(startId, 0, 0);
            map.addWaypoint(start);
        }
        if (end == null) {
            end = new Waypoint(endId, 0, 0);
            map.addWaypoint(end);
        }
        StreetSegment segment = new StreetSegment(start, end, p1, p2, c1, c2, c3, c4);
        map.addStreetSegment(segment);
    }


    //parses waypoints file
    public void parseWaypointLine(String line) {
        String[] parts = line.split(" ");

        if (parts.length != 3) {
            System.out.println("INVALID FILE INPUT: LINE CONTAINS " + parts.length + " elements but 3 are required.");
            System.out.println(line);
            System.exit(0);
        }

        int tempId = Integer.parseInt(parts[0]);
        double tempX = Integer.parseInt(parts[1]);
        double tempY = Integer.parseInt(parts[2]);


        Waypoint temp = map.findWaypointById(tempId);

        if (temp == null) {
            temp = new Waypoint(tempId, tempX, tempY);
            map.addWaypoint(temp);
        }
    }


    // parses every line of a passed in file, adds segments/waypoint to map
    public void parseSegmentFileData(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                parseLine(line); //parses a line into a segment, adds segment and waypoints to map
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // parses every line of a passed in file, adds segments/waypoint to map
    public void parseWaypointFileData(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                parseWaypointLine(line); //parses a line into a segment, adds segment and waypoints to map
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
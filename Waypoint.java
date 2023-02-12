class Waypoint {
    private int id;
    private double x;
    private double y;
    private double speedLimit;
    
    public Waypoint(int id, double x, double y, double speedLimit) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.speedLimit = speedLimit;
    }
    
    public int getId() {
        return id;
    }
//These x & y coordinates can be used in the future in order to create a graphical implementation    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }

    
    public double getSpeedLimit() {
        return speedLimit;
    }
}

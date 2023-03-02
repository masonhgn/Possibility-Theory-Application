class Waypoint {
    private final int ID;
    private double x;
    private double y;
    
    public Waypoint(int id, double x, double y) {
        this.ID = id;
        this.x = x;
        this.y = y;
    }

    public int getId() { return ID; }

    public double getX() { return x; }
    
    public double getY() { return y; }

    @Override
    public String toString() {
        return Integer.toString(getId());
    }
}

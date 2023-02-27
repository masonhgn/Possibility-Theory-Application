class StreetSegment {
    private Waypoint start;
    private Waypoint end;

    private double p1, p2, c1, c2, c3, c4;
    
    public StreetSegment(Waypoint start, Waypoint end, double p1, double p2, double c1, double c2, double c3, double c4) {
        this.start = start;
        this.end = end;
        this.p1 = p1;
        this.p2 = p2;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
    }

    public Waypoint getStart() { return start; }
    public Waypoint getEnd() { return end; }
    public double getP1() { return p1; }
    public double getP2() { return p2; }
    public double getC1() { return c1;}
    public double getC2() { return c2; }
    public double getC3() { return c3; }
    public double getC4() { return c4; }
}

package de.c1bergh0st.geometry;

public class Edge extends Polygon{

    public Edge(Vector start, Vector end, double width){
        super();
        Vector direction = end.substract(start);
        Vector startleft = start.add(direction.getUnitVector().turnLeft().multiply(width / 2));
        Vector startright = start.add(direction.getUnitVector().turnRight().multiply(width / 2));
        Vector endleft = startleft.add(direction);
        Vector endright = startright.add(direction);
        add(startleft);
        add(startright);
        add(endright);
        add(endleft);
        close();
    }
    
}

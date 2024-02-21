import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class ConvexHull {

    private static void exchange(Point[] p, int a, int b) {
        Point swap = p[a];
        p[a] = p[b];
        p[b] = swap;
    }

    private static double getAngle(Point a, Point b, Point c) {
        double lengthC = getLength(a,b);
        double lengthA = getLength(c,b);
        double lengthB = getLength(c,a);

        double resultRad = Math.acos((Math.pow(lengthA,2) + Math.pow(lengthC,2) - Math.pow(lengthB,2)) / (2*lengthA*lengthC));

        return Math.toDegrees(resultRad);
    }

    private static double getLength(Point a, Point b) {
        return Math.sqrt(Math.pow( (b.x - a.x) , 2) + Math.pow((b.y - a.y) , 2));
    }

    public static LinkedList<Point> getHulls(Point[] points) {

        if(points == null || points.length < 3) throw new IllegalArgumentException();

        Arrays.sort(points, Comparator.comparingInt(p -> p.y));

        Point lowest = points[0];
        Point referencePoint = new Point(lowest.x + 6, lowest.y);

        Arrays.sort(points, 1, points.length, Comparator.comparingDouble(p -> getAngle(p, lowest, referencePoint)));

        MyStack<Point> stack = new MyStack<>();
        LinkedList<Point> hull = new LinkedList<>();

        hull.add(points[0]);
        hull.add(points[1]);
        hull.add(points[2]);

        for (int i = points.length - 1; i > 2; i--) {
            stack.push(points[i]);
        }

        while (!stack.isEmpty()) {
            Point a = hull.get(hull.size() - 3);
            Point b = hull.get(hull.size() - 2);
            Point c = hull.getLast();

            double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);

            if (area2 > 0) {
                hull.add(stack.pop());
            } else if (area2 < 0) {
                hull.removeLast();
            }
        }

        return hull;
    }

    public static void main(String[] args) {

        Point[] points = new Point[16];

        // testing values
        points[0] = new Point(3,4);
        points[1] = new Point(3,8);
        points[2] = new Point(4,6);
        points[3] = new Point(6,3);
        points[4] = new Point(6,9);
        points[5] = new Point(7,7);
        points[6] = new Point(7,11);
        points[7] = new Point(8,1);
        points[8] = new Point(9,6);
        points[9] = new Point(9,12);
        points[10] = new Point(10,4);
        points[11] = new Point(10,9);
        points[12] = new Point(11,7);
        points[13] = new Point(11,10);
        points[14] = new Point(12,2);
        points[15] = new Point(12,5);


        LinkedList<Point> hulls = getHulls(points);

        for(Point P : hulls) {
            System.out.println(P.x + " " + P.y);
        }
    }

}

import java.util.LinkedList;

public class ConvexHull {


    public static void main(String[] args) {

        Point[] points = new Point[17];

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


    public static LinkedList<Point> getHulls(Point[] points) {

        // sort by y
        // insertion sort
        for(int i = 0; i < points.length;i++) {
            for(int j = i; j > 0;j--) {
                if(points[j].y < points[j-1].y) exchange(points, j, j-1);
                else break;
            }
        }

        // find lowest y

        Point lowest = points[0];

        // sort by angles to the lowest point
        // insertion sort
        for(int i = 1; i < points.length;i++) {
            for(int j = i; j > 1;j--) {
                if(getAngle(points[j], lowest, new Point(lowest.x + 6, lowest.y) ) <
                        getAngle(points[j-1], lowest, new Point(lowest.x + 6, lowest.y))) {
                    exchange(points,j, j-1);
                } else break;
            }
        }

        // add them to stack (except first 3)
        MyStack<Point> stack = new MyStack<>();
        LinkedList<Point> list = new LinkedList<>();

        for(int i = points.length - 1;i > 2;i--) stack.push(points[i]);

        // add first 3 to LinkedList (for the first scenario of testing)
        for(int i = 0; i < 3 ;i++) list.add(points[i]);

        // start testing

        boolean finished = false;

        while (!finished) {

            int N = list.size();
            Point a = list.get(N - 3);
            Point b = list.get(N - 2);
            Point c = list.get(N - 1);

            double area2 = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);

            if(area2 > 0) {
                if(stack.isEmpty()) {
                    finished = true;
                } else {
                    list.add(stack.pop());
                }
            } else if (area2 < 0) {
                list.remove(N - 2);
            }
        }

        // return result list
        return list;
    }

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

    private static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }



}

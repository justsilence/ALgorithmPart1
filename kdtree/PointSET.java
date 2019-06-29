/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        valid(p);
        if (!points.contains(p)) {
            points.add(p);
        }
    }

    public boolean contains(Point2D p) {
        valid(p);
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        valid(rect);
        Queue<Point2D> q = new Queue<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                q.enqueue(p);
            }
        }
        return q;
    }

    public Point2D nearest(Point2D p) {
        valid(p);
        Point2D nearestPoint = null;
        double minDistance = Double.MAX_VALUE;
        for (Point2D q : points) {
            double currDistance = q.distanceTo(p);
            if (minDistance < currDistance) {
                minDistance = currDistance;
                nearestPoint = q;
            }
        }
        return nearestPoint;
    }

    private void valid(Object o) {
        if (o == null)
            throw new IllegalArgumentException();
    }

    public static void main(String[] args) {

    }
}

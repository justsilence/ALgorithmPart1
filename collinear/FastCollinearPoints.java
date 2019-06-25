/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        isLegal(points);
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkDuplicates(sortedPoints);
        lineSegments = new ArrayList<>();
        for (Point point : sortedPoints) {
            Point[] slopeOrderedPoints = sortedPoints.clone();
            Arrays.sort(slopeOrderedPoints, point.slopeOrder());
            int i = 0;
            double prevSlope = point.slopeTo(point);

            for (int j = 1; j < slopeOrderedPoints.length; j++) {
                Point currPoint = slopeOrderedPoints[j];
                double currSlope = point.slopeTo(currPoint);
                if (Double.compare(prevSlope, currSlope) != 0) {
                    prevSlope = currSlope;
                    if (point.compareTo(slopeOrderedPoints[i]) <= 0) {
                        if (j - i >= 3) {
                            lineSegments.add(new LineSegment(point, slopeOrderedPoints[j - 1]));
                        }
                    }
                    i = j;
                } else if (j == slopeOrderedPoints.length - 1) {
                    if (point.compareTo(slopeOrderedPoints[i]) <= 0) {
                        if (j - i >= 2) {
                            lineSegments.add(new LineSegment(point, currPoint));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }


    private void isLegal(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException();
        }
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i].equals(points[i - 1]))
                throw new IllegalArgumentException();
        }
    }
}

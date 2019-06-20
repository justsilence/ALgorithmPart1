/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    // finds all line segments containing 4 points
    private ArrayList<LineSegment> lineSegmentArrayList;
    public BruteCollinearPoints(Point[] points) {
        isLegal(points);
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkDuplicates(sortedPoints);
        lineSegmentArrayList = new ArrayList<>();
        for (int p = 0; p < sortedPoints.length - 3; p++) {
            for (int q = p + 1; q < sortedPoints.length - 2; q++) {
                for (int r = q + 1; r < sortedPoints.length - 1; r++) {
                    for (int s = r + 1; s < sortedPoints.length; s++) {
                        if (sortedPoints[p].slopeTo(sortedPoints[q]) == sortedPoints[p].slopeTo(sortedPoints[r])
                                && sortedPoints[p].slopeTo(sortedPoints[q]) == sortedPoints[p].slopeTo(sortedPoints[s])) {
                            lineSegmentArrayList.add(new LineSegment(sortedPoints[p], sortedPoints[s]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegmentArrayList.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegmentArrayList.toArray(new LineSegment[0]);
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

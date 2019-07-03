/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;

public class KdTree {
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    private Node root;

    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        valid(p);
        if (!contains(p)) {
            if (root == null)
                root = insert(p, root, null, true);
            else
                root = insert(p, root, root.rect, true);
            size++;
        }
    }

    private Node insert(Point2D p, Node node, RectHV rect, boolean orientation) {
        if (node == null) {
            if (rect == null) {
                rect = new RectHV(0, 0, 1, 1);
            }
            return new Node(p, rect);
        }

        if (orientation) {
            if (Point2D.X_ORDER.compare(p, node.p) < 0) {
                if (node.lb == null) {
                    rect = new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax());
                } else {
                    rect = node.lb.rect;
                }
                node.lb = insert(p, node.lb, rect, false);
            } else {
                if (node.rt == null) {
                    rect = new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
                } else {
                    rect = node.rt.rect;
                }
                node.rt = insert(p, node.rt, rect, false);
            }
        } else {
            if (Point2D.Y_ORDER.compare(p, node.p) < 0) {
                if (node.lb == null) {
                    rect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y());
                } else {
                    rect = node.lb.rect;
                }
                node.lb = insert(p, node.lb, rect, true);
            } else {
                if (node.rt == null) {
                    rect = new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax());
                } else {
                    rect = node.rt.rect;
                }
                node.rt = insert(p, node.rt, rect, true);
            }
        }

        return node;

    }

    public boolean contains(Point2D p) {
        valid(p);
        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean orientation) {
        if (node == null)
            return false;
        if (node.p.equals(p))
            return true;
        if (orientation) {
            if (Point2D.X_ORDER.compare(node.p, p) <= 0) {
                return contains(node.rt, p, false);
            } else {
                return contains(node.lb, p, false);
            }
        } else {
            if (Point2D.Y_ORDER.compare(node.p, p) <= 0) {
                return contains(node.rt, p, true);
            } else {
                return contains(node.lb, p, true);
            }
        }
    }

    public void draw() {
        if (isEmpty())
            return ;
        draw(root, true);
    }

    private void draw(Node node, boolean orientation) {
        if (node == null)
            return ;
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());

        StdDraw.setPenRadius();
        if (orientation) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }

        StdDraw.pause(160);

        if (node.lb != null) {
            draw(node.lb, !orientation);
        }
        if (node.rt != null) {
            draw(node.rt, !orientation);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        valid(rect);
        Queue<Point2D> q = new Queue<>();
        range(root, q, rect);
        return q;
    }

    private void range(Node node, Queue<Point2D> q, RectHV rect) {
        if (node == null) {
            return ;
        }
        if (rect.contains(node.p)) {
            q.enqueue(node.p);
        }
        if (node.lb != null && rect.intersects(node.lb.rect)) {
            range(node.lb, q, rect);
        }
        if (node.rt != null && rect.intersects(node.rt.rect)) {
            range(node.rt, q, rect);
        }
    }


    public Point2D nearest(Point2D p) {
        valid(p);
        if (isEmpty())
            return null;
        return nearest(root, p, root.p, true);
    }

    private Point2D nearest(Node node, Point2D p, Point2D nearestPoint, boolean orientation) {
        if (node == null) {
            return nearestPoint;
        }

        Point2D currNearestPoint = nearestPoint;

        if (p.distanceSquaredTo(node.p) < p.distanceSquaredTo(nearestPoint)) {
            currNearestPoint = node.p;
        }

        if (orientation) {
            if (p.x() < node.p.x()) {
                currNearestPoint = nearest(node.lb, p, currNearestPoint, !orientation);
                if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearestPoint)) {
                    currNearestPoint = nearest(node.rt, p, currNearestPoint, !orientation);

                }
            } else {
                currNearestPoint = nearest(node.rt, p, currNearestPoint, !orientation);
                if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearestPoint)) {
                    currNearestPoint = nearest(node.lb, p, currNearestPoint, !orientation);
                }
            }
        } else {
            if (p.y() < node.p.y()) {
                currNearestPoint = nearest(node.lb, p, currNearestPoint, !orientation);
                if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearestPoint)) {
                    currNearestPoint = nearest(node.rt, p, currNearestPoint, !orientation);
                }
            } else {
                currNearestPoint = nearest(node.rt, p, currNearestPoint, !orientation);
                if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearestPoint)) {
                    currNearestPoint = nearest(node.lb, p, currNearestPoint, !orientation);
                }
            }
        }

        return currNearestPoint;
    }

    private void valid(Object o) {
        if (o == null)
            throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        kdtree.draw();
    }
}

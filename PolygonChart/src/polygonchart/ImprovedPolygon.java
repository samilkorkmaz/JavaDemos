package polygonchart;

import java.awt.Polygon;

/**
 * Polygon class which always returns true when a point lies on one of its edges.<br>
 *
 * Removes problem of "insideness" definition in standard polygon. The standard definition is as follows:<br>
 * A point is considered to lie inside a Shape if and only if:<br>
 * it lies completely inside theShape boundary or<br>
 * it lies exactly on the Shape boundary and the space immediately adjacent to the point in the increasing X direction is entirely inside the boundary<br>
 * or it lies exactly on a horizontal boundary segment and the space immediately adjacent to the point in the increasing Y direction is inside the boundary.<br>
 *
 * Example: xPoints = {0, 5, 10, 15, 15}, yPoints = {0, 5, 3, 10, 0}. According to this definition, contains(5,5), contains(15,10), contains(15, 0) will all
 * return false although these points are on the polygon. ImprovedPolygon returns true for these cases by checking if the input coordinates lie on the edges of
 * the polygon using the line formula y = m*x + n.
 *
 * @author samil korkmaz
 * @date November 2014
 * @blog http://www.samilkorkmaz.blogspot.com
 * @copyright Public domain
 */
public class ImprovedPolygon extends Polygon {

    private static final double DEFAULT_TOLERANCE = 1e-15;

    public ImprovedPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        this(xPoints, yPoints, nPoints, DEFAULT_TOLERANCE);
    }

    public ImprovedPolygon(int[] xPoints, int[] yPoints, int nPoints, final double tolerance) {
        super(xPoints, yPoints, nPoints);
    }

    @Override
    public boolean contains(final int x, final int y) {
        boolean contains = super.contains(x, y);
        if (!contains) {
            //check if point lies on an edge of polygon by using the line formula y = m*x+n.
            for (int i = 0; i < this.npoints - 1; i++) {
                int x0 = this.xpoints[i];
                int x1 = this.xpoints[i + 1];
                int y0 = this.ypoints[i];
                int y1 = this.ypoints[i + 1];
                double dx = x1 - x0;
                if (Math.abs(dx) < DEFAULT_TOLERANCE) {
                    //prevent division by zero. dx == 0 means vertical line (i.e x = constant line).
                    if (isOnLineInterval(x, y, x0, x1, y0, y1)) {
                        contains = true;
                        break;
                    }
                } else {
                    double dy = y1 - y0;
                    double m = dy / dx;
                    double n = y0 - m * x0;
                    if (Math.abs(y - (m * x + n)) < DEFAULT_TOLERANCE) {
                        //check if point is on line segment (not on the extension):
                        if (isOnLineInterval(x, y, x0, x1, y0, y1)) {
                            contains = true;
                            break;
                        }
                    }
                }
            }
        }
        return contains;
    }
    
    private boolean isOnLineInterval(final int x, final int y, final int x0, final int x1, final int y0, final int y1) {
        return (x >= x0 && x <= x1 || x <= x0 && x >= x1) && (y >= y0 && y <= y1 || y <= y0 && y >= y1);
    }

}

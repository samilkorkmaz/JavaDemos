package java3ddemo;

import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

/**
 * Draw line.<br/>
 * Procedure:<br/>
 * 1. Set line vertices (endpoints) and add it to branchGroup.<br>
 * 2. (Optional) Create background and add it to branchGroup. If this is not done, background will be black.<br>
 * 3. Create universe, set view and add branchGroup to universe.<br>
 *
 * Note: If after this demo you call the other demos, sometimes the line is not drawn.
 *
 * @author skorkmaz
 */
public class Demo0 {

    public Demo0() {
        BranchGroup branchGroup = new BranchGroup();
        branchGroup.addChild(getLineObject());
        branchGroup.addChild(getBackground(new Color3f(1f, 1f, 0.2f)));

        SimpleUniverse universe = new SimpleUniverse();
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(branchGroup);
    }

    private Shape3D getLineObject() {
        //Create line vertex coordinates:
        LineArray lineArray = new LineArray(2, GeometryArray.COORDINATES | GeometryArray.COLOR_3);
        lineArray.setCoordinate(0, new Point3f(-0.5f, 0.4f, 0f));
        lineArray.setCoordinate(1, new Point3f(0.5f, -0.1f, 0f));
        //Assign color to vertices:
        Color3f lineColor = new Color3f(1f, 0f, 0f);
        lineArray.setColor(0, lineColor);
        lineArray.setColor(1, lineColor);
        //Add line to branchGroup:
        Shape3D shape3D = new Shape3D(lineArray);
        return shape3D;
    }

    private Background getBackground(Color3f bgColor) {
        //Create a bounding sphere of r=0.1 and  for the background. Note that the value of r does not seem to matter, I think it 
        //only matters when there is also a light source:
        Point3d center = new Point3d(0.0, 0.0, 0.0);
        double radius = 0.1;
        BoundingSphere bounds = new BoundingSphere(center, radius);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bounds);
        return bg;
    }
}

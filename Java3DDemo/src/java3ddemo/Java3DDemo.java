package java3ddemo;

/**
 * Java 3D demos from java3d.org.<br>
 * 
 * Dependencies: j3dcore.jar, j3dutils.jar, vecmath.jar, j3dcore-ogl.dll
 *
 * @author skorkmaz, 2014
 */
public class Java3DDemo {

    public static void main(String[] args) {
        System.out.println("Library path: " + System.getProperty("java.library.path"));
        Demo0 demo0 = new Demo0();
        Demo1 demo1 = new Demo1();
        Demo2 demo2 = new Demo2();
    }
}

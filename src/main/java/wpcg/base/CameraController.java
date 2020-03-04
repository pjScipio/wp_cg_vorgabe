package wpcg.base;

import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.control.CameraControl;

public class CameraController extends CameraControl {

    private final Camera cam;

    private float angleAroundUp = 0.06f;

    private Vector3f ref;

    public CameraController(Camera cam) {
        this.cam = cam;
    }

    /**
     * Rotate the camera around the up vector.
     */
    public void rotateAroundUp(float sign) {
        Vector3f eye = cam.getLocation();
        Vector3f dir = ref.subtract(eye);
        Matrix3f m = new Matrix3f();
        m.fromAngleAxis(angleAroundUp * sign, cam.getUp());
        Vector3f newDir = m.mult(dir);
        Vector3f newEye = ref.subtract(newDir);
        cam.setLocation(newEye);
        cam.lookAt(ref, cam.getUp());
    }

    /**
     * Rotate the camera around the left vector.
     */
    public void rotateAroundLeft(float sign) {
        Vector3f eye = cam.getLocation();
        Vector3f dir = ref.subtract(eye);
        Matrix3f m = new Matrix3f();
        m.fromAngleAxis(angleAroundUp * sign, cam.getLeft());
        Vector3f newDir = m.mult(dir);
        Vector3f newEye = ref.subtract(newDir);
        cam.setLocation(newEye);
        cam.lookAt(ref, cam.getUp());
    }

    public void lookAt(Vector3f ref, Vector3f up) {
        this.ref = ref.clone();
        cam.lookAt(ref, up);
    }

    public void setup(Vector3f eye, Vector3f ref, Vector3f up ){
        cam.setLocation(eye);
        this.ref = ref.clone();
        cam.lookAt(ref, up);
    }

    public void zoomIn() {
        Vector3f eye = cam.getLocation();
        Vector3f ref = this.ref;
        Vector3f dir = ref.subtract(eye);
        dir = dir.mult(0.02f);
        cam.setLocation(eye.add(dir));
    }

    public void zoomOut() {
        Vector3f eye = cam.getLocation();
        Vector3f ref = this.ref;
        Vector3f dir = ref.subtract(eye);
        dir = dir.mult(-0.02f);
        cam.setLocation(eye.add(dir));
    }
}

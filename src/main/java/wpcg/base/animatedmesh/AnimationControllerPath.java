package wpcg.base.animatedmesh;

import com.jme3.math.Vector3f;
import wpcg.base.Path;

import java.util.List;

/**
 * An entity has a location and orientation in the scene.
 */
public class AnimationControllerPath implements AnimationController {

    /**
     * Up-Vector in of the entity.
     */
    private Vector3f up;

    /**
     * Path for the entity to move on.
     */
    private Path path;

    private float speed;

    public AnimationControllerPath(List<Vector3f> waypoints, float speed) {
        this.path = new Path(waypoints);
        this.speed = speed;
    }

    public Pose getPose() {
        return path.getCurrentPose();
    }

    @Override
    public Vector3f getDirection() {
        return path.getDirection();
    }

    public void move(float time) {
        path.moveOnPath(time * speed);
    }
}

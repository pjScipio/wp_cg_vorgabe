package wpcg.base.animatedmesh;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Container for an animated mesh in the jMonkey scene graph.
 */
public class AnimatedMesh extends Node implements AnimEventListener {

    /**
     * Scene graph node.
     */
    private Node node;

    /**
     * Animation channel for the player wpcg.model.
     */
    private AnimChannel channel;

    private AnimationController animationController;

    public AnimatedMesh(Node node, AnimationController animationController) {
        this.node = node;
        this.animationController = animationController;
        node.setLocalScale(0.003f);

        AnimControl control = node.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("walk");
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    public Node getNode() {
        return node;
    }

    public void update(float time) {
        animationController.move(time);
        getNode().setLocalTranslation(animationController.getPose().pos);
        getNode().setLocalRotation(animationController.getPose().rot);
    }

    public Vector3f getDirection() {
        return animationController.getDirection();
    }
}

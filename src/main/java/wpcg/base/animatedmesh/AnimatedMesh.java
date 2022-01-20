/**
 * This file is part of the computer graphics project of the computer graphics group led by
 * Prof. Dr. Philipp Jenke at the University of Applied Sciences (HAW) in Hamburg.
 */

package wpcg.base.animatedmesh;

import com.jme3.anim.AnimComposer;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Container for an animated mesh in the jMonkey scene graph.
 */
public class AnimatedMesh extends Node {

  /**
   * Scene graph node.
   */
  private Node node;

  /**
   * This controller is used to define the movement of the mesh over time.
   */
  private AnimationController animationController;

  public AnimatedMesh(Node node, AnimationController animationController) {
    this.node = node;
    this.animationController = animationController;
    AnimComposer composer = node.getControl(AnimComposer.class);
    var clips = composer.getAnimClips();
    composer.setCurrentAction("walk");
  }

  /**
   * Call this method to advance in the animation based on the current
   * controller.
   */
  public void update(float time) {
    animationController.move(time);
    getNode().setLocalTranslation(animationController.getPose().pos);
    getNode().setLocalRotation(animationController.getPose().rot);
  }

  // +++ GETTER/SETTER ++++++++++++++++

  public Vector3f getDirection() {
    return animationController.getDirection();
  }

  public Node getNode() {
    return node;
  }

  public void setAnimationController(AnimationController animationController) {
    this.animationController = animationController;
  }
}

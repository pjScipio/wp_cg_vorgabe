package wpcg.a1_a2_walking;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.shader.VarType;
import wpcg.base.Scene;
import wpcg.base.*;
import wpcg.base.animatedmesh.AnimatedMesh;
import wpcg.base.animatedmesh.AnimationControllerPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntroScene extends Scene {

    private AssetManager assetManager;
    private Node rootNode;
    private Node knightNode;
    private List<AnimatedMesh> animatedMeshList;

    public IntroScene() {
        assetManager = null;
        rootNode = null;
        knightNode = null;
        animatedMeshList = new ArrayList<>();
    }

    @Override
    public void init(AssetManager assetManager, Node rootNode, CameraController cameraController) {
        this.assetManager = assetManager;
        this.rootNode = rootNode;
        cameraController.setup(new Vector3f(-10, 10, -10),
                new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));

        // Load animated knight
        knightNode = loadCharacter(assetManager, rootNode, "Models/knight.gltf");
        AnimationControllerPath knightAnimationController = new AnimationControllerPath(Arrays.asList(
                new Vector3f(6, 0, -6),
                new Vector3f(6, 0, -3.5f),
                new Vector3f(-3.5f, 0, 6),
                new Vector3f(-6f, 0, 6f),
                new Vector3f(-6f, 0, -6f)), 1.2f);
        addAnimatedMesh(new AnimatedMesh(knightNode, knightAnimationController));

        createGround();
    }

    @Override
    public void update(float time) {
        // Update position
        for (AnimatedMesh animatedMesh : animatedMeshList) {
            animatedMesh.update(time);
        }
    }

    @Override
    public void render() {
    }


    protected void addAnimatedMesh(AnimatedMesh animatedMesh) {
        animatedMeshList.add(animatedMesh);
    }

    /**
     * Generate a ground mesh.
     */
    private void createGround() {
        float extend = 15;
        Box box = new Box(new Vector3f(-extend / 2, -0.05f, -extend / 2),
                new Vector3f(extend / 2, 0, extend / 2));
        Geometry quadGeometry = new Geometry("Ground", box);
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", new ColorRGBA(0.3f, 0.15f, 0.05f, 1));
        mat.setParam("UseMaterialColors", VarType.Boolean, true);
        quadGeometry.setMaterial(mat);
        rootNode.attachChild(quadGeometry);
        quadGeometry.setShadowMode(RenderQueue.ShadowMode.Receive);
    }

    protected Node getKnightNode() {
        return knightNode;
    }
}

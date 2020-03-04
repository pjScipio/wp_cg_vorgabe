package wpcg.base;

import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;

public abstract class Scene {
    public abstract void init(AssetManager assetManager, Node rootNode, CameraController cameraController);

    public abstract void update(float time);

    public abstract void render();

    public void setupLights(AssetManager assetManager, Node rootNode, ViewPort viewPort) {
        // Scene
        PointLight light1 = new PointLight(new Vector3f(-10, 10, -10));
        light1.setColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 1));
        rootNode.addLight(light1);

        // Shadows
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(new ColorRGBA(1, 1, 1, 1));
        sun.setDirection(new Vector3f(0.5f, -1, -0.5f));
        rootNode.addLight(sun);
    }

    /**
     * Load an animated character node.
     *
     * @return Scene graph node with the animated character
     */
    protected Node loadCharacter(AssetManager assetManager, Node rootNode, String gltfFilename) {
        Node node = (Node) assetManager.loadModel(gltfFilename);
        node = (Node) node.getChild("knight");
        node.setShadowMode(RenderQueue.ShadowMode.Cast);
        rootNode.attachChild(node);
        return node;
    }
}

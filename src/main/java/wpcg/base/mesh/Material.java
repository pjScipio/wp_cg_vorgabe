package wpcg.base.mesh;

import com.jme3.math.ColorRGBA;

/**
 * Represents an OBJ-file material.
 */
public class Material {
    private String name;
    private String textureFilename = null;
    private ColorRGBA color = new ColorRGBA(1, 1, 1, 1);

    public Material(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public void setColor(ColorRGBA color) {
        this.color = color;
    }

    public String getTextureFilename() {
        return textureFilename;
    }

    public void setTextureFilename(String filename) {
        this.textureFilename = filename;
    }

    @Override
    public String toString() {
        return name + ": " + color + ", " + textureFilename;
    }
}

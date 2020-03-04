/**
 * Diese Datei gehört zum Android/Java Framework zur Veranstaltung "Computergrafik für
 * Augmented Reality" von Prof. Dr. Philipp Jenke an der Hochschule für Angewandte
 * Wissenschaften (HAW) Hamburg. Weder Teile der Software noch das Framework als Ganzes dürfen
 * ohne die Einwilligung von Philipp Jenke außerhalb von Forschungs- und Lehrprojekten an der HAW
 * Hamburg verwendet werden.
 * <p>
 * This file is part of the Android/Java framework for the course "Computer graphics for augmented
 * reality" by Prof. Dr. Philipp Jenke at the University of Applied (UAS) Sciences Hamburg. Neither
 * parts of the framework nor the complete framework may be used outside of research or student
 * projects at the UAS Hamburg.
 */
package wpcg.base.mesh;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 * Represents a vertex in 3-space with position and normal.
 *
 * @author Philipp Jenke
 */
public class Vertex {

    /**
     * Vertex position in 3-space.
     */
    protected Vector3f position = new Vector3f(0, 0, 0);

    /**
     * Vertex normal in 3-space.
     */
    protected Vector3f normal = new Vector3f(0, 1, 0);

    /**
     * Color in RBGA format.
     */
    protected ColorRGBA color = new ColorRGBA(0.5f, 0.5f, 0.5f, 1);

    public Vertex(Vector3f position) {
        this(position, new Vector3f(0, 1, 0));
    }

    public Vertex(Vector3f position, Vector3f normal) {
        this.position = new Vector3f(position);
        this.normal = new Vector3f(normal);
    }

    public Vertex(Vertex vertex) {
        this.position = new Vector3f(vertex.position);
        this.normal = new Vector3f(vertex.normal);
        this.color = new ColorRGBA(vertex.color);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public void setColor(ColorRGBA color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return position.toString();
    }
}

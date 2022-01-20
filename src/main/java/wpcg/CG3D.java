/**
 * This file is part of the computer graphics project of the computer graphics group led by
 * Prof. Dr. Philipp Jenke at the University of Applied Sciences (HAW) in Hamburg.
 */

package wpcg;

import ui.GenericCGApplication;
import wpcg.lab.a1.IntroScene;

/**
 * This launcher is used to start the 3D application with a Java Swing user interface.
 */
public class CG3D extends GenericCGApplication {

  public CG3D() {
    super("WP Einführung in die Computergrafik");

    addScene3D(new IntroScene());
  }

  public static void main(String[] args) {
    new CG3D();
  }
}

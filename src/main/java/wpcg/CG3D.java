/**
 * Diese Datei ist Teil des Vorgabeframeworks zur Lehrveranstaltung Einführung in die Computergrafik
 * an der Hochschule für Angewandte Wissenschaften Hamburg von Prof. Philipp Jenke (Informatik)
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

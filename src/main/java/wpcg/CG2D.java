/**
 * Diese Datei ist Teil des Vorgabeframeworks zur Lehrveranstaltung Einführung in die Computergrafik
 * an der Hochschule für Angewandte Wissenschaften Hamburg von Prof. Philipp Jenke (Informatik)
 */

package wpcg;

import wpcg.base.sprites.Constants;
import wpcg.base.sprites.SpriteCanvas;
import wpcg.base.ui.Scene2D;

import javax.swing.*;
import java.awt.*;

/**
 * Lecture support application for 2D scenes
 */
public class CG2D extends JFrame {

  public CG2D() {

    // Layout
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    getContentPane().add(mainPanel);

    // TODO: Hier anpassen
    Scene2D canvas2D = new Scene2D(600, 600);
    //Scene2D canvas2D = new MyRendererScene(640, 480);
    //Scene2D canvas2D = new SimplificationScene(640, 480);
    mainPanel.add(canvas2D, BorderLayout.CENTER);

    // User interface
    JPanel ui = canvas2D.getUi();
    if (ui != null) {
      mainPanel.add(ui, BorderLayout.EAST);
      ui.setOpaque(true);
      ui.setBackground(Color.WHITE);
    }

    // Here be dragons
    SpriteCanvas spriteCanvas = new SpriteCanvas(100, 70);
    new Thread(() -> {
      while (true) {
        spriteCanvas.loop();
        spriteCanvas.repaint();
        try {
          Thread.sleep((int) (1000.0 / Constants.RENDER_FPS));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
    mainPanel.add(spriteCanvas, BorderLayout.SOUTH);

    // Window setup
    setTitle("Mixed Reality");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(800, 600);
    pack();
    setVisible(true);
  }

  public static void main(String[] args) {
    new CG2D();
  }
}

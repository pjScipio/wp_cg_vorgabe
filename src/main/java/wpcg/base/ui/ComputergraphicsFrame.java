/**
 * Diese Datei ist Teil des Vorgabeframeworks zur Lehrveranstaltung Einführung in die Computergrafik
 * an der Hochschule für Angewandte Wissenschaften Hamburg von Prof. Philipp Jenke (Informatik)
 */

package wpcg.base.ui;

import com.jme3.system.awt.AwtPanel;
import wpcg.base.sprites.Constants;
import wpcg.base.sprites.SpriteCanvas;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents and generates the Java Swing user interface.
 */
public class ComputergraphicsFrame extends JFrame {

  public ComputergraphicsFrame(AwtPanel jmePanel, Scene3D scene) {
    super(scene.getTitle());

    // Here be dragons
    SpriteCanvas spriteCanvas = new SpriteCanvas(800, 70);
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

    // UI panel
    Box uiPanel = new Box(BoxLayout.Y_AXIS);
    uiPanel.setOpaque(true);
    uiPanel.setBackground(Color.WHITE);
    uiPanel.add(spriteCanvas);
    JPanel sceneUI = scene.getUI();
    if (sceneUI != null) {
      sceneUI.setOpaque(true);
      sceneUI.setBackground(Color.WHITE);
      uiPanel.add(sceneUI);
    }
    uiPanel.add(Box.createHorizontalGlue());
    JPanel uiContainer = new JPanel();
    uiContainer.add(uiPanel);

    // Layout
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jmePanel, BorderLayout.CENTER);
    getContentPane().add(spriteCanvas, BorderLayout.SOUTH);
    getContentPane().add(uiContainer, BorderLayout.EAST);

    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
}

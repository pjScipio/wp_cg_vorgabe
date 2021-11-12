/**
 * Diese Datei ist Teil des Vorgabeframeworks zur Lehrveranstaltung Einführung in die Computergrafik
 * an der Hochschule für Angewandte Wissenschaften Hamburg von Prof. Philipp Jenke (Informatik)
 */

package wpcg;

import com.jme3.system.AppSettings;
import com.jme3.system.awt.AwtPanel;
import com.jme3.system.awt.AwtPanelsContext;
import com.jme3.system.awt.PaintMode;
import wpcg.base.ui.ComputergraphicsFrame;
import wpcg.base.ui.ComputergraphicsJMEApp;
import wpcg.base.ui.Scene3D;
import wpcg.lab.a1.IntroScene;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This launcher is used to start the 3D application with a Java Swing user interface.
 */
public class CG3D {

  /**
   * Size of the jMonkey canvas
   */
  public static final int JME_CANVAS_WIDTH = 600;
  public static final int JME_CANVAS_HEIGHT = 500;

  /**
   * This is the jMonkey application
   */
  private static ComputergraphicsJMEApp app;

  /**
   * This scene contains the content of the current application (exercise).
   */
  private static Scene3D scene;

  public static void main(String[] args) {

    // Change scene object here
    scene = new IntroScene();

    // Initialize jMonkey
    AppSettings settings = new AppSettings(true);
    settings.setAudioRenderer(null);
    settings.setWidth(JME_CANVAS_WIDTH);
    settings.setHeight(JME_CANVAS_HEIGHT);
    settings.setCustomRenderer(AwtPanelsContext.class);
    app = new ComputergraphicsJMEApp(scene);
    app.setSettings(settings);
    app.setShowSettings(false);
    app.createCanvas();
    app.setPauseOnLostFocus(false);
    AwtPanelsContext jmeContext = (AwtPanelsContext) app.getContext();
    jmeContext.setSystemListener(app);
    app.startCanvas();

    // Wait for OpenGL context
    while (app.getViewPort() == null) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // Extract canvas and add to Swing UI
    Dimension dim = new Dimension(JME_CANVAS_WIDTH, JME_CANVAS_HEIGHT);
    AwtPanel jmePanel = jmeContext.createPanel(PaintMode.Accelerated);
    jmeContext.setInputSource(jmePanel);
    jmePanel.attachTo(true, app.getViewPort());
    jmePanel.attachTo(true, app.getGuiViewPort());
    jmePanel.setPreferredSize(dim);
    jmePanel.setMinimumSize(dim);
    SwingUtilities.invokeLater(() -> {
      ComputergraphicsFrame cgFrame = new ComputergraphicsFrame(jmePanel, scene);
      app.enqueue(() -> app.getFlyByCamera().setDragToRotate(true));
    });

    Logger.getLogger("com.jme3").setLevel(Level.SEVERE);
  }
}

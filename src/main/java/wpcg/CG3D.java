package wpcg;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import wpcg.a1.IntroScene;
import wpcg.a5.A5Scene;
import wpcg.base.Scene;
import wpcg.solution.a1.A1SceneSolution;
import wpcg.solution.a2.A2SceneSolution;
import wpcg.solution.a3.A3SceneSolution;
import wpcg.solution.a4.A4SceneSolution;
import wpcg.solution.a5.A5SceneSolution;
import wpcg.solution.a6.A6SceneSolution;
import wpcg.solution.a7.A7SceneSolution;
import wpcg.ui.ComputergraphicsJMEApp;
import wpcg.ui.ComputergraphicsFrame;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This launcher is used to start the 3D application with a Java Swing user interface.
 */
public class CG3D {

  /**
   * Never call this method but in the main().
   */
  private void start(Scene scene) {
    java.awt.EventQueue.invokeLater(() -> {
      AppSettings settings = new AppSettings(true);
      settings.setTitle("WP Computergraphics (3D)");
      settings.setUseInput(false);
      settings.setWidth(640);
      settings.setHeight(480);

      ComputergraphicsJMEApp app = new ComputergraphicsJMEApp(scene);
      app.setSettings(settings);
      app.setShowSettings(false);
      app.createCanvas();
      app.setPauseOnLostFocus(false);
      JmeCanvasContext ctx = (JmeCanvasContext) app.getContext();
      ctx.setSystemListener(app);

      new ComputergraphicsFrame(ctx.getCanvas(), app, scene);
      app.startCanvas();
    });
  }

  public static void main(String[] args) {
    // Change scene object here
    Scene scene = new A7SceneSolution();

    // no change here
    Logger.getLogger("com.jme3").setLevel(Level.SEVERE);
    new CG3D().start(scene);
  }
}

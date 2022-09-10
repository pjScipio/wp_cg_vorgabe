/**
 * This file is part of the computer graphics project of the computer graphics group led by
 * Prof. Dr. Philipp Jenke at the University of Applied Sciences (HAW) in Hamburg.
 */

package wpcg;

import com.jme3.system.AppSettings;
import ui.CG3DApplication;
import wpcg.lab.a4.A4Scene;
import wpcg.lab.a7.A7Scene;
import wpcg.lab.solution.a4.A4SceneSolution;
import wpcg.lab.solution.a5.A5SceneSolution;
import wpcg.lab.solution.a6.A6SceneSolution;
import wpcg.lab.solution.a7.A7SceneSolution;

/**
 * This is the base class for the lecture exercise in "WP Computergrafik"
 */
public class CGApp extends CG3DApplication {

    public CGApp() {
        // Set the 3D scene (replace for each assignment)
        setScene3D(new A7SceneSolution());
    }

    public static void main(String[] args) {
        // Setup JME app
        var app = new CGApp();
        AppSettings appSettings = new AppSettings(true);
        appSettings.setTitle("Intro to Computer Graphics");
        appSettings.setResolution(800, 600);
        appSettings.setFullscreen(false);
        appSettings.setAudioRenderer(null);
        app.setSettings(appSettings);
        app.setShowSettings(false);
        app.setDisplayStatView(false);
        app.start();
    }
}

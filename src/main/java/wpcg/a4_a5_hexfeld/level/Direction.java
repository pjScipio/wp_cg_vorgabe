package wpcg.a4_a5_hexfeld.level;

import com.jme3.math.Vector2f;

/**
 * Richtungen im Spiel (für Steuerung, Wandpositionierung, ...)
 */
public enum Direction {

    UHR_0, UHR_2, UHR_4, UHR_6, UHR_8, UHR_10;

    /**
     * Liefert die Gegenüber-Richtung der aktuellen Richtung.
     *
     * @return
     */
    public Direction getGegenueber() {
        return values()[(ordinal() + 3) % 6];
    }

    public Vector2f getOrientation() {
        float angle = 0;
        switch (this) {
            case UHR_0:
                angle = 0;
                break;
            case UHR_2:
                angle = -60 / 180.0f * (float) Math.PI;
                break;
            case UHR_4:
                angle = -120 / 180.0f * (float) Math.PI;
                break;
            case UHR_6:
                angle = -180 / 180.0f * (float) Math.PI;
                break;
            case UHR_8:
                angle = -240 / 180.0f * (float) Math.PI;
                break;
            case UHR_10:
                angle = -300 / 180.0f * (float) Math.PI;
                break;
            default:
                throw new IllegalArgumentException("Should not happen");
        }
        return new Vector2f(-(float) Math.sin(angle), (float) -Math.cos(angle));
    }
}

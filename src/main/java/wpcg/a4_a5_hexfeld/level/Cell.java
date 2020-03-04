package wpcg.a4_a5_hexfeld.level;

import com.jme3.math.Vector2f;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Point;
import java.util.List;
import java.util.Map;


/**
 * Eine Zelle in einem Spiel.
 *
 * @author Philipp Jenke
 */
public class Cell {

    private static final String ZELLE_INDEX_I = "indexI";
    private static final String ZELLE_INDEX_J = "indexJ";
    private static final String NACHBARN = "nachbarn";

    /**
     * Hier wird festgelegt welche der Zellseiten Wände sind.
     */
    private Link[] nachbar = new Link[6];

    /**
     * Mittelpunkt der Zelle.
     */
    private Point index = null;


    public Cell() {
        this.index = null;
    }

    public Cell(Point index) {
        this.index = new Point(index);
    }

    /**
     * Liefert wahr, wenn die Seite in der angegebenen Richtung eine Wand ist,
     * sonst falsch.
     */
    public Cell getNachbarZelle(Direction richtung) {
        if (nachbar[richtung.ordinal()] == null) {
            return null;
        }
        return nachbar[richtung.ordinal()].getAndereZelle(this);
    }

    public Point getIndex() {
        return index;
    }

    public void setzeNachbar(Direction richtung, Cell nachbarZelle) {
        Link link = new Link(this, nachbarZelle);
        nachbar[richtung.ordinal()] = link;
        nachbarZelle.setzeLink(richtung.getGegenueber(), link);
    }

    private void setzeLink(Direction richtung, Link link) {
        nachbar[richtung.ordinal()] = link;
    }

    public void setWand(Direction richtung, boolean hatWand) {
        Link link = nachbar[richtung.ordinal()];
        if (link != null) {
            link.setWand(hatWand);
        }
    }

    /**
     * Liefert wahr wenn in der angegebenen Richtung eine Wand liegt.
     */
    public boolean istWand(Direction richtung) {
        return nachbar[richtung.ordinal()] == null
                || nachbar[richtung.ordinal()].istWand();
    }


    public Link getLink(Direction richtung) {
        return nachbar[richtung.ordinal()];
    }

    public JSONObject toJson(Object metaInformation) {
        JSONObject zellenObjekt = new JSONObject();
        Map<Link, Integer> linkMap = (Map<Link, Integer>) metaInformation;
        JSONArray links = new JSONArray();
        for (int i = 0; i < 6; i++) {
            Link link = getLink(Direction.values()[i]);
            if (link == null) {
                links.put(-1);
            } else {
                links.put(linkMap.get(link));
            }
        }
        zellenObjekt.put(NACHBARN, links);
        zellenObjekt.put(ZELLE_INDEX_I, getIndex().x);
        zellenObjekt.put(ZELLE_INDEX_J, getIndex().y);
        // Asset wird vom Asset selber gesetzt
        return zellenObjekt;
    }

    /**
     * @param metaInformation Liste aller Links (damit die Nachbarn gesetzt werden können).
     */
    public void fromJson(JSONObject jsonObjekt, Object metaInformation) {
        @SuppressWarnings("unchecked")
        List<Link> links = (List<Link>) metaInformation;
        index = new Point(jsonObjekt.getInt(ZELLE_INDEX_I),
                jsonObjekt.getInt(ZELLE_INDEX_J));
        JSONArray nachbarn = (JSONArray) jsonObjekt.get(NACHBARN);
        for (int i = 0; i < 6; i++) {
            int nachbarIndex = nachbarn.getInt(i);
            if (nachbarIndex < 0) {
                nachbar[i] = null;
            } else {
                Link link = links.get(nachbarIndex);
                nachbar[i] = link;
                link.setZelle(this);
            }
        }
    }

    /**
     * Liefert die Weltkoordinaten zu einem Index im Gitter der Zellen.
     */
    public Vector2f getCenter() {
        float x = index.x * 1.5f + 1;
        float y = index.y * getZellenhoehe() * 2.0f
                + ((index.x % 2 == 1) ? getZellenhoehe() : 0)
                + 1;
        return new Vector2f(x, y);
    }

    /**
     * Hilfsvariable: Höhe eines der sechs gleichseitigen Dreiecke, die zusammen
     * eine Zelle ergeben.
     */
    public static float getZellenhoehe() {
        return
                (float) (Math.sqrt(3) / 2.0);
    }

    /**
     * Return the i'th corner of the cell
     */
    public Vector2f getCorner(int index) {
        Vector2f cellCenter = getCenter();

        //cellCenter = cellCenter.add(new Vector2f(cell.getIndex().x * 5, cell.getIndex().y * 5));

        Vector2f p = null;
        switch (index) {
            case 0:
                p = new Vector2f(cellCenter.x - 0.5f, cellCenter.y - Cell.getZellenhoehe());
                break;
            case 1:
                p = new Vector2f(cellCenter.x + 0.5f, cellCenter.y - Cell.getZellenhoehe());
                break;
            case 2:
                p = new Vector2f(cellCenter.x + 1, cellCenter.y);
                break;
            case 3:
                p = new Vector2f(cellCenter.x + 0.5f, cellCenter.y + Cell.getZellenhoehe());
                break;
            case 4:
                p = new Vector2f(cellCenter.x - 0.5f, cellCenter.y + Cell.getZellenhoehe());
                break;
            case 5:
                p = new Vector2f(cellCenter.x - 1, cellCenter.y);
                break;
            default:
                throw new IllegalArgumentException("Should not happen.");
        }
        return p;
    }
}
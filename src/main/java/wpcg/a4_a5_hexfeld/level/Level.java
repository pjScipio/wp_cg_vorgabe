package wpcg.a4_a5_hexfeld.level;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import org.json.JSONArray;
import org.json.JSONObject;
import wpcg.base.Logger;
import wpcg.solution.a4.editor.Beobachteter;
import wpcg.solution.a4.editor.LevelGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;


/**
 * Repräsentiert den Zustand des Spiels, primär das Spielfeld und dessen Zellen.
 *
 * @author Phiipp Jenke
 */
public class Level extends Beobachteter {

    private static final String ZELLEN = "zellen";
    private static final String LINKS = "links";

    /**
     * Menge aller Zellen
     */
    private Set<Cell> zellen = new HashSet<Cell>();

    /**
     * Erzeugt ein neues Level mit der angegebenen Anzahl von Zeilen und Spalten.
     */
    public Level() {
    }

    public Iterator<Cell> getZellenIterator() {
        return zellen.iterator();
    }

    /**
     * Zelle in Level einfügen.
     */
    public void zelleHinzufuegen(Cell zelle) {
        zellen.add(zelle);
    }

    /**
     * Liefert einen Iterator über alle Links (zu den Nachbarn).
     */
    public Iterator<Link> getLinkIterator() {
        Set<Link> alleLinks = new HashSet<Link>();
        for (Cell zelle : zellen) {
            for (Direction richtung : Direction.values()) {
                Link link = zelle.getLink(richtung);
                if (link != null) {
                    alleLinks.add(link);
                }
            }
        }
        return alleLinks.iterator();
    }

    public void saveJsonToFile(String filename) {
        // Ergebnis schreiben
        try (FileWriter writer = new FileWriter(new File(filename))) {
            JSONObject wurzel = toJson(null);
            wurzel.write(writer);
            System.out.println("JSON Dokument erfolgreich in Datei "
                    + filename + " gschrieben.");
        } catch (FileNotFoundException e) {
            System.out.println("Datei nicht gefunden: " + filename);
        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben in die Datei "
                    + filename);
        }
    }

    private JSONObject toJson(Object metaInformation) {
        JSONObject levelObjekt = new JSONObject();

        // Links
        JSONArray linkListe = new JSONArray();
        Map<Link, Integer> linkMap = new HashMap<Link, Integer>();
        int linkIndex = 0;
        for (Iterator<Link> it = getLinkIterator(); it.hasNext(); ) {
            Link link = it.next();
            linkMap.put(link, linkIndex);
            linkListe.put(link.toJson(null));
            linkIndex++;
        }
        levelObjekt.put(LINKS, linkListe);

        // Zellen
        JSONArray zellenListe = new JSONArray();
        for (Iterator<Cell> it = getZellenIterator(); it.hasNext(); ) {
            Cell zelle = it.next();
            zellenListe.put(zelle.toJson(linkMap));
        }
        levelObjekt.put(ZELLEN, zellenListe);

        return levelObjekt;
    }

    public void readLevelFromFile(String filename) {
        StringBuilder text = new StringBuilder();
        try (Stream<String> stream =
                     Files.lines(Paths.get(filename), StandardCharsets.UTF_8)) {
            stream.forEach(s -> text.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(text.toString());
        fromJson(jsonObject);
    }

    private void fromJson(JSONObject jsonObjekt) {
        // Links
        JSONArray linksArray = (JSONArray) jsonObjekt.get(LINKS);
        List<Link> links = linksVonJson(linksArray);

        // Zellen
        JSONArray zellenArray = (JSONArray) jsonObjekt.get(ZELLEN);
        List<Cell> zellen = zellenVonJson(zellenArray, links);
        for (Cell zelle : zellen) {
            zelleHinzufuegen(zelle);
        }

        Logger.getInstance().msg("Successfully loaded level with " + zellen.size() + " cells");
    }

    /**
     * JSON-Array mit Links entpacken.
     */
    private static List<Link> linksVonJson(JSONArray linksArray) {
        List<Link> links = new ArrayList<Link>();
        for (int i = 0; i < linksArray.length(); i++) {
            Object obj = linksArray.get(i);
            JSONObject linkObjekt = (JSONObject) obj;
            Link link = new Link();
            link.fromJson(linkObjekt, null);
            links.add(link);
        }
        return links;
    }

    /**
     * JSON-Array mit Zellen entpacken.
     */
    private static List<Cell> zellenVonJson(JSONArray zellenArray,
                                            List<Link> links) {
        List<Cell> zellen = new ArrayList<Cell>();
        for (int i = 0; i < zellenArray.length(); i++) {
            Object obj = zellenArray.get(i);
            JSONObject zellenObjekt = (JSONObject) obj;
            Cell zelle = new Cell();
            zelle.fromJson(zellenObjekt, links);
            zellen.add(zelle);
        }
        return zellen;
    }

    public void resize(int resX, int resY) {
        LevelGenerator.generiereLevel(this, resX, resY);
        melden();
    }

    public void clear() {
        zellen.clear();
    }

    public Vector3f getCenter() {
        Vector2f ll = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
        Vector2f ur = new Vector2f(Float.MIN_VALUE, Float.MIN_VALUE);
        for (Cell cell : zellen) {
            for (int i = 0; i < 6; i++) {
                Vector2f p = cell.getCorner(i);
                if (p.x < ll.x) {
                    ll.x = p.x;
                }
                if (p.y < ll.y) {
                    ll.y = p.y;
                }
                if (p.x > ur.x) {
                    ur.x = p.x;
                }
                if (p.y > ur.y) {
                    ur.y = p.y;
                }
            }
        }
        Vector2f center2D = ll.add(ur).mult(0.5f);
        Vector3f center = new Vector3f(center2D.x, 0, center2D.y);
        return center;
    }

    /**
     * Liefert die Zelle mit den angegebenen Koordinaten.
     */
    public Cell getCell(int x, int y) {
        for (Cell cell : zellen) {
            if (cell.getIndex().x == x && cell.getIndex().y == y) {
                return cell;
            }
        }
        return null;
    }
}

/**
 * This file is part of the computer graphics project of the computer graphics group led by
 * Prof. Dr. Philipp Jenke at the University of Applied Sciences (HAW) in Hamburg.
 */

package wpcg.lab.a4.level;

import org.json.JSONObject;

/**
 * Internal data structure: a connection between two cells.
 */
public class Link {

  /**
   * The two connected cells.
   */
  private Cell[] cells;

  /**
   * Flag indicating weather the link has a wall.
   */
  private boolean isWall = false;

  public Link() {
    this(null, null);
  }

  public Link(Cell zelle1, Cell zelle2) {
    cells = new Cell[2];
    isWall = false;
    cells[0] = zelle1;
    cells[1] = zelle2;
  }

  /**
   * Return the opposite cell along the link.
   */
  public Cell getOppositeCell(Cell zelle) {
    if (cells[0] == zelle) {
      return cells[1];
    } else {
      return cells[0];
    }
  }

  /**
   * Set the cell a one link
   */
  public void setZelle(Cell zelle) {
    if (cells[0] == null) {
      cells[0] = zelle;
    } else if (cells[1] == null) {
      cells[1] = zelle;
    } else {
      throw new IllegalArgumentException("Link hat bereits zwei Zellen!");
    }
  }

  // +++ GETTER/SETTER ++++++++++++++

  public void setIsWall(boolean hatWand) {
    isWall = hatWand;
  }

  public boolean isWall() {
    return isWall;
  }

  // +++ JSON ++++++++++++++++++++++++++

  /**
   * JSON constant identifier.
   */
  private static final String IST_WAND = "istWand";

  /**
   * Create JSON objekt from link information.
   */
  public JSONObject toJson(Object metaInformation) {
    JSONObject linkObjekt = new JSONObject();
    linkObjekt.put(IST_WAND, isWall());
    return linkObjekt;
  }

  /**
   * Set link information from JSON object.
   */
  public void fromJson(JSONObject jsonObjekt, Object metaInformation) {
    boolean isWall = (boolean) jsonObjekt.get(IST_WAND);
    setIsWall(isWall);
  }
}

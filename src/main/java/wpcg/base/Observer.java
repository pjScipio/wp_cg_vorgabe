/**
 * Diese Datei ist Teil des Vorgabeframeworks zur Lehrveranstaltung Einführung in die Computergrafik
 * an der Hochschule für Angewandte Wissenschaften Hamburg von Prof. Philipp Jenke (Informatik)
 */

package wpcg.base;

/**
 * Shared interface for all observers.
 */
public interface Observer {

  /**
   * More specific information about what happend.
   *
   * @param sender  This observer sent the message.
   * @param descr   Identifier of the update reason
   * @param payload (Optional) Content of the update event.
   */
  void update(Observable sender, String descr, Object payload);
}

package game.apiintegration;

/**
 * Defines a contract for generating monologues.
 * @author Jeffrey Lam PO Yuen (35306750)
 * @version 1.1.0
 * @since 1.1.0
 */
public interface MonologueSource {
    String generateMonologue(String topic);
}

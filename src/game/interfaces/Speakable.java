package game.interfaces;

public interface Speakable {
    /**
     * Provides the speaking capability of the entity.
     *
     * @return A string representing what the entity says.
     */
    String speak();

    /**
     * Gets the name of the speakable entity.
     *
     * @return A string representing the name of the entity.
     */
    String getName();
}

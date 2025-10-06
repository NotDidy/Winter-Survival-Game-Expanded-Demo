package game.coatings;

public interface Coatable {
    /**
     * Apply a new coating to this item. Replaces any existing coating.
     * @param coating the coating to apply
     */
    void applyCoating(Coating coating);

    /**
     * Remove the current coating, if any.
     */
    void removeCoating();

    /**
     * Get the currently applied coating, if any.
     * @return the coating applied, or null if uncoated
     */
    Coating getCoating();

    /**
     * Check whether this item currently has a coating applied.
     * @return true if coated, false otherwise
     */
    boolean hasCoating();
}

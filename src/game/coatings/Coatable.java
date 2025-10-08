package game.coatings;

public interface Coatable {
    /**
     * Apply a new coating to this item. Replaces any existing coating.
     *
     * @param coating the coating to apply
     */
    void applyCoating(Coating coating);

}

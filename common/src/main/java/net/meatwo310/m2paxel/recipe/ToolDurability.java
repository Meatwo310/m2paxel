package net.meatwo310.m2paxel.recipe;

/** Durability arithmetic shared by the modern recipe implementations. */
public final class ToolDurability {
    private ToolDurability() {}

    public static int combinedDamage(int maximum, long remaining) {
        return (int) Math.max(0L, maximum - Math.max(0L, remaining));
    }

    public static int scaledDamage(int damage, int oldMaximum, int newMaximum) {
        if (oldMaximum <= 0 || newMaximum <= 0) {
            return 0;
        }
        // Round consumption up, so conversion cannot be used as a repair recipe.
        long scaled = ((long) damage * newMaximum + oldMaximum - 1) / oldMaximum;
        return (int) Math.max(0L, Math.min(newMaximum - 1L, scaled));
    }
}

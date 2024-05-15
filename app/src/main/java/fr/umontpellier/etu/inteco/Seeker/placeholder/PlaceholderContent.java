package fr.umontpellier.etu.inteco.Seeker.placeholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<Offer> ITEMS = new ArrayList<Offer>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, Offer> ITEM_MAP = new HashMap<String, Offer>();

    private static final int COUNT = 10;

    private static final String TAG = "debug PlaceholderContent";

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(Offer item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Offer createPlaceholderItem(int position) {
        /**
         * TODO : db request for all offers
         * - db request
         * - add attributes
         * - add xml tag in layout in preparation ...
         * - give the tag its value
         */
        return new Offer(String.valueOf(position), "Item " + position, makeDetails(position),"","","");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
//        }
        return builder.toString();
    }

    /**
     * A placeholder item representing a piece of content.
     */

}
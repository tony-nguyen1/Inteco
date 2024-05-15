package fr.umontpellier.etu.inteco.Seeker.placeholder;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();

    private static final int COUNT = 10;

    private static final String TAG = "debug PlaceholderContent";

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static PlaceholderItem createPlaceholderItem(int position) {
        /**
         * TODO : db request for all offers
         * - db request
         * - add attributes
         * - add xml tag in layout in preparation ...
         * - give the tag its value
         */
        return new PlaceholderItem(String.valueOf(position), "Item " + position, makeDetails(position),"","","");
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
    public static class PlaceholderItem {
        public final String id;
        public final String content;
        public final String details;
        public final String place;
        public final String postDate;
        public final String salary;

        /**
         *
         * @param id
         * @param content == jobTitle
         * @param details == companyName
         */
        public PlaceholderItem(String id, String content, String details, String place, String postDate, String salary) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.place = place;
            this.postDate = postDate;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "PlaceholderItem{" +
                    "content='" + content + '\'' +
                    ", details='" + details + '\'' +
                    ", place='" + place + '\'' +
                    ", postDate='" + postDate + '\'' +
                    ", salary='" + salary + '\'' +
                    '}';
        }
    }
}
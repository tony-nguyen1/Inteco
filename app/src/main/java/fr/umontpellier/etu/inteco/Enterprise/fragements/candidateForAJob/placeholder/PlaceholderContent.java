package fr.umontpellier.etu.inteco.Enterprise.fragements.candidateForAJob.placeholder;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        public final DocumentReference docRef;
        public final String name;
        public final String timestamp;
        public final String status;

        public PlaceholderItem(DocumentReference docRef, String name, Timestamp timestamp, String status) {
            this.docRef = docRef;
            this.name = name;
            this.timestamp = new PrettyTime(new Locale("en")).format(timestamp.toDate());
            this.status = status;
        }

        @Override
        public String toString() {
            return "PlaceholderItem{" +
                    "name='" + name + '\'' +
                    ", timestamp=" + timestamp +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}
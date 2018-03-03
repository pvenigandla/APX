package hackfsu.apx.utils;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hackfsu.apx.model.FirebaseModel;

/**
 * Created by pranathi on 3/3/18.
 */

public class FirebaseUtils {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static void addChildListener (String ref, ChildEventListener listener) {
        get(ref).addChildEventListener(listener);
    }

    public static void addValueListener (String ref, ValueEventListener listener) {
        get(ref).addValueEventListener(listener);
    }

    public static DatabaseReference get (String ref) {
        return database.getReference(ref);
    }

    public static void getSingleValue (String ref, ValueEventListener listener) {
        get(ref).addListenerForSingleValueEvent(listener);
    }

    public static <T extends FirebaseModel> void saveToFirebase (String s, final T t) {
        saveToFirebase(s, t, null);
    }

    public static <T extends FirebaseModel> void saveToFirebase (String s, final T t, final CallbackWithType<T> callback) {
        DatabaseReference announcementRef = get(s);
        DatabaseReference announcementRefWithId;
        if (StringUtils.isEmptyOrNull(t.getFirebaseKey())) {
            announcementRefWithId = announcementRef.push();
            t.setFirebaseKey(announcementRefWithId.getKey());
        }
        else { announcementRefWithId = announcementRef.child(t.getFirebaseKey()); }
        announcementRefWithId.setValue(t, new DatabaseReference.CompletionListener() {
            @Override public void onComplete (DatabaseError databaseError, DatabaseReference databaseReference) {
                if (callback != null) {
                    if (databaseError == null) { callback.onComplete(t); }
                    else { callback.onError(); }
                }
            }
        });
    } }
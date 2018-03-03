package hackfsu.apx.listener;

/**
 * Created by pranathi on 3/3/18.
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


import hackfsu.apx.model.FirebaseModel;


public abstract class SimpleListValueListener <T extends FirebaseModel> implements ValueEventListener {
    private Class<T> tClass;

    public SimpleListValueListener (Class<T> tClass) {
        this.tClass = tClass;
    }

    public abstract void onValueRetrieved (ArrayList<T> ts);

    private static <T extends FirebaseModel> ArrayList<T> getFirebaseObjects (DataSnapshot dataSnapshot, Class<T> tClass) {
        ArrayList<T> ts = new ArrayList<>();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            T t = child.getValue(tClass);
            t.setFirebaseKey(child.getKey());
            ts.add(t);
        }
        return ts;
    }

    @Override public void onDataChange (DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) { onValueRetrieved(getFirebaseObjects(dataSnapshot, tClass)); }
    }

    @Override public void onCancelled (DatabaseError databaseError) {

    }
}

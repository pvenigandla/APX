package hackfsu.apx.model;
import com.google.firebase.database.Exclude;
/**
 * Created by pranathi on 3/3/18.
 */

public abstract class FirebaseModel {
    private String firebaseKey;

    public abstract void saveToFirebase ();

    @Exclude
    public String getFirebaseKey () {
        return firebaseKey;
    }

    public void setFirebaseKey (String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }
}

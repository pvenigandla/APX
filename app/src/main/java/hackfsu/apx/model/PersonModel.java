package hackfsu.apx.model;

import android.support.annotation.NonNull;

import hackfsu.apx.utils.FirebaseUtils;

/**
 * Created by pranathi on 3/3/18.
 */

public class PersonModel extends  FirebaseModel implements Comparable<PersonModel> {
public static final String FIREBASE_NAME = "persons";

private String email;
private String name;


public PersonModel (String name, String email) {
        this.name = name;
        this.email = email;
        }


@Override public void saveToFirebase () {
        FirebaseUtils.saveToFirebase(FIREBASE_NAME, this);
        }



public String getEmail () {
        return email;
        }

public String getName() {
        return name;
        }


public void setEmail(String email) {
        this.email = email;
        }




public void setName(String name) {
        this.name = name;
        }

@Override public int compareTo (@NonNull PersonModel otherPersonModel) {
        return getName().compareTo(otherPersonModel.getName());
        }
}

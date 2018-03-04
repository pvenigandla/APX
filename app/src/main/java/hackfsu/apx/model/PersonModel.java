package hackfsu.apx.model;

import android.support.annotation.NonNull;

import hackfsu.apx.utils.FirebaseUtils;

/**
 * Created by pranathi on 3/3/18.
 */

public class PersonModel extends  FirebaseModel implements Comparable<PersonModel> {
public static final String FIREBASE_NAME = "account";

private String email;
private String name;
private String height;
private String weight;

public PersonModel (String name, String email, String height, String weight) {
        this.name = name;
        this.email = email;
    this.height = height;
    this.weight = weight;
        }
    public PersonModel () {
    }

@Override public void saveToFirebase () {
        FirebaseUtils.saveToFirebase(FIREBASE_NAME, this);
        }



public String getEmail () {
        return email;
        }

    public void setEmail(String email) {
        this.email = email;
    }

public String getName() {
        return name;
        }
    public void setName(String name) {
        this.name = name;
    }
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.name = weight;
    }


    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {this.name = height;}

@Override public int compareTo (@NonNull PersonModel otherPersonModel) {
        return getName().compareTo(otherPersonModel.getName());
        }
}

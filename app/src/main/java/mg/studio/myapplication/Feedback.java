package mg.studio.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Feedback class is used to represent feedback information.
 * It implements the Parcelable interface to allow the object to be passed between activities.
 */
class Feedback implements Parcelable {

    private String name;
    private String error_message;
    public final int SUCCESS = 1;
    public final int FAIL = 0;

    /**
     * Constructor for Feedback class.
     */
    Feedback() {
    }

    /**
     * Constructor for Feedback class that creates a Feedback object from a Parcel.
     * @param in Parcel from which to create the Feedback object.
     */
    protected Feedback(Parcel in) {
        name = in.readString();
        error_message = in.readString();
    }

    /**
     * Writes the Feedback object to a Parcel.
     * @param dest Parcel to which the Feedback object will be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(error_message);
    }

    /**
     * Describes the kinds of special objects contained in this Parcelable instance's marshaled representation.
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Creator for Feedback class.
     */
    public static final Parcelable.Creator<Feedback> CREATOR = new Parcelable.Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel in) {
            return new Feedback(in);
        }

        @Override
        public Feedback[] newArray(int size) {
            return new Feedback[size];
        }
    };

    /**
     * Getter for the name of the Feedback object.
     * @return Name of the Feedback object.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the error message of the Feedback object.
     * @return Error message of the Feedback object.
     */
    public String getError_message() {
        return error_message;
    }

    /**
     * Setter for the name of the Feedback object.
     * @param name Name of the Feedback object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the error message of the Feedback object.
     * @param error_message Error message of the Feedback object.
     */
    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

}

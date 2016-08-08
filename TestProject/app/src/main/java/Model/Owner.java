package Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by girijeshkumar on 01/07/16.
 */
public class Owner implements Parcelable {

    @SerializedName("profile_image")
    private String profileImage;

    protected Owner(Parcel in) {
        profileImage = in.readString();
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

    public void setProfileImage(String profileImage){

        this.profileImage = profileImage;
    }
    public String getProfileImage(){
        return this.profileImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profileImage);
    }
}

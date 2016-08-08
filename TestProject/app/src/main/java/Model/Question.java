package Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by girijeshkumar on 30/06/16.
 */
public class Question implements Parcelable {

    String title;
    String link;
    private Owner owner;

    protected Question(Parcel in) {
        title = in.readString();
        link = in.readString();
        owner = in.readParcelable(Owner.class.getClassLoader());
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setOwner(Owner owner){
        this.owner = owner;
    }
    public Owner getOwner(){
        return this.owner;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeParcelable(owner, flags);
    }
}
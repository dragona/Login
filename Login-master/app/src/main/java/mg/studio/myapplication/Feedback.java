package mg.studio.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */



class Feedback implements Parcelable{

    private String name;
    private String error_message;
    public final int SUCCESS = 1;
    public final int FAIL = 0;

    Feedback(){ }

    protected Feedback(Parcel in) {
        name = in.readString();
        error_message = in.readString();
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(error_message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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



    //Getters and Setters
    public String getName() {
        return name;
    }

    public String getError_message() {
        return error_message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

}

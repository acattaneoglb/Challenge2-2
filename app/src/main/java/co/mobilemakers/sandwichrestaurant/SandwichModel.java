package co.mobilemakers.sandwichrestaurant;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.EnumSet;

/**
 * Model for the sandwiches
 *
 * Created by ariel.cattaneo on 27/01/2015.
 */
public class SandwichModel implements Parcelable {
    public final static String QUANTITY_KEY = "QUANTITY";

    public static enum BreadEnum {
        WHEAT, WHITE, RYE
    }

    public static enum ToppingEnum {
        TOMATO, LATTUCE, ONION, CARROT, OLIVES, SESAME, HAM, CHEESE
    }

    protected BreadEnum breadType;

    public EnumSet<ToppingEnum> getToppingList() {
        return toppingList;
    }

    protected EnumSet<ToppingEnum> toppingList = EnumSet.noneOf(ToppingEnum.class);

    public static final Creator<SandwichModel> CREATOR = new Creator<SandwichModel>() {
        @Override
        public SandwichModel createFromParcel(Parcel source) {
            return new SandwichModel(source);
        }

        @Override
        public SandwichModel[] newArray(int size) {
            return new SandwichModel[size];
        }
    };

    SandwichModel() {

    }

    SandwichModel(Parcel source) {
        breadType = BreadEnum.valueOf(source.readString());
        toppingList = (EnumSet<ToppingEnum>)source.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(breadType.name());
        dest.writeSerializable(toppingList);
    }

    public BreadEnum getBreadType() {
        return breadType;
    }

    public void setBreadType(BreadEnum breadType) {
        this.breadType = breadType;
    }

    public void addTopping(ToppingEnum toppingType) {
        toppingList.add(toppingType);
    }

}

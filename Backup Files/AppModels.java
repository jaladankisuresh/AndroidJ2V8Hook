package com.imnotout.androidj2v8hook.Models;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.google.gson.annotations.SerializedName;
import com.imnotout.androidj2v8hook.BR;

import java.util.List;

public class AppModels {

    public static class Launcher {
        @SerializedName("label")
        private String displayLabel;

        public Launcher(String displayLabel) {
            this.displayLabel = displayLabel;
        }

        public String getDisplayLabel() {
            return displayLabel;
        }
    }

    public static class Registry
            implements Observable {

        private transient PropertyChangeRegistry mCallbacks;

        private String name;
        private String description;
        private List<Establishment> establishments;
        private int count;
        @Bindable
        public String getName() {
            return name;
        }
        @Bindable
        public String getDescription() {
            return description;
        }
        @Bindable
        public List<Establishment> getEstablishments() {
            return establishments;
        }
        @Bindable
        public int getCount() {
            return count;
        }

        public void setName(String name) {
            this.name = name;
            notifyPropertyChanged(BR.name);
        }

        public void setDescription(String description) {
            this.description = description;
            notifyPropertyChanged(BR.description);
        }

        public void setEstablishments(List<Establishment> establishments) {
            this.establishments = establishments;
            notifyPropertyChanged(BR.establishments);
        }

        public void setCount(int count) {
            this.count = count;
            notifyPropertyChanged(BR.count);
        }

        @Override
        public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
            mCallbacks.add(callback);
        }
        @Override
        public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
            if (mCallbacks != null) {
                mCallbacks.remove(callback);
            }
        }
        /**
         * Notifies listeners that all properties of this instance have changed.
         */
        public synchronized void notifyChange() {
            if (mCallbacks != null) {
                mCallbacks.notifyCallbacks(this, 0, null);
            }
        }
        /**
         * Notifies listeners that a specific property has changed. The getter for the property
         * that changes should be marked with {@link Bindable} to generate a field in
         * <code>BR</code> to be used as <code>fieldId</code>.
         *
         * @param fieldId The generated BR id for the Bindable field.
         */
        public void notifyPropertyChanged(int fieldId) {
            if (mCallbacks != null) {
                mCallbacks.notifyCallbacks(this, fieldId, null);
            }
        }
    }

    public static class Establishment extends BaseObservable {

        private String name;
        private String type;
        private String location;
        private int rating;

        private List<String> comments;

        @Bindable
        public String getName() {
            return name;
        }

        @Bindable
        public String getType() {
            return type;
        }

        @Bindable
        public String getLocation() {
            return location;
        }

        @Bindable
        public int getRating() {
            return rating;
        }

        @Bindable
        public List<String> getComments() {
            return comments;
        }

        public void setName(String name) {
            this.name = name;
            notifyPropertyChanged(BR.name);
        }

        public void setType(String type) {
            this.type = type;
            notifyPropertyChanged(BR.type);
        }

        public void setLocation(String location) {
            this.location = location;
            notifyPropertyChanged(BR.location);
        }

        public void setRating(int rating) {
            this.rating = rating;
            notifyPropertyChanged(BR.rating);
        }

        public void setComments(List<String> comments) {
            this.comments = comments;
            notifyPropertyChanged(BR.comments);
        }

    }

    public enum EstablishmentType {
        HOTEL ("Hotel"),
        RESTAURANT("Restaurant"),
        THEATRE("Theatre");

        public final String value;
        public String getValue() { return value; }
        EstablishmentType(String value) { this.value = value; }
    }

    public static class Hotel extends Establishment {

        private int stars;
        private int roomsCount;
        private boolean isAttachedRestaurant;

        @Bindable
        public int getStars() {
            return stars;
        }

        @Bindable
        public int getRoomsCount() {
            return roomsCount;
        }

        @Bindable
        public boolean isAttachedRestaurant() {
            return isAttachedRestaurant;
        }

        public void setStars(int stars) {
            this.stars = stars;
            notifyPropertyChanged(BR.stars);
        }

        public void setRoomsCount(int roomsCount) {
            this.roomsCount = roomsCount;
            notifyPropertyChanged(BR.roomsCount);
        }

        public void setAttachedRestaurant(boolean attachedRestaurant) {
            isAttachedRestaurant = attachedRestaurant;
            notifyPropertyChanged(BR.attachedRestaurant);
        }
    }

    public static class Theatre extends Establishment {

        private int screensCount;
        private int seatingCapacity;
        private int showsPerScreen;
        @Bindable
        public int getScreensCount() {
            return screensCount;
        }

        @Bindable
        public int getSeatingCapacity() {
            return seatingCapacity;
        }

        @Bindable
        public int getShowsPerScreen() {
            return showsPerScreen;
        }

        public void setScreensCount(int screensCount) {
            this.screensCount = screensCount;
            notifyPropertyChanged(BR.screensCount);
        }

        public void setSeatingCapacity(int seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
            notifyPropertyChanged(BR.seatingCapacity);
        }

        public void setShowsPerScreen(int showsPerScreen) {
            this.showsPerScreen = showsPerScreen;
            notifyPropertyChanged(BR.showsPerScreen);
        }

    }

    public static class Restaurant extends Establishment {

        private String cuisineType;
        private int chefsCount;
        private int seatingCapacity;

        @Bindable
        public String getCuisineType() {
            return cuisineType;
        }

        @Bindable
        public int getChefsCount() {
            return chefsCount;
        }

        @Bindable
        public int getSeatingCapacity() {
            return seatingCapacity;
        }

        public void setCuisineType(String cuisineType) {
            this.cuisineType = cuisineType;
            notifyPropertyChanged(BR.cuisineType);
        }

        public void setChefsCount(int chefsCount) {
            this.chefsCount = chefsCount;
            notifyPropertyChanged(BR.chefsCount);
        }

        public void setSeatingCapacity(int seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
            notifyPropertyChanged(BR.seatingCapacity);
        }

    }

}

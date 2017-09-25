package com.imnotout.androidj2v8hook.Models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.imnotout.androidj2v8hook.BR;
import com.imnotout.androidj2v8hook.DataParsers.EstablishmentModelizer;

import java.util.ArrayList;
import java.util.List;

public class AppModels {

    public static class Launcher
            extends BaseObservable implements AppBaseModels.ILauncher {
        private AppBaseModels.LauncherBase launcher;

        public Launcher(AppBaseModels.LauncherBase launcher) {
            this.launcher = launcher;
        }

        @Bindable
        @Override
        public String getDisplayLabel() {
            return launcher.displayLabel;
        }

        @Override
        public void setDisplayLabel(String displayLabel) {
            launcher.setDisplayLabel(displayLabel);
            notifyPropertyChanged(BR.displayLabel);
        }
    }

    public static class Registry
            extends BaseObservable implements AppBaseModels.IRegistry {

        private AppBaseModels.RegistryBase registry;
        private List<Establishment> establishments;

        public Registry(AppBaseModels.RegistryBase registry) {
            this.registry = registry;

            this.establishments = new ArrayList<>();
            EstablishmentModelizer establishmentModeler = EstablishmentModelizer.getInstance();
            for (AppBaseModels.EstablishmentBase establishment : registry.getEstablishments()) {
                establishments.add(establishmentModeler.modelize(establishment));
            }
        }

        @Bindable
        @Override
        public String getName() {
            return registry.getName();
        }

        @Bindable
        @Override
        public String getDescription() {
            return registry.getDescription();
        }

        @Bindable
        @Override
        public List<Establishment> getEstablishments() {
            return this.establishments;
        }

        @Bindable
        @Override
        public int getCount() {
            return registry.getCount();
        }

        @Override
        public void setName(String name) {
            registry.setName(name);
            notifyPropertyChanged(BR.name);
        }

        @Override
        public void setDescription(String description) {
            registry.setDescription(description);
            notifyPropertyChanged(BR.description);
        }

        @Override
        public void setEstablishments(List establishments) {
            registry.setEstablishments(establishments);
            notifyPropertyChanged(BR.establishments);
        }

        @Override
        public void setCount(int count) {
            registry.setCount(count);
            notifyPropertyChanged(BR.count);
        }
    }

    public static class Establishment
            extends BaseObservable implements AppBaseModels.IEstablishment {

        private AppBaseModels.EstablishmentBase establishment;
        private List<Comment> comments;

        public Establishment(AppBaseModels.EstablishmentBase establishment) {
            this.establishment = establishment;


            this.comments = new ArrayList<>();
            for (AppBaseModels.CommentBase commentBase : establishment.getComments()) {
                comments.add(new Comment(commentBase));
            }
        }

        @Bindable
        @Override
        public String getName() {
            return establishment.getName();
        }

        @Bindable
        @Override
        public String getType() {
            return establishment.getType();
        }

        @Bindable
        @Override
        public String getLocation() {
            return establishment.getLocation();
        }

        @Bindable
        @Override
        public int getRating() {
            return establishment.getRating();
        }

        @Bindable
        @Override
        public List getComments() {
            return this.comments;
        }

        public void setName(String name) {
            establishment.setName(name);
            notifyPropertyChanged(BR.name);
        }

        public void setType(String type) {
            establishment.setType(type);
            notifyPropertyChanged(BR.type);
        }

        public void setLocation(String location) {
            establishment.setLocation(location);
            notifyPropertyChanged(BR.location);
        }

        public void setRating(int rating) {
            establishment.setRating(rating);
            notifyPropertyChanged(BR.rating);
        }

        public void setComments(List comments) {
            this.comments = comments;
            notifyPropertyChanged(BR.comments);
        }

    }

    public static class Comment
            extends BaseObservable implements AppBaseModels.IComment {
        private AppBaseModels.CommentBase comment;

        public Comment(AppBaseModels.CommentBase comment) {
            this.comment = comment;
        }

        @Bindable
        @Override
        public String getId() {
            return comment.getId();
        }
        @Bindable
        @Override
        public String getText() {
            return comment.getText();
        }

        @Override
        public void setId(String id) {
            comment.setId(id);
        }
        @Override
        public void setText(String text) {
            comment.setText(text);
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

    public static class Hotel
            extends Establishment implements AppBaseModels.IHotel {

        private AppBaseModels.HotelBase hotel;

        public Hotel(AppBaseModels.HotelBase hotel) {
            super(hotel);
            this.hotel = hotel;
        }

        @Bindable
        @Override
        public int getStars() {
            return hotel.getStars();
        }

        @Bindable
        @Override
        public int getRoomsCount() {
            return hotel.getRoomsCount();
        }

        @Bindable
        @Override
        public boolean isAttachedRestaurant() {
            return hotel.isAttachedRestaurant();
        }

        @Override
        public void setStars(int stars) {
            hotel.setStars(stars);
            notifyPropertyChanged(BR.stars);
        }

        @Override
        public void setRoomsCount(int roomsCount) {
            hotel.setRoomsCount(roomsCount);
            notifyPropertyChanged(BR.roomsCount);
        }

        @Override
        public void setAttachedRestaurant(boolean attachedRestaurant) {
            hotel.setAttachedRestaurant(attachedRestaurant);
            notifyPropertyChanged(BR.attachedRestaurant);
        }
    }

    public static class Theatre
            extends Establishment implements AppBaseModels.ITheatre {

        private AppBaseModels.TheatreBase theatre;

        public Theatre(AppBaseModels.TheatreBase theatre) {
            super(theatre);
            this.theatre = theatre;
        }

        @Bindable
        @Override
        public int getScreensCount() {
            return theatre.getScreensCount();
        }

        @Bindable
        @Override
        public int getSeatingCapacity() {
            return theatre.getSeatingCapacity();
        }

        @Bindable
        @Override
        public int getShowsPerScreen() {
            return theatre.getShowsPerScreen();
        }

        @Bindable
        @Override
        public void setScreensCount(int screensCount) {
            theatre.setScreensCount(screensCount);
            notifyPropertyChanged(BR.screensCount);
        }

        @Override
        public void setSeatingCapacity(int seatingCapacity) {
            theatre.setSeatingCapacity(seatingCapacity);
            notifyPropertyChanged(BR.seatingCapacity);
        }

        @Override
        public void setShowsPerScreen(int showsPerScreen) {
            theatre.setShowsPerScreen(showsPerScreen);
            notifyPropertyChanged(BR.showsPerScreen);
        }
    }

    public static class Restaurant
            extends Establishment implements AppBaseModels.IRestaurant{

        private AppBaseModels.RestaurantBase restaurant;

        public Restaurant(AppBaseModels.RestaurantBase restaurant) {
            super(restaurant);
            this.restaurant = restaurant;
        }

        @Bindable
        @Override
        public String getCuisineType() {
            return restaurant.getCuisineType();
        }

        @Bindable
        @Override
        public int getChefsCount() {
            return restaurant.getChefsCount();
        }

        @Bindable
        @Override
        public int getSeatingCapacity() {
            return restaurant.getSeatingCapacity();
        }

        @Override
        public void setCuisineType(String cuisineType) {
            restaurant.setCuisineType(cuisineType);
            notifyPropertyChanged(BR.cuisineType);
        }

        @Override
        public void setChefsCount(int chefsCount) {
            restaurant.setChefsCount(chefsCount);
            notifyPropertyChanged(BR.chefsCount);
        }

        @Override
        public void setSeatingCapacity(int seatingCapacity) {
            restaurant.setSeatingCapacity(seatingCapacity);
            notifyPropertyChanged(BR.seatingCapacity);
        }

    }

}

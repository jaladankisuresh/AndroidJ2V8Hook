package com.imnotout.androidj2v8hook.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

public class AppBaseModels {

    public interface IParsable {
        String getPath();
    }

    public interface ILauncher {
        public String getDisplayLabel();
        public void setDisplayLabel(String displayLabel);
    }
    public static class LauncherBase
        implements ILauncher {

        private String path;
        @SerializedName("label")
        protected String displayLabel;

        public LauncherBase(String displayLabel) {
            this.displayLabel = displayLabel;
        }

        @Override
        public String getDisplayLabel() {
            return displayLabel;
        }

        @Override
        public void setDisplayLabel(String displayLabel) {
            this.displayLabel = displayLabel;
        }
    }

    public interface IRegistry {
        public String getName();
        public String getDescription();
        public List getEstablishments();
        public int getCount();

        public void setName(String name);
        public void setDescription(String description);
        public void setEstablishments(List establishments);
        public void setCount(int count);
    }

    public static class RegistryBase
        implements IRegistry, IParsable {

        private String path;
        private String name;
        private String description;
        private List<EstablishmentBase> establishments;
        private int count;

        @Override
        public String getPath() {
            return path;
        }
        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public List<EstablishmentBase> getEstablishments() {
            return establishments;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public void setEstablishments(List establishments) {
            this.establishments = establishments;
        }

        @Override
        public void setCount(int count) {
            this.count = count;
        }

    }

    public interface IEstablishment {
        public String getName();
        public String getType();
        public String getLocation();
        public int getRating();
        public List getComments();

        public void setName(String name);
        public void setType(String type);
        public void setLocation(String location);
        public void setRating(int rating);
        public void setComments(List comments);
    }
    public static class EstablishmentBase
            implements IEstablishment, IParsable {

        private String path;
        private String name;
        private String type;
        private String location;
        private int rating;
        private List<CommentBase> comments;

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public String getLocation() {
            return location;
        }

        @Override
        public int getRating() {
            return rating;
        }

        @Override
        public List<CommentBase> getComments() {
            return comments;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public void setType(String type) {
            this.type = type;
        }
        @Override
        public void setLocation(String location) {
            this.location = location;
        }
        @Override
        public void setRating(int rating) {
            this.rating = rating;
        }
        @Override
        public void setComments(List comments) {
            this.comments = comments;
        }
    }

    public interface IComment {
        public String getId();
        public String getText();

        public void setId(String id);
        public void setText(String text);
    }

    public static class CommentBase
            implements IComment {

        private String path;
        private String id;
        private String text;

        public CommentBase(String newComment) {
            this.id = UUID.randomUUID().toString();;
            this.text = newComment;
        }

        @Override
        public String getId() {
            return id;
        }
        @Override
        public String getText() {
            return text;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }
        @Override
        public void setText(String text) {
            this.text = text;
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

    public interface IHotel {
        public int getStars();
        public int getRoomsCount();
        public boolean isAttachedRestaurant();

        public void setStars(int stars);
        public void setRoomsCount(int roomsCount);
        public void setAttachedRestaurant(boolean attachedRestaurant);
    }

    public static class HotelBase
            extends EstablishmentBase implements IHotel {

        private int stars;
        private int roomsCount;

        protected boolean isAttachedRestaurant;

        @Override
        public int getStars() {
            return stars;
        }

        @Override
        public int getRoomsCount() {
            return roomsCount;
        }

        @Override
        public boolean isAttachedRestaurant() {
            return isAttachedRestaurant;
        }

        @Override
        public void setStars(int stars) {
            this.stars = stars;
        }

        @Override
        public void setRoomsCount(int roomsCount) {
            this.roomsCount = roomsCount;
        }

        @Override
        public void setAttachedRestaurant(boolean attachedRestaurant) {
            isAttachedRestaurant = attachedRestaurant;
        }
    }

    public interface ITheatre {

        public int getScreensCount();
        public int getSeatingCapacity();
        public int getShowsPerScreen();

        public void setScreensCount(int screensCount);
        public void setSeatingCapacity(int seatingCapacity);
        public void setShowsPerScreen(int showsPerScreen);
    }

    public static class TheatreBase
            extends EstablishmentBase implements ITheatre {

        protected int screensCount;
        protected int seatingCapacity;
        protected int showsPerScreen;

        @Override
        public int getScreensCount() {
            return screensCount;
        }

        @Override
        public int getSeatingCapacity() {
            return seatingCapacity;
        }

        @Override
        public int getShowsPerScreen() {
            return showsPerScreen;
        }

        @Override
        public void setScreensCount(int screensCount) {
            this.screensCount = screensCount;
        }

        @Override
        public void setSeatingCapacity(int seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
        }

        @Override
        public void setShowsPerScreen(int showsPerScreen) {
            this.showsPerScreen = showsPerScreen;
        }

    }

    public interface IRestaurant {
        public String getCuisineType();

        public int getChefsCount();
        public int getSeatingCapacity();

        public void setCuisineType(String cuisineType);
        public void setChefsCount(int chefsCount);
        public void setSeatingCapacity(int seatingCapacity);
    }

    public static class RestaurantBase
            extends EstablishmentBase implements IRestaurant{

        protected String cuisineType;
        protected int chefsCount;
        protected int seatingCapacity;

        @Override
        public String getCuisineType() {
            return cuisineType;
        }

        @Override
        public int getChefsCount() {
            return chefsCount;
        }

        @Override
        public int getSeatingCapacity() {
            return seatingCapacity;
        }

        @Override
        public void setCuisineType(String cuisineType) {
            this.cuisineType = cuisineType;
        }

        @Override
        public void setChefsCount(int chefsCount) {
            this.chefsCount = chefsCount;
        }

        @Override
        public void setSeatingCapacity(int seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
        }

    }

}

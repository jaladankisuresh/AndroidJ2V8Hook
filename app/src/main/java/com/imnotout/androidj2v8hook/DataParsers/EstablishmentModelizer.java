package com.imnotout.androidj2v8hook.DataParsers;

import com.imnotout.androidj2v8hook.AndroidApplication;
import com.imnotout.androidj2v8hook.Models.AppBaseModels;
import com.imnotout.androidj2v8hook.Models.AppModels;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class EstablishmentModelizer {
    private static EstablishmentModelizer instance;
    private static final String LOG_APP_TAG = AndroidApplication.getAppTag();

    private EstablishmentModelizer() {

    }

    public static EstablishmentModelizer getInstance() {
        if(instance == null) {
            instance = new EstablishmentModelizer();
        }
        return instance;
    }

    public AppModels.Establishment modelize(AppBaseModels.EstablishmentBase establishmentBase) {

        if(establishmentBase.getType().equals(AppBaseModels.EstablishmentType.HOTEL.getValue())) {
            return new AppModels.Hotel((AppBaseModels.HotelBase) establishmentBase);
        }
        else if(establishmentBase.getType().equals(AppBaseModels.EstablishmentType.RESTAURANT.getValue())) {
            return new AppModels.Restaurant((AppBaseModels.RestaurantBase) establishmentBase);
        }
        else if(establishmentBase.getType().equals(AppBaseModels.EstablishmentType.THEATRE.getValue())) {
            return new AppModels.Theatre((AppBaseModels.TheatreBase) establishmentBase);
        }
        else {
            return new AppModels.Establishment(establishmentBase);
        }
    }
}

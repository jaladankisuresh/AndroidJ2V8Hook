package com.imnotout.androidj2v8hook.DataParsers;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.imnotout.androidj2v8hook.AndroidApplication;
import com.imnotout.androidj2v8hook.Models.AppBaseModels.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class EstablishmentDeserializer
        implements JsonDeserializer<EstablishmentBase> {

    private Map<String, Type> mapEstablishmentType;
    private static final String LOG_APP_TAG = AndroidApplication.getAppTag();


    public EstablishmentDeserializer() {

        mapEstablishmentType = new HashMap<>();
        mapEstablishmentType.put(EstablishmentType.HOTEL.getValue(), HotelBase.class);
        mapEstablishmentType.put(EstablishmentType.RESTAURANT.getValue(), RestaurantBase.class);
        mapEstablishmentType.put(EstablishmentType.THEATRE.getValue(), TheatreBase.class);
    }

    @Override
    public EstablishmentBase deserialize(JsonElement jsonElement,
                                         Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject establishmentJsonObj = jsonElement.getAsJsonObject();
        String establishmentTypeValue = establishmentJsonObj.getAsJsonPrimitive("type").getAsString();
        Type establishmentType = mapEstablishmentType.get(establishmentTypeValue);
       try {
           return jsonDeserializationContext.deserialize(establishmentJsonObj, establishmentType);
       }
       catch(Exception err){
           Log.e(LOG_APP_TAG, "Gson err : " + err.toString());
           Log.e(LOG_APP_TAG, "Establishment Json Obj : " + establishmentJsonObj.toString());
           return null;
       }
    }
}

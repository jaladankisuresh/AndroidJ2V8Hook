package com.imnotout.androidj2v8hook.NetworkIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.imnotout.androidj2v8hook.DataParsers.EstablishmentDeserializer;
import com.imnotout.androidj2v8hook.Models.AppBaseModels;

public class GsonInstanceBuilder {
    private static Gson gson;
    public static Gson getGsonInstance() {
        if(gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(AppBaseModels.EstablishmentBase.class, new EstablishmentDeserializer());
            gson = gsonBuilder.create();
        }
        return gson;
    }
    public static class JsonCaster {

        Object dataObject;
        String json;
        public JsonCaster(Object dataObject) throws Exception {
            this.dataObject = dataObject;
            if(dataObject instanceof JsonObject || dataObject instanceof String) {
                json = dataObject.toString();
            }
            else {
                json = GsonInstanceBuilder.getGsonInstance().toJson(dataObject);
            }
        }

        public String getJson() {
            return json;
        }

        public JsonElement getAsJsonElement() {
            return GsonInstanceBuilder.getGsonInstance().fromJson(json, JsonElement.class);
        }
    }


}

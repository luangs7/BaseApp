package br.com.luan2.baseapp.extras;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;


/**
 * Created by Luan on 04/05/17.
 */

public class CustomGsonAdapter {

        public String serialize(List<Object> objects, String arrKey, String objKey) {
            JsonArray ja = new JsonArray();
            for (Object object: objects) {
                Gson gson = new Gson();
                JsonElement je = gson.toJsonTree(object);
                JsonObject jo = new JsonObject();
                jo.add(objKey, je);
                ja.add(jo);
            }

            JsonObject objMain = new JsonObject();
            objMain.add(arrKey,ja);

            return objMain.toString();

    }


    public String serialize(Object object, String objKey) {
            Gson gson = new Gson();
            JsonElement je = gson.toJsonTree(object);
            JsonObject jo = new JsonObject();
            jo.add(objKey, je);


        return jo.toString();

    }
}
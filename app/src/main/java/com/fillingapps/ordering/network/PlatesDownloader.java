package com.fillingapps.ordering.network;

import android.os.AsyncTask;

import com.fillingapps.ordering.model.Allergen;
import com.fillingapps.ordering.model.Ingredient;
import com.fillingapps.ordering.model.Plate;
import com.fillingapps.ordering.model.Plates;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;


// AsyncTask para descargar en segundo plano del menu
public class PlatesDownloader extends AsyncTask<String, Integer, Plates> {

    protected WeakReference<OnPlatesReceivedListener> mOnPlatesReceivedListener;

    public PlatesDownloader(OnPlatesReceivedListener onPlatesReceivedListener) {
        mOnPlatesReceivedListener = new WeakReference<>(onPlatesReceivedListener);
    }

    @Override
    protected Plates doInBackground(String... params) {
        Plates plates = Plates.getInstance();
        URL url;
        InputStream input = null;
        try {
            url = new URL("http://www.mocky.io/v2/56013c1d9635786e150aa3a4");
            HttpURLConnection con = (HttpURLConnection) (url.openConnection());
            con.setConnectTimeout(5000);
            con.connect();
            int responseLength = con.getContentLength();
            byte data[] = new byte[1024];
            long currentBytes  = 0;
            int downloadedBytes;
            input = con.getInputStream();
            StringBuilder sb = new StringBuilder();
            while ((downloadedBytes = input.read(data)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return null;
                }

                sb.append(new String(data, 0, downloadedBytes));

                // Si tuviéramos una longitud de respuesta podríamos incluso actualizar la barra de progreso
                if (responseLength > 0) {
                    currentBytes += downloadedBytes;
                    publishProgress((int) ((currentBytes * 100) / responseLength));
                }
                else {
                    currentBytes++;
                    publishProgress((int)currentBytes * 10);
                }
            }

            JSONObject jsonRoot = new JSONObject(sb.toString());
            JSONArray platesArray = jsonRoot.getJSONArray("plates");

            if (platesArray.length() > 0){

                plates.clearPlates();

                for (int i = 0; i < platesArray.length(); i++) {
                    JSONObject plate = platesArray.getJSONObject(i);

                    int identifier = plate.getInt("identifier");
                    String name = plate.getString("name");
                    String type = plate.getString("type");
                    String image = plate.getString("image");
                    String description = plate.getString("description");
                    float price = (float)plate.getDouble("price");

                    JSONArray allergensArray = plate.getJSONArray("allergens");
                    LinkedList<Allergen> allergens = new LinkedList<>();
                    for (int j = 0; j < allergensArray.length(); j++) {
                        JSONObject allergen = allergensArray.getJSONObject(j);
                        String aName = allergen.getString("name");
                        String aIcon = allergen.getString("icon");
                        allergens.add(new Allergen(aName, aIcon));
                    }

                    JSONArray ingredientsArray = plate.getJSONArray("ingredients");
                    LinkedList<Ingredient> ingredients = new LinkedList<>();
                    for (int j = 0; j < ingredientsArray.length(); j++) {
                        JSONObject ingredient = ingredientsArray.getJSONObject(j);
                        String iName = ingredient.getString("name");
                        ingredients.add(new Ingredient(iName));
                    }
                    plates.addPlate(new Plate(0, identifier, name, type, image, description, ingredients, allergens, price, ""));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return plates;
    }

    @Override
    protected void onPostExecute(Plates plates) {
        super.onPostExecute(plates);
        if (mOnPlatesReceivedListener != null && mOnPlatesReceivedListener.get() != null) {
            mOnPlatesReceivedListener.get().onPlatesReceivedListener();
        }
    }

    public interface OnPlatesReceivedListener {
        void onPlatesReceivedListener();
    }
}

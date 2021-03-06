package com.jz.api;

/**
 * Created by User on 25/06/2017.
 */

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.jz.language.Ingredient;


public class ApiAnalysis {

    // POST method to API
    public List<Ingredient> Post(JSONObject json)
    {
        StringBuilder sb = new StringBuilder();
        String http = "http://10.0.2.2/api/manalysis"; // When running inside emulator
        //String http = "http://192.168.43.162/api/manalysis"; // When running from the SmartPhone via Tethering
        HttpURLConnection urlConnection=null;
        List<Ingredient> ingredients;

        try {

            // Create JSONObject here
            //JSONObject json = new JSONObject();
            //json.put("Text", "sugar, salt, oat flakes"); // Recognized text will go here
            //jsonParam.put("description", "Real");
            //jsonParam.put("enable", "true");

            URL url = new URL(http);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput (true);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            //urlConnection.setUseCaches(false);
            //urlConnection.setConnectTimeout(10000);
            //urlConnection.setReadTimeout(10000);
            //urlConnection.setRequestProperty("Host", "android.schoolportal.gr");
            urlConnection.connect();


            Log.i("APIAnalysis", "Sending to API: " + json.toString());
            // Send JSON string over POST to the server
            OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream(), "utf-8");
            writer.write(json.toString());
            writer.close();

            // Process server response message
            int HttpResult = urlConnection.getResponseCode();

            Log.i("APIAnalysis", "API response code: " + urlConnection.getResponseCode());

            if(HttpResult == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                //BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));

                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                // Convert JSON string to a List of Ingredient objects
                Log.e("APIAnalysis", sb.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ingredients = Arrays.asList(gson.fromJson(sb.toString(), Ingredient[].class)); // initiaizes and assigns

                return ingredients;

            }else{
                // In case if needed a detailed message from the server
                //return urlConnection.getResponseMessage();

                Log.e("APIAnalysis", "API call did not returned 200");

                // Creates a fake ingredient
                Ingredient FailureIngredient = new Ingredient();
                //FailureIngredient.IngredientName = "HTTP Request to API Failed";
                FailureIngredient.IngredientName = urlConnection.getResponseMessage();
                FailureIngredient.IngredientDescription = "NA";
                FailureIngredient.IngredientDangerLevel = 0;
                FailureIngredient.FuzzyDistance = 0;

                ingredients = Arrays.asList(FailureIngredient); // initiaizes and assigns

                return ingredients;
            }


        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(urlConnection!=null)
                urlConnection.disconnect();
        }

        Log.e("APIAnalysis", "Connection to API is not working!");
        //return " Nothing" via a fake ingredient;
        Ingredient NothingIngredient = new Ingredient();
        NothingIngredient.IngredientName = "Connection to API is not working!";
        NothingIngredient.IngredientDescription = "NA";
        NothingIngredient.IngredientDangerLevel = 0;
        NothingIngredient.FuzzyDistance = 0;

        ingredients = Arrays.asList(NothingIngredient); // initiaizes and assigns
        return ingredients;
    }
}





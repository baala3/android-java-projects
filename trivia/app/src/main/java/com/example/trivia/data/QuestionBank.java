package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.MainActivity;
import com.example.trivia.controller.MySingleton;
import com.example.trivia.model.Questions;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    ArrayList<Questions> questionList=new ArrayList<>();
   private String url=
           "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
   public ArrayList<Questions> getquestions(final getquestionserve callback)
   {
       JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {
               for(int i=0;i<response.length();i++)
               {
                   try {
                       Questions questions=new Questions(response.getJSONArray(i).get(0).toString(),response.getJSONArray(i).get(1).toString());
                      // Log.d("SOME",questions.getQuestion());
                   questionList.add(questions);

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }


               }
               callback.setquestion(questionList);

           }
       },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               });

       MySingleton.getInstance().addToRequestQueue(jsonArrayRequest);
       return questionList;
   }


}

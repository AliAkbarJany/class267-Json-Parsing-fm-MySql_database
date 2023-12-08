package com.rafsan.class267jsonparsingfmmysql_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Spliterator;

public class MainActivity extends AppCompatActivity {


    Button buttonInsertData;
    EditText edName,edMobile,edEmail;
    ProgressBar progressBar;
    TextView textView;
    GridView gridView;

    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonInsertData = findViewById(R.id.buttonInsertData);
        edName = findViewById(R.id.edName);
        edMobile = findViewById(R.id.edMobile);
        edEmail = findViewById(R.id.edEmail);

        gridView = findViewById(R.id.gridView);
        progressBar = findViewById(R.id.progressBar);


// >>>>>>>>>>>>>>>>>>>>>>>>>> Function (CALL)>>>>>>>>>>>>>>>>>>>>>>>>>>>
        loadData();



//===================================================================================
//==================================DATA INSERT=================================================
        buttonInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edName.getText().toString();
                String phone = edMobile.getText().toString();
                String email = edEmail.getText().toString();

                String url = "https://ali71.000webhostapp.com/apps/data.php?n="+name+"&p="+phone+"&e="+email;

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Server response")
                                        .setMessage(response)
                                        .show();

                                // >>>>>>>>>>>>>>>>>>>>>>>>>> Function (CALL)>>>>>>>>>>>>>>>>>>>>>>>>>>>
                                loadData();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                if (name.length()>0){
                    queue.add(stringRequest);
                }
                else {
                    edName.setError("Input Your name");
                }

            }
        });



    }

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CUSTOM ADAPTER>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private  class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.lists,parent,false);

            TextView textName = myView.findViewById(R.id.textName);
            TextView textPhone = myView.findViewById(R.id.textPhone);
            TextView textEmail = myView.findViewById(R.id.textEmail);
            TextView textId = myView.findViewById(R.id.textId);

            Button buttonUpdate = myView.findViewById(R.id.buttonUpdate);
            Button buttonDelete = myView.findViewById(R.id.buttonDelete);

            HashMap<String,String> hashMap1 = arrayList.get(position);

            String name = hashMap1.get("name");
            String phone = hashMap1.get("phone");
            String email = hashMap1.get("email");
            String id = hashMap1.get("id");


            textName.setText(name);
            textPhone.setText(phone);
            textEmail.setText(email);
            textId.setText(id);

            //======================= Update data=======================
            //======================= Update data=======================
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = edName.getText().toString();
                    String phone = edMobile.getText().toString();
                    String email = edEmail.getText().toString();
                    String url = "https://ali71.000webhostapp.com/apps/update.php?id="+id+"&name="+name+"&phone="+phone+"&email="+email;

                    progressBar.setVisibility(View.VISIBLE);

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            progressBar.setVisibility(View.GONE);

                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Server Response")
                                    .setMessage(response)
                                    .show();
// ================================Function Call===================================
                            loadData();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d("updateRes",error.toString());

                        }
                    });

                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(stringRequest);
                }
            });

            //======================= Delete data=======================
            //======================= Delete data=======================
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url = "https://ali71.000webhostapp.com/apps/delete.php?id="+id;

                    progressBar.setVisibility(View.VISIBLE);

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            progressBar.setVisibility(View.GONE);

                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Server Response")
                                    .setMessage(response)
                                    .show();
// ================================Function Call===================================
                            loadData();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d("updateRes",error.toString());

                        }
                    });

                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(stringRequest);
                }
            });


            return myView;
        }
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Function/Method>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>VOllEy Request (Data LOAD / GET Data)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private void loadData(){

        arrayList = new ArrayList<>();


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ali71.000webhostapp.com/apps/getalldata.php";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            for (int x = 0; x<response.length(); x++){

                                JSONObject jsonObject = response.getJSONObject(x);

                                String name = jsonObject.getString("name");
                                String phone = jsonObject.getString("phone");
                                String email = jsonObject.getString("email");
                                String id = jsonObject.getString("id");

                                hashMap = new HashMap<>();
                                hashMap.put("name",name);
                                hashMap.put("phone",phone);
                                hashMap.put("email",email);
                                hashMap.put("id",id);
                                arrayList.add(hashMap);

                            }

                            MyAdapter myAdapter = new MyAdapter();
                            gridView.setAdapter(myAdapter);


                        }

                        catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("serverRes",error.toString());
            }
        }
        );

        queue.add(jsonArrayRequest);


    }
}
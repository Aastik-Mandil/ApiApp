package com.game.apiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<ValCls> lst_data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        new RetrieveFeedTask().execute();
    }

    public class DataAdapter extends BaseAdapter{
        ArrayList<ValCls> val;
        public DataAdapter(ArrayList<ValCls> d){ this.val=d; }
        @Override
        public int getCount() { return val.size(); }

        @Override
        public ValCls getItem(int i) { return val.get(i); }

        @Override
        public long getItemId(int i) { return 0; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.data_lay,parent,false);
            TextView login = view.findViewById(R.id.login);
            TextView id = view.findViewById(R.id.id);
            TextView repository_url = view.findViewById(R.id.repository_url);
            CheckBox state = view.findViewById(R.id.state);

            login.setText(getItem(position).getLogin());
            id.setText(getItem(position).getId());
            repository_url.setText(getItem(position).getRepository_url());
            String st = getItem(position).getState();
            if(st.equals("open")){
                state.isChecked();
            }
            return view;
        }
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.github.com/repositories/19438/issues");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                try{
                    JSONArray arr = new JSONArray(s);
                    for(int i=0;i<arr.length();i++){
                        JSONObject JSONobj = arr.getJSONObject(i);
                        JSONObject ob = JSONobj.getJSONObject("user");
                        String login = ob.getString("login");
                        String id = ob.getString("id");
                        String repository_url = JSONobj.getString("repository_url");
                        String state = JSONobj.getString("state");
                        ValCls val = new ValCls(login,id,repository_url,state);
                        lst_data.add(val);
                    }
                    DataAdapter adapter = new DataAdapter(lst_data);
                    listView.setAdapter(adapter);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
package com.example.absensi;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewSQLActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        JSONObject json = JSONfunctions.getJSONfromURL("http://192.168.43.75/absensi/test.php");
        try{
            JSONArray kelas = json.getJSONArray("kelas");
            for(int i=0;i<kelas.length();i++){
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonobj = kelas.getJSONObject(i);
                map.put("id",  jsonobj.getString("id"));;
                map.put("nama_kelas", jsonobj.getString("nama_kelas"));
                map.put("jumlah_siswa", jsonobj.getString("jumlah_siswa"));
                mylist.add(map);
            }
        }catch(JSONException e)        {
            Log.e("log_tag", "Error parsing data "+e.toString());
        }
        ListAdapter adapter = new SimpleAdapter(this, mylist , R.layout.list_item,
        new String[] { "nama_kelas", "jumlah_siswa" },
        new int[] { R.id.nama_kelas, R.id.jumlah_siswa });
        setListAdapter(adapter);
        final ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                @SuppressWarnings("unchecked")
                HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(position);
                Toast.makeText(ListViewSQLActivity.this, "ID '" + o.get("id") + "' was clicked.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

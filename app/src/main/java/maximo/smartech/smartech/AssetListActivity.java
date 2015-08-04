package maximo.smartech.smartech;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class AssetListActivity extends Activity implements SearchView.OnQueryTextListener {
    private final static String TAG = AssetListActivity.class.getName();
    private ListView listView;
    private JSONArray assets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);
        listView = (ListView) findViewById(R.id.listview_id);
        new RetrieveAssets().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asset_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    // DO nothing with the submit
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ((AssetsAdapter) listView.getAdapter()).getFilter().filter(newText);

        return false;
    }


    private class RetrieveAssets extends AsyncTask<Void, Void, String> {
        String jsonString = null;

        @Override
        protected String doInBackground(Void... params) {


            try {


                URL url = new URL("http://192.168.1.200:9080/maxrest/rest/os/mxasset?_lid=maxadmin&_lpwd=maxadmin&_format=json&_compact=1");
                Log.v(TAG, url.toString());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }

                jsonString = total.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.v(TAG, jsonString);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            try {
                JSONObject result = new JSONObject(jsonString);
                JSONObject QueryMXASSETResponse = result.getJSONObject("QueryMXASSETResponse");
                JSONObject MXASSETSet = QueryMXASSETResponse.getJSONObject("MXASSETSet");

                assets = MXASSETSet.getJSONArray("ASSET");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            listView.setAdapter(new AssetsAdapter(getApplicationContext(), assets));
            ((SearchView) findViewById(R.id.search_view)).setOnQueryTextListener(AssetListActivity.this);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = "";
                        JSONArray specs = ((JSONObject) ((AssetsAdapter) listView.getAdapter()).getData().get(position)).getJSONArray("ASSETSPEC");
                        for (int i = 0; i < specs.length(); ++i) {
                            Iterator<String> iterator = ((JSONObject) (specs.get(i))).keys();
                            while (iterator.hasNext()) {
                                String key = iterator.next();
                                try {
                                    String value = ((JSONObject) specs.get(i)).getString(key);
                                    text += key + ":" + value + ";";
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }


}

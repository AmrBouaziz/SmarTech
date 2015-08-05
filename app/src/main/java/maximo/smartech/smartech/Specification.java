package maximo.smartech.smartech;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import maximo.smartech.smartech.contract.Contract;

public class Specification extends Activity {

    private TextView[] textViews;
    private EditText[] editTexts;
    private RelativeLayout rootRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specification);
        // bind relative view to object
        rootRelativeLayout= (RelativeLayout) findViewById(R.id.relative_layout);
        //get extras from the calling activity (AssetListActivity)
        ArrayList<String> specsArrayList = getIntent().getExtras().getStringArrayList(Contract.EXTRA_SPECS);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_specification, menu);
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
}

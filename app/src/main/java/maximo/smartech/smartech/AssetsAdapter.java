package maximo.smartech.smartech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amrou on 04/08/15.
 */
public class AssetsAdapter extends BaseAdapter implements Filterable {
    private final static String TAG = AssetsAdapter.class.getName();
    Context context;
    JSONArray data;
    private LayoutInflater layoutInflater;
    private Filter filter = null;
    private int viewTypeCount = 2;

    public AssetsAdapter(Context context, JSONArray data) {

        this.context = context;
        this.data = data;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getViewTypeCount() {
        return viewTypeCount;
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return data.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public JSONArray getData(){
        return data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (vi == null)

            vi = layoutInflater.inflate(R.layout.asset_item, null);

        // bind object view with xml
        TextView assetNum = (TextView) vi.findViewById(R.id.asset_num);
        TextView assetDesc = (TextView) vi.findViewById(R.id.asset_desc);
        TextView assetSpecsName = (TextView) vi.findViewById(R.id.asset_spec_name);

        // set Data to view
        try {
            assetNum.setText(((JSONObject) data.get(position)).getString("ASSETNUM"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            assetDesc.setText(((JSONObject) data.get(position)).getString("DESCRIPTION"));
        } catch (JSONException e) {
            assetDesc.setText("");
            e.printStackTrace();
        }


        try {
            assetSpecsName.setText(((JSONObject) data.get(position)).getString("HIERARCHYPATH"));
        } catch (JSONException e) {
            assetSpecsName.setText("");
            e.printStackTrace();
        }

        return vi;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            return filter = new Filter();
        return filter;
    }

    class Filter extends android.widget.Filter {

        JSONArray dataCopy = data;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String assetNum, assetDesc, assetSpecsName;
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                try {
                    JSONArray filterList = new JSONArray();
                    for (int i = 0; i < dataCopy.length(); i++) {

                        try {
                            assetNum = ((JSONObject) dataCopy.get(i)).getString("ASSETNUM");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            assetNum = null;
                        }

                        try {
                            assetDesc = ((JSONObject) dataCopy.get(i)).getString("DESCRIPTION");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            assetDesc = null;
                        }


                        try {
                            assetSpecsName = ((JSONObject) dataCopy.get(i)).getString("HIERARCHYPATH");
                        } catch (JSONException e) {
                            assetSpecsName = null;
                            e.printStackTrace();
                        }
                        if ((assetNum != null && assetNum.toUpperCase().contains(constraint.toString().toUpperCase()))
                                || (assetDesc != null && assetDesc.toUpperCase().contains(constraint.toString().toUpperCase()))
                                || (assetSpecsName != null && assetSpecsName.toUpperCase().contains(constraint.toString().toUpperCase()))) {
                            filterList.put(dataCopy.get(i));
                        }

                    }
                    results.count = filterList.length();
                    results.values = filterList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                results.count = dataCopy.length();
                results.values = dataCopy;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data = (JSONArray) results.values;
            notifyDataSetChanged();
        }
    }
}

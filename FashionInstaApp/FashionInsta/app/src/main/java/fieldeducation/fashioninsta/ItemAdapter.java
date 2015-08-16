package fieldeducation.fashioninsta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ¼öÀÎ on 2015-08-11.
 */
public class ItemAdapter extends ArrayAdapter<ListItemActivity> {

    private ArrayList<ListItemActivity> itemList;
    private Context context;
    private int rowResourceId;

    public ItemAdapter(Context context, int textViewResourceId, ArrayList<ListItemActivity> itemList)
    {
        super(context, textViewResourceId, itemList);

        this.itemList  = itemList;
        this.context = context;
        this.rowResourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(this.rowResourceId, null);
        }

        ListItemActivity item = itemList.get(position);
        if(item != null)
        {
            TextView itemview =(TextView) v.findViewById(R.id.item);
            if(itemview != null)
                itemview.setText(item.getWord());
        }

        return v;
    }


}

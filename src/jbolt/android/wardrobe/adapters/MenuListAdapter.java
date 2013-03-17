package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.meta.MenuItem;

/**
 * <p>Title: MenuListAdapter</p>
 * <p>Description: MenuListAdapter</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class MenuListAdapter extends BaseListAdapter {

    private List<MenuItem> items = new ArrayList<MenuItem>();
    private Context context;

    public MenuListAdapter(Context context) {
        this.context = context;
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int i) {
        return items.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        MenuItem item = (MenuItem) getItem(i);
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.menu_item, null);
            holder = new ViewHolder();
            holder.menuItem = (TextView) convertView.findViewById(R.id.txtItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.menuItem.setText(item.getTxt());
        return convertView;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    class ViewHolder {

        TextView menuItem;
    }
}

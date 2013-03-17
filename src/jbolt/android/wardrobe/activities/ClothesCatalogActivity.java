package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import jbolt.android.R;
import jbolt.android.wardrobe.adapters.ClothesCatalogListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.models.ClothesCatalogModel;

/**
 * <p>Title: ClothesCatalogActivity</p>
 * <p>Description: ClothesCatalogActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ClothesCatalogActivity extends WardrobeFrameActivity {

    private ClothesCatalogListAdapter listAdapter;
    private ListView listView;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.clothescatalog);
        topReturn = (Button) findViewById(R.id.btnTopReturn);
        btnTopHome = (Button) findViewById(R.id.btnTopHome);
        btnTopAdd = (Button) findViewById(R.id.btnMore);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();

        listView = (ListView) findViewById(R.id.lstClothesCatalog);
        listAdapter = new ClothesCatalogListAdapter(this);
        listView.setAdapter(listAdapter);

        initListAdapter();
    }

    private void initListAdapter() {
        List<ClothesCatalogModel> items = new ArrayList<ClothesCatalogModel>();
        ClothesCatalogModel item = new ClothesCatalogModel();
        item.setContent("Content1");
        item.setImgId(R.drawable.pic);
        items.add(item);

        item = new ClothesCatalogModel();
        item.setContent("Content2");
        item.setImgId(R.drawable.pic_2);
        items.add(item);
        listAdapter.setCatalogs(items);
        listAdapter.notifyDataSetChanged();
    }
}

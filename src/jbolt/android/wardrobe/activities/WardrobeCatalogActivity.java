package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import jbolt.android.R;
import jbolt.android.meta.MenuItem;
import jbolt.android.utils.MessageHandler;
import jbolt.android.wardrobe.adapters.CatalogListAdapter;
import jbolt.android.wardrobe.adapters.MenuListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactTypeModel;
import jbolt.android.wardrobe.models.CatalogItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: WardrobeCatalogActivity</p>
 * <p>Description: WardrobeCatalogActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class WardrobeCatalogActivity extends WardrobeFrameActivity {

    private Button btnShow;
    private Button btnMore;
    private ListView lstCatalog;
    private ListView menus;
    private CatalogListAdapter listAdapter;
    private MenuListAdapter menuListAdapter;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.catalog);

        btnMore = (Button) findViewById(R.id.btnMore);
        btnShow = (Button) findViewById(R.id.btnShow);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();
        initBottomButtons();

        lstCatalog = (ListView) findViewById(R.id.lstCatalog);
        listAdapter = new CatalogListAdapter(this);
        lstCatalog.setAdapter(listAdapter);

        List<ArtifactTypeModel> types = DataFactory.getSingle().getTypes();
        List<CatalogItemModel> items = new ArrayList<CatalogItemModel>();
        int i = 0;
        CatalogItemModel catalogItem = new CatalogItemModel();
        for (ArtifactTypeModel type : types) {
            if (i == 0) {
                catalogItem.setType1(type);
                items.add(catalogItem);
                i++;
            } else if (i == 1) {
                catalogItem.setType2(type);
                i++;
            } else if (i == 2) {
                catalogItem.setType3(type);
                catalogItem = new CatalogItemModel();
                i = 0;
            }
        }
        listAdapter.setModels(items);
        listAdapter.notifyDataSetChanged();
        initMenuItems();
    }

    private void initMenuItems() {
        menus = (ListView) findViewById(R.id.menus);
        menus.setVisibility(View.INVISIBLE);
        menuListAdapter = new MenuListAdapter(this);
        menus.setAdapter(menuListAdapter);

        List<MenuItem> items = new ArrayList<MenuItem>();
        MenuItem item = new MenuItem();
        item.setTxt(getString(R.string.catalogMenu_setting));
        items.add(item);

        item = new MenuItem();
        item.setTxt(getString(R.string.catalogMenu_feedback));
        items.add(item);

        item = new MenuItem();
        item.setTxt(getString(R.string.catalogMenu_about));
        items.add(item);

        menuListAdapter.setItems(items);
        menuListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initSpecialTopButtons() {
        btnMore.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    more();
                }
            });

        btnShow.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    show();
                }
            });

        btnTopReturn.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                }
            });
    }

    private void show() {
        MessageHandler.showWarningMessage(this, "Show");
    }

    private void more() {
        if (menus.getVisibility() == View.INVISIBLE) {
            menus.setVisibility(View.VISIBLE);
        } else {
            menus.setVisibility(View.INVISIBLE);
        }
    }
}
package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import jbolt.android.R;
import jbolt.android.meta.MenuItem;
import jbolt.android.utils.StringUtilities;
import jbolt.android.utils.WidgetUtils;
import jbolt.android.wardrobe.adapters.MenuListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItemModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Title: ClothesCatalogAbstractActivity</p>
 * <p>Description: ClothesCatalogAbstractActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public abstract class ClothesCatalogAbstractActivity extends WardrobeFrameActivity {

    private MenuListAdapter leftMenuListAdapter;
    private MenuListAdapter rightMenuListAdapter;
    private ListView leftMenus;
    private ListView rightMenus;
    protected Button btnTopAdd;
    protected String type;
    private ImageView btnMidShow;
    private View latitudeBar;
    private ImageView btnMidHide;
    protected String latitudeValue1;
    protected String latitudeValue2;
    public static boolean hangerView = true;
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    refreshAdapter();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initSpecialTopButtons() {
        btnTopAdd.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    addNew();
                }
            });
    }

    protected void initMenuItems() {
        TextView txtType = (TextView) findViewById(R.id.type);
        txtType.setText(DataFactory.getSingle().findType(type).getResourceId());
        ImageButton btnLatitudeView = (ImageButton) findViewById(R.id.btnLatitudeView);
        btnLatitudeView.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    switchView();
                }
            });
        ImageButton latitude1 = (ImageButton) findViewById(R.id.latitude1);
        latitude1.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    WidgetUtils.setWidgetVisible(rightMenus, false);
                    WidgetUtils.setWidgetVisible(leftMenus, !WidgetUtils.isWidgetVisible(leftMenus));
                    handler.sendMessageDelayed(handler.obtainMessage(1), 30);
                }
            });
        ImageButton latitude2 = (ImageButton) findViewById(R.id.latitude2);
        latitude2.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    WidgetUtils.setWidgetVisible(leftMenus, false);
                    WidgetUtils.setWidgetVisible(rightMenus, !WidgetUtils.isWidgetVisible(rightMenus));
                }
            });

        leftMenus = (ListView) findViewById(R.id.leftMenus);
        leftMenus.setVisibility(View.INVISIBLE);
        leftMenuListAdapter = new MenuListAdapter(this);
        leftMenuListAdapter.setItemOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    latitudeValue1 = ((TextView) view).getText().toString();
                    leftMenus.setVisibility(View.INVISIBLE);
                    rightMenus.setVisibility(View.INVISIBLE);
                    initRightMenu();
                    latitudeValue2 = null;
                    handler.sendMessageDelayed(handler.obtainMessage(1), 30);
                }
            });
        leftMenus.setAdapter(leftMenuListAdapter);

        List<MenuItem> items = new ArrayList<MenuItem>();
        MenuItem item = new MenuItem();
        item.setTxt(getString(R.string.latitude_menu1_left));
        items.add(item);

        item = new MenuItem();
        item.setTxt(getString(R.string.latitude_menu2_left));
        items.add(item);

        leftMenuListAdapter.setLeft(true);
        leftMenuListAdapter.setItems(items);
        leftMenuListAdapter.notifyDataSetChanged();

        latitudeValue1 = getString(R.string.latitude_menu1_left);

        rightMenus = (ListView) findViewById(R.id.rightMenus);
        rightMenus.setVisibility(View.INVISIBLE);
        rightMenuListAdapter = new MenuListAdapter(this);
        rightMenus.setAdapter(rightMenuListAdapter);
        rightMenuListAdapter.setItemOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    latitudeValue2 = ((TextView) view).getText().toString();
                    leftMenus.setVisibility(View.INVISIBLE);
                    rightMenus.setVisibility(View.INVISIBLE);
                    handler.sendMessageDelayed(handler.obtainMessage(1), 30);
                }
            });

        initRightMenu();

        btnMidHide = (ImageView) findViewById(R.id.btnMidHide);
        btnMidHide.setVisibility(View.INVISIBLE);
        latitudeBar = findViewById(R.id.latitudeBar);
        latitudeBar.setVisibility(View.INVISIBLE);
        btnMidShow = (ImageView) findViewById(R.id.btnMidShow);
        btnMidShow.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    btnMidShow.setVisibility(View.INVISIBLE);
                    btnMidHide.setVisibility(View.VISIBLE);
                    latitudeBar.setVisibility(View.VISIBLE);
                }
            });
        btnMidHide.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    btnMidShow.setVisibility(View.VISIBLE);
                    btnMidHide.setVisibility(View.INVISIBLE);
                    latitudeBar.setVisibility(View.INVISIBLE);
                }
            });
    }

    private void initRightMenu() {
        List<MenuItem> items;
        MenuItem item;
        items = new ArrayList<MenuItem>();
        if (latitudeValue1.equals(getString(R.string.latitude_menu1_left))) {

            item = new MenuItem();
            item.setTxt(getString(R.string.latitude_menu1_right));
            items.add(item);

            item = new MenuItem();
            item.setTxt(getString(R.string.latitude_menu2_right));
            items.add(item);

            item = new MenuItem();
            item.setTxt(getString(R.string.latitude_menu3_right));
            items.add(item);
        } else {
            item = new MenuItem();
            item.setTxt(getString(R.string.latitude_menu4_right));
            items.add(item);

            item = new MenuItem();
            item.setTxt(getString(R.string.latitude_menu5_right));
            items.add(item);
        }
        rightMenuListAdapter.setItems(items);
        rightMenuListAdapter.notifyDataSetChanged();
    }

    protected void switchView() {
        hangerView = !hangerView;
        Intent result = new Intent();
        result.putExtra("type", type);
        setResult(SWITCH_HANGER, result);
        finish();
    }

    protected abstract void refreshAdapter();

    protected List<ArtifactItemModel> loadItems() {
        if (StringUtilities.isEmpty(latitudeValue1) ^ StringUtilities.isEmpty(latitudeValue2)) {
            return DataFactory.getSingle().filter(latitudeValue1, latitudeValue2, type);
        }
        return DataFactory.getSingle().findType(type).getItems();
    }

    @Override
    protected void addNew() {
        HashMap params = new HashMap();
        params.put(AddNewActivity.PARAM_CATALOG, type);
        params.put(AddNewActivity.PARAM_CATEGORY1, latitudeValue1);
        params.put(AddNewActivity.PARAM_CATEGORY2, latitudeValue2);
        startActivity(AddNewActivity.class, params, ADD_NEW);
    }

    @Override
    protected void onReceiveResult(int requestCode, int resultCode, Intent data) throws Exception {
        if (requestCode == ADD_NEW) {
            HashMap params = new HashMap();
            params.put(AddNewActivity.PARAM_CATALOG, type);
            params.put(AddNewActivity.PARAM_CATEGORY1, latitudeValue1);
            params.put(AddNewActivity.PARAM_CATEGORY2, latitudeValue2);
            startActivity(PicConfirmActivity.class, params, CONFIRM_ADD_NEW);
        } else if (requestCode == CONFIRM_ADD_NEW) {
            refreshAdapter();
        }
    }
}
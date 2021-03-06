package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import jbolt.android.R;
import jbolt.android.base.AppConfig;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.meta.MenuItem;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.SDCardUtilities;
import jbolt.android.wardrobe.adapters.CatalogListAdapter;
import jbolt.android.wardrobe.adapters.MenuListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactTypeModel;
import jbolt.android.wardrobe.models.CatalogItemModel;
import jbolt.android.wardrobe.models.Person;
import jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl;
import org.apache.commons.lang.StringUtils;

/**
 * <p>Title: WardrobeCatalogActivity</p>
 * <p>Description: WardrobeCatalogActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class WardrobeCatalogActivity extends WardrobeFrameActivity {

    private Button btnAdd;
    private ListView lstCatalog;
    private ListView menus;
    private CatalogListAdapter listAdapter;
    private MenuListAdapter menuListAdapter;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.catalog);
        login();
        File file = new File(SDCardUtilities.getSdCardPath() + "/tmp.jpg");
        if (file.exists()) {
            file.delete();
        }
        btnAdd = (Button) findViewById(R.id.btnAdd);
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
        btnTopReturn.setOnClickListener(
                new OnClickListener() {
                    public void onClickAction(View view) {
                        exit();
                    }
                });
        btnAdd.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClickAction(View v) {
                        addNew();
                    }
                });
    }

    private void more() {
        if (menus.getVisibility() == View.INVISIBLE) {
            menus.setVisibility(View.VISIBLE);
        } else {
            menus.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onReceiveResult(int requestCode, int resultCode, Intent data) throws Exception {
        super.onReceiveResult(requestCode, resultCode, data);
        if (resultCode != CANCEL_ADD) {
            if (requestCode == SWITCH_HANGER && data != null) {
                String type = data.getStringExtra("type");
                ActivityDispatcher.callClothesCatalogActivity(this, type);
            } else if (requestCode == ADD_NEW) {
                startActivity(PicConfirmActivity.class, new HashMap(), CONFIRM_ADD_NEW);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void login() {
        final String userId;
        byte[] userInfo = SDCardUtilities.readSDCardFile(DataFactory.FILE_ROOT + "user.info");
        if (userInfo != null) {
            userId = new String(userInfo);
            Person person = new Person();
            person.setId(userId);
            PersonManagerDefaultImpl.find(
                    person, new BaseHandler() {
                @Override
                protected void handleMsg(Message msg) throws Exception {
                    if (msg.obj instanceof Person) {
                        AppContext.setUser((Person) msg.obj);
                    } else {
                        createNewUser(userId, false);
                    }
                }
            });
        } else {
            userId = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
            createNewUser(userId, true);
        }
        AppConfig.setProperty(DataFactory.USER_ID, userId);

    }

    private void createNewUser(String userId, final boolean writeSDCard) {
        final Person person = new Person();
        person.setId(userId);
        person.setCreateDate(new Date());
        PersonManagerDefaultImpl.create(
                person, new BaseHandler() {
            @Override
            protected void handleMsg(Message msg) throws Exception {
                if (msg.obj instanceof Person) {
                    AppContext.setUser((Person) msg.obj);
                    if (writeSDCard) {
                        SDCardUtilities.writeToSDCardFile(DataFactory.FILE_ROOT + "user.info", person.getId().getBytes(), false);
                    }
                } else {
                    MessageHandler.showWarningMessage(AppContext.context, (Exception) msg.obj);
                }
            }
        });
    }
}
package jbolt.android.wardrobe.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import jbolt.android.R;
import jbolt.android.utils.SDCardUtilities;
import jbolt.android.utils.StringUtilities;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItemModel;
import jbolt.android.wardrobe.models.ArtifactTypeModel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Title: PicConfirmActivity</p>
 * <p>Description: PicConfirmActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class PicConfirmActivity extends WardrobeFrameActivity {

    private ImageButton btnCancel;
    private ImageButton btnOK;
    private ImageView imgView;
    private EditText txtContent;
    private String defaultType;
    private String defaultLatitude1;
    private String defaultLatitude2;
    private Spinner cboLatitude1;
    private Spinner cboLatitude2;
    private Spinner cboType;

    private ArrayAdapter latitude2Adapter;
    private List<String> typesSet = new ArrayList<String>();

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.pic_confirm);

        imgView = (ImageView) findViewById(R.id.imgView);
        txtContent = (EditText) findViewById(R.id.txtContent);
        File thumbnail = new File(SDCardUtilities.getSdCardPath() + DataFactory.FILE_ROOT + "/tmp/thumbnail.jpeg");
        if (thumbnail.exists()) {
            FileInputStream fis = new FileInputStream(thumbnail);
            Bitmap thumbnailPic = BitmapFactory.decodeStream(fis);
            imgView.setImageBitmap(thumbnailPic);
        }
        btnOK = (ImageButton) findViewById(R.id.btnOk);
        btnOK.setOnClickListener(
            new View.OnClickListener() {

                public void onClick(View view) {
                    confirm();
                }
            });

        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
            new View.OnClickListener() {

                public void onClick(View view) {
                    finish();
                }
            });
        initLatitudeSpinner();
    }

    private void initLatitudeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Resources resources = getResources();

        HashMap addParams = (HashMap) params;
        defaultType = (String) addParams.get(AddNewActivity.PARAM_CATALOG);
        defaultLatitude1 = (String) addParams.get(AddNewActivity.PARAM_CATEGORY1);
        defaultLatitude2 = (String) addParams.get(AddNewActivity.PARAM_CATEGORY2);

        if (StringUtilities.isEmpty(defaultType)) {
            defaultType = "clothes";
        }
        if (StringUtilities.isEmpty(defaultLatitude1)) {
            defaultLatitude1 = resources.getString(R.string.latitude_menu1_left);
        }

        //latitude 1
        adapter.add(resources.getString(R.string.latitude_menu1_left));
        adapter.add(resources.getString(R.string.latitude_menu2_left));
        cboLatitude1 = (Spinner) findViewById(R.id.cboLatitude1);
        cboLatitude1.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Spinner spinner = (Spinner) adapterView;
                    defaultLatitude1 = spinner.getSelectedItem().toString();
                    refreshSpinners();
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        cboLatitude1.setAdapter(adapter);

        cboLatitude2 = (Spinner) findViewById(R.id.cboLatitude2);
        cboLatitude2.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Spinner spinner = (Spinner) adapterView;
                    defaultLatitude2 = spinner.getSelectedItem().toString();
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        latitude2Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        latitude2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboLatitude2.setAdapter(latitude2Adapter);

        cboType = (Spinner) findViewById(R.id.cboType);
        cboType.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Spinner spinner = (Spinner) adapterView;
                    defaultType = DataFactory.getSingle().getTypes().get(spinner.getSelectedItemPosition()).getId();
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add(resources.getString(R.string.type1));
        adapter.add(resources.getString(R.string.type2));
        adapter.add(resources.getString(R.string.type3));
        adapter.add(resources.getString(R.string.type4));
        adapter.add(resources.getString(R.string.type5));
        adapter.add(resources.getString(R.string.type6));
        adapter.add(resources.getString(R.string.type7));
        adapter.add(resources.getString(R.string.type8));
        adapter.add(resources.getString(R.string.type9));
        cboType.setAdapter(adapter);
        refreshSpinners();
    }

    private void refreshSpinners() {
        List<ArtifactTypeModel> typeModels = DataFactory.getSingle().getTypes();
        int index = 0;
        for (ArtifactTypeModel typeModel : typeModels) {
            if (defaultType.equals(typeModel.getId())) {
                break;
            }
            index++;
        }
        cboType.setSelection(index);


        Resources resources = getResources();
        index = 0;
        if (!defaultLatitude1.equals(resources.getString(R.string.latitude_menu1_left))) {
            index = 1;
        }
        cboLatitude1.setSelection(index);

        latitude2Adapter.clear();
        index = 0;
        if (defaultLatitude1.equals(resources.getString(R.string.latitude_menu1_left))) {
            latitude2Adapter.add(resources.getString(R.string.latitude_menu1_right));
            latitude2Adapter.add(resources.getString(R.string.latitude_menu2_right));
            latitude2Adapter.add(resources.getString(R.string.latitude_menu3_right));
            if (StringUtilities.isEmpty(defaultLatitude2)) {
                defaultLatitude2 = resources.getString(R.string.latitude_menu1_right);
            } else {
                if (defaultLatitude2.equals(resources.getString(R.string.latitude_menu2_right))) {
                    index = 1;
                } else if (defaultLatitude2.equals(resources.getString(R.string.latitude_menu3_right))) {
                    index = 2;
                }
            }
        } else {
            latitude2Adapter.add(resources.getString(R.string.latitude_menu4_right));
            latitude2Adapter.add(resources.getString(R.string.latitude_menu5_right));
            if (StringUtilities.isEmpty(defaultLatitude2)) {
                defaultLatitude2 = resources.getString(R.string.latitude_menu4_right);
            } else {
                if (defaultLatitude2.equals(resources.getString(R.string.latitude_menu5_right))) {
                    index = 1;
                }
            }
        }
        latitude2Adapter.notifyDataSetChanged();
        cboLatitude2.setSelection(index);
    }

    private void confirm() {
        ArtifactItemModel itemModel = new ArtifactItemModel();
        itemModel.setLatitude1(defaultLatitude1);
        itemModel.setLatitude2(defaultLatitude2);
        itemModel.setDescription(txtContent.getText().toString());
        DataFactory.getSingle().addArtifactItem(itemModel, defaultType, null);
        finish();
    }


}
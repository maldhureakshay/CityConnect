package com.cityconnect.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cityconnect.R;
import com.cityconnect.adapter.CategoryIconGridViewAdapter;
import com.cityconnect.interfaces.GridItemClickInterface;
import com.cityconnect.modal.CategoryModel;
import com.cityconnect.utility.AppConstants;
import com.cityconnect.utility.AppPreferences;
import com.cityconnect.utility.LocationManager;
import com.cityconnect.utility.MarshMallowPermission;
import com.cityconnect.webservice.ApiConstants;
import com.cityconnect.webservice.ApiHelper;
import com.cityconnect.webservice.BaseHttpHelper;
import com.cityconnect.webservice.BaseResponse;
import com.cityconnect.widget.MMToast;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UploadIssueActivity extends ParentActivity implements View.OnClickListener, BaseHttpHelper.ResponseHelper, GridItemClickInterface {

    LinearLayout cameraButton,galleryButton;
    ImageView issueImageView;
    MarshMallowPermission marshMallowPermission;
    Uri file;
    Context mContext;
    ImageView selectedCategoryImageView;
    EditText issueTitleEDT, issueDescriptionEDT;
    TextView selectedCategoryNameTXTV;
    Button uploadIssueButton;
    final List<CategoryModel> list = new ArrayList<CategoryModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_issue);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Upload Issue");

        cameraButton = (LinearLayout)findViewById(R.id.camera_btn);
        cameraButton.setOnClickListener(this);
        selectedCategoryImageView = (ImageView)findViewById(R.id.selected_image);

        galleryButton = (LinearLayout)findViewById(R.id.gallery_btn);
        galleryButton.setOnClickListener(this);

        issueImageView = (ImageView)findViewById(R.id.issueImageView);
        issueTitleEDT = (EditText)findViewById(R.id.issue_title_edt);
        issueDescriptionEDT =(EditText)findViewById(R.id.issue_desc_edt);
        selectedCategoryNameTXTV =(TextView) findViewById(R.id.selected_category_txtv);
        uploadIssueButton =(Button) findViewById(R.id.upload_issue_button);
        uploadIssueButton.setOnClickListener(this);

        marshMallowPermission = new MarshMallowPermission(mContext,UploadIssueActivity.this);


        for(int i=1;i<=20;i++){
            CategoryModel model = new CategoryModel();
            model.setCategoryId(""+i);
            model.setCategoryImage(R.drawable.beggers);
            model.setCategoryName("Baggers");
            list.add(model);
        }


        GridView gridView = (GridView) findViewById(R.id.category_icon_container);

        gridView.setNumColumns(4);

        CategoryIconGridViewAdapter gridAdapter = new CategoryIconGridViewAdapter(UploadIssueActivity.this,list);
        gridView.setAdapter(gridAdapter);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==cameraButton){
          getPhotoFromCamera();
        }
        if(v==galleryButton){
            getPhotoFromGallery();
        }

        if(v == uploadIssueButton){
            uploadIssueAction();
        }

    }

    public void  getPhotoFromGallery(){
        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage();
        } else {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 101);
        }
    }


    public void getPhotoFromCamera() {

        if (!marshMallowPermission.checkPermissionForCamera()) {
            marshMallowPermission.requestPermissionForCamera();
        } else {
            if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                marshMallowPermission.requestPermissionForExternalStorage();
            } else {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = Uri.fromFile(getOutputMediaFile());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                startActivityForResult(intent, 100);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                issueImageView.setImageURI(file);
            }
        }
        if(requestCode==101){
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    issueImageView.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            }
    }


    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), AppConstants.UPLOAD_FOLDER);

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public void uploadIssueAction() {

        LocationManager locationManager = new LocationManager(mContext,
                new LocationManager.LocationCallbackListener() {

                    @Override
                    public void onLocationReceived(Location location) {

                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location
                                    .getLongitude());
                            String latitude = "" + location.getLatitude();
                            String longitude = "" + location.getLongitude();
                            AppPreferences appPreferences = AppPreferences.getInstance(mContext);
                            appPreferences.putString(AppConstants.LOC_LATITUDE, latitude);
                            appPreferences.putString(AppConstants.LOC_LONGITUDE, longitude);

                        }
                    }
                });


                            try {
            AppPreferences preferences = AppPreferences.getInstance(mContext);

                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair(AppConstants.USER_ID, "1"));
                                params.add(new BasicNameValuePair(AppConstants.ISSUE_CATEGORY_ID, "1"));
                                params.add(new BasicNameValuePair(AppConstants.ISSUE_LATITUDE, preferences.getString(AppConstants.LOC_LATITUDE, "")));
                                params.add(new BasicNameValuePair(AppConstants.LOC_LONGITUDE, preferences.getString(AppConstants.LOC_LONGITUDE, "")));
                                params.add(new BasicNameValuePair(AppConstants.ISSUE_TITLE_ENG, issueTitleEDT.getText().toString().trim()));
                                params.add(new BasicNameValuePair(AppConstants.ISSUE_TITLE_HINDI, ""));
                                params.add(new BasicNameValuePair(AppConstants.ISSUE_TITLE_MARATHI, ""));
                                params.add(new BasicNameValuePair(AppConstants.ISSUE_DESC_ENG, "sfsfsfsfs"));
                                params.add(new BasicNameValuePair(AppConstants.ISSUE_DESC_MARATHI, ""));
                                params.add(new BasicNameValuePair(AppConstants.ISSUE_DESC_HINDI, ""));
                                params.add(new BasicNameValuePair(AppConstants.Issue_IMG, "NA"));


                                showLoadingDialog();
            ApiHelper lEditProfileApi = new ApiHelper(ApiConstants.POST, ApiConstants.UPLOAD_ISSUE_URL,params, this);
            lEditProfileApi.mApiID = ApiConstants.UPLOAD_ISSUE_ID;
            lEditProfileApi.invokeAPI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String BitmaptoString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    /**
     * reduces the size of the image
     * @param image
     * @param maxSize
     * @return
     */
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onSuccess(BaseResponse pResponse) {
        dismissLoadingDialog();
     int mApiId = pResponse.getmAPIType();
        Log.d("Response", ""+pResponse.toString());
    }

    @Override
    public void onFail(BaseResponse pBaseResponse) {
        dismissLoadingDialog();
        if(pBaseResponse!=null) {
            int mApiId = pBaseResponse.getmAPIType();
            Log.d("Response", "" + pBaseResponse.toString());
        }
        else
            MMToast.getInstance().showLongToast("No Server Response",mContext);
    }

    @Override
    public void clickedGridItemAtIndex(int index) {
        CategoryModel model = list.get(index);
        selectedCategoryImageView.setImageResource(model.getCategoryImage());
        selectedCategoryNameTXTV.setText(model.getCategoryName());
    }
}


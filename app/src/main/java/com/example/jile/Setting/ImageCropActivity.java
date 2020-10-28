package com.example.jile.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.example.jile.R;
import com.xuexiang.xui.widget.imageview.crop.CropImageType;
import com.xuexiang.xui.widget.imageview.crop.CropImageView;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.app.PathUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.jile.myApplication.getContext;

public class ImageCropActivity extends AppCompatActivity {
    CropImageView cropImageView = findViewById(R.id.crop_image_view);
    SuperButton btnCrop = findViewById(R.id.btn_crop);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeSettingActivity.setActivityTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);
        // 触摸时显示网格
        cropImageView.setGuidelines(CropImageType.CROPIMAGE_GRID_ON);
        // 固定比例剪切
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setAspectRatio(40, 30);
        //确认按钮
        btnCrop.setEnabled(false);

        findViewById(R.id.btn_select).setOnClickListener(v->selectImage());
        findViewById(R.id.btn_crop).setOnClickListener(v->{
            cropImageView.cropImage();
            //使用getCroppedImage获取裁剪的图片
            Bitmap bm_image = cropImageView.getCroppedImage();
            getContext().getFilesDir();
            btnCrop.setEnabled(false);
        });
    }
    int REQUEST_IMAGE = 112;
    private void selectImage() {
        startActivityForResult(IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.IMAGE), REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择系统图片并解析
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    cropImageView.setImagePath(PathUtils.getFilePathByUri(uri));
                    btnCrop.setEnabled(true);
                }
            }
        }
    }

    private void saveBitmap(Bitmap bitmap,String path, String filename) throws IOException
    {
        File files =  getContext().getFilesDir();

        FileInputStream fis = getContext().openFileInput(filename);
        File file = new File(path + filename);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out))
            {
                out.flush();
                out.close();
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
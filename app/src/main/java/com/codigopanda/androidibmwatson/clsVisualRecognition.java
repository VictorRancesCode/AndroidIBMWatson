package com.codigopanda.androidibmwatson;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImage;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifierResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions.AcceptLanguage.ES;

public class clsVisualRecognition extends AppCompatActivity {
    private VisualRecognition vrClient;
    private CameraHelper helper;

    String API_KEY = "YOUR API KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fvisualrecognition);
        // Initialize Visual Recognition client
        vrClient = new VisualRecognition(
                VisualRecognition.VERSION_DATE_2016_05_20,
                API_KEY
        );

        // Initialize camera helper
        helper = new CameraHelper(this);
    }


    public void takePicture(View view) {
        helper.dispatchTakePictureIntent();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
            final Bitmap photo = helper.getBitmap(resultCode);
            final File photoFile = helper.getFile(resultCode);
            ImageView preview = findViewById(R.id.preview);
            preview.setImageBitmap(photo);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        ClassifiedImages response = vrClient.classify(new ClassifyOptions.Builder().acceptLanguage(ES).imagesFile(photoFile).build()).execute();
                        ClassifiedImage image = response.getImages().get(0);
                        List<ClassifierResult> list = image.getClassifiers();
                        ClassifierResult x = list.get(0);
                        List<ClassResult> resultado = x.getClasses();
                        String datos="Resultado: \n";
                        for (int i = 0; i < resultado.size(); i++) {
                            if (resultado.get(i).getScore() > 0.5f) {
                                datos+=resultado.get(i).getClassName()+", ";
                            }
                        }
                        final String res =datos;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView detectedObjects =
                                        findViewById(R.id.detected_objects);
                                detectedObjects.setText(res.toString());
                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    }


}

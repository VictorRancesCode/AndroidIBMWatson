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
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ImageClassification;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassifier;

import java.io.File;

public class clsVisualRecognition extends AppCompatActivity {
    private VisualRecognition vrClient;
    private CameraHelper helper;

    String API_KEY= "YOUR API KEY";

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
                    VisualClassification response =
                            vrClient.classify(new ClassifyImagesOptions.Builder().images(photoFile).build()).execute();
                    // More code here
                    ImageClassification classification =
                            response.getImages().get(0);

                    VisualClassifier classifier =
                            classification.getClassifiers().get(0);

                    final StringBuffer output = new StringBuffer();
                    for(VisualClassifier.VisualClass object: classifier.getClasses()) {
                        if(object.getScore() > 0.7f)
                            output.append("<")
                                    .append(object.getName())
                                    .append("> ");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView detectedObjects =
                                    findViewById(R.id.detected_objects);
                            detectedObjects.setText(output);
                        }
                    });
                }
            });


        }
    }


}

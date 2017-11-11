package com.codigopanda.androidibmwatson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

public class clsWatsonTTS extends AppCompatActivity {

    TextView title;
    EditText texto;
    Button boton;

    StreamPlayer streamPlayer;

    private TextToSpeech iniTextToSpeech() {
        TextToSpeech service = new TextToSpeech();
        String username = "YOUR USER";
        String password = "YOUR PASSWORD";
        service.setUsernameAndPassword(username, password);
        return service;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_watson_tts);

        texto = (EditText) findViewById(R.id.editwatsontts);
        boton = (Button) findViewById(R.id.buttonwatsontts);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WatsonTask task = new WatsonTask();
                task.execute(new String[]{});
            }
        });
    }


    private class WatsonTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
            TextToSpeech textToSpeech = iniTextToSpeech();
            streamPlayer = new StreamPlayer();
            streamPlayer.playStream(textToSpeech.synthesize(texto.getText().toString(), Voice.ES_ENRIQUE).execute());
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

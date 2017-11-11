package com.codigopanda.androidibmwatson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneHelper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;

public class clsSpeechToText extends AppCompatActivity {
    private SpeechToText speechService;

    private SpeechToText initSpeechToTextService() {
        SpeechToText service = new SpeechToText();
        String username = "YOUR USER";
        String password = "YOUR PASSWORD";
        service.setUsernameAndPassword(username, password);
        service.setEndPoint(getString(R.string.speech_text_url));
        return service;
    }

    private MicrophoneHelper microphoneHelper;

    private ImageButton mic;
    private boolean listening = false;
    private MicrophoneInputStream capture;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fspeechtotext);
        speechService = initSpeechToTextService();
        mic = (ImageButton) findViewById(R.id.mic);
        microphoneHelper = new MicrophoneHelper(this);
        input = (EditText) findViewById(R.id.input);
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mic.setEnabled(false);

                if (!listening) {
                    capture = microphoneHelper.getInputStream(true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                speechService.recognizeUsingWebSocket(capture, getRecognizeOptions(),
                                        new MicrophoneRecognizeDelegate());
                            } catch (Exception e) {
                                showError(e);
                            }
                        }
                    }).start();
                    listening = true;
                } else {
                    microphoneHelper.closeInputStream();
                    listening = false;
                }
            }
        });
    }


    private void showError(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(clsSpeechToText.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }


    private RecognizeOptions getRecognizeOptions() {
        return new RecognizeOptions.Builder().continuous(true).contentType(ContentType.OPUS.toString())
                .model("es-ES_BroadbandModel").interimResults(true).inactivityTimeout(2000).build();
    }


    private void enableMicButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mic.setEnabled(true);
            }
        });
    }

    private void showMicText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                input.setText(text);
            }
        });
    }


    private class MicrophoneRecognizeDelegate extends BaseRecognizeCallback {

        @Override
        public void onTranscription(SpeechResults speechResults) {
            System.out.println(speechResults);
            if (speechResults.getResults() != null && !speechResults.getResults().isEmpty()) {
                String text = speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();
                showMicText(text);
            }
        }

        @Override
        public void onError(Exception e) {
            showError(e);
            enableMicButton();
        }

        @Override
        public void onDisconnected() {
            enableMicButton();
        }
    }
}

package com.codigopanda.androidibmwatson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;

public class clsLanguageTranslator extends AppCompatActivity {

    private LanguageTranslator translationService;
    private RadioGroup targetLanguage;
    private Language selectedTargetLanguage = Language.SPANISH;
    private TextView translatedText;
    private Button translate;
    private EditText input;

    private LanguageTranslator initLanguageTranslatorService() {
        LanguageTranslator service = new LanguageTranslator();
        String username = "YOUR USER";
        String password = "YOUR PASSWORD";
        service.setUsernameAndPassword(username, password);
        service.setEndPoint("");
        return service;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flanguage_translator);

        translationService = initLanguageTranslatorService();

        translatedText = (TextView) findViewById(R.id.translated_text);
        targetLanguage = (RadioGroup) findViewById(R.id.target_language);
        translate = (Button) findViewById(R.id.translate);
        input = (EditText) findViewById(R.id.inputtranslator);
        targetLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.spanish:
                        selectedTargetLanguage = Language.SPANISH;
                        break;
                    case R.id.french:
                        selectedTargetLanguage = Language.FRENCH;
                        break;
                    case R.id.italian:
                        selectedTargetLanguage = Language.ITALIAN;
                        break;
                    case R.id.english:
                        selectedTargetLanguage = Language.ENGLISH;
                        break;
                }
            }
        });

        translate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new TranslationTask().execute(input.getText().toString());
            }
        });

    }


    private void showTranslation(final String translation) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                translatedText.setText(translation);
            }
        });
    }

    private class TranslationTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            showTranslation(translationService.translate(params[0], Language.SPANISH, selectedTargetLanguage).execute()
                    .getFirstTranslation());
            return "Did translate";
        }
    }
}

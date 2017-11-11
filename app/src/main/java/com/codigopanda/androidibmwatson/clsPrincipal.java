package com.codigopanda.androidibmwatson;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class clsPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<ItemMenu> lista;
    ListView listamenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fprincipal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        listamenu = (ListView) findViewById(R.id.listamenu);
        CargarDatos();
        AdapMenu menu = new AdapMenu(lista, clsPrincipal.this);
        listamenu.setAdapter(menu);

        listamenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(clsPrincipal.this, clsWatsonTTS.class));
                        break;
                    case 1:
                        startActivity(new Intent(clsPrincipal.this, clsSpeechToText.class));
                        break;
                    case 2:
                        startActivity(new Intent(clsPrincipal.this, clsVisualRecognition.class));
                        break;
                    case 3:
                        startActivity(new Intent(clsPrincipal.this, clsLanguageTranslator.class));
                        break;
                }
            }
        });

    }


    public void CargarDatos() {
        lista = new ArrayList<>();
        lista.add(new ItemMenu("Text to Speech", R.drawable.texttospeech));
        lista.add(new ItemMenu("Speech to Text", R.drawable.speechtotext));
        lista.add(new ItemMenu("Visual Recognition", R.drawable.visual));
        lista.add(new ItemMenu("Lenguaje Translator", R.drawable.lenguaje2));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cls_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.WatsonTTS) {
            startActivity(new Intent(clsPrincipal.this, clsWatsonTTS.class));
            // Handle the camera action
        } else if (id == R.id.VisualRecognition) {
            startActivity(new Intent(clsPrincipal.this, clsVisualRecognition.class));
        } else if (id == R.id.SpeechToText) {
            startActivity(new Intent(clsPrincipal.this, clsSpeechToText.class));
        } else if (id == R.id.LanguageTranslator) {
            startActivity(new Intent(clsPrincipal.this, clsLanguageTranslator.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.zekaoyunlari;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class OyunlarActivity extends Activity implements AdapterView.OnItemSelectedListener {

    boolean userSelected = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oyunlar);
        Spinner spinner = (Spinner) findViewById(R.id.games_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.games_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        Intent intent;
        if (userSelected){
            switch (pos){
                case 0:
                    intent = new Intent(this, HedefTahtasiActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(this, ArtiEksiSayiActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(this, KaplarActivity.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(this, HarflerActivity.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(this, GruplarActivity.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(this, FenerlerActivity.class);
                    startActivity(intent);
                    break;
                case 6:
                    intent = new Intent(this, EslerActivity.class);
                    startActivity(intent);
                    break;
                case 7:
                    intent = new Intent(this, KutuSilActivity.class);
                    startActivity(intent);
                    break;
                case 8:
                    intent = new Intent(this, HazineAviActivity.class);
                    startActivity(intent);
                    break;
                case 9:
                    intent = new Intent(this, AltigenActivity.class);
                    startActivity(intent);
                    break;
                case 10:
                    intent = new Intent(this, SayiBilmeceActivity.class);
                    startActivity(intent);
                    break;
                case 11:
                    intent = new Intent(this, SatrancTaslariActivity.class);
                    startActivity(intent);
                    break;
                case 12:
                    intent = new Intent(this, PiramitActivity.class);
                    startActivity(intent);
                    break;
                case 13:
                    intent = new Intent(this, CarpmacaActivity.class);
                    startActivity(intent);
                    break;
                case 14:
                    intent = new Intent(this, OklarActivity.class);
                    startActivity(intent);
                    break;
                case 15:
                    intent = new Intent(this, CadirActivity.class);
                    startActivity(intent);
                    break;
                case 16:
                    intent = new Intent(this, CiceklerActivity.class);
                    startActivity(intent);
                    break;
                case 17:
                    intent = new Intent(this, KapsulActivity.class);
                    startActivity(intent);
                    break;
            }
        }
        userSelected = true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}

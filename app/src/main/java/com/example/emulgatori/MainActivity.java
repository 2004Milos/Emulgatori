package com.example.emulgatori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addBtn; //DUGME ZA DODAVANJE PROIZVODA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.fabAdd);

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddImageActivity.class);
                startActivity(i);
            }
        });

        SQLiteDatabase dbase = openOrCreateDatabase("aditivi",MODE_PRIVATE,null);

        dbase.execSQL(
            "CREATE TABLE if not exists Emulgator (\n" +
                    "  Naziv varchar(15) NOT NULL,\n" +
                    "  poruka text DEFAULT NULL,\n" +
                    "  drugi_naziv text DEFAULT NULL\n" +
                    ")"
        );

        dbase.execSQL(
                "INSERT INTO `Emulgator` (`Naziv`, `poruka`, `drugi_naziv`) VALUES\n" +
                        "('E100', 'Nije stetan', NULL),\n" +
                        "('E101', 'Nije stetan', NULL),\n" +
                        "('E103', 'Nije stetan', NULL),\n" +
                        "('E104', 'Nije stetan', NULL),\n" +
                        "('E105', 'Nije stetan', NULL),\n" +
                        "('E111', 'Nije stetan', NULL),\n" +
                        "('E121', 'Nije stetan', NULL),\n" +
                        "('E130', 'Nije stetan', NULL),\n" +
                        "('E132', 'Nije stetan', NULL),\n" +
                        "('E140', 'Nije stetan', NULL),\n" +
                        "('E151', 'Nije stetan', NULL),\n" +
                        "('E152', 'Nije stetan', NULL),\n" +
                        "('E160', 'Nije stetan', NULL),\n" +
                        "('E161', 'Nije stetan', NULL),\n" +
                        "('E162', 'Nije stetan', NULL),\n" +
                        "('E170', 'Nije stetan', NULL),\n" +
                        "('E174', 'Nije stetan', NULL),\n" +
                        "('E175', 'Nije stetan', NULL),\n" +
                        "('E180', 'Nije stetan', NULL),\n" +
                        "('E200', 'Nije stetan', NULL),\n" +
                        "('E201', 'Nije stetan', NULL),\n" +
                        "('E202', 'Nije stetan', NULL),\n" +
                        "('E203', 'Nije stetan', NULL),\n" +
                        "('E236', 'Nije stetan', NULL),\n" +
                        "('E237', 'Nije stetan', NULL),\n" +
                        "('E238', 'Nije stetan', NULL),\n" +
                        "('E260', 'Nije stetan', NULL),\n" +
                        "('E261', 'Nije stetan, ali neka ga izbegavaju ljudi sa oštećenjem bubrega', NULL),\n" +
                        "('E263', 'Nije stetan', NULL),\n" +
                        "('E270', 'Nije stetan', NULL),\n" +
                        "('E282', 'Nije stetan', NULL),\n" +
                        "('E290', 'Nije stetan, ali povećava dejstvo alkohola', NULL),\n" +
                        "('E300', 'Nije stetan', NULL),\n" +
                        "('E301', 'Nije stetan', NULL),\n" +
                        "('E303', 'Nije stetan', NULL),\n" +
                        "('E305', 'Nije stetan', NULL),\n" +
                        "('E306', 'Nije stetan', NULL),\n" +
                        "('E307', 'Nije stetan', NULL),\n" +
                        "('E308', 'Nije stetan', NULL),\n" +
                        "('E309', 'Nije stetan', NULL),\n" +
                        "('E322', 'Nije stetan', NULL),\n" +
                        "('E325', 'Nije stetan,ali neka ga izbegavaju deca netolerantna na laktozu', NULL),\n" +
                        "('E326', 'Nije stetan', NULL),\n" +
                        "('E327', 'Nije stetan', NULL),\n" +
                        "('E331', 'Nije stetan', NULL),\n" +
                        "('E332', 'Nije stetan', NULL),\n" +
                        "('E333', 'Nije stetan', NULL),\n" +
                        "('E334', 'Nije stetan', NULL),\n" +
                        "('E335', 'Nije stetan', NULL),\n" +
                        "('E336', 'Nije stetan', NULL),\n" +
                        "('E337', 'Nije stetan', NULL),\n" +
                        "('E382', 'Nije stetan', NULL),\n" +
                        "('E400', 'Nije stetan', NULL),\n" +
                        "('E401', 'Nije stetan', NULL),\n" +
                        "('E402', 'Nije stetan', NULL),\n" +
                        "('E403', 'Nije stetan', NULL),\n" +
                        "('E404', 'Nije stetan', NULL),\n" +
                        "('E405', 'Nije stetan', NULL),\n" +
                        "('E406', 'Nije stetan', NULL),\n" +
                        "('E408', 'Nije stetan', NULL),\n" +
                        "('E410', 'Nije stetan', NULL),\n" +
                        "('E411', 'Nije stetan', NULL),\n" +
                        "('E413', 'Nije stetan, ali moze izazvati alergiju', NULL),\n" +
                        "('E422', 'Nije stetan', NULL),\n" +
                        "('E440', 'Nije stetan', NULL),\n" +
                        "('E471', 'Nije stetan', NULL),\n" +
                        "('E472', 'Nije stetan', NULL),\n" +
                        "('E473', 'Nije stetan', NULL),\n" +
                        "('E474', 'Nije stetan', NULL),\n" +
                        "('E475', 'Nije stetan', NULL),\n" +
                        "('E480', 'Nije stetan', NULL),\n" +
                        "('E125', 'Sumljivi dodaci', NULL),\n" +
                        "('E141', 'Sumljivi dodaci', NULL),\n" +
                        "('E150', 'Sumljivi dodaci', NULL),\n" +
                        "('E153', 'Sumljivi dodaci', NULL),\n" +
                        "('E171', 'Sumljivi dodaci', NULL),\n" +
                        "('E172', 'Sumljivi dodaci', NULL),\n" +
                        "('E173', 'Sumljivi dodaci', NULL),\n" +
                        "('E240', 'Sumljivi dodaci', NULL),\n" +
                        "('E241', 'Sumljivi dodaci', NULL),\n" +
                        "('E102', 'Opasni dodaci', NULL),\n" +
                        "('E110', 'Opasni dodaci', NULL),\n" +
                        "('E120', 'Opasni dodaci', NULL),\n" +
                        "('E124', 'Opasni dodaci', NULL),\n" +
                        "('E220', 'Problemi sa crevima, unistava vitamine astma, uništava vitamin B1', NULL),\n" +
                        "('E221', 'Problemi sa crevima', NULL),\n" +
                        "('E232', 'Problemi sa crevima, kozne bolesti', NULL),\n" +
                        "('E224', 'Problemi sa crevima', NULL),\n" +
                        "('E338', 'Problemi sa probavom', NULL),\n" +
                        "('E339', 'Problemi sa probavom i narusava ravnotezu izmedu kalcijuma i fosfora', NULL),\n" +
                        "('E340', 'Problemi sa probavom', NULL),\n" +
                        "('E341', 'Problemi sa probavom', NULL),\n" +
                        "('E450', 'Problemi sa probavom i remeti ravnotezu vode u arganizmu', NULL),\n" +
                        "('E461', 'Problemi sa probavom, gasovi, obstrukcija tankog creva', NULL),\n" +
                        "('E463', 'Problemi sa probavom i zabranjen u nekim zemljama', NULL),\n" +
                        "('E465', 'Problemi sa probavom', NULL),\n" +
                        "('E466', 'Problemi sa probavom', NULL),\n" +
                        "('E407', 'Problemi sa probavom i uzukuje cir', NULL),\n" +
                        "('E230', 'Kozne bolesti', NULL),\n" +
                        "('E231', 'Kozne bolesti', NULL),\n" +
                        "('E233', 'Kozne bolesti', NULL),\n" +
                        "('B12', 'Unistava vitamine', NULL),\n" +
                        "('E320', 'Povecava horesterol i utice na alergije, hiperaktivnost', NULL),\n" +
                        "('E321', 'Povecava horesterol', NULL),\n" +
                        "('E311', 'nadrazuje zivce', NULL),\n" +
                        "('E312', 'nadrazuje zivce', NULL),\n" +
                        "('E330', 'upala usen duplje', NULL),\n" +
                        "('E131', 'nadrazuje zivce', NULL),\n" +
                        "('E142', 'nadrazuje zivce', NULL),\n" +
                        "('E210', 'nadrazuje zivce, astma, neurološke teškoće, hiperaktivnost dece ', NULL),\n" +
                        "('E211', 'nadrazuje zivce i astma', NULL),\n" +
                        "('E213', 'nadrazuje zivce', NULL),\n" +
                        "('E214', 'nadrazuje zivce', NULL),\n" +
                        "('E215', 'nadrazuje zivce', NULL),\n" +
                        "('E216', 'nadrazuje zivce i alergije', NULL),\n" +
                        "('E217', 'nadrazuje zivce', NULL),\n" +
                        "('E239', 'nadrazuje zivce', NULL),\n" +
                        "('E123', 'vrlo otrovan te je zabranjen u SAD', NULL),\n" +
                        "('E218', 'kožne alergije', NULL),\n" +
                        "('E235', 'mučnina, povraćanje, proliv, nadražaj kože, anoreksija', NULL),\n" +
                        "('E249', 'poremećaj prenosa kiseonika, glavobolje, loša koncentracija, poteškoće sa disanjem', NULL),\n" +
                        "('E250', 'potencialni kancerogen, u želucu stvara nitrozamine, hiperaktivnost dece', NULL),\n" +
                        "('E251', 'u organizmu se pretvara u nitrit', NULL),\n" +
                        "('E252', 'hiperaktivnost, potencialno kancerogen, malokrvnost, štetan za bubrege, zabranjen u nekim zemljama', NULL),\n" +
                        "('E264', 'mučnine, povraćanje', NULL),\n" +
                        "('E280', 'uzrokuje migrene', NULL),\n" +
                        "('E281', 'uzrokuje migrene', NULL),\n" +
                        "('E302', 'uzrukuje stvaranje kamena u buregu', NULL),\n" +
                        "('E310', 'nadrazaj zeluca i koze', NULL),\n" +
                        "('E319', 'mucnina, povracanje, delirijum, smrtonosna doza je 5g', NULL),\n" +
                        "('E363', 'zabranjen u nekim zemljama', NULL),\n" +
                        "('E370', 'zabranjen u nekim zemljama', NULL),\n" +
                        "('E375', 'los za jetru, podize nivo mokracne kiseline, gastritis', NULL),\n" +
                        "('E380', 'los za jetru i sluznicu zeluca', NULL),\n" +
                        "('E385', 'zabranjen u nekim zemljama', NULL),\n" +
                        "('E412', 'mucnina, grcevi u stomaku, snizava nivo holesterola', NULL),\n" +
                        "('E414', 'nadrazenje sluznice, alergen', NULL),\n" +
                        "('E416', 'moguce su alergije', NULL),\n" +
                        "('E420', 'los za zeludac, nije dozvoljen za decju prehranu', NULL),\n" +
                        "('E421', 'proliv, mucnina, povracanje, los za bubrege', NULL),\n" +
                        "('E432', 'zabranjen u nekim zemljama', NULL),\n" +
                        "('E503', 'nadrazuje sluznicu', NULL),\n" +
                        "('E508', 'problemi sa zelucem', NULL),\n" +
                        "('E510', 'neka ga izbegavaju ljudi s bolesnim bubrezima i jetrom', NULL),\n" +
                        "('E513', 'zabranjen u nekim zemljama', NULL),\n" +
                        "('E514', 'remeti ravnotezu vode u organizmu', NULL),\n" +
                        "('E553', 'moze da izazove rak zeluca', NULL),\n" +
                        "('E554', 'teskoce s placentom kod trudnoce, alchajmerova bolest', NULL),\n" +
                        "('E620', 'neka ga izbegavju deca', NULL),\n" +
                        "('E621', 'glavobolja, zed, vrtoglavica, grcevi u stomaku, proliv, aritmija, zabranjen u proizvodima za decu', NULL),\n" +
                        "('E622', 'mucnina, povracanje, proliv', NULL),\n" +
                        "('E626', 'moze uzrokovati giht', NULL),\n" +
                        "('E627', 'moze uzrokovati giht, zabranjen u prizvodima za decu', NULL),\n" +
                        "('E629', 'moze uzrokovati giht', NULL),\n" +
                        "('E631', 'moze uzrokovati giht', NULL),\n" +
                        "('E633', 'moze uzorkovati giht', NULL),\n" +
                        "('E635', 'moze izazvati promene na kozi, zabranjen u mnogobrojnim zemljama', NULL),\n" +
                        "('E905', 'moguc rak pobavnog trakta', NULL),\n" +
                        "('E907', 'zabranjen u nekim zemljama', NULL),\n" +
                        "('E924', 'mucnina, povracanje, proliv', NULL),\n" +
                        "('E925', 'kancerogen, unistava biolosku vrednost hrane', NULL),\n" +
                        "('E927', 'zabranjen u nekim zemljama', NULL),\n" +
                        "('E950', 'spada medu najopasnije aditive, lose utice na stitnu zlezdu', NULL),\n" +
                        "('E951', 'alergije, migrene, vrtoglavica, smetnje kod vida, sluha i ukusa, remeti pamcenje, depresija, uzrocnik nekih degenerativnih bolesti (multipla skleroza, parkinsonova bolest), hormonski poremecaji', NULL),\n" +
                        "('E952', 'migrene, potencialno kancerogen, zabranjen u SAD i Engleskoj', NULL),\n" +
                        "('E954', 'izaziva rak kod zivotinja', NULL),\n" +
                        "('E967', 'kamen u bubregu, diuretik', NULL),\n" +
                        "('E1505', 'metabolise se u etanol', NULL),\n" +
                        "('E1520', 'moze da izazove infarkt, lose utice na nervni sistem, dermatitis', NULL);");




        dbase.close();
    }
}
package com.example.emulgatori;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addBtn; //DUGME ZA DODAVANJE PROIZVODA
    LinearLayout LLlista;
    SQLiteDatabase dbase;
    MenuItem clearbtn;

    @Override
    protected void onStart() {
        super.onStart();
        LLlista.removeAllViews();
        dbase = SQLiteDatabase.openDatabase(dbase.getPath(), null, 0);
        String query = "SELECT * FROM Proizvodi";
        Cursor cursor = (dbase).rawQuery(query, null);
        while (cursor.moveToNext()) {
            TextView tvp = new TextView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            int dp1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()); //velicina 1dp
            layoutParams.bottomMargin = dp1 * 8; //8dp (1dp * 8)
            tvp.setPadding((int) (dp1 * 6.5), (int) (dp1 * 6.5), (int) (dp1 * 6.5), (int) (dp1 * 6.5));
            tvp.setLayoutParams(layoutParams);
            tvp.setBackgroundResource(R.drawable.textviewborder);
            tvp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);

            String txt = cursor.getString(0).toUpperCase() + "\n" + cursor.getString(1);
            SpannableString ss = new SpannableString(txt);
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            ss.setSpan(new RelativeSizeSpan(.81f), txt.indexOf("\n"), ss.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(boldSpan, 0, txt.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            tvp.setText(ss);

            LLlista.addView(tvp, 0);
        }
        dbase.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.fabAdd);
        LLlista = findViewById(R.id.product_list);

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddImageActivity.class);
                startActivity(i);
            }
        });

        dbase = openOrCreateDatabase("aditivi", MODE_PRIVATE, null);

        dbase.execSQL(
                "CREATE TABLE if not exists Emulgator (\n" +
                        "  Naziv varchar(20) NOT NULL,\n" +
                        "  poruka text DEFAULT NULL,\n" +
                        "  drugi_naziv text DEFAULT NULL\n" +
                        ");"
        );

        dbase.execSQL(
                "CREATE TABLE if not exists Proizvodi (\n" +
                        "Naziv varchar(25) NOT NULL,\n" +
                        "Info text DEFAULT NULL)");

        dbase.execSQL(
                "INSERT INTO `Emulgator` (`Naziv`, `poruka`, `drugi_naziv`) VALUES\n" +
                        "('100', 'Nije štetan', NULL),\n" +
                        "('101', 'Nije štetan', NULL),\n" +
                        "('103', 'Nije štetan', NULL),\n" +
                        "('104', 'Nije štetan', NULL),\n" +
                        "('105', 'Nije štetan', NULL),\n" +
                        "('111', 'Nije štetan', NULL),\n" +
                        "('121', 'Nije štetan', NULL),\n" +
                        "('130', 'Nije štetan', NULL),\n" +
                        "('132', 'Nije štetan', NULL),\n" +
                        "('140', 'Nije štetan', NULL),\n" +
                        "('151', 'Nije štetan', NULL),\n" +
                        "('152', 'Nije štetan', NULL),\n" +
                        "('160', 'Nije štetan', NULL),\n" +
                        "('161', 'Nije štetan', NULL),\n" +
                        "('162', 'Nije štetan', NULL),\n" +
                        "('170', 'Nije štetan', NULL),\n" +
                        "('174', 'Nije štetan', NULL),\n" +
                        "('175', 'Nije štetan', NULL),\n" +
                        "('180', 'Nije štetan', NULL),\n" +
                        "('200', 'Nije štetan', NULL),\n" +
                        "('201', 'Nije štetan', NULL),\n" +
                        "('202', 'Nije štetan', NULL),\n" +
                        "('203', 'Nije štetan', NULL),\n" +
                        "('236', 'Nije štetan', NULL),\n" +
                        "('237', 'Nije štetan', NULL),\n" +
                        "('238', 'Nije štetan', NULL),\n" +
                        "('260', 'Nije štetan', NULL),\n" +
                        "('261', 'Nije štetan, ali neka ga izbegavaju ljudi sa oštećenjem bubrega', NULL),\n" +
                        "('263', 'Nije štetan', NULL),\n" +
                        "('270', 'Nije štetan', NULL),\n" +
                        "('282', 'Nije štetan', NULL),\n" +
                        "('290', 'Nije štetan, ali povećava dejstvo alkohola', NULL),\n" +
                        "('300', 'Nije štetan', NULL),\n" +
                        "('301', 'Nije štetan', NULL),\n" +
                        "('303', 'Nije štetan', NULL),\n" +
                        "('305', 'Nije štetan', NULL),\n" +
                        "('306', 'Nije štetan', NULL),\n" +
                        "('307', 'Nije štetan', NULL),\n" +
                        "('308', 'Nije štetan', NULL),\n" +
                        "('309', 'Nije štetan', NULL),\n" +
                        "('322', 'Nije štetan', NULL),\n" +
                        "('325', 'Nije štetan, ali treba da ga izbegavaju deca netolerantna na laktozu', NULL),\n" +
                        "('326', 'Nije štetan', NULL),\n" +
                        "('327', 'Nije štetan', NULL),\n" +
                        "('331', 'Nije štetan', NULL),\n" +
                        "('332', 'Nije štetan', NULL),\n" +
                        "('333', 'Nije štetan', NULL),\n" +
                        "('334', 'Nije štetan', NULL),\n" +
                        "('335', 'Nije štetan', NULL),\n" +
                        "('336', 'Nije štetan', NULL),\n" +
                        "('337', 'Nije štetan', NULL),\n" +
                        "('382', 'Nije štetan', NULL),\n" +
                        "('400', 'Nije štetan', NULL),\n" +
                        "('401', 'Nije štetan', NULL),\n" +
                        "('402', 'Nije štetan', NULL),\n" +
                        "('403', 'Nije štetan', NULL),\n" +
                        "('404', 'Nije štetan', NULL),\n" +
                        "('405', 'Nije štetan', NULL),\n" +
                        "('406', 'Nije štetan', NULL),\n" +
                        "('408', 'Nije štetan', NULL),\n" +
                        "('410', 'Nije štetan', NULL),\n" +
                        "('411', 'Nije štetan', NULL),\n" +
                        "('413', 'Nije štetan, ali moze izazvati alergiju', NULL),\n" +
                        "('422', 'Nije štetan', NULL),\n" +
                        "('440', 'Nije štetan', NULL),\n" +
                        "('471', 'Nije štetan', NULL),\n" +
                        "('472', 'Nije štetan', NULL),\n" +
                        "('473', 'Nije štetan', NULL),\n" +
                        "('474', 'Nije štetan', NULL),\n" +
                        "('475', 'Nije štetan', NULL),\n" +
                        "('480', 'Nije štetan', NULL),\n" +
                        "('125', 'Sumnjivi dodaci', NULL),\n" +
                        "('141', 'Sumnjivi dodaci', NULL),\n" +
                        "('150', 'Sumnjivi dodaci', NULL),\n" +
                        "('153', 'Sumnjivi dodaci', NULL),\n" +
                        "('171', 'Sumnjivi dodaci', NULL),\n" +
                        "('172', 'Sumnjivi dodaci', NULL),\n" +
                        "('173', 'Sumnjivi dodaci', NULL),\n" +
                        "('240', 'Sumnjivi dodaci', NULL),\n" +
                        "('241', 'Sumnjivi dodaci', NULL),\n" +
                        "('102', 'Opasni dodaci', NULL),\n" +
                        "('110', 'Opasni dodaci', NULL),\n" +
                        "('120', 'Opasni dodaci', NULL),\n" +
                        "('124', 'Opasni dodaci', NULL),\n" +
                        "('220', 'Izaziva probleme sa crevima, uništava vitamine (posebno B1), pogotovo se ne preporučuje astmatičarima', NULL),\n" +
                        "('221', 'Izaziva probleme sa crevima', NULL),\n" +
                        "('232', 'Izaziva probleme sa crevima, kožne bolesti', NULL),\n" +
                        "('224', 'Izaziva probleme sa crevima', NULL),\n" +
                        "('338', 'Izaziva probleme sa probavom', NULL),\n" +
                        "('339', 'Izaziva probleme sa probavom i narušava ravnotežu izmedu kalcijuma i fosfora', NULL),\n" +
                        "('340', 'Izaziva probleme sa probavom', NULL),\n" +
                        "('341', 'Izaziva probleme sa probavom', NULL),\n" +
                        "('450', 'Izaziva probleme sa probavom i remeti ravnotežu vode u organizmu', NULL),\n" +
                        "('461', 'Izaziva probleme sa probavom, gasove i obstrukcija tankog creva', NULL),\n" +
                        "('463', 'Izaziva probleme sa probavom i zabranjen je u nekim zemljama', NULL),\n" +
                        "('465', 'Izaziva probleme sa probavom', NULL),\n" +
                        "('466', 'Izaziva probleme sa probavom', NULL),\n" +
                        "('407', 'Izaziva probleme sa probavom i uzukuje čir', NULL),\n" +
                        "('230', 'Izaziva kožne bolesti', NULL),\n" +
                        "('231', 'Izaziva kožne bolesti', NULL),\n" +
                        "('233', 'Izaziva kožne bolesti', NULL),\n" +
                        "('12', 'Uništava vitamine', NULL),\n" +
                        "('320', 'Povećava horesterol i utice na alergije, hiperaktivnost', NULL),\n" +
                        "('321', 'Povećava horesterol', NULL),\n" +
                        "('311', 'Nadražuje živce', NULL),\n" +
                        "('312', 'Nadražuje živce', NULL),\n" +
                        "('330', 'upala usne duplje', NULL),\n" +
                        "('131', 'Nadražuje živce', NULL),\n" +
                        "('142', 'Nadražuje živce', NULL),\n" +
                        "('210', 'Nadražuje živce, astma, neurološke teškoće, hiperaktivnost dece ', NULL),\n" +
                        "('211', 'Nadražuje živce i astma', NULL),\n" +
                        "('213', 'Nadražuje živce', NULL),\n" +
                        "('214', 'Nadražuje živce', NULL),\n" +
                        "('215', 'Nadražuje živce', NULL),\n" +
                        "('216', 'Nadražuje živce i alergije', NULL),\n" +
                        "('217', 'Nadražuje živce', NULL),\n" +
                        "('239', 'Nadražuje živce', NULL),\n" +
                        "('123', 'Vrlo otrovan te je zabranjen u SAD', NULL),\n" +
                        "('218', 'Izaziva kožne alergije', NULL),\n" +
                        "('235', 'Može izazvati mučninu, povraćanje, proliv, nadražaj kože, anoreksiju', NULL),\n" +
                        "('249', 'Može izazvati poremećaj prenosa kiseonika, glavobolje, loša koncentracija, poteškoće sa disanjem', NULL),\n" +
                        "('250', 'Potencialno kancerogen, u želucu stvara nitrozamine, hiperaktivnost dece', NULL),\n" +
                        "('251', 'U organizmu se pretvara u nitrit', NULL),\n" +
                        "('252', 'Može izazvati hiperaktivnost i malokrvnost. Potencialno kancerogen. Štetan za bubrege, zabranjen u nekim zemljama', NULL),\n" +
                        "('264', 'Može izazvati mučninu i povraćanje', NULL),\n" +
                        "('280', 'Uzrokuje migrene', NULL),\n" +
                        "('281', 'Uzrokuje migrene', NULL),\n" +
                        "('302', 'Uzrukuje stvaranje kamena u buregu', NULL),\n" +
                        "('310', 'Nadražuje želudac i kožu', NULL),\n" +
                        "('319', 'Može izazvati mučninu, povraćanje, delirijum. Smrtonosna doza je 5g', NULL),\n" +
                        "('363', 'Zabranjen u nekim zemljama', NULL),\n" +
                        "('370', 'Zabranjen u nekim zemljama', NULL),\n" +
                        "('375', 'Loš za jetru, podize nivo mokracne kiseline, gastritis', NULL),\n" +
                        "('380', 'Loš za jetru i sluznicu zeluca', NULL),\n" +
                        "('385', 'Zabranjen u nekim zemljama', NULL),\n" +
                        "('412', 'Može izazvati mučninu i grčeve u stomaku. Snižava nivo holesterola', 'guar'),\n" +
                        "('414', 'Nadražuje sluznice i alergen je', NULL),\n" +
                        "('415', 'Izaziva respiratorne probleme', 'antan'),\n" +
                        "('416', 'Može izazvati alergije', NULL),\n" +
                        "('420', 'Loš za zeludac, nije dozvoljen za decju prehranu', NULL),\n" +
                        "('421', 'Može izazvati proliv, mučninu i povraćanje. Loš je za bubrege', NULL),\n" +
                        "('432', 'Zabranjen je u nekim zemljama', NULL),\n" +
                        "('503', 'Nadražuje sluznicu', NULL),\n" +
                        "('508', 'Izaziva probleme sa zelucem', NULL),\n" +
                        "('510', 'Treba da ga izbegavaju ljudi s bolesnim bubrezima i jetrom', NULL),\n" +
                        "('513', 'Zabranjen je u nekim zemljama', NULL),\n" +
                        "('514', 'Remeti ravnotežu vode u organizmu', NULL),\n" +
                        "('553', 'Može izazvati rak zeluca', NULL),\n" +
                        "('554', 'Može izazvati teškoće s placentom kod trudnoće i alchajmerovu bolest', NULL),\n" +
                        "('620', 'Treba da ga izbegavju deca', NULL),\n" +
                        "('621', 'Može izazvati glavobolju, žeđ, vrtoglavicu, grčeve u stomaku, proliv i aritmiju. Zabranjen je u proizvodima za decu', NULL),\n" +
                        "('622', 'mucnina, povracanje, proliv', NULL),\n" +
                        "('626', 'moze uzrokovati giht', NULL),\n" +
                        "('627', 'moze uzrokovati giht, zabranjen u prizvodima za decu', NULL),\n" +
                        "('629', 'moze uzrokovati giht', NULL),\n" +
                        "('631', 'moze uzrokovati giht', NULL),\n" +
                        "('633', 'moze uzorkovati giht', NULL),\n" +
                        "('635', 'moze izazvati promene na kozi, zabranjen u mnogobrojnim zemljama', NULL),\n" +
                        "('905', 'moguc rak pobavnog trakta', NULL),\n" +
                        "('907', 'zabranjen u nekim zemljama', NULL),\n" +
                        "('924', 'mučnina, povracanje, proliv', NULL),\n" +
                        "('925', 'kancerogen, unistava biolosku vrednost hrane', NULL),\n" +
                        "('927', 'zabranjen u nekim zemljama', NULL),\n" +
                        "('950', 'spada medu najopasnije aditive, lose utice na stitnu zlezdu', NULL),\n" +
                        "('951', 'alergije, migrene, vrtoglavica, smetnje kod vida, sluha i ukusa, remeti pamcenje, depresija, uzrocnik nekih degenerativnih bolesti (multipla skleroza, parkinsonova bolest), hormonski poremecaji', NULL),\n" +
                        "('952', 'migrene, potencialno kancerogen, zabranjen u SAD i Engleskoj', NULL),\n" +
                        "('954', 'izaziva rak kod zivotinja', NULL),\n" +
                        "('967', 'kamen u bubregu, diuretik', NULL),\n" +
                        "('1505', 'metabolise se u etanol', NULL),\n" +
                        "('1520', 'moze da izazove infarkt, lose utice na nervni sistem, dermatitis', NULL);");

        dbase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainactmenu, menu);
        clearbtn = menu.findItem(R.id.clear_btn);


        clearbtn.setOnMenuItemClickListener(item -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Da li sigurno želite da obrišete sve sačuvane proizvode?");

            builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbase = SQLiteDatabase.openDatabase(dbase.getPath(), null, 0);
                    String query = "DELETE FROM Proizvodi";
                    dbase.execSQL(query);
                    dbase.close();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        });
        return true;
    }
}
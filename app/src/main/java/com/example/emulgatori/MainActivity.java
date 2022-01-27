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
                        "('100', 'Nije štetan', 'kurkumin'),\n" +
                        "('101', 'Nije štetan', 'riboflavin'),\n" +
                        "('103', 'Nije štetan', NULL),\n" +
                        "('104', 'Nije štetan', 'hinolin'),\n" +
                        "('105', 'Nije štetan', NULL),\n" +
                        "('111', 'Nije štetan', NULL),\n" +
                        "('121', 'Nije štetan', NULL),\n" +
                        "('130', 'Nije štetan', NULL),\n" +
                        "('132', 'Nije štetan', 'indigotin'),\n" +
                        "('140', 'Nije štetan', 'hlorofili'),\n" +
                        "('151', 'Nije štetan', 'brilijant'),\n" +
                        "('152', 'Nije štetan', NULL),\n" +
                        "('160', 'Nije štetan', NULL),\n" +
                        "('161', 'Nije štetan', NULL),\n" +
                        "('162', 'Nije štetan', 'betanin'),\n" +
                        "('170', 'Nije štetan', 'kalcijum-karbonat'),\n" +
                        "('174', 'Nije štetan', 'srebro'),\n" +
                        "('175', 'Nije štetan', 'zlato'),\n" +
                        "('180', 'Nije štetan', 'litolrubin'),\n" +
                        "('200', 'Nije štetan', 'sorbin'),\n" +
                        "('201', 'Nije štetan', NULL),\n" +
                        "('202', 'Nije štetan', 'kalijum-sorbat'),\n" +
                        "('203', 'Nije štetan', 'kalcijum-sorbat'),\n" +
                        "('236', 'Nije štetan', NULL),\n" +
                        "('237', 'Nije štetan', NULL),\n" +
                        "('238', 'Nije štetan', NULL),\n" +
                        "('260', 'Nije štetan', 'sircetn'),\n" +
                        "('261', 'Nije štetan, ali neka ga izbegavaju ljudi sa ostecenjem bubrega', 'kalijum-acetat'),\n" +
                        "('263', 'Nije štetan', 'kalcijum-acetat'),\n" +
                        "('270', 'Nije štetan', 'mlecna'),\n" +
                        "('282', 'Nije štetan', 'kalcijum-propionat'),\n" +
                        "('290', 'Nije štetan, ali povecava dejstvo alkohola', 'ugljen-dioksid'),\n" +
                        "('300', 'Nije štetan', 'askorbin'),\n" +
                        "('301', 'Nije štetan', 'natrijum-askorbat'),\n" +
                        "('303', 'Nije štetan', NULL),\n" +
                        "('305', 'Nije štetan', NULL),\n" +
                        "('306', 'Nije štetan', 'tokoferol'),\n" +
                        "('307', 'Nije štetan', 'alfa-tokoferol'),\n" +
                        "('308', 'Nije štetan', 'gama-tokoferol'),\n" +
                        "('309', 'Nije štetan', 'delta-tokoferol'),\n" +
                        "('322', 'Nije štetan', 'lecitin'),\n" +
                        "('325', 'Nije štetan, ali treba da ga izbegavaju deca netolerantna na laktozu', 'natrijum-laktat'),\n" +
                        "('326', 'Nije štetan', 'kalijum-laktat'),\n" +
                        "('327', 'Nije štetan', 'kalcijum-laktat'),\n" +
                        "('331', 'Nije štetan', 'natrijum-citrat'),\n" +
                        "('332', 'Nije štetan', 'monokalijum-citrat'),\n" +
                        "('333', 'Nije štetan', 'monokalcijum-citrat'),\n" +
                        "('334', 'Nije štetan', 'vinsk'),\n" +
                        "('335', 'Nije štetan', 'mononatrijum-tartarat'),\n" +
                        "('336', 'Nije štetan', 'monokalijum-tartarat'),\n" +
                        "('337', 'Nije štetan', 'kalijum-natrijum'),\n" +
                        "('382', 'Nije štetan', NULL),\n" +
                        "('400', 'Nije štetan', 'alginsk'),\n" +
                        "('401', 'Nije štetan', 'natrijum-alginat'),\n" +
                        "('402', 'Nije štetan', 'kalijum-alginat'),\n" +
                        "('403', 'Nije štetan', 'amonijum-alginat'),\n" +
                        "('404', 'Nije štetan', 'kalcijum-alginat'),\n" +
                        "('405', 'Nije štetan', 'diolalginat'),\n" +
                        "('406', 'Nije štetan', 'agar'),\n" +
                        "('408', 'Nije štetan', NULL),\n" +
                        "('410', 'Nije štetan', NULL),\n" +
                        "('411', 'Nije štetan', NULL),\n" +
                        "('413', 'Nije štetan, ali može izazvati alergiju', 'tragakant'),\n" +
                        "('422', 'Nije štetan', 'glicerol'),\n" +
                        "('440', 'Nije štetan', 'pektin'),\n" +
                        "('471', 'Nije štetan', 'diglicerid'),\n" +
                        "('472', 'Nije štetan', NULL),\n" +
                        "('473', 'Nije štetan', 'estri'),\n" +
                        "('474', 'Nije štetan', 'saharozoglicerid'),\n" +
                        "('475', 'Nije štetan', 'poliglicerol'),\n" +
                        "('480', 'Nije štetan', NULL),\n" +
                        "('125', 'Sumnjivi dodaci', NULL),\n" +
                        "('141', 'Sumnjivi dodaci', 'bakarni'),\n" +
                        "('150', 'Sumnjivi dodaci', NULL),\n" +
                        "('153', 'Sumnjivi dodaci', 'ugalj'),\n" +
                        "('171', 'Sumnjivi dodaci', 'titan-dioksid'),\n" +
                        "('172', 'Sumnjivi dodaci', 'hidroksid'),\n" +
                        "('173', 'Sumnjivi dodaci', 'aluminijum'),\n" +
                        "('240', 'Sumnjivi dodaci', NULL),\n" +
                        "('241', 'Sumnjivi dodaci', NULL),\n" +
                        "('102', 'Opasni dodaci', 'tartrazin'),\n" +
                        "('110', 'Opasni dodaci', 'sunset'),\n" +
                        "('120', 'Opasni dodaci', 'kosenil'),\n" +
                        "('124', 'Opasni dodaci', 'ponso'),\n" +
                        "('220', 'Izaziva probleme sa crevima, unistava vitamine (posebno b1), pogotovo se ne preporucuje astmaticarima', 'sumpor-dioksid'),\n" +
                        "('221', 'Izaziva probleme sa crevima', 'natrijum-sulfit'),\n" +
                        "('232', 'Izaziva probleme sa crevima, kozne bolesti', NULL),\n" +
                        "('224', 'Izaziva probleme sa crevima', 'metabisulfit'),\n" +
                        "('338', 'Izaziva probleme sa probavom', 'fosforna'),\n" +
                        "('339', 'Izaziva probleme sa probavom i narusava ravnotezu izmedu kalcijuma i fosfora', 'mononatrijum-fosfat'),\n" +
                        "('340', 'Izaziva probleme sa probavom', 'monokalijum-fosfat'),\n" +
                        "('341', 'Izaziva probleme sa probavom', 'monokalcijum-fosfat'),\n" +
                        "('450', 'Izaziva probleme sa probavom i remeti ravnotezu vode u organizmu', 'dinatrijum-difosfat'),\n" +
                        "('461', 'Izaziva probleme sa probavom, gasove i obstrukcija tankog creva', 'metilceluloza'),\n" +
                        "('463', 'Izaziva probleme sa probavom i zabranjen je u nekim zemljama', 'hidroksipropil'),\n" +
                        "('465', 'Izaziva probleme sa probavom', 'etilmetil'),\n" +
                        "('466', 'Izaziva probleme sa probavom', 'karboksimetilceluloza'),\n" +
                        "('407', 'Izaziva probleme sa probavom i uzukuje cir', 'karagenan'),\n" +
                        "('230', 'Izaziva kozne bolesti', NULL),\n" +
                        "('231', 'Izaziva kozne bolesti', NULL),\n" +
                        "('233', 'Izaziva kozne bolesti', NULL),\n" +
                        "('12', 'Uništava vitamine', NULL),\n" +
                        "('320', 'Povećava horesterol i utice na alergije, hiperaktivnost', 'butilhidroksianizol'),\n" +
                        "('321', 'Povećava horesterol', 'butilhidroksitoluen'),\n" +
                        "('311', 'Nadražuje živce', 'oktilgalat'),\n" +
                        "('312', 'Nadražuje živce', 'dodecilgalat'),\n" +
                        "('330', 'Izaziva upalu usne duplje', 'limunsk'),\n" +
                        "('131', 'Može nadražiti živce', 'patent'),\n" +
                        "('142', 'Može nadražiti živce', 'zelena'),\n" +
                        "('210', 'Može nadražiti živce, astma, neuroloske teskoce, hiperaktivnost dece ', 'benzojev'),\n" +
                        "('211', 'Može nadražiti živce i astma', 'natrijum-benzoat'),\n" +
                        "('213', 'Može nadražiti živce', 'kalcijum-benzoat'),\n" +
                        "('214', 'Može nadražiti živce', 'etil-r-hidroksibenzoat'),\n" +
                        "('215', 'Može nadražiti živce', 'natrijum-etil-r-hidroksibenzoat'),\n" +
                        "('216', 'Može nadražiti živce i alergije', 'propil-r-hidroksibenzoat'),\n" +
                        "('217', 'Može nadražiti živce', 'natrijum-propil-r-hidroksibenzoat'),\n" +
                        "('239', 'Može nadražiti živce', 'heksametilentetramin'),\n" +
                        "('123', 'Vrlo je otrovan, te je zabranjen u SAD', 'amarant'),\n" +
                        "('218', 'Izaziva kožne alergije', 'metil-r-hidroksibenzoat'),\n" +
                        "('235', 'Može izazvati mučninu, povraćanje, proliv, nadražaj kože i anoreksiju', 'natamicin'),\n" +
                        "('249', 'Može izazvati poremecaj prenosa kiseonika, glavobolje, losa koncentracija, poteskoce sa disanjem', 'kalijum-nitrit'),\n" +
                        "('250', 'potencialno kancerogen, u zelucu stvara nitrozamine, hiperaktivnost dece', 'natrijum-nitrit'),\n" +
                        "('251', 'U organizmu se pretvara u nitrit', 'natrijum-nitrat'),\n" +
                        "('252', 'Može izazvati hiperaktivnost i malokrvnost. potencialno kancerogen. stetan za bubrege, zabranjen u nekim zemljama', 'kalijum-nitrat'),\n" +
                        "('264', 'Može izazvati mucninu i povracanje', NULL),\n" +
                        "('280', 'Uzrokuje migrene', 'propionsk'),\n" +
                        "('281', 'Uzrokuje migrene', 'natrijum-propionat'),\n" +
                        "('302', 'Uzrukuje stvaranje kamena u buregu', 'kalcijum-askorbat'),\n" +
                        "('310', 'Nadražuje želudac i kožu', 'propilgalat'),\n" +
                        "('319', 'Može izazvati mucninu, povracanje, delirijum. Smrtonosna doza je 5g', NULL),\n" +
                        "('363', 'Zabranjen je u nekim zemljama', 'cilibarn'),\n" +
                        "('370', 'Zabranjen u nekim zemljama', NULL),\n" +
                        "('375', 'Loš za jetru, podize nivo mokracne kiseline, gastritis', NULL),\n" +
                        "('380', 'Loš za jetru i sluznicu zeluca', 'triamonijum-citrat'),\n" +
                        "('385', 'Zabranjen je u nekim zemljama', 'etilendiamintetraacetat'),\n" +
                        "('412', 'Može izazvati mucninu i grceve u stomaku. snižava nivo holesterola', 'guar'),\n" +
                        "('414', 'nadrazuje sluznice i alergen je', 'akacij'),\n" +
                        "('415', 'Može izazivati respiratorne probleme', 'antan'),\n"+
                        "('416', 'Može izazvati alergije', 'karaj'),\n" +
                        "('420', 'Loš za zeludac, nije dozvoljen za decju prehranu', 'sorbitol'),\n" +
                        "('421', 'Može izazvati proliv, mučninu i povraćanje. Loš je za bubrege', 'manitol'),\n" +
                        "('432', 'Zabranjen je u nekim zemljama', 'polioksietilensorbitanmonolaurat'),\n" +
                        "('503', 'Nadražuje sluznicu', 'amonijum-karbonat'),\n" +
                        "('508', 'Izaziva probleme sa želucem', 'kalijum-hlorid'),\n" +
                        "('510', 'Treba da ga izbegavaju ljudi s bolesnim bubrezima i jetrom', NULL),\n" +
                        "('513', 'Zbranjen je u nekim zemljama', 'sumporn'),\n" +
                        "('514', 'Remeti ravnotezu vode u organizmu', 'natrijum-sulfat'),\n" +
                        "('553', 'Može izazvati rak zeluca', NULL),\n" +
                        "('554', 'Može izazvati teskoce s placentom kod trudnoce i alchajmerovu bolest', 'silikat'),\n" +
                        "('620', 'Treba da ga izbegavju deca', 'glutaminsk'),\n" +
                        "('621', 'Može izazvati glavobolju, zeđ, vrtoglavicu, grčeve u stomaku, proliv i aritmiju. Zabranjen je u proizvodima za decu', 'mononatrijum-glutaminat'),\n" +
                        "('622', 'Može izazvati mucninu, povracanje, proliv', 'monokalijum-glutaminat'),\n" +
                        "('626', 'Može uzrokovati giht', 'guaniln'),\n" +
                        "('627', 'Može uzrokovati giht, zabranjen u prizvodima za decu', 'dinatrijum-guanilat'),\n" +
                        "('629', 'Može uzrokovati giht', 'kalcijum-guanilat'),\n" +
                        "('631', 'Može uzrokovati giht', 'dinatrijum-inozitat'),\n" +
                        "('633', 'Može uzorkovati giht', 'kalcijum-inozitat'),\n" +
                        "('635', 'Može izazvati promene na kozi, zabranjen u mnogobrojnim zemljama', 'ribonukleotidi'),\n" +
                        "('905', 'Izaziva rak pobavnog trakta', 'mikrokristal'),\n" +
                        "('907', 'Zabranjen je u nekim zemljama', 'poli-1-decen'),\n" +
                        "('924', 'Izaziva mučninu, povracanje, proliv', NULL),\n" +
                        "('925', 'Kancerogen, unistava biolosku vrednost hrane', NULL),\n" +
                        "('927', 'Zabranjen u nekim zemljama', NULL),\n" +
                        "('950', 'Spada medu najopasnije aditive, loše utice na stitnu zlezdu', 'acesulfam'),\n" +
                        "('951', 'Izaziva alergije, migrene, vrtoglavicu, smetnje kod vida, sluha i ukusa. Remeti pamcenje, depresija, uzrocnik nekih degenerativnih bolesti (multipla skleroza, parkinsonova bolest), hormonski poremecaji', 'aspartam'),\n" +
                        "('952', 'Izaziva migrene. Potencialno je kancerogen. Zabranjen je u SAD i Engleskoj', 'ciklamsk'),\n" +
                        "('954', 'Izaziva rak kod životinja', 'saharin'),\n" +
                        "('967', 'Izaziva kamen u bubregu, diuretik je', 'ksilitol'),\n" +
                        "('1505', 'Metaboliše se u etanol', 'trietilcitrat'),\n" +
                        "('1520', 'Može da izazove infarkt, loše utice na nervni sistem, dermatitis', 'aluminijum-sulfat');");

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
package com.example.emulgatori;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Telephony;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;
import java.util.Arrays;

enum IntentCode{galerija, kamera}

public class AddImageActivity extends AppCompatActivity {

    final int READ_EXTERNAL_STORAGE = 100;
    Uri urislike;
    IntentCode intentCode; //Za odredjivanje da li se slika preuzima iz galerije ili kamere

    ImageView imageView;
    Bitmap photo;
    FloatingActionButton camFab; //Dugme za otvaranje kamere
    FloatingActionButton attachFab; //Dugme za otvaranje galerije

    MenuItem doneBtn, cancelBtn; //Dugmad u meni-u na vrhu
    ImageButton leftRotBtn, rightRotBtn, flipXBtn, flipYBtn, cropBtn; //Dugmad u toolbaru

    Intent pickImageIntent; //Intent za preuzimanje slike iz galerije
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_image_activity);

        camFab = findViewById(R.id.fabCamera);
        attachFab = findViewById(R.id.fabAttach);
        imageView = findViewById(R.id.img);
        leftRotBtn = findViewById(R.id.rotate_left_btn);
        rightRotBtn = findViewById(R.id.rotate_right_btn);
        flipXBtn = findViewById(R.id.flipx_btn);
        flipYBtn = findViewById(R.id.flipy_btn);
        cropBtn = findViewById(R.id.crop_btn);


        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);//DIMENZIJA EKRANA
        attachFab.setX(0.22f * size.x);

        pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //Intent za otvaranje galerije
        sharedPref = AddImageActivity.this.getSharedPreferences(getString(R.string.values_file_key), Context.MODE_PRIVATE);//Cuvanje podataka u memoriji, u key-value formatu

        camFab.setOnClickListener(v -> {
            intentCode = IntentCode.kamera;
            Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            File image = new File(getFilesDir(), "slika.png");

            urislike = FileProvider.getUriForFile(
                    getApplicationContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    image);

            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, urislike);
            StartForResult.launch(imageIntent);
        });

        attachFab.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                intentCode = IntentCode.galerija;
                StartForResult.launch(pickImageIntent); //POKRETANJE PRETRAGE SLIKA, AKO JE DATA DOZVOLA
            }
            else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
                askForPermissionDialog("Potrebna dozvola", getString(R.string.read_memory_perm_message), Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, false);
            else {
                //Ako korisnik nikad nije odbio premisiju - otvoriti dijalog, u suprotnom je odbio vise od 2 puta
                // pa se dozvola mora dati korz podesavanja
                if(sharedPref.getInt("BrojOdbijanjaReadPermisije", 0) == 0) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                }
                else
                    askForPermissionDialog("Potrebna dozvola", getString(R.string.read_memory_perm_message), Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, true);
            }
        });

        leftRotBtn.setOnClickListener(l -> {
            photo = rotateBitmap(photo, -90);
            imageView.setImageBitmap(photo);

        });

        rightRotBtn.setOnClickListener(l -> {
            photo = rotateBitmap(photo, 90);
            imageView.setImageBitmap(photo);
        });

        flipXBtn.setOnClickListener(l -> {
            photo = flipBitmap(photo, true, false);
            imageView.setImageBitmap(photo);

        });

        flipYBtn.setOnClickListener(l -> {
            photo = flipBitmap(photo, false, true);
            imageView.setImageBitmap(photo);
        });

        cropBtn.setOnClickListener(l -> {

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        cancelBtn = menu.findItem(R.id.cancel_btn);
        doneBtn = menu.findItem(R.id.done_btn);
        leftRotBtn = findViewById(R.id.rotate_left_btn);
        rightRotBtn = findViewById(R.id.rotate_right_btn);
        flipXBtn = findViewById(R.id.flipx_btn);
        flipYBtn = findViewById(R.id.flipy_btn);


        cancelBtn.setOnMenuItemClickListener(item -> {
            finish();//Vraca se u MainActivity
            return true;
        });


        doneBtn.setOnMenuItemClickListener(item -> {
            FirebaseVisionImage fvimage = FirebaseVisionImage.fromBitmap(photo);

            FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                    .getOnDeviceTextRecognizer();

            textRecognizer.processImage(fvimage)
                    .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                        @Override
                        public void onSuccess(FirebaseVisionText result) {
                            String text = result.getText();
                            String rezultat;
                            //TODO: Pretrega baze kljucnih reci

                            Toast toast = Toast.makeText(AddImageActivity.this, text, Toast.LENGTH_LONG);
                            toast.show();

                        }
                    })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast errorToast = Toast.makeText(AddImageActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                                    errorToast.show();
                                }
                            });
            return true;
        });

        return super.onCreateOptionsMenu(menu);
    }

    ActivityResultLauncher<Intent> StartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        switch(intentCode){
                            case galerija: //U slucaju da se slika preuzima iz galerije
                                Uri selectedImage = result.getData().getData();
                                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                cursor.close();
                                photo = BitmapFactory.decodeFile(picturePath);
                                break;
                            case kamera: //U slucaju da se slika preuzima sa kamere
                                //photo = (Bitmap)result.getData().getExtras().get("data");
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                                photo = BitmapFactory.decodeFile(getFilesDir()+ "/slika.png", options);
                                break;
                            default: return;
                        }

                        imageView.setImageBitmap(photo);
                        doneBtn.setVisible(true);
                        findViewById(R.id.tbar).setVisibility(View.VISIBLE);

                        Toast toast = Toast.makeText(AddImageActivity.this, "Rotirajte sliku tako da bude uspravna, ako već nije", Toast.LENGTH_LONG);
                        toast.show();
                        //TODO -> BITMAPA SE (CROPUJE) i PROSLEEDJUJE U OPENCV/TESSERACT...


                    }
                }
            });

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) //Poziva se nakon odgovora na dijalog za zahtev dozvole
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == READ_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            intentCode = IntentCode.galerija;
            StartForResult.launch(pickImageIntent);
        }
        else if(requestCode == READ_EXTERNAL_STORAGE)
        {
            int p = sharedPref.getInt("BrojOdbijanjaReadPermisije", 0)+1;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("BrojOdbijanjaReadPermisije", p); //broj odbijanja pristupa memoriji +1
            editor.apply();
        }
        else if(requestCode == READ_EXTERNAL_STORAGE+1)
        {
            int p = sharedPref.getInt("BrojOdbijanjaManPermisije", 0)+1;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("BrojOdbijanjaManPermisije", p); //broj odbijanja manage memoriji +1
            editor.apply();
        }
    }

    /**
     @param inSettings Da li ce funkcija pokusati da otvori dijalog za davanje dozvole, ili ce otvoriti settings
     */
    private void askForPermissionDialog(String title, String message, final String permission, final int permissionRequestCode, boolean inSettings) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    if(inSettings)
                    {   //Otvaranje podesavanja
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else
                        requestPermissions(new String[]{permission}, permissionRequestCode);
                })
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> {
                });
        builder.create().show();
    }

    public static Bitmap rotateBitmap(Bitmap bm, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
    }

    public static Bitmap flipBitmap(Bitmap bm, boolean x, boolean y)
    {
        Matrix matrix = new Matrix();
        matrix.postScale(x ? -1 : 1, y ? -1 : 1, bm.getWidth() / 2f, bm.getHeight() / 2f);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
    }
}
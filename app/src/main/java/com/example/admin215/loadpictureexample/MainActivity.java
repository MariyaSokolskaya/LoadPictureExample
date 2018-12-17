package com.example.admin215.loadpictureexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String[] imageUrls ={ "http://srd-maestro.ru/content/catalog/75_south-landscape/1/fo3127.jpg",
            "http://img1.postila.ru/storage/6368000/6341379/48491bc488f1a3158b64913acd14a559.jpg",
            "http://is5.mzstatic.com/image/thumb/Music6/v4/1e/e4/57/1ee457b0-e296-a959-a424-56e2c5502ee0/source/100x100bb.jpg",
            "http://wallpaperscraft.ru/image/oblaka_pasmurno_nebo_tuchi_hmuroe_seroe_kusty_leto_zelen_55744_300x175.jpg"};

    Button loadButton;
    ProgressBar progressBar;
    TableRow imagesRow;
    ArrayList<ImageView> imageView = new ArrayList<ImageView>();
    AsyncImagesLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadButton = (Button) findViewById(R.id.button1);
        progressBar = (ProgressBar) findViewById(R.id.loading_progress);
        imagesRow = (TableRow) findViewById(R.id.images);
//        for (int i = 0; i <imageUrls.length ; i++) {
//            try {
//                Bitmap image = getImageByUrl(imageUrls[i]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        setUpViews();
        if(loader == null || loader.getStatus() == AsyncTask.Status.FINISHED){
            loader = new AsyncImagesLoader();
            loader.execute(imageUrls);
            progressBar.setProgress(0);
        }else
            Toast.makeText(this,"Ждите завершения загрузки", Toast.LENGTH_LONG).show();
    }

    private void setUpViews(){
        for (int i = 0; i < imagesRow.getChildCount(); i++) {
            imageView.add((ImageView) imagesRow.getChildAt(i));
        }
    }
    private Bitmap getImageByUrl(String url) throws MalformedURLException, IOException  {
        Bitmap image = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        return image;
    }
}

class  AsyncImagesLoader extends AsyncTask<String, Pair<Integer, Bitmap>, Void>{
    @Override
    protected Void doInBackground(String... params) {
        return null;
    }
}
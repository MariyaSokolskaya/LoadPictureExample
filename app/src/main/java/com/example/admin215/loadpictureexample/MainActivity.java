package com.example.admin215.loadpictureexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
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
        setUpViews();
    }

    private void setUpViews(){
        for (int i = 0; i < imagesRow.getChildCount(); i++) {
            imageView.add((ImageView) imagesRow.getChildAt(i));
        }
    }
    protected void startLoading(View v){
        if(loader == null || loader.getStatus() == AsyncTask.Status.FINISHED){
            loader = new AsyncImagesLoader();
            loader.execute(imageUrls);
            progressBar.setProgress(0);
        }else
            Toast.makeText(this,"Ждите завершения загрузки", Toast.LENGTH_LONG).show();
    }

    class  AsyncImagesLoader extends AsyncTask<String, Pair<Integer, Bitmap>, Void>{
        @Override
        protected Void doInBackground(String... params) {
            for (int i = 0; i < params.length; i++) {
                try {
                    Bitmap image = getImageByUrl(params[i]);
                    Thread.sleep(400);
                    Pair pair = new Pair(i, image);
                    publishProgress(pair);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    //Toast.makeText(getApplicationContext(),"Не могу загрузить картинку №" + Integer.valueOf(i).toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Загрузка завершена", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Pair<Integer, Bitmap>... values) {
            int position = values[0].first;
            int currentPosition = position + 1;
            progressBar.setProgress(currentPosition*100);
            Bitmap image = values[0].second;
            imageView.get(position).setImageBitmap(image);
            ((ImageView)imagesRow.getChildAt(position)).setImageBitmap(image);
            super.onProgressUpdate(values);
        }

        private Bitmap getImageByUrl(String url) throws MalformedURLException, IOException {
            Bitmap image = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            return image;
        }
    }
}


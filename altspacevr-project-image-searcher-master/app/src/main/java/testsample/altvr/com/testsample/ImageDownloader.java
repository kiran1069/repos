package testsample.altvr.com.testsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import testsample.altvr.com.testsample.util.LogUtil;

/**
 * Created by kiran on 9/3/2016.
 */
public class ImageDownloader extends AsyncTask<Void, Void, Void> {

    private ImageView img;
    private String mUrl;
    private Bitmap mBitmap;

    public ImageDownloader(ImageView imageView, String url) {
        img = imageView;
        mUrl = url;
    }

    @Override
    protected  void onPostExecute(Void result) {
        super.onPostExecute(result);

        img.setImageBitmap(mBitmap);

    }
    @Override
    protected Void doInBackground(Void... params) {
        HttpURLConnection connection = null;
        try {
            URL  url = new URL(mUrl);


                connection = (HttpURLConnection) url.openConnection();

                LogUtil.log(" Pas One");
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setInstanceFollowRedirects(true);

            LogUtil.log(" Pas TWO");
            connection.setDoInput(true);

            LogUtil.log(" Pas THREE");

            LogUtil.log(" Pas FOur "+connection.getResponseCode());
            InputStream input = connection.getInputStream();
            LogUtil.log(" Pas Five");
            mBitmap = BitmapFactory.decodeStream(input);
            LogUtil.log(" Bitmap "+mBitmap + " -- "+mUrl);
        } catch (MalformedURLException e) {
            LogUtil.log(" EXCEPTION 1 "+e.getCause());
            e.printStackTrace();
        } catch (IOException e) {
            LogUtil.log(" EXCEPTION 2 "+e.getCause());
        e.printStackTrace();
        }
        return null;
    }
}

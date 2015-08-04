package fieldeducation.fashioninsta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageActivity extends Activity {

    Bitmap bitmapSample1 = null;
    ImageView  imgSample1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ViewFlipper filpper = new ViewFlipper(this);
        imgSample1 = (ImageView)findViewById(R.id.imageView);
     //   Bitmap bitmapSample1 = null;
        new Thread(new Runnable() {
            public void run() {
                try {
                    bitmapSample1 = getBitmap("https://scontent.cdninstagram.com/hphotos-xfa1/t51.2885-15/e15/11356511_769373729845800_388551311_n.jpg");
                }catch(Exception e) {

                }finally {
                    if(bitmapSample1!=null) {
                        runOnUiThread(new Runnable() {
                            @SuppressLint("NewApi")
                            public void run() {
                                imgSample1.setImageBitmap(bitmapSample1);
                            }
                        });
                    }
                }
            }
        }).start();



    }

    private Bitmap getBitmap(String url) {
        URL imgUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;

        Bitmap retBitmap = null;

        try{
            imgUrl = new URL(url);
            connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true); //url로 input받는 flag 허용
            connection.connect(); //연결
            is = connection.getInputStream(); // get inputstream
            retBitmap = BitmapFactory.decodeStream(is);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(connection!=null) {
                connection.disconnect();
            }
            return retBitmap;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
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
}

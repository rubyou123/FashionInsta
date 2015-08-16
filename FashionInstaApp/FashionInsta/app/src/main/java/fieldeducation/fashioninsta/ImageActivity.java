package fieldeducation.fashioninsta;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ImageActivity extends Activity {

    Bitmap bitmapSample1 = null;
    ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
    ArrayList<ImageView> imageList = new ArrayList<ImageView>();
    ImageView  imgSample1 = null;
    ArrayList<String> urlList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView imgView = (ImageView)findViewById(R.id.imageView1);
  //      bitmapSample1 = getBitmapFromURL("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/s640x640/e15/11410530_927043480690497_313092714_n.jpg");
      //  imgView.setImageBitmap(bitmapSample1);
    }
    public Bitmap getBitmapFromURL(String src) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(src);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
            if(connection!=null)connection.disconnect();
        }
    }


 /*   private Drawable LoadImageFromWebOperation(String url)
    {
        try
        {
            InputStream is= (InputStream)new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }
        catch (Exception e)
        {
            System.out.println("Exc=" +e);
            return null;
        }
    }


    public void setImageBitmapList()
    {
        for(int i=0; i<20; i++)
        {
            imageList.get(i).setImageBitmap(bitmapList.get(i));
        }
    }

    public void getBitmapList() throws Exception
    {
        for(int i=0; i<20; i++) {
            bitmapList.add(getBitmap(urlList.get(i)));
        }
    }

    public void getImageViewList()
    {

        imageList.add((ImageView)findViewById(R.id.imageView1));
        imageList.add((ImageView)findViewById(R.id.imageView2));
        imageList.add((ImageView)findViewById(R.id.imageView3));
        imageList.add((ImageView)findViewById(R.id.imageView4));
        imageList.add((ImageView)findViewById(R.id.imageView5));
        imageList.add((ImageView)findViewById(R.id.imageView6));
        imageList.add((ImageView)findViewById(R.id.imageView7));
        imageList.add((ImageView)findViewById(R.id.imageView8));
        imageList.add((ImageView)findViewById(R.id.imageView9));

        imageList.add((ImageView)findViewById(R.id.imageView10));
        imageList.add((ImageView)findViewById(R.id.imageView11));
        imageList.add((ImageView)findViewById(R.id.imageView12));
        imageList.add((ImageView)findViewById(R.id.imageView13));
        imageList.add((ImageView)findViewById(R.id.imageView14));
        imageList.add((ImageView)findViewById(R.id.imageView15));
        imageList.add((ImageView)findViewById(R.id.imageView16));
        imageList.add((ImageView)findViewById(R.id.imageView17));
        imageList.add((ImageView)findViewById(R.id.imageView18));
        imageList.add((ImageView)findViewById(R.id.imageView19));
        imageList.add((ImageView)findViewById(R.id.imageView20));




    }



    public void insertURLList()
    {
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/s640x640/e15/11410530_927043480690497_313092714_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/s640x640/e35/sh0.08/11378754_862572260492770_362658869_n.jpg\n");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xpt1/t51.2885-15/s640x640/e15/10268726_1598933580355885_688807085_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xfa1/t51.2885-15/s640x640/e35/sh0.08/11417270_405619106288574_321407231_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xap1/t51.2885-15/s640x640/e35/sh0.08/10296712_701490803316859_569795603_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xap1/t51.2885-15/s640x640/e35/sh0.08/10661148_376634665880467_1233486719_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/s640x640/e15/11376687_1012831738749852_1634875894_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xfa1/t51.2885-15/s640x640/e35/sh0.08/11377526_858580890896358_60260536_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/s640x640/e15/11374623_907224266002369_2083328859_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/e15/11377540_1616728231928431_1391961854_n.jpg");


        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/s640x640/e35/sh0.08/11357583_1003995472966311_205060305_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/s640x640/e35/sh0.08/11378334_374528892757435_276823374_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xfa1/t51.2885-15/s640x640/e35/sh0.08/11430212_429595780558711_1153594598_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/s640x640/e35/sh0.08/11379769_844237188958516_1579608901_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/e15/11335044_1656016507950884_881980715_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/e15/11410374_927118143997349_36706528_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/e15/11377417_483341028498269_1837645629_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xfa1/t51.2885-15/e15/11379185_388667737989027_859083637_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/e15/11383309_118626698472673_1749309792_n.jpg");
        urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/e15/11378340_1609642672653551_507852969_n.jpg");

    }

    public void printImageView()
    {
        imgSample1 = (ImageView)findViewById(R.id.imageView1);
        new Thread(new Runnable() {
            public void run() {
                try {
                    bitmapSample1 = getBitmap(urlList.get(0));
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
*/

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

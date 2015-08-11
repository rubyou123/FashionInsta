package fieldeducation.fashioninsta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends Activity {

    String item;
    TextView textview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item = getIntent().getStringExtra("item");
        textview = (TextView)findViewById(R.id.itemtextView);
        textview.setText(item);
        final GridView gv = (GridView) findViewById(R.id.gridView1);
        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public class MyGridAdapter extends BaseAdapter{
        Context context;
        ArrayList<String> urlList= new ArrayList<String>();
        Bitmap bitmapSample1 = null;
        ArrayList<ImageView> viewList = new ArrayList<ImageView>();
        public MyGridAdapter(Context c)
        {
            context = c;
            urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/s640x640/e15/11410530_927043480690497_313092714_n.jpg");
            urlList.add("https://scontent.cdninstagram.com/hphotos-xaf1/t51.2885-15/s640x640/e35/sh0.08/11378754_862572260492770_362658869_n.jpg");
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

            final ImageView imageView = new ImageView(context);
            for(int i=0; i<urlList.size(); i++) {
                final String url = urlList.get(i);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            bitmapSample1 = getBitmap(url);
                        } catch (Exception e) {

                        } finally {
                            if (bitmapSample1 != null) {
                                runOnUiThread(new Runnable() {
                                    @SuppressLint("NewApi")
                                    public void run() {
                                        imageView.setImageBitmap(bitmapSample1);
                                    }
                                });
                            }
                        }
                    }
                }).start();
                viewList.add(imageView);
            }

        }
        public int getCount()
        {
            return urlList.size();
        }
        public Object getItem(int arg0)
        {
            return null;
        }
        public long getItemId(int arg0)
        {
            return 0;
        }
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            final ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(5, 5, 5, 5);


      /*      new Thread(new Runnable() {
                public void run() {
                    try {
                        bitmapSample1 = getBitmap(urlList.get(position));
                    }catch(Exception e) {

                    }finally {
                        if(bitmapSample1!=null) {
                            runOnUiThread(new Runnable() {
                                @SuppressLint("NewApi")
                                public void run() {
                                    imageView.setImageBitmap(bitmapSample1);


                                }
                            });
                        }
                    }
                }
            }).start();*/
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View dialogView = (View) View.inflate(MainActivity.this, R.layout.dialog, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    ImageView ivPoster = (ImageView) dialogView.findViewById(R.id.ivPoster);
                    ivPoster.setImageBitmap(bitmapSample1);
                    dlg.setTitle("큰 포스터");
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.show();
                }
            });
            viewList.add(position, imageView);
            return imageView;
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
    }


}

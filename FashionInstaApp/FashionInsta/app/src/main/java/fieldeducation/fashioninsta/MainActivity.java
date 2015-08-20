package fieldeducation.fashioninsta;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends Activity {

    String url;
    String key;
    String id;
    TextView textview;
    phpDown task;
    ArrayList<String> imageURLList = new ArrayList<String>();
    ArrayList<String> postIDList = new ArrayList<String>();
    ArrayList<String> userIDList = new ArrayList<String>();
    ArrayList<String> createdTimeList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        key = getIntent().getStringExtra("key");
        id = getIntent().getStringExtra("id");
        textview = (TextView)findViewById(R.id.textView2);
        textview.setText(key);
        try
        {
            id = URLEncoder.encode(id, "UTF-8");
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
        task = new phpDown(imageURLList, postIDList,userIDList,createdTimeList, this);

        url = "http://192.168.123.126//getImageUrl.php?id=" + id;

        task.execute(url);
    }

    private class LoadImagefromUrl extends AsyncTask< Object, Void, Bitmap > {
        ImageView ivPreview = null;

        @Override
        protected Bitmap doInBackground( Object... params ) {
            this.ivPreview = (ImageView) params[0];
            String url = (String) params[1];
            return loadBitmap( url );
        }

        @Override
        protected void onPostExecute( Bitmap result ) {
            super.onPostExecute( result );
            ivPreview.setImageBitmap( result );
        }
    }

    public Bitmap loadBitmap( String url ) {
        URL newurl = null;
        Bitmap bitmap = null;
        try {
            newurl = new URL( url );
            bitmap = BitmapFactory.decodeStream( newurl.openConnection( ).getInputStream( ) );
        } catch ( MalformedURLException e ) {
            e.printStackTrace( );
        } catch ( IOException e ) {

            e.printStackTrace( );
        }
        return bitmap;
    }

    public class MyGridAdapter extends BaseAdapter{
        Context context;
        ArrayList<String> urlList;
        ArrayList<String> userIDList;
        ArrayList<String> createdTimeList;
        public MyGridAdapter(Context c, ArrayList<String> urlList, ArrayList<String> userIDList, ArrayList<String> createdTimeList)
        {
            this.context = c;
            //Listeye image url si ekliyor
            this.urlList = urlList;
            this.userIDList = userIDList;
            this.createdTimeList = createdTimeList;
            Log.d("MyGridAdapter", String.valueOf(urlList.size()));
        }

        @Override
        public int getCount() {
            return urlList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d("getView", "getView 입니다.");
            Log.d("position", String.valueOf(position));

            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(5, 5, 5, 5);
            Log.d("position", String.valueOf(urlList.get(position)));
            //     Bitmap bitmap = getBitmapFromURL(urlList.get(position));
            new LoadImagefromUrl( ).execute( imageView, urlList.get(position) );
            //    imageView.setImageBitmap(bitmap);


            Log.d("getView URL", urlList.get(position));

            final int pos = position;
            imageView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v)
                {
                    View dialogView = (View) View.inflate(MainActivity.this, R.layout.dialog, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    TextView contentview = (TextView)dialogView.findViewById(R.id.textView3);
                    ImageView ivPoster = (ImageView)dialogView.findViewById(R.id.ivPoster);
                    new LoadImagefromUrl( ).execute(ivPoster, urlList.get(pos));
                    String msg = createdTimeList.get(pos) + "에 작성하셨습니다.";
                    contentview.setText(msg);
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.setTitle(userIDList.get(pos) + "님의 글");

                    Log.d("createdTime", createdTimeList.get(pos));

                    dlg.show();
                }
            });

            return imageView;
        }

        public Bitmap getBitmapFromURL(String src) {
            Log.d("getBitmapFromURL", String.valueOf(src));
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
    }


    private class phpDown extends AsyncTask<String, Integer,String> {

        final ArrayList<String> imageURLList;
        final ArrayList<String> postIDList;
        final ArrayList<String> userIDList;
        final ArrayList<String> createdTimeList;
        final MainActivity main;

        public phpDown(final ArrayList<String> imageURLList, final ArrayList<String> postIDList, final ArrayList<String> userIDList ,final ArrayList<String> createdTimeList, MainActivity main)
        {
            this.imageURLList = imageURLList;
            this.postIDList = postIDList;
            this.userIDList = userIDList;
            this.createdTimeList = createdTimeList;
            this.main = main;
        }


        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try{
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                // 연결되었으면.
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    conn.setRequestProperty("Accept", "application/json");
                    // 연결되었음 코드가 리턴되면.
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for(;;){
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if(line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }

                        br.close();
                    }
                    conn.disconnect();
                }

            } catch(Exception ex){
                ex.printStackTrace();
            }
            String str = jsonHtml.toString();
    //        String str = jsonHtml.substring(jsonHtml.lastIndexOf("<body>")+6, jsonHtml.indexOf("</body>"));
            Log.d("jsonHtml", str);
            return str;
        }
        protected void onPostExecute(String str){
            String key;
            String url = "imageURL";
            String postID = "postID";
            String userID = "stringID";
            String createdTime = "createdTime";
            try{
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("urls");

                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    postIDList.add(jo.getString(postID));
                    imageURLList.add(jo.getString(url));
                    userIDList.add(jo.getString(userID));
                    createdTimeList.add(jo.getString(createdTime));
                    Log.d(url, jo.getString(url));
                    Log.d(postID,jo.getString(postID));
                    Log.d(createdTime,jo.getString(userID));
                    Log.d(createdTime,jo.getString(createdTime));
                    //    wordList[i] = key;
                    //        textView.setText(key);

                }
            }catch(JSONException e){
                e.printStackTrace();
            }

            final GridView gv = (GridView)findViewById(R.id.gridView);
            MyGridAdapter gridAdapter = new MyGridAdapter(main, imageURLList, userIDList, createdTimeList);
            gv.setAdapter(gridAdapter);
        }
        protected ArrayList<String> getImageURLList()
        {
            return imageURLList;
        }
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
}

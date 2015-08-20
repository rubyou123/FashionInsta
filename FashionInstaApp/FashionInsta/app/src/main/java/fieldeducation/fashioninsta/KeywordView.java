package fieldeducation.fashioninsta;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class KeywordView extends Activity {

    JSONObject jobj = null;

    TextView textView;
    String ab;
    phpDown task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_view);

        final ArrayList<String> midList = new ArrayList<String>();
        final ArrayList<String> idList = new ArrayList<String>();

        ListView list = (ListView) findViewById(R.id.listView);

        task = new phpDown(midList, idList);
        task.execute("http://192.168.123.126//getkeyword.php");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(KeywordView.this, MainActivity.class);
                intent.putExtra("id", idList.get(position));
                intent.putExtra("key", midList.get(position));

                startActivity(intent);
                //   Toast.makeText(getApplication(), midList.get(position), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_keyword_view, menu);
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


    private class phpDown extends AsyncTask<String, Integer,String>{

        final ArrayList<String> midList;
        final ArrayList<String> idList;

        public phpDown(final ArrayList<String> mid, final ArrayList<String> idList)
        {
            midList = mid;
            this.idList = idList;
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
        //    String str = jsonHtml.substring(jsonHtml.lastIndexOf("<body>")+6, jsonHtml.indexOf("</body>"));
            Log.d("jsonHtml", str);
            return str;
        }
        protected void onPostExecute(String str){
            String key;
            String num;
            try{
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");

                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    num = jo.getString("idkeyword");
                    //num = Integer.toString(i + 1);
                    idList.add(num);
                    key = jo.getString("word");
                    midList.add(key);
                    Log.d("key", key);
                    //    wordList[i] = key;
                    //        textView.setText(key);
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

    }

}

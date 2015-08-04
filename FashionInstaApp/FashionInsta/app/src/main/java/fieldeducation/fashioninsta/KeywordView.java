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

import org.json.JSONObject;
import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class KeywordView extends Activity implements AdapterView.OnItemClickListener{

    public String[] wordList = new String[7];
    public String[] postIDList = new String[7];
    String keywordURL = "http://127.0.0.1/getkeyword.php/";
    ListView list;

    JSONObject jobj = null;
    ClientSeverInterface clientServerInterface = new ClientSeverInterface();
    TextView textView;
    String ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_view);
        ArrayAdapter<String> Adapter;

    //    String jsonobejct = connectURL(keywordURL);
      //  onPostExcute(jsonobejct);
        new RetreiveData().execute();
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordList);

        list = (ListView)findViewById(R.id.listView);
        list.setAdapter(Adapter);
        list.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String c_list = wordList[position];
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("item", c_list);
        startActivity(intent);
    }

    protected String connectURL(String address)
    {
        StringBuilder jsonHtml = new StringBuilder();
        try{
            // 연결 url 설정
            URL url = new URL(address);
            // 커넥션 객체 생성
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // 연결되었으면.
            if(conn != null){
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
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
        return jsonHtml.toString();
    }

    protected void onPostExcute(String str)
    {
        try {
            JSONObject root = new JSONObject(str);
            JSONArray ja = root.getJSONArray("results");
            for(int i=0; i<ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                wordList[i] = jo.getString("word");
                postIDList[i] = jo.getString("idkeyword");
                }
            }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            jobj = clientServerInterface.makeHttpRequest(keywordURL);

            try {
                ab = jobj.getString("key");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return ab;
        }
    protected void onPostExecute(String ab){

        textView.setText(ab);
    }

}


}

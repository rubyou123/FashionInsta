package fieldeducation.fashioninsta;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    String key;
    //   TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        key = getIntent().getStringExtra("key");
        //    textview =  (TextView)findViewById(R.id.keyText);
        //   textview.setText(key);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        TextView tabView1 = (TextView) findViewById(R.id.editText);
        tabView1.setText(key);
        tabView1.setEnabled(false);
        TextView tabView2 = (TextView) findViewById(R.id.editText2);
        tabView2.setText(key);
        tabView2.setEnabled(false);
        TextView tabView3 = (TextView) findViewById(R.id.editText3);
        tabView3.setText(key);
        tabView3.setEnabled(false);

        tabHost.setup();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("이미지");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("연관단어");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("사용자추천");
        tab1.setIndicator("이미지");
        tab1.setContent(R.id.tab1);
        tab2.setIndicator("연관단어");
        tab2.setContent(R.id.tab2);
        tab3.setIndicator("사용자추천");
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.setCurrentTab(0);
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

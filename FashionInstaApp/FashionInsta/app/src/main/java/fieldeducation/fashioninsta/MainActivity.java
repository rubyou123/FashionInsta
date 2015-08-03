package fieldeducation.fashioninsta;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class MainActivity extends TabActivity {

    String item;
    //   TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item = getIntent().getStringExtra("item");

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("Tab1").setIndicator("이미지");
        tab1.setContent(new Intent(this, ImageActivity.class));
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Tab2").setIndicator("연관 단어");
        tab2.setContent(new Intent(this, AssociateTerm.class));
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Tab3").setIndicator("사용자 추천");
        tab3.setContent(new Intent(this, RecommendUserActivity.class));
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.setCurrentTab(0);
      /*  tabHost.setup();
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Tab2");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Tab3");
        tab2.setIndicator("연관단어");
        tab2.setContent(R.string.tab2);
        tab3.setIndicator("사용자추천");
        tab3.setContent(R.string.tab3);


        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 80;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 80;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height = 80;


        */
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

package clemadr06.litclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import clemadr06.LitClub.R;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "CardListActivity";
    private ClubAdapter cardArrayAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main
        );
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ArrayList<club> words = new ArrayList<club>();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        gettitle();


/*            club card = new club("Card "+ " Line 1", " Line 2", " Line 2");
            cardArrayAdapter.add(card);
        }
        listView.setAdapter(cardArrayAdapter);*/
    }

public void gettitle()
{
    listView = (ListView) findViewById(R.id.card_listView);

    listView.addHeaderView(new View(this));
    listView.addFooterView(new View(this));

    cardArrayAdapter = new ClubAdapter(getApplicationContext(), R.layout.list);
    AsyncHttpClient client=new AsyncHttpClient();
    client.get("https://dev.rajkumaar.co.in/clement/zara/api/posts.php",new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                JSONArray jsonArray=response.getJSONArray("data");
       for(int i=0;i<jsonArray.length();i++)
{
    JSONObject obj=jsonArray.getJSONObject(i);

    club card = new club(obj.getString("title"), obj.getString("subtitle"), obj.getString("author"));
    cardArrayAdapter.add(card);
}
                listView.setAdapter(cardArrayAdapter);

            }
            catch(Exception e)
            {
             e.printStackTrace();
            }
            super.onSuccess(statusCode, headers, response);
        }
    });

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           // Object o = listView.getItemAtPosition(position);
            //listView str = (listView) o; //As you are using Default String Adapter
            //Toast.makeText(getBaseContext(),str.getTitle(),Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), description.class);
            i.putExtra("position",position);
            startActivity(i);
        }
    });
}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent i = new Intent(getApplicationContext(),login.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {
            Intent i = new Intent(getApplicationContext(),description.class);
            startActivity(i);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

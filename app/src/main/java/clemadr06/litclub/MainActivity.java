package clemadr06.litclub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import clemadr06.LitClub.R;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "CardListActivity";
    private ClubAdapter cardArrayAdapter;
    private ListView listView;
    int j;
    int id[];
    club card;
    clubsadapter clubsadapter;
    String token1;
    ArrayList<club> clubList ;
    ArrayList<club> clubList1 = new ArrayList<club>();
    ProgressBar p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main
        );
        sendToken();
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
        listView = findViewById(R.id.card_listView);
        p=(ProgressBar)findViewById(R.id.progressBar1);
       if(check())
       {

           gettitle();
       }
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);
                       /* clubsadapter = new clubsadapter(MainActivity.this,clubList1);
                        listView.setAdapter(clubsadapter);
                         gettitle();*/
                        if(check()) {

                               // clubsadapter = new clubsadapter(MainActivity.this,clubList1);
                                gettitle();

                        }
                        else{
                            p.setVisibility(View.GONE);
                        }
                    }
                },3000);
            }
        });


/*            club card = new club("Card "+ " Line 1", " Line 2", " Line 2");
            cardArrayAdapter.add(card);
        }
        listView.setAdapter(cardArrayAdapter);*/
    }
    public void sendToken()
    {
         String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        SharedPreferences preferences=getSharedPreferences("firebase",Context.MODE_PRIVATE) ;
       if( preferences.getBoolean("refresh",true))
       {   preferences.getString("token",token1);
           AsyncHttpClient client=new AsyncHttpClient();
           RequestParams params = new RequestParams();
           params.put("token",token1);
           params.put("android_id", android_id);
           client.post("https://dev.rajkumaar.co.in/clement/zara/api/settoken.php",params, new JsonHttpResponseHandler() {
               @Override
               public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   Log.e("response:",response.toString());
               }

               @Override
               public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                   // called when response HTTP status is "4XX" (eg. 401, 403, 404)

               }
           });
       }
    }
    public boolean check()
    {
        if(isNetworkAvailable()){
            return true;
        }
        else{
Toast.makeText(MainActivity.this,"Connect to Internet",Toast.LENGTH_SHORT).show();
          //  p.setVisibility(View.GONE);
return false;
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
public void gettitle()
{
   // listView = (ListView) findViewById(R.id.card_listView);

  //  listView.addHeaderView(new View(this));
   // listView.addFooterView(new View(this));

   // cardArrayAdapter = new ClubAdapter(getApplicationContext(), R.layout.list);
    clubList = new ArrayList<club>();
    AsyncHttpClient client=new AsyncHttpClient();
    client.get("https://dev.rajkumaar.co.in/clement/zara/api/posts.php",new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                JSONArray jsonArray=response.getJSONArray("data");
                id=new int[jsonArray.length()];
       for(int i=0;i<jsonArray.length();i++)
{
    JSONObject obj=jsonArray.getJSONObject(i);
   // id[i]=obj.getInt("id");
    card = new club(obj.getString("title"), obj.getString("subtitle"), obj.getString("author"),obj.getInt("id"));
   // cardArrayAdapter.add(card);
   clubList.add(card);
} clubsadapter = new clubsadapter(MainActivity.this,clubList);
                listView.setAdapter(clubsadapter);
                p.setVisibility(View.GONE);

            }
            catch(Exception e)
            {
             e.printStackTrace();
            }
            super.onSuccess(statusCode, headers, response);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            Toast.makeText(MainActivity.this,"failure",Toast.LENGTH_SHORT).show();
            super.onFailure(statusCode, headers, responseString, throwable);
        }
    });

   /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
           // Object o = listView.getItemAtPosition(position);
            //listView str = (listView) o; //As you are using Default String Adapter
            //Toast.makeText(getBaseContext(),str.getTitle(),Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), description.class);
            club card1;
            card=clubList.get(position-1);
            j=card.getId();
            i.putExtra("position",j);
            startActivity(i);
        }
    });*/
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

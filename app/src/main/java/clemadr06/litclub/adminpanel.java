package clemadr06.litclub;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import clemadr06.LitClub.R;
import cz.msebera.android.httpclient.Header;

public class adminpanel extends AppCompatActivity {
    private ClubAdapter cardArrayAdapter;
    private ListView listView;
    int id[];
    int j;
    int p;
    ProgressBar p1;
    club card;
    ClubAdapter clubsadapter;
    ArrayList<club> clubList ;
    TextView error,whoop;
    Button retry;
    ArrayList<club> clubList1= new ArrayList<club>();
    gdata g=new gdata();
LinearLayout internet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Admin Pannel");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        internet=(LinearLayout)findViewById(R.id.lyt_no_connectionadmin);
        p1=(ProgressBar)findViewById(R.id.progressBar2);
        error=(TextView)findViewById(R.id.error);
        retry=(Button)findViewById(R.id.bt_retry);
        whoop=(TextView)findViewById(R.id.whoop);
        /*if(check())
        {
            internet.setVisibility(View.GONE);
            list();
        }
        else{
            // setContentView(R.layout.noitem)
            p1.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            //  gettitle();
        }*/
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout1);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);

                        //gettitle();
                       refresh();

                    }
                },2000);
            }
        });
    }
    protected void onPostResume() {
        if(clubsadapter!=null)
            clubsadapter.clear();

    refresh();
    super.onPostResume();

    }
public void refresh()
{
    if(check()) {
        internet.setVisibility(View.GONE);
        list();
    }
    else {
        clubsadapter= new ClubAdapter(adminpanel.this,clubList1);
        listView.setAdapter(clubsadapter);
        internet.setVisibility(View.VISIBLE);

        p1.setVisibility(View.GONE);
        error.setText("No internet connections found. Check your connection or try again");
        whoop.setVisibility(View.VISIBLE);
        retry.setVisibility(View.VISIBLE);
    }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.logout:
                g.setBoolean(false);
                g.setId(0);
                g.setToken(null);
                finish();
                break;

            case R.id.home:
                finish();
                break;
        }
        return true;
    }

    public boolean check()
    {
        if(isNetworkAvailable()){
            return true;
        }
        else{
            //Toast.makeText(adminpanel.this,"Connect to Internet",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void retry(View view){
        if(check())
        { p1.setVisibility(View.VISIBLE);
            internet.setVisibility(View.GONE);
            list();
        }
        else{
            // setContentView(R.layout.noitem);

            internet.setVisibility(View.VISIBLE);
            p1.setVisibility(View.GONE);
            //  gettitle();
        }
    }
    public void list()
    {
        listView = (ListView) findViewById(R.id.card_listView1);



        clubList = new ArrayList<club>();
        Toast.makeText(adminpanel.this,""+g.getid(), Toast.LENGTH_SHORT).show();
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(getString(R.string.domain)+getString(R.string.posts)+"/"+g.getid(),new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getJSONArray("data").length()<1) {
                        p1.setVisibility(View.GONE);
                      //  Toast.makeText(adminpanel.this,"no posts added",Toast.LENGTH_SHORT).show();;
                        internet.setVisibility(View.VISIBLE);
                        whoop.setVisibility(View.GONE);
                        retry.setVisibility(View.GONE);
                        error.setText("NO POSTS YET");
                    } else {
                        JSONArray jsonArray = response.getJSONArray("data");
                        id = new int[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            // id[i]=obj.getInt("id");
                            card = new club(obj.getString("title"), obj.getString("subtitle"), obj.getString("author"), obj.getInt("id"));
                            // cardArrayAdapter.add(card);
                            clubList.add(card);
                        }
                        clubsadapter = new ClubAdapter(adminpanel.this, clubList);
                        p1.setVisibility(View.GONE);
                        listView.setAdapter(clubsadapter);
                    }
                }
                catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    super.onSuccess(statusCode, headers, response);
                }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(adminpanel.this,"failure",Toast.LENGTH_SHORT).show();
                super.onFailure(statusCode, headers, responseString, throwable);
            }
            @Override
            public void onFinish() {
                p1.setVisibility(View.GONE);
                //  if(out.to)
                super.onFinish();

            }
        });




    }
    public void addpost(View v)
    {
        Intent i = new Intent(getApplicationContext(), organise.class);

        startActivity(i);
    }
    }


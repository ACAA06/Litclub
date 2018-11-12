package clemadr06.litclub;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import clemadr06.LitClub.R;
import cz.msebera.android.httpclient.Header;

public class description extends AppCompatActivity {
    TextView desc;
    TextView time;
    TextView venue;
    TextView title;
    TextView subtitle;
    TextView m1n;
    TextView m1ph;
    TextView m2n;
    TextView m2ph;
    ImageView img;
int s;

    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x);
        desc=(TextView)findViewById(R.id.desco);
        venue=(TextView)findViewById(R.id.venueo);
        time=(TextView)findViewById(R.id.timo);
        title=(TextView)findViewById(R.id.titleo);
        subtitle=(TextView)findViewById(R.id.subtitleo);
        m1n=(TextView)findViewById(R.id.m1no);
        m2n=(TextView)findViewById(R.id.m2no);
        m1ph=(TextView)findViewById(R.id.m1po);
        m2ph=(TextView)findViewById(R.id.m2po);
        img=(ImageView) findViewById(R.id.header);
        ActionBar actionBar = getActionBar();
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("LitClubs");
  /*      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/
        //actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("LitClub");
        s= getIntent().getIntExtra("position",0);
        AsyncHttpClient client=new AsyncHttpClient();
        client.get("https://dev.rajkumaar.co.in/clement/zara/api/posts.php",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("data");
                        JSONObject obj=jsonArray.getJSONObject(s-1);
                        desc.setText(obj.getString("description"));
                        time.setText(obj.getString("timing"));
                       venue.setText(obj.getString("venue"));
                       title.setText(obj.getString("title"));
                       subtitle.setText(obj.getString("subtitle"));
                       m1n.setText(obj.getString("manager1_name"));
                       m2n.setText(obj.getString("manager2_name"));
                       m1ph.setText(obj.getString("manager1_contact"));
                       m2ph.setText(obj.getString("manager2_contact"));
                    Picasso.with(description.this)
                            .load(obj.getString("img_url"))
                            .into(img);
                    //time.setText(obj.getString("timing"));




                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                super.onSuccess(statusCode, headers, response);
        }
    });
}
    }

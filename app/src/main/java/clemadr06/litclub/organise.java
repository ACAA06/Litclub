package clemadr06.litclub;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import clemadr06.LitClub.R;
import cz.msebera.android.httpclient.Header;

public class organise extends AppCompatActivity {
EditText title;
    EditText subtitle;
    EditText time;
    EditText venue;
    EditText description;
    EditText imurl;
    EditText m1name;
    EditText m1ph;EditText m2name;
    EditText m2ph;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organise);
        title=(EditText)findViewById(R.id.titlee);
        ActionBar actionBar = getActionBar();
       // actionBar.setDisplayHomeAsUpEnabled(true);
        subtitle=(EditText)findViewById(R.id.sub2);
        description=(EditText)findViewById(R.id.desc1);
        time=(EditText)findViewById(R.id.time1);
        venue=(EditText)findViewById(R.id.venue1);
        imurl=(EditText)findViewById(R.id.imrl1);
        m1name=(EditText)findViewById(R.id.m1n);
        m1ph =(EditText)findViewById(R.id.m1p);
        m2name=(EditText)findViewById(R.id.m2n);
        m2ph=(EditText)findViewById(R.id.m2p);


    }
    public void orga(View v)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("title", title.getText().toString());
        params.put("subtitle", subtitle.getText().toString());
        params.put("description", description.getText().toString());
        params.put("img_url", imurl.getText().toString());
        params.put("venue", venue.getText().toString());
        params.put("timing", time.getText().toString());
        params.put("m1_name", m1name.getText().toString());
        params.put("m1_contact", m1ph.getText().toString());
        params.put("m2_name", m2name.getText().toString());
        params.put("m2_contact", m2ph.getText().toString());
        gdata g=new gdata();
        //String s=g.getToken();
        String s= getIntent().getStringExtra("token1");
        client.addHeader("Authorization","Bearer "+s);
        client.post("https://dev.rajkumaar.co.in/clement/zara/api/addpost.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(organise.this,"s", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
        });
        }


}

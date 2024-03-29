package clemadr06.litclub;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import clemadr06.LitClub.R;
import cz.msebera.android.httpclient.Header;

public class description1 extends AppCompatActivity {
    TextView desc1;
    TextView date1,date2,date3;
    TextView venue1;
    TextView title1;
    TextView subtitle1;
    TextView m1n1;
    TextView m1ph1;
    TextView m2n1;
    TextView m2ph1;
    ImageView img11;
    ImageView img1;
    String d1,d2,d3;
    int s;
    int id;
    String url;
    ProgressBar p;
    LinearLayout error,descxml;
    boolean isImageFitToScreen;

    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description1);
        desc1=(TextView)findViewById(R.id.desco1);
        venue1=(TextView)findViewById(R.id.venueo1);
        date1=(TextView)findViewById(R.id.timo11);
        date2=(TextView)findViewById(R.id.timo21);
        date3=(TextView)findViewById(R.id.timo31);
        title1=(TextView)findViewById(R.id.titleo1);
        subtitle1=(TextView)findViewById(R.id.subtitleo1);
        m1n1=(TextView)findViewById(R.id.m1no1);
        m2n1=(TextView)findViewById(R.id.m2no1);
        m1ph1=(TextView)findViewById(R.id.m1po1);
        m2ph1=(TextView)findViewById(R.id.m2po1);
        img1=(ImageView) findViewById(R.id.header1);
        ActionBar actionBar = getActionBar();
        error=(LinearLayout)findViewById(R.id.lyt_no_connection2);
        error.setVisibility(View.GONE);
        descxml=(LinearLayout)findViewById(R.id.linear1) ;
        p=(ProgressBar)findViewById(R.id.progressBardesc1);
        p.setVisibility(View.VISIBLE);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarxx);
     toolbar.setTitle("LitClubs");


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

get();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.editpost:
                Intent i = new Intent(getApplicationContext(), editpost.class);
                i.putExtra("id",id);
                startActivity(i);
                finish();
                break;

            case R.id.home:
                finish();
                break;
        }
        return true;
    }
    public void edit(View v)
    {
        Intent i = new Intent(getApplicationContext(), editpost.class);
        i.putExtra("id",id);
        startActivity(i);
    }
    public void get()
    {
        s= getIntent().getIntExtra("id",0);
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(getString(R.string.domain)+getString(R.string.posts),new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {descxml.setVisibility(View.VISIBLE);
                    JSONArray jsonArray=response.getJSONArray("data");
                    for(int j=0;j<jsonArray.length();j++) {
                        JSONObject obj = jsonArray.getJSONObject(j);
                        if(obj.getInt("id")==s) {
                            desc1.setText(obj.getString("description"));
                            // time.setText(obj.getString("timing"));
                            venue1.setText(obj.getString("venue"));
                            title1.setText(obj.getString("title"));
                            d1 = obj.getString("startdate");
                            d2 = obj.getString("enddate");
                            date1.setText(d1);
                            date2.setText(d2);
                            subtitle1.setText(obj.getString("subtitle"));
                            m1n1.setText(obj.getString("manager1_name"));
                            m2n1.setText(obj.getString("manager2_name"));
                            m1ph1.setText(obj.getString("manager1_contact"));
                            m2ph1.setText(obj.getString("manager2_contact"));
                            url = obj.getString("img_url");
                            id = obj.getInt("id");
                            Picasso.with(description1.this)
                                    .load(url)

                                    .error(R.drawable.placeholder)
                                    .into(img1);
                            //time.setText(obj.getString("timing"));
                        }
                    }


                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                error.setVisibility(View.VISIBLE);
                descxml.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {

                p.setVisibility(View.GONE);
                super.onFinish();
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Dialog dialog = new Dialog(description.this);
                dialog.setContentView(R.layout.imgdialog);

                dialog.show();*/
               /* AlertDialog.Builder builder = new AlertDialog.Builder(description.this);
                final AlertDialog dialog = builder.create();
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.imgdialog, null);
                dialog.setView(dialogLayout);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.show();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {
                        ImageView image = (ImageView) dialog.findViewById(R.id.dialog_imageview);
                   Bitmap x=getBitmapFromURL(url);
                       // x.createScaledBitmap(Bitmap url, int dstWidth, int dstHeight, boolean filter);
                        float imageWidthInPX = (float)image.getWidth();

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                                Math.round(imageWidthInPX * (float)x.getHeight() / (float)x.getWidth()));
                        image.setLayoutParams(layoutParams);

*/
                Dialog dialog = new Dialog(description1.this);
                dialog.setContentView(R.layout.imgdialog);
                img11=(ImageView) dialog.findViewById(R.id.imaged);
                Picasso.with(description1.this)
                        .load(url)
                        .error(R.drawable.placeholder)

                        .into(img11);

                dialog.show();
            }
        });

    }
    public void retry(View view)
    {   error.setVisibility(View.GONE);
        p.setVisibility(View.VISIBLE);
        get();

    }
}

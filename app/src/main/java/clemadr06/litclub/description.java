package clemadr06.litclub;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import clemadr06.LitClub.*;
import cz.msebera.android.httpclient.Header;

public class description extends AppCompatActivity {
    TextView desc;
    TextView date1,date2,date3;
    TextView venue;
    TextView title;
    TextView subtitle,by;
    TextView m1n;
    TextView m1ph;
    TextView m2n;
    TextView m2ph;
    ImageView img;
    ImageView img1;
    String d1,d2,d3;
    ProgressBar p;
    LinearLayout error,descxml;
int s;
String url;
    boolean isImageFitToScreen;
    String dates;

    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x);
        desc=(TextView)findViewById(R.id.desco);
        venue=(TextView)findViewById(R.id.venueo);
        date1=(TextView)findViewById(R.id.timo1);
        date2=(TextView)findViewById(R.id.timo2);
        date3=(TextView)findViewById(R.id.timo3);
        title=(TextView)findViewById(R.id.titleo);
        subtitle=(TextView)findViewById(R.id.subtitleo);
        m1n=(TextView)findViewById(R.id.m1no);
        m2n=(TextView)findViewById(R.id.m2no);
        m1ph=(TextView)findViewById(R.id.m1po);
        m2ph=(TextView)findViewById(R.id.m2po);
        img=(ImageView) findViewById(R.id.header);
        by=(TextView)findViewById(R.id.byline);
        ActionBar actionBar = getActionBar();
        error=(LinearLayout)findViewById(R.id.lyt_no_connection1);
        descxml=(LinearLayout)findViewById(R.id.linear);
        error.setVisibility(View.GONE);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarx);
        toolbar.setTitle("LitClubs");
toolbar.setBackgroundResource(R.drawable.grad);
        //actionBar.setDisplayHomeAsUpEnabled(true);
      //  toolbar = (Toolbar) findViewById(R.id.to);
        p=(ProgressBar)findViewById(R.id.progressBardesc);
        p.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
       if (getSupportActionBar() != null)

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
       // collapsingToolbar.setTitle("LitClub");

        get();
            }
            public void retry(View view)
            {   error.setVisibility(View.GONE);
            p.setVisibility(View.VISIBLE);
                get();

            }
            public void get()
            {
                s= getIntent().getExtras().getInt("id");
                Toast.makeText(description.this," "+s,Toast.LENGTH_SHORT).show();
                AsyncHttpClient client=new AsyncHttpClient();
                client.get(getString(R.string.domain)+getString(R.string.posts),new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {descxml.setVisibility(View.VISIBLE);
                            JSONArray jsonArray=response.getJSONArray("data");
                            for(int j=0;j<jsonArray.length();j++) {
                                JSONObject obj = jsonArray.getJSONObject(j);
                                if(obj.getInt("id")==s) {
                                    desc.setText(obj.getString("description"));
                                    // time.setText(obj.getString("timing"));
                                    by.setText("By "+obj.getString("author"));
                                    venue.setText(obj.getString("venue"));
                                    title.setText(obj.getString("title"));
                                    d1 = obj.getString("startdate");
                                    d2 = obj.getString("enddate");
                                    date1.setText(d1);
                                    date2.setText(d2);
                                    subtitle.setText(obj.getString("subtitle"));
                                    m1n.setText(obj.getString("manager1_name"));
                                    m2n.setText(obj.getString("manager2_name"));
                                    m1ph.setText(obj.getString("manager1_contact"));
                                    m2ph.setText(obj.getString("manager2_contact"));
                                    url = obj.getString("img_url");
                                    //  id = obj.getInt("id");
                                    Picasso.with(description.this)
                                            .load(url)

                                            .error(R.drawable.placeholder)
                                            .into(img);
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
                        error.setVisibility(View.VISIBLE);
                        descxml.setVisibility(View.GONE);
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onFinish() {
                        p.setVisibility(View.GONE);
                        super.onFinish();
                    }
                });

                img.setOnClickListener(new View.OnClickListener() {
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
                        Dialog dialog = new Dialog(description.this);
                        dialog.setContentView(R.layout.imgdialog);
                        img1=(ImageView) dialog.findViewById(R.id.imaged);
                        Picasso.with(description.this)
                                .load(url)
                                .error(R.drawable.placeholder)
                                .into(img1);

                        dialog.show();
                    }
                });
            }


}
    /*public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }*/


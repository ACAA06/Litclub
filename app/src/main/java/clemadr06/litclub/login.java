package clemadr06.litclub;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import clemadr06.LitClub.R;
import cz.msebera.android.httpclient.Header;

public class login extends AppCompatActivity {
    public EditText uname;
    public EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        uname=(EditText) findViewById(R.id.uname);
        pass=(EditText) findViewById(R.id.pass);

    }
    public void log(View v)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", uname.getText().toString());
        params.put("password", pass.getText().toString());
        client.post("https://dev.rajkumaar.co.in/clement/zara/api/signin.php", params,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if(response.getString("status").equals("true")) {
                        gdata g = new gdata();
                        g.setToken(response.getString("token"));
                        g.setId(response.getInt("id"));
                        Log.e("token",response.getString("token"));
                        Intent i = new Intent(getApplicationContext(), adminpanel.class);
                        //i.putExtra("token1", response.getString("token"));
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(login.this,response.getString("error"), Toast.LENGTH_SHORT).show();

                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                //Toast.makeText(login.this,"Invalid Username or password", Toast.LENGTH_SHORT).show();
                Toast.makeText(login.this,"failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

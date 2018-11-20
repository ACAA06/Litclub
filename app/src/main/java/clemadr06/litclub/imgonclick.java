package clemadr06.litclub;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import clemadr06.LitClub.R;

public class imgonclick extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
       // setContentView(R.layout.imgdialog);
        Dialog dialog = new Dialog(imgonclick.this);
        dialog.setContentView(R.layout.imgdialog);
        ImageView img1=(ImageView) findViewById(R.id.imaged);
            String x= getIntent().getStringExtra("url");
        Picasso.with(imgonclick.this)
                .load(x)
                .into(img1);

        dialog.show();


    }
}

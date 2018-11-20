package clemadr06.litclub;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import clemadr06.LitClub.R;
import cz.msebera.android.httpclient.Header;

public class ClubAdapter extends ArrayAdapter<club>{

    ClubAdapter(Context context, ArrayList<club> clubs) {

        super(context, 0,clubs);

    }



    @NonNull

    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null) {

            listItemView = LayoutInflater.from(getContext()).inflate(

                    R.layout.list, parent, false);

        }



        //  mMaterialColors = getContext().getResources().getIntArray(R.array.colors);

        final club current=getItem(position);



        TextView title=listItemView.findViewById(R.id.line1);

        title.setText(current.getTitle());



        TextView subtitle=listItemView.findViewById(R.id.line2);
        subtitle.setText(current.getSubtitle());
        subtitle.setVisibility(View.VISIBLE);

       /* if(current.getSubtitle()!=null) {

            subtitle.setVisibility(View.VISIBLE);

            subtitle.setTextColor(getContext().getResources().getColor(android.R.color.white));

            subtitle.setText(current.getSubtitle());

        }else{

            subtitle.setVisibility(View.GONE);

        }*/
        TextView author=listItemView.findViewById(R.id.line3);
        author.setText(current.getAuthor());



        listItemView.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {



                getContext().startActivity(new Intent(getContext(),description1.class).putExtra("id",current.getId()));

            }

        });

        listItemView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                Toast.makeText(getContext(),""+current.getId(),Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Closing Activity")
                        .setMessage("Are you sure you want to delete this event?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {gdata g1=new gdata();
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AsyncHttpClient client1 = new AsyncHttpClient();
                                client1.addHeader("Authorization","Bearer "+g1.getToken());
                                client1.get("https://dev.rajkumaar.co.in/clement/zara/api/delete.php?id="+current.getId(),new JsonHttpResponseHandler()
                                {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        Toast.makeText(getContext(),"deleted",Toast.LENGTH_SHORT).show();
                                        super.onSuccess(statusCode, headers, response);
                                    }
                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                        Toast.makeText(getContext(),"failure " , Toast.LENGTH_SHORT).show();
                                    }
                                });
                                // finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;    // <- set to true
            }
        });





        return listItemView;

    }
}

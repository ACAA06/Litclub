package clemadr06.litclub;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import clemadr06.LitClub.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
public class clubsadapter extends ArrayAdapter<club>{
    LinearLayout internet;
    clubsadapter(Context context, ArrayList<club> clubs) {

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
        if(current.getSubtitle().equals(""))
        {
            subtitle.setVisibility(View.GONE);
        }
        else {
            subtitle.setVisibility(View.VISIBLE);
            subtitle.setText(current.getSubtitle());
        }TextView author=listItemView.findViewById(R.id.line3);
        author.setText(current.getAuthor());
       /* if(current.getSubtitle()!=null) {

            subtitle.setVisibility(View.VISIBLE);

            subtitle.setTextColor(getContext().getResources().getColor(android.R.color.white));

            subtitle.setText(current.getSubtitle());

        }else{

            subtitle.setVisibility(View.GONE);

        }*/



        listItemView.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                if(isNetworkAvailable()){
                    getContext().startActivity(new Intent(getContext(),description.class).putExtra("id",current.getId()));
                }
                else{
                   // Toast.makeText(MainActivity.class,"Connect to Internet",Toast.LENGTH_SHORT).show();
                    //  p.setVisibility(View.GONE);


                }


            }

        });





        return listItemView;

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager= (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package clemadr06.litclub;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;

import java.net.URL;
import java.util.TimeZone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import clemadr06.LitClub.R;
import cz.msebera.android.httpclient.Header;

public class organise extends AppCompatActivity {
EditText title;
    EditText subtitle;
    EditText date1;
    EditText date2,date3;
    EditText venue;
    EditText description;
    EditText imurl;
    EditText m1name;
    EditText m1ph;EditText m2name;
    EditText m2ph;
    TextView ldate1,ldate2,ldate3;
    String d1,d2,d3;
    String format;
    EditText t1,t2,t3;
    int day1,month1,year1,day2,month2,year2,day3,month3,year3;
    Button setd1,setd2,setd3,sett1,sett2,sett3;
    String d;
    int hr,min;
    long timestamp3,timestamp2,timestamp1;
    Calendar mycurrentdate,mycurrenttime;
    int day,month,year;
    Spinner set;
String days="3";
String a1,a2,a3;
    Dialog dial;
    LinearLayout pl;
    String s;
    //TextView noe;
    String ts1,ts2,ts3;
int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organise);
        title=(EditText)findViewById(R.id.titlee);
        ActionBar actionBar = getActionBar();
       // actionBar.setDisplayHomeAsUpEnabled(true);
        subtitle=(EditText)findViewById(R.id.sub2);
        description=(EditText)findViewById(R.id.desc1);
        date1=(EditText)findViewById(R.id.edate1);
        date2=(EditText)findViewById(R.id.edate2);
        venue=(EditText)findViewById(R.id.venue1);
        imurl=(EditText)findViewById(R.id.imrl1);
        m1name=(EditText)findViewById(R.id.m1n);
        m1ph =(EditText)findViewById(R.id.m1p);
        m2name=(EditText)findViewById(R.id.m2n);
        m2ph=(EditText)findViewById(R.id.m2p);

        t1=(EditText)findViewById(R.id.etime1);
        t2=(EditText)findViewById(R.id.etime2);

pl=(LinearLayout)findViewById(R.id.parent);
        mycurrentdate = Calendar.getInstance();
        mycurrenttime = Calendar.getInstance();
        day = mycurrentdate.get(Calendar.DAY_OF_MONTH);
        month = mycurrentdate.get(Calendar.MONTH);
        year = mycurrentdate.get(Calendar.YEAR);
        month = month + 1;
        hr = mycurrenttime.get(Calendar.HOUR_OF_DAY);
        min = mycurrenttime.get(Calendar.MINUTE);
        ldate1=(TextView)findViewById(R.id.date1);
        ldate2=(TextView)findViewById(R.id.date2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarorg);
        setSupportActionBar(toolbar);
        toolbar.setTitle("LitClub");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // days=set.getSelectedItem().toString();


        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(organise.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthyear, int dayofmonth) {
                        monthyear = monthyear + 1;
                        day1=dayofmonth;
                        month1=monthyear;
                        year1=year;
                        if (dayofmonth < 10 && monthyear < 10)
                        {
                              date1.setText(year1 + "-0" + monthyear + "-0" + dayofmonth);
                            d1=year1 + "-0" + monthyear + "-0" + dayofmonth;}
                        else {
                            if (dayofmonth < 10)
                            { date1.setText(year + "-" + monthyear + "-0" + dayofmonth);
                                d1=year1 + "-0" + monthyear + "-0" + dayofmonth;}
                            else {
                                date1.setText(year + "-" + monthyear + "-" + dayofmonth);
                                d1=year1 + "-0" + monthyear + "-0" + dayofmonth;
                            }
                            if (monthyear < 10)
                            {date1.setText(year + "-0" + monthyear + "-" + dayofmonth);
                                d1=year1 + "-0" + monthyear + "-0" + dayofmonth;}
                            else {
                                date1.setText(year + "-" + monthyear + "-" + dayofmonth);
                                d1=year1 + "-0" + monthyear + "-0" + dayofmonth;
                            }
                        }

                    }
                }, year, month- 1, day);
                datePickerDialog.show();
               /* TimePickerDialog timePickerDialog = new TimePickerDialog(organise.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hr, int min) {
                        selectedtimeformat(hr);
                        d+= " "+hr + " : " + min + " "+ format;
                    }
                }, hr, min, true);
                timePickerDialog.show();*/
            }
        });
        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(organise.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthyear, int dayofmonth) {
                        monthyear = monthyear + 1;
                        day2=dayofmonth;
                        month2=monthyear;
                        year2=year;
                        if (dayofmonth < 10 && monthyear < 10)
                        { date2.setText(year2 + "-0" + monthyear + "-0" + dayofmonth);
                            d2=year2 + "-0" + monthyear + "-0" + dayofmonth;}
                        else {
                            if (dayofmonth < 10)
                            {date2.setText(year2 + "-" + monthyear + "-0" + dayofmonth);
                                d2=year2 + "-0" + monthyear + "-0" + dayofmonth;}
                            else {
                                {date2.setText(year2 + "-" + monthyear + "-" + dayofmonth);
                                d2=year2 + "-0" + monthyear + "-0" + dayofmonth;}
                            }
                            if (monthyear < 10)
                            {date2.setText(year2 + "-0" + monthyear + "-" + dayofmonth);
                                d2=year2 + "-0" + monthyear + "-0" + dayofmonth;}
                            else {
                                date2.setText(year + "-" + monthyear + "-" + dayofmonth);
                                d2=year2 + "-0" + monthyear + "-0" + dayofmonth;
                            }
                        }

                    }
                }, year, month - 1, day);
                datePickerDialog.show();
                /*TimePickerDialog timePickerDialog = new TimePickerDialog(organise.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hr, int min) {
                        selectedtimeformat(hr);
                        d+= " "+hr + " : " + min + " "+ format;
                    }
                }, hr, min, true);
                timePickerDialog.show();*/
            }
        });

       // date1.setText(d);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(organise.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hr, int min) {
                        selectedtimeformat(hr);
                        if(hr>12){
                           if(min<10) {
                               t1.setText(hr-12 + ":0" + min + "" + format);
                               ts1 = hr-12 + ":0" + min + "" + format;
                               }else{
                                  t1.setText(hr-12 + ":" + min + "" + format);
                                 ts1=hr + ":0" + min + "" + format; }}
                                 else{
                                       if(min<10) { t1.setText(hr + ":0" + min + "" + format);
                                      ts1 = hr + ":0" + min + "" + format;
                                     }else{
                                     t1.setText(hr + ":" + min + "" + format);
                                     ts1=hr + ":0" + min + "" + format;
                                 }
                                 }
                    }
                }, hr, min, true);
                timePickerDialog.show();
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(organise.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hr, int min) {
                        selectedtimeformat(hr);
                        if(hr> 12){
                                if(min<10) {
                                        t2.setText(hr-12 + ":0" + min + "" + format);
                                         ts2=hr-12 + ":0" + min + "" + format;
                                    }else{
                                         t2.setText(hr-12 + ":" + min + "" + format);
                                         ts2=hr-12 + ":0" + min + "" + format;
                                             }}
                                             else{
                                          if(min<10) {
                                         t2.setText(hr + ":0" + min + "" + format);
                                         ts2=hr + ":0" + min + "" + format;
                                          }else{ t2.setText(hr + ":" + min + "" + format);
                                                     ts2=hr + ":0" + min + "" + format;
                                                 }
                        }
                    }
                }, hr, min, true);
                timePickerDialog.show();
            }
        });


      //  d1=sdf.parse(date1.getText().toString());
        //Timestamp ts=new Timestamp(d1);
       /* Calendar calendar1=new GregorianCalendar(year1,month1,day1);
        timestamp1= TimeUnit.MILLISECONDS.toSeconds(calendar1.getTimeInMillis());
        Calendar calendar2=new GregorianCalendar(year2,month2,day2);
        timestamp2= TimeUnit.MILLISECONDS.toSeconds(calendar2.getTimeInMillis());*/
      //  Calendar calendar3=new GregorianCalendar(year3,month3,day3);
     //timestamp3= TimeUnit.MILLISECONDS.toSeconds(calendar3.getTimeInMillis());
        a1 =d1+" "+ts1;
        a2 =d2+" "+ts2;




    }
    public static boolean isValid(String url)
    {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
    public void org1(){
       // Log.e("Calendar", " " + days + " " + date1.getText().toString() + " " + t1.getText().toString() + " " + date2.getText().toString() + " " + t2.getText().toString() + " " + date3.getText().toString() + " " + t3.getText().toString());
        //Log.e("year", " " + timestamp1 + " " + timestamp2 + " " + timestamp3);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("title", title.getText().toString());
        params.put("subtitle", subtitle.getText().toString());
        params.put("description", description.getText().toString());
        params.put("img_url", imurl.getText().toString());
        params.put("venue", venue.getText().toString());
        params.put("m1_name", m1name.getText().toString());
        params.put("m1_contact", m1ph.getText().toString());
        params.put("m2_name", m2name.getText().toString());
        params.put("m2_contact", m2ph.getText().toString());
        params.put("startdate", date1.getText().toString());
        // + " " + t1.getText().toString());
        params.put("enddate", date2.getText().toString());
        // + " " + t2.getText().toString());




        gdata g = new gdata();
        //String s=g.getToken();
        s = g.getToken();
        client.addHeader("Authorization", "Bearer " + s);
        Log.e("token", s);
        client.post(getString(R.string.domain)+"/api/create", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(organise.this, "s" + timestamp1, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),adminpanel.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(organise.this, "failure " + s + " " + days, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void orga(View v)
    {flag=1;
        if(title.getText().length()==0)
         {
           title.setError("Field cannot be left blank.");
           flag=0;
        }
        if(imurl.getText().length()==0)
        {   flag=0;
            imurl.setError("Field cannot be left blank.");
        }
        if(description.getText().length()==0)
        {   flag=0;
            description.setError("Field cannot be left blank.");
        }
        if(m1name.getText().length()==0)
        {   flag=0;
            m1name.setError("Field cannot be left blank.");
        }
        if(m1ph.getText().length()==0)
        {   flag=0;
            m1ph.setError("Field cannot be left blank.");
        }
        if(venue.getText().length()==0)
        {   flag=0;
            venue.setError("Field cannot be left blank.");
        }
        if(!isValid(imurl.getText().toString()))
        { flag=0;
            imurl.setError("Invalid url");
        }

        if(date1.getText().length()==0)
            {   flag=0;
                date1.setError("Field cannot be left blank.set value");
            }
            if(t1.getText().length()==0)
            {   flag=0;
                t1.setError("Field cannot be left blank.set value");

            }

        if(date2.getText().length()==0)
        {   flag=0;
            date2.setError("Field cannot be left blank.set value");
        }
        if(t2.getText().length()==0)
        {   flag=0;
            t2.setError("Field cannot be left blank.set value");

        }

        if(flag==1)
        {
            org1();
        }


                }




    public void selectedtimeformat(int h)
    {
        if(h==0)
        {
            hr+=12;
            format="AM";
        }
        else{
            if(h==12)
            {
                format="PM";
            }
            else{
                if(h>12)
                {
                    hr-=12;
                    format="PM";

                }
                else{
                    format="AM";

                }
            }
        }
    }





}

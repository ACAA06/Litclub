package clemadr06.litclub;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import clemadr06.LitClub.R;

public class ClubAdapter extends ArrayAdapter<club> {
    private static final String TAG = "ClubArrayAdapter";
    private List<club> clubList = new ArrayList<club>();

    static class CardViewHolder {
        TextView title;
        TextView subtitle;
        TextView author;
    }

    public ClubAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(club object) {
        clubList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.clubList.size();
    }

    @Override
    public club getItem(int index) {
        return this.clubList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.title = (TextView) row.findViewById(R.id.line1);
            viewHolder.subtitle = (TextView) row.findViewById(R.id.line2);
            viewHolder.author = (TextView) row.findViewById(R.id.line3);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        club card = getItem(position);
        viewHolder.title.setText(card.getLine1());
        viewHolder.subtitle.setText(card.getLine2());
        viewHolder.author.setText(card.getLine3());
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
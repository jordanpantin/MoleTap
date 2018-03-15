package org.diiage.pantin.moletap.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.diiage.pantin.moletap.Models.Score;
import org.diiage.pantin.moletap.R;

import java.util.ArrayList;

/**
 * Created by Jordan on 3/15/2018.
 */

public class ScoreBisAdapter extends BaseAdapter {
    ArrayList<Score> scores;
    Activity activity;

    public ScoreBisAdapter(Activity activity, ArrayList<Score> addresses){
        this.activity = activity;
        this.scores = addresses;
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int i) {
        return scores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null){
            LayoutInflater inflater = this.activity.getLayoutInflater();
            view = inflater.inflate(R.layout.item_score, viewGroup, false);

            holder = new ViewHolder();
            //holder.txtDate = (TextView)view.findViewById(R.id.txtDate);
            holder.txtScore = (TextView)view.findViewById(R.id.lblNbPoint);
            holder.txtNbTaupesManquees = (TextView)view.findViewById(R.id.lblTaupeManque);

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        Score score = (Score)getItem(i);

        if (score != null){
            //holder.txtDate.setText(score.);
            holder.txtScore.setText(String.valueOf(score.getNbPoints()));
            holder.txtNbTaupesManquees.setText(String.valueOf(score.getNbTaupesManquees()));
        }

        return view;
    }

    private static class ViewHolder {
        //public TextView txtDate;
        public TextView txtScore;
        public TextView txtNbTaupesManquees;
    }
}

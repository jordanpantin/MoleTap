package org.diiage.pantin.moletap.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.diiage.pantin.moletap.Models.Score;
import org.diiage.pantin.moletap.R;

import java.util.ArrayList;

/**
 * Created by Jordan on 3/15/2018.
 */
public class ScoresAdapter extends BaseAdapter {

    /**
     * Cette classe permet de mémoriser des référence vers des sous-vues d'un élément de la liste.
     */
    private static class ScoreViewHolder
    {
        public ScoreViewHolder(TextView lblNbPoint, TextView lblTempReacMin, TextView lblTempReacMax, TextView lblTempReacAvg, TextView lblTaupeManque) {
            this.lblNbPoint = lblNbPoint;
            this.lblTempReacMin = lblTempReacMin;
            this.lblTempReacMax = lblTempReacMax;
            this.lblTempReacAvg = lblTempReacAvg;
            this.lblTaupeManque = lblTaupeManque;
        }

        public TextView lblNbPoint;
        public TextView lblTempReacMin;
        public TextView lblTempReacMax;
        public TextView lblTempReacAvg;
        public TextView lblTaupeManque;
    }

    /**
     * Liste des scores à afficher
     */
    private ArrayList<Score> scores;

    /**
     * Page en cours
     */
    private Activity context;

    public ScoresAdapter(Activity context, ArrayList<Score> scores)
    {
        this.scores = scores;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.scores.size();
    }

    /**
     * Obtient un item selon sa position.
     * @param i la position de l'item dans la liste.
     * @return un objet contenant les informations du score.
     */
    @Override
    public Object getItem(int i) {
        return this.scores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        ScoreViewHolder scoreViewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (view != null)
        {
            v = view;
            scoreViewHolder = (ScoreViewHolder)view.getTag();
        }
        else
        {
            LayoutInflater layoutInflater = this.context.getLayoutInflater();

            v = layoutInflater.inflate(R.layout.item_score, viewGroup,false);

            TextView lblNbPoint = v.findViewById(R.id.lblNbPoint);
            TextView lblTempReacMin = v.findViewById(R.id.lblTempReacMin);
            TextView lblTempReacMax = v.findViewById(R.id.lblTempReacMax);
            TextView lblTempReacAvg = v.findViewById(R.id.lblTempReacAvg);
            TextView lblTaupeManque = v.findViewById(R.id.lblTaupeManque);

            scoreViewHolder = new ScoreViewHolder(lblNbPoint, lblTempReacMin, lblTempReacMax ,lblTempReacAvg, lblTaupeManque);
            v.setTag(scoreViewHolder);
        }

        Score score = this.scores.get(i);

        if(score != null)
        {
            scoreViewHolder.lblNbPoint.setText(String.valueOf(score.getNbPoints()));
            scoreViewHolder.lblTaupeManque.setText(String.valueOf(score.getNbTaupesManquees()));
            scoreViewHolder.lblTempReacAvg.setText(String.valueOf(score.getTempsReactionAvg()));
            scoreViewHolder.lblTempReacMax.setText(String.valueOf(score.getTempsReactionMax()));
            scoreViewHolder.lblTempReacMin.setText(String.valueOf(score.getTempsReactionMin()));
        }

        // Return the completed view to render on screen
        return view;
    }
}

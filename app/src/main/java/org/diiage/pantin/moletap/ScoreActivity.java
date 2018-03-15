package org.diiage.pantin.moletap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.diiage.pantin.moletap.Adapters.ScoreBisAdapter;
import org.diiage.pantin.moletap.Models.Score;
import org.diiage.pantin.moletap.Models.Session;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();

        // Get session data.
        Session session = (Session)intent.getExtras().getParcelable("session");

        if (session == null)
        {
            session = new Session();
        }

        //region test

        // Pour tester (si la liste est vide).
        if (session.getListScores().size() <= 0)
        {
            ArrayList<Score> scores = new ArrayList<>();
            Score s1 = new Score();
            s1.setNbPoints(10);
            s1.setNbTaupesManquees(4000);
            s1.setTempsReactionAvg(2000);
            s1.setTempsReactionMax(3000);
            s1.setTempsReactionMin(1000);

            Score s2 = new Score();
            s2.setNbPoints(100);
            s2.setNbTaupesManquees(440);
            s2.setTempsReactionAvg(200);
            s2.setTempsReactionMax(300);
            s2.setTempsReactionMin(100);

            scores.add(s1);
            scores.add(s2);

            session.setListScores(scores);
        }

        //endregion

        ListView listViewScores = findViewById(R.id.listViewScores);
        listViewScores.setAdapter(new ScoreBisAdapter(this, session.getListScores()));
    }
}
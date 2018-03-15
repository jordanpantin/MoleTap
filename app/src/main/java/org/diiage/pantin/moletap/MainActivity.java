package org.diiage.pantin.moletap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.diiage.pantin.moletap.Models.Session;

public class MainActivity extends AppCompatActivity {

    private Session session = new Session();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentMainActivity = getIntent();

        try
        {
            Session s = intentMainActivity.getExtras().getParcelable("session");

            if (s != null)
            {
                session = s;
            }
        }catch (Exception ex)
        {
            Log.e("Error get session", ex.getMessage());
        }


        // Get le bouton permettant de créer une nouvelle partie
        Button btnNewGame = findViewById(R.id.btnNewGame);

        // Gestion des évènements suite au clic sur le bouton nouvelle partie
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);


                startActivity(intent);
            }
        });

        // Get le bouton permettant d'affiche les scores du joueurs
        Button btnScores = findViewById(R.id.btnScores);

        // Gestion des évènements suite au clic sur le bouton score
        btnScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView txtName = findViewById(R.id.txtName);
                session.setNomJoueur(txtName.getText().toString());

                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                intent.putExtra("session", session);
                startActivityForResult(intent, 1);
            }
        });
    }
}

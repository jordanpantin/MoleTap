package org.diiage.pantin.moletap;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.diiage.pantin.moletap.Models.Score;
import org.diiage.pantin.moletap.Models.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity
{

    //region fields
    /**
     * L'identifiant de la taupe visible.
     */
    private int idTaupeVisible = 0;

    /**
     * Le temps écoulé
     */
    private int elapsedTime = 30;

    /**
     * Vrai si le timer est en cours. Faux dans le cas contraire
     */
    private boolean isTimerRunning = false;

    /**
     * Récupère l'image button d'une taupe selon son numéro.
     * @param idTaupe numéro de la taupe (entre 1 et 9).
     * @return L'image button d'une taupe.
     */
    private ImageButton GetImageButtonTaupe(int idTaupe)
    {
        int id =  getResources().getIdentifier("mole"+idTaupe, "id", getPackageName());
        return findViewById(id);
    }

    // Random object
    private Random rnd = new Random();

    /**
     * Chronomètre de la partie
     */
    private Timer timer = new Timer("Minuteur");
    private Timer timerMole = new Timer("Taupe");

    /**
     * L'identifiant de la dernière taupe touchée.
     */
    private int idLastTaupe = 0;

    private int score = 0;
    private ArrayList<Integer> tempsReaction = new ArrayList<>();

    /**
     * La date à laquelle la taupe apparait (pour calculer le temps de reaction du joueur).
     */
    private Date dateMoleOn;
    private Session session;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Get the session.
        Intent intent = getIntent();

        session = new Session();

        try
        {
            // Récupère la session du jeu.
            session = intent.getExtras().getParcelable("session");
        }
        catch (Exception ex)
        {
            Log.e("Error get session", ex.getMessage());
        }

        // Toutes les taupes sont au départ caché
        for(int i = 1; i <= 9; i++)
        {
            ImageButton taupeImage = GetImageButtonTaupe(i);
            taupeImage.setImageDrawable(null);

            // Evènement au clic du bouton
            taupeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Gestion du temps de réaction
                    long diff = Math.abs(dateMoleOn.getTime() - new Date().getTime());
                    tempsReaction.add((int)diff);

                    // l'id du contrôle cliqué.
                    int id = view.getId();

                    // Si l'id correspond à l'id d'une taupe
                    if (id == R.id.mole2 || id == R.id.mole3 || id == R.id.mole4 || id == R.id.mole5
                            || id == R.id.mole6 || id == R.id.mole7 || id == R.id.mole8 || id == R.id.mole9
                            || id == R.id.mole1)
                    {
                        ImageButton imageButtonTaupeVisible = GetImageButtonTaupe(idTaupeVisible);
                        // Si c'est l'id de la taupe visible
                        if (imageButtonTaupeVisible == findViewById(id))
                        {
                            // Permet d'éviter le double clic rapide (qui ferai gagner deux points d'un coup).
                            if (idLastTaupe != id)
                            {
                                idLastTaupe = id;
                                // Fin du timer des taupes en cours (seulement pour le deuxième timer).
                                //timerMole.cancel();
                                taupeTouchee = true;
                                nbPoint++;

                                // On cache la taupe visible précédement visible (sauf lors de la première seconde) (si pas de second timer).
                                imageButtonTaupeVisible.setImageDrawable(null);
                            }
                        }

                        // On relance une taupe (seulement pour le deuxième timer).
                        //HandleMole();
                    }
                }
            });
        }

        // Démarre le chronomètre
        startTimer();

        // (seulement pour le deuxième timer).
        //HandleMole();

    }


    //region HandleMole

    /**
     * Vrai si la taupe a été touché
     */
    private boolean taupeTouchee = false;

    /**
     * Sort l'une des neuf taupes aléatoirement.
     */
    private void GetRandomMole()
    {
        int oldIdTaupe = idTaupeVisible;

        while (oldIdTaupe == idTaupeVisible)
        {
            // entre 0 et 8
            idTaupeVisible = rnd.nextInt(8);
            idTaupeVisible++;
        }

        Log.e("identifiant de la taupe", String.valueOf(idTaupeVisible));
        ImageButton buttonMole = GetImageButtonTaupe(idTaupeVisible);
        buttonMole.setImageResource(R.drawable.lilmole);

        // La date système (moment auquel la taupe apparait.
        dateMoleOn = new Date();
    }

    /**
     * Gestion des taupes
     */
    protected void HandleMole()
    {
        taupeTouchee = false;

        Random r = new Random();

        // entre 500ms et 2s
        int nbMsSecond = rnd.nextInt(((2000-500)+1)) + 500;

        // Log de la seconde aléatoire.
        Log.e("random mili seconde", String.valueOf(nbMsSecond));

        // Lancement du timer de la taupe
        timerMole.schedule(new TimerTask() {
            public void run()
            {
                // Gestion de l'affichage des taupes.
               mHandlerTaupe.obtainMessage(1).sendToTarget();

               // Si le chronomètre est toujours en route.
                if (isTimerRunning)
                {
                    try
                    {
                        // On essaye d'annuler le timer
                        timerMole.cancel();
                    }
                    catch(Exception ex)
                    {
                        // On ne veut pas que l'application crache dans le cas d'une erreur lors de l'annulation du timer
                    }

                    if (!taupeTouchee)
                    {
                        nbTaupesManquees++ ;
                    }
                    // Si le chronomètre est encore en route, continue de lancer des taupes
                    HandleMole();
                }

            }
        }, 0, nbMsSecond);
    }

    /**
     * Permet de mettre à jour de l'écran pour le timer des taupes.
     */
    private Handler mHandlerTaupe = new Handler() {
        public void handleMessage(Message msg)
        {

            // On cache la taupe visible précédement visible (sauf lors de la première seconde)
            if (idTaupeVisible > 0)
            {
                ImageButton taupe = GetImageButtonTaupe(idTaupeVisible);
                taupe.setImageDrawable(null);
            }

            // Sort une nouvelle taupe.
            GetRandomMole();
        }
    };

    //endregion

    //region Hangle timer
    /**
     * Démarre le chronomètre et le mets à jour toutes les secondes
     */
    protected void startTimer()
    {
        isTimerRunning = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run()
            {
                // Si le timmer est en cours.
                if (isTimerRunning)
                {
                    // Mise à jour du TextView
                    mHandler.obtainMessage(1).sendToTarget();

                    // Gestion des taupes (dans le cas ou le deuxième timer avec un temps aléatoire ne fonctionne pas.
                    mHandlerTaupe.obtainMessage(1).sendToTarget();

                    // Timer à 0 => fin du jeu.
                    if (elapsedTime == 0)
                    {
                        // Set the text view with the new value.
                        // TO do : afficher le score ou retour vers l'accueil?
                        /*TextView stopWatch = findViewById(R.id.txtChrono);
                        stopWatch.setText();*/

                        // Le chrono est à l'arret.
                        isTimerRunning = false;

                        // Action => fin du jeu
                        HandleEndGame();
                    }

                    // Toutes les secondes le compteur perd 1
                    elapsedTime -= 1;
                }

            }
        }, 0, 1000);
}

    /**
     * Permet de mettre à jour le chronomètre.
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg)
        {
            TextView stopWatch = findViewById(R.id.txtChrono);

            // Set le text view du chronomètre avec le nouveau temps.
            stopWatch.setText(String.valueOf(elapsedTime));
        }
    };

    //endregion

    //region field fin du jeu
    /**
     * Le nombre de point obtenus durant la partie
     */
    private int nbPoint = 0;

    /**
     * Le nombre de taupes manquées au cours de la partie
     */
    private int nbTaupesManquees = 0;

    //endregion

    /**
     * Gestion des actions à la fin du jeu.
     */
    public void HandleEndGame()
    {
        // Récupère les résultats de la partie et définit le score.
        Score score = new Score();
        score.setNbPoints(nbPoint);
        score.setNbTaupesManquees(nbTaupesManquees);

        int nbTotalTempsReaction = 0;
        for (Integer time : tempsReaction)
        {
            if (score.getTempsReactionMin() == 0 || time < score.getTempsReactionMin())
            {
                score.setTempsReactionMin(time);
            }

            if (score.getTempsReactionMax() == 0 || time > score.getTempsReactionMax())
            {
                score.setTempsReactionMax(time);
            }

            nbTotalTempsReaction += time;
        }

        // Calcul temps moyen des réactions.
        score.setTempsReactionAvg(nbTotalTempsReaction/tempsReaction.size());

        // Log des valeus (pour tester).
        Log.d("nbPoint", String.valueOf(nbPoint));
        Log.d("nbTaupesManquees", String.valueOf(nbTaupesManquees));

        session.addScore(score);

        // TO DO : set result de la session

        // A la fin du jeu, on retourne sur la page d'accueil.
         finish();
    }
}

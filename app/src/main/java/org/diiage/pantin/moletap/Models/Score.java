package org.diiage.pantin.moletap.Models;

import java.io.Serializable;

/**
 * Created by Jordan on 3/15/2018.
 */

public class Score implements Serializable
{
    /**
     * Nombre de points.
     */
    private int nbPoints;

    /**
     * Nombre de taupes manquées.
     */
    private int nbTaupesManquees;

    /**
     * Temps de réaction maximun en ms.
     */
    private int TempsReactionMax;

    /**
     * Temps de réaction minimun en ms.
     */
    private int TempsReactionMin;

    /**
     * Temps de réaction moyen en ms.
     */
    private int TempsReactionAvg;

    //region getter setter

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public int getNbTaupesManquees() {
        return nbTaupesManquees;
    }

    public void setNbTaupesManquees(int nbTaupesManquees) {
        this.nbTaupesManquees = nbTaupesManquees;
    }

    public int getTempsReactionMax() {
        return TempsReactionMax;
    }

    public void setTempsReactionMax(int tempsReactionMax) {
        TempsReactionMax = tempsReactionMax;
    }

    public int getTempsReactionMin() {
        return TempsReactionMin;
    }

    public void setTempsReactionMin(int tempsReactionMin) {
        TempsReactionMin = tempsReactionMin;
    }

    public int getTempsReactionAvg() {
        return TempsReactionAvg;
    }

    public void setTempsReactionAvg(int tempsReactionAvg) {
        TempsReactionAvg = tempsReactionAvg;
    }

    //endregion
}

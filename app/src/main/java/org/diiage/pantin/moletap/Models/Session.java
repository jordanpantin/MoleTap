package org.diiage.pantin.moletap.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jordan on 3/15/2018.
 */

public class Session implements Parcelable
{
    //region fields

    /**
     * Date de début de la session
     */
    private Date dateDebut;

    /**
     * Nom du joueur
     */
    private String nomJoueur;

    /**
     * La liste des scores du joueurs
     */
    private ArrayList<Score> listScores;

    //endregion

    public Session(){
        this.listScores = new ArrayList<>();
    }

    public void addScore(Score score)
    {
        this.listScores.add(score);
    }

    protected Session(Parcel in) {
        nomJoueur = in.readString();
        dateDebut = (Date)in.readSerializable();
        listScores = (ArrayList<Score>)in.readSerializable();
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public ArrayList<Score> getListScores() {
        return listScores;
    }

    public void setListScores(ArrayList<Score> listScores) {
        this.listScores = listScores;
    }

    //region Parcelable

    public static final Creator<Session> CREATOR = new Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };

    /**
     * Pas utile.
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nomJoueur);
        parcel.writeSerializable(dateDebut);
        parcel.writeSerializable(listScores);
    }

    //endregion
}

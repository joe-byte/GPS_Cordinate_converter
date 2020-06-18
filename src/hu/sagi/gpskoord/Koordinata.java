/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.sagi.gpskoord;


public class Koordinata {
    enum Tipus {DD,DM,DMS};
    private String fok;
    private String perc;
    private String másodperc;
    private String mptört;
    private Tipus tipus;
    private boolean negatív;
    public Koordinata() {
      
    }


    public boolean isNegatív() {
        return negatív;
    }

    public void setNegatív(boolean negatív) {
        this.negatív = negatív;
    }

  
    public String getFok() {
        return fok;
    }

    public void setFok(String fok) {
        this.fok = fok;
    }

    public String getPerc() {
        return perc;
    }

    public void setPerc(String perc) {
        this.perc = perc;
    }

    public String getMásodperc() {
        return másodperc;
    }

    public void setMásodperc(String másődperc) {
        this.másodperc = másődperc;
    }

    public Tipus getTipus() {
        return tipus;
    }

    public void setTipus(Tipus tipus) {
        this.tipus = tipus;
    }

    public String getMptört() {
        return mptört;
    }

    public void setMptört(String mptört) {
        this.mptört = mptört;
    }

    @Override
    public String toString() {
        return "Koordin\u00e1ta{" + "fok=" + fok + ", perc=" + perc + ", m\u00e1sodperc=" + másodperc + ", mpt\u00f6rt=" + mptört + ", tipus=" + tipus + ", negat\u00edv=" + negatív + '}';
    }
    
    
}

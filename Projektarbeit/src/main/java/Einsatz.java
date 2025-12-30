public class Einsatz {
    public String adresse;
    public String hausNr;
    public int plz;
    public String ort;
    public String bemerkung;
    public boolean miG;
    public String stichwort;
    public boolean signalfahrt;

    public Einsatz(String adresse, String hausNr, int plz, String ort, String bemerkung, boolean miG, String stichwort, boolean signalfahrt) {
        this.adresse = adresse;
        this.hausNr = hausNr;
        this.plz = plz;
        this.ort = ort;
        this.bemerkung = bemerkung;
        this.miG = miG;
        this.stichwort = stichwort;
        this.signalfahrt = signalfahrt;
    }

    public String getAdresse(){return adresse;}
    public String getHausNr(){ return hausNr;}
    public int getPlz(){return plz;}
    public String getOrt(){return ort;}
    public String getBemerkung(){ return bemerkung;}
    public boolean getMiG(){return miG;}
    public String getStichwort(){return stichwort;}
    public boolean getSignalfahrt(){return signalfahrt;}

   // Methoden für JUnit Test
    public String volladresse() {
        return adresse + " " + hausNr + ", " + plz + " " + ort;
    }
    public boolean istDringend() {
        return miG && signalfahrt;
    }
}


package no.oslomet.cs.algdat;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] s = { "Ole" , null , "Per" , "Kari ", null };
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System. out .println(liste. antall () + " " + liste. tom ());
    }
}

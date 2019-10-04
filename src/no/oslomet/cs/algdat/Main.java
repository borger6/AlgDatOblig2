package no.oslomet.cs.algdat;

import org.junit.jupiter.api.Test;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        String[] s = { "Ole" , null , "Per" , "Kari ", null };
//        Liste<String> liste = new DobbeltLenketListe<>(s);
//        System. out .println(liste. antall () + " " + liste. tom ());
//
//        String[] s1 = {}, s2 = { "A" }, s3 = {null, "A" ,null, "B" ,null};
//        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
//        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
//        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);
//        System. out .println(l1.toString() + " " + l2.toString()
//                + " " + l3.toString() + " " + l1.omvendtString() + " "
//                + l2.omvendtString() + " " + l3.omvendtString());
//
//        DobbeltLenketListe<Integer> liste1 = new DobbeltLenketListe<>();
//        System. out .println(liste1.toString() + " " + liste1.omvendtString());
//        for ( int i = 1; i <= 3; i++)
//        {
//            liste1.leggInn(i);
//            System. out .println(liste1.toString() + " " + liste1.omvendtString());
//        }

        String[] Pokemon = {"Pikachu" , "Bulbasaur" , "Arcanine" , "Lucario", "Squirtle" };
        //String[] Pokemon = {};
        DobbeltLenketListe<String> indeksTilTest = new DobbeltLenketListe<>(Pokemon);
        System.out.println(indeksTilTest.toString());
        System.out.println(indeksTilTest.indeksTil("Arcanine"));


        System.out.println(indeksTilTest.antall());
        indeksTilTest.leggInn(4, "Bisharp");
        System.out.println(indeksTilTest.toString());
    }


    @Test
    void oppgave2(){
        String[] s = {"Ole" , null , "Per" , "Kari", null };

        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste);
    }



}

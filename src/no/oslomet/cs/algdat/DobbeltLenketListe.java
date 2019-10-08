package no.oslomet.cs.algdat;


////////////////// class DobbeltLenketListe //////////////////////////////


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;



public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;

    }

    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a, "ingen verdier i tabellen");
        hode = hale = new Node<T>(null);
        //for (T verdi : a){

        int i = 0;
        for(; i<a.length; i++){
            if(a[i] != null){
                hale = hode = new Node(a[i]);
                antall++;
                break;
            }
        }
        for(int j = i+1;j<a.length;j++){
            if (a[j] != null){
                hale = hale.neste = new Node<T>(a[j], hale, null);
                antall++;
            }
        }
        if (antall == 0){
            hode = hale = null;
        }
    }

    public Liste<T> subliste(int fra, int til){
        Liste<T> subliste = new DobbeltLenketListe<>();
        fratilKontroll(antall,fra,til);
        for(int i = fra; i<til; i++) {
            if(hent(fra) != null){
                Node<T> q = finnNode(i);
                subliste.leggInn(q.verdi);
            }
        }

        return subliste;
    }
    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");

        if (til > antall) throw new IndexOutOfBoundsException
                ("til(" + til + ") er større enn antall(" + antall + ")");

        if (fra > til)
            throw new IllegalArgumentException("fra(" + fra + ") er større enn" +
                    " til(" + til + ") Sett 'fra ' mindre enn 'til'");
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi);
        if (tom()){
            hode = hale = new Node<T>(verdi, null, null);
        }
        else {
            hale = hale.neste = new Node<T>(verdi, hale, null);
        }

        antall++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(indeks);
        Objects.requireNonNull(verdi);
        Node q = new Node<>(verdi);
        Node r;
        Node p;
        Node current;
        int teller = 0;
        indeksKontroll(indeks, true);
        if (tom()) {
            hode = hale = q;
            endringer++;
            antall++;
        }
        else if (indeks == 0){
            q.neste = hode;
            hode.forrige = q;
            hode = q;
            endringer++;
            antall++;
        }
        else if (indeks == antall){
            q.forrige = hale;
            hale.neste = q;
            hale = q;
            endringer++;
            antall++;
        }
        else {
            current = hode;
            while(teller != indeks){
                current = current.neste;
                teller++;
            }
            p = current.forrige;
            r = current;

            p.neste = q;
            r.forrige = q;
            q.forrige = p;
            q.neste = r;

            endringer++;
            antall++;
        }
    }


    @Override
    public boolean inneholder(T verdi) {
        //throw new NotImplementedException();
        int inneholder = indeksTil(verdi);
        if(inneholder == -1){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks,false);
        return finnNode(indeks).verdi;
    }

    public Node<T> finnNode(int indeks){
        Node<T> p = hode;
        Node<T> r = hale;
        Node<T> node;

        if(indeks<antall/2){
            for(int i = 0; i<indeks; i++){
                p = p.neste;
            }
            node = p;
        }
        else{
            for (int i = antall-1; i > indeks; i--){
                r = r.forrige;
            }
            node = r;
        }
        return node;
    }


    @Override
    public int indeksTil(T verdi) {
        //throw new NotImplementedException();
        //skal gaa gjennom hele linkedlisten med en while-lokke og hente stoppe der current sin verdi er samme som input verdi
        int indeks = 0; //teller hvor mange ganger while-lokken kjorer
        if(hode == null){
            return -1;
        }
        Node current = hode;
        while(!current.verdi.equals(verdi)){
            if(current.neste == null){
                indeks = -1;
                break;
            }
            current = current.neste;
            indeks++;
        }
        return indeks;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        indeksKontroll(indeks, false);
        Objects.requireNonNull(nyverdi, "Ikke lov til å legge inn null-verdier");
        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;
        p.verdi = nyverdi;
        endringer++;
        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        //throw new NotImplementedException();
        if(verdi == null){
            return false;
        }
        if (hode.equals(null)){
            return false;
        }
        Node p;
        Node r;
        Node current = hode;
        int indeks = 0;
        while (!current.verdi.equals(verdi)) {
            if (indeks < antall - 1) {
                current = current.neste;
            }
            indeks++;
            if (indeks == antall-1) {
                break;
            }
        }
        if (!current.verdi.equals(verdi)) {
            return false;
        }
        if(antall == 1){
            hode = hale = null;
            endringer++;
            antall--;
        }
        else if (indeks == 0){
            hode = hode.neste;
            hode.forrige = null;
            endringer++;
            antall--;
        }
        else if(current.equals(hale)){
            hale = hale.forrige;
            hale.neste = null;
            endringer++;
            antall--;
        }
        else{
            p = current.forrige;
            r = current.neste;

            p.neste = r;
            r.forrige = p;
            endringer++;
            antall--;
        }
        return true;
    }


    @Override
    public T fjern(int indeks) {
        //throw new NotImplementedException();
        indeksKontroll(indeks, false);
        T returnerVerdi;
        if(antall == 1 && indeks == 0){
            returnerVerdi = hode.verdi;
            hode = hale = null;
            endringer++;
            antall--;
        }
        else if(indeks == 0){
            returnerVerdi = hode.verdi;
            hode = hode.neste;
            hode.forrige.neste = null;
            hode.forrige = null;
            endringer++;
            antall--;
        }
        else if(indeks == antall-1){
            returnerVerdi = hale.verdi;
            hale = hale.forrige;
            hale.neste.forrige = null;
            hale.neste = null;
            endringer++;
            antall--;
        }
        else {
            Node p;
            Node r;
            Node<T> current = hode;
            int teller = 0;
            while (teller != indeks) {
                current = current.neste;
                teller++;
            }
            returnerVerdi = current.verdi;
            p = current.forrige;
            r = current.neste;

            p.neste = r;
            r.forrige = p;
            endringer++;
            antall--;
        }
        return returnerVerdi;
    }

    @Override
    public void nullstill() {// vi finner ingen konsekvent raskeste av de to variantene
        Node p;
        Node current = hode;
        while(!current.equals(hale)){
            current.verdi = null;
            current = current.neste;
            p = current.forrige;
            p.neste = null;
            current.forrige = null;
            endringer++;
        }
        hode = hale = null;
        antall = 0;

        /*
        int n = antall;
        for(int i = 0; i < n; i++){
            fjern(0);
            endringer++;
        }
        */
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        Node<T> current = hode;
        while (current != null){
            if (current.verdi == null){
                current = current.neste;
            }
            else {
                str.append(current.verdi);
                if (antall >= 2){
                    if (current.neste == hale.neste){
                        break;
                    }
                    str.append(", ");
                }
                current = current.neste;
            }
        }
        str.append("]");
        return str.toString();
        //throw new NotImplementedException();
    }

    public String omvendtString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        Node<T> current = hale;
        while (current != null) {
            if (current.verdi == null) {
                current = current.forrige;
            } else {
                str.append(current.verdi);
                if (antall >= 2) {
                    if (current.forrige == hode.forrige){
                        break;
                    }
                    str.append(", ");
                }
                    current = current.forrige;
            }
        }
        str.append("]");
        return str.toString();

    }

    @Override
    public Iterator<T> iterator() {
        //throw new NotImplementedException();
        Iterator<T> enIterator = new DobbeltLenketListeIterator();
        return enIterator;
    }

    public Iterator<T> iterator(int indeks) {
        //throw new NotImplementedException();
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);

    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // p starter på den første i listen
            this.fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            //throw new NotImplementedException();
            denne = hode;
            int teller = 0;
            while(teller != indeks){
                denne = denne.neste;
                teller++;
            }
            this.fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            //throw new NotImplementedException();
            if (iteratorendringer != endringer){
                throw new ConcurrentModificationException();
            }
            if (hasNext() != true){
                throw new NoSuchElementException();
            }
            this.fjernOK = true;
            T verdi = denne.verdi;
            denne = denne.neste;
            return verdi;
        }

        @Override
        public void remove(){
            if(fjernOK == false){
                throw new IllegalStateException("Ikke tillatt å kalle denne metoden");
            }
            if(endringer != iteratorendringer){
                throw new ConcurrentModificationException("Antall endringer : "+endringer+ ", er ikke det " +
                        "samme som : "+iteratorendringer);
            }

            fjernOK = false;
            if(antall==1){
                nullstill();
            }
            else if(denne == null){
                hale = hale.forrige;
                hale.neste = null;
                antall--;
            }
            else if(denne.forrige == hode){
                hode = denne;
                denne.forrige = null;
                antall--;
            }
            else{
                denne.forrige = denne.forrige.forrige;
                denne.forrige.neste = denne;
                antall--;
            }
            endringer++;
            iteratorendringer++;
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        T a;
        T b;
        T temp;

        int n = liste.antall();
        for(int i = 0; i<n-1; i++){
           a= liste.hent(i);
           b = liste.hent(i+1);
           Comparable første = (Comparable)a;
           Comparable andre = (Comparable)b;

           if(første.compareTo(andre) < 0){
               temp = liste.hent(i);
               liste.oppdater(i,b);
               liste.oppdater(i+1,temp);
           }
        }

    }

} // class DobbeltLenketListe



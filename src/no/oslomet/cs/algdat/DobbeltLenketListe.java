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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }

    @Override
    public boolean fjern(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T fjern(int indeks) {
        throw new NotImplementedException();
    }

    @Override
    public void nullstill() {
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new NotImplementedException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            throw new NotImplementedException();
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new NotImplementedException();
        }

        @Override
        public boolean hasNext(){
            throw new NotImplementedException();
        }

        @Override
        public T next(){
            throw new NotImplementedException();
        }

        @Override
        public void remove(){
            throw new NotImplementedException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new NotImplementedException();
    }

} // class DobbeltLenketListe



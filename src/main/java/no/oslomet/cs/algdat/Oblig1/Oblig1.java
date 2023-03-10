package no.oslomet.cs.algdat.Oblig1;

////// Løsningsforslag Oblig 1 ////////////////////////

import java.lang.UnsupportedOperationException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;



public class Oblig1 {

    private Oblig1() {}

    ///// Oppgave 1 //////////////////////////////////////

    public static int maks(int[] a) {       // metoden som finner det største tallet i array

        if( a.length == 0){                 // tom tabell
            throw new NoSuchElementException
                    ("tomt tabellintervall!");

        }

        for (int i = 0; i < a.length -1; ++i){
            if (a[i] > a[i +1]){        // Største tall sammenlignes med tall ved siden av
                int temp = a[i];
                a[i] = a[i+1];
                a[i+1] = temp;
            }
        }

        return a[a.length-1];   //Vis siste element i array
    }

    public static int ombyttinger(int[] a) {


        if( a.length == 0){         // tom tabell
            throw new NoSuchElementException
                    ("tomt tabellintervall!");
        }

        // teller ombytninger
        int ombytting = 0;
        for (int i = 0; i < a.length -1; ++i){
            if (a[i] > a[i +1]){
                // Bytt plass med større tall og øk antall ombytninger
                ombytting++;
                int temp = a[i];
                a[i] = a[i+1];
                a[i+1] = temp;
            }
        }

        return ombytting;   //returnerer  antall  obytninger
    }

    ///// Oppgave 2 //////////////////////////////////////

    //Tar utgangspunkt i kompendiets programkode:
    // 1. hjelpemetoden erSortert() -  1.3.2  Inversjoner og
    // 2. sortering Programkode 1.3.2 c)


    public static int antallUlikeSortert(int[] a) {

        if( a.length == 0){     //  tabellen er tomt
            return 0;
        }

        int antallLike = 1;

        // Plukker alle elementene en av gangen start fra 1
        int i = 1;
        while (i < a.length)    // looper gjennom løkken
        {
            int j = 0;

            // start fra første index av løkka og loop så lenge j < i
            while (j < i)
            {
                if (a[i] == a[j]) {         // hvis a[i] og a[j] er ulike j++ øker

                    break;                  // Hvis verdiene er like hopp ut ut av loopen og går videre til andre testen i == j
                }
                j++;
            }

            // Hvis den ikke har blitt printet tideligere,
            // printes den nå
            if (i == j) {
                antallLike++;
            }
            i++;
        }

        // Er array sortert?
        if(erSortert(a)== false){
            throw new IllegalStateException
                    ("Array er ikke sortert!");
        }

        // returnerer antall Like
        return antallLike;

    }
    public static boolean erSortert(int[] a)  // legges i samleklassen Tabell
    {
        for (int i = 1; i < a.length; i++)      // starter med i = 1
            if (a[i-1] > a[i]) return false;      // en inversjon

        return true;
    }

    ///// Oppgave 3 //////////////////////////////////////
    public static int antallUlikeUsortert(int[] a) {
        //throw new UnsupportedOperationException();
        if( a.length == 0){         // tomt tabell
            return 0;
        }
        int likeTall = 0;
        //finner antall like tall
        for (int i = 0; i < a.length; i++) {
            for (int j = i+1; j < a.length; j++){
                if (a[i] == a[j]){
                    likeTall++;
                    break;
                }
            }
        }
        // hvis vi trekker like tall fra array length skal vi få antall ulike tall
        return (a.length - likeTall);
    }

    ///// Oppgave 4 //////////////////////////////////////

    //følgende metoder er tatt fra kompendie kapittel 1.3.9  Partisjonering og kvikksortering
    // private static int parter0(int[] a, int v, int h, int skilleverdi) tatt fra - Programkode 1.3.9 a)
    //private static int sParter0(int[] a, int v, int h, int indeks)     tatt fra -Programkode 1.3.9 f)
    // metoden  kvikksortering(int[] a), kvikksortering(int[] a, int fra, int til)
    // og kvikksortering0(int[] a, int v, int h)  tatt fra  -  1.3.9 h)


    public static void delsortering(int[] a) {

        kvikksortering(a); // alle elementer i arreyet sorteres
        // siden arrayet sortert, vet vi at annehver tall er par tall
        // int oddetall++ skal øke hver gang vi møter en odde tall, og det skal samtidig frigjøre plass for å plassere par tall, hvis vi
        // møter en par tall skal i++ øke istedenfor oddetall++  og peke på neste indeks

        int oddetall = 0;

        int i = 0;
        while ( i < a.length)
        {
            if (a[i]%2 == 0) // hvis en partall - gjør ingenting
            {}
            else {          // hvis oddetall

                int temp = a[oddetall];
                a[oddetall] = a[i];
                a[i] = temp;
                oddetall++;
            }
            i++;
        }
        // Etter at partallene har blitt overført fra venstre til høtyre, er høyre siden ferdig sortert.
        // Nå er det er bare venstre siden med par tall som er usortert.  Resten av array sorteres ved hjelp av følgende metode
        kvikksortering(a, oddetall, a.length);


    }

    public static void bytt(int[] a, int i, int j)
    {
        int temp = a[i]; a[i] = a[j]; a[j] = temp;
    }

    private static int parter0(int[] a, int v, int h, int skilleverdi)
    {
        while (true)                                  // stopper når v > h
        {
            while (v <= h && a[v] < skilleverdi) v++;   // h er stoppverdi for v
            while (v <= h && a[h] >= skilleverdi) h--;  // v er stoppverdi for h

            if (v < h) bytt(a,v++,h--);                 // bytter om a[v] og a[h]
            else  return v;  // a[v] er nåden første som ikke er mindre enn skilleverdi
        }
    }
    private static int sParter0(int[] a, int v, int h, int indeks)
    {
        bytt(a, indeks, h);           // skilleverdi a[indeks] flyttes bakerst
        int pos = parter0(a, v, h - 1, a[h]);  // partisjonerer a[v:h - 1]
        bytt(a, pos, h);              // bytter for å få skilleverdien på rett plass
        return pos;                   // returnerer posisjonen til skilleverdien
    }
    private static void kvikksortering0(int[] a, int v, int h)
    {
        if (v >= h) return;  // a[v:h] er tomt eller har maks ett element
        int k = sParter0(a, v, h, (v + h)/2);  // bruker midtverdien
        kvikksortering0(a, v, k - 1);     // sorterer intervallet a[v:k-1]
        kvikksortering0(a, k + 1, h);     // sorterer intervallet a[k+1:h]
    }

    public static void kvikksortering(int[] a, int fra, int til) // a[fra:til> ****
    {
        kvikksortering0(a, fra, til - 1);  // v = fra, h = til - 1
    }

    public static void kvikksortering(int[] a)   // sorterer hele tabellen
    {
        kvikksortering0(a, 0, a.length - 1);
    }

    ///// Oppgave 5 //////////////////////////////////////
    public static void rotasjon(char[] a) {


        if(a.length < 1) // hvis vi har mindre en en index
            return;

        // sparer siste elementet i array
        char sist = a[a.length-1];

        int j = a.length-1;     // starter bakerst
        while (j > 0)
        {
            a[j] = a[j-1]; // indexsen til j minker, slik beveger vi oss en enhet mot høyre

            j--;
        }
        //Sist element blir satt på første plass
        a[0] = sist;

        //Printer rotert array
        System.out.println("Array etter høyre rotasjonen: ");
        for(int i = 0; i< a.length; i++){
            System.out.print(a[i] + " ");
        }
    }

    

    ///// Oppgave 7 //////////////////////////////////////
    /// 7a)
    public static String flett(String s, String t) {

        // lagrer endelig array
        StringBuilder stringFlettet = new StringBuilder();

        // looper gjennom strengen
        int i = 0;
        while (i < s.length() || i < t.length()){ // så lenge s-sin eller t-sin legde er lang


            if (i < s.length()){
                stringFlettet.append(s.charAt(i));
            }

            if (i < t.length()){
                stringFlettet.append(t.charAt(i));
            }

            i++;
        }
        String ny = stringFlettet.toString();       // ny setning lages
        return ny;                                  // printer setningen
    }

    /// 7b)
    public static String flett(String... s) {

        // finner den lengste verdien i arraye
        int makslengde = 0;
        int l = 0;
        while (l < s.length)
        {
            if (s[l].length() >= makslengde) {      // sjekker hvor lang ord er value.lenght()
                makslengde = s[l].length();         // hvis riktig sett 0 til value.length()
            }
            l++;
        }

        StringBuilder stringFlettet = new StringBuilder();

        // Her dannes en matrise der maks lengde er presentert med den største setning i arrayet det vil si rader i en matrise
        //  Også har vi koloner som er presentert med s. length
        for (int i = 0; i < makslengde; i++) {      //går gjennom bokstaver
            for (int j = 0; j < s.length; j++) {    // går gjennom ord

                // samme test som i oppgave a) men her må vi s[j].length sjekker om ordet er større en i
                if (s[j].length() > i) {

                    stringFlettet.append(s[j].charAt(i));       // tar ut første bokstav fra ordet setningen

                }
            }
        }
        // Print ny setning
        String ferdig = stringFlettet.toString();
        return ferdig;

    }

    ///// Oppgave 8 //////////////////////////////////////
    public static int[] indekssortering(int[] a) {
        throw new UnsupportedOperationException();
    }

    ///// Oppgave 9 //////////////////////////////////////
    public static int[] tredjeMin(int[] a) {
        throw new UnsupportedOperationException();
    }

    ///// Oppgave 10 //////////////////////////////////////
    public static int bokstavNr(char bokstav) {
        throw new UnsupportedOperationException();
    }

    public static boolean inneholdt(String a, String b) {
        throw new UnsupportedOperationException();
    }

}  // Oblig1

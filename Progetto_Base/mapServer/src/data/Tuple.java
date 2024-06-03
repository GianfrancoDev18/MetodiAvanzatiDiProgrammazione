package data;

import java.io.Serializable;
import java.util.Set;

/**
 * Classe che rappresenta una tuple come sequenza
 * {@code attributo-valore} implementa l'interfaccia {@link Serializable}.
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 * @see Item
 *
 */
public class Tuple implements Serializable {
    /**
     * array di {@link Item}
     */
    private Item[] tuple;

    /**
     * Costruttore che istanzia gli attributi di classe
     * @param size numero di item che costituira' la tupla
     */
    Tuple(int size) {
        tuple = new Item[size];
    }

    /**
     * Restituisce {@code tuple.length}.
     * @return la dimensione di {@link #tuple}
     */
    public int getLength() {
        return tuple.length;
    }

    /**
     * Restituisce l'item in posizione i.
     * @param i indice
     * @return {@link Item} in posizione i
     */
    public Item get(int i) {
        return tuple[i];
    }

    /**
     * Memorizza l'item c in posizione i.
     * @param c item d'aggiungere
     * @param i indice
     */
    public void add(Item c, int i) {
        tuple[i] = c;
    }

    /**
     * Determina la distanxza tra la tupla riferita da obj
     * e la tupla corrente. La distanza è ottenuta come la somma delle
     * distanze tra gli item in posizioni eguali nelle due tuple.
     * @param obj tupla riferita
     * @return distanza ottenuta
     */
    public double getDistance(Tuple obj) {
        double distance = 0;
        for (int i = 0; i < tuple.length; i++) {
            distance += tuple[i].distance(obj.get(i).getValue());
        }
        return distance;
    }

    /**
     * Restituisce la media delle distanza.
     * @param data oggetto da cui prendere le tuple
     * @param clusteredData indici
     * @return media delle distanze tra le tupla
     */
    public double avgDistance(Data data, Set<Integer> clusteredData) {
        double p = 0.0, sumD = 0.0;
        for (int i: clusteredData) {
            double d = getDistance(data.getItemSet(i));
            sumD += d;
        }
        p = sumD / clusteredData.size();
        return p;
    }
}

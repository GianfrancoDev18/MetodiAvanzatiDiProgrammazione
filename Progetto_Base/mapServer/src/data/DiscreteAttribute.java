package data;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe concreta data.DiscreteAttribute che estende la classe {@link Attribute} ed
 * implementa l'interfaccia {@link Comparable<DiscreteAttribute>}, {@link Iterable<String>}
 * e rappresenta un attributo discreto (categorico).
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 * @see Attribute
 */
class DiscreteAttribute extends Attribute implements Comparable<DiscreteAttribute>, Iterable<String>{
    /**
     * contenitore di tipo {@link TreeSet<String>} per memorizzare
     * i valori
     */
    TreeSet<String> values;

    /**
     * Costruttore che avvolara gli attributi della classe e quelli della super classe.
     * @param name nome attributo
     * @param index indice
     * @param values array di stringhe rappresentati il dominio dell'attributo
     */
    DiscreteAttribute(String name, int index, String[] values) {
        super(name, index);
        this.values = new TreeSet<String>();
        this.values.addAll(Arrays.asList(values));
    }

    /**
     * Restituisce l'iteratore su {@link #values}.
     * @return iteratore
     */
    public Iterator<String> iterator() {
        return this.values.iterator();
    }

    /**
     * Restituisce la dimensione di {@link #values}.
     * @return numero di valori discreti nel dominio dell'attributo
     */
    int getNumberOfDistinctValues() {
        return values.size();
    }

    /**
     * Restituisce 0, -1, 1 sulla base del risultato
     * del confronto. 0 se i due esempi includono gli stessi valori. Altrimenti il
     * risultato del compareTo(...) invocato sulla prima coppia di valori in
     * disaccordo.
     * @param o l'oggetto da comparare
     * @return intero inerente al confronto
     */
    public int compareTo(DiscreteAttribute o) {
        return this.getName().compareTo(o.getName());
    }

    /**
     * Determoma il numero di volte che il valore {@code v} compare in
     * corrispondenza dell'attributo corrente negli esempi memorizzati in
     * {@link Data} indicizzate da {@code idList}.
     * @param data oggetto {@link Data}
     * @param idList contenitore su cui iterare
     * @param v valore su cui determinare l'occorrenza
     * @return numero di occorrenze del valore discreto
     */
    int frequency(Data data, Set<Integer> idList, String v) {
        int count = 0;
        for (int i: idList) {
            if (data.getAttributeValue(i,this.getIndex()).equals(v))
                count++;
        }
        return count;
    }
}

package data;

import java.io.Serializable;
import java.util.Set;


/**
 * Classe astratta che modella un generico item ovvero una
 * coppia {@code attributo-valore}, implementa l'interfaccia {@link Serializable}.
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 *
 */
public abstract class Item implements Serializable {
    /**
     * attributo conivolto nell'item.
     */
    Attribute attribute;
    /**
     * valore assegnato all'attributo.
     */
    Object value;

    /**
     * Costruttore che avvalora gli attributi della classe.
     * @param attribute valore da inizializzare
     * @param value valore da inizializzare
     */
    Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Restituisce {@link Attribute}.
     * @return attributo coinvolto nell'item
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * Restituisce {@link Object}.
     * @return valore assegnato all'attributo
     */
    public Object getValue() {
        return value;
    }

    /**
     * Override della super classee e resitusce {@link Object} in un formato stringa.
     * @return valore coinvolto nell'item in un formato stringa
     */
    @Override
    public String toString() {
        return this.value.toString();
    }

    /**
     * Metodo astratto l'implementazione sara' diversa per item discreto e continuo.
     * @param a valore su cui calcolare la distanza
     * @return distanza calcolata
     */
    abstract double distance(Object a);

    /**
     * Modifica il membro {@link #value} assegnandoli il valore restituito da
     * {@code computePrototype}.
     * @param data insieme di tuple
     * @param clusteredData insieme di indici
     */
    public void update(Data data, Set<Integer> clusteredData) {
        this.value = data.computePrototype(clusteredData, attribute);
    }
}

package data;

/**
 * Classe che estende {@link Item} e rappresenta la coppia
 * {@code attributo discreto - valore discreto}
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 * @see Item
 */
public class DiscreteItem extends Item {
    /**
     * Costruttore che avvolara gli attributi della super classe
     * @param attribute attributo discreto da inizializzare
     * @param value valore discreto da inizializzare
     */
    DiscreteItem(DiscreteAttribute attribute, String value){
        super(attribute, value);
    }

    /**
     * Restituisce 0 se (getValue().equals(a)) , 1 altrimenti
     * @param a valore su cui calcolare la distanza
     * @return restituisce 0 o 1 in base ad a
     */
    double distance(Object a){
        return (getValue().equals(a) ? 0 : 1);}
}

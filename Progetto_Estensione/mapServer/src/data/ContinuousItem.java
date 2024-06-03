package data;

/**
 * Classe che estende la classe {@link Item} e modella una coppia
 * {@code attributo continuo - valore numerico}.
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 * @see Item
 */
class ContinuousItem extends Item {
    /**
     * Costruttore che avvolara gli attributi della super classe
     * @param attribute attributo
     * @param value valore numerico
     */
    ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    /**
     * Determina la distanza in valore assoluto tra il valore scalato memorizzato nell'
     * {@link Item} corrente e quello scalato associato al paramaetro {@code a}.
     * @param a valore da scalare
     * @return valore scalato
     */
    double distance(Object a) {
        ContinuousAttribute attribute = (ContinuousAttribute) this.getAttribute();
        return Math.abs(attribute.getScaledValue((Double) this.getValue()) - attribute.getScaledValue((Double) a));
    }

}

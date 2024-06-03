package data;

/**
 * Classe concreta a data.ContinuousAttribute che estende la classe {@link Attribute} e
 * modella un attributo continuo (numerico). Tale classe include i metodi per la
 * “normalizzazione” del dominio dell'attributo nell'intervallo [0,1] al fine da rendere
 * confrontabili attributi aventi domini diversi
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 * @see Attribute
 */
class ContinuousAttribute extends Attribute {
    /**
     * Rappresenta l'estremo superiore dell'intervallo.
     */
    private double max;

    /**
     * Rappresenta l'estremo inferiore dell'intervallo.
     */
    private double min;

    /**
     * Costruttore avvolara gli attributi di classe e quelli della super classe
     * @param name nome attributo
     * @param index indentificativo numerico
     * @param min valore minimo
     * @param max valore massimo
     */
    ContinuousAttribute(String name, int index, double min, double max){
        super(name, index);
        this.max= max;
        this.min= min;
    }

    /**
     * Calcola e restituisce il valore normalizzato del parametro
     * passato in input. La normalizzazione ha come codominio lo intervallo [0,1]. La
     * normalizzazione di v è quindi calcolata come segue.
     * @param v valore dell'attributo da normalizzare
     * @return valore normalizzato
     */
    double getScaledValue(double v){
        return (v-min)/(max-min);
    }
}

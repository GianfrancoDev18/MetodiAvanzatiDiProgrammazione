package data;

import java.io.Serializable;

/**
 * Classe astratta data.Attribute che modella l' entita' attributo,
 * implementa l'interfaccia {@link Serializable}.
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 */
public abstract class Attribute implements Serializable {
    /**
     * Nome simbolico dell'attributo.
     */
    private String name;

    /**
     * Identificativo numerico attributo.
     */
    private int index;

    /**
     * Costruttore che avvolora gli attributi della classe.
     * @param name nome dell'attributo
     * @param index identificativo numerico dell'attributo
     */
     Attribute(String name, int index){
        this.name= name;
        this.index= index;
    }

    /**
     * Restituisce {@link #name}.
     * @return nome dell'attributo
     */
    String getName(){
        return name;
    }

    /**
     * Restituisce {@link #index}.
     * @return identificativo numerico dell'attributo
     */
    int getIndex(){
        return index;
    }


    /**
     * Override della superclasse e restitusce {@code super.toString()}.
     * @return sovrascrive metodo ereditato dalla superclasse e restuisce la stringa
     * rappresentante lo stato dell'oggetto
     */
    @Override
    public String toString() {
        return super.toString();
    }
}



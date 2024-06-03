package database;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che servira' per modellare
 * ciascuna transizione, implementa l'interfaccia
 * {@link Comparable<Example>}.
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 */
public class Example implements Comparable<Example>{
	/**
	 * Contenitore di oggetti {@link List<Object>}
	 */
	private List<Object> example=new ArrayList<Object>();

	/**
	 * Aggiunge l'oggetto o in coda.
	 * @param o oggetto da aggiungere
	 */
	public void add(Object o){
		example.add(o);
	}

	/**
	 * Restitusice un {@code example.get(i)}.
	 * @param i indice
	 * @return oggetto in posizione i
	 */
	public Object get(int i){
		return example.get(i);
	}

	/**
	 * Compare l'oggetto ex e restituisce un intero
	 * @param ex oggetto da comparare.
	 * @return valore intero in base alla comparazione
	 */
	public int compareTo(Example ex) {
		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}

	/**
	 * Override della super classe e modella example in una stringa.
	 * @return Stringa che indica tutte le transazioni
	 */
	public String toString(){
		String str="";
		for(Object o:example)
			str+=o.toString()+ " ";
		return str;
	}
	
}
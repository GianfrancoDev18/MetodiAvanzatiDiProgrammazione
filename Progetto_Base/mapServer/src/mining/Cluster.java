package mining;

import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe che implementa l'interfaccia {@link Serializable}
 * e modella un cluster.
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 */
class Cluster implements Serializable {
	/**
	 * tupla che fa da centroide del custer.
	 */
	private Tuple centroid;

	/**
	 * insieme degli indici che verranno utilizzati per accedere a {@link Data}.
	 */
	private Set<Integer> clusteredData;

	/**
	 * Costruttore che avvolara e istanzia gli attributi della classe.
	 * @param centroid centroide del cluster
	 */
	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
	}

	/**
	 * Restituisce {@link #centroid}.
	 * @return centroide del Cluster
	 */
	Tuple getCentroid() {
		return centroid;
	}

	/**
	 * Computazione del centroide.
	 * @param data Insieme delle tuple
	 */
	void computeCentroid(Data data) {
		for (int i = 0; i < centroid.getLength(); i++) {
			centroid.get(i).update(data, clusteredData);
		}
	}

	/**
	 * Metodo che ritorna vero o falso in base se la tupla ha modificato il cluster o meno.
	 * @param id indice
	 * @return booleano che indica la modifica o meno
	 */
	boolean addData(int id) {
		return clusteredData.add(id);

	}

	/**
	 * Metodo che verifica se una transazione e' clusterizzata negli indici di clusteredData.
	 * @param id indice
	 * @return booleano che indica se l'indice e' gia presente o meno
	 */
	boolean contain(int id) {
		return clusteredData.contains(id);
	}


	/**
	 * Metodo che rimuove la tupla che ha cambiato il cluster.
	 * @param id indice
	 */
	void removeTuple(int id) {
		clusteredData.remove(id);
	}


	/**
	 * Override della super classe, e ritorna il centroide sotto formato di stringa
	 * @return centroide in formato stringa
	 */
	public String toString() {
		String str = "Centroid=(";
		for (int i = 0; i < centroid.getLength(); i++) {
			if(i == centroid.getLength()-1) {
				str += centroid.get(i);
			} else {
				str += centroid.get(i) + ", ";

			}
		}

		str += ")";
		return str;

	}


	/**
	 * Metodo che ritorna il centroide piu altre info piu' specifiche come la distanza ecc...
	 * @param data insieme di tuple
	 * @return centroide + info in formato stringa
	 */
	public String toString(Data data) {
		String str = "Centroid=(";
		for (int i = 0; i < centroid.getLength(); i++)
			str += centroid.get(i) + " ";
		str += ")\nExamples:\n";
		for (int i = 0; i < clusteredData.size(); i++) {
			str += "[";
			for (int j = 0; j < data.getNumberOfAttributes(); j++)
				str += data.getAttributeValue((Integer) i, j) + " ";
			str += "] dist=" + getCentroid().getDistance(data.getItemSet(i)) + "\n";
		}
		str += "\nAvgDistance=" + getCentroid().avgDistance(data, clusteredData);
		return str;

	}

}
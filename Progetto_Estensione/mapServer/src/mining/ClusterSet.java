package mining;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

import java.io.Serializable;

/**
 * Classe che implementa l'interfaccia {@link Serializable}, e rappresenta un insieme di
 * Cluster determinati da k-means
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 * @see Cluster
 */
public class ClusterSet implements Serializable {
    /**
     * Insieme di Cluster.
     */
    private Cluster[] C;

    /**
     * posizione valida per la memorizzazione di un nuovo Cluster in {@link #C}.
     */
    private int i = 0;

    /**
     * Costruttore che crea l'oggetto array riferito da C.
     * @param k dimensione array
     * @throws OutOfRangeSampleSize lancia l'eccezione in caso il k inserito e' uguale o minore di 0
     * oppure maggiore delle tuple distinte
     */
    ClusterSet(int k) throws OutOfRangeSampleSize {
        if (k <= 0) {
            throw new OutOfRangeSampleSize("k inserito errato");
        }
        C = new Cluster[k];
    }

    /**
     * Metodo che assegna il cluster di input c a {@code C[i] e incrementa i}.
     * @param c cluster d'aggiungere
     */
    void add(Cluster c) {
        C[i] = c;
        i++;
    }

    /**
     * Restituisce {@code C[i]}.
     * @param i indice
     * @return Cluster in posizione i
     */
    Cluster get(int i) {
        return C[i];
    }

    /**
     * Metodo che sceglie i centroidi, crea un cluster per ogni centroide
     * e lo memorizza in C.
     * @param data insieme di tuple
     * @throws OutOfRangeSampleSize lancia l'eccezione in caso il k inserito e' uguale o minore di 0
     * oppure maggiore delle tuple distinte
     */
    void initializeCentroids(Data data) throws OutOfRangeSampleSize {
        int[] centroidIndexes = data.sampling(C.length);
        for (int i = 0; i < centroidIndexes.length; i++) {
            Tuple centroidI = data.getItemSet(centroidIndexes[i]);
            add(new Cluster(centroidI));
        }
    }

    /**
     * Metodo che calcola la distanza tra la tupla riferita da tuple ed il
     * centroide di ciascun cluster in C e restituisce il cluster più vicino.
     * @param tuple tupla da cui calcolare la distanza
     * @return cluster più “vicino” alla tupla passata in input
     */
    Cluster nearestCluster(Tuple tuple) {
        double min = tuple.getDistance(C[0].getCentroid());
        Cluster c = C[0];
        double tmp;
        for (int i = 1; i < C.length; i++) {
            tmp = tuple.getDistance(C[i].getCentroid());
            if (tmp < min) {
                min = tmp;
                c = C[i];
            }
        }
        return c;
    }

    /**
     * Metodo che identifica e restituisce il cluster a cui la tupla
     * rappresentate l'esempio identificato da id. Se la tupla non è inclusa in
     * nessun cluster restituisce {@code null}
     * @param id indice
     * @return cluster corrente
     */
    Cluster currentCluster(int id) {
        for (int i = 0; i < C.length; i++) {
            if (C[i].contain(id)) {
                return C[i];
            }
        }
        return null;
    }

    /**
     * Metodo che calcola il nuovo centroide per ciascun cluster in C.
     * @param data insieme di tuple
     */
    void updateCentroids(Data data) {
        for (int i = 0; i < C.length; i++) {
            C[i].computeCentroid(data);
        }
    }


    /**
     * Override della super classe e restituisce una stringa fatta da ciascun centroide
     * dell’insieme dei cluster
     * @return insieme di cluster sottoforma di stringa
     */
    public String toString() {
        String output = "";
        for (int i = 0; i < C.length; i++) {
            output += C[i].toString()+"\n";
        }
        return output;
    }

    /**
     * Metodo che restituisce una stringa che descriva lo stato di
     * ciascun cluster in C
     * @param data insieme di tuple
     * @return insieme di cluster sottoforma di stringa
     */
    public String toString(Data data) {
        String str = "";
        for (int i = 0; i < C.length; i++) {
            if (C[i] != null) {
                str += i + ":" + C[i].toString(data) + "\n";
            }
        }
        return str;
    }


}
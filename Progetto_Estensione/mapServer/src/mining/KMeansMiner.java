package mining;

import data.Data;
import data.OutOfRangeSampleSize;

import java.io.*;

/**
 * Classe che implementa l'interfaccia {@link Serializable},
 * ed include l'algoritmo del Kmeans
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 * @see ClusterSet
 * @see Cluster
 */
public class KMeansMiner implements Serializable {
    /**
     * rappresenta un insieme di cluster
     */
    private ClusterSet C;

    /**
     * Costruttore di classe che crea l'oggetto array riferito da C
     * @param k numero di cluster da generare
     * @throws OutOfRangeSampleSize lancia l'eccezione in caso il k inserito e' uguale o minore di 0
     * oppure maggiore delle tuple distinte
     */
    public KMeansMiner(int k) throws OutOfRangeSampleSize {
        C = new ClusterSet(k);
    }

    /**
     * Metodo che apre il file identificato da fileName ed assegna a C cio' che sta
     * all'itnerno del file.
     * @param fileName nome del file
     * @throws IOException lancia l'eccezione in caso di file non trovato
     * @throws ClassNotFoundException lancia l'eccezione in caso di non compatibilita' delle classi
     * tra l'oggetto letto nel file con {@link #C}
     */
    public KMeansMiner(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        C = (ClusterSet) objectIn.readObject();
        fileIn.close();
    }

    /**
     * Metodo che apre il file identificato da fileName e salva l'oggetto riferito
     * da {@link #C} in tale file.
     * @param fileName nome del file
     * @throws IOException lancia l'eccezione in caso di file non trovato
     */
    public void salva(String fileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(".\\clustersetsaved\\" + fileName);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(C);
        objectOut.close();
    }


    /**
     * restituisce {@link #C}
     * @return l'insieme di cluster
     */
    public ClusterSet getC() {
        return C;
    }

    /**
     * Esegue l’algoritmo k-means eseguendo i passi dello pseudo-codice
     * @param data insieme di tuple
     * @return numero di iterazioni eseguite
     * @throws OutOfRangeSampleSize lancia l'eccezione in caso il k inserito e' uguale o minore di 0
     * oppure maggiore delle tuple distinte
     */
    public int kmeans(Data data) throws OutOfRangeSampleSize {
        int numberOfIterations = 0;
//STEP 1   . Scelta casuale di centroidi per k clusters
        C.initializeCentroids(data);
        boolean changedCluster = false;
        do {
            numberOfIterations++;
//STEP 2    Assegnazione di ciascuna riga della matrice in data al cluster avente centroide più vicino all'esempio
            changedCluster = false;
            for (int i = 0; i < data.getNumberOfExamples(); i++) {
                Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
                Cluster oldCluster = C.currentCluster(i);
                boolean currentChange = nearestCluster.addData(i);
                if (currentChange)
                    changedCluster = true;
//rimuovo la tupla dal vecchio cluster
                if (currentChange && oldCluster != null)
//il nodo va rimosso dal suo vecchio cluster
                    oldCluster.removeTuple(i);
            }
//STEP 3      Calcolo dei nuovi centroidi per ciascun cluster
            C.updateCentroids(data);
        }
        while (changedCluster);
        return numberOfIterations;
    }
}
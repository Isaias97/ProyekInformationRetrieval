/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Aureli Isaias
 */
public class DocumentToClusterSimilarity implements Comparable<DocumentToClusterSimilarity> {
    private double similarity;
    private Clustering cluster;

    public DocumentToClusterSimilarity() {
    }

    public DocumentToClusterSimilarity(double similarity, Clustering cluster) {
        this.similarity = similarity;
        this.cluster = cluster;
    }

    /**
     * @return the similarity
     */
    public double getSimilarity() {
        return similarity;
    }

    /**
     * @param similarity the similarity to set
     */
    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    /**
     * @return the cluster
     */
    public Clustering getCluster() {
        return cluster;
    }

    /**
     * @param cluster the cluster to set
     */
    public void setCluster(Clustering cluster) {
        this.cluster = cluster;
    }

    @Override
    public int compareTo(DocumentToClusterSimilarity o) {
        return Double.compare(similarity, o.getSimilarity());
}
}

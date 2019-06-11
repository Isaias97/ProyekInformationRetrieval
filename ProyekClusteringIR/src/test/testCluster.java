/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.util.ArrayList;
import model.*;

/**
 *
 * @author Aureli Isaias
 */
public class testCluster {
    public static void main(String[] args) {
        InvertedIndex index = new InvertedIndex();
        File dir = new File("G:\\NetBeansProjects\\ProyekInformationRetrieval165314023\\ProyekClusteringIR\\Dokument Directory");
        File files [] = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            Document doc = new Document();
            doc.setId((i+1));
            
            File file = files[i];
            doc.readFile((i+1), file);
            index.addNewDocument(doc);
        }
        index.makeDictionaryWithTermNumber();
        index.clustering();
        for (int i = 0; i < index.getCluster().size(); i++) {
            System.out.println("Cluster = "+i+", center = "+index.getCluster().get(i).getCenter().getId());
            for (int j = 0; j < index.getCluster().get(i).getMember().size(); j++) {
                System.out.println("ID doc = "+index.getCluster().get(i).getMember().get(j).getId()
                        +", File name = "+index.getCluster().get(i).getMember().get(j).getTitle());
            }
        }
    }
}

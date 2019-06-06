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
public class testPreCluster {
    public static void main(String[] args) {
        InvertedIndex index = new InvertedIndex();
        File dir = new File("G:\\NetBeansProjects\\ProyekInformationRetrieval165314023\\ProyekClusteringIR\\Directory");
        File files [] = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            Document doc = new Document();
            doc.setId((i+1));
            
            File file = files[i];
            doc.readFile((i+1), file);
            index.addNewDocument(doc);
        }
        index.makeDictionaryWithTermNumber();
        index.preClustering();
        for (int i = 0; i < index.getListOfDocument().size(); i++) {
            ArrayList<Posting> listPosting = 
                    index.getListOfDocument().get(i).getListOfClusteringPosting();
            System.out.println("IdDoc = "+index.getListOfDocument().get(i).getId());
            for (int j = 0; j < listPosting.size(); j++) {
                System.out.println(listPosting.get(j));
            }
        }
    }
}

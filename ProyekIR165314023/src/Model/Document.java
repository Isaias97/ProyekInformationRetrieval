/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javafx.print.Collation;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.id.IndonesianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

/**
 *
 * @author Aureli Isaias
 */
public class Document implements Comparable<Document>{
    private int id;
    private String content;
    private String realContent;

    public Document(int id) {
        this.id = id;
    }

    public Document() {
    }

    public Document(String content) {
        this.content = content;
        this.realContent = content;
    }

    public Document(int id, String content) {
        this.id = id;
        this.content = content;
        this.realContent = content;
    }
    
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    public String getRealContent() {
        return realContent;
    }

    public void setRealContent(String realContent) {
        this.realContent = realContent;
    }
    
    public String[] getListofTerm(){
        String value = this.getContent();
        value = value.replaceAll("[.,?!]", "");
        return value.split(" ");
    }
    
    public ArrayList<Posting> getListofPosting(){
        // panggil fungsi getListofTerm
        String [] term = getListofTerm();
        // buat objek Arraylist<Posting> untuk menampung hasil
        ArrayList<Posting> result = new ArrayList<Posting>();
        // buat looping sebanyak listofTerm
        for (int i = 0; i < term.length; i++) {            
            // di dalam looping
            // jika term pertama maka
            if (i == 0) {
                // buat objek tempPosting
                // set atribut document, gunakan "this"
                Posting tempPosting = new Posting(term[0],this);
                // tambahkan ke ArrayList result
                result.add(tempPosting);
            }
            // lainnya
            else {
                // sorting ArrayList result
                Collections.sort(result);
                // cek apakah term sudah ada
                // gunakan fungsi search dengan luaran indek objek yang memenuhi
                Posting tempPosting = new Posting(term[i], this);
                int cari = Collections.binarySearch(result, tempPosting);
                // jika hasil cari kurang dari 0(objek tidak ada)
                if (cari < 0) {                    
                    // set atribut document, gunakan "this"                    
                    // tambahkan ke ArrayList result
                    result.add(tempPosting);
                }
                // lainnya (objek ada)
                else {
                    // ambil postingnya,                    
                    // tambahkan atribut numberofTerm dengan 1 
                    // dng fungsi get(indekhasilCari).getNumberofTerm
                    int tempNumber = result.get(cari).getNumberOfTerm() + 1;
                    
                    result.get(cari).setNumberOfTerm(tempNumber);
                }
            }
        }
        return result;
    }
    
    public void readFile(int id, File file){
        // simpan id doc
        this.id = id;
        // baca file
        try {
            // menyimpan file ke objek bacaFile
            FileReader bacaFile = new FileReader(file);
            // menyimpan bacaFile ke bjek bufReader
            BufferedReader bufReader = new BufferedReader(bacaFile);
            // menyiapkan variable str bertipe String
            String str;
            // melakukan looping 
            while ((str = bufReader.readLine()) != null) {            
                // menyimpan str ke content
                this.setContent(str);
                this.setRealContent(str);
            }
            // menutup bufReader
            bufReader.close();
        }  
        // apabila terjadi error maka akan menampilkan pesan
        catch (FileNotFoundException f){            
            System.out.println("File not found");
        }        
        catch (IOException e) {
            System.out.println(e.toString());
        }
        this.IndonesiaStem();
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", content=" + content + ", realContent=" + realContent + '}';
    }
    
    @Override
    public int compareTo(Document t) {
        return id - t.getId();
    }
    
    /* 
    fungsi untuk menghilankan kata
    */
    public void removeStopWord(){
        String text = content;
        Version matchVersion = Version.LUCENE_7_7_0;
        Analyzer analyzer = new StandardAnalyzer();
        analyzer.setVersion(matchVersion);
        
        //ambil stopwords
        CharArraySet stopword = EnglishAnalyzer.getDefaultStopSet();
        // buat token
        TokenStream tokenStream = analyzer.tokenStream("myField", 
                new StringReader(text.trim()));
        
        tokenStream = new StopFilter(tokenStream, stopword);
        
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {                
                String term = charTermAttribute.toString();
                sb.append(term + " ");
            }
        } catch (Exception e) {
            System.out.println("Exection: "+e);
        }
        content = sb.toString();
    }
    
    /**
     * fungsi untuk menghilangkan stop word dan steaming
     */
    public void Stemming(){
        String text = content;
        Version matchVersion = Version.LUCENE_7_7_0;
        Analyzer analyzer = new StandardAnalyzer();
        analyzer.setVersion(matchVersion);
        
        // buat token
        TokenStream tokenStream = analyzer.tokenStream("myField", 
                new StringReader(text.trim()));
        
        tokenStream = new PorterStemFilter(tokenStream);
        
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {                
                String term = charTermAttribute.toString();
                sb.append(term + " ");
            }
        } catch (Exception e) {
            System.out.println("Exection: "+e);
        }
        content = sb.toString();
    }
    
    public void IndonesiaStem(){
        Version matchVersion = Version.LUCENE_7_7_0;
        Analyzer analyzer = new IndonesianAnalyzer();
        analyzer.setVersion(matchVersion);
        
        //ambil stopwords
        CharArraySet stopword = IndonesianAnalyzer.getDefaultStopSet();
        // buat token
        TokenStream tokenStream = analyzer.tokenStream("myField", 
                new StringReader(realContent.trim()));
        
        tokenStream = new StopFilter(tokenStream, stopword);
        
        StringBuilder sb = new StringBuilder();
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {                
                String term = charTermAttribute.toString();
                sb.append(term + " ");
            }
        } catch (Exception e) {
            System.out.println("Exection: "+e);
        }
        content = sb.toString();
    }
}

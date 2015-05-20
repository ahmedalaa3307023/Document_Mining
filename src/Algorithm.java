import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by karim on 5/19/15.
 */
public class Algorithm {
    public static String documentDirectory = "classic";
    public static double tfidfThreshold = 0.99;
    public static int hypernymCount = 5;
    public static ArrayList<DocumentTermFrequency> documents;
    public static HashMap<String, WordInfo> wordsVector;
    private static Wordnet wordnet;
    public static String mainDirectory = "C:\\Users\\AhmedA\\Desktop\\Data_mining_project\\";



    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        wordsVector = new HashMap<>();
        documents = new ArrayList<>();
        //apply preprocessing and write to file
        Preprocess.apply(getFiles(mainDirectory+documentDirectory), documents, wordsVector);
        //close wordnet
        wordnet.close();

        Preprocess.saveToFile();

        int count = 0;
        //update minmax + check if any word is empty in all documents
        for(String w: wordsVector.keySet())
        {
            wordsVector.get(w).updateMinMaxAvg(w, wordsVector.size());
        }

        System.out.println("Total: " + (System.currentTimeMillis() - start) / 1000f + " seconds");
        System.out.println("Count of Failures: " + count);


    }

    public static Wordnet getWordnet(){
        if(wordnet == null)
        {
            wordnet = new Wordnet(true);
        }
        return wordnet;
    }

    private static ArrayList<File> getFiles(String folderName) {
        File folder = new File(folderName);
        ArrayList<File> files = new ArrayList<File>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                getFiles(fileEntry.getName());
            } else {
                files.add(fileEntry);
            }
        }
        return files;
    }
}

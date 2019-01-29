/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FeatureExtractorLibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FeatureExtractorLibraryMainTest is the class I used to test the
 * FeatureExtractor class
 *
 * @author Krisztian Nemeth
 * @version 1.0
 * @since 23 ‎July, ‎2018
 */
public class FeatureExtractorLibraryMainTest {

    /**
     * This is the function that contains invocations of the FeatureExtractor
     * class functions
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @since 23 ‎July, ‎2018
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Settings.usingFrames(128);
        String IOFolder = "../FeatureExtractorLibrary_IO_files/";
        /*try {
            FeatureExtractor.extractFeaturesFromCsvFileToCsvFile("in1.csv","out2.csv");
        } catch (FeatureExtractorException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //try {
            try {
            Settings.setOutputHasHeader(true);
            Settings.setOutputFileType(Settings.FileType.ARFF);
            Settings.usingCycles();
            //Settings.setNumStepsIgnored(2);
            FeatureExtractor.extractFeaturesFromCsvFileToFile(IOFolder + "rawdata_WsY044SgeaeZtDrQKVpRyWpo7hx1_20181212_000000.csv", IOFolder + "features_WsY044SgeaeZtDrQKVpRyWpo7hx1_20181212_000000");
            } catch (FeatureExtractorException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /*try {
            //Settings.setOutputHasHeader(true);
            Settings.setDefaultUserId("dasdsaa");
            Settings.setOutputFileType(Settings.FileType.CSV);
            FeatureExtractor.extractFeaturesFromCsvFileToFile("rawdata_in11.csv", "features_LnntbFQGpBeHx3RwMu42e2yOks32_20181130_164700");
            } catch (FeatureExtractorException ex) {
                Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            //System.out.println((new Util()).arrayListOfFeaturesToInstances(FeatureExtractor.extractFeaturesFromCsvFileToArrayListOfFeatures("rawdata_LnntbFQGpBeHx3RwMu42e2yOks32_20181130_164700.csv")));
        //} catch (FeatureExtractorException ex) {
            //Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        //}
        System.out.println("done");
        System.out.println(Settings.getAllSettings());
        /*
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("in1.csv"));
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file " + "in1.csv");
            System.exit(1);
        }

        if (Settings.getInputHasHeader() && scanner.hasNextLine()) {
            scanner.nextLine();
        }

        ArrayList<Accelerometer> dataset = new ArrayList<>();
        while (scanner.hasNextLine()) {  //lines starting the first index 

            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }
            String items[] = line.split(",");
            if (items.length != 5) {
                System.out.println("Corrupted input file error");
                return;
            }
            dataset.add(new Accelerometer(Long.parseLong(items[0]),
                    Double.parseDouble(items[1]),
                    Double.parseDouble(items[2]),
                    Double.parseDouble(items[3]),
                    Integer.parseInt(items[4])));
        }
        Settings.usingCycles();
        try {
            FeatureExtractor.extractFeaturesFromArrayListToCsvFile(dataset, "out4.csv", "1");
        } catch (FeatureExtractorException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            System.out.println(FeatureExtractor.extractFeaturesFromArrayListToArrayListOfFeatures(dataset, "1"));
        } catch (FeatureExtractorException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("done");*/
        //mergeArffFilesEqually("features_Dummy.arff", "features_L.arff");
    }

    public static void mergeArffFiles(String input, String output) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(input));
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file " + input);
        }

        StringBuilder sb = new StringBuilder();
        Scanner scanner2 = null;

        try {
            scanner2 = new Scanner(new File(output));
        } catch (Exception ex) {
            System.out.println("File not found: " + output);
        }

        FileWriter writer = null;

        String line2 = null;
        while (scanner2.hasNextLine()) {
            line2 = scanner2.nextLine().trim();
            if (line2.contains("@attribute userID") || line2.contains("@attribute userid") || line2.contains("@attribute userId")) {
                break;
            }
            sb.append(line2 + "\n");
        }
        String line = null;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();
            if (line.contains("@attribute userID") || line.contains("@attribute userid") || line.contains("@attribute userId")) {
                break;
            }
        }

        String item1 = line.split(" ")[2];
        String item2 = line2.split(" ")[2];
        sb.append("@attribute userID{" + item1.substring(1, item1.length() - 1) + "," + item2.substring(1, item2.length() - 1) + "}\n\n");
        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();
            if (line.equals("@data")) {
                break;
            }
        }

        while (scanner2.hasNextLine()) {

            line = scanner2.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }
            sb.append(line + "\n");
        }
        try {
            writer = new FileWriter(output, false);
        } catch (Exception ex) {
            System.out.println("File not found: " + output);
        }
        try {
            writer.write(sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (scanner.hasNextLine()) {

            line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            try {
                //writer.println(line);
                writer.write(line + "\n");
            } catch (IOException ex) {
                Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        scanner.close();
        scanner2.close();
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mergeArffFilesEqually(String input, String output) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(input));
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file " + input);
        }

        StringBuilder sb = new StringBuilder();
        Scanner scanner2 = null;

        try {
            scanner2 = new Scanner(new File(output));
        } catch (Exception ex) {
            System.out.println("File not found: " + output);
        }

        FileWriter writer = null;

        String line2 = null;
        while (scanner2.hasNextLine()) {
            line2 = scanner2.nextLine().trim();
            if (line2.contains("@attribute userID") || line2.contains("@attribute userid") || line2.contains("@attribute userId")) {
                break;
            }
            sb.append(line2 + "\n");
        }
        String line = null;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();
            if (line.contains("@attribute userID") || line.contains("@attribute userid") || line.contains("@attribute userId")) {
                break;
            }
        }

        String item1 = line.split(" ")[2];
        String item2 = line2.split(" ")[2];
        sb.append("@attribute userID{" + item1.substring(1, item1.length() - 1) + "," + item2.substring(1, item2.length() - 1) + "}\n\n");
        
        try {
            writer = new FileWriter(output, false);
        } catch (Exception ex) {
            System.out.println("File not found: " + output);
        }
        
        try {
            writer.write(sb.toString());
            sb.delete(0, sb.length());
        } catch (IOException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (scanner2.hasNextLine()) {
            line = scanner2.nextLine().trim();
            if (line.equals("@data")) {
                break;
            }
        }

        int counter=0;
        while (scanner2.hasNextLine()) {
            
            line = scanner2.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }
            counter++;
            sb.append(line + "\n");
        }
        
        while (scanner.hasNextLine() && counter>0) {
            
            line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }
            counter--;
            try {
                //writer.println(line);
                writer.write(line + "\n");
            } catch (IOException ex) {
                Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //System.out.println(sb);
        //sb.delete(counter, sb.length());
        try {
            writer.write(sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        scanner.close();
        scanner2.close();
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mergeArffFiles2(String input, String output){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(input));
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file " + input);
        }

        StringBuilder sb=new StringBuilder();
        Scanner scanner2 = null;

        try {
            scanner2 = new Scanner(new File(output));
        } catch (Exception ex) {
            System.out.println("File not found: "+output);
        }

        FileWriter writer = null;

        String line2=null;
        while(scanner2.hasNextLine()){
            line2 = scanner2.nextLine().trim();
            if(line2.contains("@attribute userID") || line2.contains("@attribute userid") || line2.contains("@attribute userId")){
                break;
            }
            sb.append(line2+"\n");
        }
        String line=null;
        while(scanner.hasNextLine()){
            line = scanner.nextLine().trim();
            if(line.contains("@attribute userID") || line.contains("@attribute userid") || line.contains("@attribute userId")){
                break;
            }
        }

        String item1=line.split(" ")[2];
        String item2=line2.split(" ")[2];
        sb.append("@attribute userID{"+ item1.substring(1,item1.length()-1) +","+ item2.substring(1,item2.length()-1) +"}\n\n");
        while(scanner.hasNextLine()){
            line = scanner.nextLine().trim();
            if(line.equals("@data")){
                break;
            }
        }

        //int counter=0;
        while (scanner2.hasNextLine()) {
            //TODO counter++;
            line = scanner2.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }
            sb.append(line+"\n");
        }
        try {
            writer = new FileWriter(output, false);
        } catch (Exception ex) {
            System.out.println("File not found: "+output);
        }
        try {
            writer.write(sb.toString());
        } catch (IOException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (scanner.hasNextLine()) {
            //TODO counter++;
            line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            try {
                //writer.println(line);
                writer.write(line+"\n");
            } catch (IOException ex) {
                //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        scanner.close();
        scanner2.close();
        try {
            writer.close();
        } catch (IOException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

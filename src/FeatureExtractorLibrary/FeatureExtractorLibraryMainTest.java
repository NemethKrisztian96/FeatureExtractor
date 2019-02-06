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
     * class functions with the specified settings
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @since 23 ‎July, ‎2018
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //THESE ARE ALL JUST TEST
        //THEY CAN GIVE YOU INSIGHT ABOUT THE LIBRARY'S USAGE
        
        Settings.usingFrames(128);
        String IOFolder = "../FeatureExtractorLibrary_IO_files/";
        
        try {
            Settings.setOutputHasHeader(true);
            Settings.setOutputFileType(Settings.FileType.ARFF);
            //Settings.usingCycles();
            //Settings.setNumStepsIgnored(2);
            //Settings.setNumFramesIgnored(2);
            FeatureExtractor.extractFeaturesFromCsvFileToFile(IOFolder + "rawdata_WsY044SgeaeZtDrQKVpRyWpo7hx1_20181212_133019.csv", IOFolder + "features_WsY044SgeaeZtDrQKVpRyWpo7hx1_20181212_133019");
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
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FeatureExtractorLibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
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
     * class functions with the specified settings, also containing a more
     * detailed usage example
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @since 23 ‎July, ‎2018
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //TestUsageExamples();

        String IOFolder = "../FeatureExtractorLibrary_IO_files/";
        String inputFileName = "rawdata_LnntbFQGpBeHx3RwMu42e2yOks32_20181130_164700.csv";
        String outputFileName = "features_LnntbFQGpBeHx3RwMu42e2yOks32_20181130_164700.arff";

        //Settings.useRecommendedSettingsWithCycles();
        Settings.useRecommendedSettingsWithFrames();
        System.out.println(FeatureExtractor.getFilenameWithoutExtension(IOFolder+outputFileName));
        //now based on the previous settings we are extracting features from the input file
        try {
            //extracting into a file
            FeatureExtractor.extractFeaturesFromCsvFileToFile(IOFolder + inputFileName, IOFolder + outputFileName);
            
            //extracting into a list
            List<Feature> featureList = FeatureExtractor.extractFeaturesFromCsvFileToArrayListOfFeatures(IOFolder + inputFileName);
            System.out.println(featureList);
        } catch (FeatureExtractorException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void TestUsageExamples() {
        //THESE ARE ALL JUST TESTS
        //THEY CAN GIVE YOU INSIGHT ABOUT THE LIBRARY'S USAGE

        //String IOFolder = "../FeatureExtractorLibrary_IO_files/";
        String IOFolder = "data/";
        String inputFileName = "data_ttJMxBAjuHNVLCKhaXNvBTFDbIc2_20181122_161810.csv";
        String outputFileName = "features_data_ttJMxBAjuHNVLCKhaXNvBTFDbIc2_20181122_161810";

        //settings regarding the input and output files
        Settings.setInputHasHeader(true); //input has a header that has to be skipped
        Settings.setOutputHasHeader(true); //output will have a header
        Settings.setOutputFileType(Settings.FileType.ARFF); //output will be an .arff file
        Settings.setDefaultUserId("dummy");

        //if we would like to use walking cycles based feature extraction
        //Settings.usingCycles(); 
        //Settings.setNumStepsIgnored(1); //ignoring first and last step
        //if we would like to use walking cycles based feature extraction
        Settings.usingFrames(128); //using frames made of 128 datapoints
        Settings.setNumFramesIgnored(2); //ignoring first and last 2 frames (256 datapoints in this scenario)

        Settings.usingPreprocessing(true);
        Settings.setUseDynamicPreprocessingThreshold(true);
        Settings.setPreprocessingInterval(128);

        //now based on the previous settings we are extracting features from the input file
        try {
            //extracting into a file
            FeatureExtractor.extractFeaturesFromCsvFileToFile(IOFolder + inputFileName, outputFileName);

            //extracting into a list
            //List<Feature> featureList = FeatureExtractor.extractFeaturesFromCsvFileToArrayListOfFeatures(IOFolder + inputFileName);
            //System.out.println(featureList);
        } catch (FeatureExtractorException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        //now we are extracting features from an ArrayList<Feature>
        //first we read the data
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(IOFolder + inputFileName));
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file " + IOFolder + inputFileName);
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
            dataset.add(new Accelerometer(Long.parseLong(items[0]), //timesptamp
                    Double.parseDouble(items[1]), //X
                    Double.parseDouble(items[2]), //Y
                    Double.parseDouble(items[3]), //Z
                    Integer.parseInt(items[4])));                    //stepNumber
        }

        //exract features from the dataset
        try {
            //extracting into a file
            FeatureExtractor.extractFeaturesFromArrayListToFile(dataset, outputFileName, Settings.getDefaultUserId());

            //extracting into a list
            List<Feature> featureList2 = FeatureExtractor.extractFeaturesFromArrayListToArrayListOfFeatures(dataset, Settings.getDefaultUserId());
            //System.out.println(featureList2);
        } catch (FeatureExtractorException ex) {
            Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

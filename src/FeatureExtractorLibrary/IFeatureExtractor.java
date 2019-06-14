/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FeatureExtractorLibrary;

import java.util.ArrayList;

/**
 * This is an interface used to implement a class that extracts features from a
 * dataset given as an ArrayList of Accelerometer objects or a CSV file.
 *
 * @author Krisztian Nemeth
 * @version 1.0
 * @since 23 ‎July, ‎2018
 */
public interface IFeatureExtractor {

    /**
     * Extracts features from the dataset.
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param dataset ArrayList containing the data as Accelerometer object
     * @param userId subject Id
     * @return ArrayList containing the resulting Feature objects
     * @since 23 ‎July, ‎2018
     */
    //public ArrayList<Feature> extractFeaturesFromArrayListToArrayListOfFeatures(ArrayList<Accelerometer> dataset, String userId);

    /**
     * Extracts features from the dataset into a file that's type is specified
     * in the Settings.
     *
     * @author Krisztian Nemeth
     * @version 1.1
     * @param dataset ArrayList containing the data as Accelerometer object
     * @param userId subject Id
     * @param filename output file name that will contain the features
     * @since 23 ‎July, ‎2018
     */
    //public void extractFeaturesFromArrayListToFile(ArrayList<Accelerometer> dataset, String userId, String filename);

    /**
     * Extracts features from the given file.
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param inputFileName file name that contains the userID in
     * rawdata_userId_date_time format and the input data
     * @return ArrayList containing the resulting Feature objects
     * @since 23 ‎July, ‎2018
     */
    //public ArrayList<Feature> extractFeaturesFromCsvFileToArrayListOfFeatures(String inputFileName);

    /**
     * Extracts features from the given file to an output file that's type is
     * specified in the Settings.
     *
     * @author Krisztian Nemeth
     * @version 1.1
     * @param inputFileName file name containing the input data
     * @param outputFileName output file name that will contain the features
     * @since 23 ‎July, ‎2018
     */
    //public void extractFeaturesFromCsvFileToFile(String inputFileName, String outputFileName);
}

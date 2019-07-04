/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FeatureExtractorLibrary;

import java.util.ArrayList;
import java.util.List;
import weka.core.Instances;

/**
 * A class containing useful methods
 *
 * @author Krisztian Nemeth
 * @version 1.0
 * @since 23 ‎July, ‎2018
 */
public interface IUtil {

    /**
     * Creates a list off Attribute objects from a list of Feature objects.
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param features the ArrayList containing the Feature objects
     * @return ArrayList containing the resulting Attribute objects
     * @since 23 ‎July, ‎2018
     */
    public Instances arrayListOfFeaturesToInstances(ArrayList<Feature> features);

    /**
     * Function that preprocesses an input list based on the given settings
     * regarding preprocessing threshold and interval in the Settings class.
     *
     * @param inputData a List containing the Accelerometer data
     * @return the preprocessed list
     */
    public List<Accelerometer> preprocess(List<Accelerometer> inputData);

    /**
     * Function that does a min-max normalization on the given input in
     * accordance with the formula: y = (x - min) / (max - min)
     *
     * @param inputList the Accelerometer data
     * @param min the minimum value for the normalization
     * @param max the maximum value for the normalization
     * @return the normalized list
     */
    public List<Accelerometer> normalize(List<Accelerometer> inputList, double min, double max);
}

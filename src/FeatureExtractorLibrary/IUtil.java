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
 *
 * @author claudiu
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

    public List<Accelerometer> preprocess(List<Accelerometer> inputData);
}

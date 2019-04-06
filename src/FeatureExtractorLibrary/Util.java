/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FeatureExtractorLibrary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * A class containing useful methods
 *
 * @author Krisztian Nemeth
 * @version 1.0
 * @since 23 ‎July, ‎2018
 */
public class Util implements IUtil {

    //private ProtectedProperties classVal;
    private ArrayList attributes;
    //private Attribute userId;

    /**
     * Constructor
     */
    public Util() {}
    
    /**
     * Function that preprocesses an input list based on the given settings 
     * regarding preprocessing threshold and interval in the Settings class.
     * @param inputData
     * @return the preprocessed list
     */
    @Override
    public List<Accelerometer> preprocess(List<Accelerometer> inputData) {
        if (Settings.isUsingDynamicPreprocessingThreshold()) {
            Settings.setPreprocessingThreshold(magnitudeMean(inputData));
        }

        List<Accelerometer> outputData = new ArrayList<>();

        int incrementationValue = Settings.getPreprocessingInterval() / 10;
        for (int i = 0; i < inputData.size(); i += incrementationValue) { //making bigger steps for faster performance
            if (shouldEliminateInterval(inputData, i)) {
                //System.out.print(i+" ");
                i += Settings.getPreprocessingInterval(); //skipping the useless data
                i -= incrementationValue;
            } else { //keeping the useful data
                for (int j = i; j < i + incrementationValue; ++j) {
                    outputData.add(inputData.get(j));
                }
            }
        }

        return outputData;
    }

    private static double calculateMagnitude(Accelerometer dataPoint) {
        return Math.sqrt(dataPoint.getX() * dataPoint.getX() + dataPoint.getY() * dataPoint.getY() + dataPoint.getZ() * dataPoint.getZ());
    }

    private static double magnitudeMean(List<Accelerometer> data) {
        double sum = 0.0;

        for (Accelerometer accel : data) {
            sum += calculateMagnitude(accel);
        }

        return sum / data.size();
    }

    private static boolean shouldEliminateInterval(List<Accelerometer> data, int startIndex) {
        int intervalSize;
        if (data.size() - startIndex > Settings.getPreprocessingInterval()) {
            intervalSize = Settings.getPreprocessingInterval();
        } else {
            intervalSize = data.size() - startIndex;
        }

        for (int i = startIndex; i < startIndex + intervalSize; ++i) {
            if (calculateMagnitude(data.get(i)) > Settings.getPreprocessingThreshold()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Instances arrayListOfFeaturesToInstances(ArrayList<Feature> features) {
        ArrayList<String> userids = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for (Feature f : features) {
            set.add(f.getUserId());
        }
        userids.addAll(set);

        ArrayList<Attribute> attributes = getAllAtributes(userids);

        Instances data = new Instances("accelerometer", attributes, 0);

        //Enumeration<Attribute> attribs = data.enumerateAttributes();
        //while (attribs.hasMoreElements()) {
        //attributes.add(attribs.nextElement());
        //}
        int i = 0;
        for (Feature f : features) {
            data.add(createInstance(f.getMinX(), f.getMinY(), f.getMinZ(), f.getMinMag(),
                    f.getAvgAccelerationX(), f.getAvgAccelerationY(), f.getAvgAccelerationZ(), f.getAvgAccelerationMag(),
                    f.getStdDevX(), f.getStdDevY(), f.getStdDevZ(), f.getStdDevMag(),
                    f.getAvgAbsDiffX(), f.getAvgAbsDiffY(), f.getAvgAbsDiffZ(), f.getAvgAbsDiffMag(),
                    f.getZeroCrossingX(), f.getZeroCrossingY(), f.getZeroCrossingZ(),
                    f.getBinsX(), f.getBinsY(), f.getBinsZ(), f.getBinsMag(),
                    f.getUserId()));
            Attribute userId = (Attribute) attributes.get(attributes.size() - 1);
            data.get(i).setValue(userId, f.getUserId());
            i++;
        }

        data.setClassIndex(data.numAttributes() - 1);

        return data;
    }

    private ArrayList<Attribute> getAllAtributes(ArrayList<String> userids) {
        Attribute minimum_for_axis_X;
        Attribute minimum_for_axis_Y;
        Attribute minimum_for_axis_Z;
        Attribute minimum_for_magnitude;
        Attribute average_acceleration_for_axis_X;
        Attribute average_acceleration_for_axis_Y;
        Attribute average_acceleration_for_axis_Z;
        Attribute average_acceleration_for_magnitude;
        Attribute standard_deviation_for_axis_X;
        Attribute standard_deviation_for_axis_Y;
        Attribute standard_deviation_for_axis_Z;
        Attribute standard_deviation_for_magnitude;
        Attribute average_absolute_difference_for_axis_X;
        Attribute average_absolute_difference_for_axis_Y;
        Attribute average_absolute_difference_for_axis_Z;
        Attribute average_absolute_difference_for_magnitude;
        Attribute zero_crossing_rate_for_axis_X;
        Attribute zero_crossing_rate_for_axis_Y;
        Attribute zero_crossing_rate_for_axis_Z;
        Attribute bin0_X;
        Attribute bin1_X;
        Attribute bin2_X;
        Attribute bin3_X;
        Attribute bin4_X;
        Attribute bin5_X;
        Attribute bin6_X;
        Attribute bin7_X;
        Attribute bin8_X;
        Attribute bin9_X;
        Attribute bin0_Y;
        Attribute bin1_Y;
        Attribute bin2_Y;
        Attribute bin3_Y;
        Attribute bin4_Y;
        Attribute bin5_Y;
        Attribute bin6_Y;
        Attribute bin7_Y;
        Attribute bin8_Y;
        Attribute bin9_Y;
        Attribute bin0_Z;
        Attribute bin1_Z;
        Attribute bin2_Z;
        Attribute bin3_Z;
        Attribute bin4_Z;
        Attribute bin5_Z;
        Attribute bin6_Z;
        Attribute bin7_Z;
        Attribute bin8_Z;
        Attribute bin9_Z;
        Attribute bin0_magnitude;
        Attribute bin1_magnitude;
        Attribute bin2_magnitude;
        Attribute bin3_magnitude;
        Attribute bin4_magnitude;
        Attribute bin5_magnitude;
        Attribute bin6_magnitude;
        Attribute bin7_magnitude;
        Attribute bin8_magnitude;
        Attribute bin9_magnitude;
        Attribute userId;

        minimum_for_axis_X = new Attribute("minimum_for_axis_X");
        minimum_for_axis_Y = new Attribute("minimum_for_axis_Y");
        minimum_for_axis_Z = new Attribute("minimum_for_axis_Z");
        minimum_for_magnitude = new Attribute("minimum_for_magnitude");
        average_acceleration_for_axis_X = new Attribute("average_acceleration_for_axis_X");
        average_acceleration_for_axis_Y = new Attribute("average_acceleration_for_axis_Y");
        average_acceleration_for_axis_Z = new Attribute("average_acceleration_for_axis_Z");
        average_acceleration_for_magnitude = new Attribute("average_acceleration_for_axis_magnitude");
        standard_deviation_for_axis_X = new Attribute("standard_deviation_for_axis_X");
        standard_deviation_for_axis_Y = new Attribute("standard_deviation_for_axis_Y");
        standard_deviation_for_axis_Z = new Attribute("standard_deviation_for_axis_Z");
        standard_deviation_for_magnitude = new Attribute("standard_deviation_for_axis_magnitude");
        average_absolute_difference_for_axis_X = new Attribute("average_absolute_difference_for_axis_X");
        average_absolute_difference_for_axis_Y = new Attribute("average_absolute_difference_for_axis_Y");
        average_absolute_difference_for_axis_Z = new Attribute("average_absolute_difference_for_axis_Z");
        average_absolute_difference_for_magnitude = new Attribute("average_absolute_difference_for_axis_magnitude");
        zero_crossing_rate_for_axis_X = new Attribute("zero_crossing_rate_for_axis_X");
        zero_crossing_rate_for_axis_Y = new Attribute("zero_crossing_rate_for_axis_Y");
        zero_crossing_rate_for_axis_Z = new Attribute("zero_crossing_rate_for_axis_Z");
        bin0_X = new Attribute("bin0_X");
        bin1_X = new Attribute("bin1_X");
        bin2_X = new Attribute("bin2_X");
        bin3_X = new Attribute("bin3_X");
        bin4_X = new Attribute("bin4_X");
        bin5_X = new Attribute("bin5_X");
        bin6_X = new Attribute("bin6_X");
        bin7_X = new Attribute("bin7_X");
        bin8_X = new Attribute("bin8_X");
        bin9_X = new Attribute("bin9_X");
        bin0_Y = new Attribute("bin0_Y");
        bin1_Y = new Attribute("bin1_Y");
        bin2_Y = new Attribute("bin2_Y");
        bin3_Y = new Attribute("bin3_Y");
        bin4_Y = new Attribute("bin4_Y");
        bin5_Y = new Attribute("bin5_Y");
        bin6_Y = new Attribute("bin6_Y");
        bin7_Y = new Attribute("bin7_Y");
        bin8_Y = new Attribute("bin8_Y");
        bin9_Y = new Attribute("bin9_Y");
        bin0_Z = new Attribute("bin0_Z");
        bin1_Z = new Attribute("bin1_Z");
        bin2_Z = new Attribute("bin2_Z");
        bin3_Z = new Attribute("bin3_Z");
        bin4_Z = new Attribute("bin4_Z");
        bin5_Z = new Attribute("bin5_Z");
        bin6_Z = new Attribute("bin6_Z");
        bin7_Z = new Attribute("bin7_Z");
        bin8_Z = new Attribute("bin8_Z");
        bin9_Z = new Attribute("bin9_Z");
        bin0_magnitude = new Attribute("bin0_magnitude");
        bin1_magnitude = new Attribute("bin1_magnitude");
        bin2_magnitude = new Attribute("bin2_magnitude");
        bin3_magnitude = new Attribute("bin3_magnitude");
        bin4_magnitude = new Attribute("bin4_magnitude");
        bin5_magnitude = new Attribute("bin5_magnitude");
        bin6_magnitude = new Attribute("bin6_magnitude");
        bin7_magnitude = new Attribute("bin7_magnitude");
        bin8_magnitude = new Attribute("bin8_magnitude");
        bin9_magnitude = new Attribute("bin9_magnitude");

        attributes = new ArrayList();
        attributes.add(minimum_for_axis_X);
        attributes.add(minimum_for_axis_Y);
        attributes.add(minimum_for_axis_Z);
        attributes.add(minimum_for_magnitude);
        attributes.add(average_acceleration_for_axis_X);
        attributes.add(average_acceleration_for_axis_Y);
        attributes.add(average_acceleration_for_axis_Z);
        attributes.add(average_acceleration_for_magnitude);
        attributes.add(standard_deviation_for_axis_X);
        attributes.add(standard_deviation_for_axis_Y);
        attributes.add(standard_deviation_for_axis_Z);
        attributes.add(standard_deviation_for_magnitude);
        attributes.add(average_absolute_difference_for_axis_X);
        attributes.add(average_absolute_difference_for_axis_Y);
        attributes.add(average_absolute_difference_for_axis_Z);
        attributes.add(average_absolute_difference_for_magnitude);
        attributes.add(zero_crossing_rate_for_axis_X);
        attributes.add(zero_crossing_rate_for_axis_Y);
        attributes.add(zero_crossing_rate_for_axis_Z);
        attributes.add(bin0_X);
        attributes.add(bin1_X);
        attributes.add(bin2_X);
        attributes.add(bin3_X);
        attributes.add(bin4_X);
        attributes.add(bin5_X);
        attributes.add(bin6_X);
        attributes.add(bin7_X);
        attributes.add(bin8_X);
        attributes.add(bin9_X);
        attributes.add(bin0_Y);
        attributes.add(bin1_Y);
        attributes.add(bin2_Y);
        attributes.add(bin3_Y);
        attributes.add(bin4_Y);
        attributes.add(bin5_Y);
        attributes.add(bin6_Y);
        attributes.add(bin7_Y);
        attributes.add(bin8_Y);
        attributes.add(bin9_Y);
        attributes.add(bin0_Z);
        attributes.add(bin1_Z);
        attributes.add(bin2_Z);
        attributes.add(bin3_Z);
        attributes.add(bin4_Z);
        attributes.add(bin5_Z);
        attributes.add(bin6_Z);
        attributes.add(bin7_Z);
        attributes.add(bin8_Z);
        attributes.add(bin9_Z);
        attributes.add(bin0_magnitude);
        attributes.add(bin1_magnitude);
        attributes.add(bin2_magnitude);
        attributes.add(bin3_magnitude);
        attributes.add(bin4_magnitude);
        attributes.add(bin5_magnitude);
        attributes.add(bin6_magnitude);
        attributes.add(bin7_magnitude);
        attributes.add(bin8_magnitude);
        attributes.add(bin9_magnitude);

        /*StringBuilder users=new StringBuilder();
        for(String s : userids){ //creating list of users separated by commas
            users.append(s);
            users.append(",");
        }
        users.deleteCharAt(users.lastIndexOf(","));
         */
        userId = new Attribute("userID", userids);
        attributes.add(userId);

        return attributes;
    }

    private Instance createInstance(double minimum_for_axis_X,
            double minimum_for_axis_Y,
            double minimum_for_axis_Z,
            double minimum_for_magnitude,
            double average_acceleration_for_axis_X,
            double average_acceleration_for_axis_Y,
            double average_acceleration_for_axis_Z,
            double average_acceleration_for_magnitude,
            double standard_deviation_for_axis_X,
            double standard_deviation_for_axis_Y,
            double standard_deviation_for_axis_Z,
            double standard_deviation_for_magnitude,
            double average_absolute_difference_for_axis_X,
            double average_absolute_difference_for_axis_Y,
            double average_absolute_difference_for_axis_Z,
            double average_absolute_difference_for_magnitude,
            double zero_crossing_rate_for_axis_X,
            double zero_crossing_rate_for_axis_Y,
            double zero_crossing_rate_for_axis_Z,
            double[] binsX,
            double[] binsY,
            double[] binsZ,
            double[] binsMag,
            String userID) {
        double[] instanceValue = new double[]{
            minimum_for_axis_X,
            minimum_for_axis_Y,
            minimum_for_axis_Z,
            minimum_for_magnitude,
            average_acceleration_for_axis_X,
            average_acceleration_for_axis_Y,
            average_acceleration_for_axis_Z,
            average_acceleration_for_magnitude,
            standard_deviation_for_axis_X,
            standard_deviation_for_axis_Y,
            standard_deviation_for_axis_Z,
            standard_deviation_for_magnitude,
            average_absolute_difference_for_axis_X,
            average_absolute_difference_for_axis_Y,
            average_absolute_difference_for_axis_Z,
            average_absolute_difference_for_magnitude,
            zero_crossing_rate_for_axis_X,
            zero_crossing_rate_for_axis_Y,
            zero_crossing_rate_for_axis_Z,
            binsX[0],
            binsX[1],
            binsX[2],
            binsX[3],
            binsX[4],
            binsX[5],
            binsX[6],
            binsX[7],
            binsX[8],
            binsX[9],
            binsY[0],
            binsY[1],
            binsY[2],
            binsY[3],
            binsY[4],
            binsY[5],
            binsY[6],
            binsY[7],
            binsY[8],
            binsY[9],
            binsZ[0],
            binsZ[1],
            binsZ[2],
            binsZ[3],
            binsZ[4],
            binsZ[5],
            binsZ[6],
            binsZ[7],
            binsZ[8],
            binsZ[9],
            binsMag[0],
            binsMag[1],
            binsMag[2],
            binsMag[3],
            binsMag[4],
            binsMag[5],
            binsMag[6],
            binsMag[7],
            binsMag[8],
            binsMag[9],
            0//userId
        };

        DenseInstance dense = new DenseInstance(1.0, instanceValue);

        return dense;
    }

}

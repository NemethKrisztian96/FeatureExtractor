/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FeatureExtractorLibrary;

import java.util.Arrays;

/**
 * Feature is the class that contains the different feature values; besides the
 * constructors and getters it has a generateHeader function and an overloaded
 * toString method.
 *
 * @author Krisztian Nemeth
 * @version 1.0
 * @since 23 ‎July, ‎2018
 */
public class Feature {

    private static StringBuilder header = new StringBuilder();

    private double minX;
    private double minY;
    private double minZ;
    private double minMag;
    private double avgAccelerationX;
    private double avgAccelerationY;
    private double avgAccelerationZ;
    private double avgAccelerationMag;
    private double stdDevX;
    private double stdDevY;
    private double stdDevZ;
    private double stdDevMag;
    private double avgAbsDiffX;
    private double avgAbsDiffY;
    private double avgAbsDiffZ;
    private double avgAbsDiffMag;
    private double zeroCrossingX;
    private double zeroCrossingY;
    private double zeroCrossingZ;
    private double binsX[];
    private double binsY[];
    private double binsZ[];
    private double binsMag[];
    private String userId;
    public static final int NUM_FEATURES = 60;

    /**
     * This constructor gets all the data in an array and supposing that the
     * order is correct fills the Feature attributes.
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param data array containing all the data
     * @param userID subjects ID
     * @param noOfBins number of bins in every histogram
     * @since 23 ‎July, ‎2018
     */
    public Feature(double data[], String userID, int noOfBins) {
        int counter = 0;
        minX = data[counter++];
        minY = data[counter++];
        minZ = data[counter++];
        minMag = data[counter++];
        avgAccelerationX = data[counter++];
        avgAccelerationY = data[counter++];
        avgAccelerationZ = data[counter++];
        avgAccelerationMag = data[counter++];
        stdDevX = data[counter++];
        stdDevY = data[counter++];
        stdDevZ = data[counter++];
        stdDevMag = data[counter++];
        avgAbsDiffX = data[counter++];
        avgAbsDiffY = data[counter++];
        avgAbsDiffZ = data[counter++];
        avgAbsDiffMag = data[counter++];
        zeroCrossingX = data[counter++];
        zeroCrossingY = data[counter++];
        zeroCrossingZ = data[counter++];
        binsX = new double[noOfBins];
        binsY = new double[noOfBins];
        binsZ = new double[noOfBins];
        binsMag = new double[noOfBins];
        for (int i = counter; i < counter + noOfBins; ++i) {
            binsX[i] = data[i];
            binsY[i] = data[noOfBins + i];
            binsZ[i] = data[2 * noOfBins + i];
            binsMag[i] = data[3 * noOfBins + i];
        }
        userId = userID;
    }

    /**
     * This constructor gets all the data in arrays grouped by the type of
     * attribute to fill the Feature attributes.
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param min array containing the minimum value on every axis and magnitude
     * @param avgAcceleration array containing the minimum value on every axis
     * and magnitude
     * @param stdDev array containing the minimum value on every axis and
     * magnitude
     * @param avgAbsDiff array containing the minimum value on every axis and
     * magnitude
     * @param zeroCrossing array containing the minimum value on every axis
     * @param noOfBins number of bins in every histogram
     * @param bins array containing the histogram values
     * @param userID subjects ID
     * @since 23 ‎July, ‎2018
     */
    public Feature(double min[],
            double avgAcceleration[],
            double stdDev[],
            double avgAbsDiff[],
            double zeroCrossing[],
            int noOfBins,
            double bins[],
            String userID) {

        minX = min[0];
        minY = min[1];
        minZ = min[2];
        minMag = min[3];
        avgAccelerationX = avgAcceleration[0];
        avgAccelerationY = avgAcceleration[1];
        avgAccelerationZ = avgAcceleration[2];
        avgAccelerationMag = avgAcceleration[3];
        stdDevX = stdDev[0];
        stdDevY = stdDev[1];
        stdDevZ = stdDev[2];
        stdDevMag = stdDev[3];
        avgAbsDiffX = avgAbsDiff[0];
        avgAbsDiffY = avgAbsDiff[1];
        avgAbsDiffZ = avgAbsDiff[2];
        avgAbsDiffMag = avgAbsDiff[3];
        zeroCrossingX = zeroCrossing[0];
        zeroCrossingY = zeroCrossing[1];
        zeroCrossingZ = zeroCrossing[2];
        binsX = new double[noOfBins];
        binsY = new double[noOfBins];
        binsZ = new double[noOfBins];
        binsMag = new double[noOfBins];
        for (int i = 0; i < noOfBins; ++i) {
            binsX[i] = bins[i];
            binsY[i] = bins[noOfBins + i];
            binsZ[i] = bins[2 * noOfBins + i];
            binsMag[i] = bins[3 * noOfBins + i];
        }
        userId = userID;
    }

    /**
     * This constructor gets all the data in separate parameters, one by one,
     * until it reaches the histograms, which are passed through arrays; the
     * number of bins is not needed, but the userID of the subject is needed to
     * fill the Feature attributes.
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param minX minimum value on axis X
     * @param minY minimum value on axis Y
     * @param minZ minimum value on axis Z
     * @param minMag minimum value on magnitude
     * @param avgAccelerationX average value on axis X
     * @param avgAccelerationY average value on axis Y
     * @param avgAccelerationZ average value on axis Z
     * @param avgAccelerationMag average value on magnitude
     * @param stdDevX standard deviation on axis X
     * @param stdDevY standard deviation on axis Y
     * @param stdDevZ standard deviation on axis Z
     * @param stdDevMag standard deviation on magnitude
     * @param avgAbsDiffX average absolute difference on axis X
     * @param avgAbsDiffY average absolute difference on axis Y
     * @param avgAbsDiffZ average absolute difference on axis Z
     * @param avgAbsDiffMag average absolute difference on magnitude
     * @param zeroCrossingX zero crossing on axis X
     * @param zeroCrossingY zero crossing on axis Y
     * @param zeroCrossingZ zero crossing on axis Z
     * @param binsX array containing the histogram values on axis X
     * @param binsY array containing the histogram values on axis Y
     * @param binsZ array containing the histogram values on axis Z
     * @param binsMag array containing the histogram values on magnitude
     * @param userID subjects ID
     * @since 23 ‎July, ‎2018
     */
    public Feature(double minX, double minY, double minZ, double minMag,
            double avgAccelerationX, double avgAccelerationY, double avgAccelerationZ, double avgAccelerationMag,
            double stdDevX, double stdDevY, double stdDevZ, double stdDevMag,
            double avgAbsDiffX, double avgAbsDiffY, double avgAbsDiffZ, double avgAbsDiffMag,
            double zeroCrossingX, double zeroCrossingY, double zeroCrossingZ,
            double binsX[], double binsY[], double binsZ[], double binsMag[],
            String userID) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.minMag = minMag;
        this.avgAccelerationX = avgAccelerationX;
        this.avgAccelerationY = avgAccelerationY;
        this.avgAccelerationZ = avgAccelerationZ;
        this.avgAccelerationMag = avgAccelerationMag;
        this.stdDevX = stdDevX;
        this.stdDevY = stdDevY;
        this.stdDevZ = stdDevZ;
        this.stdDevMag = stdDevMag;
        this.avgAbsDiffX = avgAbsDiffX;
        this.avgAbsDiffY = avgAbsDiffY;
        this.avgAbsDiffZ = avgAbsDiffZ;
        this.avgAbsDiffMag = avgAbsDiffMag;
        this.zeroCrossingX = zeroCrossingX;
        this.zeroCrossingY = zeroCrossingY;
        this.zeroCrossingZ = zeroCrossingZ;
        this.binsX = binsX;
        this.binsY = binsY;
        this.binsZ = binsZ;
        this.binsMag = binsMag;
        userId = userID;
    }

    /**
     * This function generates an *.arff file specific header that includes all
     * the attributes used in the class
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @since 23 ‎July, ‎2018
     */
    public static void generateHeader() {
        header.append("@relation accelerometer\n\n");
        header.append("@attribute minimum_for_axis_X numeric\n");
        header.append("@attribute minimum_for_axis_Y numeric\n");
        header.append("@attribute minimum_for_axis_Z numeric\n");
        header.append("@attribute minimum_for_magnitude numeric\n");
        header.append("@attribute average_acceleration_for_axis_X numeric\n");
        header.append("@attribute average_acceleration_for_axis_Y numeric\n");
        header.append("@attribute average_acceleration_for_axis_Z numeric\n");
        header.append("@attribute average_acceleration_for_magnitude numeric\n");
        header.append("@attribute standard_deviation_for_axis_X numeric\n");
        header.append("@attribute standard_deviation_for_axis_Y numeric\n");
        header.append("@attribute standard_deviation_for_axis_Z numeric\n");
        header.append("@attribute standard_deviation_for_magnitude numeric\n");
        header.append("@attribute average_absolute_difference_for_axis_X numeric\n");
        header.append("@attribute average_absolute_difference_for_axis_Y numeric\n");
        header.append("@attribute average_absolute_difference_for_axis_Z numeric\n");
        header.append("@attribute average_absolute_difference_for_magnitude numeric\n");
        header.append("@attribute zero_crossing_rate_for_axis_X numeric\n");
        header.append("@attribute zero_crossing_rate_for_axis_Y numeric\n");
        header.append("@attribute zero_crossing_rate_for_axis_Z numeric\n");

        header.append("@attribute bin0_X numeric\n");
        header.append("@attribute bin1_X numeric\n");
        header.append("@attribute bin2_X numeric\n");
        header.append("@attribute bin3_X numeric\n");
        header.append("@attribute bin4_X numeric\n");
        header.append("@attribute bin5_X numeric\n");
        header.append("@attribute bin6_X numeric\n");
        header.append("@attribute bin7_X numeric\n");
        header.append("@attribute bin8_X numeric\n");
        header.append("@attribute bin9_X numeric\n");

        header.append("@attribute bin0_Y numeric\n");
        header.append("@attribute bin1_Y numeric\n");
        header.append("@attribute bin2_Y numeric\n");
        header.append("@attribute bin3_Y numeric\n");
        header.append("@attribute bin4_Y numeric\n");
        header.append("@attribute bin5_Y numeric\n");
        header.append("@attribute bin6_Y numeric\n");
        header.append("@attribute bin7_Y numeric\n");
        header.append("@attribute bin8_Y numeric\n");
        header.append("@attribute bin9_Y numeric\n");

        header.append("@attribute bin0_Z numeric\n");
        header.append("@attribute bin1_Z numeric\n");
        header.append("@attribute bin2_Z numeric\n");
        header.append("@attribute bin3_Z numeric\n");
        header.append("@attribute bin4_Z numeric\n");
        header.append("@attribute bin5_Z numeric\n");
        header.append("@attribute bin6_Z numeric\n");
        header.append("@attribute bin7_Z numeric\n");
        header.append("@attribute bin8_Z numeric\n");
        header.append("@attribute bin9_Z numeric\n");

        header.append("@attribute bin0_magnitude numeric\n");
        header.append("@attribute bin1_magnitude numeric\n");
        header.append("@attribute bin2_magnitude numeric\n");
        header.append("@attribute bin3_magnitude numeric\n");
        header.append("@attribute bin4_magnitude numeric\n");
        header.append("@attribute bin5_magnitude numeric\n");
        header.append("@attribute bin6_magnitude numeric\n");
        header.append("@attribute bin7_magnitude numeric\n");
        header.append("@attribute bin8_magnitude numeric\n");
        header.append("@attribute bin9_magnitude numeric\n");

        header.append("@attribute userid");

        header.append("@data");
    }

    public String getHeader(Settings.FileType filetype){
        if(filetype == Settings.FileType.ARFF){
            if(header.length()<1){
                generateHeader();
            }
            return header.toString();
        }
        return "minimum_for_axis_X,minimum_for_axis_Y,minimum_for_axis_Z,minimum_for_magnitude,"
            + "average_acceleration_for_axis_X,average_acceleration_for_axis_Y,average_acceleration_for_axis_Z,average_acceleration_for_magnitude,"
            + "standard_deviation_for_axis_X,standard_deviation_for_axis_Y,standard_deviation_for_axis_Z,standard_deviation_for_magnitude,"
            + "average_absolute_difference_for_axis_X,average_absolute_difference_for_axis_Y,average_absolute_difference_for_axis_Z,average_absolute_difference_for_magnitude,"
            + "zero_crossing_rate_for_axis_X,zero_crossing_rate_for_axis_Y,zero_crossing_rate_for_axis_Z,"
            + "bin0_X,bin1_X,bin2_X,bin3_X,bin4_X,bin5_X,bin6_X,bin7_X,bin8_X,bin9_X,"
            + "bin0_Y,bin1_Y,bin2_Y,bin3_Y,bin4_Y,bin5_Y,bin6_Y,bin7_Y,bin8_Y,bin9_Y,"
            + "bin0_Z,bin1_Z,bin2_Z,bin3_Z,bin4_Z,bin5_Z,bin6_Z,bin7_Z,bin8_Z,bin9_Z,"
            + "bin0_magnitude,bin1_magnitude,bin2_magnitude,bin3_magnitude,bin4_magnitude,bin5_magnitude,bin6_magnitude,bin7_magnitude,bin8_magnitude,bin9_magnitude,"
            + "userID\n";
    }
    
    @Override
    public String toString() {
        return "Feature{" + minX + "," + minY + "," + minZ + "," + minMag + "," + avgAccelerationX + "," + avgAccelerationY + "," + avgAccelerationZ + "," + avgAccelerationMag + "," + stdDevX + "," + stdDevY + "," + stdDevZ + "," + stdDevMag + "," + avgAbsDiffX + "," + avgAbsDiffY + "," + avgAbsDiffZ + "," + avgAbsDiffMag + "," + zeroCrossingX + "," + zeroCrossingY + "," + zeroCrossingZ + "," + Arrays.toString(binsX) + "," + Arrays.toString(binsY) + "," + Arrays.toString(binsZ) + "," + Arrays.toString(binsMag) + "," + userId + "}\n";
    }

    //public static StringBuilder getHeader() {
    //    return header;
    //}

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMinMag() {
        return minMag;
    }

    public double getAvgAccelerationX() {
        return avgAccelerationX;
    }

    public double getAvgAccelerationY() {
        return avgAccelerationY;
    }

    public double getAvgAccelerationZ() {
        return avgAccelerationZ;
    }

    public double getAvgAccelerationMag() {
        return avgAccelerationMag;
    }

    public double getStdDevX() {
        return stdDevX;
    }

    public double getStdDevY() {
        return stdDevY;
    }

    public double getStdDevZ() {
        return stdDevZ;
    }

    public double getStdDevMag() {
        return stdDevMag;
    }

    public double getAvgAbsDiffX() {
        return avgAbsDiffX;
    }

    public double getAvgAbsDiffY() {
        return avgAbsDiffY;
    }

    public double getAvgAbsDiffZ() {
        return avgAbsDiffZ;
    }

    public double getAvgAbsDiffMag() {
        return avgAbsDiffMag;
    }

    public double getZeroCrossingX() {
        return zeroCrossingX;
    }

    public double getZeroCrossingY() {
        return zeroCrossingY;
    }

    public double getZeroCrossingZ() {
        return zeroCrossingZ;
    }

    public double[] getBinsX() {
        return binsX;
    }

    public double[] getBinsY() {
        return binsY;
    }

    public double[] getBinsZ() {
        return binsZ;
    }

    public double[] getBinsMag() {
        return binsMag;
    }

    public String getUserId() {
        return userId;
    }

    public double[] getFeatureAsDoubleArray() {
        double[] arr = new double[NUM_FEATURES];
        int counter = 0;
        arr[counter++] = minX;
        arr[counter++] = minY;
        arr[counter++] = minZ;
        arr[counter++] = minMag;
        arr[counter++] = avgAccelerationX;
        arr[counter++] = avgAccelerationY;
        arr[counter++] = avgAccelerationZ;
        arr[counter++] = avgAccelerationMag;
        arr[counter++] = stdDevX;
        arr[counter++] = stdDevY;
        arr[counter++] = stdDevZ;
        arr[counter++] = stdDevMag;
        arr[counter++] = avgAbsDiffX;
        arr[counter++] = avgAbsDiffY;
        arr[counter++] = avgAbsDiffZ;
        arr[counter++] = avgAbsDiffMag;
        arr[counter++] = zeroCrossingX;
        arr[counter++] = zeroCrossingY;
        arr[counter++] = zeroCrossingZ;
        int binY = binsX.length;
        int binZ = binsX.length+binsY.length;
        int binMag = binsX.length+binsY.length+binsZ.length;
        for (int i = 0; i < binsX.length; ++i) {
//            arr[counter + i] = binsX[i];
//            
//            
//            
            arr[counter + i] = binsX[i];
            arr[counter + binY + i] = binsY[i];
            arr[counter + binZ + i] = binsZ[i];
            arr[counter + binMag + i] = binsMag[i];
            System.out.println(counter + i);
            //counter++;
        }
        counter+=binMag+binsMag.length;
        System.out.println(counter);
        arr[counter]=0; //userId
        return arr;
    }
}

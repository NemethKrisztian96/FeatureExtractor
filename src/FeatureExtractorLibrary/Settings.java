/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FeatureExtractorLibrary;

/**
 * This class contains the relevant settings needed to the proper working of the
 * FeatureExtractor; the minimum requirement involves specifying whether the
 * FeatureExtractor uses fixed frames and the frameSize, or it uses cycles, but
 * it can contain information regarding the input header, output header and file
 * type and the number of steps ignored.
 *
 * @author Krisztian Nemeth
 * @version 1.0
 * @since 23 ‎July, ‎2018
 */
public final class Settings {

    private static String defaultUserId = "noname";

    /**
     * Specifies the type of the output file
     *
     * @author Krisztian Nemeth
     * @version 1.1
     * @since 23 ‎July, ‎2018
     */
    public static enum FileType {
        ARFF, CSV
    };

    public static final double GRAVITY = 9.8;

    private static FileType outputFileType = FileType.ARFF;
    private static boolean cycles = false;
    private static boolean frames = false;
    private static int frameSize = 0;
    private static boolean inputHasHeader = false;
    private static boolean outputHasHeader = false;
    private static int numStepsIgnored = 0;
    private static int numFramesIgnored = 0;
    private static boolean preprocessing = false;
    private static boolean useDynamicPreprocessingThreshold = false;
    private static double preprocessingThreshold = 0;
    private static int preprocessingInterval = 0; //the length of the interval that will be cut out
    private static double histogramGravityMultiplier = GRAVITY;

    /**
     * Sets the FeatureExtractor to use cycles instead of fixed frames
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @since 23 ‎July, ‎2018
     */
    public static final void usingCycles() {
        cycles = true;
        frames = false;
    }

    /**
     * Sets the FeatureExtractor to use fixed frames instead of cycles
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param size the size of the frames
     * @since 23 ‎July, ‎2018
     */
    public static final void usingFrames(int size) {
        if (size > 0) {
            cycles = false;
            frames = true;
            frameSize = size;
        } else {
            frames = false;
            cycles = false;
        }
    }

    /**
     * Checks if the setting are correctly set and returns the proper boolean
     * value.
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return true if the minimal settings have been set and the
     * FeatureExtractor is ready to be used
     * @since 23 ‎July, ‎2018
     */
    public static final boolean settingsAreSet() {
        return cycles != frames;
    }

    /**
     * Checks if the FeatureExtractor is using cycles instead of fixed frames
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return true if cycles are used, or false if frames are used
     * @since 23 ‎July, ‎2018
     */
    public static final boolean isUsingCycles() {
        return cycles == true;
    }

    /**
     * Checks if the FeatureExtractor is using fixed frames instead of cycles.
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return true if frames are used, or false if cycles are used
     * @since 23 ‎July, ‎2018
     */
    public static final boolean isUsingFrames() {
        return frames == true;
    }

    /**
     * Gets the frame size that the FeatureExtractor is using.
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return the size of the fixed frames
     * @since 23 ‎July, ‎2018
     */
    public static final int getFrameSize() {
        if (!isUsingFrames()) {
            return 0;
        }
        return frameSize;
    }

    /**
     * Checks if the FeatureExtractor input file is expected to have a header
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return true if input has header, false it it doesn't have
     * @since 23 ‎July, ‎2018
     */
    public static boolean getInputHasHeader() {
        return inputHasHeader;
    }

    /**
     * Sets the FeatureExtractor to expect an input file that has a header
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param inputHeader true to specify that the input has a header, or false
     * to specify that the input doesn't have a header
     * @since 23 ‎July, ‎2018
     */
    public static void setInputHasHeader(boolean inputHeader) {
        Settings.inputHasHeader = inputHeader;
    }

    /**
     * Checks if the FeatureExtractor output file is expected to have a header
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return true if output has header, false it it hasn't
     * @since 23 ‎July, ‎2018
     */
    public static boolean getOutputHasHeader() {
        return outputHasHeader;
    }

    /**
     * Sets the FeatureExtractor to include a header in the output file
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param outputHeader true to specify that the output should contain a
     * header, or false to specify that the output shouldn't contain a header
     * @since 23 ‎July, ‎2018
     */
    public static void setOutputHasHeader(boolean outputHeader) {
        Settings.outputHasHeader = outputHeader;
    }

    /**
     * Gets the number of step cycles the FeatureExtractor will ignore from the
     * input
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return the number of steps ignored
     * @since 23 ‎July, ‎2018
     */
    public static int getNumStepsIgnored() {
        return numStepsIgnored;
    }

    /**
     * Sets the number of step cycles the FeatureExtractor will ignore from the
     * input
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param numStepsIgnored the number of steps ignored
     * @since 23 ‎July, ‎2018
     */
    public static void setNumStepsIgnored(int numStepsIgnored) {
        if (numStepsIgnored > 0) {
            Settings.numStepsIgnored = numStepsIgnored;
        } else {
            Settings.numStepsIgnored = 0;
        }
    }

    /**
     * Gets the number of frames the FeatureExtractor will ignore from the input
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return the number of steps ignored
     * @since 23 ‎July, ‎2018
     */
    public static int getNumFramesIgnored() {
        return numFramesIgnored;
    }

    /**
     * Sets the number of frames the FeatureExtractor will ignore from the input
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param numFramesIgnored the number of steps ignored
     * @since 23 ‎July, ‎2018
     */
    public static void setNumFramesIgnored(int numFramesIgnored) {
        if (numFramesIgnored > 0) {
            Settings.numFramesIgnored = numFramesIgnored;
        } else {
            Settings.numFramesIgnored = 0;
        }
    }

    /**
     * Returns the FileType of the output
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return specified type of the output file
     * @since 23 ‎July, ‎2018
     */
    public static FileType getOutputFileType() {
        return outputFileType;
    }

    /**
     * Sets the FeatureExtractor to use a desired output file type
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param fileType desired type of the output file from the FileType enum
     * @since 23 ‎July, ‎2018
     */
    public static void setOutputFileType(FileType fileType) {
        Settings.outputFileType = fileType;
    }

    /**
     * Gives the String value that is used as default userId
     *
     * @author Krisztian Nemeth
     * @version 1.1
     * @return default userID
     * @since 23 ‎July, ‎2018
     */
    public static String getDefaultUserId() {
        return defaultUserId;
    }

    /**
     * Sets the String that is used as default userId
     *
     * @author Krisztian Nemeth
     * @version 1.1
     * @param defaultUserID the desired default userID
     * @since 23 ‎July, ‎2018
     */
    public static void setDefaultUserId(String defaultUserID) {
        defaultUserId = defaultUserID;
    }

    /**
     * Gives a detailed description about the set attributes of the class
     *
     * @author Krisztian Nemeth
     * @version 1.1
     * @return String containing the existing settings
     * @since 23 ‎July, ‎2018
     */
    public static String getAllSettings() {
        StringBuilder sb = new StringBuilder();
        if (!settingsAreSet()) {
            sb.append("Settings class is not properly set!!!\n");
        }
        sb.append("Settings:\n");
        sb.append("\tDefaultUserID: ");
        sb.append(defaultUserId);
        sb.append("\n");

        if (isUsingCycles()) {
            sb.append("\tFeatureExtractor is using cycles\n");
            sb.append("\tNumber of step cycles ignored ");
            sb.append(numStepsIgnored);
            sb.append("\n");
        }
        if (isUsingFrames()) {
            sb.append("\tFeatureExtractor is using frames\n");
            sb.append("\t\tThe frame size is ");
            sb.append(frameSize);
            sb.append("\n");
            sb.append("\tNumber of frames ignored ");
            sb.append(numFramesIgnored);
            sb.append("\n");
        }
        sb.append("\tOutputFileType: ");
        sb.append(getOutputFileType() == FileType.ARFF ? ".arff" : ".csv");
        sb.append("\n");
        sb.append("\tThe input file has a header\n");
        sb.append("\tThe output file has a header\n");
        return sb.toString();
    }

    /**
     * Checks if the FeatureExtractor is running preprocessing before extracting
     * features
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return true if it is using preprocessing
     * @since 23 ‎July, ‎2018
     */
    public static boolean isUsingPreprocessing() {
        return preprocessing;
    }

    /**
     * Sets the FeatureExtractor to run preprocessing before extracting features
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param preprocessing true to specify that preprocessing should be run, or
     * false to specify that preprocessing shouldn't be run
     * @since 23 ‎July, ‎2018
     */
    public static void usingPreprocessing(boolean preprocessing) {
        Settings.preprocessing = preprocessing;
    }

    /**
     * Sets the FeatureExtractor to run preprocessing before extracting features
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param useDynamicPreprocessingThreshold true to specify that dynamic
     * preprocessing threshold should be used, or false to specify that it
     * shouldn't be used
     * @since 23 ‎July, ‎2018
     */
    public static void setUseDynamicPreprocessingThreshold(boolean useDynamicPreprocessingThreshold) {
        Settings.useDynamicPreprocessingThreshold = useDynamicPreprocessingThreshold;
    }

    /**
     * Checks if the preprocessing procedure is using dynamic preprocessing
     * thresholds
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return true if it is using dynamic preprocessing threshold, or false if
     * it is not
     * @since 23 ‎July, ‎2018
     */
    public static boolean isUsingDynamicPreprocessingThreshold() {
        return useDynamicPreprocessingThreshold;
    }

    /**
     * Gets the preprocessing threshold's size
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return the size of the preprocessing threshold
     * @since 23 ‎July, ‎2018
     */
    public static double getPreprocessingThreshold() {
        return preprocessingThreshold;
    }

    /**
     * Sets the threshold's value used by the preprocessing procedure; redundant
     * if dynamic preprocessing threshold is used
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param preprocessingThreshold the desired threshold's value
     * @since 23 ‎July, ‎2018
     */
    public static void setPreprocessingThreshold(double preprocessingThreshold) {
        if (preprocessingThreshold < 0) {
            Settings.preprocessingThreshold = 0;
        } else {
            Settings.preprocessingThreshold = preprocessingThreshold;
        }
    }

    /**
     * Gets the length of the intervals that can be excluded during the
     * preprocessing procedure
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return the size of the preprocessing threshold
     * @since 23 ‎July, ‎2018
     */
    public static int getPreprocessingInterval() {
        return preprocessingInterval;
    }

    /**
     * Sets the FeatureExtractor to exclude intervals with the specified length
     * from the input data during the preprocessing procedure
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param preprocessingInterval the desired interval length
     * @since 23 ‎July, ‎2018
     */
    public static void setPreprocessingInterval(int preprocessingInterval) {
        if (preprocessingInterval < 0) {
            Settings.preprocessingInterval = 0;
        } else {
            Settings.preprocessingInterval = preprocessingInterval;
        }
    }

    /**
     * Gets the multiplier value that multiplied with the gravitational
     * acceleration value will be used to determine the intervals for histogram
     * calculation during feature extraction
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @return the size of the preprocessing threshold
     * @since 23 ‎July, ‎2018
     */
    public static double getHistogramGravityMultiplier() {
        return histogramGravityMultiplier;
    }

    /**
     * Determines the intervals that will be used for histogram calculation
     * during feature extraction by specifying the value that should be
     * multiplied with the gravitational acceleration value (g=9.8)
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param histogramGravityMultiplier the value that should be multiplied
     * with the gravitational
     * @since 23 ‎July, ‎2018
     */
    public static void setHistogramGravityMultiplier(double histogramGravityMultiplier) {
        if (histogramGravityMultiplier == 0) {
            Settings.histogramGravityMultiplier = 1;
        }
        Settings.histogramGravityMultiplier = histogramGravityMultiplier;
    }
}

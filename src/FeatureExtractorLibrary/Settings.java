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

    private static FileType outputFileType = FileType.ARFF;
    private static boolean cycles = false;
    private static boolean frames = false;
    private static int frameSize = 0;
    private static boolean inputHasHeader = false;
    private static boolean outputHasHeader = false;
    private static int numStepsIgnored = 0;
    private static int numFramesIgnored = 0;

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

}

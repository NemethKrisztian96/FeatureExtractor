package FeatureExtractorLibrary;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * This is a class with static methods meant to extract features from a dataset
 * given as an ArrayList of Accelerometer objects or a CSV file, using the
 * settings from Setting class.
 *
 * @author Krisztian Nemeth
 * @version 1.0
 * @since 23 ‎July, ‎2018
 */
public class FeatureExtractor implements IFeatureExtractor {

    private static final String TAG = "FeatureExtractor - ";
    private static StringBuilder dataString = new StringBuilder();
    private static int WINSIZE;
    //private ArrayList<Accelerometer> dataset;

    private FeatureExtractor() {
    }

    /**
     * Extracts features from the dataset.
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param dataset ArrayList containing the data as Accelerometer object
     * @param userId subject Id
     * @return ArrayList containing the resulting Feature objects
     * @throws FeatureExtractorLibrary.FeatureExtractorException if userId is
     * empty
     * @throws FeatureExtractorLibrary.FeatureExtractorException if Settings are
     * not properly given
     * @throws FeatureExtractorLibrary.FeatureExtractorException if dataset is
     * empty
     * @since 23 ‎July, ‎2018
     */
    public static ArrayList<Feature> extractFeaturesFromArrayListToArrayListOfFeatures(ArrayList<Accelerometer> dataset, String userId) throws FeatureExtractorException {
        if (dataset.isEmpty()) {
            throw new FeatureExtractorException(TAG + "empty dataset");
        }
        if (userId.isEmpty()) {
            throw new FeatureExtractorException(TAG + "invalid or empty userID");
        }
        if (Settings.settingsAreSet()) {
            if (Settings.isUsingCycles()) {
                return extractFeaturesFromArrayListToArrayListOfFeaturesUsingCycles(dataset, userId);
            } else {
                return extractFeaturesFromArrayListToArrayListOfFeaturesUsingFrames(dataset, userId);
            }
        } else {
            throw new FeatureExtractorException(TAG + "Settings are not set correctly");
        }
    }

    /**
     * Extracts features from the dataset into a file that's type is specified
     * in the Settings.
     *
     * @author Krisztian Nemeth
     * @version 1.1
     * @param dataset ArrayList containing the data as Accelerometer object
     * @param userId subject Id
     * @param filename output file name that will contain the features
     * @throws FeatureExtractorLibrary.FeatureExtractorException if userId is
     * empty
     * @throws FeatureExtractorLibrary.FeatureExtractorException if Settings are
     * not properly given
     * @throws FeatureExtractorLibrary.FeatureExtractorException if dataset is
     * empty
     * @throws FeatureExtractorLibrary.FeatureExtractorException if filename is
     * empty
     * @since 23 ‎July, ‎2018
     */
    public static void extractFeaturesFromArrayListToFile(ArrayList<Accelerometer> dataset, String userId, String filename) throws FeatureExtractorException {
        if (dataset.isEmpty()) {
            throw new FeatureExtractorException(TAG + "empty dataset");
        }
        if (userId.isEmpty()) {
            throw new FeatureExtractorException(TAG + "invalid userID");
        }
        if (filename.isEmpty()) {
            throw new FeatureExtractorException(TAG + "incorrect filename");
        }
        if (Settings.settingsAreSet()) {
            filename = getFilenameWithoutExtension(filename);
            if (Settings.isUsingCycles()) {
                extractFeaturesFromArrayListToFileUsingCycles(dataset, userId, filename + (Settings.getOutputFileType() == Settings.FileType.ARFF ? ".arff" : ".csv"));
            } else {
                extractFeaturesFromArrayListToFileUsingFrames(dataset, userId, filename + (Settings.getOutputFileType() == Settings.FileType.ARFF ? ".arff" : ".csv"));
            }
        } else {
            throw new FeatureExtractorException(TAG + "Settings are not set correctly");
        }
    }

    /**
     * Extracts features from the given file.
     *
     * @author Krisztian Nemeth
     * @version 1.0
     * @param inputFileName file name that contains the userID in
     * rawdata_userId_date_time format and the input data
     * @return ArrayList containing the resulting Feature objects
     * @throws FeatureExtractorLibrary.FeatureExtractorException if Settings are
     * not properly given
     * @throws FeatureExtractorLibrary.FeatureExtractorException if filename is
     * empty
     * @since 23 ‎July, ‎2018
     */
    public static ArrayList<Feature> extractFeaturesFromCsvFileToArrayListOfFeatures(String inputFileName) throws FeatureExtractorException {
        if (Settings.settingsAreSet()) {
            String userId = extractUserIdFromFileName(inputFileName);
            if (Settings.isUsingCycles()) {
                return extractFeaturesFromCsvFileToArrayListOfFeaturesUsingCycles(inputFileName, userId);
            } else {
                return extractFeaturesFromCsvFileToArrayListOfFeaturesUsingFrames(inputFileName, userId);
            }
        } else {
            if (inputFileName.isEmpty()) {
                throw new FeatureExtractorException(TAG + "incorrect filename");
            }
            throw new FeatureExtractorException(TAG + "Settings are not set correctly");
        }
    }

    /**
     * Extracts features from the given file to an output file that's type is
     * specified in the Settings.
     *
     * @author Krisztian Nemeth
     * @version 1.1
     * @param inputFileName file name containing the input data
     * @param outputFileName output file name that will contain the features
     * @throws FeatureExtractorLibrary.FeatureExtractorException if Settings are
     * not properly given
     * @throws FeatureExtractorLibrary.FeatureExtractorException if filename is
     * empty
     * @since 23 ‎July, ‎2018
     */
    public static void extractFeaturesFromCsvFileToFile(String inputFileName, String outputFileName) throws FeatureExtractorException {
        if (Settings.settingsAreSet() && !inputFileName.isEmpty() && !outputFileName.isEmpty()) {
            String userId = extractUserIdFromFileName(inputFileName);
            outputFileName = getFilenameWithoutExtension(outputFileName);
            if (Settings.isUsingCycles()) {
                extractFeaturesFromCsvFileToFileUsingCycles(inputFileName, outputFileName + (Settings.getOutputFileType() == Settings.FileType.ARFF ? ".arff" : ".csv"), userId);
            } else {
                extractFeaturesFromCsvFileToFileUsingFrames(inputFileName, outputFileName + (Settings.getOutputFileType() == Settings.FileType.ARFF ? ".arff" : ".csv"), userId);
            }
        } else {
            if (inputFileName.isEmpty() || outputFileName.isEmpty()) {
                throw new FeatureExtractorException(TAG + "incorrect filename");
            } else {
                throw new FeatureExtractorException(TAG + "Settings are not set correctly");
            }
        }
    }

    private static ArrayList<Feature> extractFeaturesFromArrayListToArrayListOfFeaturesUsingFrames(List<Accelerometer> dataset, String userId) throws FeatureExtractorException {
        if (Settings.isUsingPreprocessing()) {
            dataset = (new Util()).preprocess(dataset);
        }

        ArrayList<Feature> features = new ArrayList<>();

        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int zero[] = {0, 0, 0};
        final int bins = 10;
        int position = 0;  //indicates the position in the cordX,cordY,... arrays

        WINSIZE = Settings.getFrameSize();

        int skippedDataPointsCount = Settings.getNumFramesIgnored() * Settings.getFrameSize();
        for (int i = skippedDataPointsCount; i < dataset.size() - skippedDataPointsCount; ++i) { //skipping first and last N frames
            if (dataset.size() - skippedDataPointsCount - i < WINSIZE) { //skip the part frames (last acceptable frame)
                break;
            }

            //using the biggest WINSIZE at declaration
            cordX = new double[WINSIZE + 1];
            cordZ = new double[WINSIZE + 1];
            cordY = new double[WINSIZE + 1];
            Amag = new double[WINSIZE + 1];

            while (position < WINSIZE && i < (dataset.size() - skippedDataPointsCount)) { //while it is in the same frame 
                //also skip last N frames
                cordX[position] = dataset.get(i).getX();
                cordY[position] = dataset.get(i).getY();
                cordZ[position] = dataset.get(i).getZ();
                Amag[position] = calculateMagnitude(cordX[position], cordY[position], cordZ[position]);

                //zero crossing
                if (position > 0) {
                    if (cordX[position - 1] * cordX[position] <= 0) {
                        zero[0]++;
                    }
                    if (cordY[position - 1] * cordY[position] <= 0) {
                        zero[1]++;
                    }
                    if (cordZ[position - 1] * cordZ[position] <= 0) {
                        zero[2]++;
                    }
                }
                i++;
                position++;

                //extracting features from vectors if the frame has ended
                if (position >= (dataset.size() - skippedDataPointsCount) || position >= WINSIZE) { //CHECK TODO
                    //-------FEATURES
                    //min
                    if (cordX.length <= 0) {
                        throw new FeatureExtractorException(TAG + "negative array length");
                    }
                    features.add(createFeature(cordX, cordY, cordZ, Amag, position, zero, bins, userId));

                    zero[0] = zero[1] = zero[2] = 0;
                    cordX = new double[WINSIZE + 1];
                    cordZ = new double[WINSIZE + 1];
                    cordY = new double[WINSIZE + 1];
                    Amag = new double[WINSIZE + 1];
                    position = 0;
                }
            }
        }

        return features;
    }

    private static ArrayList<Feature> extractFeaturesFromArrayListToArrayListOfFeaturesUsingCycles(List<Accelerometer> dataset, String userId) throws FeatureExtractorException {
        if (Settings.isUsingPreprocessing()) {
            dataset = (new Util()).preprocess(dataset);
        }

        ArrayList<Feature> features = new ArrayList<>();

        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int counter = 0;
        int zero[] = {0, 0, 0};
        final int bins = 10;
        int cyclometer;  //indicates the number of the curent step
        int position = 0;  //indicates the position in the cordX,cordY,... arrays

        //search the biggest WINSIZE
        WINSIZE = 0;
        cyclometer = dataset.get(0).getStep();  //number of the first recorded step from the dataset
        int index = 0;
        while (index < dataset.size() - 1) {
            if (dataset.get(index).getStep() == cyclometer) {
                counter++;
            } else {
                if (counter > WINSIZE) {
                    WINSIZE = counter;
                }
                counter = 0;
                cyclometer++;
            }
            index++;
        }

        counter = 0;
        cyclometer = dataset.get(0).getStep();

        int i = 0;
        while (i < dataset.size() && dataset.get(i).getStep() <= cyclometer + Settings.getNumStepsIgnored()) { //skipping first N steps
            ++i;
        }

        int lastStep = dataset.get(dataset.size() - 1).getStep() - Settings.getNumStepsIgnored(); //first step that is not accepted anymore
        cyclometer++;
        for (; i < dataset.size(); ++i) {
            //using the biggest WINSIZE at declaration
            cordX = new double[WINSIZE + 1];
            cordZ = new double[WINSIZE + 1];
            cordY = new double[WINSIZE + 1];
            Amag = new double[WINSIZE + 1];
            while (i < dataset.size() && cyclometer == dataset.get(i).getStep() && position < WINSIZE) { //while it is in the same step
                cordX[position] = dataset.get(i).getX();
                cordY[position] = dataset.get(i).getY();
                cordZ[position] = dataset.get(i).getZ();
                Amag[position] = calculateMagnitude(cordX[position], cordY[position], cordZ[position]);

                //zero crossing
                if (position > 0) {
                    if (cordX[position - 1] * cordX[position] <= 0) {
                        zero[0]++;
                    }
                    if (cordY[position - 1] * cordY[position] <= 0) {
                        zero[1]++;
                    }
                    if (cordZ[position - 1] * cordZ[position] <= 0) {
                        zero[2]++;
                    }
                }
                i++;
                position++;
                counter = i;

                //extracting features from vectors if the step has ended
                if (counter < dataset.size() && cyclometer < dataset.get(counter).getStep() && cyclometer < lastStep) { //CHECK TODO
                    ///not outOfBounds                          end of step                     not last step
                    ///                 because we ignore the last step
                    //-------FEATURES
                    //min
                    if (cordX.length <= 0) {
                        throw new FeatureExtractorException(TAG + "negative array length");
                    }

                    features.add(createFeature(cordX, cordY, cordZ, Amag, position, zero, bins, userId));

                    zero[0] = zero[1] = zero[2] = 0;
                    cordX = new double[WINSIZE + 1];
                    cordZ = new double[WINSIZE + 1];
                    cordY = new double[WINSIZE + 1];
                    Amag = new double[WINSIZE + 1];
                    //counter=0;
                    cyclometer++;
                    position = 0;
                }
            }
        }

        return features;
    }

    private static void extractFeaturesFromArrayListToFileUsingCycles(List<Accelerometer> dataset, String userId, String filename) throws FeatureExtractorException {
        if (Settings.isUsingPreprocessing()) {
            dataset = (new Util()).preprocess(dataset);
        }

        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int counter = 0;
        int zero[] = {0, 0, 0};
        final int bins = 10;
        int cyclometer;  //indicates the number of the curent step
        int position = 0;  //indicates the position in the cordX,cordY,... arrays
        //search the biggest WINSIZE
        try (PrintStream writer = generateOutputFile(filename)) {
            if (Settings.getOutputHasHeader()) {//adding header to output
                if (Settings.getOutputFileType() == Settings.FileType.CSV) {
                    writer.print(generateCsvHeader());
                }
                if (Settings.getOutputFileType() == Settings.FileType.ARFF) {
                    writer.print(generateArffHeader(userId));
                }
            }

            //search the biggest WINSIZE
            WINSIZE = 0;
            cyclometer = dataset.get(0).getStep();  //number of the first recorded step from the dataset
            int index = 0;
            while (index < dataset.size() - 1) {
                if (dataset.get(index).getStep() == cyclometer) {
                    counter++;
                } else {
                    if (counter > WINSIZE) {
                        WINSIZE = counter;
                    }
                    counter = 0;
                    cyclometer++;
                }
                index++;
            }

            counter = 0;
            cyclometer = dataset.get(0).getStep();
            int i = 0;
            while (i < dataset.size() && dataset.get(i).getStep() <= cyclometer + Settings.getNumStepsIgnored()) { //skipping first N steps
                ++i;
            }

            int lastStep = dataset.get(dataset.size() - 1).getStep() - Settings.getNumStepsIgnored(); //first step that is not accepted anymore
            cyclometer++;
            for (; i < dataset.size(); ++i) {
                //using the biggest WINSIZE at declaration
                cordX = new double[WINSIZE + 1];
                cordZ = new double[WINSIZE + 1];
                cordY = new double[WINSIZE + 1];
                Amag = new double[WINSIZE + 1];
                counter = i;

                while (i < dataset.size() && cyclometer == dataset.get(i).getStep() && position < WINSIZE) { //while it is in the same step
                    cordX[position] = dataset.get(i).getX();
                    cordY[position] = dataset.get(i).getY();
                    cordZ[position] = dataset.get(i).getZ();
                    Amag[position] = calculateMagnitude(cordX[position], cordY[position], cordZ[position]);

                    //zero crossing
                    if (position > 0) {
                        if (cordX[position - 1] * cordX[position] <= 0) {
                            zero[0]++;
                        }
                        if (cordY[position - 1] * cordY[position] <= 0) {
                            zero[1]++;
                        }
                        if (cordZ[position - 1] * cordZ[position] <= 0) {
                            zero[2]++;
                        }
                    }
                    i++;
                    position++;
                    counter = i;

                    //extracting features from vectors if the step has ended
                    if (counter < dataset.size() && cyclometer < dataset.get(counter).getStep() && cyclometer < lastStep) { //CHECK TODO
                        ///not outOfBounds                          end of step                     not last step
                        ///                 because we ignore the last step
                        //-------FEATURES
                        if (cordX.length <= 0) {
                            throw new FeatureExtractorException(TAG + "negative array length");
                        }

                        fillDataStringWithFeature(cordX, cordY, cordZ, Amag, position, zero, bins, userId);

                        //writing to file
                        appendToFile(writer, dataString);

                        zero[0] = zero[1] = zero[2] = 0;
                        cordX = new double[WINSIZE + 1];
                        cordZ = new double[WINSIZE + 1];
                        cordY = new double[WINSIZE + 1];
                        Amag = new double[WINSIZE + 1];

                        cyclometer++;
                        position = 0;
                    }
                }
            }
        }
    }

    private static void extractFeaturesFromArrayListToFileUsingFrames(List<Accelerometer> dataset, String userId, String filename) throws FeatureExtractorException {
        if (Settings.isUsingPreprocessing()) {
            dataset = (new Util()).preprocess(dataset);
        }

        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int zero[] = {0, 0, 0};
        final int bins = 10;
        int position = 0;  //indicates the position in the cordX,cordY,... arrays
        try (PrintStream writer = generateOutputFile(filename)) {
            if (Settings.getOutputHasHeader()) {//adding header to output
                if (Settings.getOutputFileType() == Settings.FileType.CSV) {
                    writer.print(generateCsvHeader());
                }
                if (Settings.getOutputFileType() == Settings.FileType.ARFF) {
                    writer.print(generateArffHeader(userId));
                }
            }

            WINSIZE = Settings.getFrameSize();

            int skippedDataPointsCount = Settings.getNumFramesIgnored() * Settings.getFrameSize();
            for (int i = skippedDataPointsCount; i < dataset.size() - skippedDataPointsCount; ++i) { //skipping first and last N frames
                if (dataset.size() - skippedDataPointsCount - i < WINSIZE) { //skip the part frames (last acceptable frame)
                    break;
                }

                cordX = new double[WINSIZE + 1];
                cordZ = new double[WINSIZE + 1];
                cordY = new double[WINSIZE + 1];
                Amag = new double[WINSIZE + 1];

                while (position < WINSIZE && i < (dataset.size() - skippedDataPointsCount)) { //while it is in the same frame 
                    //also skip last step
                    cordX[position] = dataset.get(i).getX();
                    cordY[position] = dataset.get(i).getY();
                    cordZ[position] = dataset.get(i).getZ();
                    Amag[position] = calculateMagnitude(cordX[position], cordY[position], cordZ[position]);

                    //zero crossing
                    if (position > 0) {
                        if (cordX[position - 1] * cordX[position] <= 0) {
                            zero[0]++;
                        }
                        if (cordY[position - 1] * cordY[position] <= 0) {
                            zero[1]++;
                        }
                        if (cordZ[position - 1] * cordZ[position] <= 0) {
                            zero[2]++;
                        }
                    }
                    i++;
                    position++;

                    //extracting features from vectors if the frame has ended
                    if (position >= (dataset.size() - skippedDataPointsCount) || position >= WINSIZE) {
                        //-------FEATURES
                        //min
                        if (cordX.length <= 0) {
                            throw new FeatureExtractorException(TAG + "negative array length");
                        }

                        fillDataStringWithFeature(cordX, cordY, cordZ, Amag, position, zero, bins, userId);

                        //writing to file
                        appendToFile(writer, dataString);

                        zero[0] = zero[1] = zero[2] = 0;
                        cordX = new double[WINSIZE + 1];
                        cordZ = new double[WINSIZE + 1];
                        cordY = new double[WINSIZE + 1];
                        Amag = new double[WINSIZE + 1];
                        position = 0;
                    }
                }
            }
        }
    }

    private static ArrayList<Feature> extractFeaturesFromCsvFileToArrayListOfFeaturesUsingCycles(String inputFileName, String userId) throws FeatureExtractorException {
        List<Accelerometer> dataset = readDatasetFromCsv(inputFileName);

        return extractFeaturesFromArrayListToArrayListOfFeaturesUsingCycles(dataset, userId);
    }

    private static ArrayList<Feature> extractFeaturesFromCsvFileToArrayListOfFeaturesUsingFrames(String inputFileName, String userId) throws FeatureExtractorException {
        List<Accelerometer> dataset = readDatasetFromCsv(inputFileName);

        return extractFeaturesFromArrayListToArrayListOfFeaturesUsingFrames(dataset, userId);
    }

    private static void extractFeaturesFromCsvFileToFileUsingCycles(String inputFileName, String outputFileName, String userId) throws FeatureExtractorException {
        List<Accelerometer> dataset = readDatasetFromCsv(inputFileName);

        extractFeaturesFromArrayListToFileUsingCycles(dataset, userId, outputFileName);
    }

    private static void extractFeaturesFromCsvFileToFileUsingFrames(String inputFileName, String outputFileName, String userId) throws FeatureExtractorException {
        List<Accelerometer> dataset = readDatasetFromCsv(inputFileName);

        extractFeaturesFromArrayListToFileUsingFrames(dataset, userId, outputFileName);
    }

    private static PrintStream generateOutputFile(String filename) throws FeatureExtractorException {
        PrintStream writer = null;

        try {
            writer = new PrintStream(filename);
        } catch (FileNotFoundException ex) {
            throw new FeatureExtractorException(TAG + "File opening error " + filename);
        }
        return writer;
    }

    private static void appendToFile(PrintStream writer, StringBuilder builder) {
        writer.println(builder);
    }

    private static String generateCsvHeader() {
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

    private static String generateArffHeader(String userId) {
        StringBuilder header = new StringBuilder();

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

        header.append("@attribute userID {");
        header.append(userId);
        header.append("}\n\n");

        header.append("@data\n");

        return header.toString();
    }

    private static double calculateMagnitude(double cordX, double cordY, double cordZ) {
        return Math.sqrt(cordX * cordX + cordY * cordY + cordZ * cordZ);
    }

    private static double calculateMagnitude(Accelerometer dataPoint) {
        return Math.sqrt(dataPoint.getX() * dataPoint.getX() + dataPoint.getY() * dataPoint.getY() + dataPoint.getZ() * dataPoint.getZ());
    }

    private static double min(double[] arr, int length) {
        double m = arr[0];
        for (int i = 0; i < length; ++i) {
            if (m > arr[i]) {
                m = arr[i];
            }
        }
        return m;
    }

    private static double max(double[] arr, int length) {
        double m = arr[0];
        for (int i = 0; i < length; ++i) {
            if (m < arr[i]) {
                m = arr[i];
            }
        }
        return m;
    }

    private static double mean(double[] arr, int length) throws FeatureExtractorException {
        double total = 0.0;
        for (int i = 0; i < length; i++) {
            total += arr[i];
        }
        if (Double.isNaN(total)) {
            throw new FeatureExtractorException(TAG + "NANerror2 total " + total);
        }
        if (Double.isNaN(length) || length <= 0) {
            throw new FeatureExtractorException(TAG + "NANlength length " + length);
        }
        return total / length;
    }

    private static double stddev(double[] arr, int length, double mymean) {
        double sum = 0.0;
        for (int i = 0; i < length; i++) {
            sum += Math.pow(arr[i] - mymean, 2);
        }
        return Math.sqrt(sum / length);
    }

    private static double absdif(double[] arr, int length, double mymean) {
        double sum = 0.0;
        for (int i = 0; i < length; i++) {
            sum += Math.abs(arr[i] - mymean);
        }
        return sum / length;
    }

    private static double[] histogram(double[] arr, int length, double mymin, double mymax, int noOfBins) {
        double histo[] = new double[noOfBins];
        //double binSize=Math.floor((Math.abs(mymin)+Math.abs(mymax))/noOfBins);
        double binSize = (mymax - mymin) / noOfBins;
        int index;
        for (int i = 0; i < length; ++i) {

            index = (int) Math.floor(Math.abs(arr[i] - mymin) / binSize);    //corresponding bin

            if (index >= noOfBins) {
                histo[noOfBins - 1]++;
            } else {
                if (index < 0) {
                    histo[0]++;
                } else {
                    histo[index]++;
                }
            }
        }
        //percentage
        for (int i = 0; i < noOfBins; ++i) {
            histo[i] /= length;
        }
        return histo;
    }

    private static String histoToString(double[] histo, int length) {
        String str = "";
        for (int i = 0; i < length - 1; ++i) {
            str += histo[i] + ",";
        }
        str += histo[length - 1];
        return str;
    }

    private static Feature createFeature(double[] cordX, double[] cordY, double[] cordZ, double[] Amag, int dataLength, int[] zero, int bins, String userId) throws FeatureExtractorException {
        double minX = min(cordX, dataLength);
        double minY = min(cordY, dataLength);
        double minZ = min(cordZ, dataLength);
        double minA = min(Amag, dataLength);

        double meanX = mean(cordX, dataLength);
        double meanY = mean(cordY, dataLength);
        double meanZ = mean(cordZ, dataLength);
        double meanA = mean(Amag, dataLength);

        Feature feature = new Feature(minX, minY, minZ, minA,
                meanX, meanY, meanZ, meanA,
                stddev(cordX, dataLength, meanX), stddev(cordY, dataLength, meanY), stddev(cordZ, dataLength, meanZ), stddev(Amag, dataLength, meanA),
                absdif(cordX, dataLength, meanX), absdif(cordY, dataLength, meanY), absdif(cordZ, dataLength, meanZ), absdif(Amag, dataLength, meanA),
                (double) zero[0] / dataLength, (double) zero[1] / dataLength, (double) zero[2] / dataLength,
                histogram(cordX, dataLength, -Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, bins), histogram(cordY, dataLength, Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, bins), histogram(cordZ, dataLength, -Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, bins), histogram(Amag, dataLength, 0, 2 * Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, bins),
                userId);                   ///-Settings.c_G*G, Settings.c_G*G                                                                                                                                      0, 2*Settings.c_G*G
        return feature;
    }

    private static void fillDataStringWithFeature(double[] cordX, double[] cordY, double[] cordZ, double[] Amag, int dataLength, int[] zero, int bins, String userId) throws FeatureExtractorException {
        //empty datastring first
        dataString.delete(0, dataString.length());

        double minX = min(cordX, dataLength);
        double minY = min(cordY, dataLength);
        double minZ = min(cordZ, dataLength);
        double minA = min(Amag, dataLength);
        dataString.append(minX);
        dataString.append(",");
        dataString.append(minY);
        dataString.append(",");
        dataString.append(minZ);
        dataString.append(",");
        dataString.append(minA);
        dataString.append(",");

        double meanX = mean(cordX, dataLength);
        double meanY = mean(cordY, dataLength);
        double meanZ = mean(cordZ, dataLength);
        double meanA = mean(Amag, dataLength);
        dataString.append(meanX);
        dataString.append(",");
        dataString.append(meanY);
        dataString.append(",");
        dataString.append(meanZ);
        dataString.append(",");
        dataString.append(meanA);
        dataString.append(",");

        dataString.append(stddev(cordX, dataLength, meanX));
        dataString.append(",");
        dataString.append(stddev(cordY, dataLength, meanY));
        dataString.append(",");
        dataString.append(stddev(cordZ, dataLength, meanZ));
        dataString.append(",");
        dataString.append(stddev(Amag, dataLength, meanA));
        dataString.append(",");

        dataString.append(absdif(cordX, dataLength, meanX));
        dataString.append(",");
        dataString.append(absdif(cordY, dataLength, meanY));
        dataString.append(",");
        dataString.append(absdif(cordZ, dataLength, meanZ));
        dataString.append(",");
        dataString.append(absdif(Amag, dataLength, meanA));
        dataString.append(",");

        dataString.append((double) zero[0] / dataLength);
        dataString.append(",");
        dataString.append((double) zero[1] / dataLength);
        dataString.append(",");
        dataString.append((double) zero[2] / dataLength);
        dataString.append(",");

        dataString.append(histoToString(histogram(cordX, dataLength, -Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, bins), bins));
        dataString.append(",");
        dataString.append(histoToString(histogram(cordY, dataLength, -Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, bins), bins));
        dataString.append(",");
        dataString.append(histoToString(histogram(cordZ, dataLength, -Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, bins), bins));
        dataString.append(",");
        dataString.append(histoToString(histogram(Amag, dataLength, 0, 2 * Settings.getHistogramGravityMultiplier() * Settings.GRAVITY, bins), bins));
        dataString.append(",");

        dataString.append(userId);
    }

    private static List<Accelerometer> readDatasetFromCsv(String inputFileName) throws FeatureExtractorException {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(inputFileName));
        } catch (FileNotFoundException ex) {
            throw new FeatureExtractorException(TAG + "Unable to open file " + inputFileName);
        }

        if (Settings.getInputHasHeader() && scanner.hasNextLine()) {
            scanner.nextLine();
        }

        List<Accelerometer> dataset = new ArrayList<>();
        while (scanner.hasNextLine()) {  //lines starting the first index in cycles.txt
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }
            String items[] = line.split(",");
            if (items.length != 5) {
                throw new FeatureExtractorException(TAG + "Corrupted input file");
            }

            dataset.add(new Accelerometer(Long.parseLong(items[0]),
                    Double.parseDouble(items[1]),
                    Double.parseDouble(items[2]),
                    Double.parseDouble(items[3]),
                    Integer.parseInt(items[4])));
        }

        return dataset;
    }
    
    private static String extractUserIdFromFileName(String inputFileName){
        String userId = Settings.getDefaultUserId();
        int lastslash = inputFileName.lastIndexOf("/");
        int lastbackslash = inputFileName.lastIndexOf("\\");
        String filename = inputFileName.substring((lastslash > lastbackslash ? lastslash : lastbackslash) + 1);
        if (filename.matches("[A-Za-z0-9]+_[A-Za-z0-9]+_.*")) {
            userId = filename.substring(filename.indexOf('_') + 1, filename.indexOf('_', filename.indexOf('_') + 1));
            System.out.println("UserId found in filename: " + userId);
        }
        else{
            System.out.println("UserId not found in filename; assigning default UserId");
        }
        return userId;
    }
    
    public static String getFilenameWithoutExtension(String filename){
        int lastslash = filename.lastIndexOf("/");
        int lastbackslash = filename.lastIndexOf("\\");
        //stripping path from filename
        String path = filename.substring(0, (lastslash > lastbackslash ? lastslash : lastbackslash) + 1);
        String file = filename.substring((lastslash > lastbackslash ? lastslash : lastbackslash) + 1);
        int index = file.lastIndexOf(".");
        if (index > 0) {   //might have extension
            file = file.substring(0, index);
        }
        return path+file;
    }
}

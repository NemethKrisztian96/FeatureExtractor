package FeatureExtractorLibrary;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
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
public class FeatureExtractor {

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
            String userId = Settings.getDefaultUserId();
            if (inputFileName.matches("[A-Za-z0-9]+_[A-Za-z0-9]+_.*")) {
                userId = inputFileName.substring(inputFileName.indexOf('_') + 1, inputFileName.indexOf('_', inputFileName.indexOf('_') + 1));
            }
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
            String userId = Settings.getDefaultUserId();
            if (inputFileName.matches("[A-Za-z0-9]+_[A-Za-z0-9]+_.*")) {
                userId = inputFileName.substring(inputFileName.indexOf('_') + 1, inputFileName.indexOf('_', inputFileName.indexOf('_') + 1));
                System.out.println(userId);
            }
            if (Settings.isUsingCycles()) {
                extractFeaturesFromCsvFileToFileUsingCycles(inputFileName, outputFileName + (Settings.getOutputFileType() == Settings.FileType.ARFF ? ".arff" : ".csv"), userId);
            } else {
                extractFeaturesFromCsvFileToFileUsingFrames(inputFileName, outputFileName + (Settings.getOutputFileType() == Settings.FileType.ARFF ? ".arff" : ".csv"), userId);
            }
        } else {
            if (inputFileName.isEmpty() || outputFileName.isEmpty()) {
                throw new FeatureExtractorException(TAG + "incorrect filename");
            }
            throw new FeatureExtractorException(TAG + "Settings are not set correctly");
        }
    }

    private static ArrayList<Feature> extractFeaturesFromArrayListToArrayListOfFeaturesUsingFrames(ArrayList<Accelerometer> dataset, String userId) throws FeatureExtractorException {
        ArrayList<Feature> features = new ArrayList<>();

        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int zero[] = {0, 0, 0};
        double meanX, meanY, meanZ, meanA, minX, minY, minZ, minA;
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
                Amag[position] = Math.sqrt(cordX[position] * cordX[position] + cordY[position] * cordY[position] + cordZ[position] * cordZ[position]);

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
                    minX = min(cordX, position);
                    minY = min(cordY, position);
                    minZ = min(cordZ, position);
                    minA = min(Amag, position);

                    meanX = mean(cordX, position);
                    meanY = mean(cordY, position);
                    meanZ = mean(cordZ, position);
                    meanA = mean(Amag, position);
                    if (Double.isNaN(meanX)) {
                        throw new FeatureExtractorException(TAG + "NANerror " + position + "," + dataset.get(i).getStep());
                    }

                    //adding features
                    features.add(new Feature(minX, minY, minZ, minA,
                            meanX, meanY, meanZ, meanA,
                            stddev(cordX, position, meanX), stddev(cordY, position, meanY), stddev(cordZ, position, meanZ), stddev(Amag, position, meanA),
                            absdif(cordX, position, meanX), absdif(cordY, position, meanY), absdif(cordZ, position, meanZ), absdif(Amag, position, meanA),
                            (double) zero[0] / position, (double) zero[1] / position, (double) zero[2] / position,
                            histogram(cordX, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(cordY, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(cordZ, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(Amag, position, 0, 3 * 9.8, bins),
                            userId));

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

    private static ArrayList<Feature> extractFeaturesFromArrayListToArrayListOfFeaturesUsingCycles(ArrayList<Accelerometer> dataset, String userId) throws FeatureExtractorException {
        ArrayList<Feature> features = new ArrayList<>();

        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int counter = 0;
        int zero[] = {0, 0, 0};
        double meanX, meanY, meanZ, meanA, minX, minY, minZ, minA;
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
        while (dataset.get(i).getStep() <= cyclometer+Settings.getNumStepsIgnored()) { //skipping first N steps
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
            while (cyclometer == dataset.get(i).getStep() && position < WINSIZE) { //while it is in the same step
                cordX[position] = dataset.get(i).getX();
                cordY[position] = dataset.get(i).getY();
                cordZ[position] = dataset.get(i).getZ();
                Amag[position] = Math.sqrt(cordX[position] * cordX[position] + cordY[position] * cordY[position] + cordZ[position] * cordZ[position]);

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
                    minX = min(cordX, position);
                    minY = min(cordY, position);
                    minZ = min(cordZ, position);
                    minA = min(Amag, position);

                    meanX = mean(cordX, position);
                    meanY = mean(cordY, position);
                    meanZ = mean(cordZ, position);
                    meanA = mean(Amag, position);
                    if (Double.isNaN(meanX)) {
                        throw new FeatureExtractorException(TAG + "NANerror " + cyclometer + "," + counter + ", step " + dataset.get(i).getStep());
                    }

                    //adding features
                    features.add(new Feature(minX, minY, minZ, minA,
                            meanX, meanY, meanZ, meanA,
                            stddev(cordX, position, meanX), stddev(cordY, position, meanY), stddev(cordZ, position, meanZ), stddev(Amag, position, meanA),
                            absdif(cordX, position, meanX), absdif(cordY, position, meanY), absdif(cordZ, position, meanZ), absdif(Amag, position, meanA),
                            (double) zero[0] / position, (double) zero[1] / position, (double) zero[2] / position,
                            histogram(cordX, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(cordY, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(cordZ, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(Amag, position, 0, 3 * 9.8, bins),
                            userId));

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

    private static void extractFeaturesFromArrayListToFileUsingCycles(ArrayList<Accelerometer> dataset, String userId, String filename) throws FeatureExtractorException {
        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int counter = 0;
        int zero[] = {0, 0, 0};
        double meanX, meanY, meanZ, meanA, minX, minY, minZ, minA;
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
            while (dataset.get(i).getStep() <= cyclometer+Settings.getNumStepsIgnored()) { //skipping first N steps
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
                counter=i;

                while (cyclometer == dataset.get(i).getStep() && position < WINSIZE) { //while it is in the same step
                    cordX[position] = dataset.get(i).getX();
                    cordY[position] = dataset.get(i).getY();
                    cordZ[position] = dataset.get(i).getZ();
                    Amag[position] = Math.sqrt(cordX[position] * cordX[position] + cordY[position] * cordY[position] + cordZ[position] * cordZ[position]);

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
                        minX = min(cordX, position);
                        minY = min(cordY, position);
                        minZ = min(cordZ, position);
                        minA = min(Amag, position);
                        dataString.append(minX);
                        dataString.append(",");
                        dataString.append(minY);
                        dataString.append(",");
                        dataString.append(minZ);
                        dataString.append(",");
                        dataString.append(minA);
                        dataString.append(",");

                        meanX = mean(cordX, position);
                        meanY = mean(cordY, position);
                        meanZ = mean(cordZ, position);
                        meanA = mean(Amag, position);
                        if (Double.isNaN(meanX)) {
                            throw new FeatureExtractorException(TAG + "NANerror " + cyclometer + "," + counter + ", step " + dataset.get(i).getStep());
                        }
                        dataString.append(meanX);
                        dataString.append(",");
                        dataString.append(meanY);
                        dataString.append(",");
                        dataString.append(meanZ);
                        dataString.append(",");
                        dataString.append(meanA);
                        dataString.append(",");

                        dataString.append(stddev(cordX, position, meanX));
                        dataString.append(",");
                        dataString.append(stddev(cordY, position, meanY));
                        dataString.append(",");
                        dataString.append(stddev(cordZ, position, meanZ));
                        dataString.append(",");
                        dataString.append(stddev(Amag, position, meanA));
                        dataString.append(",");

                        dataString.append(absdif(cordX, position, meanX));
                        dataString.append(",");
                        dataString.append(absdif(cordY, position, meanY));
                        dataString.append(",");
                        dataString.append(absdif(cordZ, position, meanZ));
                        dataString.append(",");
                        dataString.append(absdif(Amag, position, meanA));
                        dataString.append(",");

                        dataString.append((double) zero[0] / position);
                        dataString.append(",");
                        dataString.append((double) zero[1] / position);
                        dataString.append(",");
                        dataString.append((double) zero[2] / position);
                        dataString.append(",");

                        dataString.append(histoToString(histogram(cordX, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(cordY, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(cordZ, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(Amag, position, 0, 3 * 9.8, bins), bins));
                        dataString.append(",");

                        dataString.append(userId);

                        //writing to file
                        appendToFile(writer, dataString);
                        dataString.delete(0, dataString.length());

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

    private static void extractFeaturesFromArrayListToFileUsingFrames(ArrayList<Accelerometer> dataset, String userId, String filename) throws FeatureExtractorException {
        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int zero[] = {0, 0, 0};
        double meanX, meanY, meanZ, meanA, minX, minY, minZ, minA;
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
                    Amag[position] = Math.sqrt(cordX[position] * cordX[position] + cordY[position] * cordY[position] + cordZ[position] * cordZ[position]);

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
                        minX = min(cordX, position);
                        minY = min(cordY, position);
                        minZ = min(cordZ, position);
                        minA = min(Amag, position);
                        dataString.append(minX);
                        dataString.append(",");
                        dataString.append(minY);
                        dataString.append(",");
                        dataString.append(minZ);
                        dataString.append(",");
                        dataString.append(minA);
                        dataString.append(",");

                        meanX = mean(cordX, position);
                        meanY = mean(cordY, position);
                        meanZ = mean(cordZ, position);
                        meanA = mean(Amag, position);
                        if (Double.isNaN(meanX)) {
                            throw new FeatureExtractorException(TAG + "NANerror " + position + "," + dataset.get(i).getStep());
                        }
                        dataString.append(meanX);
                        dataString.append(",");
                        dataString.append(meanY);
                        dataString.append(",");
                        dataString.append(meanZ);
                        dataString.append(",");
                        dataString.append(meanA);
                        dataString.append(",");

                        dataString.append(stddev(cordX, position, meanX));
                        dataString.append(",");
                        dataString.append(stddev(cordY, position, meanY));
                        dataString.append(",");
                        dataString.append(stddev(cordZ, position, meanZ));
                        dataString.append(",");
                        dataString.append(stddev(Amag, position, meanA));
                        dataString.append(",");

                        dataString.append(absdif(cordX, position, meanX));
                        dataString.append(",");
                        dataString.append(absdif(cordY, position, meanY));
                        dataString.append(",");
                        dataString.append(absdif(cordZ, position, meanZ));
                        dataString.append(",");
                        dataString.append(absdif(Amag, position, meanA));
                        dataString.append(",");

                        dataString.append((double) zero[0] / position);
                        dataString.append(",");
                        dataString.append((double) zero[1] / position);
                        dataString.append(",");
                        dataString.append((double) zero[2] / position);
                        dataString.append(",");

                        dataString.append(histoToString(histogram(cordX, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(cordY, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(cordZ, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(Amag, position, 0, 3 * 9.8, bins), bins));
                        dataString.append(",");

                        dataString.append(userId);

                        //writing to file
                        appendToFile(writer, dataString);
                        dataString.delete(0, dataString.length());

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
        ArrayList<Feature> features = new ArrayList<>();

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(inputFileName));
        } catch (FileNotFoundException ex) {
            throw new FeatureExtractorException(TAG + "Unable to open file " + inputFileName);

        }

        if (Settings.getInputHasHeader() && scanner.hasNextLine()) {
            scanner.nextLine();
        }

        ArrayList<Accelerometer> dataset = new ArrayList<>();
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

        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int counter = 0;
        int zero[] = {0, 0, 0};
        double meanX, meanY, meanZ, meanA, minX, minY, minZ, minA;
        final int bins = 10;
        int cyclometer;  //indicates the number of the curent step
        int position = 0;  //indicates the position in the cordX,cordY,... arrays

        //search the biggest WINSIZE
        WINSIZE = 0;
        cyclometer = dataset.get(0).getStep();  //number of the first recorded step from the dataset
        int index = 0;

        while (index < dataset.size()) {
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

        int i = 0;
        while (dataset.get(i).getStep() <= cyclometer+Settings.getNumStepsIgnored()) { //skipping first N steps
            ++i;
        }

        int lastStep = dataset.get(dataset.size() - 1).getStep() - Settings.getNumStepsIgnored(); //first step that is not accepted anymore
        for (; i < dataset.size(); ++i) {
            //using the biggest WINSIZE at declaration
            cordX = new double[WINSIZE + 1];
            cordZ = new double[WINSIZE + 1];
            cordY = new double[WINSIZE + 1];
            Amag = new double[WINSIZE + 1];
            counter=i;
            
            while (cyclometer == dataset.get(i).getStep() && position < WINSIZE) { //while it is in the same step
                cordX[position] = dataset.get(i).getX();
                cordY[position] = dataset.get(i).getY();
                cordZ[position] = dataset.get(i).getZ();
                Amag[position] = Math.sqrt(cordX[position] * cordX[position] + cordY[position] * cordY[position] + cordZ[position] * cordZ[position]);

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
                counter++;

                //extracting features from vectors if the step has ended
                if (counter < dataset.size() && (cyclometer < dataset.get(counter).getStep() && cyclometer < lastStep)) { //CHECK TODO
                    ///not outOfBounds                          end of step                     not last step
                    ///                 because we ignore the last step
                    //-------FEATURES
                    //min
                    if (cordX.length <= 0) {
                        throw new FeatureExtractorException(TAG + "negative array length");
                    }
                    minX = min(cordX, position);
                    minY = min(cordY, position);
                    minZ = min(cordZ, position);
                    minA = min(Amag, position);

                    meanX = mean(cordX, position);
                    meanY = mean(cordY, position);
                    meanZ = mean(cordZ, position);
                    meanA = mean(Amag, position);
                    if (Double.isNaN(meanX)) {
                        throw new FeatureExtractorException(TAG + "NANerror " + cyclometer + "," + counter + "," + dataset.get(i).getStep());
                    }

                    //adding features
                    features.add(new Feature(minX, minY, minZ, minA,
                            meanX, meanY, meanZ, meanA,
                            stddev(cordX, position, meanX), stddev(cordY, position, meanY), stddev(cordZ, position, meanZ), stddev(Amag, position, meanA),
                            absdif(cordX, position, meanX), absdif(cordY, position, meanY), absdif(cordZ, position, meanZ), absdif(Amag, position, meanA),
                            (double) zero[0] / position, (double) zero[1] / position, (double) zero[2] / position,
                            histogram(cordX, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(cordY, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(cordZ, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(Amag, position, 0, 3 * 9.8, bins),
                            userId));

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

        return features;
    }

    private static ArrayList<Feature> extractFeaturesFromCsvFileToArrayListOfFeaturesUsingFrames(String inputFileName, String userId) throws FeatureExtractorException {
        ArrayList<Feature> features = new ArrayList<>();

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(inputFileName));
        } catch (FileNotFoundException ex) {
            throw new FeatureExtractorException(TAG + "Unable to open file " + inputFileName);

        }

        if (Settings.getInputHasHeader() && scanner.hasNextLine()) {
            scanner.nextLine();
        }

        ArrayList<Accelerometer> dataset = new ArrayList<>();
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

        WINSIZE = Settings.getFrameSize();
        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int zero[] = {0, 0, 0};
        double meanX, meanY, meanZ, meanA, minX, minY, minZ, minA;
        final int bins = 10;
        int position = 0;  //indicates the position in the cordX,cordY,... arrays

        int skippedDataPointsCount = Settings.getNumFramesIgnored() * Settings.getFrameSize();
        for (int i = skippedDataPointsCount; i < dataset.size() - skippedDataPointsCount; ++i) {//skipping first and last N frames
            if (dataset.size() - skippedDataPointsCount - i < WINSIZE) { //skip the part frames (last acceptable frame)
                break;
            }
            
            //using the biggest WINSIZE at declaration
            cordX = new double[WINSIZE + 1];
            cordZ = new double[WINSIZE + 1];
            cordY = new double[WINSIZE + 1];
            Amag = new double[WINSIZE + 1];

            while (position < WINSIZE && i < (dataset.size() - skippedDataPointsCount)) { //while it is in the same frame 
                //also skip last step
                cordX[position] = dataset.get(i).getX();
                cordY[position] = dataset.get(i).getY();
                cordZ[position] = dataset.get(i).getZ();
                Amag[position] = Math.sqrt(cordX[position] * cordX[position] + cordY[position] * cordY[position] + cordZ[position] * cordZ[position]);

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
                    ///not outOfBounds          not last step                 end of step
                    ///                 because we ignore the last step
                    //-------FEATURES
                    //min

                    if (cordX.length <= 0) {
                        throw new FeatureExtractorException(TAG + "negative array length");
                    }
                    minX = min(cordX, position);
                    minY = min(cordY, position);
                    minZ = min(cordZ, position);
                    minA = min(Amag, position);

                    meanX = mean(cordX, position);
                    meanY = mean(cordY, position);
                    meanZ = mean(cordZ, position);
                    meanA = mean(Amag, position);
                    if (Double.isNaN(meanX)) {
                        throw new FeatureExtractorException(TAG + "NANerror meanX at step " + dataset.get(i).getStep());
                    }

                    //adding features
                    features.add(new Feature(minX, minY, minZ, minA,
                            meanX, meanY, meanZ, meanA,
                            stddev(cordX, position, meanX), stddev(cordY, position, meanY), stddev(cordZ, position, meanZ), stddev(Amag, position, meanA),
                            absdif(cordX, position, meanX), absdif(cordY, position, meanY), absdif(cordZ, position, meanZ), absdif(Amag, position, meanA),
                            (double) zero[0] / position, (double) zero[1] / position, (double) zero[2] / position,
                            histogram(cordX, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(cordY, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(cordZ, position, -1.5 * 9.8, 1.5 * 9.8, bins), histogram(Amag, position, 0, 3 * 9.8, bins),
                            userId));

                    zero[0] = zero[1] = zero[2] = 0;
                    cordX = new double[WINSIZE + 1];
                    cordZ = new double[WINSIZE + 1];
                    cordY = new double[WINSIZE + 1];
                    Amag = new double[WINSIZE + 1];
                    //counter=0;
                    position = 0;
                }
            }
        }

        return features;
    }

    private static void extractFeaturesFromCsvFileToFileUsingCycles(String inputFileName, String outputFileName, String userId) throws FeatureExtractorException {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(inputFileName));
        } catch (FileNotFoundException ex) {
            throw new FeatureExtractorException(TAG + "Unable to open file " + inputFileName);
        }

        ArrayList<Accelerometer> dataset = new ArrayList<>();

        if (Settings.getInputHasHeader() && scanner.hasNextLine()) {
            scanner.nextLine(); //skipping header
        }

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

        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int counter = 0;
        int zero[] = {0, 0, 0};
        double meanX, meanY, meanZ, meanA, minX, minY, minZ, minA;
        final int bins = 10;
        int cyclometer;  //indicates the number of the curent step
        int position = 0;  //indicates the position in the cordX,cordY,... arrays

        try (PrintStream writer = generateOutputFile(outputFileName)) {
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
            cyclometer = dataset.get(0).getStep()+Settings.getNumStepsIgnored();

            int i = 0;
            while (dataset.get(i).getStep() < cyclometer) { //skipping first N steps
                ++i;
            }
            
            int lastStep = dataset.get(dataset.size() - 1).getStep() - Settings.getNumStepsIgnored(); //first step that is not accepted anymore
            
            for (; i < dataset.size(); ++i) {
                //using the biggest WINSIZE at declaration
                cordX = new double[WINSIZE + 1];
                cordZ = new double[WINSIZE + 1];
                cordY = new double[WINSIZE + 1];
                Amag = new double[WINSIZE + 1];
                counter=i;
                
                //System.out.println(i+ " < "+ dataset.size()+ " && " +cyclometer +" == " +dataset.get(i).getStep());
                while (i < dataset.size() && cyclometer == dataset.get(i).getStep()) { //while it is in the same step
                    cordX[position] = dataset.get(i).getX();
                    cordY[position] = dataset.get(i).getY();
                    cordZ[position] = dataset.get(i).getZ();
                    Amag[position] = Math.sqrt(cordX[position] * cordX[position] + cordY[position] * cordY[position] + cordZ[position] * cordZ[position]);

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
                    counter++;
                    //System.out.println(counter +" < "+ dataset.size() + " && " + cyclometer + " < " + dataset.get(counter).getStep()+ " && " + cyclometer + " < " + lastStep);
                    //extracting features from vectors if the step has ended
                    if (counter < dataset.size() && (cyclometer < dataset.get(counter).getStep() && cyclometer < lastStep)) { //CHECK TODO
                        ///not outOfBounds                          end of step                     not last step 
                        ///                 because we ignore the last step
                        //-------FEATURES
                        //min
                        //System.out.println(counter + " " + cyclometer);
                        if (cordX.length <= 0) {
                            throw new FeatureExtractorException(TAG + "negative array length");
                        }
                        minX = min(cordX, position);
                        minY = min(cordY, position);
                        minZ = min(cordZ, position);
                        minA = min(Amag, position);
                        dataString.append(minX);
                        dataString.append(",");
                        dataString.append(minY);
                        dataString.append(",");
                        dataString.append(minZ);
                        dataString.append(",");
                        dataString.append(minA);
                        dataString.append(",");

                        meanX = mean(cordX, position);
                        meanY = mean(cordY, position);
                        meanZ = mean(cordZ, position);
                        meanA = mean(Amag, position);
                        if (Double.isNaN(meanX)) {
                            throw new FeatureExtractorException(TAG + "NANerror " + position + "," + dataset.get(i).getStep());
                        }
                        dataString.append(meanX);
                        dataString.append(",");
                        dataString.append(meanY);
                        dataString.append(",");
                        dataString.append(meanZ);
                        dataString.append(",");
                        dataString.append(meanA);
                        dataString.append(",");

                        dataString.append(stddev(cordX, position, meanX));
                        dataString.append(",");
                        dataString.append(stddev(cordY, position, meanY));
                        dataString.append(",");
                        dataString.append(stddev(cordZ, position, meanZ));
                        dataString.append(",");
                        dataString.append(stddev(Amag, position, meanA));
                        dataString.append(",");

                        dataString.append(absdif(cordX, position, meanX));
                        dataString.append(",");
                        dataString.append(absdif(cordY, position, meanY));
                        dataString.append(",");
                        dataString.append(absdif(cordZ, position, meanZ));
                        dataString.append(",");
                        dataString.append(absdif(Amag, position, meanA));
                        dataString.append(",");

                        dataString.append((double) zero[0] / position);
                        dataString.append(",");
                        dataString.append((double) zero[1] / position);
                        dataString.append(",");
                        dataString.append((double) zero[2] / position);
                        dataString.append(",");

                        dataString.append(histoToString(histogram(cordX, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(cordY, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(cordZ, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(Amag, position, 0, 3 * 9.8, bins), bins));
                        dataString.append(",");

                        dataString.append(userId);

                        appendToFile(writer, dataString);
                        dataString.delete(0, dataString.length());

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

    private static void extractFeaturesFromCsvFileToFileUsingFrames(String inputFileName, String outputFileName, String userId) throws FeatureExtractorException {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(inputFileName));
        } catch (FileNotFoundException ex) {
            throw new FeatureExtractorException(TAG + "Unable to open file " + inputFileName);
        }

        if (Settings.getInputHasHeader() && scanner.hasNextLine()) {
            scanner.nextLine();
        }

        ArrayList<Accelerometer> dataset = new ArrayList<>();
        while (scanner.hasNextLine()) {  //lines starting the first index in cycles.txt

            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }
            String items[] = line.split(",");
            if (items.length != 5) {
                throw new FeatureExtractorException(TAG + "Corrupted input file error");
            }
            dataset.add(new Accelerometer(Long.parseLong(items[0]),
                    Double.parseDouble(items[1]),
                    Double.parseDouble(items[2]),
                    Double.parseDouble(items[3]),
                    Integer.parseInt(items[4])));
        }

        double[] cordX = {};
        double[] cordZ = {};
        double[] cordY = {};
        double[] Amag = {};
        int zero[] = {0, 0, 0};
        double meanX, meanY, meanZ, meanA, minX, minY, minZ, minA;
        final int bins = 10;
        int position = 0;  //indicates the position in the cordX,cordY,... arrays

        try (PrintStream writer = generateOutputFile(outputFileName)) {
            if (Settings.getOutputHasHeader()) {//adding header to output
                if (Settings.getOutputFileType() == Settings.FileType.CSV) {
                    writer.print(generateCsvHeader());
                }
                if (Settings.getOutputFileType() == Settings.FileType.ARFF) {
                    writer.print(generateArffHeader(userId));
                }
            }

            WINSIZE = Settings.getFrameSize();

            int skippedDataPointsCount = Settings.getNumFramesIgnored()  * Settings.getFrameSize();
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
                    //also skip last step
                    cordX[position] = dataset.get(i).getX();
                    cordY[position] = dataset.get(i).getY();
                    cordZ[position] = dataset.get(i).getZ();
                    Amag[position] = Math.sqrt(cordX[position] * cordX[position] + cordY[position] * cordY[position] + cordZ[position] * cordZ[position]);

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
                        minX = min(cordX, position);
                        minY = min(cordY, position);
                        minZ = min(cordZ, position);
                        minA = min(Amag, position);
                        dataString.append(minX);
                        dataString.append(",");
                        dataString.append(minY);
                        dataString.append(",");
                        dataString.append(minZ);
                        dataString.append(",");
                        dataString.append(minA);
                        dataString.append(",");

                        meanX = mean(cordX, position);
                        meanY = mean(cordY, position);
                        meanZ = mean(cordZ, position);
                        meanA = mean(Amag, position);
                        if (Double.isNaN(meanX)) {
                            throw new FeatureExtractorException(TAG + "NANerror " + position + "," + dataset.get(i).getStep());
                        }
                        dataString.append(meanX);
                        dataString.append(",");
                        dataString.append(meanY);
                        dataString.append(",");
                        dataString.append(meanZ);
                        dataString.append(",");
                        dataString.append(meanA);
                        dataString.append(",");

                        dataString.append(stddev(cordX, position, meanX));
                        dataString.append(",");
                        dataString.append(stddev(cordY, position, meanY));
                        dataString.append(",");
                        dataString.append(stddev(cordZ, position, meanZ));
                        dataString.append(",");
                        dataString.append(stddev(Amag, position, meanA));
                        dataString.append(",");

                        dataString.append(absdif(cordX, position, meanX));
                        dataString.append(",");
                        dataString.append(absdif(cordY, position, meanY));
                        dataString.append(",");
                        dataString.append(absdif(cordZ, position, meanZ));
                        dataString.append(",");
                        dataString.append(absdif(Amag, position, meanA));
                        dataString.append(",");

                        dataString.append((double) zero[0] / position);
                        dataString.append(",");
                        dataString.append((double) zero[1] / position);
                        dataString.append(",");
                        dataString.append((double) zero[2] / position);
                        dataString.append(",");

                        dataString.append(histoToString(histogram(cordX, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(cordY, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(cordZ, position, -1.5 * 9.8, 1.5 * 9.8, bins), bins));
                        dataString.append(",");
                        dataString.append(histoToString(histogram(Amag, position, 0, 3 * 9.8, bins), bins));
                        dataString.append(",");

                        dataString.append(userId);

                        appendToFile(writer, dataString);
                        dataString.delete(0, dataString.length());

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

        header.append("@attribute userID {" + userId + "}\n\n");

        header.append("@data\n");

        return header.toString();
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

}

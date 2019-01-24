/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FeatureExtractorLibrary;

/**
 * Accelerometer is the class that contains the raw data collected from the
 * sensor and is used to pass ArrayLists to the FeatureExtractor.
 *
 * @author Krisztian Nemeth
 * @version 1.0
 * @since 23 ‎July, ‎2018
 */
public class Accelerometer {

    private long timestamp;
    private double x;
    private double y;
    private double z;
    private int step;

    public Accelerometer(long time, double xx, double yy, double zz, int step) {
        this.timestamp = time;
        this.x = xx;
        this.y = yy;
        this.z = zz;
        this.step = step;
    }

    public int getStep() {
        return step;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public long getTimeStamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return timestamp + ", " + x + ", " + y + ", " + z + ", " + step;
    }
}

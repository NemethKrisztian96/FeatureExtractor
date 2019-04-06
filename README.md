# FeatureExtractor
Library that handles feature extraction for accelerometer based gait recognition. 
The various features extracted can be used for creating a model and classifying the recorded data. 

## Installation

Use Java to compile the .java files from the [`src/FeatureExtractorLibrary/`](https://github.com/NemethKrisztian96/FeatureExtractor/blob/master/src/FeatureExtractorLibrary) folder.

```java
javac *.java
```

You can run the program if you modify the main function from `FeatureExtractorLibraryMainTest` class according to your needs or of you include the library in your project as a *.jar* dependency. For the latter you can use the `FeatureExtractorLibrary.jar` file present in the root of the repo.

## Javadoc

The javadoc present in the docs/ folder can also be accessed at https://nemethkrisztian96.github.io/FeatureExtractor/.

## Usage

The library's main functions can be used by calling the public methods from "FeatureExtractor" class. 
You can find more detailed description about the usage of different component classes and methods in the previously mentioned Javadoc you can access at https://nemethkrisztian96.github.io/FeatureExtractor/.

A usage example with minimal settings would be:
```java
Settings.usingFrames(128); 
//or Settings.usingCycles(); 
try {
    FeatureExtractor.extractFeaturesFromCsvFileToFile("data/rawdataFile", "outputFile");
} catch (FeatureExtractorException ex) {
    Logger.getLogger(FeatureExtractorLibraryMainTest.class.getName()).log(Level.SEVERE, null, ex);
}
```

A more detailed usage example can be found in the [`FeatureExtractorLibraryMainTest.java`](https://github.com/NemethKrisztian96/FeatureExtractor/blob/master/src/FeatureExtractorLibrary/FeatureExtractorLibraryMainTest.java) class. There, in the `main` method are provided various settings based on which feature extraction is executed using the input data file from the `data/` folder.

## Support
You can open an issue in the Issues section on Github, or contact the project admin at n.krisztian96@gmail.com.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Authors and acknowledgment

This project has been made by Nemeth Krisztian-Miklos, under the careful supervision of prof. dr. Antal Margit from Sapientia Hungarian University of Transylvania.


## License
[MIT](https://choosealicense.com/licenses/mit/)

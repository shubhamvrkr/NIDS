package com.nids.main;

import java.io.File;
import java.io.IOException;

import com.nids.driver.NidsDriver;
import com.nids.interfaces.PreprocessInterface;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;



public class Main {


    private	static String FILE_PATH = "../NIDS_Java/dataset/Trainingset.csv";
    
    private	static String FILE_PATH_NEW = "../NIDS_Java/dataset/Trainingset_new.csv";
    
    private static String PREPROCESSING_CLASS="com.nids.preprocess.DefaultPreprocess";
	
	public static void main(String[] args) {
		
		try {
				//get the initial content of the data from the train data set
			  	CSVLoader loader = new CSVLoader();
			    loader.setSource(new File(FILE_PATH));
			    Instances data = loader.getDataSet();
			    
			    System.out.println("Number of instances before preprocessing: "+data.numInstances());
			    
			    
			    //create an instance of the default pre-process class dynamically using driver
			    //driver is a class that dynamically creates a new instance of pre-process
			    //change the class path for creating instanse of your own pre-process class
			    PreprocessInterface preprocessObj = NidsDriver.getPreprocessInstance(PREPROCESSING_CLASS);
			   
			    //get the processed data by the pre-processing class
			    //by default uses DefaultPreprocess.Java
			    data =  preprocessObj.preProcess(data);
			    
			    System.out.println("Number of instances after preprocessing: "+data.numInstances());
			    
			    //save the data to a new csv file
			    //just for testing purpose to check if the data is pre processed corectly
				CSVSaver saver = new CSVSaver();
			    saver.setInstances(data);
			    saver.setFile(new File(FILE_PATH_NEW));
			    saver.writeBatch();
			    
			    
			    // save ARFF
			  /*  ArffSaver saver = new ArffSaver();
			    saver.setInstances(data);//set the dataset we want to convert
			    //and save as ARFF
			    saver.setFile(new File("C:\\Users\\Shubham\\eclipse-workspace\\NIDS_Java\\dataset\\Trainingset.arff"));
			    saver.writeBatch();
			    ASEvaluation eval = new GainRatioAttributeEval();
			    Create a Weka's AS Search algorithm 
			    ASSearch search = new Ranker();
			    Wrap WEKAs' Algorithms in bridge 
			    
			    AttributeSelection wekaattrsel = new AttributeSelection();
			    wekaattrsel.setEvaluator(eval);
			    wekaattrsel.setSearch(search);
			    
			    for (int i = 0; i < wekaattrsel.noAttributes(); i++) 
			        System.out.println("Attribute  " +  i +  "  Ranks  " + wekaattrsel.rank(i) + " and Scores " + wekaattrsel.score(i) );
				/*for (int i=0;i<newData.numAttributes();i++){
					
					System.out.println(newData.attribute(i).name()+" ");
				}*/
				/*CSVSaver saver = new CSVSaver();
			    saver.setInstances(newData);//set the dataset we want to convert
			    //and save as CSV
			    saver.setFile(new File("C:\\\\Users\\\\Shubham\\\\eclipse-workspace\\\\NIDS_Java\\\\dataset\\\\Trainingset1.csv"));
			    saver.writeBatch();*/
			    
		}catch(IOException e) {
			
			e.printStackTrace();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

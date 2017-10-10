package com.nids.main;

import java.io.File;
import java.io.IOException;
import com.nids.driver.NidsDriver;
import com.nids.interfaces.FeatureSelectionInterface;
import com.nids.interfaces.PreprocessInterface;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.EuclideanDistance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;



public class Main {


    private	static String FILE_PATH = "../NIDS_Java/dataset/Trainingset.csv";
    
    private	static String TEST_FILE_PATH = "../NIDS_Java/dataset/Testset.csv";
    
    private	static String FILE_PATH_NEW = "../NIDS_Java/dataset/Trainingset_new.csv";
    
    private static String PREPROCESSING_CLASS="com.nids.preprocess.DefaultPreprocess";
    
    private static String FEATURESELECTION_CLASS="";
    
    private static String CENTROIDSELECTION_CLASS="com.nids.centroidselection.DefaultCentroidSelection";
    
    private static String[] attrNamesTobeSelected= {
    		
    		//"duration",	
    		//"protocol_type",
    		//"service",
    		//"flag"
    };
    
    private static int K=22;
    
    private static int[] attrIndices;
	
	public static void main(String[] args) {
		
		try {
				//get the initial content of the data from the train data set
			  	CSVLoader loader = new CSVLoader();
			    loader.setSource(new File(FILE_PATH));
			    Instances data = loader.getDataSet();
			    
			    System.out.println("Number of instances before preprocessing: "+data.numInstances());
			    
			    
			    //create an instance of the default pre-process class dynamically using driver
			    //driver is a class that dynamically creates a new instance of pre-process
			    //change the class path for creating instance of your own pre-process class
			    PreprocessInterface preprocessObj = NidsDriver.getPreprocessInstance(PREPROCESSING_CLASS);
			   
			    //get the processed data by the pre-processing class
			    //by default uses DefaultPreprocess.Java
			    data =  preprocessObj.preProcess(data);
			    
			    System.out.println("Number of instances after preprocessing: "+data.numInstances());
			    
			    //if feature selection class is specified , do attributes/feature selection using the specified attribute selection cass
			    //else check if user has given the attributes to consider 
			    //else consider all the attributes for the kmeans algorithm
			    if(FEATURESELECTION_CLASS.trim().length()>0) {
				   
			    	//create an instance of feature selection (i.e. default = Feature Selection with Greedy )
				    //override Feature Selection Interface to define your own feature selection algorithm
				    //Do selection method has to override that contains the logic for feature selection
				    FeatureSelectionInterface featureSelectionObj =  NidsDriver.getFeatureSelectionInterface(FEATURESELECTION_CLASS);
				    //get the index of the best features/attributes
				    attrIndices  = featureSelectionObj.doSelection(data);
				    
				   
				    //remove attributes other than attributes at attrIndices from the data set
				    data = removeAttributesAtIndices(attrIndices,data);
				    
				    
			    }else if(attrNamesTobeSelected.length>0) {
			    	
			    	//remove attributes specified manually from data set
			    	data = removeAttributesByNames(attrNamesTobeSelected,data);
			    	
			    }
			    		
			    //check is k is set manually or not,if not generate an optimized value of k using an algorithm
			    //YET TO BE IMPLEMENTED
			    if(K<=0) {
			    	
			    	
			    }
			    
			   // CentroidSelectionInterface centroidObj = NidsDriver.getCentroidSelectionInterface(CENTROIDSELECTION_CLASS);
			  //  Instances initialcentroid = centroidObj.selectInitialCentroids(K, data);
			    
			    SimpleKMeans kmeans = new SimpleKMeans();
			    kmeans.setSeed(310);
			    kmeans.setDistanceFunction(new EuclideanDistance());
			    kmeans.setPreserveInstancesOrder(true);
			    kmeans.setNumClusters(K);
			    kmeans.setDontReplaceMissingValues(true);
			    kmeans.buildClusterer(data);
			
			   
			    ClusterEvaluation clsEval = new ClusterEvaluation();
			    loader.setSource(new File(TEST_FILE_PATH));
			    Instances testData = loader.getDataSet();
			
			    testData= preprocessObj.preProcess(testData);
			    
			    if(FEATURESELECTION_CLASS.trim().length()>0) {
					   
			    	//create an instance of feature selection (i.e. default = Feature Selection with Greedy )
				    //override Feature Selection Interface to define your own feature selection algorithm
				    //Do selection method has to override that contains the logic for feature selection
				    FeatureSelectionInterface featureSelectionObj =  NidsDriver.getFeatureSelectionInterface(FEATURESELECTION_CLASS);
				    //get the index of the best features/attributes
				    attrIndices  = featureSelectionObj.doSelection(testData);
				    
				   
				    //remove attributes other than attributes at attrIndices from the data set
				    testData = removeAttributesAtIndices(attrIndices,testData);
				    
				    
			    }else if(attrNamesTobeSelected.length>0) {
			    	
			    	//remove attributes specified manually from data set
			    	testData = removeAttributesByNames(attrNamesTobeSelected,testData);
			    	
			    }
			    //System.out.println("Testdataset: "+testData.numInstances());
				clsEval.setClusterer(kmeans);
				clsEval.evaluateClusterer(testData); //this should be a test dataset!
				
				System.out.println(clsEval.clusterResultsToString());

				/*saver = new CSVSaver();
			    saver.setInstances(data);
			    saver.setFile(new File(FILE_PATH_NEW));
			    saver.writeBatch();*/
  		    
		}catch(IOException e) {
			
			e.printStackTrace();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public static Instances removeAttributesByNames(String[] names , Instances data) {
		
			int[] indices = new int[names.length];
			int i=0;
			for(String name:names) {
				Attribute attrtoSelect = data.attribute(name);
				indices[i]=attrtoSelect.index();
				i++;
			}
			
			data = removeAttributesAtIndices(indices,data);
			return data;
		
	}
	public static Instances removeAttributesAtIndices(int[] arr , Instances data) {
		
		try {
			
			Remove remove = new Remove();
			remove.setInvertSelection(true);
			remove.setAttributeIndicesArray(arr);
			remove.setInputFormat(data);
			data = Filter.useFilter(data, remove);
			return data;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public static boolean anyMatch(int[] arr , int x) {

		for(int a:arr) {
			if(a==x) {
				return true;
			}
		}
		return false;
		
	}
}

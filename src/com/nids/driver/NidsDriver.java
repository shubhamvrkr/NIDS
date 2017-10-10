package com.nids.driver;

import com.nids.interfaces.CentroidSelectionInterface;
import com.nids.interfaces.FeatureSelectionInterface;
import com.nids.interfaces.PreprocessInterface;

public class NidsDriver {

	
	public static PreprocessInterface getPreprocessInstance(String classPath) {
		

		 ClassLoader classLoader = NidsDriver.class.getClassLoader();
		 try {
		        Class preprocessClass = classLoader.loadClass(classPath);
		       
		        return (PreprocessInterface)preprocessClass.newInstance();
		        
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		        
		    } catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return null;
	}
	
	public static FeatureSelectionInterface getFeatureSelectionInterface(String classPath) {
		 
		
		ClassLoader classLoader = NidsDriver.class.getClassLoader();
		 try {
		        Class featureSelectionClass = classLoader.loadClass(classPath);
		       
		        return (FeatureSelectionInterface)featureSelectionClass.newInstance();
		        
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		        
		    } catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return null;
	}

	public static CentroidSelectionInterface getCentroidSelectionInterface(String classPath) {
		 
		
		ClassLoader classLoader = NidsDriver.class.getClassLoader();
		 try {
		        Class centroidSelectionInterface = classLoader.loadClass(classPath);
		       
		        return (CentroidSelectionInterface)centroidSelectionInterface.newInstance();
		        
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		        
		    } catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return null;
	}
}

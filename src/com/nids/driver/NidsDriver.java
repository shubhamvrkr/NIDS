package com.nids.driver;

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
	
}

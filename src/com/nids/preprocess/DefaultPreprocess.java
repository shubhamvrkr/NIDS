package com.nids.preprocess;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;

import com.nids.interfaces.PreprocessInterface;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.RenameNominalValues;
import weka.filters.unsupervised.instance.RemoveDuplicates;
/*
 * This is the default class used for pre-processing.
 * This class will remove the duplicates entry, remove the instances containing null values/ noise 
 * and will set a default mimimum values for class attribute (e.g. normal = 0.001 or tcp = 0.001 ...)
 * To create your own preprocessing operations kindly create a new class and implement the PreprocessInterface
 */
public class DefaultPreprocess implements PreprocessInterface {

	//does the preprocessing of data
	@Override
	public Instances preProcess(Instances data) {
		// TODO Auto-generated method stub
		Instance processed;
		new ArrayList<Instance>();
		
		try {
			
			// removes the duplicate rows from the data instances
			RemoveDuplicates duplicates= new RemoveDuplicates();
			duplicates.setInputFormat(data);
			for (int i = 0; i < data.numInstances(); i++) {
				
				duplicates.input(data.instance(i));
			}
			duplicates.batchFinished();
			Instances newData = duplicates.getOutputFormat();
			while ((processed = duplicates.output()) != null) {
			    newData.add(processed);
			}
			
			//System.out.println("Number of instances after removing duplications: "+newData.numInstances());
			
			//newData contains no redundant data
			//filter newData to remove attributes with null values
			Enumeration<?> enumAtts = newData.enumerateAttributes();
			 while (enumAtts.hasMoreElements()) {
				 Attribute attribute = (Attribute) enumAtts.nextElement();
				 newData.deleteWithMissing(attribute);
			 
			 }
			 
			 //System.out.println("Number of instances after removing null: "+newData.numInstances());
			 
			 //newData contains no data with missing values
			 //convert nominal attributes to numeric by assigning it very small value (0.001,0.002...)
			 //since small number wont affect the computations and k means only works effectively on numeric data 
			newData = convertNominalToNumeric(newData);
			 
			return newData;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public Instances convertNominalToNumeric (Instances data) {
		
		HashMap<String, Double> map = new HashMap<String, Double>();
		Enumeration<?> enumAtts = data.enumerateAttributes();
		String[] options = new String[5];
		options[0] = "-R";
		options[1] = "";
		options[2] = "-N";
		options[3] = "";
		options[4] = "-I";
		
		//identify nominal attributes
		// get uniques values for each attribute
		//stores key=attributename and value = 0.001 (replacement value)
		//auto increment by 0.001 for next value of same attribute
		//for new attribute reset value = 0.001 for firts value
		 while (enumAtts.hasMoreElements()) {
			 
			 Attribute attribute = (Attribute) enumAtts.nextElement();
			 if(attribute.isNominal()) {
				 
				 options[1] += attribute.name()+",";
				 Enumeration<?> attrsVal =  attribute.enumerateValues();
				 double def_val= 0.001;
				 
				 while (attrsVal.hasMoreElements()) {
					 
					 String name_attr = String.valueOf(attrsVal.nextElement());
					 map.put(name_attr, def_val);
					 def_val+=0.001;
				 }
			 }
		 }

		 Set<String> key = map.keySet();
		 for(String s :key) {
			 options[3]+=s+":"+String.valueOf(map.get(s))+",";
		 }
		 options[1] = options[1].substring(0, options[1].length()-1);
		 options[3] =  options[3].substring(0, options[3].length()-1);
		
		 //replace value for the attributes value in data 
		 try {
			 RenameNominalValues renameNominalValues = new RenameNominalValues();
			 renameNominalValues.setOptions(options);
			 renameNominalValues.setInputFormat(data);
			 data = Filter.useFilter(data, renameNominalValues);
			 return data;
			 
		 }catch(Exception e) {
			 
			 e.printStackTrace();
		 }
		 
		return null;
	}
	

}

package com.nids.featureselection;

import com.nids.interfaces.FeatureSelectionInterface;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Utils;

public class WrapperSubsetWithBestFirst implements FeatureSelectionInterface {

	@Override
	public int[] doSelection(Instances data) {
		// TODO Auto-generated method stub
		int attrIndices[];
		
		try {
			
		
			AttributeSelection Filter = new AttributeSelection ();
			
			WrapperSubsetEval eval = new WrapperSubsetEval(); 
	
			BestFirst search = new BestFirst(); 
			String[] options = new String[2];
			options[2] = "-D";
			options[3] = "0";
			
			search.setOptions(options);
			
			J48 base = new J48();
			
			eval.setClassifier(base); 
	
			Filter.setEvaluator(eval); 
			Filter.setSearch(search); 
			Filter.SelectAttributes(data); 
	
			attrIndices =Filter.selectedAttributes(); 
			return attrIndices;
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}

}

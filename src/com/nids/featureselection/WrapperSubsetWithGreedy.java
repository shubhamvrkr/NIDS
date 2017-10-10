package com.nids.featureselection;

import java.util.Arrays;

import com.nids.interfaces.FeatureSelectionInterface;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.GreedyStepwise;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;

public class WrapperSubsetWithGreedy implements FeatureSelectionInterface {

	@Override
	public int[] doSelection(Instances data) {
		// TODO Auto-generated method stub
	
		int attrIndices[];
		
		try {
			
			
			AttributeSelection Filter = new AttributeSelection();
			
			//User wrapper for feature selection
			//uses learning for feature selection
			WrapperSubsetEval eval = new WrapperSubsetEval(); 
			
			//greedy hill climbing algorithm;
			GreedyStepwise search = new GreedyStepwise(); 
			search.setSearchBackwards(true); 
			search.setGenerateRanking(true);
			//get the number of CPU core of host machine
			search.setNumExecutionSlots(2);
			
			J48 base = new J48();
			
			eval.setClassifier(base); 
	
			Filter.setEvaluator(eval); 
			Filter.setSearch(search); 
			Filter.SelectAttributes(data); 
	
			attrIndices =Filter.selectedAttributes();
			attrIndices = Arrays.copyOfRange(attrIndices, 0, attrIndices.length-10);

			return attrIndices;
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}

}

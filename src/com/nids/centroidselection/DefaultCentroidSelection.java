package com.nids.centroidselection;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.nids.interfaces.CentroidSelectionInterface;

import weka.core.Instance;
import weka.core.Instances;

public class DefaultCentroidSelection implements CentroidSelectionInterface  {

	
	@SuppressWarnings("null")
	@Override
	public Instances selectInitialCentroids(int k, Instances data) {
		Instances centroids = new Instances(data);
		centroids.delete();
		List<Integer> list = new ArrayList<Integer>();
		Random r = new Random();
		
		for(int i=0;i<k;i++) {
			
			int temp = r.nextInt(data.numInstances()-0) + 0;
			while(list.contains(temp)) {
				temp = r.nextInt(data.numInstances()-0) + 0;
			}
			Instance inst = data.get(temp);
			centroids.add(inst);
		}
		//System.out.println("Value of k: "+k);
		//System.out.println("Value of centroid: "+centroids.numInstances());
		// TODO Auto-generated method stub
		return centroids;
	}

}

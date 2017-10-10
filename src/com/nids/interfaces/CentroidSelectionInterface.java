package com.nids.interfaces;

import weka.core.Instances;

public interface CentroidSelectionInterface {

	public Instances selectInitialCentroids(int k,Instances data);
}

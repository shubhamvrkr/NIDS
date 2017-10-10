package com.nids.interfaces;

import weka.core.Instances;

public interface FeatureSelectionInterface {

	public int[] doSelection(Instances data);
}

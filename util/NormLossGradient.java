package org.apache.flink.examples.java.ml.util;

import org.apache.flink.api.java.tuple.Tuple2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Norms the (loss, gradient) tuple by dividing it by the total number of Elements in the dataset.
 */
public class NormLossGradient implements org.apache.flink.api.common.functions.MapFunction<Tuple2<Double,double[]>,org.apache.flink.api.java.tuple.Tuple2<Double,double[]>> {
	private long numberofelements;

	public NormLossGradient(long n){
		this.numberofelements=n;
	}
	@Override
	public Tuple2<Double, double[]> map(Tuple2<Double, double[]> lossgradient) throws Exception {

		double loss = lossgradient.f0 / this.numberofelements;

		int length = lossgradient.f1.length;
		double gradient[] = new double[length];

		for(int i=0; i < length; i++){
			gradient[i] = lossgradient.f1[i] / this.numberofelements;
		}

		return new Tuple2<>(loss,gradient);
	}
}

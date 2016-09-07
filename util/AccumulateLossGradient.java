package org.apache.flink.examples.java.ml.util;

import org.apache.flink.api.java.tuple.Tuple2;

/**
 * ReduceFunction that accumulates the pointwise losses and gradients
 * simply by adding them up
 */
public class AccumulateLossGradient implements org.apache.flink.api.common.functions.ReduceFunction<Tuple2<Double,double[]>> {


	@Override
	public Tuple2<Double, double[]> reduce(Tuple2<Double, double[]> value1, Tuple2<Double, double[]> value2) throws Exception {

		double loss1 = value1.f0;
		double[] gradient1 = value1.f1;

		double loss2 = value2.f0;
		double[] gradient2 = value2.f1;

		double lossSum = loss1 + loss2;
		double[] gradientSum = addVectors(gradient1,gradient2);

		return new Tuple2<>(lossSum,gradientSum);

	}

	private double[] addVectors(double[] value1, double[] value2) throws IllegalArgumentException{
		if(value1.length != value2.length) throw new IllegalArgumentException("gradients have different length");
		double sum[] = new double[value1.length];
		for(int i=0;i<value1.length;i++){
			sum[i]=value1[i]+value2[i];
		}
		return sum;
	}
}

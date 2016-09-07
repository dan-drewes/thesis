/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.examples.java.ml;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.examples.java.ml.util.State;

import java.util.List;


/**
 * RichMapFunction that returns loss and gradient for a data point.
 * loss = 0.5 *(prediction - label) * (prediction - label)
 * prediction is computed linear with weights*features
 */
public final class DiffFunction extends RichMapFunction<double[], Tuple2<Double,double[]>> {
	private double[] weights;

	@Override
	public void open(Configuration parameters) throws Exception {
		List<State> state = getRuntimeContext().getBroadcastVariable("state");
		this.weights = (state.get(0)).getX();
	}


	@Override
	public Tuple2<Double, double[]> map(double[] x) throws Exception {

		double label = x[0];
		double prediction = 0;

		for(int i=0;i<x.length-1;i++){
			prediction += x[i+1]*weights[i];
		}
		prediction+=weights[weights.length-1];

		double loss = 0.5 * (prediction - label) * (prediction - label);

		double[] gradient = new double[weights.length];
		for(int j=0;j<weights.length-1;j++){
			gradient[j] = x[j+1]*(prediction-label);
		}
		gradient[weights.length-1]=1*(prediction-label);

		return new Tuple2<>(loss,gradient);

	}


}

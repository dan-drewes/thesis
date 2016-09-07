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

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.operators.IterativeDataSet;
import org.apache.flink.api.java.tuple.Tuple2;

import org.apache.flink.examples.java.ml.util.*;


/**
 * Implements a linear regression using the L-BFGS algorithm and Flink Iterations.
 * In particular, the java translation by Dodier of the original Fortran Code by Nocedal is used.
 * See the documentation of class LBFGS for more information.
 */
public class IterationsLBFGS {

	public static void main (String args[]) throws Exception{

		String csvFile = args[0];
		int datasize = Integer.parseInt(args[1]);
		final int nfeatures = Integer.parseInt(args[2]);//without label
		int niter = Integer.parseInt(args[3]);
		final String csvSplitBy = " ";

		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		DataSet<String> input = env.readTextFile(csvFile);

		//parsing the input
		DataSet<double[]> data = input.map(new MapFunction<String, double[]>() {
			@Override
			public double[] map(String line) throws Exception {
				line = line.trim().replaceAll(" +", " "); //replace all multiple, leading or trailing whitespaces

				String[] datapoint = line.split(csvSplitBy);

				double[] tmp = new double[nfeatures+1];
				for(int i=0;i<nfeatures+1;i++) { //preprocessing
					if (i == nfeatures) { //insert label at index [0]
						tmp[0] = Double.parseDouble(datapoint[i]);
					} else {
						tmp[i + 1] = Double.parseDouble(datapoint[i]);
					}
				}
				return tmp;
			}
		});

		double initweights[] = new double[nfeatures+1];
		for(int k=0;k<nfeatures+1;k++){
			initweights[k]=Math.random();
		}

		double result[] = minimize(5,datasize,niter,initweights,data,new DiffFunction(),new AccumulateLossGradient());

		System.out.print("Weights: [");
		for(int i=0; i < result.length-1;i++){
			System.out.print(""+result[i]+", ");
		}
		System.out.println(""+result[result.length-1]+"]");

	}

	/**
	 * Finds the weights that minimize the given difffunction.
	 * @param m memory used for the L-BFGS
	 * @param datasize the number of points in the DataSet data
	 * @param niter maximum number of Iterations
	 * @param initweights initial weights to compute first loss and gradient
	 * @param data the data used for computing loss and gradient
	 * @param difffunction RichMapFunction that returns loss and gradient for a data point
	 * @param accumulate ReduceFunction that accumulates the pointwise losses and gradients
	 * @return the minimizing weights
	 * @throws Exception forwards all occuring Exceptions
	 */
	public static double[] minimize(int m, int datasize, int niter, double[] initweights, DataSet<double[]> data, RichMapFunction<double[], Tuple2<Double,double[]>> difffunction, ReduceFunction<Tuple2<Double,double[]>> accumulate) throws Exception{

		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		State state = initialState(m, initweights,0,new double[initweights.length]);
		DataSet<State> statedataset = env.fromElements(state);

		//start of iteration section
		IterativeDataSet<State> loop= statedataset.iterate(niter);;

		DataSet<State> statewithnewlossgradient = data.map(difffunction).withBroadcastSet(loop, "state")
                .reduce(accumulate)
                .map(new NormLossGradient(datasize))
                .map(new SetLossGradient()).withBroadcastSet(loop,"state")
                .map(new LBFGS());

		//check for convergence
		DataSet<State> converged = statewithnewlossgradient.filter(
			new FilterFunction<State>() {
				@Override
				public boolean filter(State value) throws Exception {
					if(value.getIflag()[0] == 0){
						return false;
					}
					return true;
				}
			}
		);

		DataSet<State> finalstate = loop.closeWith(statewithnewlossgradient,converged);
		//end of iteration section

		return finalstate.collect().get(0).getX();
	}

	/**
	 * Creates an initial State from the given parameters
	 * @param m
	 * @param weights
	 * @param f
	 * @param g
	 * @return a new State
	 */
	private static State initialState(int m, double[] weights, double f, double[] g) {

		int ndim = weights.length;
		double diag [ ] , w [ ];

		diag = new double [ ndim ];

		double eps, xtol, gtol, t1, t2, stpmin, stpmax;
		int iprint [ ] , icall, n, mp, lp, j;
		int iflag[] = new int[1];
		iprint = new int [ 2 ];
		boolean diagco;

		n=weights.length;
		iprint [ 1 -1] = -1;
		iprint [ 2 -1] = 3;
		diagco= false;
		eps= 1.0e-5;
		xtol= 1.0e-16;
		icall=0;
		iflag[0]=0;

		return new State(n, m, weights, f, g, diagco , diag , iprint , eps , xtol , iflag);
	}


}


package org.apache.flink.examples.java.ml.util;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;

import java.util.List;


/**
 * Simply merges new loss and gradient with the other variables in the given State to a new State.
 */
public class SetLossGradient extends RichMapFunction<Tuple2<Double,double[]>,State> implements MapFunction<Tuple2<Double,double[]>,State> {

	State oldstate;

	@Override
	public void open(Configuration parameters) throws Exception {
		List<State> oldstateaslist = getRuntimeContext().getBroadcastVariable("state");
		this.oldstate = oldstateaslist.get(0);
	}

	@Override
	public State map(Tuple2<Double,double[]> lossgradient) throws Exception {

		double loss = lossgradient.f0;
		double gradient[] = lossgradient.f1;
		return new State(oldstate, loss, gradient);
	}

}

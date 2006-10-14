// ============================================================================
//   Copyright 2006 Daniel W. Dyer
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
// ============================================================================
package org.uncommons.watchmaker.examples.travellingsalesman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.stats.PoissonSequence;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.StandaloneEvolutionEngine;
import org.uncommons.watchmaker.framework.factories.ListPermutationFactory;
import org.uncommons.watchmaker.framework.operators.ListOrderMutation;
import org.uncommons.watchmaker.framework.selection.TruncationSelection;

/**
 * Sample application - an evolutionary solution to the well-known Travelling
 * Salesman problem.
 * @author Daniel Dyer
 */
public class TravellingSalesmanExample
{
    private TravellingSalesmanExample()
    {
        // Prevents instantiation.
    }

    public static void main(String args[])
    {
        Random rng = new MersenneTwisterRNG();
        List<EvolutionaryOperator<? super List<String>>> pipeline = new ArrayList<EvolutionaryOperator<? super List<String>>>(2);
        pipeline.add(new ListOrderMutation(new PoissonSequence(1.5, rng),
                                           new PoissonSequence(1.5, rng)));
        CandidateFactory<List<String>> candidateFactory = new ListPermutationFactory<String>(Europe.getInstance().getCities());
        EvolutionEngine<List<String>> engine = new StandaloneEvolutionEngine<List<String>>(candidateFactory,
                                                                                           pipeline,
                                                                                           new RouteEvaluator(),
                                                                                           new TruncationSelection(0.5),
                                                                                           rng);
        engine.setEliteRatio(0.01d); // Preserve the top 1% of each generation.
        engine.addEvolutionObserver(new EvolutionLogger());
        long startTime = System.currentTimeMillis();
        engine.evolve(300, // 300 individuals in the population.
                      100); // 100 generations.
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Elapsed time: " + elapsedTime + "ms.");
    }


    private static String stringListToString(List<String> list)
    {
        StringBuilder buffer = new StringBuilder();
        for (String s : list)
        {
            buffer.append(s);
            buffer.append(' ');
        }
        return buffer.toString();
    }


    private static class EvolutionLogger implements EvolutionObserver<List<String>>
    {
        public void populationUpdate(PopulationData<? extends List<String>> data)
        {
            System.out.println("Generation " + data.getGenerationNumber() + ": " + data.getBestCandidateFitness() + "km");
            System.out.println("  " + stringListToString(data.getBestCandidate()));
        }
    }
}

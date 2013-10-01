/**
 * org.uncommons.watchmaker.framework.termination
 * Author: sarah.killcoyne
 * Copyright University of Luxembourg and Luxembourg Centre for Systems Biomedicine 2013
 * Open Source License Apache 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */


package org.uncommons.watchmaker.framework.termination;


import org.uncommons.watchmaker.framework.PopulationData;

public class TestPopulationData extends PopulationData
  {
  private int size;
  private double mean;
  private double sd;
  private double bestFit;

  public TestPopulationData(double bestFit, double meanFitness, double fitnessSD, boolean naturalFitness, int popSize, int eliteCount, int generationNumber, long elapsedTime)
    {
    super(null, naturalFitness, eliteCount, generationNumber, elapsedTime);
    this.sd = fitnessSD;
    this.mean = meanFitness;
    this.size = popSize;
    this.bestFit = bestFit;
    }

  @Override
  public double getFitnessStandardDeviation()
    {
    return this.sd;
    }

  @Override
  public int getPopulationSize()
    {
    return this.size;
    }

  @Override
  public double getBestCandidateFitness()
    {
    return this.bestFit;
    }

  @Override
  public double getMeanFitness()
    {
    return this.mean;
    }
  }



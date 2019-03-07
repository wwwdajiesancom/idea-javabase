package com.loujie.www.threads;

/**
 * @name loujie
 * @date 2019/3/7
 */
public class EnergyBall {

    private final double[] allEnergys;

    public EnergyBall(int initLength, double initValue) {
        allEnergys = new double[initLength];

        for (int i = 0; i < initLength; i++) {
            allEnergys[i] = initValue;
        }
    }

    public void cal(int from, int to, double value) {
        if (allEnergys[from] < value) {
            return;
        }

        allEnergys[from] = allEnergys[from] - value;
        allEnergys[to] = allEnergys[to] + value;

        System.out.println("总能量为:" + getTotalEnergy());
    }

    public double getTotalEnergy() {
        double totalEnergy = 0;
        for (int i = 0; i < allEnergys.length; i++) {
            totalEnergy += allEnergys[i];
        }
        return totalEnergy;
    }

}

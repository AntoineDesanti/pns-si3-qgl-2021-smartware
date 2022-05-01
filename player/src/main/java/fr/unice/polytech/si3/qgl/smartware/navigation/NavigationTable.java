package fr.unice.polytech.si3.qgl.smartware.navigation;

public class NavigationTable {

    private double[][] navigationTable;
    private int numberOfSailors;
    private int numberOfOars;

    public NavigationTable(int numberOfSailors, int numberOfOars)
    {
        this.numberOfSailors = numberOfSailors;
        this.numberOfOars = numberOfOars;
        int tableLength = 0; //(int) Math.pow(((numberOfOars)/2.0+1), 2);
        for (int leftSailors = 0; leftSailors <= numberOfOars/2; leftSailors++)
            for (int rightSailors = 0; rightSailors <= Math.min(numberOfSailors-leftSailors, numberOfOars/2); rightSailors++)
                tableLength++;
        navigationTable = new double[tableLength][4];
    }

    public void fill()
    {
        int line = 0;
        for (int leftSailors = 0; leftSailors <= numberOfOars/2; leftSailors++)
            for (int rightSailors = 0; rightSailors <= Math.min(numberOfSailors-leftSailors, numberOfOars/2); rightSailors++)
            {
                navigationTable[line] = new double[]{
                        leftSailors,
                        rightSailors,
                        ((double)(leftSailors+rightSailors))/numberOfSailors,
                        Math.PI/numberOfOars * (rightSailors - leftSailors)
                };
                line++;
            }
    }

    public int getLeftSailors(int line) { return (int) navigationTable[line][0]; }

    public int getRightSailors(int line) { return (int) navigationTable[line][1]; }

    public double getSpeed(int line) { return navigationTable[line][2]; }

    public double getAngle(int line) { return navigationTable[line][3]; }

    public int size()
    {
        return navigationTable.length;
    }

    public void print()
    {
        for (double[] line : navigationTable)
            System.out.println("Left: "+ (int) line[0]+ " | Right: "+ (int) line[1]
                    + " | Speed: "+ String.format("%.2f", line[2])
                    +" | Angle: "+Math.round(Math.toDegrees(line[3])));
    }
}

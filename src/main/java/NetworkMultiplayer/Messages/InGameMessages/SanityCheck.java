package NetworkMultiplayer.Messages.InGameMessages;

import GameBoard.Position;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.IMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class SanityCheck implements IMessage, Serializable {
    private final TreeMap<Robot, Position> botPositions;

    public SanityCheck(TreeMap<Robot, Position> botPositions){
        this.botPositions = botPositions;
    }

    public static void assertEqualSimulation(SanityCheck check1, SanityCheck check2){
        check1.assertEqualSimulation(check2);
        check2.assertEqualSimulation(check1);
    }

    public void assertEqualSimulation(SanityCheck other){
        ArrayList<Robot> robots1 = new ArrayList<>(this.botPositions.keySet());
        ArrayList<Robot> robots2 = new ArrayList<>(other.botPositions.keySet());

        if (robots1.size() != robots2.size())
            throw new UnequalSimulationException("The list of robots don't have the same size;"
                    + " expected " + robots1.size() + ", but got " + robots2.size() + ".");
        for (int i = 0; i < robots1.size(); i++) {
            Robot bot1 = robots1.get(i);
            Robot bot2 = robots2.get(i);
            if ( ! bot1.equals(bot2)) throw new UnequalSimulationException("Expected two equal robots, but got: \n" + bot1 + "\nand:\n" + bot2);
            Position pos1 = this.botPositions.get(bot1);
            Position pos2 = other.botPositions.get(bot2);
            if ( ! pos1.equals(pos2)) throw new UnequalSimulationException("The same robot is in differing locations " +
                    "across simulations: (" + pos1.getX() + ", " + pos1.getY() + ") != (" + pos2.getX() + ", " + pos2.getY() + ").");
        }
    }

    @Override
    @Deprecated
    public boolean equals(Object obj) throws UnequalSimulationException {
        throw new UnsupportedOperationException("Please use assertEqualSimulation() instead");
    }

    public static class UnequalSimulationException extends RuntimeException{
        public UnequalSimulationException(String s){ super(s); }
    }
}

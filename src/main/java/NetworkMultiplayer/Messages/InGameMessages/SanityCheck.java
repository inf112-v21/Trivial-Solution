package NetworkMultiplayer.Messages.InGameMessages;

import GameBoard.Components.IComponent;
import GameBoard.Position;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.IMessage;

import java.util.ArrayList;
import java.util.TreeMap;

public class SanityCheck implements IMessage {
    private final IComponent[][] midgrid;
    private final IComponent[][] forgrid;
    private final TreeMap<Robot, Position> botPositions;

    public SanityCheck(IComponent[][] midgrid, IComponent[][] forgrid, TreeMap<Robot, Position> botPositions){
        this.midgrid = midgrid;
        this.forgrid = forgrid;
        this.botPositions = botPositions;
    }

    @Override
    public boolean equals(Object obj) throws UnequalSimulationException {
        if (! (obj instanceof SanityCheck)) return false;
        SanityCheck o = (SanityCheck) obj;
        if ( this.midgrid.length != o.midgrid.length)
            throw new UnequalSimulationException("The boards do not have the same size");
        for (int y = 0; y < this.midgrid.length; y++) {
            for (int x = 0; x < this.midgrid[y].length; x++) {
                if ( ! (this.midgrid[y][x] == null && o.midgrid[y][x] == null || this.midgrid[y][x].equals(o.midgrid[y][x])))
                    throw new UnequalSimulationException("Found differing components in the middleground-grid in position (" + x + ", " + y + "), "
                    + "expected a " + this.midgrid[y][x] + ", but was " + o.midgrid[y][x] + ".");
                if( ! ( this.forgrid[y][x] == null && o.forgrid[y][x] == null || this.forgrid[y][x].equals(o.forgrid[y][x])))
                    throw new UnequalSimulationException("Found differing components in the forground-grid in position (" + x + ", " + y + "), "
                    + "expected a " + this.forgrid[y][x] + ", but was " + o.forgrid[y][x] + ".");
            }
        };
        ArrayList<Robot> robots1 = new ArrayList<>(this.botPositions.keySet());
        ArrayList<Robot> robots2 = new ArrayList<>(o.botPositions.keySet());

        if (robots1.size() != robots2.size())
            throw new UnequalSimulationException("The list of robots don't have the same size;"
            + " expected " + robots1.size() + ", but got " + robots2.size() + ".");
        for (int i = 0; i < robots1.size(); i++) {
            Robot bot1 = robots1.get(i);
            Robot bot2 = robots2.get(i);
            if ( ! bot1.equals(bot2)) throw new UnequalSimulationException("Expected two equal robots, but got: \n" + bot1 + "\nand:\n" + bot2);
            Position pos1 = this.botPositions.get(bot1);
            Position pos2 = o.botPositions.get(bot2);
            if ( ! pos1.equals(pos2)) throw new UnequalSimulationException("The same robot is in differing locations " +
                    "across simulations: (" + pos1.getX() + ", " + pos1.getY() + ") != (" + pos2.getX() + ", " + pos2.getY() + ").");
        }
        return true;
    }

    public static class UnequalSimulationException extends RuntimeException{
        public UnequalSimulationException(String s){ super(s); }
    }
}

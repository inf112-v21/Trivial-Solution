package Objects;

public class Tile implements IComponent{

    private static final String name = "Tile";
    private static final char symbol = 'T';

    @Override
    public String getName() {
        return name;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}


package Objects;

public class Tile implements IComponent{

    private String name = "Tile";
    private final char SYMBOL = 'T';

    @Override
    public String getName() {
        return name;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}


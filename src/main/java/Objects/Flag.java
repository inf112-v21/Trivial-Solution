package Objects;

public class Flag implements IComponent {

    String name;
    char SYMBOL = 'F';

    Flag(String name){

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}

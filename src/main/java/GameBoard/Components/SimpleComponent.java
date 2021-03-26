package GameBoard.Components;

public abstract class SimpleComponent implements IComponent {

    /**
     * Hver enkelt rute på skjermen har et tilsvarende objekt i backend.
     * Eksempler kan være lasere, samlebånd, vegger eller flagg.
     * De eneste tingene på kartet som ikke har en tilsvarende Component er robotene (siden de fungerer helt forskjellig),
     * bakgrunnstiles (siden de ikke har noen innvirkning på gameplay), og luft/tomme ruter. De er bare null.
     */

    protected String name;
    protected final int id;

    public SimpleComponent(int id){
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getID() { return id; }
}

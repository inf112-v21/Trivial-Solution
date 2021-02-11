package Components;

public abstract class SimpleComponent implements IComponent {

    /**
     * Hver enkelt rute på skjermen har et tilsvarende objekt i backend.
     * Eksempler kan være lasere, samlebånd, vegger eller flagg.
     * De eneste tingene på kartet som ikke har en tilsvarende Component er robotene (siden de fungerer helt forskjellig),
     * og luft/tomme ruter. De er bare null.
     */

    protected String name;
    protected int id;

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

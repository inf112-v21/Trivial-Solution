package GameBoard.Components;

public abstract class SimpleComponent implements IComponent {

    /**
     * Hver enkelt rute på skjermen har et tilsvarende objekt i backend.
     * Eksempler kan være lasere, samlebånd, vegger eller flagg.
     * De eneste tingene på kartet som ikke har en tilsvarende Component er robotene (siden de fungerer helt forskjellig),
     * bakgrunnstiles (siden de ikke har noen innvirkning på gameplay), og luft/tomme ruter. De er bare null.
     */

    protected final int id;

    public SimpleComponent(int id){
        this.id = id;
    }


    @Override
    public int getID() { return id; }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof SimpleComponent)) return false;
        SimpleComponent simp = (SimpleComponent) obj;
        return this.id == simp.id;
    }
}

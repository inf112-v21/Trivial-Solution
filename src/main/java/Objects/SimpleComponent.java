package Objects;

public abstract class SimpleComponent implements IComponent {

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

package Components;

/**
 * Samlebånd, som flytter roboter rundt om de avslutter turen oppå dem.
 * De har en hastighet og en retning.
 * Retningene er representert som 0, 1, 2, 3 og tilsvarer Nord, Øst, Sør og Vest i den rekkefølgen.
 */
public class ConveyorBelt extends SimpleComponent{

    private int speed;
    private int dir;


    public ConveyorBelt(int id, int dir, int speed){
        super(id);
        this.dir = dir;
        this.speed = speed;
        this.name = "ConveyorBelt";
    }
}

package inf112.skeleton.app;

import Objects.ConveyorBelt;
import Objects.Flag;
import Objects.IComponent;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class ComponentFactory {

    public static IComponent spawnComponent(TiledMapTileLayer.Cell cell){
        if(cell == null) return null;
        switch (cell.getTile().getId()){
            case 55:
                return new Flag(55);
            case 63:
                return new Flag(63);
            case 71:
                return new Flag(71);
            case 14:
                return new ConveyorBelt(14, 1, 2);
            case 21:
                return new ConveyorBelt(21, 2, 2);
            case 23:
                return new ConveyorBelt(23, 3, 2);
            default:
                System.err.println("Could not recognize ID no. " + cell.getTile().getId() + ". Please go to ComponentFactory and add that case to the list. Or go complain to Steinar.");
                return null;
        }
    }

}

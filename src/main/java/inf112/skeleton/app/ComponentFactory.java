package inf112.skeleton.app;

import Objects.ConveyorBelt;
import Objects.Flag;
import Objects.IComponent;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class ComponentFactory {

    public static IComponent spawnComponent(TiledMapTileLayer.Cell cell){
        if(cell == null) return null;
        switch (cell.getTile().getId()){
            /*case 55:
                return new Flag("1");
                break;
            case 14:
                return new ConveyorBelt(14, 1, 2);
                break;
            case 23:
                return new ConveyorBelt(23, 2, 3);
                break;*/
            default:
                System.err.println("Could not recognize ID no. " + cell.getTile().getId() + ". Please go to ComponentFactory and add that case to the list.");
                return null;
        }
    }

}

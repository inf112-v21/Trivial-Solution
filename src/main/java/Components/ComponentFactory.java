package Components;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class ComponentFactory {

    public static IComponent spawnComponent(TiledMapTileLayer.Cell cell){
        if(cell == null) return null;
        switch (cell.getTile().getId()){

            //Flagg
            case 55:
                return new Flag(55);
            case 63:
                return new Flag(63);
            case 71:
                return new Flag(71);
            case 79:
                return new Flag(79);

            //Misc.
            case 5:
                return new SteelPlate(5);
            case 6:
                return new Hole(6);
            case 7:
                return new CheckPoint(7);
            case 15:
                return new Wrench(15);
            case 53:
                return new Gear(53, -1);
            case 54:
                return new Gear(54, 1);

            //Alt under dette til neste kommentar er kun diverse samlebånd
            case 13:
                return new ConveyorBelt(13, 0, 2);
            case 14:
                return new ConveyorBelt(14, 1, 2);
            case 17:
                return new ConveyorBelt(17, 2, 2);
            case 18:
                return new ConveyorBelt(18, 3, 2);
            case 19:
                return new ConveyorBelt(19, 1, 2);
            case 20:
                return new ConveyorBelt(20, 2, 2);
            case 21:
                return new ConveyorBelt(21, 2, 2);
            case 23:
                return new ConveyorBelt(23, 3, 2);

            case 26:
                return new ConveyorBelt(26, 1, 2);
            case 27:
                return new ConveyorBelt(27, 0, 2);
            case 28:
                return new ConveyorBelt(28, 0, 2);
            case 29:
                return new ConveyorBelt(29, 3, 2);

            case 33:
                return new ConveyorBelt(33, 2, 1);
            case 34:
                return new ConveyorBelt(34, 3, 1);
            case 35:
                return new ConveyorBelt(35, 1, 1);
            case 36:
                return new ConveyorBelt(36, 2, 1);

            case 41:
                return new ConveyorBelt(41, 1, 1);
            case 42:
                return new ConveyorBelt(42, 0, 1);
            case 43:
                return new ConveyorBelt(43, 0, 1);
            case 44:
                return new ConveyorBelt(44, 3, 1);

            case 49:
                return new ConveyorBelt(49, 0, 1);
            case 50:
                return new ConveyorBelt(50, 2, 1);
            case 51:
                return new ConveyorBelt(51, 3, 1);
            case 52:
                return new ConveyorBelt(52, 1, 1);

            case 57:
                return new ConveyorBelt(57, 0, 1);
            case 58:
                return new ConveyorBelt(58, 1, 1);
            case 59:
                return new ConveyorBelt(59, 2, 1);
            case 60:
                return new ConveyorBelt(60, 3, 1);
            case 61:
                return new ConveyorBelt(61, 1, 1);
            case 62: //Kill me
                return new ConveyorBelt(62, 2, 1);

            case 65:
                return new ConveyorBelt(65, 0, 1);
            case 66:
                return new ConveyorBelt(66, 1, 1);
            case 67:
                return new ConveyorBelt(67, 2, 1);
            case 68:
                return new ConveyorBelt(68, 3, 1);
            case 69:
                return new ConveyorBelt(69, 0, 1);
            case 70:
                return new ConveyorBelt(70, 3, 1);

            case 73:
                return new ConveyorBelt(73, 0, 2);
            case 74:
                return new ConveyorBelt(74, 1, 2);
            case 75:
                return new ConveyorBelt(75, 2, 2);
            case 76:
                return new ConveyorBelt(76, 3, 2);
            case 77:
                return new ConveyorBelt(77, 0, 2);
            case 78:
                return new ConveyorBelt(78, 1, 2);

            case 81:
                return new ConveyorBelt(81, 1, 2);
            case 82:
                return new ConveyorBelt(82, 2, 2);
            case 83:
                return new ConveyorBelt(83, 3, 2);
            case 84:
                return new ConveyorBelt(84, 0, 2);
            case 85:
                return new ConveyorBelt(85, 3, 2);
            case 86:
                return new ConveyorBelt(86, 2, 2);

            //Luft-ruter
            case 56:
                return null;
            case 64:
                return null;
            case 72:
                return null;
            case 80:
                return null;

            default:
                System.err.println("Could not recognize ID no. " + cell.getTile().getId() + ". Please go to ComponentFactory and add that case to the list. Or you could go complain to Steinar.");
                return null;
        }
    }

}

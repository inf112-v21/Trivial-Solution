package GameBoard.Components;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import static GUIMain.GUI.DEVELOPER_MODE;

public class ComponentFactory {

    /**
     * Gitt en celle fra Libgdx-biblioteket, spawner denne vår ojektorienterte versjon.
     * Om celler er null returnerer vi bare null. Det er på en måte 'luft'.
     *
     * @param cell Cellen fra Libgdx, som skal konverteres til sitt tilsvarende objekt.
     * @return komponenten som ble skapt
     */

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
                return null; //Gulv, har ingen innvirkning på gameplay
            case 6:
                return new Hole(6);
            case 91: // "moderne" hull med kanter
                return new Hole(91);
            case 92: // hull uten kanter
                return new Hole(92);
            case 109: // hull uten venstre-kant
                return new Hole(109);
            case 110: // hull uten øverste-kant
                return new Hole(110);
            case 117: // hull uten høyre-kant
                return new Hole(117);
            case 118: // hull uten nederste-kant
                return new Hole(118);
            case 7:
                return new CheckPoint(7);
            case 15:
                return new Wrench(15);
            case 53:
                return new Gear(53, -1);
            case 54:
                return new Gear(54, 1);

            case 116: //hull med kun venstre-kant
                return new Hole(116);
            case 115: //hull uten øverste og venstre-kanter
                return new Hole(115);
            case 114: //hull med nederste-kant
                return new Hole(114);
            case 113: // hull uten øverste og høyre-kanter
                return new Hole(113);
            case 108: //hull med høyre-kant
                return new Hole(108);
            case 107: // hull uten nederste og venstre-kanter
                return new Hole(107);
            case 106: //hull med kun øverste-kant
                return new Hole(106);
            case 105: //hull uten nederste og høyre-kanter
                return new Hole(105);
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
            case 22:
                return new ConveyorBelt(22, 3, 2);

            case 25:
                return new ConveyorBelt(25, 1, 2);
            case 26:
                return new ConveyorBelt(26, 0, 2);
            case 27:
                return new ConveyorBelt(27, 0, 2);
            case 28:
                return new ConveyorBelt(28, 3, 2);

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
            case 48:
            case 56:
            case 64:
            case 72:
            case 80:
            case 88:
            case 96:
            case 104:
            case 111:
            case 112:
            case 119:
            case 120:

            case 125:
            case 126:
            case 127:
            case 128:

            case 133:
            case 134:
            case 135:
            case 136:
                return null;

            //Wall-ruter med en wall
            case 31:
                return new Wall(30,false,true,true,true); //Kan ikke gå opp vegg
            case 23:
                return new Wall(22,true,false,true,true); //Kan ikke gå til høyre vegg
            case 29:
                return new Wall(30,true,true,false,true); // Kan ikke gå ned vegg
            case 30:
                return new Wall(30,true,true,true,false); //Kan ikke gå til venstre
            case 8:
                return new Wall(8,true,false,false,true); //Kan ikke gå ned eller til høyre (Dobbelvegg)
            case 16:
                return new Wall(16,false,false,true,true); //Kan ikke gå til høyre eller opp (Dobbelvegg)
            case 24:
                return new Wall(24,false,true,true,false); //Kan ikke gå til venstre eller opp (Dobbelvegg)
            case 32:
                return new Wall(32,true,true,false,false); //Kan ikke gå til venstre eller ned (Dobbelvegg)


            //Lasere
            case 37:
                return new Laser(37, 0, false);
            case 38:
                return new Laser(38, 1, false);
            case 45:
                return new Laser(45, 2, false);
            case 46:
                return new Laser(46, 3, false);

            case 87:
                return new Laser(87, 0, true);
            case 93:
                return new Laser(93, 1, true);
            case 94:
                return new Laser(94, 2, true);
            case 95:
                return new Laser(95, 3, true);

            case 121:
                return new SpawnPoint(121);
            case 122:
                return new SpawnPoint(122);
            case 123:
                return new SpawnPoint(123);
            case 124:
                return new SpawnPoint(124);

            case 129:
                return new SpawnPoint(129);
            case 130:
                return new SpawnPoint(130);
            case 131:
                return new SpawnPoint(131);
            case 132:
                return new SpawnPoint(132);


            default:
                if (DEVELOPER_MODE) System.err.println("Could not recognize ID no. " + cell.getTile().getId() + ". Please go to ComponentFactory and add that case to the list. Or you could go complain to Steinar.");
                return null;
        }
    }
}

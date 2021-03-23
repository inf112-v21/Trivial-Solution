package GameBoard.Cards;

import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Samueljacoo
 *
 */

public interface ICard {

    /**
     * Om dette er et bevgelseskort, er tallet -1, 1, 2 eller 3 for hvor langt roboten skal gå.
     * Hvis ikke, return 0.
     */
    int getDistance();

    /**
     * Om dette er et rotasjonskort, er tallet 0, 1, 2, 3, som tilsvarer Nord, Øst, Sør og Vest i den rekkefølgen.
     * Hvis ikke, return 0.
     */
    int getRotation();

    /**
     * Prioriteten til kortet. Om flere spillere spiller mot hverandre, er det den med høyest prioritet på det valgte kortet sitt som skal flytte.
     * Alle kort har unik prioritet.
     */
    int priority();

    /**
     *
     * @return Bilde av et kort
     */
     Texture getCardImage();
}

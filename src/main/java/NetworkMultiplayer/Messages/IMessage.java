package NetworkMultiplayer.Messages;

public interface IMessage {

    /**
     * Brukes bare intill videre for å ha en samlet metode for meldinger
     * slik at vi kan bruke instanceOF i addlisteners() i client og server.
     *
     * Dette gjør vi får at alle objektene skal ha den
     * samme standard når de blir sendt over nettverket.
     *
     *
     */
}

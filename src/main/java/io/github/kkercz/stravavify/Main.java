package io.github.kkercz.stravavify;

import io.github.kkercz.stravavify.connector.ConnectorFactory;

public class Main {
    public static void main(String[] args) {
        try {
            ConnectorFactory connectorFactory = new ConnectorFactory();
            Stravavify stravavify = new Stravavify(
                    connectorFactory.spotifyConnector(),
                    connectorFactory.stravaConnector());

            stravavify.updateDescriptionWithSongs();

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
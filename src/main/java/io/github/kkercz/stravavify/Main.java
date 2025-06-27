package io.github.kkercz.stravavify;

import io.github.kkercz.stravavify.connector.ConnectorFactory;

public class Main {
    public static void main(String[] args) {

        System.out.println("\n\n---------- Stravavify: START ----------\n\n\n");

        try {
            ConnectorFactory connectorFactory = new ConnectorFactory();
            Stravavify stravavify = new Stravavify(
                    connectorFactory.spotifyConnector(),
                    connectorFactory.stravaConnector());


            stravavify.updateDescriptionWithSongs();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

        System.out.println("\n\n\n----------- Stravavify: END -----------\n");
    }
}
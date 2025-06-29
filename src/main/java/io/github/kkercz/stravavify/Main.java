package io.github.kkercz.stravavify;

import io.github.kkercz.stravavify.connector.ConnectorFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("\n---------- Stravavify: START ----------\n");

        ConnectorFactory connectorFactory = new ConnectorFactory();
        Stravavify stravavify = new Stravavify(
                connectorFactory.musicPlayer(),
                connectorFactory.stravaConnector());

        stravavify.updateDescriptionWithSongs();

        System.out.println("\n----------- Stravavify: END -----------\n");
    }
}
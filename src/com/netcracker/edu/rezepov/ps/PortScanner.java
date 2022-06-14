package com.netcracker.edu.rezepov.ps;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.IllegalBlockingModeException;

/**
 * Class that could help to scan ports.
 */
public class PortScanner {

    /**
     * Scans given port interval for open and closed ports.
     *
     * @param args hostname, port to start with, port to finish at
     */
    public PortScanner (String[] args) {
        try {
            Integer startPort = new Integer(args[1]);
            Integer endPort = new Integer(args[2]);
            Socket socket;
            System.out.println("Checking ports on " + args[0] + ":");
            for (int port = startPort; port <= endPort; ++port ) {
                try {
                    (socket = new Socket()).connect(new InetSocketAddress(args[0], port));
                    if (socket.isConnected()) {
                        System.out.println("Port " + port + " is opened.");
                        socket.close();
                    } else {
                        System.out.println("Port " + port + " is closed.");
                    }
                } catch (IllegalArgumentException | UnknownHostException e) {
                    System.err.println("Wrong ports or unresolvable hostname.");
                    System.exit(0);
                } catch (SecurityException | IOException | IllegalBlockingModeException e) {
                    System.out.println("Port " + port + " is closed.");
                }
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println("Wrong input format.");
        }
    }
}

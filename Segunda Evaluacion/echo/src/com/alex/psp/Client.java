package com.alex.psp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }
        int portNumber = 0;
        try {
            portNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("<port number> must be an integer value");
            System.exit(1);
        }
        if (portNumber < Server.MIN_PORT_NUMBER || portNumber > Server.MAX_PORT_NUMBER) {
            System.err.printf("<port number> must be an integer value between %d and %d%n", Server.MIN_PORT_NUMBER, Server.MAX_PORT_NUMBER);
            System.exit(1);
        }
        InetAddress localhost = InetAddress.getLocalHost();
        try (
                Socket socket = new Socket(localhost, portNumber);
                BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader stdInt = new BufferedReader(new InputStreamReader(System.in));


        ) {
            String line;
            while ((line = stdInt.readLine()) != null) {
                socketOut.println(line);
                System.out.println(socketIn.readLine());
            }
        }
    }
}

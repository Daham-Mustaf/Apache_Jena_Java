package gim.config;

import com.jcraft.jsch.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class GIMConnector {

    // credentials
    String user;
    String privateKey;

    // config local machine
    int sshPort;
    String localHost;
    int localRDFPort;
    int localNEO4JPort;
    int localBoltPort;

    // config server
    String remoteHost;
    int remoteRDFPort;
    int remoteNEO4JPort;
    int remoteBoltPort;

    Session session;

    public GIMConnector(GIMConfig gimConfig) {

        this.user = gimConfig.getUser();
        this.privateKey = gimConfig.getPrivateKey();
        this.sshPort = gimConfig.getSshPort();
        this.localHost = gimConfig.getLocalHost();
        this.localRDFPort = gimConfig.getLocalRDFPort();
        this.localNEO4JPort = gimConfig.getLocalNEO4JPort();
        this.localBoltPort = gimConfig.getLocalBoltPort();
        this.remoteHost = gimConfig.getRemoteHost();
        this.remoteRDFPort = gimConfig.getRemoteRDFPort();
        this.remoteNEO4JPort = gimConfig.getRemoteNEO4JPort();
        this.remoteBoltPort = gimConfig.getRemoteBoltPort();

    }

    /**
     * This method connects your local machine to the server and activates and sets the parameter of the port
     * forwarding functionality.
     */
    public void connect() {

        try {

            // Create connection to VM
            JSch jsch = new JSch();
            jsch.addIdentity(privateKey);

            // create and config session
            session = jsch.getSession(user, localHost, sshPort);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            
            // port forwarding
            session.setPortForwardingL(localRDFPort, remoteHost, remoteRDFPort);
            session.setPortForwardingL(localNEO4JPort, remoteHost, remoteNEO4JPort);
            session.setPortForwardingL(localBoltPort, remoteHost, remoteBoltPort);

            System.out.println("\nThe RDF store (Fuseki) can be accessed under http://" + remoteHost + ":" + localRDFPort);
            System.out.println("The property store (Neo4j) can be accessed under http://" + remoteHost + ":" + localNEO4JPort);

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {

                Desktop.getDesktop().browse(new URI("http://" + remoteHost + ":" + localRDFPort));
                Desktop.getDesktop().browse(new URI("http://" + remoteHost + ":" + localNEO4JPort));

            }
            
            
        } catch (JSchException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method disconnects your local machine from the server.
     */
    public void disconnect() {

        this.session.disconnect();

        System.out.println("Connection closed.");

    }

    /**
     * This method returns the session of the current connection.
     * @return a Java Session Object of the corresponding connection.
     */
    public Session getSession() {

        return session;

    }
}

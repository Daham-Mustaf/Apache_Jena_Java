package gim.config;

public class GIMConfig {

    // local machine
    private int sshPort;
    private String localHost;
    private int localRDFPort;
    private int localNEO4JPort;
    private int localBoltPort;

    // server
    private String remoteHost;
    private int remoteRDFPort;
    private int remoteNEO4JPort;
    private int remoteBoltPort;

    // credentials
    private String user;
    private String privateKey;


    public String getLocalHost() {

        return localHost;

    }

    public int getLocalRDFPort() {

        return localRDFPort;

    }

    public int getLocalNEO4JPort() {

        return localNEO4JPort;

    }

    public int getLocalBoltPort() {

        return localBoltPort;

    }

    public String getRemoteHost() {

        return remoteHost;

    }

    public int getRemoteRDFPort() {

        return remoteRDFPort;

    }

    public int getRemoteNEO4JPort() {

        return remoteNEO4JPort;

    }

    public int getRemoteBoltPort() {

        return remoteBoltPort;

    }

    public int getSshPort() {

        return sshPort;

    }

    public String getPrivateKey() {

        return privateKey;

    }

    public String getUser() {

        return user;

    }

}
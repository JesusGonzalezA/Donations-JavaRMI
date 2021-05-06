package server;

import client.Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Replica extends UnicastRemoteObject implements IReplica {

    // Attributes
    private final String  HOST;
    private final Integer PORT;
    private final String identifier;
    private final Registry registry;

    private ArrayList<String> idOtherReplicas;
    private ArrayList<IReplica> otherReplicas;
    private Map<String, Client> clients;
    private float total;

    // Constructor
    public Replica ( String host, int port, String identifier ) throws RemoteException {
        super();
        this.HOST = host;
        this.PORT = port;
        this.identifier = identifier;
        this.registry = LocateRegistry.getRegistry( this.HOST, this.PORT );

        this.idOtherReplicas = new ArrayList<>();
        this.otherReplicas   = new ArrayList<>();
        this.clients = new HashMap<String, Client>();
        this.total   = 0.0f;
    }

    public void addOtherReplica ( String idOtherReplica ) throws RemoteException, NotBoundException {
        this.idOtherReplicas.add( idOtherReplica );
        this.otherReplicas.add( (IReplica) this.registry.lookup( idOtherReplica ) );
    }


    // Getters
    /**
     * @return replica's identifier
     */
    public String getIdentifier() { return this.identifier; }

    /**
     * @return all clients
     */
    private Map<String, Client> getClients () { return this.clients; }

    // Methods

    /**
     * Increment the total. [MUTEX]
     * @param amount
     */
    private synchronized void incTotal ( float amount ){
        this.total += amount;
    }

    /**
     * Check if the user is registered and their credentials are correct
     * @param email
     * @param password
     * @return true if the client is registered and their credentials matches
     */
    private boolean passMiddleware ( String email, String password ) throws Exception {
        IReplica replica = this.whereIsClient( email );
        if ( replica == null ) return false;
        return replica.checkCredentials( email, password );
    }

    /**
     * Returns the replica where the client is registered
     * @param email
     * @return the replica or null if the client has not been auth
     */
    public IReplica whereIsClient( String email ) throws RemoteException, NotBoundException {
        if ( this.isClientRegistered( email ) ) return (IReplica) this;

        // Look for the client in other replicas
        for ( IReplica replica : this.otherReplicas )
            if ( replica.isClientRegistered( email ) )
                return replica;

        return null;
    }

    // Communication
        // Server - Server
    @Override
    public Client getClient ( String email ) {
        return this.getClients().get( email );
    }

    @Override
    public Boolean checkCredentials ( String email, String password ) throws RemoteException {
        return password.equals( this.getClient( email ).getPassword() );
    }

    @Override
    public Boolean isClientRegistered ( String email ) {
        return ( this.getClient( email ) != null );
    }

    @Override
    public Integer getNumClients () { return this.getClients().size(); }

    @Override
    public void register( String email, String password ) {
        this.getClients().put( email, new Client( email, password ) );
    }

    @Override
    public Boolean checkHasDonated (String email ) throws RemoteException {
        return ( this.getNumDonationsMadeByClient( email ) > 0 );
    }

    @Override
    public Integer getNumDonationsMadeByClient( String email ) throws RemoteException {
        return ( this.getClient( email).getNumDonations() );
    }

    @Override
    public float getTotalAmountDonatedByClient( String email ) throws RemoteException {
        return ( this.getClient( email).getTotal() );
    }

    @Override
    public float getTotalAmountDonated() throws RemoteException {
        return this.total;
    }


    @Override
    public void donate ( String email, float amount ) throws RemoteException {
        this.getClient( email ).donate( amount );
        this.incTotal(amount);
    }

        // Server - Client
    @Override
    public void registerCOMMClient(String email, String password) throws Exception {
        // Check if the client is registered or not
        if ( this.isClientRegistered( email ) ) throw new Exception("The user has already been registered");
        for ( IReplica otherReplica : this.otherReplicas )
                if ( otherReplica.isClientRegistered( email ) )
                throw new Exception("The user has already been registered");

        // Get the replica with the minimum number of clients
        IReplica replica = this;
        for ( IReplica otherReplica : this.otherReplicas )
            if ( otherReplica.getNumClients() <= replica.getNumClients() )
                replica = otherReplica;

        replica.register( email, password );
    }

    @Override
    public void donateCOMMClient(String email, String password, float amount) throws Exception {
        if ( !this.passMiddleware( email, password ) ) throw new Exception("Invalid credentials");
        if ( Float.compare(amount, 0.0f) <= 0 )        throw new Exception("Amount should be positive");

        IReplica replica = this.whereIsClient( email );
        replica.donate( email, amount );
    }

    @Override
    public float getClientTotalAmountCOMMClient(String email, String password) throws Exception {
        if ( !this.passMiddleware( email, password )   ) throw new Exception("Invalid credentials");

        IReplica replica = this.whereIsClient( email );

        if ( !replica.checkHasDonated ( email) )
            throw new Exception("The client has not donated yet");

        return replica.getTotalAmountDonatedByClient( email );
    }

    @Override
    public Integer getClientNumDonationsCOMMClient(String email, String password) throws Exception {
        if ( !this.passMiddleware  ( email, password ) ) throw new Exception("Invalid credentials");

        IReplica replica = this.whereIsClient( email );

        if ( !replica.checkHasDonated ( email) )
            throw new Exception("The client has not donated yet");

        return replica.getNumDonationsMadeByClient( email );
    }

    @Override
    public float getTotalAmountCOMMClient(String email, String password) throws Exception, RemoteException {
        if ( !this.passMiddleware  ( email, password ) ) throw new Exception("Invalid credentials");

        IReplica replica = this.whereIsClient( email );
        if ( !replica.checkHasDonated ( email) )
            throw new Exception("The client has not donated yet");

        float total = this.getTotalAmountDonated();
        for ( IReplica otherReplica : this.otherReplicas )
            total += otherReplica.getTotalAmountDonated();

        return total;
    }

    @Override
    public String toStringCOMMClient() throws RemoteException {
        String toString = "Replica information: \n"
                + "- Identifier: "        + this.getIdentifier() + "\n"
                + "- Number of clients: " + this.getNumClients() + "\n"
                + "- Total donated: "     + this.getTotalAmountDonated() + "\n" ;
        return toString;
    }

}

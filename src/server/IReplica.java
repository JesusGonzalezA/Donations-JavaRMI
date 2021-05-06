package server;

import client.Client;

import java.rmi.RemoteException;

/**
 * It provides the methods a replica should implement
 */
public interface IReplica extends IReplicaCommunicableClient{

    /**
     * Check if a client is registered
     * @param email
     * @return whether a client is registered or not
     * @throws RemoteException
     */
    Boolean
        isClientRegistered ( String email )
        throws RemoteException;

    /**
     * Check if the password matches with the client's password registered by email
     * @pre the user is registered
     * @param email
     * @param password
     * @return true if the password is correct
     */
    Boolean
        checkCredentials ( String email, String password )
        throws RemoteException;

    /**
     * Get the number of clients registered in this replica
     * @return Number of clients registered in this replica
     * @throws RemoteException
     */
    Integer
        getNumClients ()
        throws RemoteException;

    /**
     * Register a new client in the replica
     * @param email
     * @param password
     * @throws RemoteException
     * @throws Exception if the user has already been registered
     */
    void
        register(String email, String password )
        throws Exception, RemoteException;

    /**
     * Get a registered client
     * @param email
     * @return the client identified by email
     * @return null if the client is not registered
     * @throws RemoteException
     */
    Client
        getClient ( String email )
        throws RemoteException;

    /**
     * Check if a client registered by email has already donated
     * @pre the user is registered
     * @param email
     * @return true if the client has alreaady donated
     */
    Boolean
        checkHasDonated ( String email )
        throws RemoteException;

    /**
     * Get the number of donations made by a client
     * @param email
     * @return
     * @throws RemoteException
     */
    Integer
        getNumDonationsMadeByClient ( String email )
        throws RemoteException;

    /**
     * Get the total ammount donated by a client
     * @param email
     * @return
     * @throws RemoteException
     */
    float
        getTotalAmountDonatedByClient ( String email )
        throws RemoteException;

    /**
     * Get the total ammount donated
     * @return
     * @throws RemoteException
     */
    float
        getTotalAmountDonated ()
        throws RemoteException;


    /**
     * Makes a new donation in the replica where the client is registered
     * @param email
     * @param amount
     * @throws RemoteException
     */
     void
        donate ( String email, float amount )
        throws RemoteException;
}

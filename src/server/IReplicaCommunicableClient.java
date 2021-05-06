package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * It provides the methods a replica should implement in order to comm with a client
 */
public interface IReplicaCommunicableClient extends Remote {
    /**
     * Register a new client in the replica
     * @param email
     * @param password
     * @throws RemoteException
     * @throws Exception if the user has already been registered
     */
    void
        registerCOMMClient(String email, String password )
        throws Exception, RemoteException;

    /**
     * Add a new donation to the total
     * @param email
     * @param amount Amount to donate. Should be greater than 0.
     * @throws Exception if the user is not registered in the replica or incorrect credentials
     * @throws RemoteException
     */
    void
        donateCOMMClient( String email, String password, float amount )
        throws Exception, RemoteException;

    /**
     *
     * @param email
     * @return Total amount donated by a client
     * @throws Exception if the user is not registered in the replica or incorrect credentials
     * @throws RemoteException
     */
    float
        getClientTotalAmountCOMMClient( String email, String password )
        throws Exception, RemoteException;

    /**
     *
     * @param email
     * @return Number of donations made by a client
     * @throws Exception if the user is not registered in the replica or incorrect credentials
     * @throws RemoteException
     */
    Integer
        getClientNumDonationsCOMMClient( String email, String password )
        throws Exception, RemoteException;

    /**
     *
     * @param email
     * @return Total amount donated
     * @throws Exception if the user is not registered in the replica or incorrect credentials
     * @throws RemoteException
     */
    float
        getTotalAmountCOMMClient( String email, String password )
        throws Exception, RemoteException;


    /**
     * Show information about the server
     * @return information as a string
     * @throws RemoteException
     */
    String
        toStringCOMMClient ()
        throws RemoteException;
}

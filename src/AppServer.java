
import server.Replica;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServer {

    public static void main(String [] args) throws RemoteException {

        // Variables declaration
        final String HOST = "127.0.0.1";
        final int PORT = 1099;
        final String idReplica1 = "replica1";
        final String idReplica2 = "replica2";
        final String idReplica3 = "replica3";

        Replica replica1 = new Replica( HOST, PORT, idReplica1 );
        Replica replica2 = new Replica( HOST, PORT, idReplica2 );
        Replica replica3 = new Replica( HOST, PORT, idReplica3 );

        // Create security manager
        if (System.getSecurityManager() == null){
            System.setSecurityManager( new SecurityManager() );
        }

        // Start the server
        try{
            Registry reg = LocateRegistry.createRegistry( PORT );
            Naming.rebind( replica1.getIdentifier(), replica1 );
            Naming.rebind( replica2.getIdentifier(), replica2 );
            Naming.rebind( replica3.getIdentifier(), replica3 );

            replica1.addOtherReplica( idReplica2 );
            replica1.addOtherReplica( idReplica3 );

            replica2.addOtherReplica( idReplica1 );
            replica2.addOtherReplica( idReplica3 );

            replica3.addOtherReplica( idReplica1 );
            replica3.addOtherReplica( idReplica2 );

            System.out.println("Servidor RemoteException | MalformedURLExceptiondor preparado");
        }
        // Error
        catch (RemoteException | MalformedURLException | NotBoundException e) {
            System.out.println("Exception: " + e.getMessage());
        }


    }
}

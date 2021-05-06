import server.IReplicaCommunicableClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.AbstractMap;
import java.util.Scanner;

public class AppClient {

    public static void main (String args[]){

        // Variables declaration
        final int PORT = 1099;
        final String HOST       = "127.0.0.1";
        final String idReplica1 = "replica1";
        final String idReplica2 = "replica2";
        final String idReplica3 = "replica3";
        final Menu menu = new Menu();

        // Create security manager
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // Look in register
            Registry reg = LocateRegistry.getRegistry(HOST, PORT);
            IReplicaCommunicableClient replica1 = (IReplicaCommunicableClient) reg.lookup(idReplica1);
            IReplicaCommunicableClient replica2 = (IReplicaCommunicableClient) reg.lookup(idReplica2);
            IReplicaCommunicableClient replica3 = (IReplicaCommunicableClient) reg.lookup(idReplica3);

            // Start
            Options option = Options.SALIR;
            String email   = "", password = "";
            Replicas index_replica = Replicas.REPLICA1;
            IReplicaCommunicableClient replica = replica1;

            do {
                menu.showMenu();
                option = menu.readReponse();

                if ( option != Options.SALIR )
                {
                    index_replica = menu.readReplica();

                    switch ( index_replica ) {
                        case REPLICA1:
                            replica = replica1;
                            break;
                        case REPLICA2:
                            replica = replica2;
                            break;
                        case REPLICA3:
                            replica = replica3;
                            break;
                    }

                }

                switch ( option ) {

                    case DONATE:
                        float amount = menu.readAmountToDonate();
                        replica.donateCOMMClient(email, password, amount);

                        break;

                    case LOGIN:
                        AbstractMap.SimpleEntry<String, String> login = menu.showLogin();

                        email = login.getKey();
                        password = login.getValue();

                        break;

                    case REGISTER:
                        AbstractMap.SimpleEntry<String, String> register = menu.showLogin();
                        email = register.getKey();
                        password = register.getValue();

                        replica.registerCOMMClient(email, password);

                        break;


                    case GET_DONATE:
                        System.out.println(
                              "Total donado por el cliente: "
                            + replica.getClientTotalAmountCOMMClient(email, password)
                        );

                        break;


                    case GET_NUM_DONATIONS:
                        System.out.println(
                                "Numero de donaciones realizadas por el cliente: "
                               + replica.getClientNumDonationsCOMMClient(email, password)
                        );

                        break;

                    case GET_TOTAL:
                        System.out.println(
                                "Total donado: "
                               + replica.getTotalAmountCOMMClient(email, password)
                        );
                        break;

                    case SHOW_INFO:
                        System.out.println( replica.toStringCOMMClient() );
                        break;
                }

            } while ( option != Options.SALIR );

        }
        // Error RMI
        catch(NotBoundException | RemoteException e){
            System.err.println("Exception del sistema: " + e);
        }
        // Exception
        catch (Exception e) {
            System.out.println("\033[0m" + "Excepcion");
            e.printStackTrace();
        }

        System.exit(0);
    }
}

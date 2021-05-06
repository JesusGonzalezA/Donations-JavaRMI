import java.util.AbstractMap;
import java.util.Scanner;

class Menu {

    // Colors
    public static final String ANSI_RESET = "\033[0m";      // Text Reset
    public static final String ANSI_GREEN = "\033[0;32m";   // GREEN
    public static final String ANSI_BLUE = "\033[0;34m";    // BLUE

    private final Scanner in;

    Menu () {
        this.in     = new Scanner( System.in );
    }

    void showMenu () {
        System.out.println(
                ANSI_GREEN
                        + "------------------------------------------------\n"
                        + "Menu \n"
                        + "- 0: Login                                      \n"
                        + "- 1: Registrar un nuevo cliente                 \n"
                        + "- 2: Donar                                      \n"
                        + "- 3: Ver numero de donaciones realizadas por mi \n"
                        + "- 4: Ver total donado por mi                    \n"
                        + "- 5: Ver total donado                           \n"
                        + "- 6: Mostrar informacion del servidor           \n"
                        + "- 7: Salir                                      \n"
                        + "------------------------------------------------\n"
                        + ANSI_RESET
        );
    }

    AbstractMap.SimpleEntry<String, String> showLogin () {
        String email, password;

        System.out.print(ANSI_BLUE);
        System.out.println("Login");
        System.out.print("- Email: ");
        email = this.in.next();
        System.out.print("- Password: ");
        password = this.in.next();
        System.out.println(ANSI_RESET);

        return new  AbstractMap.SimpleEntry<>(email, password);
    }

    float readAmountToDonate () {
        System.out.print(ANSI_BLUE);
        System.out.print("Introduzca una cantidad a donar: ");
        float amount = this.in.nextFloat();
        System.out.println(ANSI_RESET);

        return amount;
    }

    Replicas readReplica() {
        System.out.println(ANSI_BLUE);
        System.out.println("Seleccione la replica con la que trabajar");
        System.out.println("1 - Replica1");
        System.out.println("2 - Replica2");
        System.out.println("3 - Replica3");
        System.out.print("Replica: ");
        int index_replica = this.in.nextInt();
        System.out.println(ANSI_RESET);

        return ( Replicas.values() )[index_replica - 1];
    }

    Options readReponse() {
        System.out.print(ANSI_GREEN + "Repuesta: ");
        int index_option = this.in.nextInt();
        System.out.println(ANSI_RESET);

        Options option = ( Options.values() )[index_option];
        return option;
    }
}
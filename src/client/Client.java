package client;

import java.rmi.RemoteException;

public class Client {

    // Attributes
    private String
            email,
            password;
    private int numDonations;
    private float total;

    // Contructor
    public Client ( String email, String password ) {
        this.email        = email;
        this.password     = password;
        this.numDonations = 0;
        this.total        = 0.0f;
    }

    // Getters
    public String  getEmail        () { return this.email;        }
    public String  getPassword     () { return this.password;     }
    public Integer getNumDonations () throws RemoteException { return this.numDonations; }
    public float   getTotal        () { return this.total;        }

    // Setters
    public void setNumDonations ( int n )       { this.numDonations = n;     }
    public void setTotal        ( float total ) { this.total        = total; }

    // Increment
    private void incNumDonations ()               { this.numDonations++;  }
    private void incTotal        ( float amount ) { this.total += amount; }

    // Methods

    /**
     * Donate
     * @pre amount > 0
     * @post total will be incremented by amount and numDonations plus one
     * @param amount to donate
     */
    public void donate ( float amount ) {
        this.incNumDonations();
        this.incTotal( amount );
    }
}

package agenziaviaggi;

/**
 *
 * @author lucadev23
 */
public class AgenziaViaggi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        gestoreViaggi gestore = new gestoreViaggi();
        
        threadForeground tf = new threadForeground(gestore);
        threadBackground tb = new threadBackground(gestore);
        
        tf.start();
        tb.start();
    }
    
}

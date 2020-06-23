package agenziaviaggi;

/**
 *
 * @author lucadev23
 */
public class threadBackground extends Thread{
    
    private gestoreViaggi gestore;
    
    public threadBackground(gestoreViaggi gestore){
        this.gestore=gestore;
    }
    
    @Override
    public void run(){
        while(true){
            try{
                gestore.ricavoTotalePerViaggio();
                gestore.contollaDisponibilita();
                gestore.totalePersonePrenotate();
                Thread.sleep(5000);
            }catch(InterruptedException iex){
                System.out.println("");
            }
        }
    }
}

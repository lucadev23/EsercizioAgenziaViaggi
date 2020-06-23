package agenziaviaggi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author lucadev23
 */
public class threadForeground extends Thread{
    
    private gestoreViaggi gestore;
    private BufferedReader tastiera;
    
    public threadForeground(gestoreViaggi gestore){
        this.gestore=gestore;
        tastiera=new BufferedReader(new InputStreamReader(System.in));
    }
    
    @Override
    public void run(){
        gestore.caricaViaggi();
        gestore.caricaPrenotazioni();
        while(true){
            switch(menu()){
                case 0:
                    System.exit(0);
                case 1:
                    gestore.inserisciPrenotazioneDaInput();
                    break;
                case 2:
                    gestore.cancellaPrenotazione();
                    break;
                case 3:
                    gestore.inserisciNuovoViaggio();
                    break;
                case 4:
                    gestore.viaggiDiUnaPersona();
                    break;
                case 5:
                     gestore.stampaRicavoTotale();
                    break;
                case 6:
                    gestore.stampaControlloDisponibilita();
                    break;
                case 7:
                    gestore.stampaTotalePersonePrenotate();
                    break;
                default:
                    break;
            }
        }
    }
    
    public int menu(){
        int scel=-1;
        do{
            System.out.print(""
                    + "1 - Inserisci prenotazione\n"
                    + "2 - Cancella prenotazione\n"
                    + "3 - Inserisci viaggio\n"
                    + "4 - Crea lista persona specifica\n"
                    + "5 - Stampa riavo totale\n"
                    + "6 - Stampa disponibilità\n"
                    + "7 - Stampa totale persone prenotate\n"
                    + "0 - Esci\n"
                    + "Scegli: ");
            try{
                scel=Integer.parseInt( tastiera.readLine() );
            }
            catch(NumberFormatException nfex){
                System.out.println("Devi inserire un valore numerico");
            }
            catch(IOException ioex){
                System.out.println("Errore i/o");
            }
        }while(scel<0 || scel>7);
        return scel;
    }
    
}

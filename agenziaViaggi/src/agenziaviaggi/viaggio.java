package agenziaviaggi;

import java.util.LinkedList;

/**
 *
 * @author luca
 */
public class viaggio {
    private String identificativo;
    private String descrizione;
    private float costo;
    private int maxPersone;
    private int count;
    private LinkedList<persona> listaPrenotazioni;
    
    public viaggio(String identificativo, String descrizione, float costo, int maxPersone){
        this.identificativo=identificativo;
        this.descrizione=descrizione;
        this.costo=costo;
        this.maxPersone=maxPersone;
        listaPrenotazioni=new LinkedList<persona>();
        count=0;
    }
    
    public String getId(){
        return this.identificativo;
    }
    
    public void aggiungiPrenotazione(persona p){
        listaPrenotazioni.add(p);
        count++;
    }
    
    public boolean comparaCodiceFiscale(String codice){
        for(persona p: listaPrenotazioni){
            if(codice.equalsIgnoreCase(p.getCodice())){
                return true;
            }
        }
        return false;
    }
    
    public boolean postiLiberi(){
        if(maxPersone>count){
            return true;
        }
        return false;
    }
    
    public void cancellaPrenotazione(String codice){
        listaPrenotazioni.remove(new persona(null, codice));
    }
    
    public float getRicavoTotale(){
        return (costo*count);
    }
    
    public int getNumPersone(){
        return this.count;
    }
}

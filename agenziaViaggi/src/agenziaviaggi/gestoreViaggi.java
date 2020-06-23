package agenziaviaggi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 *
 * @author lucadev23
 */
public class gestoreViaggi {
    
    private LinkedList<viaggio> listaViaggi;
    private BufferedReader tastiera;
    private LinkedList<Float> ricavoTotale;
    private LinkedList<viaggio> viaggiConPostiDisponibili; /*Anzichè una lista di viaggi, si poteva fare una lista di String che contiene solo L'ID di ogni viaggio. Ciò potrebbe ridurre spazio in memoria*/
    private LinkedList<Integer> personePrenotatePerViaggio;
    private LinkedList<String> viaggiDiUnaPersona;
    
    public gestoreViaggi(){
        listaViaggi=new LinkedList<viaggio>();
        tastiera=new BufferedReader(new InputStreamReader(System.in));
    }
    
    public synchronized void stampaViaggiPersona(String codiceFiscale){
        viaggiDiUnaPersona = new LinkedList<String>();
        for(viaggio v: listaViaggi){
            if(v.comparaCodiceFiscale(codiceFiscale)){
                viaggiDiUnaPersona.add(v.getId());
            }
        }
        if(viaggiDiUnaPersona.isEmpty()){
            System.out.println("Persona non trovata");
        }
        else{
            int i=0;
            System.out.println("Viaggi di "+codiceFiscale+":");
            for(String id: viaggiDiUnaPersona){
                i++;
                System.out.println("Viaggio "+i+" -> "+id);
            }
        }
    }
    
    public synchronized void viaggiDiUnaPersona(){
        String codice;
        try{
            System.out.println("Inserisci il codice fiscale: ");
            codice=tastiera.readLine();
            stampaViaggiPersona(codice);
        }catch(IOException ioex){
            System.out.println("Errore i/o");
            System.exit(-1);
        }
    }
    
    public synchronized void stampaTotalePersonePrenotate(){
        int i=0;
        for(viaggio v: listaViaggi){
            System.out.println("Viaggio "+v.getId()+" persone prenotate: "+personePrenotatePerViaggio.get(i));
            i++;
        }
    }
    
    public synchronized void totalePersonePrenotate(){
        personePrenotatePerViaggio = new LinkedList<Integer>();
        for(viaggio v: listaViaggi){
            personePrenotatePerViaggio.add(new Integer(v.getNumPersone()));
        }
    }
    
    public synchronized void stampaControlloDisponibilita(){
        for(viaggio v: viaggiConPostiDisponibili){
            System.out.println("Viaggio "+v.getId()+" disponibile");
        }
    }
    
    public synchronized void contollaDisponibilita(){
        viaggiConPostiDisponibili = new LinkedList<viaggio>();
        for(viaggio v: listaViaggi){
            if(v.postiLiberi()){
                viaggiConPostiDisponibili.add(v);
            }
        }
    }
    
    public synchronized void stampaRicavoTotale(){
        int i=0;
        for(viaggio v: listaViaggi){
            System.out.println("Viaggio "+v.getId()+" ricavo: "+ricavoTotale.get(i));
            i++;
        }
    }
    
    /* Non mi piace manco per il cazzo, senza senso*/
    public synchronized void ricavoTotalePerViaggio(){
        ricavoTotale=new LinkedList<Float>();
        for(viaggio v: listaViaggi){
            ricavoTotale.add(new Float(v.getRicavoTotale()));
        }
    }
    
    public synchronized void inserisciNuovoViaggio(){
        String id,desc;
        float prezzo;
        int max;
        try{
            do{
                System.out.println("Inserisci nuovo identificativo viaggio: ");
                id=tastiera.readLine();
            }while(identificativoEsistente(id));
            System.out.println("Inserisci descrizione viaggio: ");
            desc=tastiera.readLine();
            System.out.println("Inserisci prezzo viaggio: ");
            prezzo=Float.parseFloat(tastiera.readLine());
            System.out.println("Inserisci max viaggiatori: ");
            max=Integer.parseInt(tastiera.readLine());
            listaViaggi.add(new viaggio(id, desc, prezzo, max));
        }catch(IOException ioex){
            System.out.println("Errore i/o");
            System.exit(-1);
        }
    }
    
    public synchronized void cancellaPrenotazione(){
        String id, codice;
        try{
            do{
                System.out.println("--Identificativi viaggi--");
                stampaIdentificativi();
                System.out.print("Scegli identificativo: ");
                id = tastiera.readLine();
            }while(!identificativoEsistente(id));
            System.out.println("Inserisci codice: ");
            codice=tastiera.readLine();
            for(viaggio v: listaViaggi){
                if(v.getId().equalsIgnoreCase(id)){
                    v.cancellaPrenotazione(codice);
                }
            }
        }catch(IOException ioe){
            System.out.println("Errore i/o");
            System.exit(-1);
        }
    }
    
    public synchronized void controlloSuperamentoPrenotazioni(String id) throws NumeroMaxPrenotazioniException{
        for(viaggio v: listaViaggi){
            if(v.getId().equalsIgnoreCase(id)){
                if(v.postiLiberi()==false){
                    throw new NumeroMaxPrenotazioniException();
                }
            }
        }
    }
    
    public synchronized void controlloEsistenzaPrenotazione(String id, String codice) throws PersonaEsistenteException{
        for(viaggio v: listaViaggi){
            if(v.getId().equalsIgnoreCase(id)){
                if(v.comparaCodiceFiscale(codice)){
                    throw new PersonaEsistenteException();
                }
            }
        }
    }
    
    public synchronized void stampaIdentificativi(){
        for(viaggio v: listaViaggi){
            System.out.println(v.getId());
        }
    }
    
    public synchronized boolean identificativoEsistente(String ids){
        for(viaggio v: listaViaggi){
            if(v.getId().equalsIgnoreCase(ids)){
                return true;
            }
        }
        return false;
    }
    
    public synchronized void inserisciPrenotazioneDaInput(){
        String id, nomeCognome, codice;
        try{
            do{
                System.out.println("--Identificativi viaggi--");
                stampaIdentificativi();
                System.out.print("Scegli identificativo: ");
                id = tastiera.readLine();
            }while(!identificativoEsistente(id));
            controlloSuperamentoPrenotazioni(id);
            System.out.println("Inserisci nome e cognome della persona: ");
            nomeCognome=tastiera.readLine();
            System.out.println("Inserisci codice fiscale: ");
            codice=tastiera.readLine();
            controlloEsistenzaPrenotazione(id,codice);
            inserisciPrenotazione(id, new persona(nomeCognome,codice));
        }
        catch(NumeroMaxPrenotazioniException nmpex){
            System.out.println("Eccezzione: Nessun posto libero");
            return;
        }
        catch(PersonaEsistenteException pee){
            System.out.println("Eccezzione: Persona già esistente");
            return;
        }
        catch(IOException ioe){
            System.out.println("Errore i/o");
            System.exit(-1);
        }
    }
    
    public synchronized void inserisciPrenotazione(String id, persona personaX){
        for(viaggio v: listaViaggi){
            if(v.getId().equalsIgnoreCase(id)){
                v.aggiungiPrenotazione(personaX);
            }
        }
    }
    
    public synchronized void caricaPrenotazioni(){
        BufferedReader fpPrenotazioni;
        try{
            fpPrenotazioni = new BufferedReader(new FileReader("prenotazioni.txt"));
            String id, nomeCognome, codice;
            id=fpPrenotazioni.readLine();
            while(id!=null){
                nomeCognome=fpPrenotazioni.readLine();
                codice=fpPrenotazioni.readLine();
                persona personaX = new persona(nomeCognome, codice);
                inserisciPrenotazione(id,personaX);
                id=fpPrenotazioni.readLine();
            }
        }catch(FileNotFoundException fex){
            System.out.println("Errore apertura file 'pacchetti-viaggi.txt'");
            System.exit(-1);
        }
        catch(IOException ioex){
            System.out.println("Errore lettura file 'pacchetti-viaggi.txt'");
            System.exit(-1);
        }
    }
    
    public synchronized void caricaViaggi(){
        BufferedReader fpViaggi;
        try{
            fpViaggi = new BufferedReader(new FileReader("pacchetti-viaggi.txt"));
            String id, desc;
            float costo;
            int max;
            id=fpViaggi.readLine();
            while(id!=null){
                desc=fpViaggi.readLine();
                costo=Float.parseFloat(fpViaggi.readLine());
                max=Integer.parseInt(fpViaggi.readLine());
                listaViaggi.add(new viaggio(id,desc,costo,max));
                id=fpViaggi.readLine();
            }
        }catch(FileNotFoundException fex){
            System.out.println("Errore apertura file 'pacchetti-viaggi.txt'");
            System.exit(-1);
        }
        catch(IOException ioex){
            System.out.println("Errore lettura file 'pacchetti-viaggi.txt'");
            System.exit(-1);
        }
    }
    
}

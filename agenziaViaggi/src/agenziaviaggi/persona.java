package agenziaviaggi;

/**
 *
 * @author lucadev23
 */
public class persona {
    private String nomeCognome;
    private String codice;
    
    
    public persona(String nomeCognome, String codice){
        this.nomeCognome=nomeCognome;
        this.codice=codice;
    }
    
    public String getCodice(){
        return this.codice;
    }
    
    @Override
    public boolean equals(Object p){
        return( this.codice.equalsIgnoreCase( ((persona)p).getCodice() ) );
    }
    
}

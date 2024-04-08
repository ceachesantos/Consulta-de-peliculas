package p2;

public class Year implements Comparable<Year>{

    String titulo;
    String idiomas;
    String generos;
    String sinopsis;

    public Year (String titulo, String idiomas, String generos, String sinopsis){
        this.titulo  = titulo;
        this.idiomas = idiomas;
        this.generos  = generos;
        this.sinopsis  = sinopsis;
    }

    public void setTitulo(String titulo){
        this.titulo=titulo;
        return;
    }

    public void setIdiomas(String idiomas){
        this.idiomas=idiomas;
        return;
    }

    public void setGeneros(String generos){
        this.generos=generos;
        return;
    }

    public void setSinopsis(String sinopsis){
        this.sinopsis=sinopsis;
        return;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getIdiomas(){
        return idiomas;
    }

    public String getGeneros(){
        return generos;
    }

    public String getSinopsis(){
        return sinopsis;
    }

    public int compareTo(Year Year2){
        int compidiomas = 0;
        int comptitulo = 0;
        compidiomas = this.idiomas.compareTo(Year2.idiomas);
        comptitulo = this.titulo.compareTo(Year2.titulo);

        if(compidiomas == 0){
            return comptitulo;
        }else{
            return compidiomas;
        }
    }

    public String toString(){
        return "Titulo = '"+titulo+"'   ---   Idiomas = '"+idiomas+"'   ---   Generos = '"+generos+"'   ---   Sinopsis = '"+sinopsis+"'";
    }

}
package p2;

import java.util.ArrayList;

public class Movie implements Comparable<Movie>{

    String titulo;
    String idioma;
    ArrayList<String> genero;
    String sinopsis;
    int duracion;

    public Movie (){
    
    }

    public Movie (String titulo, String idioma, ArrayList<String> genero, String sinopsis){
        this.titulo  = titulo;
        this.idioma = idioma;
        this.genero  = genero;
        this.sinopsis  = sinopsis;
    }

    public void setTitulo(String titulo){
        this.titulo=titulo;
        return;
    }

    public void setIdioma(String idioma){
        this.idioma=idioma;
        return;
    }

    public void setGenero(ArrayList<String> genero){
        this.genero=genero;
        return;
    }

    public void setSinopsis(String sinopsis){
        this.sinopsis=sinopsis;
        return;
    }

    public void setDuracion(int duracion){
        this.duracion=duracion;
        return;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getIdioma(){
        return idioma;
    }

    public ArrayList<String> getGenero(){
        return genero;
    }

    public String getSinopsis(){
        return sinopsis;
    }

    public int getDuracion(){
        return duracion;
    }

    public int compareTo(Movie Pelicula){

        if(this.genero.size() < Pelicula.genero.size()){
            return 1;
        }

        else if(this.genero.size() > Pelicula.genero.size()){
            return -1;
        }

        else{
            return this.titulo.compareTo(Pelicula.titulo);
        }
    }

    public String toString(){
        return "Pelicula = '"+titulo+"'   ---   Idioma = '"+idioma+"'   ---   Genero = '"+String.join(",", genero)+"'   ---   Sinopsis = '"+sinopsis+"'";
    }

}
package p2;

import p2.Year;
import p2.Cast;
import p2.Movie;
import p2.Sint96P2;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.util.*;

import java.net.*;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.XMLConstants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class DataModel {

    public ArrayList<String> getQ1Years(){
        
        ArrayList<String> varYears = new ArrayList<String>();
        varYears.addAll(Sint96P2.MapaFicheros.keySet());

        Collections.sort(varYears, Collections.reverseOrder()); //ordenamos al reves con el reverseOrder
        return varYears;
    }

    public ArrayList<Movie> getQ1Movies(String year){
        ArrayList<Movie> arrayMovies = new ArrayList<Movie>();
        NodeList NLmovies;
        try{
            NLmovies = (NodeList)Sint96P2.xpath.evaluate("/Movies/Movie", Sint96P2.MapaFicheros.get(year), XPathConstants.NODESET);
            //this.log("longitud "+peliculas.getLength());

            for(int i=0; i<NLmovies.getLength(); i++){
                Element elementMovies = (Element)NLmovies.item(i);
                Movie movie = new Movie();

                movie.setTitulo(((Element)(elementMovies.getElementsByTagName("Title")).item(0)).getTextContent().trim());
                try{
                    int duration = Integer.parseInt(((Element)(elementMovies.getElementsByTagName("Duration")).item(i)).getTextContent().trim());
                    movie.setDuracion(duration);
                }catch(NullPointerException e){}

                movie.setIdioma(elementMovies.getAttribute("langs").trim());
                movie.setSinopsis(((String)Sint96P2.xpath.evaluate("text()[normalize-space()]", NLmovies.item(i), XPathConstants.STRING)).trim());
                ArrayList<String> genres = new ArrayList<>();

                for(int j=0; j<elementMovies.getElementsByTagName("Genre").getLength(); j++){
                    genres.add(((Element)(elementMovies.getElementsByTagName("Genre")).item(j)).getTextContent().trim());
                }
                
                movie.setGenero(genres);
                arrayMovies.add(movie);
            }
        } catch(XPathExpressionException e){
            e.printStackTrace();
        }
        Collections.sort(arrayMovies); //ordenamos usando el compareTo hecho en la clase Movie
        return arrayMovies;
        
    }

    public ArrayList<Cast> getQ1Cast(String year, String movie){
        //this.log("ano "+year+" peli "+movie);
       
        ArrayList<Cast> ArrayListCast = new ArrayList<Cast>();
        NodeList NLcast = null;

        try{
            //this.log("hola1");
           NLcast = (NodeList) Sint96P2.xpath.evaluate("/Movies/Movie[Title='"+movie+"']/Cast", Sint96P2.MapaFicheros.get(year), XPathConstants.NODESET);
            //this.log("hola2");
        }catch(Exception e){
            e.printStackTrace();
        }
        //this.log("longitud cast "+NLcast.getLength());

        //para cada actor:
        for(int i=0; i<NLcast.getLength(); i++){
            //this.log("hola3");
            Element elemCast = (Element) NLcast.item(i);

            NodeList NLNombre = elemCast.getElementsByTagName("Name");  //obtenemos el elemento Name
            Element elemNombre = (Element) NLNombre.item(0);
            String nombre = elemNombre.getTextContent().trim();
            //this.log("nombre"+nombre);

            String ID = elemCast.getAttribute("id");    //obtenemos la id
            
            NodeList NLRol = elemCast.getElementsByTagName("Role"); //obtenemos Role
            Element elemRol = (Element) NLRol.item(0);
            String rol = elemRol.getTextContent().trim();

            //capturamos email y telefono para saber que meter en contacto
            NodeList NLemail = elemCast.getElementsByTagName("Email");
            NodeList NLphone = elemCast.getElementsByTagName("Phone");
            String contacto = null;

            if(NLemail.getLength() == 0){
                NodeList NLcontacto = elemCast.getElementsByTagName("Phone");
                Element elemContacto = (Element) NLcontacto.item(0);
                contacto = elemContacto.getTextContent().trim();
            }
            if(NLphone.getLength() == 0){
                NodeList NLcontacto = elemCast.getElementsByTagName("Email");
                Element elemContacto = (Element) NLcontacto.item(0);
                contacto = elemContacto.getTextContent().trim();
            }

            ArrayListCast.add(new Cast(nombre, ID, rol, contacto));
        }

        Collections.sort(ArrayListCast); //ordenamos con el compareTo de cast
        return ArrayListCast;
    }

}

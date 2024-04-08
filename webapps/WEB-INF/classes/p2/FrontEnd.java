package p2;

import p2.Year;
import p2.Cast;
import p2.Movie;

import java.io.*;

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

public class FrontEnd{

    public FrontEnd(){}

    public static void doGetErrorHTML (HttpServletResponse response, String stringTexto) { //errores pagina HTML
        response.setContentType("text/html");
        try{
            PrintWriter out = response.getWriter();
            out.println("<html lang='es-es'>");
            out.println("<head>");
            out.println("<meta charset='utf-8'>");
            out.println("<link rel='stylesheet' href='p2/decoracion.css'>");
            out.println("<title>Servicio de consulta de peliculas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>"+stringTexto+"<h2>");
            out.println("</body>");
            out.println("<footer>Christian Santos Duarte (2020-2021)</footer>");
            out.println("</html>");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void doGetErrorXML (HttpServletResponse response, String stringTexto) { //errores XML
        response.setContentType("text/xml");
        try{
            PrintWriter out = response.getWriter(); 

            out.println("<?xml version='1.0' encoding='utf-8'?>");
            out.println("<wrongRequest>" + stringTexto + "</wrongRequest>");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /*******************************************************************************************************************
     * 
     *                                                  Envio de HTML
     * 
     *******************************************************************************************************************/
    
    public void enviarHTMLF01(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String ppassword = request.getParameter("p");

        out.println("<html lang='es-es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8' />");
        out.println("<link rel='stylesheet' href='p2/decoracion.css'>");
        out.println("<title>Servlet SINT96 P2</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de peliculas</h1>");
        out.println("<h2>Bienvenido a este servicio</h2>");
        out.println("<h2>Seleccione una consulta</h2>");
        out.println("<form>");
        out.println("<li><a href='?p="+ppassword+"&pphase=11'>Consulta 1: Reparto de una película de un año</a></li>");
        out.println("<input type='hidden' name='p' value='"+ppassword+"'>");
        out.println("<input type='hidden' name='pphase' value='11'><br><br>");
        out.println("</form>");
        out.println("</body>");
        out.println("<br><br><footer>Christian Santos Duarte (2021-2022)</footer>");
        out.println("</html>");
        return;
    }

    public void enviarHTMLF11(HttpServletRequest request, HttpServletResponse response, ArrayList<String> ArrayYears) throws IOException{
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String ppassword = request.getParameter("p");

        out.println("<html lang='es-es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8' />");
        out.println("<link rel='stylesheet' href='p2/decoracion.css'>");
        out.println("<title>Servlet SINT96 P2</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de peliculas</h1>");
        out.println("<h2>Consulta 1: Fase 1</h2>");
        out.println("<h2>Seleccione un año:</h2>");
        out.println("<form>");
        out.println("<ul>");

        for(int i=0; i<ArrayYears.size(); i++){
                out.println("<li><a href='?p="+ppassword+"&pphase=12&pyear="+ArrayYears.get(i)+"'>"+ArrayYears.get(i)+"</a></li>");
            }

        out.println("</ul>");
        out.println("<input type='hidden' name='p' value='"+ppassword+"'>");
        out.println("<input type='hidden' name='pphase' value='12'><br>");
        //inicio
        out.println("<a href='?pphase=01&p="+ppassword+"''><input type='button' value='Inicio'></a>");
        //
        out.println("</form>");
        out.println("</body>");
        out.println("<br><br><footer>Christian Santos Duarte (2021-2022)</footer>");
        out.println("</html>");
        return;
    }

    public void enviarHTMLF12(HttpServletRequest request, HttpServletResponse response, ArrayList<Movie> ArrayMovies) throws IOException{
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String ppassword = request.getParameter("p");
        String pyear = request.getParameter("pyear");

        out.println("<html lang='es-es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8' />");
        out.println("<link rel='stylesheet' href='p2/decoracion.css'>");
        out.println("<title>Servlet SINT96 P2</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de peliculas</h1>");
        out.println("<h2>Consulta 1: Fase 2 (Año = "+pyear+")</h2>");
        out.println("<h2>Seleccione una pelicula:</h2>");
        out.println("<form>");
        out.println("<ul>");

        for(int i=0; i<ArrayMovies.size(); i++){
            out.println("<li><a href='?p="+ppassword+"&pphase=13&pyear="+pyear+"&pmovie="+ArrayMovies.get(i).getTitulo()+"'>"+ArrayMovies.get(i).toString()+"</a></li>");
        }

        out.println("<input type='hidden' name='p' value='"+ppassword+"'>");
        out.println("<input type='hidden' name='pyear' value='"+pyear+"'>");
        out.println("<input type='hidden' name='pphase' value='13'>");
        //inicio
        out.println("<a href='?pphase=01&p="+ppassword+"''><input type='button' value='Inicio'></a>");
        //atras
        out.println("<a href='?pphase=11&p="+ppassword+"''><input type='button' value='Atras'></a>");
        out.println("</ul>");
        out.println("</form>");
        out.println("</body>");
        out.println("<br><br><footer>Christian Santos Duarte (2021-2022)</footer>");
        out.println("</html>");
        return;
    }

    public void enviarHTMLF13(HttpServletRequest request, HttpServletResponse response, ArrayList<Cast> ArrayCast) throws IOException{
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String ppassword = request.getParameter("p");
        String pyear = request.getParameter("pyear");
        String pmovie = request.getParameter("pmovie");

        out.println("<html lang='es-es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8' />");
        out.println("<link rel='stylesheet' href='p2/decoracion.css'>");
        out.println("<title>Servlet SINT96 P2</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de peliculas</h1>");
        out.println("<h2>Consulta 1: Fase 3 (Año = "+pyear+", Pelicula = "+pmovie+")</h2>");
        out.println("<h2>Este es el resultado de la consulta:</h2>");
        out.println("<form>");
        out.println("<ul>");

        for(int i=0; i<ArrayCast.size(); i++){
            out.println("<li>"+ArrayCast.get(i).toString()+"</li>");
        }

        out.println("<input type='hidden' name='p' value='"+ppassword+"'>");
        out.println("<input type='hidden' name='pyear' value='"+pyear+"'>");
        out.println("<input type='hidden' name='pmovie' value='"+pmovie+"'>");
        //inicio
        out.println("<br><a href='?pphase=01&p="+ppassword+"''><input type='button' value='Inicio'></a>");
        //atras
        out.println("<a href='?pphase=12&p="+ppassword+"&pyear="+pyear+"'><input type='button' value='Atras'></a>");
        out.println("</ul>");
        out.println("</form>");
        out.println("</body>");
        out.println("<br><br><footer>Christian Santos Duarte (2021-2022)</footer>");
        out.println("</html>");
        return;
    }

    /****************************************************************************************************************************
     * 
     *                                                   envio de XML
     * 
     ****************************************************************************************************************************/

    public void enviarXMLF01(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<service>");
        out.println("<status>OK</status>");
        out.println("</service>");
        return;
    }

    public void enviarXMLF11(HttpServletRequest request, HttpServletResponse response, ArrayList<String> ArrayYears) throws IOException{
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<years>");

        for(int i = 0; i<ArrayYears.size(); i++){
            out.println("<year>"+ArrayYears.get(i)+"</year>");
        }

        out.println("</years>");
        return;
    }

    public void enviarXMLF12(HttpServletRequest request, HttpServletResponse response, ArrayList<Movie> ArrayMovies) throws IOException{
        response.setContentType("text/xml");
        String pyear = request.getParameter("pyear");
        PrintWriter out = response.getWriter();
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<movies>");

        for(int i = 0; i<ArrayMovies.size();i++){
            out.println("<movie langs='"+ArrayMovies.get(i).getIdioma()+"' genres='"+String.join(",",ArrayMovies.get(i).getGenero()).trim()
            +"' synopsis='"+ArrayMovies.get(i).getSinopsis()+ "'>" +ArrayMovies.get(i).getTitulo()+"</movie>");
        }

        out.println("</movies>");
        return;
    }

    public void enviarXMLF13(HttpServletRequest request, HttpServletResponse response, ArrayList<Cast> ArrayCast) throws IOException{
        response.setContentType("text/xml");
        String pyear  = request.getParameter("pyear");
        String pmovie = request.getParameter("pmovie");
        PrintWriter out = response.getWriter();
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<thecast>");

        for(int i = 0; i<ArrayCast.size();i++){
            out.println("<cast id='"+ArrayCast.get(i).getID()+"' role='"+ArrayCast.get(i).getPapel()+"' contact='"+ArrayCast.get(i).getContacto()
            +"'>"+ArrayCast.get(i).getName()+"</cast>");
        }

        out.println("</thecast>");
        return;
    }
    

} //llave final
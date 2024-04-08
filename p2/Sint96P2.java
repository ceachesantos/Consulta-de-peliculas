package p2;

import p2.Year;
import p2.Cast;
import p2.Movie;
import p2.DataModel;

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

//javac -cp ~/apache-tomcat/lib/servlet-api.jar *.java

@WebServlet ("/P2M")

public class Sint96P2 extends HttpServlet {

    private static final String PASSWORD = "aprobar123";
    private static final String XMLinicialString = "http://alberto.gil.webs.uvigo.es/SINT/21-22/mml2001.xml";

    static HashMap<String, Document> MapaFicheros = new HashMap<String, Document>();

    static XPath xpath = XPathFactory.newInstance().newXPath();
    FrontEnd frontend = new FrontEnd();
    DataModel datamodel = new DataModel(); //funciones get

    /******************************************************************
     * 
     *                            INIT Y doGET
     * 
     ******************************************************************/

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        try {
            parseXML(context.getResource("/p2/mml.xsd"));
        } catch (Exception e) {
            System.out.println("error en init");
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();

        String ppassword = req.getParameter("p");
        String pphase = req.getParameter("pphase");
        String pyear = req.getParameter("pyear");
        String pmovie = req.getParameter("pmovie");
        String auto = req.getParameter("auto");

        if(auto == null){
            auto = "false";
        }

        if (ppassword == null) {
            if(auto.equals("true")){
                FrontEnd.doGetErrorXML(res, "no passwd");
            }
            else FrontEnd.doGetErrorHTML(res, "No existe contraseña");
        }
            
        else if (!ppassword.equals(PASSWORD)) {
            if(auto.equals("true")){
                FrontEnd.doGetErrorXML(res, "bad passwd");
            }
            else FrontEnd.doGetErrorHTML(res, "Contraseña incorrecta");
        }
        
        else{
            if(pphase == null){
                this.doGetF01(req, res);
            }
            
            else{
                switch (pphase) {
                    case "11":
                        // Pide la pantalla inicial (documento Screens, sección 0.1–S.0.1-), en la que
                        // se presentará
                        // un mensaje de bienvenida y se ofrecerá un botón para iniciar la consulta.
                        // Será el valor por defecto si no hay parámetro pphase.
                        this.doGetF11(req, res);
                        break;

                    case "12":
                        // Se pide la lista del Cast de una película(parámetro pmovie)de un año
                        // (parámetro pyear).
                        // Para cada miembro del Cast se devolverá el nombre, su ID, su papel en la
                        // película,
                        // y su contacto, de acuerdo a lo descrito en el Apéndice A(S.1.3).
                        if (pyear == null) {
                            this.log("12: pyear mal");
                            break;
                        }
                        this.doGetF12(req, res);
                        break;

                    case "13": // Lista de Cast
                        if (pyear == null) {
                            this.log("13: pyear mal");
                            if(auto.equals("true")){
                                FrontEnd.doGetErrorXML(res, "no param:pyear");
                            }
                            else FrontEnd.doGetErrorHTML(res, "No existe pyear");
                            break;
                        }
                        if (pmovie == null) {
                            this.log("13: pmovie mal");
                            if(auto.equals("true")){
                                FrontEnd.doGetErrorXML(res, "no param:pmovie");
                            }
                            else FrontEnd.doGetErrorHTML(res, "No existe pmovie");
                            break;
                        }
                        this.doGetF13(req, res);
                        break;

                    default:
                        this.doGetF01(req, res); // valor por defecto si no hay ninguno
                        break;
                }
            }
        }
    }

    /******************************************************************
     * 
     *                  METODOS DE DECISION HTML/XML
     * 
     ******************************************************************/

    public void doGetF01(HttpServletRequest request, HttpServletResponse response) {
        this.log("doGetF01");
        String auto = request.getParameter("auto");

        if (auto == null) {
            auto = "false";
        }

        if (!(auto.equals("true"))) {

            try {
                frontend.enviarHTMLF01(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            try {
                frontend.enviarXMLF01(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void doGetF11(HttpServletRequest request, HttpServletResponse response) {
        this.log("doGetF11");
        String auto = request.getParameter("auto");
        ArrayList<String> ArrayBienYears = new ArrayList<String>();

        if (auto == null) {
            auto = "false";
        }

        ArrayBienYears = datamodel.getQ1Years();  //recibimos el array bien formado, con lo incorrecto descartado

        if (!(auto.equals("true"))) {

            try {
                frontend.enviarHTMLF11(request, response, ArrayBienYears);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            try {
                frontend.enviarXMLF11(request, response, ArrayBienYears);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void doGetF12(HttpServletRequest request, HttpServletResponse response) {
        this.log("doGetF12");
        String auto = request.getParameter("auto");
        String pyear = request.getParameter("pyear");
        ArrayList<Movie> ArrayBienMovies = new ArrayList<Movie>();

        if (auto == null) {
            auto = "false";
        }

        ArrayBienMovies = datamodel.getQ1Movies(pyear); //recibimos el array bien formado, con lo incorrecto descartado

        if (!(auto.equals("true"))) {

            try {
                frontend.enviarHTMLF12(request, response, ArrayBienMovies);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            try {
                frontend.enviarXMLF12(request, response, ArrayBienMovies);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void doGetF13(HttpServletRequest request, HttpServletResponse response) {
        this.log("doGetF13");
        String auto = request.getParameter("auto");
        String pyear = request.getParameter("pyear");
        String pmovie = request.getParameter("pmovie");
        ArrayList<Cast> ArrayBienCast = new ArrayList<Cast>();

        if (auto == null) {
            auto = "false";
        }

        ArrayBienCast = datamodel.getQ1Cast(pyear, pmovie); //recibimos el array bien formado, con lo incorrecto descartado

        if (!(auto.equals("true"))) {

            try {
                frontend.enviarHTMLF13(request, response, ArrayBienCast);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            try {
                frontend.enviarXMLF13(request, response, ArrayBienCast);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**************************************************************************************************************************************
     * 
     *                                              parser
     * 
     **************************************************************************************************************************************/

    public void parseXML(URL URL_fuente) {
        this.log("------------------------------------");
        this.log("---------------parser---------------");
        this.log("------------------------------------");

        LinkedList<URL> listaSinParsear = new LinkedList<URL>();
        LinkedList<URL> listaParseados = new LinkedList<URL>();

        DocumentBuilderFactory DocBuildFactory = DocumentBuilderFactory.newInstance();

        // Preparamos el schema en el DocumentBuilderFactory:
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;

        try {
            schema = schemaFactory.newSchema(URL_fuente);
        } catch (IllegalArgumentException e1) {
            this.log("IllegalArgumentException");
            e1.printStackTrace();
        } catch (NullPointerException e2) {
            this.log("NullPointerException");
            e2.printStackTrace();
        } catch (Exception e) {
            this.log("Exception");
            e.printStackTrace();
        }
        DocBuildFactory.setSchema(schema);
        this.log("XMLSchema cargado");

        DocumentBuilder DocBuil = null;
        try {
            DocBuil = DocBuildFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DocBuil.setErrorHandler(new XMLParserErrorHandler());

        URL XMLinicial = null;
        try {
            XMLinicial = new URL(XMLinicialString);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //bucle parser
        URLConnection connection = null;
        listaSinParsear.add(XMLinicial);

        while (!listaSinParsear.isEmpty()) {
            Document document = null;
            NodeList NLdocumentos = null;
            String year = null;

            try {
                //añadimos un fichero a la lista de parseados
                connection = listaSinParsear.pop().openConnection();
                document = DocBuil.parse(connection.getInputStream(), connection.getURL().toString());

                year = (String) xpath.evaluate("/Movies/Year", document, XPathConstants.STRING);
                NLdocumentos = (NodeList) xpath.evaluate("/Movies/Movie/Cast/MML", document,
                        XPathConstants.NODESET);

                listaParseados.add(connection.getURL());

            } catch (IOException e) {
                listaParseados.add(connection.getURL());
                continue;

            } catch (SAXException e) {
                listaParseados.add(connection.getURL());
                continue;

            } catch (Exception e) {
                listaParseados.add(connection.getURL());
                continue;

            }
            MapaFicheros.put(year, document);

            //añadimos otros MML
            for (int i = 0; i < NLdocumentos.getLength(); i++) {

                String NodoMML = null;

                try {
                    // Cogemos el primer documento
                    NodoMML = (String) xpath.evaluate("text()", NLdocumentos.item(i), XPathConstants.STRING);

                    if (NodoMML == "" || NodoMML == null) {
                        break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                URL anadido = null;

                try {
                    anadido = new URL(connection.getURL(), NodoMML);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                //si no existe el documento, lo añadimos
                if (!listaParseados.contains(anadido) && (!listaSinParsear.contains(anadido))) {
                    listaSinParsear.add(anadido);
                }
            }
        } // fin del while
    }// fin parser

    public static class XMLParserErrorHandler extends DefaultHandler{

        public XMLParserErrorHandler(){
            super();
        }
    
        public void warning(SAXParseException e) throws SAXException{
            throw(e);
        }
    
        public void error(SAXParseException e) throws SAXException{
            throw(e);
        }
    
        public void fatalError(SAXParseException e) throws SAXException{
            throw(e);
        }

    }

} // llave cierre clase
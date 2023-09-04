package no.nsd.polsys.actions.admin.storting;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.admin.storting.TempDataLogikk;
import no.nsd.polsys.modell.admin.storting.Votering;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;


public class ImportereAlleEmnerAction extends HttpServlet {


    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        //importert fra sak
        Vector sak = new Vector();

        Vector kildelink = new Vector();
        Vector<Vector<String>> emnenavn = new Vector<Vector<String>>();
        Vector saksopplysning_intern_referanse = new Vector();


        Vector emneid = new Vector();

        Vector hovedemneid = new Vector();
        //Vector hovedemneid2 = new Vector();
        String hovedemneid2 =null;
        Vector<String> emne_navn = new Vector<String>();



        String sesjon_id = null;

        Connection conn = null;
        TempDataLogikk logikk = new TempDataLogikk();
        Votering modellvotering = new Votering();

        try {

            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            conn.setAutoCommit(false);



            String sesjon_xml = null;
            if (request.getParameter("sesjon") != null) {
                sesjon_id = request.getParameter("sesjon");
            }


            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new URL("http://data.stortinget.no/eksport/emner" ).openStream());

            NodeList nList_sak = doc.getElementsByTagName("emne");

            for (int s = 0; s < nList_sak.getLength(); s++) {

                Node nNodesak = nList_sak.item(s);
                if (nNodesak.getNodeType() == Node.ELEMENT_NODE) {

                    //Periode og sessjon
                    Element sElement = (Element) nNodesak;


                    //hovedemneid
                    if (sElement.getElementsByTagName("er_hovedemne").item(0).getTextContent().equals("true") && sElement.getElementsByTagName("hovedemne_id").item(0) != null){
                        emneid.add(sElement.getElementsByTagName("hovedemne_id").item(0).getTextContent());
                        hovedemneid.add(0);
                         hovedemneid2 = sElement.getElementsByTagName("hovedemne_id").item(0).getTextContent();

                    }

                    // emnenavnnewww
                    if (sElement.getElementsByTagName("er_hovedemne").item(0).getTextContent().equals("true") && sElement.getElementsByTagName("navn").item(0) != null){
                        emne_navn.add(sElement.getElementsByTagName("navn").item(0).getTextContent());

                    }






                    //emne
                    Element fstElmnt = (Element) nNodesak;
                    NodeList nodeLst_emne_liste = fstElmnt.getElementsByTagName("underemne_liste");
                    Node fstFieldNode_emne_liste = nodeLst_emne_liste.item(0);
                    if (fstFieldNode_emne_liste.getNodeType() == Node.ELEMENT_NODE ) {
                        NodeList nList_emne = fstFieldNode_emne_liste.getChildNodes();
                        for (int i = 0; i < nList_emne.getLength(); i++) {
                            Node eNode = nList_emne.item(i);
                            if (eNode.getNodeType() == Node.ELEMENT_NODE && eNode.getNodeName().equals("emne")) {
                                Element eElement = (Element) eNode;
                                hovedemneid.add(hovedemneid2);


                                if (eElement.getElementsByTagName("hovedemne_id").item(0) != null){
                                    //emneid.add(sElement.getElementsByTagName("hovedemne_id").item(0).getTextContent());
                                }
                                if (eElement.getElementsByTagName("er_hovedemne").item(0).getTextContent().equals("false") && eElement.getElementsByTagName("navn").item(0) != null){
                                    String emne = eElement.getElementsByTagName("navn").item(0).getTextContent();
                                    emne_navn.add(eElement.getElementsByTagName("navn").item(0).getTextContent());
                                    emneid.add(eElement.getElementsByTagName("id").item(0).getTextContent());

                                }
                                else{
                                    //emne_navn.add("ikke_emne");

                                }
                            }
                        }
                        if(emne_navn.size() ==0){emne_navn.add("ikke_spesifisert");}
                    }


                    //sak.add(s);
                    //emnenavn.add(emne_navn);

                }
            }
            //System.out.println("size of sak= " + nList_sak.getLength()  );
            //System.out.println("size of saknr= " + saknr.size());

            logikk.InsertEmne(emneid, emne_navn, hovedemneid);
            conn.commit();





        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ignored) {}
            }
            throw new ServletException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception ignored) {
                conn = null;
            }
        }


        String url = request.getContextPath() + "/storting/votering/lesfil.p?"+"emne_importert";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);

    }

}
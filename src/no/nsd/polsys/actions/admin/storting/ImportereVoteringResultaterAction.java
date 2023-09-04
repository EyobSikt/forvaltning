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
import java.util.List;
import java.util.Vector;


public class ImportereVoteringResultaterAction extends HttpServlet {


	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        Vector representantinitialer = new Vector();
        Vector representantfornavn = new Vector();
        Vector representantetternavn = new Vector();
        Vector representantfodtdato = new Vector();
        Vector representantkjoenn = new Vector();
        Vector representantpartiid = new Vector();
        Vector representantpartinavn = new Vector();

        //votering resultat

        Vector periode_vr = new Vector();
        Vector ses_vr = new Vector();
        Vector dato = new Vector();
        Vector kart_vr = new Vector();
        Vector sak_vr = new Vector();
        Vector votnr_vr = new Vector();
        Vector votering_vr = new Vector();
        Vector representant_personnavn_vr = new Vector();
        Vector representant_parti_vr = new Vector();
        Vector personopplysning_intern_referanse = new Vector();
        Vector votering_text = new Vector();


        String fra_votid = null;
        String til_votid = null;
        String sesjontid = null;

        Connection conn = null;
       TempDataLogikk logikk = new TempDataLogikk();
       Votering modellvotering = new Votering();

        try {

            conn = DatabaseConnectionFactory.getConnection();
            logikk.setConn(conn);
            conn.setAutoCommit(false);

            if (request.getParameter("fravotid") != null) {
                fra_votid = request.getParameter("fravotid");
            }
            if (request.getParameter("tilvotid") != null) {
                til_votid = request.getParameter("tilvotid");
            }
            if (request.getParameter("sesjontid") != null) {
                sesjontid = request.getParameter("sesjontid");
            }


         //
        //votering resultat to be inserted into t_storting_votering_personvotering
        //


            //List<List<Integer>> votid = logikk.voteringIdVoteringresultat();
            List<List<String>> votid = logikk.voteringIdVoteringresultat(sesjontid);

                                       //votid.size()   1376    for towmoorw change to place frok 2 to 3

            if(fra_votid !=null && fra_votid!=null){

            for(int v= Integer.parseInt(fra_votid)-1; v < Integer.parseInt(til_votid); v++ ) {


                DocumentBuilderFactory db_vr= DocumentBuilderFactory.newInstance();
                DocumentBuilder dbvr = db_vr.newDocumentBuilder();
                Document doc3 = dbvr.parse(new URL("http://data.stortinget.no/eksport/voteringsresultat?VoteringId="+    ((int) Float.parseFloat(votid.get(v).get(4))) ).openStream());

                NodeList nList_representant_vr = doc3.getElementsByTagName("representant_voteringsresultat");
                for (int s = 0; s < nList_representant_vr.getLength(); s++) {

                    periode_vr.add(((int) Float.parseFloat(votid.get(v).get(0))) );
                    ses_vr.add(votid.get(v).get(1));
                    kart_vr.add(votid.get(v).get(2));
                    sak_vr.add(votid.get(v).get(3));
                    votnr_vr.add(votid.get(v).get(4));

                    personopplysning_intern_referanse.add(votid.get(v).get(5));

                    Node nNode_voteringresultat = nList_representant_vr.item(s);
                    if (nNode_voteringresultat.getNodeType() == Node.ELEMENT_NODE) {

                        //votering for, mot, ikke tilstedet
                        Element vElement = (Element) nNode_voteringresultat;
                        if (vElement.getElementsByTagName("votering").item(0) != null){
                            String votering =     vElement.getElementsByTagName("votering").item(0).getTextContent();
                            if(votering.equals("for")){ votering_vr.add(1);}
                            if(votering.equals("mot")){ votering_vr.add(2);}
                            if(votering.equals("ikke_tilstede")){ votering_vr.add(3);}
                            votering_text.add(vElement.getElementsByTagName("votering").item(0).getTextContent());
                        }


                        //representant
                        Node nNodesak = nList_representant_vr.item(s);
                        NodeList nList_rep = nNodesak.getChildNodes();
                        for (int j = 0; j < nList_rep.getLength(); j++) {
                            Node kNode = nList_rep.item(j);
                            if (kNode.getNodeType() == Node.ELEMENT_NODE && kNode.getNodeName().equals("representant")) {
                                Element eElement = (Element) kNode;
                                String fornavn = eElement.getElementsByTagName("fornavn").item(0).getTextContent();
                                String etternavn = eElement.getElementsByTagName("etternavn").item(0).getTextContent();
                                String fodtdato = eElement.getElementsByTagName("foedselsdato").item(0).getTextContent();
                                String id = eElement.getElementsByTagName("id").item(0).getTextContent();
                                String fodt = fodtdato.substring(0, 10);
                                representant_personnavn_vr.add(logikk.president(fornavn, etternavn, fodt, id));

                                representantinitialer.add(eElement.getElementsByTagName("id").item(0).getTextContent());
                                representantfornavn.add(eElement.getElementsByTagName("fornavn").item(0).getTextContent());
                                representantetternavn.add(eElement.getElementsByTagName("etternavn").item(0).getTextContent());
                                representantfodtdato.add(eElement.getElementsByTagName("foedselsdato").item(0).getTextContent().substring(0, 10));
                                representantkjoenn.add(eElement.getElementsByTagName("kjoenn").item(0).getTextContent());

                            }
                        }


/*
                     if (vElement.getElementsByTagName("representant").item(0) != null) {
                            String fornavn = vElement.getElementsByTagName("fornavn").item(0).getTextContent();
                            String etternavn = vElement.getElementsByTagName("etternavn").item(0).getTextContent();
                            String fodtdato = vElement.getElementsByTagName("foedselsdato").item(0).getTextContent();
                            String id = vElement.getElementsByTagName("id").item(0).getTextContent();
                            String fodt = fodtdato.substring(0, 10);
                            representant_personnavn_vr.add(logikk.president(fornavn, etternavn, fodt, id));

                            representantinitialer.add(vElement.getElementsByTagName("id").item(0).getTextContent());
                            representantfornavn.add(vElement.getElementsByTagName("fornavn").item(0).getTextContent());
                            representantetternavn.add(vElement.getElementsByTagName("etternavn").item(0).getTextContent());
                            representantfodtdato.add(vElement.getElementsByTagName("foedselsdato").item(0).getTextContent().substring(0, 10));
                            representantkjoenn.add(vElement.getElementsByTagName("kjoenn").item(0).getTextContent());

                                //System.out.print("eeeeeeeeeeeeeeeee = " + fornavn + " - " + etternavn + " ");
                        }
                        */

                        //parti

                        Element representantElmnt = (Element) nNode_voteringresultat;
                        NodeList nodeLst_representant_liste = representantElmnt.getElementsByTagName("representant");
                        Node fstFieldNode_representant_liste = nodeLst_representant_liste.item(0);
                        if (fstFieldNode_representant_liste.getNodeType() == Node.ELEMENT_NODE ) {
                            NodeList nList_representant = fstFieldNode_representant_liste.getChildNodes();
                            for (int i = 0; i < nList_representant.getLength(); i++) {
                                Node reprsentantNode = nList_representant.item(i);
                                if (reprsentantNode.getNodeType() == Node.ELEMENT_NODE && reprsentantNode.getNodeName().equals("parti") ) {
                                    Element reprsentantNodeElement = (Element) reprsentantNode;
                                    if (reprsentantNodeElement.getElementsByTagName("navn").item(0) != null){
                                        String parti = reprsentantNodeElement.getElementsByTagName("navn").item(0).getTextContent();
                                        representant_parti_vr.add(logikk.presidentparti(parti));
                                        representantpartiid.add(reprsentantNodeElement.getElementsByTagName("id").item(0).getTextContent());
                                        representantpartinavn.add(reprsentantNodeElement.getElementsByTagName("navn").item(0).getTextContent());
                                    }
                                }
                            }
                        }
                    }
                }
            }


            logikk.Insert_t_storting_votering_import_personvotering(periode_vr, ses_vr, kart_vr, sak_vr, votnr_vr, votering_vr, votering_text, representant_personnavn_vr,representantinitialer,representantfornavn,representantetternavn,representantfodtdato,representantkjoenn, representant_parti_vr,representantpartiid,representantpartinavn, personopplysning_intern_referanse);
            conn.commit();

            }


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


         String url = request.getContextPath() + "/storting/votering/lesfil.p?"+"antallvotnrlist="+Integer.parseInt(til_votid);
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);

    }

}
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


public class ImportereSakerVoteringerFilAction extends HttpServlet {


    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //importert fra sak
        Vector sak = new Vector();
        Vector sesjon = new Vector();
        Vector periode = new Vector();
        Vector saknr = new Vector();
        Vector typesak = new Vector();
        Vector saksreferanse = new Vector();
        Vector saksregister = new Vector();
        Vector komiteid = new Vector();
        Vector komitenavn = new Vector();
        Vector kildelink = new Vector();
        Vector<Vector<String>> emnenavn = new Vector<Vector<String>>();
        Vector saksopplysning_intern_referanse = new Vector();
        Vector sesjon_text = new Vector();


        //votering
        Vector votnr = new Vector();
        Vector vottype = new Vector();
        //Vector sak_votering = new Vector();
        Vector kartnr_votering = new Vector();
        Vector president = new Vector();
        Vector presidentparti = new Vector();
        Vector typesak_text = new Vector();
        Vector typsakid = new Vector();
        Vector vottype_text = new Vector();
        Vector presidentinitialer = new Vector();
        Vector presidentfornavn = new Vector();
        Vector presidentetternavn = new Vector();
        Vector presidentfodtdato = new Vector();
        Vector presidentkjoenn = new Vector();
        Vector presidentpartiid = new Vector();
        Vector presidentpartinavn = new Vector();

        Vector representantinitialer = new Vector();
        Vector representantfornavn = new Vector();
        Vector representantetternavn = new Vector();
        Vector representantfodtdato = new Vector();
        Vector representantkjoenn = new Vector();
        Vector representantpartiid = new Vector();
        Vector representantpartinavn = new Vector();


        //votering if exists more than one
        Vector sak_mer = new Vector();
        Vector sesjon_mer = new Vector();
        Vector periode_mer = new Vector();
        Vector dato_mer = new Vector();
        Vector saknr_mer = new Vector();
        Vector typesak_mer = new Vector();
        Vector saksreferanse_mer = new Vector();
        Vector saksregister_mer = new Vector();
        Vector komitenr_mer = new Vector();
        Vector komiteid_mer = new Vector();
        Vector komitenavn_mer = new Vector();
        Vector kildelink_mer = new Vector();
        Vector  emnenr_mer = new Vector();
        Vector  emnenavn_mer = new Vector();
        Vector saksopplysning_intern_referanse_mer = new Vector();
        Vector sesjon_text_mer = new Vector();
        Vector internkommentar_mer = new Vector();
        Vector typsakid_mer = new Vector();
        Vector vottypid = new Vector();

        //Vector alt_vottid = new Vector();





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
            Document doc = dBuilder.parse(new URL("http://data.stortinget.no/eksport/saker?sesjonid=" + sesjon_id).openStream());

            NodeList nList_sak = doc.getElementsByTagName("sak");

            for (int s = 0; s < nList_sak.getLength(); s++) {
                Vector<String> emne_navn = new Vector<String>();
                Node nNodesak = nList_sak.item(s);
                if (nNodesak.getNodeType() == Node.ELEMENT_NODE) {

                    //Periode og sessjon
                    Element sElement = (Element) nNodesak;
                    if (sElement.getElementsByTagName("behandlet_sesjon_id").item(0) != null){
                        sesjon_xml =   sElement.getElementsByTagName("behandlet_sesjon_id").item(0).getTextContent();
                        sesjon.add(modellvotering.getSesjon(sElement.getElementsByTagName("behandlet_sesjon_id").item(0).getTextContent()));
                        periode.add(modellvotering.getPeriod(sElement.getElementsByTagName("behandlet_sesjon_id").item(0).getTextContent()));
                        sesjon_text.add(sElement.getElementsByTagName("behandlet_sesjon_id").item(0).getTextContent());
                        //System.out.println(sElement.getElementsByTagName("behandlet_sesjon_id").item(0).getTextContent());
                    }

                    //Dato
                    if (sElement.getElementsByTagName("sist_oppdatert_dato").item(0) != null ){
                        dato.add(sElement.getElementsByTagName("sist_oppdatert_dato").item(0).getTextContent().substring(0,10));
                    }

                    //sak nummer
                    NodeList fileNodes = sElement.getElementsByTagName("id");

                    //if(sElement.equals(fileNodes.item(s).getParentNode())) {
                    //  if (fileNodes.item(s).getNodeType() == Node.ELEMENT_NODE) {

                    //process the child node...
                    //}
                    //}
                    for (int j = 0; j < fileNodes.getLength(); j++) {
                        if (sElement.getElementsByTagName("id").item(j) != null && sElement.getElementsByTagName("id").item(j).getParentNode().getNodeName().equals("sak")) {
                            //System.out.println(sElement.getElementsByTagName("id").item(j).getParentNode().getNodeName());
                            saknr.add(sElement.getElementsByTagName("id").item(j).getTextContent());
                            //System.out.println(sElement.getElementsByTagName("id").item(j).getTextContent());
                        }
                    }

                    //Type sak
                    if (sElement.getElementsByTagName("dokumentgruppe").item(0) != null){
                        typesak_text.add(sElement.getElementsByTagName("dokumentgruppe").item(0).getTextContent());
                        typsakid.add(modellvotering.typeSak(sElement.getElementsByTagName("dokumentgruppe").item(0).getTextContent()));

                    }

                    //saksreferanse
                    if (sElement.getElementsByTagName("tittel").item(0) != null){
                        saksreferanse.add(sElement.getElementsByTagName("tittel").item(0).getTextContent());
                    }

                    //saksregister
                    if (sElement.getElementsByTagName("korttittel").item(0) != null){
                        saksregister.add(sElement.getElementsByTagName("korttittel").item(0).getTextContent());
                    }

                    //komite
                    NodeList nList_komite = nNodesak.getChildNodes();
                    for (int j = 0; j < nList_komite.getLength(); j++) {
                        Node kNode = nList_komite.item(j);
                        if (kNode.getNodeType() == Node.ELEMENT_NODE && kNode.getNodeName().equals("komite")) {
                            Element eElement = (Element) kNode;
                            if (eElement.getElementsByTagName("id").item(0) != null){
                            }
                            if (eElement.getElementsByTagName("navn").item(0) != null){
                                komiteid.add(eElement.getElementsByTagName("id").item(0).getTextContent());
                                komitenavn.add(eElement.getElementsByTagName("navn").item(0).getTextContent());
                            }
                            else{
                                komiteid.add(0);
                                komitenavn.add(null);
                            }
                        }
                    }


                    //emne
                    Element fstElmnt = (Element) nNodesak;
                    NodeList nodeLst_emne_liste = fstElmnt.getElementsByTagName("emne_liste");
                    Node fstFieldNode_emne_liste = nodeLst_emne_liste.item(0);
                    if (fstFieldNode_emne_liste.getNodeType() == Node.ELEMENT_NODE ) {
                        NodeList nList_emne = fstFieldNode_emne_liste.getChildNodes();
                        for (int i = 0; i < nList_emne.getLength(); i++) {
                            Node eNode = nList_emne.item(i);
                            if (eNode.getNodeType() == Node.ELEMENT_NODE && eNode.getNodeName().equals("emne")) {
                                Element eElement = (Element) eNode;
                                if (eElement.getElementsByTagName("id").item(0) != null){
                                }
                                if (eElement.getElementsByTagName("er_hovedemne").item(0).getTextContent().equals("true") && eElement.getElementsByTagName("navn").item(0) != null){
                                    String emne = eElement.getElementsByTagName("navn").item(0).getTextContent();
                                    emne_navn.add(eElement.getElementsByTagName("navn").item(0).getTextContent());
                                }
                                else{
                                    //emne_navn.add("ikke_emne");

                                }
                            }
                        }
                        if(emne_navn.size() ==0){emne_navn.add("ikke_spesifisert");}
                    }

                    //internkommentar og link

                    kildelink.add("http://data.stortinget.no/eksport/saker?sesjonid=" + sesjon_id);
                    saksopplysning_intern_referanse.add(sesjon_id);


                    sak.add(s);
                    emnenavn.add(emne_navn);

                }
            }
            //System.out.println("size of sak= " + nList_sak.getLength()  );
            //System.out.println("size of saknr= " + saknr.size());

            logikk.InsertSaksOpplysning(sak, periode, dato, sesjon, sesjon_text, saknr, typesak_text, saksreferanse, saksregister, komiteid, komitenavn, emnenavn, kildelink, saksopplysning_intern_referanse, typsakid);
            conn.commit();




            //VOTERING STARTER HER... votnr, votering_tema, president, president parti to be inserted into saksopplysninger  .......

            // Vector saksid = logikk.saksIdVotering();

            List<List<String>> saksid = logikk.saksIdVotering(sesjon_id);

            logikk.deleteData(sesjon_id);
            conn.commit();

            for(int v=0; v < saksid.size(); v++ ) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc2 = db.parse(new URL("http://data.stortinget.no/eksport/voteringer?sakid="+     ((int) Float.parseFloat(saksid.get(v).get(4)))    ).openStream());
                NodeList nList_votering = doc2.getElementsByTagName("sak_votering");
                //System.out.print("votlength = " + nList_votering.getLength());
                for (int s = 0; s < nList_votering.getLength(); s++) {


                    Vector<String> emne_navn22 = new Vector<String>();
                    Node nNodevotering = nList_votering.item(s);
                    if (nNodevotering.getNodeType() == Node.ELEMENT_NODE) {

                        //Periode og sessjon
                        Element vElement = (Element) nNodevotering;


                        //This one is if you are taking into consideration only alternative votering but
                        //if (!votnr.contains(vElement.getElementsByTagName("alternativ_votering_id").item(0).getTextContent()) ) {

                            //This one considers bith alternative votering and if samme votering har flere ulkie saker da tar det bare en saknummer.
                            //if (!votnr.contains(vElement.getElementsByTagName("alternativ_votering_id").item(0).getTextContent()) && !votnr.contains(vElement.getElementsByTagName("votering_id").item(0).getTextContent())) {


                            periode_mer.add(saksid.get(v).get(0));
                            dato_mer.add(saksid.get(v).get(1));
                            sesjon_mer.add(saksid.get(v).get(2));
                            sesjon_text_mer.add(saksid.get(v).get(3));
                            saknr_mer.add(saksid.get(v).get(4));
                            typesak_mer.add(saksid.get(v).get(5));
                            komitenr_mer.add(saksid.get(v).get(6));
                            komiteid_mer.add(saksid.get(v).get(7));
                            komitenavn_mer.add(saksid.get(v).get(8));
                            saksreferanse_mer.add(saksid.get(v).get(9));
                            saksregister_mer.add(saksid.get(v).get(10));
                            emnenr_mer.add(saksid.get(v).get(11));
                            emnenavn_mer.add(saksid.get(v).get(12));
                            internkommentar_mer.add(saksid.get(v).get(13));
                            kildelink_mer.add(saksid.get(v).get(14));
                            saksopplysning_intern_referanse_mer.add(saksid.get(v).get(15));

                            typsakid_mer.add(saksid.get(v).get(16));


                            if (vElement.getElementsByTagName("sak_id").item(0) != null) {
                                //if (!votnr.contains(vElement.getElementsByTagName("votering_id").item(0).getTextContent())) {
                                // sak_votering.add(vElement.getElementsByTagName("sak_id").item(0).getTextContent());
                                //System.out.println("sakid = "+ vElement.getElementsByTagName("sak_id").item(0).getTextContent());
                                //}
                            }
                            //String alt_nr = vElement.getElementsByTagName("alternativ_votering_id").item(0).getTextContent();
                            //String vot_nr = vElement.getElementsByTagName("votering_id").item(0).getTextContent();


                            if (vElement.getElementsByTagName("alternativ_votering_id").item(0) != null && vElement.getElementsByTagName("alternativ_votering_id").item(0).getTextContent() != "-1") {
                                //alt_vottid.add(vElement.getElementsByTagName("alternativ_votering_id").item(0).getTextContent());
                            }

                            if (Integer.parseInt(vElement.getElementsByTagName("alternativ_votering_id").item(0).getTextContent()) == -1) {
                                // votnr.add(vElement.getElementsByTagName("votering_id").item(0).getTextContent());
                            }
                            //if (alt_vottid.contains(vElement.getElementsByTagName("votering_id").item(0).getTextContent())){continue;}

                            if (vElement.getElementsByTagName("votering_id").item(0) != null) {
                                //continue;
                                votnr.add(vElement.getElementsByTagName("votering_id").item(0).getTextContent());

                                if(vElement.getElementsByTagName("votering_id").item(0).getTextContent().equals("1500") || vElement.getElementsByTagName("votering_id").item(0).getTextContent().equals("1501")) {
                                    System.out.println("votid222222222222222222 = " + vElement.getElementsByTagName("votering_id").item(0).getTextContent());
                                }

                                //System.out.println("votid = " + vElement.getElementsByTagName("votering_id").item(0).getTextContent());
                                //System.out.println("alternativ votid = " + vElement.getElementsByTagName("alternativ_votering_id").item(0).getTextContent());
                                //System.out.println("votid = " + vElement.getElementsByTagName("votering_id").item(0).getTextContent());
                            }

                            //boolean blnFound = votnr.contains("4432");
                            //System.out.println("Does Vector contain 3 ? " + blnFound);


                            if (vElement.getElementsByTagName("votering_tema").item(0) != null) {
                                vottype_text.add(vElement.getElementsByTagName("votering_tema").item(0).getTextContent());
                                vottypid.add(modellvotering.typeVot(vElement.getElementsByTagName("votering_tema").item(0).getTextContent()));
                                //System.out.println(vElement.getElementsByTagName("votering_tema").item(0).getTextContent());
                            }

                            //kart
                            if (vElement.getElementsByTagName("mote_kart_nummer").item(0) != null) {
                                kartnr_votering.add(vElement.getElementsByTagName("mote_kart_nummer").item(0).getTextContent());
                            }

                            if (vElement.getElementsByTagName("votering_id").item(0) != null) {
                                String fornavn = vElement.getElementsByTagName("fornavn").item(0).getTextContent();
                                String etternavn = vElement.getElementsByTagName("etternavn").item(0).getTextContent();
                                String fodtdato = vElement.getElementsByTagName("foedselsdato").item(0).getTextContent();
                                String id = vElement.getElementsByTagName("id").item(0).getTextContent();
                                String fodt = fodtdato.substring(0, 10);

                                presidentinitialer.add(vElement.getElementsByTagName("id").item(0).getTextContent());
                                presidentfornavn.add(vElement.getElementsByTagName("fornavn").item(0).getTextContent());
                                presidentetternavn.add(vElement.getElementsByTagName("etternavn").item(0).getTextContent());
                                presidentfodtdato.add(vElement.getElementsByTagName("foedselsdato").item(0).getTextContent().substring(0, 10));
                                presidentkjoenn.add(vElement.getElementsByTagName("kjoenn").item(0).getTextContent());

                                president.add(logikk.president(fornavn, etternavn, fodt, id));
                            }

                            //parti

                            Element presidentElmnt = (Element) nNodevotering;
                            NodeList nodeLst_president_liste = presidentElmnt.getElementsByTagName("president");
                            Node fstFieldNode_president_liste = nodeLst_president_liste.item(0);
                            if (fstFieldNode_president_liste.getNodeType() == Node.ELEMENT_NODE) {
                                NodeList nList_president = fstFieldNode_president_liste.getChildNodes();
                                for (int i = 0; i < nList_president.getLength(); i++) {
                                    Node eNode = nList_president.item(i);
                                    if (eNode.getNodeType() == Node.ELEMENT_NODE && eNode.getNodeName().equals("parti")) {
                                        Element eElement = (Element) eNode;
                                        if (eElement.getElementsByTagName("navn").item(0) != null) {
                                            String parti = eElement.getElementsByTagName("navn").item(0).getTextContent();

                                            presidentpartiid.add(eElement.getElementsByTagName("id").item(0).getTextContent());
                                            presidentpartinavn.add(eElement.getElementsByTagName("navn").item(0).getTextContent());

                                            presidentparti.add(logikk.presidentparti(parti));
                                        }
                                    }
                                }
                            }

                            sak_mer.add(s);


                        //}

                        //System.out.println("size of sak_mer= " + sak_mer.size()  );

                    }
                }
            }

            logikk.InsertVoteringplace(sak_mer,  periode_mer, dato_mer,sesjon_mer,sesjon_text_mer, kartnr_votering, saknr_mer,  votnr, typesak_mer, vottype_text, komitenr_mer, komiteid_mer, komitenavn_mer, saksreferanse_mer, saksregister_mer, emnenr_mer, emnenavn_mer, president, presidentinitialer,presidentfornavn,presidentetternavn,presidentfodtdato, presidentkjoenn, presidentparti, presidentpartiid, presidentpartinavn,internkommentar_mer,kildelink_mer, saksopplysning_intern_referanse_mer, typsakid_mer, vottypid);

            conn.commit();


            List<List<String>> votid = logikk.voteringIdVoteringresultat(sesjon_id);

            request.setAttribute("voteringList",votid.size());
            System.out.println("size of ids list22222 :"+votid.size());

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


        String url = request.getContextPath() + "/storting/votering/lesfil.p?"+"fil_lest";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);

    }

}
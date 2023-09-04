package no.nsd.polsys.actions.parti;

import no.nsd.polsys.factories.DatabaseConnectionFactory;
import no.nsd.polsys.logikk.parti.PartiLogikk;
import no.nsd.polsys.modell.parti.Parti;
import no.nsd.polsys.modell.parti.Solr;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Eyob
 * Date: 06.06.11
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
public class SokResultatAction extends HttpServlet {

    public void process (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {

      QueryResponse qr = null;
       QueryResponse qr2 = null;

      List<Solr> resultat = new ArrayList<Solr>();
        int i = 0;
         Object id = null;
       Object partikode = null;
        String sokfelt = "";
        String encoding = request.getCharacterEncoding();
        String[] fq = new String[0];
        StringBuffer cleanString = new StringBuffer();
        SolrQuery query = new SolrQuery();//Search for everything/anything
        String tekstresultat = null;
        String tittel =null;
        String partinavn = null;
        Object doktype = null;
      String doktypenavn = null;
        String aarstall = null;
        Connection conn = null;
        String sortresult =null;
        String sortfacet=null;
        String filename = null;
        String filenameformat = null;
       //Set your default encoding here
      if (null == encoding) {request.setCharacterEncoding("UTF-8");} else {request.setCharacterEncoding(encoding); }

       /*partihistorie sende link til partihistorie side og hente partinavn og dokumentnavn */
      Parti parti = new Parti();
       PartiLogikk sqlkr = new PartiLogikk();
      List<Parti> partihistorie = null;
       List<Parti> partinavnkode = null;
      List<Parti> dokumenttyper = null;
      List<Parti> aarstallkode = null;
      List<Parti> samiskdok = null;
      try {
           conn = DatabaseConnectionFactory.getConnection();
           sqlkr.setConn(conn);
         partihistorie = sqlkr.getAllePartiHistorie();
         request.setAttribute("partihistorie", partihistorie);

         partinavnkode = sqlkr.getAlleParti();
         request.setAttribute("partinavnkode", partinavnkode);

      dokumenttyper = sqlkr.getAlledokumenter();
         request.setAttribute("dokumenttyper", dokumenttyper);

        aarstallkode = sqlkr.getAlleaarstall();
         request.setAttribute("aarstallkode", aarstallkode);

       samiskdok = sqlkr.getSamsikDok();
         request.setAttribute("samiskdok", samiskdok);

        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ignored) {
            }
        }
      /*hente URL parameter*/
       if (request.getParameter("q") != null) {
           sokfelt =parti.escapeQueryChars(request.getParameter("q").toString().trim());
           sokfelt = sokfelt.replaceAll("(\"(?=\\S)[^\"]*(?<=)\")|\"", "$1");
       }
        if (request.getParameterValues("fq") != null) fq = request.getParameterValues("fq");
         if (fq != null)
               for (String item : fq)
                   if (item != null && !item.equals("null") && !item.equals(""))
                       cleanString.append("&fq=").append(item);

          request.setAttribute("url", cleanString.toString());

       /*fjrene alle partier*/
        StringBuffer markpartiurlString = new StringBuffer();
              for (String item : fq){
                   if (item != null && !item.equals("null") && !item.equals("")) {
                if( !parti.substringBefore(item.toString(), ":").equals("partikode")){
                    markpartiurlString.append("&fq=").append(item);
                    }
                }
          }

         /*mark alle partier*/
        StringBuffer partiurlString = new StringBuffer();
          for(int p =0; p< partinavnkode.size(); p++ ){
              partiurlString.append("&fq=partikode:").append(partinavnkode.get(p).getPartikode2().toString());
              for (String item : fq){
                   if (item != null && !item.equals("null") && !item.equals("")) {
                if(item.toString().equals("partikode:"+partinavnkode.get(p).getPartikode2().toString())){
                    partiurlString.delete(partiurlString.indexOf("&fq="+item.toString()), partiurlString.indexOf("&fq="+item.toString()) + "&fq=".length()+item.toString().length());
                    request.setAttribute("minst_en_parti_mark", true);
                    break;
                    }
                }
             }
          }
      request.setAttribute("markpartiurl", markpartiurlString.toString());
      request.setAttribute("partiurl", partiurlString.toString());

      /*fjerne alle doktype*/
        StringBuffer markdoktypeurlString = new StringBuffer();
              for (String item : fq){
                   if (item != null && !item.equals("null") && !item.equals("")) {
               if( !parti.substringBefore(item.toString(), ":").equals("doktype")){

                    markdoktypeurlString.append("&fq=").append(item);
                    }
                }
          }
           /*mark alle doktype*/
           StringBuffer doktypeurlString = new StringBuffer();
          for(int d =0; d< dokumenttyper.size(); d++ ){
              doktypeurlString.append("&fq=doktype:").append(dokumenttyper.get(d).getDoktypekode().toString());
              for (String item : fq){
                   if (item != null && !item.equals("null") && !item.equals("")) {
                if(item.toString().equals("doktype:"+dokumenttyper.get(d).getDoktypekode().toString())){
                    doktypeurlString.delete(doktypeurlString.indexOf("&fq="+item.toString()), doktypeurlString.indexOf("&fq="+item.toString()) + "&fq=".length()+item.toString().length());
              request.setAttribute("minst_en_doktype_mark", true);
                    break;
                    }
                }
          }
          }
    request.setAttribute("markdoktypeurl", markdoktypeurlString.toString());
      request.setAttribute("doktypeurl", doktypeurlString.toString());

        /*fjerne alle arstall*/
        StringBuffer markaarstallurlString = new StringBuffer();
              for (String item : fq){
                   if (item != null && !item.equals("null") && !item.equals("")) {
           if(!parti.substringBefore(item.toString(), ":").equals("aarstall")){
                    markaarstallurlString.append("&fq=").append(item);
                    }
                }
          }
       /*mark alle aarstall*/
             StringBuffer aarstallurlString = new StringBuffer();
               for(int d =0; d< aarstallkode.size(); d++ ){
                   aarstallurlString.append("&fq=aarstall:").append(aarstallkode.get(d).getAarstall().toString());
                   for (String item : fq){
                        if (item != null && !item.equals("null") && !item.equals("")) {
                     if(item.toString().equals("aarstall:"+aarstallkode.get(d).getAarstall().toString())){
                         aarstallurlString.delete(aarstallurlString.indexOf("&fq="+item.toString()), aarstallurlString.indexOf("&fq="+item.toString()) + "&fq=".length()+item.toString().length());
                         request.setAttribute("minst_en_aarstall_mark", true);
                         break;
                         }
                     }
               }
               }
           request.setAttribute("markaarstallurl", markaarstallurlString.toString());
           request.setAttribute("aarstallurl", aarstallurlString.toString());

     if (request.getParameter("sideresultat") != null)
          request.setAttribute("sideresultat",request.getParameter("sideresultat"));

      /*solr server*/
    CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://synne.nsd.lan:8080/solr/partidok/");

      int start = 0;
      int rows = 10;
       if (request.getParameter("start") != null && request.getParameter("start") != ""){try {start = Integer.parseInt(request.getParameter("start"));}catch (Exception e) { RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp"); rd.forward(request, response);return;}} else {start = 0;}
      if (request.getParameter("rows") != null && request.getParameter("rows") != ""){try {rows = Integer.parseInt(request.getParameter("rows"));}catch (Exception e) {RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp"); rd.forward(request, response);return;}} else {rows = 10;}
     if(rows <=0 || start < 0){ RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp");rd.forward(request, response);return;}

       ArrayList<String> list_partinavn = new ArrayList<String>();
      ArrayList<String> list_doktype = new ArrayList<String>();
      ArrayList<String> list_aarstall = new ArrayList<String>();
      String beforecolon;
     String aftercolon;
     StringBuffer sb_partinavn = new StringBuffer();
     StringBuffer sb_doktype = new StringBuffer();
     StringBuffer sb_aarstall = new StringBuffer();
    List<FacetField> facets = null;
      /*   url fq values til arraylist */
           for(int w=0; w< fq.length; w++ ){
           if(fq[w] ==null){}else{
           beforecolon = parti.substringBefore(fq[w], ":");
           aftercolon = parti.substringAfter(fq[w], ":");
            if(beforecolon.equals("partikode") && !aftercolon.equals(null) && !aftercolon.equals("")){try {list_partinavn.add(String.valueOf(Integer.parseInt(aftercolon)));} catch (Exception e) {}}
            if(beforecolon.equals("doktype")&& !aftercolon.equals(null) && !aftercolon.equals("")){try {list_doktype.add(String.valueOf(Integer.parseInt(aftercolon)));} catch (Exception e) {}}
            if(beforecolon.equals("aarstall")&& !aftercolon.equals(null) && !aftercolon.equals("")){try {list_aarstall.add(String.valueOf(Integer.parseInt(aftercolon)));} catch (Exception e) {}}
             }
          }
         /*arryalist til &fq= type */
         if(list_partinavn.size()!=0){
               sb_partinavn.append("{!tag=partikode}partikode:(");
                for(int s=0; s<list_partinavn.size(); s++){
                sb_partinavn.append(list_partinavn.get(s));
                sb_partinavn.append(" ");
         }
              sb_partinavn.append(")");
         }
           if(list_doktype.size()!=0){
             sb_doktype.append("{!tag=doktype}doktype:(");
                for(int d=0; d<list_doktype.size(); d++){
                sb_doktype.append(list_doktype.get(d));
                sb_doktype.append(" ");
               }
              sb_doktype.append(")");
            }
            if(list_aarstall.size()!=0){
               sb_aarstall.append("{!tag=aarstall}aarstall:(");
                for(int a=0; a<list_aarstall.size(); a++){
                sb_aarstall.append(list_aarstall.get(a));
                sb_aarstall.append(" ");
               }
              sb_aarstall.append(")");
            }

      /* for query -resultat list */
     if(sokfelt==null || sokfelt.equals("") || sokfelt.equals("*")){query.setQuery("*:*");}else{query.setQuery(sokfelt);}
     query.setIncludeScore(true);
     query.setFacet(true);
     query.addFacetField("{!ex=partinavn}partinavn");
     query.addFacetField("{!ex=partikode}partikode");
     query.addFacetField("{!ex=doktypenavn}doktypenavn");
     query.addFacetField("{!ex=doktype}doktype");
     query.addFacetField("{!ex=aarstall}aarstall");
     query.setStart(start);
     query.setRows(rows);
     query.setFacetMinCount(0);

     if(list_doktype.size() !=0 && list_partinavn.size()!=0 && list_aarstall.size()!=0 ){
     query.addFilterQuery(sb_partinavn.toString());
       query.addFilterQuery(sb_doktype.toString());
        query.addFilterQuery(sb_aarstall.toString());
      }
      else if(list_partinavn.size()!=0 && list_doktype.size() ==0 && list_aarstall.size()==0) {
     query.addFilterQuery(sb_partinavn.toString());
      }
     else if(list_partinavn.size()==0 && list_doktype.size() !=0 && list_aarstall.size()==0) {
     query.addFilterQuery(sb_doktype.toString());
      }
    else if(list_partinavn.size()==0 && list_doktype.size() ==0 && list_aarstall.size() !=0) {
     query.addFilterQuery(sb_aarstall.toString());
      }
     else if(list_partinavn.size()!=0 && list_doktype.size() !=0 && list_aarstall.size()==0) {
     query.addFilterQuery(sb_partinavn.toString());
      query.addFilterQuery(sb_doktype.toString());
      }
     else if(list_partinavn.size()!=0 && list_doktype.size() ==0 && list_aarstall.size() !=0) {
     query.addFilterQuery(sb_partinavn.toString());
     query.addFilterQuery(sb_aarstall.toString());
      }
      else if(list_partinavn.size()==0 && list_doktype.size() !=0 && list_aarstall.size() !=0) {
     query.addFilterQuery(sb_doktype.toString());
     query.addFilterQuery(sb_aarstall.toString());
     }
      else{
          //query.addFilterQuery(sb.toString()+sb2.toString());
      }
      /*highlight resultat*/
      query.add("hl","true");
      query.add("hl.useFastVectorHighlighter","true");
      query.add("hl.snippets","1");
      query.add("hl.fragsize","300");
     /*sortering resultat*/

     if (request.getParameter("sortresult") != null) {sortresult =request.getParameter("sortresult");}
     if(sortresult !=null && !sortresult.equals("")){
     if(sortresult.equals("aarstallsynkende")){query.setSortField("aarstall", SolrQuery.ORDER.desc);}
     else if(sortresult.equals("aarstallstigende")){query.setSortField("aarstall", SolrQuery.ORDER.asc);}
     else if (sortresult.equals("partinavn") || sortresult.equals("doktypenavn")){query.setSortField(sortresult, SolrQuery.ORDER.asc);}
     }
      else{query.setSortField("aarstall", SolrQuery.ORDER.desc);}

     /*sortering sidebannen*/
    if (request.getParameter("sortfacet") != null) {sortfacet =request.getParameter("sortfacet");}if(sortfacet !=null && !sortfacet.equals("")){query.setFacetSort(sortfacet);} else {sortfacet="index";}

     /*hente fra solr server*/
      try {
          qr = server.query(query);
         facets = qr.getFacetFields();
         } catch (SolrServerException e) {
             e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
      SolrDocumentList sdl = qr.getResults();
      int antalltreff = (int) sdl.getNumFound();
      request.setAttribute("antalltreff", antalltreff);

      ArrayList<Solr> listpartinavn = new ArrayList<Solr>();
      ArrayList<Solr> listdoktype = new ArrayList<Solr>();
      ArrayList<Solr> listaar = new ArrayList<Solr>();

    /**********************************facet data**************************************/
        for(FacetField facet : facets)
            {
                List<FacetField.Count> facetEntries = facet.getValues();
                for(FacetField.Count fcount : facetEntries)
                {
                  if( facet.getName().equals("partikode")  ){
                    Solr p = new Solr();
                   p.setDokumenttall((int) fcount.getCount());
                      p.setPartikode(fcount.getName());
                Object pkode = -1;
                String  pnavn = "";
                 for(int k =0; k< partihistorie.size(); k++ ){
                if(fcount.getName().toString().equals(partihistorie.get(k).getPartilinkkode().toString())){
                pkode = partihistorie.get(k).getPartilinkkode();
                    break;
                    }
                }
                 for(int k =0; k< partinavnkode.size(); k++ ){
                if(fcount.getName().toString().equals(partinavnkode.get(k).getPartikode2().toString())){
                pnavn = partinavnkode.get(k).getPartinavn();
                    break;
                    }
                }
                 p.setPartilinkid(pkode);
                 p.setPartinavn(pnavn.toString());
                 listpartinavn.add(p);
               }
                if( facet.getName().equals("doktype") ){
                   String doktypetall =     fcount.getName()+" "+"("+fcount.getCount()+")";
                   Solr  d = new Solr();
                     d.setDoktypekode(fcount.getName());
                    d.setDoktypetall((int) fcount.getCount());
                   String  doknavn = "";
                     for(int k =0; k< dokumenttyper.size(); k++ ){
                if(fcount.getName().toString().equals(dokumenttyper.get(k).getDoktypekode().toString())){
                doknavn = dokumenttyper.get(k).getDoktypenavn();
                    break;
                    }
                }
                  d.setDoktypenavn(doknavn.toString());
                    listdoktype.add(d);
                  }
                 if( facet.getName().equals("aarstall") ){
                   String aar =     fcount.getName()+" "+"("+fcount.getCount()+")";
                   Solr  a = new Solr();
                     a.setAar_tall(aar.toString());
                    a.setAar(fcount.getName());
                    a.setAartall((int) fcount.getCount());
                    listaar.add(a);
                  }
                }
            }
      /*sort aarsrall i desc og parti ascending*/
      if( sortfacet!=null && !sortfacet.equals("") && sortfacet.equals("index")){Collections.sort(listaar, Solr.COMPARE_BY_AAR); Collections.sort(listpartinavn,  Solr.COMPARE_BY_PARTINAVN); Collections.sort(listdoktype,  Solr.COMPARE_BY_DOKUMENTTYPE);}

        request.setAttribute("solr_partinavn", listpartinavn);
        request.setAttribute("solr_doktype", listdoktype);
        request.setAttribute("solr_aar", listaar);

    /*******************************slutt facet data********************************/

     /*************************hente  indeksert resulatat data********************************************/
      if(sdl.getNumFound()==0){
      }
      else{
		for (SolrDocument doc : sdl) {
			 id = doc.get("id");
            if( doc.get("tittel") !=null){
            tittel =  doc.get("tittel").toString();
            }
             if( doc.get("filename") !=null){
            filename =  doc.get("filename").toString();
            }
             if( doc.get("filenameformat") !=null){
            filenameformat =  doc.get("filenameformat").toString();
            }
           if( doc.get("partinavn") !=null){
            partinavn =  doc.get("partinavn").toString();
             }
             partikode = doc.get("partikode");

             if( doc.get("doktypenavn") !=null){
            doktypenavn =  doc.get("doktypenavn").toString();
             }
            doktype = doc.get("doktype");
             if( doc.get("aarstall") !=null){
            aarstall = doc.get("aarstall").toString();
             }
         tekstresultat =  doc.get("tekst").toString().trim();//.substring(0, 40);

            Object pkode = -1;
            Solr  s = new Solr();
            s.setId(id);
            s.setTittel(tittel);
            s.setFilename(filename);
            s.setFilenameformat(filenameformat);
            s.setPartinavn(partinavn);
            for(int k =0; k< partihistorie.size(); k++ ){
                if(partikode.equals(partihistorie.get(k).getPartilinkkode())){
                pkode = partikode;
                    break;
                }
            }
            String samiskdokref = null;
            for(int k =0; k< samiskdok.size(); k++ ){
                if(id.equals(samiskdok.get(k).getDoktypekode())){
                samiskdokref = samiskdok.get(k).getDoktypenavn();
                    break;
                }
            }
             s.setSamiskdokref(samiskdokref);
            s.setPartikode(pkode);
           s.setDoktypenavn(doktypenavn);
            s.setAarstall(aarstall);

            if (qr.getHighlighting().get(id) != null) {
        List<String> highightSnippets = qr.getHighlighting().get(id).get("tekst");
         if (highightSnippets != null && highightSnippets.size() > 0)
                s.setTekst(highightSnippets.get(0));
                 else s.setTekst(tekstresultat.substring(0,   Math.min(300, tekstresultat.length())));
       }
            resultat.add(s);
        }
        request.setAttribute("solr", resultat);
      }

      /**********************slutt hente  indeksert resulatat data*******************************/

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/parti/sokresultat.jsp");
        rd.forward(request, response);
  }

}

package no.nsd.polsys.tags.forvaltning;

import no.nsd.polsys.logikk.Util;
import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Tallgruppe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author et
 */
public class EnhetregisteretHierarkiTag extends SimpleTagSupport {

    // -------------------------------------------------------------- Variabler

    private Enhet rot;
    private Integer idnum;

    private boolean engelsk = false;

    private boolean forvaltning = false;


    // --------------------------------------------------------- setter metoder

    public void setIdnum(Integer idnum) {
        this.idnum = idnum;
    }

    public void setRot(Enhet rot) {
        this.rot = rot;
    }


    /**
     * Skriver valgresultatet.
     *
     * @throws IOException
     */
    @Override
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) this.getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        JspWriter out = this.getJspContext().getOut();
        String urlPrefix = request.getContextPath();

        if (request.getAttribute("en") != null) {
            this.engelsk = true;
            urlPrefix += "/en";
        }

        if (this.rot == null) {
            return;
        }
        Integer idnum = null;
        String url = (String) request.getAttribute("javax.servlet.forward.request_uri");
        idnum = Util.getId(url);
        out.print("<div id=\"enhethierarki\">");
        out.print("<ul>");
        this.skrivEnhet(this.rot, out, urlPrefix, idnum);
        out.print("</ul>");

        out.print("</div>");

    }

    private void skrivEnhet(Enhet enhet, JspWriter out, String urlPrefix, Integer idnum) throws IOException {
        Set<Enhet> s = enhet.getUnderordnet();
        Set<Tallgruppe> t = enhet.getTallgrupper();

        String idstring = "";

        if ((s != null && s.size() > 0) || (t != null && t.size() > 0)) {
            idstring = "hideid" + enhet.getIdnum();
        }

        if (enhet.getTilknytningsform() != null) {
            if (idnum == null) {
                out.print("<li class=\"hidearea\"" + (idstring.length() != 0 ? "id=\"" + idstring + "area\"" : "") + ">");
            } else {
                out.print("<li class=\"showarea\"" + (idstring.length() != 0 ? "id=\"" + idstring + "area\"" : "") + ">");
            }
            if (idstring.length() != 0) {
                out.print("<a class=\"hidelink\" id=\"" + idstring + "\" href=\"javascript:onClick=showHide('" + idstring + "');\">&nbsp;</a>");
            } else {
                out.print("<span>&bull;</span>");
            }
        } else {
            out.print("<li>");
            out.print("<span>&bull;</span>");
        }


        if (this.idnum != null && this.idnum.equals(enhet.getForvaltningsidnum())) {
            out.print("<strong>");
        }

        if (enhet.getForvaltningsidnum() == null) {
            out.print(this.escapeXml(enhet.getLangtNavn()));
            out.print(" (" + "<a target=\"_blank\" class=\"" + this.getCssClassName(enhet) +  "\" href=\"" + urlPrefix + "/data/organisasjon/" + enhet.getIdnum() + "?aar=" + enhet.getAar() +  "\">" +  enhet.getIdnum() + "</a>" + ")");
        }
        else {
            out.print("<a class=\"" + this.getCssClassName(enhet) + "\" href=\"" + urlPrefix + "/data/enhet/" + enhet.getForvaltningsidnum() + "/enhetsregisterethierarki?aar=" + enhet.getAar() + "\">" + this.escapeXml(enhet.getLangtNavn()) + "</a>");
            out.print(" (" + "<a target=\"_blank\" class=\"" + this.getCssClassName(enhet) +  "\" href=\"" + urlPrefix + "/data/organisasjon/" + enhet.getIdnum() + "?aar=" + enhet.getAar() +  "\">" +  enhet.getIdnum() + "</a>" + ")");
        }

        if (this.idnum != null && this.idnum.equals(enhet.getForvaltningsidnum())) {
            out.print("</strong>");
        }

        if ((s != null && s.size() > 0) || (t != null && t.size() > 0)) {
            //out.print("<ul>");
            out.print("<ul class=\"" + idstring  + "\"" +  ">");
            if (t != null) {
                Iterator<Tallgruppe> i = t.iterator();
                while (i.hasNext()) {
                    Tallgruppe neste = i.next();
                    if (neste.isTallgruppe()) {
                        String url = urlPrefix + "/data/enhet/" + enhet.getForvaltningsidnum() +  "/enhetsregisterethierarki?aar=" + enhet.getAar() + "/desentralisert" + (neste.getLpnr() != null ? "#" + neste.getLpnr() : "");
                        out.print("<li><span>&bull;</span><em><a class=\"" + this.getCssClassName(enhet) +  "\" href=\"" + url + "\">" + neste.getAntallEnheter() + " "
                                + ((neste.getEnheterKartlagt() != null && neste.getEnheterKartlagt().equals(2)) ? "(est.) " : "")
                                + this.escapeXml((this.engelsk ? neste.getEngelskNavn() : neste.getNavn())) + "</a></em></li>");
                    } else {
                        String url = urlPrefix + "/data/enhet/" + enhet.getForvaltningsidnum() +  "/enhetsregisterethierarki?aar=" + enhet.getAar() + "/avdeling";
                        out.print("<li><span>&bull;</span><a class=\"" + this.getCssClassName(enhet) +  "\" href=\"" + url + "\">" + this.escapeXml((this.engelsk ? neste.getEngelskNavn() : neste.getNavn())) + "</a></li>");
                    }
                }
            }
            if (s != null) {
                boolean selskap = false;
                boolean stiftelse = false;

                Iterator<Enhet> i = s.iterator();
                while (i.hasNext()) {
                    Enhet next = i.next();
                    if (next.getTilknytningsform() != null) {
                        int tilkn = next.getTilknytningsform() / 10;

                        if ((next.getTilknytningsform().equals(15) || tilkn == 2 || tilkn == 3) && !forvaltning) {
                            forvaltning = true;
                            out.print("<span class=\"overskrift\">" + (this.engelsk ? "Subordinate units:" : "Underliggende forvaltningsorgan:") + "</span>");
                        }
                        if (tilkn == 4 && !selskap) {
                            selskap = true;
                            out.print("<span class=\"overskrift\">" + (this.engelsk ? "Affiliated companies:" : "Tilknyttede selskap og foretak:") + "</span>");
                        }
                        if (tilkn == 5 && !stiftelse) {
                            stiftelse = true;
                            out.print("<span class=\"overskrift\">" + (this.engelsk ? "Affiliated foundations:" : "Tilknyttede stiftelser:") + "</span>");
                        }

                    }
                    this.skrivEnhet(next, out, urlPrefix, idnum);
                }
            }
            out.print("</ul>");
        }

        out.print("</li>");
    }


    private String getCssClassName(Enhet e) {
        int til = (e.getTilknytningsform() != null ? e.getTilknytningsform().intValue() : 0);
        int grunn = (e.getGrunnenhet() != null ? e.getGrunnenhet().intValue() : 0);

        if (grunn == 10 || grunn == 20) {
            return "gruppe";
        }
        if (til == 15 || til == 20 || til/10 == 3) {
            return "forvaltning";
        }
        if (til/10 == 4) {
            return "selskap";
        }
        if (til/10 == 5) {
            return "stiftelse";
        }

        return "dep";
    }


    private String escapeXml(String s) {
        if (s == null) return null;
        s = s.replaceAll("&", "&#38;");
        s = s.replaceAll("\"", "&#34;");
        s = s.replaceAll("'", "&#39;");
        s = s.replaceAll("<", "&#60;");
        s = s.replaceAll(">", "&#62;");
        return s;
    }

}


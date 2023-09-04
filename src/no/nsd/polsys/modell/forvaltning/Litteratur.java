package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;
import java.util.Date;

/**
 * Klassen representerer publikasjon.
 *
 * @author hvb
 */
public class Litteratur implements Serializable, Comparable<Litteratur> {

   // ============================================================== Variabler
   /**
    * id til denne publikasjonen.
    */
   private Integer pubId;
   private String tittel;
   private String forfatter;
   private String aar;
   private DokCache type;
   private String utgiver;
   private String lenkePublikasjon;
   private String lenkeOmtale;
   private String kommentarEkstern;
   private String kommentarIntern;
   private Date sisteRegTid;
   private Integer antallSider;
   private String isbn;
   private String issn;
   private String spraak;
   private String land;
   private String sammendrag;
   /**
    * Dette tekstfeltet brukes for utenlandske studier.
    */
   private String tekstfeltUtl;
   /**
    * Referanse til hovedverket, hvis denne litteratur er et kapittel
    */
   private Litteratur referanse;
   /**
    * Enhet som denne publikasjonen omhandler.
    */
   private Enhet enhet;
   /**
    * Tilknytningsform som denne publikasjonen omhandler.
    */
   private DokCache tilknytningsform;
   /**
    * Når enheten omhandler denne publikasjonen fra.
    */
   private Integer fraAar;
   /**
    * Når enheten omhandler denne publikasjonen til.
    */
   private Integer tilAar;


   // ================================================================ Metoder
   public String getAar() {
      return aar;
   }

   public void setAar(String aar) {
      this.aar = aar;
   }

   public String getForfatter() {
      return forfatter;
   }

   public void setForfatter(String forfatter) {
      this.forfatter = forfatter;
   }

   public Integer getPubId() {
      return pubId;
   }

   public void setPubId(Integer pubId) {
      this.pubId = pubId;
   }

   public String getTittel() {
      return tittel;
   }

   public void setTittel(String tittel) {
      this.tittel = tittel;
   }

   public DokCache getType() {
      return type;
   }

   public void setType(DokCache type) {
      this.type = type;
   }

   public Integer getAntallSider() {
      return antallSider;
   }

   public void setAntallSider(Integer antallSider) {
      this.antallSider = antallSider;
   }

   public String getIsbn() {
      return isbn;
   }

   public void setIsbn(String isbn) {
      this.isbn = isbn;
   }

   public String getIssn() {
      return issn;
   }

   public void setIssn(String issn) {
      this.issn = issn;
   }

   public String getKommentarEkstern() {
      return kommentarEkstern;
   }

   public void setKommentarEkstern(String kommentarEkstern) {
      this.kommentarEkstern = kommentarEkstern;
   }

   public String getLand() {
      return land;
   }

   public void setLand(String land) {
      this.land = land;
   }

   public String getLenkeOmtale() {
      return lenkeOmtale;
   }

   public void setLenkeOmtale(String lenkeOmtale) {
      this.lenkeOmtale = lenkeOmtale;
   }

   public String getLenkePublikasjon() {
      return lenkePublikasjon;
   }

   public void setLenkePublikasjon(String lenkePublikasjon) {
      this.lenkePublikasjon = lenkePublikasjon;
   }

   public String getSammendrag() {
      return sammendrag;
   }

   public void setSammendrag(String sammendrag) {
      this.sammendrag = sammendrag;
   }

   public Date getSisteRegTid() {
      return sisteRegTid;
   }

   public void setSisteRegTid(Date sisteRegTid) {
      this.sisteRegTid = sisteRegTid;
   }

   public String getSpraak() {
      return spraak;
   }

   public void setSpraak(String spraak) {
      this.spraak = spraak;
   }

   public String getTekstfeltUtl() {
      return tekstfeltUtl;
   }

   public void setTekstfeltUtl(String tekstfeltUtl) {
      this.tekstfeltUtl = tekstfeltUtl;
   }

   public String getUtgiver() {
      return utgiver;
   }

   public void setUtgiver(String utgiver) {
      this.utgiver = utgiver;
   }

   public String getKommentarIntern() {
      return kommentarIntern;
   }

   public void setKommentarIntern(String kommentarIntern) {
      this.kommentarIntern = kommentarIntern;
   }

   public Litteratur getReferanse() {
      return referanse;
   }

   public void setReferanse(Litteratur referanse) {
      this.referanse = referanse;
   }

   public Integer getFraAar() {
      return fraAar;
   }

   public void setFraAar(Integer fraAar) {
      this.fraAar = fraAar;
   }

   public Integer getTilAar() {
      return tilAar;
   }

   public void setTilAar(Integer tilAar) {
      this.tilAar = tilAar;
   }

   public Enhet getEnhet() {
      return enhet;
   }

   public void setEnhet(Enhet enhet) {
      this.enhet = enhet;
   }

   public DokCache getTilknytningsform() {
      return tilknytningsform;
   }

   public void setTilknytningsform(DokCache tilknytningsform) {
      this.tilknytningsform = tilknytningsform;
   }

   // Overskrevne metoder
   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      Litteratur other = (Litteratur) obj;
      if (this.pubId == null || !this.pubId.equals(other.pubId)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      return (this.pubId != null ? this.pubId.hashCode() : 7);
   }

   @Override
   public String toString() {
      return (this.tittel != null ? this.tittel : "");
   }

   public int compareTo(Litteratur o) {
      if (o == null) {
         return -1;
      }
      if (o.tittel == null) {
         return -1;
      }
      if (this.tittel == null) {
         return 1;
      }
      int i = this.tittel.compareToIgnoreCase(o.tittel);
      if (i != 0) {
         return i;
      }
      if (o.pubId == null) {
         return -1;
      }
      if (this.pubId == null) {
         return 1;
      }
      return this.pubId.compareTo(o.pubId);
   }
}

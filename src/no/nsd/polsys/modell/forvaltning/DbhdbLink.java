package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;

/**
 * Klassen representerer en årsmelding.
 *
 * @author hvb
 */
public final class DbhdbLink implements Serializable, Comparable<DbhdbLink> {

   // ============================================================== Variabler
   /**
    * id til denne årsmeldingen.
    */
   private Integer id;
   /**
    * idnum til enheten denne årsmeldingen gjelder.
    */
   private Integer idnum;
   private String dbh_instkode;
   private Integer aar;
   private String url;
   private String kommentar;


   // ================================================================ Metoder
   public Integer getAar() {
      return aar;
   }

   public void setAar(Integer aar) {
      this.aar = aar;
   }

   public String getDbh_instkode() {
      return dbh_instkode;
   }

   public void setDbh_instkode(String dbh_instkode) {
      this.dbh_instkode = dbh_instkode;
   }

   public String getKommentar() {
      return kommentar;
   }

   public void setKommentar(String kommentar) {
      this.kommentar = kommentar;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getIdnum() {
      return idnum;
   }

   public void setIdnum(Integer idnum) {
      this.idnum = idnum;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (!(obj instanceof DbhdbLink)) {
         return false;
      }
      final DbhdbLink other = (DbhdbLink) obj;
      return (this.id != null ? this.id.equals(other.id) : false);
   }

   @Override
   public int hashCode() {
      return (this.id != null ? this.id.hashCode() : 7);
   }

   @Override
   public String toString() {
      return (this.aar != null ? this.aar.toString() : "");
   }

   public int compareTo(DbhdbLink o) {
      if (o == null) {
         return -1;
      }
      if (o.aar == null) {
         return -1;
      }
      if (this.aar == null) {
         return 1;
      }
      int i = this.aar.compareTo(o.aar);
      if (i != 0) {
         return i;
      }
      if (o.id == null) {
         return -1;
      }
      if (this.id == null) {
         return 1;
      }
      return this.id.compareTo(o.id);
   }
}

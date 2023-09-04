package no.nsd.polsys.modell.forvaltning;

import java.io.Serializable;
import java.util.Date;

/**
 * Klassen representerer en orgprinsipp.
 */
public final class OrgPrinsipp implements Serializable, Comparable<OrgPrinsipp> {

   // ============================================================== Variabler
   /**
    * id til denne instruksen.
    */
   private Integer id;
   /**
    * idnum til enheten denne instruksen gjelder.
    */
   private Integer idnum;
   private Date fratidspunkt;
   private Date tiltidspunkt;
  private Integer orgprinsippId;
    private String norskOrgPrinsipp;
    private String engelskOrgPrinsipp;
    private String kommentar;



   // ================================================================ Metoder
   public Date getFraTidspunkt() {
      return fratidspunkt;
   }

   public void setFraTidspunkt(Date fratidspunkt) {
      this.fratidspunkt = fratidspunkt;
   }

   public Date getTilTidspunkt() {
      return tiltidspunkt;
   }

   public void setTilTidspunkt(Date tiltidspunkt) {
      this.tiltidspunkt = tiltidspunkt;
   }

   public Integer getOrgprinsippId() {
      return orgprinsippId;
   }

   public void setOrgprinsippId(Integer orgprinsippId) {
      this.orgprinsippId = orgprinsippId;
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


    public String getNorskOrgPrinsipp() {
        return norskOrgPrinsipp;
    }

    public void setNorskOrgPrinsipp(String norskOrgPrinsipp) {
        this.norskOrgPrinsipp = norskOrgPrinsipp;
    }

    public String getEngelskOrgPrinsipp() {
        return engelskOrgPrinsipp;
    }

    public void setEngelskOrgPrinsipp(String engelskOrgPrinsipp) {
        this.engelskOrgPrinsipp = engelskOrgPrinsipp;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }



   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (!(obj instanceof OrgPrinsipp)) {
         return false;
      }
      final OrgPrinsipp other = (OrgPrinsipp) obj;
      return (this.id != null ? this.id.equals(other.id) : false);
   }

   @Override
   public int hashCode() {
      return (this.id != null ? this.id.hashCode() : 7);
   }

   @Override
 /*  public String toString() {
      return (this.fraaar != null ? this.fraaar.toString() : "");
   }*/

   public int compareTo(OrgPrinsipp o) {
      if (o == null) {
         return -1;
      }
      if (o.fratidspunkt == null) {
         return -1;
      }
      if (this.fratidspunkt == null) {
         return 1;
      }
      int i = this.fratidspunkt.compareTo(o.fratidspunkt);
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

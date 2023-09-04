package no.nsd.polsys.logikk;

/**
 * Utility class for polsys.
 * @author hvb
 */
public final class Util {

   
   private Util() {
   }
   
   
   /**
    * Hvis s er den tomme stengen (eller null) returneres null, ellers
    * returneres s.
    *
    * @param s
    * @return
    */
   public static String tomStrengTilNull(String s) {
      if (s == null || s.length() == 0) {
         return null;
      }
      return s;
   }

   /**
    * Konverterer en ikke-null og ikke-tom streng til int. Metoden kaster
    * exception.
    *
    * @param s
    * @return
    */
   public static Integer strengTilInteger(String s) {
      if (s == null || s.length() == 0) {
         return null;
      }
      return new Integer(s);
   }

   /**
    * Returnerer en fargekode for gitt i.
    *
    * @param i
    * @return
    */
   public static String getFarge(int i) {
      String[] farger = {
         "000000",
         "FF0000",
         "00FF00",
         "0000FF",
         "FFFF00",
         "FF00FF",
         "00FFFF",
         "AAAAAA",
         "AA0000",
         "00AA00",
         "0000AA",
         "AAAA00",
         "AA00AA",
         "00AAAA"};

      int index = i % farger.length;
      return farger[index];
   }

   /**
    * Koder om streng til bruk i url.
    *
    * @param s
    * @return
    */
   public static String encodeString(String s) {
      s = s.replace("&", "&amp;");
      s = s.replace(" ", "+");
      s = s.replace("æ", "&aelig;");
      s = s.replace("Æ", "&AElig;");
      s = s.replace("ø", "&oslash;");
      s = s.replace("Ø", "&Oslash;");
      s = s.replace("å", "&aring;");
      s = s.replace("Å", "&Aring;");
      return s;
   }

   /**
    * Returnerer (første) id-delen av url'ene som Integer.
    *
    * @param url
    * @return
    */
   public static Integer getId(String url) {
      String[] split = url.split("/");
      for (String s : split) {
         try {
            return new Integer(s);
         } catch (Exception e) {
         }
      }
      return null;
   }

   /**
    * Returns an uri without the ';sessionid=ABC...' appended.
    *
    * @param uri the sting containing the uri.
    * @return
    */
   public static String getUriWithoutJsessionId(String uri) {
      if (uri == null) {
         return null;
      }
      int i = uri.indexOf(';');
      if (i == -1) {
         return uri;
      }
      return uri.substring(0, i);
   }
}

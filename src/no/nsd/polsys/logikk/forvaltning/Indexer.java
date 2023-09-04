/*
 * Indexer.java
  */

package no.nsd.polsys.logikk.forvaltning;

import java.io.IOException;
import java.io.StringReader;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import no.nsd.polsys.modell.forvaltning.Enhet;
import no.nsd.polsys.modell.forvaltning.Enhetsregisteret;
import no.nsd.polsys.modell.forvaltning.Tildelingsbrev;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field; 
import org.apache.lucene.document.StringField; 
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import java.sql.Date;

import javax.servlet.http.HttpServlet;


/**
 *
 * @author Eyob
 */
public class Indexer extends HttpServlet {

    /** Creates a new instance of Indexer */
    public Indexer() {
    }
 
    private IndexWriter indexWriter = null;
    //private static final String DESTINATION_DIR_PATH ="\\\\129.177.90.23\\Tomcat-polsyswebapp\\webapps\\filer\\indeksertenheter";
    private static final String DESTINATION_DIR_PATH ="../polsys/indeksertenheter";
    public static final File INDEX_DIRECTORY = new File(DESTINATION_DIR_PATH);
    public static Integer totalNummerHits = 0;

    public IndexWriter getIndexWriter(boolean create) throws IOException {

        INDEX_DIRECTORY.getCanonicalFile().delete();

        if (indexWriter == null) {

            //String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
            Analyzer analyzer = new StandardAnalyzer();

            // Store the index in memory:
            //Directory indexDir = new RAMDirectory();
            // To store an index on disk, use this instead:
            //Directory directory = FSDirectory.open("/tmp/testindex");

            Path path = Paths.get(String.valueOf(INDEX_DIRECTORY));

            //Directory directory = new SimpleFSDirectory(INDEX_DIRECTORY);

            Directory indexDir = new SimpleFSDirectory(path);
            IndexWriterConfig config = new IndexWriterConfig(analyzer).setOpenMode(IndexWriterConfig.OpenMode.CREATE);
           indexWriter = new IndexWriter(indexDir, config);
        }
        return indexWriter;
   }    
   
    public void closeIndexWriter() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
   }
    
    public void indexEnhet(Enhetsregisteret enhetsregisteret ) throws IOException {
        IndexWriter writer = getIndexWriter(false);
        Document doc = new Document();

        doc.add(new StringField("topLevel", String.valueOf(enhetsregisteret.getTopLevel()), Field.Store.YES));

        doc.add(new StringField("level_1", String.valueOf(enhetsregisteret.getLevel_1()), Field.Store.YES));
         doc.add(new StringField("level_2", String.valueOf(enhetsregisteret.getLevel_2()), Field.Store.YES));
        doc.add(new StringField("level_3", String.valueOf(enhetsregisteret.getLevel_3()), Field.Store.YES));
        doc.add(new StringField("level_4", String.valueOf(enhetsregisteret.getLevel_4()), Field.Store.YES));

        doc.add(new StringField("idnum", String.valueOf(enhetsregisteret.getIdnum()), Field.Store.YES));
        doc.add(new StringField("navn", enhetsregisteret.getNavn(), Field.Store.YES));

        doc.add(new StringField("overordnetEnhet", String.valueOf(enhetsregisteret.getOverordnetEnhet()), Field.Store.YES));

        Calendar myCal = Calendar.getInstance();
        myCal.setTime(enhetsregisteret.getAar());
        doc.add(new StringField("aar", String.valueOf(myCal.get(Calendar.YEAR)), Field.Store.YES));

        doc.add(new StringField("sst_antall_menn", String.valueOf(enhetsregisteret.getAntallAnsatte_menn()), Field.Store.YES));
        doc.add(new StringField("sst_antall_kvinner", String.valueOf(enhetsregisteret.getAntallAnsatte_kvinner()), Field.Store.YES));
        doc.add(new StringField("organisasjonsform", enhetsregisteret.getOrganisasjonsform(), Field.Store.YES));
        doc.add(new StringField("sektorkode", String.valueOf(enhetsregisteret.getSektorkode()), Field.Store.YES));
        doc.add(new StringField("sektorkode_besk", String.valueOf(enhetsregisteret.getSektorkode_besk()), Field.Store.YES));

        doc.add(new StringField("naringskode", enhetsregisteret.getNaringskode(), Field.Store.YES));
        doc.add(new StringField("naringskode_besk", enhetsregisteret.getNaringskode_besk(), Field.Store.YES));
        doc.add(new StringField("nsd_idnum", String.valueOf(enhetsregisteret.getNsd_idnum()), Field.Store.YES));
        doc.add(new StringField("forretningskommune", String.valueOf(enhetsregisteret.getForretningskommune()), Field.Store.YES));
        String fullSearchableText = enhetsregisteret.getNavn() + " " + enhetsregisteret.getForretningskommune();
        doc.add(new TextField("content", fullSearchableText, Field.Store.NO));

        writer.addDocument(doc);
    }   

    public void rebuildIndexes(Connection conn) throws IOException, SQLException {
          //
          // Erase existing index
          //
          getIndexWriter(true);
          //
          // Index all Accommodation entries
          //
        Enhetsregisteret[] enheter = getalleData(conn);
          for(Enhetsregisteret enhet : enheter) {
              indexEnhet(enhet);
          }
          //
          // Don't forget to close the index writer when done
          //
          closeIndexWriter();
     }



    public static Enhetsregisteret[] getalleData(Connection conn) throws SQLException {
        ResultSet rs = null;
        Statement stmt = null;
        String sqlSelect = "select * from t_forvaltning_bronnoysund_enhethierarki ";
        int i = 1;
        Enhetsregisteret[] Enheter3 = null;
        ArrayList <Enhetsregisteret> result = new ArrayList<Enhetsregisteret>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlSelect);
            while (rs.next()) {
                /*
                Enhetsregisteret www = new Enhetsregisteret(
                        (Integer) rs.getObject("idnum"), (String) rs.getObject("level_1_navn"),(String) rs.getObject("level_2_navn"),(String) rs.getObject("level_3_navn"),(String) rs.getObject("level_4_navn") ,
                         (Integer) rs.getObject("level_1"), (String) rs.getObject("navn"),

                         (Integer) rs.getObject("overordnetEnhet"),
                        (java.sql.Timestamp) rs.getObject("registreringsdato") ,(Integer) rs.getObject("sst_antall_menn"),(Integer) rs.getObject("sst_antall_kvinner"),
                        (String) rs.getObject("organisasjonsform"), (String) rs.getObject("institusjonellsektorkode_kode"), (String) rs.getObject("institusjonellsektorkode_beskrivelse")
                        , (String) rs.getObject("naringskode_1"),(String) rs.getObject("naringskode_1_beskrivelse"), (Integer) rs.getObject("forvaltningsidnum"), (String) rs.getObject("forretningsadresse_kommune")



                );
                result.add(www);
                */
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
        }
        conn.close();
        totalNummerHits = result.size();
        Enheter3 = (Enhetsregisteret[]) result.toArray(new Enhetsregisteret[result.size()]);
        return Enheter3;
    }

}

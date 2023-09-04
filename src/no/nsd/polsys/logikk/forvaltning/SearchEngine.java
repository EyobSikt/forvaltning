/*
 * SearchEngine.java
 *
  */

package no.nsd.polsys.logikk.forvaltning;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import javax.servlet.http.HttpServlet;

public class SearchEngine extends HttpServlet {
    private IndexSearcher searcher = null;
    private QueryParser parser = null;
    private QueryParser categoryParser  = null;

    //private static final String DESTINATION_DIR_PATH ="\\\\129.177.90.23\\Tomcat-polsyswebapp\\webapps\\filer\\indeksertenheter";
    private static final String DESTINATION_DIR_PATH ="../polsys/indeksertenheter";
    public static final File INDEX_DIRECTORY = new File(DESTINATION_DIR_PATH);

    /** Creates a new instance of SearchEngine */
    public SearchEngine() throws IOException {

        //String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
        Path path = Paths.get(String.valueOf(INDEX_DIRECTORY));
        searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(path)));
        parser = new QueryParser("content", new StandardAnalyzer());
        categoryParser = new QueryParser("idnum", new StandardAnalyzer());
    }
    
    public TopDocs performSearch(String queryString, StringBuffer idnum , int n)
    throws IOException, ParseException {
        Query query = parser.parse(queryString);
            Query query2 = null;
        if(idnum !=null || !idnum.equals(null) || String.valueOf(idnum) !="" || !idnum.equals("") || !String.valueOf(idnum).equals("")) {
            query2 = categoryParser.parse(String.valueOf(idnum).trim());
        }
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        booleanQuery.add(query, BooleanClause.Occur.MUST);
        if(query2 !=null || !query2.equals(null)) {
            booleanQuery.add(query2, BooleanClause.Occur.FILTER);
        }
        return  searcher.search(booleanQuery.build(), n);
    }

    public Document getDocument(int docId)
    throws IOException {
        return searcher.doc(docId);
    }
}

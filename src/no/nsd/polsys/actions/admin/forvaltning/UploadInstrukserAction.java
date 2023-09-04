package no.nsd.polsys.actions.admin.forvaltning;

// Import required java libraries

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;


public class UploadInstrukserAction extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 1024*1024*1024;
    private int maxMemSize = 8 * 1024;
    private File file ;

     public void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter( );

        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);

        // Location to save data that is larger than maxMemSize.
        //factory.setRepository(new File("c:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            while ( i.hasNext () ) {
                FileItem fi = (FileItem)i.next();
                if ( !fi.isFormField () ) {
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    // Write the file
                    File fileExist;
                    if( fileName.lastIndexOf("\\") >= 0 ) {
                        fileExist = new File( "/data/tomcat9/webapps/instrukser/" + fileName.substring( fileName.lastIndexOf("\\"))) ;
                        if(fileExist.exists()){
                            fileExist.delete();
                        }
                        file = new File( "/data/tomcat9/webapps/instrukser/" + fileName.substring( fileName.lastIndexOf("\\"))) ;
                    } else {
                        fileExist = new File( "/data/tomcat9/webapps/instrukser/" + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                        if(fileExist.exists()){
                            fileExist.delete();
                        }
                        file = new File( "/data/tomcat9/webapps/instrukser/" + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                    }
                    fi.write( file ) ;
                }
            }
        } catch(Exception ex) {
            System.out.println(ex);
        }

        //RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/forvaltning/uploadfile.jsp");
        //rd.forward(request, response);
        String url = request.getContextPath() + "/forvaltning/uploadfile.p"  + "?lagretinstrukser";
        url = response.encodeRedirectURL(url);
        response.sendRedirect(url);
    }


}
package mx.krieger.mapaton.publicapi.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.krieger.mapaton.publicutils.utils.GTFSHelper;


public class GetGtfs extends HttpServlet {
	
   	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String paramValue = req.getParameter("version");
        
        if(paramValue == null){
        	paramValue = "";
        }
        
        byte[] zipData = new GTFSHelper().getGTFSZipFile(paramValue);
		// Serve the data to response's stream
		String filename = "mapatonGTFS.zip";

		// following header statement instructs the web browser
		// to treat the file as attachment (= to download the file)
		res.setHeader("Content-Disposition", "attachment; filename=" + filename);

		res.setContentType("application/x-download");
		res.setContentLength(zipData.length);
		res.getOutputStream().write(zipData);
	}
    
}
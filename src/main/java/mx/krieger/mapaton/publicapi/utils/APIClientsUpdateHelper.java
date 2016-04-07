/**
 * 
 */
package mx.krieger.mapaton.publicapi.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>
 * This class is called to update the pom descriptor for the api clients.
 * </p>
 * 
 * @author JJMS(juanjo@krieger.mx)
 * @version 1.0.0.0
 *          <p>
 *          history:<br>
 *          <table border="1">
 *          <thead>
 *          <tr>
 *          <th width="15%">Date</th>
 *          <th width="30%">Author</th>
 *          <th width="55%">Comment</th>
 *          </tr>
 *          </thead><tbody>
 *          <tr>
 *          <td>28 Jul 2015 - 12:12:26</td>
 *          <td>JJMS (juanjo@krieger.mx)</td>
 *          <td>
 *          <ul>
 *          <li>creation</li>
 *          </ul>
 *          </td>
 *          </tr>
 *          </tbody>
 *          </table>
 *          </p>
 */
public class APIClientsUpdateHelper {

	private static final String APP_CLIENT_POM_TEMPLATE_FILE = "clients/app/pom_template.xml";
	private static final String APP_CLIENT_POM_FILE = "clients/app/pom.xml";
	private static final String APP_CLIENT_POM_DEPLOYABLE_FILE = "target/endpoints-client-libs/dashboardAPI/pom.xml";
	private static final String BACKEND_POM = "pom.xml";
	private static boolean updateAppClient = true;

	/**
	 * <p>
	 * This method executes the sequence to update poms for API clients, using
	 * templates.
	 * </p>
	 * 
	 * @author JJMS(juanjo@krieger.mx)
	 *         <p>
	 *         history:<br>
	 *         <table border="1">
	 *         <thead>
	 *         <tr>
	 *         <th width="15%">Date</th>
	 *         <th width="30%">Author</th>
	 *         <th width="55%">Comment</th>
	 *         </tr>
	 *         </thead><tbody>
	 *         <tr>
	 *         <td>28 Jul 2015 - 12:15:55</td>
	 *         <td>JJMS (juanjo@krieger.mx)</td>
	 *         <td>
	 *         <ul>
	 *         <li>creation</li>
	 *         </ul>
	 *         </td>
	 *         </tr>
	 *         </tbody>
	 *         </table>
	 *         </p>
	 * @param args
	 *            The following arguments are expected:<br>
	 *            args[0] : true if the API client for the app must be updated,
	 *            false otherwise<br>
	 *            If no parameters are passed, both API clients will be updated.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("Updating API clients...");

		try {
			updateTemplatesToCurrentVersion();
			System.out.println("API clients updated");
			System.out.println("--SUCCESS--");
			return;
		} catch (IOException e) {
			System.out.println("========================================================================");
			System.out.println("************************************************************************");
			System.out.println("ERROR: Unable to create pom for clients");
			System.out.println("Clients were not deployed. Verify that the libraries were created.");
			System.out.println("************************************************************************");
			System.out.println("========================================================================");
			System.out.println("--ERROR--");
			return;
		}

	}

	private static void updateTemplatesToCurrentVersion() throws IOException {
		System.out.println("Updating API clients version");

		String backendPOM;
		String mapatonClientPOMtemplate;
		String backendVersion;

		try {
			backendPOM = readfFile(BACKEND_POM);
			backendVersion = backendPOM.split("<version>")[1].split("</version>")[0];
			System.out.println("Current backend version: " + backendVersion);
		} catch (IOException e) {
			System.out.println("Can't find local pom file for backend project");
			throw e;
		}

		if (updateAppClient) {
			try {
				mapatonClientPOMtemplate = readfFile(APP_CLIENT_POM_TEMPLATE_FILE);
				mapatonClientPOMtemplate = mapatonClientPOMtemplate.replace("[artifact-version]", backendVersion);
				writeFile(APP_CLIENT_POM_FILE, mapatonClientPOMtemplate);
				writeFile(APP_CLIENT_POM_DEPLOYABLE_FILE, mapatonClientPOMtemplate);
			} catch (IOException e) {
				System.out.println("Can't find local pom file for mapaton api client");
				throw e;
			}
		}

		System.out.println("API clients version updated");
	}

	public static String readfFile(String file) throws IOException {

		BufferedReader br = null;
		StringBuilder text = new StringBuilder();

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(file));

			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);
				text.append(sCurrentLine).append("\n");
			}

			return text.toString();

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void writeFile(String fileName, String content) {
		try {

			File file = new File(fileName);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

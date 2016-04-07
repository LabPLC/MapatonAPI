package mx.krieger.pides.mapaton.tasks;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.krieger.internal.apicommons.model.Task;
import mx.krieger.internal.commons.utils.logging.Logger;
import mx.krieger.pides.mapaton.utils.GTFSHelper;


public class GtfsGenerationTask extends Task{

	private static final String TASK_NAME = GtfsGenerationTask.class
		.getSimpleName();
	private static final String QUEUE = "gtfsQueue";
	private static final long serialVersionUID = 1L;
	private static final Logger logger = new Logger(GtfsGenerationTask.class);

	public enum Params {
		trailIds;
	}
	
	/**
	 * This is the default constructor used to create a new instance of this
	 * task.
	 */
	public GtfsGenerationTask(){
		super(TASK_NAME, QUEUE);
	}

	/* (non-Javadoc)
	 * 
	 * @see mx.krieger.labplc.mapaton.tasks.trailregistration.Task#doPost(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@Override
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException{
		logger.debug("Initializing " + GtfsGenerationTask.class.getSimpleName());

		String trailIds = request.getParameter(Params.trailIds.name());

		execute(trailIds);

	}

	/* (non-Javadoc)
	 * 
	 * @see
	 * mx.krieger.labplc.mapaton.tasks.trailregistration.Task#execute(java.lang.
	 * String[]) */
	@Override
	public void execute(String... params){

		logger.debug("Executing " + GtfsGenerationTask.class.getSimpleName());

		String[] ids = params[0].split(",");
		long[] trailIds = new long[ids.length];
		int i = 0;
		for(String s : ids){
			trailIds[i++] = Long.parseLong(s); 
		}
		try {
			new GTFSHelper().generateGTFS(trailIds);
		} catch (IOException e) {
			logger.error("impossible to generate gtfs");
			e.printStackTrace();
		}
		

	}

}

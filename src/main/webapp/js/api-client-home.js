
//In case if the dashboard API has not loaded, we store the wanted action in here
window.pendingAction = -1;

//Boolean to know if the mapaton public dashboard API has loaded
window.clientHasLoaded = false;


/**
 * this method is called when the google client js has loaded on the client page
 * it fetches the dashboard API for mapaton public and then gets the trails to display them
 */
function initDashboardAPI(){
	//Call the google client to load the Mapaton Public dashboard API
    gapi.client.load(DASHBOARD_API_NAME, DASHBOARD_API_VERSION, function() {
        window.clientHasLoaded = true;
        console.log("Client has loaded!");
        getAllTrails('');
        window.pendingAction = -1;

    }, API_PATH);
}


/**
 * This method will fetch the trails from the API and format them in a correct way to display
 * @param cursor the current cursor for pagination, can be an empty string if it is the first page
 */
function getAllTrails(cursor){
    if(window.clientHasLoaded){
        gapi.client.dashboardAPI.getAllTrails({
        	numberOfElements:NUMBER_OF_ELEMENTS,
        	cursor:cursor
        }).execute(function (resp) {
            if(typeof resp.error=='undefined'){
                var trails = []; //The array of objects that will be displayed on a table
                if(resp.trails){
                	for(var i=0; i<resp.trails.length; i++){ //Get a trail and verify that all the data needed is there
                        var tempTrail = resp.trails[i];
                        var trail = {};
                        trail.origin = tempTrail.originStationName;
                        trail.destination = tempTrail.destinationStationName;
                        trail.transportType = tempTrail.transportType;
                        if(tempTrail.schedule)
                            trail.schedule = tempTrail.schedule;
                        if(tempTrail.branchName)
                            trail.branch = tempTrail.branchName;
                        trail.id = tempTrail.trailId;
                        trail.status = tempTrail.trailStatus;

                        //Set a human readable date for the trail
                        if(tempTrail.revisionDate)
                        	trail.revisionDate = dateString(new Date(tempTrail.revisionDate)) ;
                        else
                        	trail.revisionDate = 'Sin revisión';
                        //Set a human readable status for the trail;
                        switch(tempTrail.trailStatus){
                        	case window.TRAIL_STATUS.VALID:
	                        	trail.trailStatus = "Válido";
	                        	break;
	                        case window.TRAIL_STATUS.INVALID_TOO_SHORT:
	                        	trail.trailStatus = "Inválido, recorrido corto";
	                        	break;
	                        case window.TRAIL_STATUS.INVALID_OUTSIDE_TIMEFRAME:
	                        	trail.trailStatus = "Inválido, recorrido fuera de tiempo";
	                        	break;
	                    	default:
	                    		trail.trailStatus = "Inválido";
                        }
                        
                        trails.push(trail);
                    }
                }
                //Call the method to fill the table with the fetched trails
                updateTrailTable(trails, resp.cursor);
            } else {
                console.log(resp);
                var wrapper = $("#container-trails");
                wrapper.html("<p onclick='onMenuItem3Press()'>Hubo un error al obtener los mapeos, por favor presione aquí para intentar de nuevo</p>");
                window.currentElement = -1;
            }
        });
    }else{
        console.log("Client hasn't loaded, registering pending action...");
        window.pendingAction = 1;
    }
}





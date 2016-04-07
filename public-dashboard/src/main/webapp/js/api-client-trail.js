/**
 * this method is called when the google client js has loaded on the client page
 * it fetches the dashboard API for mapaton public and then gets the trail to display it
 */
function initTrailAPI(){
    gapi.client.load(PUBLIC_API_NAME, PUBLIC_API_VERSION, function() {
        window.clientHasLoaded = true;
        console.log("Client has loaded!");
    	var id = getParameterByName("id");
        if(id === 'undefined' || id == null || id == ''){
            $("#content").html("Error de navegación, por favor utilice los elementos de navegación del dashboard para desplazarse");
        } else
            getTrail(id);
        
    }, API_PATH);
}

/**
 * This method fetches a trail and passes the information to the function that renders it
 * @param trailId the trail id.
 */
function getTrail(trailId){
    gapi.client.dashboardAPI.getTrailDetails({
        "trailId":trailId
    }).execute(function (resp) {
        if(typeof resp.error=='undefined'){
        	//We format and verify that all the required data is present
            var trail = new Object();
            trail.trailId = resp.trailId;
            var origin = new Object();
            origin.stationName = resp.originStationName;
            origin.platformName = resp.originPlatformName;
            trail.origin = origin;
            var destination = new Object();
            destination.stationName = resp.destinationStationName;
            destination.platformName = resp.destinationPlatformName;
            trail.destination = destination;
            trail.creationDate = resp.creationDate;
            trail.branchName = resp.branchName;
            trail.transportType = resp.transportType;
            trail.maxTariff = resp.maxTariff;
            trail.photoUrl = resp.photoUrl;
            trail.schedule = resp.schedule;
            trail.invalidReason = resp.invalidReason;
            trail.notes = resp.notes;
            trail.score = resp.score;

            trail.totalMinutes = resp.totalMinutes;
            trail.totalMeters = resp.totalMeters;

            if(trail.branchName === 'undefined' || trail.branchName == null)
                trail.branchName = "-";

            if(trail.schedule === 'undefined' || trail.schedule == null)
                trail.schedule = "-";

            if(trail.notes === 'undefined' || trail.notes == null)
                trail.notes = "-";

            if(trail.invalidReason === 'undefined' || trail.invalidReason == null)
                trail.invalidReason = "";
            
            //Set a human readable status for the trail;
            switch(resp.trailStatus){
	            case window.TRAIL_STATUS.VALID:
	            	trail.trailStatus = "Válido";
	            	break;
	            case window.TRAIL_STATUS.INVALID_TOO_SHORT:
	            	trail.trailStatus = "Inválido, recorrido corto";
	            	break;
	            case window.TRAIL_STATUS.INVALID_OUTSIDE_TIMEFRAME:
	            	trail.trailStatus = "Inválido, recorrido fuera de tiempo";
	            	break;
	            case window.TRAIL_STATUS.INVALID_WRONG_DATA:
	            	trail.trailStatus = "Inválido, datos erróneos";
	            	break;
	        	default:
	        		trail.trailStatus = "Inválido";
            }
            inflateTrailUI(trail);
        } else {
        	showNoData();
        }
    });
}

/**
 * This function gets all the registered points of the trail. 
 * It calls itself over and over to be able to get the all the points,
 * the array of points is really big and more petitions are needed to get all 
 * @param trailId
 * @param cursor
 */
var trailPoints = []; //An array to store the current points fetched from the server
function getTrailPoints(trailId, cursor){
    gapi.client.dashboardAPI.getTrailRawPoints({
        "trailId":trailId,
        "numberOfElements":NUMBER_OF_ELEMENTS,
        "cursor":cursor
    }).execute(function (resp) {
    	if(typeof resp.points === 'undefined'){ //in case there are no more points, fill the map in the UI
    		fillMap(trailPoints);
    	} else { //in case there are more points, add the points to the array and call the function again with the updated cursor
    		trailPoints = trailPoints.concat(resp.points);
        	getTrailPoints(trailId, resp.cursor);

    	}
    });
    
}

/**
 * This function gets all the registered and snapped points of the trail. 
 * It calls itself over and over to be able to get the all the points,
 * the array of points is really big and more petitions are needed to get all 
 * @param trailId
 * @param cursor
 */
var trailSnappedPoints = []; //An array to store the current snapped points fetched from the server
function getTrailSnappedPoints(trailId, cursor){
    gapi.client.dashboardAPI.getTrailSnappedPoints({
        "trailId":trailId,
        "numberOfElements":NUMBER_OF_ELEMENTS,
        "cursor":cursor
    }).execute(function (resp) {
    	if(typeof resp.points === 'undefined'){ //in case there are no more points, fill the clean map in the UI
    		fillMap(trailSnappedPoints, 'clean-map');
    	} else { //in case there are more points, add the points to the array and call the function again with the updated cursor
    		trailSnappedPoints = trailSnappedPoints.concat(resp.points);
    		getTrailSnappedPoints(trailId, resp.cursor);
    	}
    });
    
}

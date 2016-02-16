
function initTrailAPI(){
    gapi.client.load(DASHBOARD_API_NAME, DASHBOARD_API_VERSION, function() {
        window.clientHasLoaded = true;
        console.log("Client has loaded!");
    	var id = getParameterByName("id");
        if(id === 'undefined' || id == null || id == ''){
            $("#content").html("Error de navegación, por favor utilice los elementos de navegación del dashboard para desplazarse");
        } else
            getTrail(id);
        
    }, API_PATH);
}

function getTrail(trailId){
    gapi.client.dashboardAPI.getTrailDetails({
        "trailId":trailId
    }).execute(function (resp) {
        if(typeof resp.error=='undefined'){
            console.log(resp);
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

//            trail.points = resp.points;
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

var trailPoints = [];
function getTrailPoints(trailId, cursor){
    gapi.client.dashboardAPI.getTrailRawPoints({
        "trailId":trailId,
        "numberOfElements":NUMBER_OF_ELEMENTS,
        "cursor":cursor
    }).execute(function (resp) {
    	if(typeof resp.points === 'undefined'){
    		fillMap(trailPoints);
    	} else {
    		trailPoints = trailPoints.concat(resp.points);
        	getTrailPoints(trailId, resp.encodedCursor);

    	}
    });
    
}

var trailSnappedPoints = [];
function getTrailSnappedPoints(trailId, cursor){
    gapi.client.dashboardAPI.getTrailSnappedPoints({
        "trailId":trailId,
        "numberOfElements":NUMBER_OF_ELEMENTS,
        "cursor":cursor
    }).execute(function (resp) {
    	if(typeof resp.points === 'undefined'){
    		fillMap(trailSnappedPoints, 'clean-map');
    	} else {
    		trailSnappedPoints = trailSnappedPoints.concat(resp.points);
    		getTrailSnappedPoints(trailId, resp.encodedCursor);
    	}
    });
    
}

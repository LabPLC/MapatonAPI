
window.pendingAction = -1;
window.clientHasLoaded = false;



function initDashboardAPI(){
    gapi.client.load(DASHBOARD_API_NAME, DASHBOARD_API_VERSION, function() {
        window.clientHasLoaded = true;
        console.log("Client has loaded!");
        getAllTrails('');
        window.pendingAction = -1;

    }, API_PATH);
}


function getAllTrails(cursor){
    if(window.clientHasLoaded){
        gapi.client.dashboardAPI.getAllTrails({
        	numberOfElements:NUMBER_OF_ELEMENTS,
        	cursor:cursor
        }).execute(function (resp) {
        	//console.log(resp);
            if(typeof resp.error=='undefined'){
                var trails = [];
                if(resp.trails){
                	for(var i=0; i<resp.trails.length; i++){
                        var t = resp.trails[i];
                        var trail = {};
                        trail.origin = t.originStationName;
                        trail.destination = t.destinationStationName;
                        trail.transportType = t.transportType;
                        if(t.schedule)
                            trail.schedule = t.schedule;
                        if(t.branchName)
                            trail.branch = t.branchName;
                        trail.id = t.trailId;
                        trail.status = t.trailStatus;

                        if(t.revisionDate)
                        	trail.revisionDate = dateString(new Date(t.revisionDate)) ;
                        else
                        	trail.revisionDate = 'Sin revisión';
                        switch(t.trailStatus){
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





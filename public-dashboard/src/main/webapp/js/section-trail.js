window.mapDataHasBeenLoaded = false; //Boolean to know if the data for the map has been loaded
window.cleanMapHasBeenLoaded = false; //Boolean to know if the data for the clean map has been loaded
window.gMapsAPIHasLoaded = false; //Boolean to know if the google maps API has been loaded


/**
 * This function will be called when the DOM has been loaded. 
 * @param $ jquery
 */
(function($) {
	initSkel();
})(jQuery);

/**
 * Fill the HTML with the data that was fetched from the server.
 * @param trailData
 */
function inflateTrailUI(trailData){
    $("#page-title").html(trailData.origin.stationName + " - " + trailData.destination.stationName);
    var html =
        '<div class="row uniform">' +
            '<div class="12u">' +
                '<div class="mdl-card mdl-shadow--2dp card" id="mainTable">' +
                
	                '<div class="mdl-tabs mdl-js-tabs mdl-js-ripple-effect">'+
		                '<div class="mdl-tabs__tab-bar">'+
		                    '<a href="#dirty-map-tab" class="mdl-tabs__tab is-active">Mapa del Recorrido</a>'+
		                    '<a href="#clean-map-tab" class="mdl-tabs__tab" id="clean-tab-id">Mapa limpio del Recorrido</a>'+
		                '</div>'+
	
		                '<div class="mdl-tabs__panel is-active" id="dirty-map-tab">'+
			                '<div class="graph-container map" id="trailmap">' +
			                	'<div class="loading-wrapper"><img src="images/loader.svg"></div>' +
			                '</div>' +
			            '</div>'+
		                '<div class="mdl-tabs__panel" id="clean-map-tab">'+
		                	'<div class="graph-container map" id="clean-map">' +
		            			'<div class="loading-wrapper"><img src="images/loader.svg"></div>' +
		            		'</div>' +
		                '</div>'+
		              '</div>'+
                '</div>' +
            '</div>' +
            '<div class="6u 12u(medium)">' +
                '<div class="mdl-card mdl-shadow--2dp card">' +
                    '<h4 class="title">Información</h4>' +
                    '<div class="with-card-padding fill-parent-horizontally">' +
                        '<div class="row">' +
                            '<div class="6u 12u(small)">' +
                                '<p class="subtitle">Fecha de carga</p>' +
                                '<p class="with-margin-bottom">' + dateDetailedString(new Date(trailData.creationDate)) + '</p>'+
                                '<p class="subtitle">Tarifa máxima</p>' +
                                '<p class="with-margin-bottom">$' + parseFloat(trailData.maxTariff).toFixed(2) + '</p>'+
                                '<p class="subtitle">Tipo de vehículo</p>' +
                                '<p class="with-margin-bottom">' + trailData.transportType.replace('u_ACUTE','ú') + '</p>'+
                                '<p class="subtitle">Tiempo del recorrido</p>' +
                                '<p class="with-margin-bottom">' + trailData.totalMinutes + ' minutos</p>'+
                                '<p class="subtitle">Distancia recorrida</p>' +
                                '<p>' + trailData.totalMeters.toFixed(2) + ' metros</p>'+
                            '</div>' +
                            '<div class="6u$ 12u(small)">' +
	                            '<p class="subtitle">Validez del recorrido</p>' +
	                            '<p class="with-margin-bottom">'+ trailData.trailStatus+ '</p>' +
	                            '<p class="with-margin-bottom">' + trailData.invalidReason + '</p>'+
                                '<p class="subtitle">Ramal</p>' +
                                '<p class="with-margin-bottom">' + trailData.branchName + '</p>'+
	                            '<p class="subtitle">Frecuencia, calendario y horario</p>' +
	                            '<p class="with-margin-bottom">' + trailData.schedule + '</p>'+
                                '<p class="subtitle">Tipo de vehículo</p>' +
                                '<p class="with-margin-bottom">' + trailData.transportType.replace('u_ACUTE','ú')  + '</p>'+
	                            '<p class="subtitle">Notas</p>' +
	                            '<p class="with-margin-bottom">' + trailData.notes + '</p>'+
                            '</div>' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>' +
            '<div class="4u$ 9u$(medium) 12u(small)">' +
                '<div class="mdl-card mdl-shadow--2dp card">' +
                    '<img class="trail-photo" src="' + trailData.photoUrl + '"/>' +
                '</div>' +
            '</div>' +
        '</div>';

    var wrapper = $("#content");
    wrapper.html(html); //append the trail description form to the html
    window.data = trailData;
    window.mapDataHasBeenLoaded = true; //Specify that we are able to load the points from the server
    if(window.gMapsAPIHasLoaded){ //get the registered points from the server
    	getTrailPoints(trailData.trailId, '');
    }
    componentHandler.upgradeElements(document.getElementById('mainTable')); //specify to MDL that we added some elements that are handled by it
    
    //Add an onclick to the tab that contains the clean map
    $('#clean-tab-id').click(function(){
    	if(!window.cleanMapHasBeenLoaded){ //in case that the clean points have not been loaded, load them.
    		getTrailSnappedPoints(trailData.trailId, '');
    		window.cleanMapHasBeenLoaded = true; //specify that the clean points have been loaded
    	}

    });

    
}

//Specify that the google maps API has loaded.
function initMaps(){
	console.log('init maps');   
	window.gMapsAPIHasLoaded = true;

}
//Fill the points of a given map (Selector) and place them.
function fillMap(trailPoints, selector){
    var map;
    selector = selector ? selector: 'trailmap';
    window.gMapsAPIHasLoaded = true;
    if(window.mapDataHasBeenLoaded) {
        if(trailPoints === 'undefined' || trailPoints ==  null || trailPoints.length === 0){
            $("#" + selector).html("Error: el recorrido está vacío");
        }else{
            var points = [];
            var bounds = new google.maps.LatLngBounds();
            for(var i=0; i<trailPoints.length; i++){
                var latlon = new google.maps.LatLng(trailPoints[i].location.latitude, trailPoints[i].location.longitude);
                points.push(latlon);
                bounds.extend(latlon);
            }

            map = new google.maps.Map(document.getElementById(selector), {
                mapTypeId: google.maps.MapTypeId.ROADMAP
            });
            map.fitBounds(bounds);
            map.setCenter(bounds.getCenter());

            var mappedTrail = new google.maps.Polyline({
                path: points,
                geodesic: true,
                strokeColor: '#000',
                strokeOpacity: 1.0,
                strokeWeight: 2
            });

            mappedTrail.setMap(map);
        }
    }
}

/**
 * In case there is no data about the trail, display that to the user
 */
function showNoData(){
	var html = 
		'<div class="row">' +
			'<div class="4u 12u(medium)">' +
			    '<div class="mdl-card mdl-shadow--2dp card">' +
			    '<h4 class="title">El recorrido no existe</h4>' +
			    '<div class="with-card-padding fill-parent-horizontally">' +
			        '<div class="row">' +
						'<div class="12u">' +
							 ' Lo sentimos, el recorrido que estás buscando no existe'+
			            '</div>' +
			        '</div>' +
			    '</div>' +   
		    '</div>' +   
		'</div>';
	$("#page-title").html("Recorrido inexistente");
	$("#content").html(html);
}

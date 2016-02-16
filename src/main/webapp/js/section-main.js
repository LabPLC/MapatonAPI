window.SECTION_MAPPINGS = 1; //Define the constant section of trails

window.currentElement = -1; //Define the current section we are in

window.TRAIL_TABLE = 'trail-table'; //Define the table that will be used in the section of trails
window.loading = false; //boolean to know if there is a petition to load more elements to a table
window.canLoadMore = true; //boolean to know if there are more elements to load from the server


/**
 * This function will be called when the DOM has been loaded. 
 * @param $ jquery
 */
(function($) {
	initSkel();

	document.getElementById("nav-3").onclick = onMenuItemPress;

    google.load('visualization', '1.0', {packages: ['corechart']});


    onMenuItemPress();
    
    //This is a listener to be able to scroll and load more elements when the end of the list has been reached
    $('.content-wrapper').scroll(function(){
    	var table = $('#tableName').val();
    	if(window.canLoadMore && !window.loading &&  (table === window.TRAIL_TABLE)){
	    	var $wrap = $('#' + table);
	    	var contentHeight = $wrap.height();
	    	var yOffset = $wrap.offset().top; 
	    	var windowHeight = $(window).height();
	    	if(contentHeight + yOffset - windowHeight <= 100){
	    		window.loading = true;
	    		console.log('loading');
	    		var loadingHtml = '<div class="loading-wrapper small" style="margin-top:10px;"><img src="images/loader.svg"></div>';
	    		$('.content-wrapper').append(loadingHtml);
	    		if(table === window.TRAIL_TABLE)
	    			getAllTrails($('#cursor').val());
	    		
	    	}
    	}
    });
    
    
})(jQuery);

/**
 * This function removes the loading gif from the table.
 */
function removeLoadingFromTable(){
	var table = $('#tableName').val();
	$('.content-wrapper div:last-child').remove();
}

/**
 * This function loads the data when a menu item has been pressed
 */
function onMenuItemPress(){
	var i = window.SECTION_MAPPINGS;
    if(window.currentElement != i) {
        window.location.hash = i;
        window.data = null;

        if(window.currentElement != -1){
            var lastItem = $("#nav-"+window.currentElement);
            lastItem.removeClass("selected");
        }

        var wrapper = $(".content-wrapper");
        var loadingHtml = "";

        //Add selected class to the current selected element in the menu
        var navItem = $("#nav-"+i);
        var title = navItem.clone().children().remove().end().text();
        navItem.addClass("selected");
        window.currentElement = i;
        $(".mdl-layout-title").html(title);

        window.canLoadMore = true; //assume that we are able to load more elements
        loadingHtml = //The html that contains the loading element and the container of the table
            '<div id="container-trails">' +
                '<div class="inner-padding">' +
                    '<div class="loading-wrapper"><img src="images/loader.svg"></div>' +
                '</div>' +
            '</div>';
        wrapper.html(loadingHtml);
        
        getAllTrails(); //Get all the trails that are registered in the datastore
        wrapper.scrollTop(0);
        var layout = $(".layout");
        var drawer = $(".mdl-layout__drawer");
        if(layout.hasClass("is-small-screen"))
            $(drawer).removeClass("is-visible");
    }
}

/**
 * This function adds the initial HTML elements for the TrailTable that will be filled later
 */
function inflateTrailTable(){
    var html =
        '<table class="mdl-data-table mdl-js-data-table fill-parent-horizontally">' +
            '<thead>' +
                '<tr>' +
                    '<th class="mdl-data-table__cell--non-numeric">Origen</th>' +
                    '<th class="mdl-data-table__cell--non-numeric">Destino</th>' +
                    '<th class="mdl-data-table__cell--non-numeric">Ramal</th>' +
                    '<th class="mdl-data-table__cell--non-numeric">Vehículo</th>' +
                    '<th class="mdl-data-table__cell--non-numeric">Horario</th>' +
                    '<th class="mdl-data-table__cell--non-numeric">Status</th>' +
                '</tr>' +
            '</thead>' +
            '<tbody id="' + window.TRAIL_TABLE + '">'
            '</tbody>' +
        '</table>';
    var wrapper = $("#container-trails");
    wrapper.html(html);
    
}

/**
 * This function adds the newly fetched trails to the trail table
 * @param trails the array of elements fetched
 * @param cursor the current cursor for pagination
 */
function updateTrailTable(trails, cursor){
	$('#cursor').val(cursor); //save the cursor to be able to use it later
	var html = '';
	var $trailTable = $('#' + window.TRAIL_TABLE);
	if($trailTable.length === 0) { //in the case that there is no TRAIL_TABLE on the current dom, initialize the table
		$('#tableName').val(window.TRAIL_TABLE);
		inflateTrailTable();
		$trailTable = $('#' + window.TRAIL_TABLE);
	} 

	if(window.loading) //in the case that there is a loading element in the table, remove it
		removeLoadingFromTable();
	if(trails.length > 0){ //if there are trails on the array, display them on the table
    	for(var i=0; i<trails.length; i++){
            var trail = trails[i];
            var branch = trail.branch;
            var schedule = trail.schedule;
            if(!branch)
                branch = '-';
            if(!schedule)
                schedule = "-";
            html += //The new row that will be added to the table.
                    '<tr class="clickable" onclick="onTrailClick(' + trail.id + ');">' +
                        '<td class="mdl-data-table__cell--non-numeric">' + trail.origin + '</td>' +
                        '<td class="mdl-data-table__cell--non-numeric">' + trail.destination + '</td>' +
                        '<td class="mdl-data-table__cell--non-numeric">' + branch + '</td>' +
                        '<td class="mdl-data-table__cell--non-numeric">' + trail.transportType.replace('u_ACUTE','ú') + '</td>' +
                        '<td class="mdl-data-table__cell--non-numeric">' + schedule + '</td>' +
                        '<td class="mdl-data-table__cell--non-numeric">' + trail.trailStatus + '</td>' +
                    '</tr>';
        }
        
    } else { //If there are no more elements, specify that we are no able to load more
    	window.canLoadMore = false;
    }
    $trailTable.append(html); //append the current html to the table.

	window.loading = false;
	
}


/**
 * This function is called when a trail is clicked on the table. It will open a new tab window with the details of the trail clicked
 */
function onTrailClick(trailId){
    window.open('trailDetail.html?id='+trailId,'_blank');
}



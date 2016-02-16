window.SECTION_MAPPINGS = 1;

window.currentElement = -1;
window.mapDataHasBeenLoaded = false;
window.gMapsAPIHasLoaded = false;

window.TRAIL_TABLE = 'trail-table';
window.loading = false;
window.canLoadMore = true;


(function($) {
	initSkel();

	document.getElementById("nav-3").onclick = onMenuItemPress;

    google.load('visualization', '1.0', {packages: ['corechart']});


    onMenuItemPress();
    
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

function removeLoadingFromTable(){
	var table = $('#tableName').val();
	$('.content-wrapper div:last-child').remove();
}

function onMenuItemPress(){
	var i = 1;
    if(window.currentElement != i) {
        window.location.hash = i;
        window.mapDataHasBeenLoaded = false;
        window.data = null;

        if(window.currentElement != -1){
            var lastItem = $("#nav-"+window.currentElement);
            lastItem.removeClass("selected");
        }

        var wrapper = $(".content-wrapper");
        var loadingHtml = "";

        var navItem = $("#nav-"+i);
        var title = navItem.clone().children().remove().end().text();
        navItem.addClass("selected");
        window.currentElement = i;
        $(".mdl-layout-title").html(title);

        window.canLoadMore = true;
        loadingHtml =
            '<div id="container-trails">' +
                '<div class="inner-padding">' +
                    '<div class="loading-wrapper"><img src="images/loader.svg"></div>' +
                '</div>' +
            '</div>';
        getAllTrails();
        
        wrapper.html(loadingHtml);
        wrapper.scrollTop(0);
        var layout = $(".layout");
        var drawer = $(".mdl-layout__drawer");
        if(layout.hasClass("is-small-screen"))
            $(drawer).removeClass("is-visible");
    }
}


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


function updateTrailTable(trails, cursor){
	$('#cursor').val(cursor);
	var html = '';
	var $trailTable = $('#' + window.TRAIL_TABLE);
	if($trailTable.length === 0) {
		$('#tableName').val(window.TRAIL_TABLE);
		inflateTrailTable(trails);
		$trailTable = $('#' + window.TRAIL_TABLE);
	} 

	if(window.loading)
		removeLoadingFromTable();
	if(trails.length > 0){
    	for(var i=0; i<trails.length; i++){
            var trail = trails[i];
            var branch = trail.branch;
            var schedule = trail.schedule;
            if(!branch)
                branch = '-';
            if(!schedule)
                schedule = "-";
            html +=
                    '<tr class="clickable" onclick="onTrailClick(' + trail.id + ');">' +
                        '<td class="mdl-data-table__cell--non-numeric">' + trail.origin + '</td>' +
                        '<td class="mdl-data-table__cell--non-numeric">' + trail.destination + '</td>' +
                        '<td class="mdl-data-table__cell--non-numeric">' + branch + '</td>' +
                        '<td class="mdl-data-table__cell--non-numeric">' + trail.transportType.replace('u_ACUTE','ú') + '</td>' +
                        '<td class="mdl-data-table__cell--non-numeric">' + schedule + '</td>' +
                        '<td class="mdl-data-table__cell--non-numeric">' + trail.trailStatus + '</td>' +
                    '</tr>';
        }
        
    } else {
    	window.canLoadMore = false;
    }
    $trailTable.append(html);

	window.loading = false;
	
}



function onTrailClick(trailId){
    window.open('trailDetail.html?id='+trailId,'_blank');
}



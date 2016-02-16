

window.TRAIL_STATUS = {}; //Object to define the constants of the trail status
window.TRAIL_STATUS.VALID = 0;
window.TRAIL_STATUS.INVALID_TOO_SHORT = 1;
window.TRAIL_STATUS.INVALID_OUTSIDE_TIMEFRAME = 2;
window.TRAIL_STATUS.INVALID_WRONG_DATA = 3;

/**
 * function to initialize the content manager
 */
function initSkel(){
    skel.breakpoints({
        xlarge:		'(max-width: 1680px)',
        large:		'(max-width: 1280px)',
        medium:		'(max-width: 980px)',
        small:		'(max-width: 736px)',
        xsmall:		'(max-width: 480px)',
        xxsmall:	'(max-width: 360px)'
    });

    $(function() {
        var	$window = $(window),
            $body = $('body');

        // Disable animations/transitions until the page has loaded.
        $body.addClass('is-loading');

        $window.on('load', function() {
            window.setTimeout(function() {
                $body.removeClass('is-loading');
            }, 100);
        });

        // Mobile?
        if (skel.vars.mobile)
            $body.addClass('is-mobile');
        else
            skel
                .on('-medium !medium', function() {
                    $body.removeClass('is-mobile');
                })
                .on('+medium', function() {
                    $body.addClass('is-mobile');
                });
    });
}


/**
 * function to make a date human readable
 * @param date {Date}
 * @returns {String}
 */
function dateString(date){
	if(date === 'undefined' || date == null){
		return "";
	}
	var months = ['enero', 'febrero', 'marzo', 
	              'abril', 'mayo', 'junio',
	              'julio', 'agosto', 'septiembre',
	              'octubre', 'noviembre', 'diciembre'];
	var day = date.getDate();
	var monthIndex = date.getMonth();
	var year = date.getFullYear();
	return day + ' de ' + months[monthIndex] + ' de ' + year;
	
}

/**
 * function to make a date human readable, with hours and minutes
 * @param date {Date}
 * @returns {String}
 */
function dateDetailedString(date){
	if(date === 'undefined' || date == null){
		return "";
	}
	var months = ['enero', 'febrero', 'marzo', 
	              'abril', 'mayo', 'junio',
	              'julio', 'agosto', 'septiembre',
	              'octubre', 'noviembre', 'diciembre'];
	var minute = date.getMinutes();
	var hour = date.getHours();
	var second = date.getSeconds();
	var day = date.getDate();
	var monthIndex = date.getMonth();
	var year = date.getFullYear();
	return day + ' de ' + months[monthIndex] + ' de ' + year + ', ' + hour + ':' + twoNumberString(minute) + ':' + twoNumberString(second);
	
}

/**
 * format a number as a 2 number string
 * @param string to be formatted
 * @returns {String}
 */
function twoNumberString(string){
	return ("0" + string).slice(-2);
}

/**
 * Get a parameter in the url;
 * @param name
 * @returns
 */
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
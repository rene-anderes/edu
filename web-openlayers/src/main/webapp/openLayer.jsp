<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:url var="js" value="/js" />
<c:url var="css" value="/css" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>dev::OpenLayer</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="content-script-type" content="text/javascript" />
<meta http-equiv="content-style-type" content="text/css" />
<meta http-equiv="content-language" content="de" />

<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,400italic,500,500italic,700,700italic,900,900italic,300italic,300" type="text/css">
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto+Slab:400,700,300,100" type="text/css">
<link rel="stylesheet" href="https://openlayers.org/en/v4.2.0/css/ol.css" type="text/css">
<link rel="stylesheet" type="text/css" href="${ css }/map.css"></link>
<link rel="stylesheet" type="text/css" href="${ css }/w3pro.css"></link>

<script type="text/javascript" src="https://openlayers.org/en/v4.2.0/build/ol.js"></script>
<script type="text/javascript" src="${ js }/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ js }/guideData.js"></script>
</head>
<body class="w3-body">
	<div class="w3-container">
		<header class="w3-container w3-teal">
			<h1 id="tour-name"></h1>
			<p id="tour-description"></p>
		</header>
		<article class="w3-container">
			<div id="areas"></div>
		</article>
		<article class="w3-container">
			<div id="map"><div id="popup"></div></div>
			<div id="position"></div>
		</article>
	</div>
	<script>
		$(function() {
			initMap();
			getLocation();
			handleTour( tour );
		});
		
		var map;
		var vectorSource = new ol.source.Vector({ });
		var iconStyle = new ol.style.Style({
		  image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
			anchor: [0.5, 46],
         	anchorXUnits: 'fraction',
          	anchorYUnits: 'pixels',
            crossOrigin: 'anonymous',
			src: 'images/map-pin.png',
		  }))
		});
		var vectorLayer = new ol.layer.Vector({
			source: vectorSource,
			style: iconStyle
	    });
	    
		function initMap() {
			map = new ol.Map({
				target : 'map',
				layers : [ new ol.layer.Tile({
					source : new ol.source.OSM()
				}), vectorLayer ],
				view : new ol.View({
					center : ol.proj.fromLonLat([ 47.4245, 9.3748 ]),
					zoom : 4
				})
			});
		}

		function handleTour(tour) {
			$("#tour-name").text(tour.name);
			$("#tour-description").html(tour.description);
			tour.areas.sort( function( a, b ) {
    			var a1 = a.name, b1 = b.name;
    			if( a1 == b1 ) return 0;
    			return a1 > b1 ? 1: -1;
    		});
			$.each(tour.areas, function(idx, area) {
				console.log("startPoint: " + area.startPoint);
				console.log("name: " + area.name);
				$("#areas").append("<p>" + area.name + "</p>");
				if (area.coordinates.length > 0 && area.coordinates[0].lon && area.coordinates[0].lat) {
					console.log("coordinates: " + area.coordinates[0].lon + " | " + area.coordinates[0].lat);
					addMarker(area.coordinates[0].lon, area.coordinates[0].lat, area.name);
				}
			})
		}

		function addMarker(lon, lat, description) {
			var iconFeature = new ol.Feature({
				geometry : new ol.geom.Point(ol.proj.fromLonLat([ lon, lat ])),
				name : description
			});
			vectorSource.addFeature(iconFeature);
		}

		function getLocation() {
			var geolocation = new ol.Geolocation({
				// take the projection to use from the map's view
				projection : map.getView().getProjection()

			});
			geolocation.on('change', function(evt) {
				console.log("location change: " + geolocation.getPosition());
			});

			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(showPosition);
			} else {
				$("#position").text(
						"Geolocation is not supported by this browser.");
			}
		}

		function showPosition(position) {
			$("#position").html(
					"Aktuelle Position:<br>Latitude: "
							+ position.coords.latitude + "<br>Longitude: "
							+ position.coords.longitude);
			jumpTo(position.coords.longitude, position.coords.latitude, 15);
		}

		function jumpTo(lon, lat, zoom) {
			map.getView().setCenter(ol.proj.fromLonLat([ lon, lat ]));
			map.getView().setZoom(zoom);
		}
	</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:url var="js" value="/js"/>
<c:url var="css" value="/css"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>dev::OpenStreetMap</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="content-script-type" content="text/javascript" />
<meta http-equiv="content-style-type" content="text/css" />
<meta http-equiv="content-language" content="de" />

<link href="http://fonts.googleapis.com/css?family=Roboto:400,400italic,500,500italic,700,700italic,900,900italic,300italic,300" rel="stylesheet" type="text/css">
<link href="http://fonts.googleapis.com/css?family=Roboto+Slab:400,700,300,100" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ css }/map.css"></link>
<link rel="stylesheet" type="text/css" href="${ css }/w3pro.css"></link>

<script type="text/javascript" src="http://www.openlayers.org/api/OpenLayers.js"></script>
<script type="text/javascript" src="http://www.openstreetmap.org/openlayers/OpenStreetMap.js"></script>
<script type="text/javascript" src="${ js }/map.js"></script>
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
			<div id="map"></div>
			<div id="position"></div>
		</article>
	</div>
	<script>
	
		$(function() {
			initMap();
			getLocation();
			handleTour( tour );
			addPolygon();
		});
		
		function handleTour( tour ) {
			$( "#tour-name" ).text( tour.name );
			$( "#tour-description" ).html( tour.description );
			$.each( tour.areas, function( idx, area ) {
				console.log( "startPoint: " + area.startPoint );
				console.log( "name: " + area.name );
				$( "#areas" ).append( "<p>" + area.name + "</p>" );
				if ( area.coordinates.length > 0 && area.coordinates[0].lon && area.coordinates[0].lat ) {
					console.log( "coordinates: " + area.coordinates[0].lon + " | " + area.coordinates[0].lat);
					addMarker(layer_markers, area.coordinates[0].lon, area.coordinates[0].lat, "<p>" + area.name + "</p>");
				}	
			})
		}
		
		function getLocation() {
		    if (navigator.geolocation) {
		        navigator.geolocation.getCurrentPosition( showPosition );
		    } else {
		        $( "#position" ).text("Geolocation is not supported by this browser.");
		    }
		}
		
		function showPosition( position ) {
		    $( "#position" ).html( "Aktuelle Position:<br>Latitude: " + position.coords.latitude + "<br>Longitude: " + position.coords.longitude);
		    jumpTo(position.coords.longitude, position.coords.latitude, 15);
		}
		
		var map;
		var layer_mapnik;
		var layer_tah;
		var layer_markers;
		
		function initMap() {
			map = new OpenLayers.Map( 'map', {
		        projection: new OpenLayers.Projection("EPSG:900913"),
		        displayProjection: new OpenLayers.Projection("EPSG:4326"),
		        target: document.getElementById( 'map' ),
		        controls: [
		            new OpenLayers.Control.Navigation(),
		            new OpenLayers.Control.PanZoomBar()],
		        maxExtent:
		            new OpenLayers.Bounds(-20037508.34,-20037508.34, 20037508.34, 20037508.34),
		        numZoomLevels: 18,
		        maxResolution: 156543,
		        units: 'meters'
		    });
		    layer_mapnik = new OpenLayers.Layer.OSM("Simple OSM Map");
		    layer_markers = new OpenLayers.Layer.Markers("Address", { projection: new OpenLayers.Projection("EPSG:4326"), 
		    	                                          visibility: true, displayInLayerSwitcher: false });
			
			var ring = [
			  [106.972534, -6.147714], [106.972519, -6.133398],
			  [106.972496, -6.105892], [106.972534, -6.147714]
			];
			var polygon = new OpenLayers.Polygon([ring]);
			polygon.transform('EPSG:4326', 'EPSG:3857');
			// Create feature with polygon.
			var feature = new OpenLayers.Feature(polygon);
			// Create vector source and the feature to it.
			var vectorSource = new OpenLayers.source.Vector();
			vectorSource.addFeature(feature);
			// Create vector layer attached to the vector source.
			var vectorLayer = new OpenLayers.layer.Vector({
			  source: vectorSource
			});
			// Add the vector layer to the map.
			map.addLayer(vectorLayer);
		
		    map.addLayers([layer_mapnik, layer_markers]);
		    
		}
		
	</script>

</body>
</html>
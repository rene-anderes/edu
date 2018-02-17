/*
 *	Storage für die Rezepte 
 */
angular.module('service.storage', [ 'service.notification', 'coobook.constant', 'service.base64' ])
	.factory('storage', function ($http, $rootScope, notification, Base64, cookbook) {
		
	    var getRecipes = function(callback) {
	    	return $http({
	    	    method: 'GET',
	    	    url: cookbook.getRecipesUrl
	    	}).success(function (data, status, headers, config) {
	    		callback(data);
	    	}).error(function (data, status, headers, config) {
	    		if (console && console.log) {
	    			console.log("GET Fehler: " + status);
	    		}
	    		callback(undefined);
	    	});
	    };
	    
	    var getRecipe = function(recipeId, callback) {
	    	return $http({
	    	    method: 'GET',
	    	    url: cookbook.getRecipeUrl + "/" + recipeId
	    	}).success(function (data, status, headers, config) {
	    		callback(data);
	    	}).error(function (data, status, headers, config) {
	    		if (console && console.log) {
	    			console.log("GET Fehler: " + status);
	    		}
	    		callback(undefined);
	    	});
	    };
	    
	    var updateRecipe = function(recipeId, recipe, callback, credentials) {
	    	return $http({
	    	    method: 'PUT',
	    	    url: cookbook.getRecipeUrl + "/" + recipeId,
	    	    withCredentials: true,
	    	    headers: {
	                'Authorization': 'Basic ' + Base64.encode(credentials.username + ':' + credentials.password)
	            },
	    	    data: recipe
	    	}).success(function (data, status, headers, config) {
	    		callback(recipeId);
	    		notification.changeItem(recipeId);
			}).error(function (data, status, headers, config) {
				if (console && console.log) {
					console.log("Update Recipe: PUT Fehler " + status);
				}
				callback(undefined);
			});
	    };
	    
	    var saveRecipe = function(recipe, callback, credentials) {
	    	$http.post(cookbook.getRecipesUrl, recipe, 	{ 
	    		headers: {'Authorization': 'Basic ' + Base64.encode(credentials.username + ':' + credentials.password)}
	    	})
	    	.success(function (data, status, headers, config) {
	    		console.log("Header Location " + headers("Location"));
	    		h = headers("Location");
	    		index = h.lastIndexOf("/"); 
	    		id = h.substring(index+1, h.length); 
	    		if (console && console.log) {
					console.log("RecipeId " + id);
				}
				callback(id);
				notification.createItem(id);
			}).error(function (data, status, headers, config) {
				if (console && console.log) {
					console.log("Save Recipe: POST Fehler " + status);
				}
				callback(undefined);
			});
	    };			    
	    
	    var deleteRecipe = function(recipeId, callback, credentials) {
	    	$http({
	    		method: 'DELETE',
	    		withCredentials: true,
		    	headers: {
		    		'Authorization': 'Basic ' + Base64.encode(credentials.username + ':' + credentials.password)
		        },
	    		url: cookbook.getRecipeUrl + "/" + recipeId
	    	})
	    	.success(function (data, status, headers, config) {
				callback(recipeId);
				notification.deleteItem(recipeId);
			}).error(function (data, status, headers, config) {
				if (console && console.log) {
					console.log("Delete Recipe: DELETE Fehler " + status);
				}
				callback("Fehler beim löschen des Rezepts.");
			});
	    };
	    
	    var getTags = function(callback) {
	    	$http({ method: 'GET', url: cookbook.getTagsUrl })
	    	.success(function (data, status, headers, config) {	callback(data);	})
	    	.error(function (data, status, headers, config) {
	    		if (console && console.log) {
	    			console.log("GET Fehler: " + status);
	    		}
	    		callback(undefined);
	    	});	
	    };
	    
	    var makeBackup = function(callback, credentials) {
	    	$http({
	    	    method: 'GET',
	    	    withCredentials: true,
		    	headers: {
		    		'Authorization': 'Basic ' + Base64.encode(credentials.username + ':' + credentials.password)
		        },
	    	    url: cookbook.backupUrl
	    	}).success(function (data, status, headers, config) {
	    		callback(data);
	    	}).error(function (data, status, headers, config) {
	    		if (console && console.log) {
	    			console.log("GET Fehler: " + status);
	    		}
	    		callback("Backup konnte nicht durchgeführt werden! Status: " + status);
	    	});	
	    };
	    
	    var systemInfos = function(callback) {
	    	$http({
	    	    method: 'GET',
	    	    withCredentials: true,
	    	    url: cookbook.systemInfosUrl
	    	}).success(function (data, status, headers, config) {
	    		callback(data);
	    	}).error(function (data, status, headers, config) {
	    		if (console && console.log) {
	    			console.log("GET Fehler: " + status);
	    		}
	    		callback(undefined);
	    	});	
	    };
	    
	    var checkLogonData = function(callback, credentials) {
	    	$http({
	    	    method: 'GET',
	    	    withCredentials: true,
		    	headers: {
		    		'Authorization': 'Basic ' + Base64.encode(credentials.username + ':' + credentials.password)
		        },
	    	    url: cookbook.checkLogonData
	    	}).success(function (data, status, headers, config) {
	    		callback(status);
	    	}).error(function (data, status, headers, config) {
	    		callback(status);
	    	});
	    }
	    
	    /* ------------ Reveal public API. ------------- */
		return {
			getRecipes: getRecipes,
			getRecipe: getRecipe,
            updateRecipe: updateRecipe,
            saveRecipe: saveRecipe,
            deleteRecipe: deleteRecipe,
            getTags: getTags,
            makeBackup: makeBackup,
            getSystemInfos: systemInfos,
            checkLogonData: checkLogonData
        };
	});

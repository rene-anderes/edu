/**
 * Lesen der Daten vom Server via REST
 */
angular.module('service.storage', [])
    .factory('storage', function ($http, $resource) {
 
        var getEmployees = function(callback) {
        	return $http({
	    	    method: 'GET',
	    	    url: '/jee-employee/rest/employees'
	    	}).success(function (data, status, headers, config) {
	    		callback(data.employee);
	    	}).error(function (data, status, headers, config) {
	    		if (console && console.log) {
	    			console.log("GET Fehler: " + status);
	    		}
	    		callback(undefined);
	    	});
        };
        
        var getProjects = function(callback){
        	return $http({
	    	    method: 'GET',
	    	    url: '/jee-employee/rest/projects'
	    	}).success(function (data, status, headers, config) {
	    		callback(data);
	    	}).error(function (data, status, headers, config) {
	    		if (console && console.log) {
	    			console.log("GET Fehler: " + status);
	    		}
	    		callback(undefined);
	    	});
        	
        };
        
        var getEmployee = function(employeeId, callback) {
        	return $http({
	    	    method: 'GET',
	    	    url: '/jee-employee/rest/employees/' + employeeId
	    	}).success(function (data, status, headers, config) {
	    		callback(data);
	    	}).error(function (data, status, headers, config) {
	    		if (console && console.log) {
	    			console.log("GET Fehler: " + status);
	    		}
	    		callback(undefined);
	    	});
        };
        
        // REST-Zugriff mittels ngResource
        address = $resource('/jee-employee/rest/employees/:employeeId/address', {employeeId:'@id'});
        
        var getAddress = function(employeeId, callback) { address.get({employeeId:employeeId}, callback) } 
        
        // Reveal public API.
        return {
        	getEmployees: getEmployees,
        	getEmployee: getEmployee,
        	getProjects: getProjects,
        	getAddress: getAddress
        };
    });
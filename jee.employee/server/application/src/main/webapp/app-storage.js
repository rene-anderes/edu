/**
 * 
 */
angular.module('service.storage', [])
    .factory('storage', function ($http) {
 
        var getEmployees = function (callback) {
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
 
        var getEmployeesStub = function (callback) {
        	data = {
        			"employee": [{
        				"lastname": "Way",
        				"firstname": "John",
        				"jobtitle": "Manager",
        				"id": 70,
        				"links": {
        					"link": [{
        						"rel": "employee",
        						"url": "http://localhost:8088/jee-employee/rest/employees/70"
        					}]
        				}
        			},
        			{
        				"lastname": "May",
        				"firstname": "Jill",
        				"jobtitle": "CEO",
        				"id": 96,
        				"links": {
        					"link": [{
        						"rel": "employee",
        						"url": "http://localhost:8088/jee-employee/rest/employees/96"
        					}]
        				}
        			}]
        		};
        	callback(data.employee);
        };
        
        // Reveal public API.
        return {
        	getEmployees: getEmployees
        };
    });
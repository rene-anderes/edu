/**
 * Lesen der Daten via Stub
 */
angular.module('service.storage', [])
    .factory('storage', function ($http) {
        var getEmployees = function (callback) {
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
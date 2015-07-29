/**
 * Lesen der Daten vom Server via REST
 */
angular.module('service.storage', [])
    .factory('storage', function ($http, $resource) {
    	
    	// REST-Zugriff mittels ngResource
 
		employees = $resource('/jee-employee/rest/employees');
        var getEmployees = function(callback) { employees.query(callback); };
         
        employee = $resource('/jee-employee/rest/employees/:employeeId', {employeeId:'@id'});
        var getEmployee = function(employeeId, callback) { employee.get({employeeId:employeeId}, callback); };
        
        address = $resource('/jee-employee/rest/employees/:employeeId/address', {employeeId:'@id'});
        var getAddress = function(employeeId, callback) { address.get({employeeId:employeeId}, callback); };
        
        projects = $resource('/jee-employee/rest/projects');
        var getProjects = function(callback) { projects.query(callback); };
        
        // Reveal public API.
        return {
        	getEmployees: getEmployees,
        	getEmployee: getEmployee,
        	getProjects: getProjects,
        	getAddress: getAddress
        };
    });
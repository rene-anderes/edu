/**
 * JEE Employee Application
 */

var employeeApp = angular.module('employeeApp', [ 'ngRoute', 'ngResource', 'service.storage' ]);

employeeApp.config(function($routeProvider, $locationProvider) {
//	$locationProvider.html5Mode(true);
	$routeProvider.when('/Employees', {
		templateUrl : 'partials/employees.html',
		controller : 'EmployeesController'
	}).when('/Employees/:employeeId', {
		templateUrl : 'partials/employee.html',
		controller : 'EmployeeController'
	}).when("/", {
		templateUrl : 'partials/employees.html',
		controller : 'EmployeesController'
	})
	.when('/Projects', {
	templateUrl: 'partials/projects.html',
	controller: 'ProjectsController'
	})
});

employeeApp.controller('EmployeesController', function($scope, storage) {
	$scope.employees = {};

	storage.getEmployees(function(data) {
		if (data === undefined) {
			if (console && console.log) {
				console.log("Fehler");
			}
		} else {
			$scope.employees = data;
		}
	});

});

employeeApp.controller('EmployeeController', function($scope, storage, $routeParams) {
	$scope.employee = {};
	employeeId = $routeParams.employeeId;
	storage.getEmployee(employeeId, function(data) {
		if (data === undefined) {
			if (console && console.log) {
				console.log("Fehler");
			}
		} else {
			$scope.employee = data;
		}
	});
	storage.getAddress(employeeId, function(address) {
		$scope.address = address;
	});
});

employeeApp.controller('ProjectsController', function($scope, storage) {
	$scope.projects = {};

	storage.getProjects(function(data) {
		if (data === undefined) {
			if (console && console.log) {
				console.log("Fehler");
			}
		} else {
			$scope.projects = data;
		}
	});

});


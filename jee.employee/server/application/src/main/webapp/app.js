/**
 * JEE Employee Application
 */

var employeeApp = angular.module('employeeApp', [ 'ngRoute', 'ngResource', 'service.storage' ]);

employeeApp.config(function($routeProvider, $locationProvider) {
//	$locationProvider.html5Mode(true);
	$routeProvider.when('/Employees', {
		templateUrl : 'partials/employees.html'
	}).when('/Employees/:employeeId', {
		templateUrl : 'partials/employee.html'
	}).when("/", {
		templateUrl : 'partials/employees.html'
	}).when('/Projects', {
		templateUrl: 'partials/projects.html'
	}).when('/Project/:projectId', {
		templateUrl: 'partials/project.html'
	})
});

employeeApp.controller('ParentController', function($scope) {
	$scope.q = "";
});

employeeApp.controller('EmployeesController', function($scope, storage) {
	$scope.employees = [{}];

	storage.getEmployees(function(data) {
		if (data === undefined) {
			if (console && console.log) {
				console.log("Fehler");
			}
		} else {
			angular.copy(data, $scope.employees);
			console.log(Object.prototype.toString.call($scope.employees));
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
	$scope.projects = [{}];

	storage.getProjects(function(data) {
		if (data === undefined) {
			if (console && console.log) {
				console.log("Fehler");
			}
		} else {
			angular.copy(data, $scope.projects);
		}
	});

});


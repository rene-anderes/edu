/**
 * JEE Employee Application
 */

var employeeApp = angular.module('employeeApp', [ 'ngRoute', 'ngResource', 'service.storage' ]);

employeeApp.config(function($routeProvider, $locationProvider, $httpProvider) {
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
	}).when('/Error/:errorNo', {
		templateUrl: 'partials/error.html'
	})
	
	$httpProvider.interceptors.push(function($q, $location) {
		return {
			responseError: function(response) {
				if (console && console.log) {
					console.log("Fehler: " + response.status);
				}
				$location.path('/Error/' + response.status);      
				return $q.reject(response);
			}
	    };
	 });
});

employeeApp.controller('ParentController', function($scope) {
	$scope.q = "";
});

employeeApp.controller('EmployeesController', function($scope, storage) {
	$scope.employees = [{}];

	storage.getEmployees(function(data) {
		angular.copy(data.employee, $scope.employees);
	});

});

employeeApp.controller('EmployeeController', function($scope, storage, $routeParams) {
	$scope.employee = {};
	employeeId = $routeParams.employeeId;
	storage.getEmployee(employeeId, function(data) {
		$scope.employee = data;
	});
	storage.getAddress(employeeId, function(address) {
		$scope.address = address;
	});
});

employeeApp.controller('ProjectsController', function($scope, storage) {
	$scope.projects = [{}];

	storage.getProjects(function(data) {
		angular.copy(data, $scope.projects);
	});

});

employeeApp.controller('ErrorController', function($scope, $routeParams) {
	$scope.status = $routeParams.errorNo;
});


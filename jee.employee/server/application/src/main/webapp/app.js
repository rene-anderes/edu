/**
 * JEE Employee Application
 */

var employeeApp = angular.module('employeeApp', ['service.storage']);

employeeApp.controller('employeesCtrl', ['$scope', 'storage', function($scope, storage) {
	
	$scope.employees = {};
	
	storage.getEmployees(function(data){
		if(data === undefined) {
			if (console && console.log) {
				console.log("Fehler");
			}
		} else {
			$scope.employees = data;
		}
	});
	
}]);

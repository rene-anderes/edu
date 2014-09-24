
describe('employeesCtrl', function() {

	var $httpBackend, $rootScope, createController;
	
	var data = {
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
		}]
	};
	
	beforeEach(module('employeeApp'));
	
	beforeEach(inject(function($injector) {
		$httpBackend = $injector.get('$httpBackend');
		$httpBackend.when('GET', '/jee-employee/rest/employees').respond(data);
		$rootScope = $injector.get('$rootScope');
		var $controller = $injector.get('$controller');
		createController = function() {
			return $controller('employeesCtrl', {'$scope' : $rootScope });
		};
	}));
	
	it('sollte die Liste aller Mitarbeiter laden', function() {
		$httpBackend.expectGET('/jee-employee/rest/employees');
		var controller = createController();
		$httpBackend.flush();
		expect($rootScope.employees[0].lastname).toBe('Way');
	});
   
});

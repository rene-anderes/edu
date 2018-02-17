/**
 * AngulaJS Applikation "Cookbook"
 * René Anderes
 */

var myApp = angular.module('recipeApp', ['ngSanitize', 'ngRoute', 'ngMaterial', 'service.storage', 'service.notification']);

myApp.config(['$locationProvider', '$httpProvider', '$mdThemingProvider', '$mdIconProvider',
              function($locationProvider, $httpProvider, $mdThemingProvider, $mdIconProvider, httpInterceptor) {
	
	$httpProvider.interceptors.push('httpInterceptor');
	
	$mdThemingProvider.theme('default').primaryPalette('indigo').accentPalette('deep-orange');
	
	$mdIconProvider
		.icon("menu", "./svg/menu.svg", 24)
		.icon("more_vert", "./svg/more_vert.svg", 24)
		.icon("delete", "./svg/delete_black.svg", 24)
		.icon("add", "./svg/add_black.svg", 24);
}]);

myApp.controller('recipeCtrl', ['$scope', 'storage', 'notification', '$log', '$mdSidenav', '$mdDialog', '$mdMedia', 'credentials', 
                                function ($scope, storage, notification, $log, $mdSidenav, $mdDialog, $mdMedia, credentials) {
	$scope.recipes = [];
	$scope.recipe = {};
	$scope.self = this;
	
	var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
	var parentElement = angular.element(document.body);
	$scope.$watch(function() {
		return $mdMedia('xs') || $mdMedia('sm');
    }, function(wantsFullScreen) {
     	$scope.customFullscreen = (wantsFullScreen === true);
    });
	this.selected = null;
	this.credentials = credentials;
	
	storage.getRecipes(function(data) {
		$scope.recipes = data;
	});
	
	notification.onCreateItem($scope, function(recipeId) {
		storage.getRecipes(function(data) {
    		$scope.recipes = data;
    		angular.forEach(data, function(recipe, key) {
    			if (recipe.id === recipeId) {
    				$scope.self.selectRecipe(recipe)
    			}
    		});
    	});
    });  
	notification.onChangeItem($scope, function(recipeId) {
		storage.getRecipes(function(data) {
    		$scope.recipes = data;
    		angular.forEach(data, function(recipe, key) {
    			if (recipe.id === recipeId) {
    				$scope.self.selectRecipe(recipe)
    			}
    		});
    	});
    }); 
	notification.onDeleteItem($scope, function(recipeId) {
		storage.getRecipes(function(data) {
    		$scope.recipes = data;
    		if (data) {
    			$scope.self.selectRecipe($scope.recipes[0])
    		}
    	});
    }); 
	
	var getRecipe = function (recipeId) {
		storage.getRecipe(recipeId, function(data) {
			$scope.recipe = data;
		});
	};
	
	var updateRecipe = function () {
		storage.updateRecipe($scope.recipe.id, $scope.recipe, handleRecipeCallback, credentials);
	}
	
	var handleRecipeCallback = function(data) {
		if (data === undefined) {
			$mdDialog.show(
				$mdDialog.alert()
			        .parent(parentElement)
			        .clickOutsideToClose(true)
			        .title('Fehler bei der Verbindung zum Server')
			        .textContent('Die Daten konnten nicht auf dem Server gespeichert werden.')
			        .ariaLabel('Fehler')
			        .ok('OK')
				);
		} else {
			$log.info("Rezept gespeichert: " + data);
		}
	}
	
	var saveNewRecipe = function (recipe) {
		$scope.recipe = recipe;
		storage.saveRecipe(recipe, handleRecipeCallback, credentials);
	}
	
	var deleteRecipe = function () {
		storage.deleteRecipe($scope.recipe.id, handleRecipeCallback, credentials);
	}
	
	this.selectRecipe = function (recipe) {
		this.selected = angular.isNumber(recipe) ? $scope.recipes[recipe] : recipe;
	    $log.debug("selectRecipe: " + this.selected.id);
	    getRecipe(this.selected.id);
    }
	
	
	var colors = ['#ffebee', '#ffcdd2', '#ef9a9a', '#e57373', '#ef5350', '#f44336', '#e53935', '#d32f2f', '#c62828', '#b71c1c', '#ff8a80', '#ff5252', '#ff1744', '#d50000', '#f8bbd0', '#f48fb1', '#f06292', '#ec407a', '#e91e63', '#d81b60', '#c2185b', '#ad1457', '#880e4f', '#ff80ab', '#ff4081', '#f50057', '#c51162', '#e1bee7', '#ce93d8', '#ba68c8', '#ab47bc', '#9c27b0', '#8e24aa', '#7b1fa2', '#4a148c', '#ea80fc', '#e040fb', '#d500f9', '#aa00ff', '#ede7f6', '#d1c4e9', '#b39ddb', '#9575cd', '#7e57c2', '#673ab7', '#5e35b1', '#4527a0', '#311b92', '#b388ff', '#7c4dff', '#651fff', '#6200ea', '#c5cae9', '#9fa8da', '#7986cb', '#5c6bc0', '#3f51b5', '#3949ab', '#303f9f', '#283593', '#1a237e', '#8c9eff', '#536dfe', '#3d5afe', '#304ffe', '#e3f2fd', '#bbdefb', '#90caf9', '#64b5f6', '#42a5f5', '#2196f3', '#1e88e5', '#1976d2', '#1565c0', '#0d47a1', '#82b1ff', '#448aff', '#2979ff', '#2962ff', '#b3e5fc', '#81d4fa', '#4fc3f7', '#29b6f6', '#03a9f4', '#039be5', '#0288d1', '#0277bd', '#01579b', '#80d8ff', '#40c4ff', '#00b0ff', '#0091ea', '#e0f7fa', '#b2ebf2', '#80deea', '#4dd0e1', '#26c6da', '#00bcd4', '#00acc1', '#0097a7', '#00838f', '#006064', '#84ffff', '#18ffff', '#00e5ff', '#00b8d4', '#e0f2f1', '#b2dfdb', '#80cbc4', '#4db6ac', '#26a69a', '#009688', '#00897b', '#00796b', '#00695c', '#a7ffeb', '#64ffda', '#1de9b6', '#00bfa5', '#e8f5e9', '#c8e6c9', '#a5d6a7', '#81c784', '#66bb6a', '#4caf50', '#43a047', '#388e3c', '#2e7d32', '#1b5e20', '#b9f6ca', '#69f0ae', '#00e676', '#00c853', '#f1f8e9', '#dcedc8', '#c5e1a5', '#aed581', '#9ccc65', '#8bc34a', '#7cb342', '#689f38', '#558b2f', '#33691e', '#ccff90', '#b2ff59', '#76ff03', '#64dd17', '#f9fbe7', '#f0f4c3', '#e6ee9c', '#dce775', '#d4e157', '#cddc39', '#c0ca33', '#afb42b', '#9e9d24', '#827717', '#f4ff81', '#eeff41', '#c6ff00', '#aeea00', '#fffde7', '#fff9c4', '#fff59d', '#fff176', '#ffee58', '#ffeb3b', '#fdd835', '#fbc02d', '#f9a825', '#f57f17', '#ffff8d', '#ffff00', '#ffea00', '#ffd600', '#fff8e1', '#ffecb3', '#ffe082', '#ffd54f', '#ffca28', '#ffc107', '#ffb300', '#ffa000', '#ff8f00', '#ff6f00', '#ffe57f', '#ffd740', '#ffc400', '#ffab00', '#fff3e0', '#ffe0b2', '#ffcc80', '#ffb74d', '#ffa726', '#ff9800', '#fb8c00', '#f57c00', '#ef6c00', '#e65100', '#ffd180', '#ffab40', '#ff9100', '#ff6d00', '#fbe9e7', '#ffccbc', '#ffab91', '#ff8a65', '#ff7043', '#ff5722', '#f4511e', '#e64a19', '#d84315', '#bf360c', '#ff9e80', '#ff6e40', '#ff3d00', '#dd2c00', '#d7ccc8', '#bcaaa4', '#795548', '#d7ccc8', '#bcaaa4', '#8d6e63', '#eceff1', '#cfd8dc', '#b0bec5', '#90a4ae', '#78909c', '#607d8b', '#546e7a', '#cfd8dc', '#b0bec5', '#78909c'];
	this.tiles = [];
	this.colorTiles = function() {
		this.tiles = [];
		for (var i = 0; i < $scope.recipe.ingredients.length; i++) {
			this.tiles.push({
				color : randomColor(),
				colspan : randomSpan(),
				rowspan : randomSpan()
			});
		}
		$log.info("Tiles: " + this.tiles);
	};
	
	function randomColor() {
		return colors[Math.floor(Math.random() * colors.length)];
	}
	
	function randomSpan() {
		var r = Math.random();
		if (r < 0.8) {
			return 1;
		} else if (r < 0.9) {
			return 1;
		} else {
			return 2;
		}
	}

	
	this.toggleList = function toggleList() {
		$mdSidenav('left').toggle();
	}

	this.showDeleteDialog = function (event) {
		var confirm = $mdDialog.confirm()
        	.title('Möchtest Du das Rezept löschen?')
        	.textContent('Das angezeigte Rezept wird gelöscht....')
        	.ariaLabel('Rezept löschen')
        	.targetEvent(event)
        	.ok('Please do it!')
        	.cancel('Cancel');
		$mdDialog.show(confirm).then(function() {
			$log.info("Rezept wird gelöscht.");
			deleteRecipe();
		}, function() {
			$log.info("Rezept wird NICHT gelöscht.");
		});
	}
	
	this.showInfoDialog = function (event) {
		$mdDialog.show({
			controller: InfoDialogController,
			templateUrl: './partials/sysinfos.tmpl.html',
			parent: parentElement,
			targetEvent: event,
			clickOutsideToClose: true,
			fullscreen: useFullScreen
		});
		function InfoDialogController($scope, $mdDialog) {
			storage.getSystemInfos( function(data) {
				$scope.systemInfos = data;
			});
		    $scope.closeDialog = function() {
		    	$mdDialog.hide();
		    }
		}
	};
	
	this.showBackupDialog = function (event) {
		var backupCallback = function(data) {
			var backupInfo = $mdDialog.alert()
	        	.parent(parentElement)
	        	.clickOutsideToClose(true)
	        	.title('Datenbank Backup')
	        	.textContent("Backup-Path: " + data.backuppath)
	        	.ariaLabel('Datenbank Backup')
	        	.ok('OK');
	        $mdDialog.show(backupInfo);	
		}
		storage.makeBackup(backupCallback, credentials)
	}
	
	this.showLogonDialog = function (event) {
        $mdDialog.show({
			controller: LogonDialogController,
			templateUrl: './partials/logon.tmpl.html',
			parent: parentElement,
			targetEvent: event,
			clickOutsideToClose: false,
			fullscreen: useFullScreen
        });
        function LogonDialogController($scope, $mdDialog) {
        	$scope.credentials = angular.copy(credentials);
        	$scope.checked = false;
        	$scope.cancelDialog = function() {
        		$mdDialog.hide();
        	}
            $scope.okDialog = function() {
            	angular.copy($scope.credentials, credentials);
            	$mdDialog.hide();
            }
            $scope.checkLogon = function() {
            	storage.checkLogonData(checkLogonCallback, $scope.credentials);
            }
            var checkLogonCallback = function(data) {
        		if (data === 200) {
        			$scope.credentials.logonOk = true;
        			$scope.checked = true;
        		} else {
        			$scope.credentials.logonOk = false;
        			$scope.checked = false;
        		}
        	}
        }
    };
    
    this.showNewDialog = function (event) {
    	var recipe = {};
    	recipe.tags = [];
    	recipe.ingredients = [ { "portion": "", "description": "",	"comment": "" } ];
    	recipe.image = null;
    	$mdDialog.show({
			controller: EditDialogController,
			templateUrl: './partials/recipe.edit.tmpl.html',
			parent: parentElement,
			targetEvent: event,
			clickOutsideToClose: false,
			fullscreen: useFullScreen,
			locals: { recipe: recipe }
        }).then(function(save) {
        	$log.info("save new recipe");
        	saveNewRecipe(recipe);
        }, function() {
        	$log.info("Cancel");
        });
    };
    
    this.showEditDialog = function showEditDialog(event) {
    	$log.info("Edit Rezept: " + $scope.recipe.title)
        $mdDialog.show({
			controller: EditDialogController,
			templateUrl: './partials/recipe.edit.tmpl.html',
			parent: parentElement,
			targetEvent: event,
			clickOutsideToClose: false,
			fullscreen: useFullScreen,
			locals: { recipe: $scope.recipe }
        }).then(function(save) {
        	$log.info("Save");
        	updateRecipe();
        }, function() {
        	$log.info("Cancel");
        	getRecipe($scope.recipe.id);
        });
    };
    
    function EditDialogController($scope, $mdDialog, $timeout, recipe) {
    	$scope.recipe = recipe;
    	$scope.radioData = [
    	                    { label: '1', value: 1 },
    	                    { label: '2', value: 2 },
    	                    { label: '3', value: 3 },
    	                    { label: '4', value: 4 },
    	                    { label: '5', value: 5 }
    	                  ];
    	$scope.selectedItem = null;
    	$scope.searchText = null;
    	$scope.querySearch = querySearch;
    	$scope.tags = [ ];
    	storage.getTags(function(values) {
    		angular.forEach(values, function(tag, key) {
    			if (tag.name != null) {
    				this.push(tag.name);
    			}
    		  }, $scope.tags);
    	});
    	function querySearch(query) {
    	      return $scope.tags.filter(createFilterFor(query));
    	};
    	function createFilterFor(query) {
    		var lowercaseQuery = angular.lowercase(query);
    		return function filterFn(tag) {
    			return (tag.indexOf(lowercaseQuery) === 0);
    		};
    	};
    	$scope.cancel = function() {
    		$mdDialog.cancel();
    	};
    	$scope.save = function() {
    		$mdDialog.hide();
    	};
    	$scope.deleteIngredient = function(index) {
    		$log.info("Delete Zutat mit index " + index);
    		$scope.recipe.ingredients.splice(index, 1);
    	};
    	$scope.addIngredient = function() {
    		ingredient = { portion: "", description: "", comment: "" };
        	$scope.recipe.ingredients.push(ingredient);
    	};
    };
}]);

myApp.factory('credentials', function() {
	return { username: '', password: '', logonOk: true };
});

myApp.factory('httpInterceptor', function ($q, $location) { 
    return {
        responseError: function (rejection) {
        	alert("Fehler beim Aufruf des Servers. Statuscode:" + rejection.status);
            return $q.reject(rejection);
        }
    };
});

myApp.directive('ckEditor', [ function () {
    return {
        require: '?ngModel',
        restrict: 'C',
        link: function (scope, elm, attr, model) {
            var isReady = false;
            var data = [];
            var ck = CKEDITOR.replace(elm[0]);
            CKEDITOR.config.entities = false ;
            CKEDITOR.config.toolbar_Basic = [
                 { name: 'basicstyles', items : [ 'Bold','Italic','Strike','-','RemoveFormat' ] },
                 { name: 'paragraph', items : [ 'NumberedList','BulletedList' ] },
                 { name: 'document', items : [ 'Source' ] }
            ];
            CKEDITOR.config.font_names = 'Comic Sans MS';
            CKEDITOR.config.toolbar = 'Basic';
            function setData() {
                if (!data.length) {
                    return;
                }
                var d = data.splice(0, 1);
                ck.setData(d[0] || '<span></span>', function () {
                    setData();
                    isReady = true;
                });
            }

            ck.on('instanceReady', function (e) {
                if (model) {
                    setData();
                }
            });
            
            elm.bind('$destroy', function () {
                ck.destroy(false);
            });

            if (model) {
                ck.on('change', function () {
                    scope.$apply(function () {
                        var data = ck.getData();
                        if (data == '<span></span>') {
                            data = null;
                        }
                        model.$setViewValue(data);
                    });
                });

                model.$render = function (value) {
                    if (model.$viewValue === undefined) {
                        model.$setViewValue(null);
                        model.$viewValue = null;
                    }

                    data.push(model.$viewValue);

                    if (isReady) {
                        isReady = false;
                        setData();
                    }
                };
            }
        }
    };
}]);


/*
 *	Storage für die Rezepte 
 */
var storageModule = angular.module('service.storage', [ 'service.notification' ]);

storageModule.config(function() {
	recipesData = [
  			  {
  			    "title": "Arabische Spaghetti",
  			    "id": "c0e5582e-252f-4e94-8a49-e12b4b047afb",
  			    "editingDate": 1396703408657
  			  },
  			  {
  			    "title": "Asiatische Spaghetti",
  			    "id": "77acc29a-6809-4183-8945-e8b759e9b3f0",
  			    "editingDate": 1396703504750
  			  },
  			  {
  			    "title": "Avocadotatar",
  			    "id": "adf99b55-4804-4398-af4e-e37ec2c692c7",
  			    "editingDate": 1258806742228
  			  },
  			  {
  			    "title": "Bananen-Cappuccino",
  			    "id": "d60588da-7971-42f1-b514-85e1bfa02b1e",
  			    "editingDate": 1396703680664
  			  },
  			  {
  			    "title": "Bananen-Creme",
  			    "id": "0e89ed78-68a8-4218-a48a-2280f8facce7",
  			    "editingDate": 1367146270837
  			  },
  			  {
  			    "title": "Basilikum-Pesto",
  			    "id": "a9a8feed-4010-4a86-aa06-6f010d02edf9",
  			    "editingDate": 1402422197726
  			  },
  			  {
  			    "title": "Cannelloni mit Ricotta und Spinat",
  			    "id": "5e1de5e3-5f29-4524-adf9-f2dcb8556360",
  			    "editingDate": 1396703785515
  			  },
  			  {
  			    "title": "Cremige Käsepolenta mit Spinat",
  			    "id": "dfffb3de-8cc9-44fb-b323-832905b51cf8",
  			    "editingDate": 1396703864413
  			  },
  			  {
  			    "title": "Curry-Kokosmilch Souce mit Pasta",
  			    "id": "4689b3c7-dccb-4a51-8f99-e28a2479e611",
  			    "editingDate": 1396702186282
  			  },
  			  {
  			    "title": "Filetgratin",
  			    "id": "25639975-2c80-427f-8e13-664f4552c176",
  			    "editingDate": 1396703955202
  			  },
  			  {
  			    "title": "Gefülltes Filet",
  			    "id": "8620dcee-4bba-4da8-99af-5f4fd98fdb24",
  			    "editingDate": 1396703998666
  			  },
  			  {
  			    "title": "Gemüsegratin mit Gschwellti",
  			    "id": "feb96fd6-1555-471a-b4af-10e9cbca16f6",
  			    "editingDate": 1396704087388
  			  },
  			  {
  			    "title": "Gerstotto mit Spinat und Bünderfleisch",
  			    "id": "be94aed0-d61f-4301-8dfd-e90f266873af",
  			    "editingDate": 1335531834849
  			  },
  			  {
  			    "title": "Grand Marnier Parfait",
  			    "id": "ad857cb4-51c1-4e66-af5f-543867d48276",
  			    "editingDate": 1333970083953
  			  },
  			  {
  			    "title": "Götterspeise",
  			    "id": "6eb844b3-64b6-4adf-88ca-a40532f63979",
  			    "editingDate": 1402591659195
  			  },
  			  {
  			    "title": "Hasli-Filet",
  			    "id": "c5a773b1-0165-4717-b15b-341c974fe8ba",
  			    "editingDate": 1396704194692
  			  },
  			  {
  			    "title": "Humus",
  			    "id": "41b7e03e-0cf8-470e-babf-19c8a87c4e33",
  			    "editingDate": 1258806800394
  			  },
  			  {
  			    "title": "Kalbsmedaillons mit Aceto-Rotwein-Sauce",
  			    "id": "3d5251e1-1a5a-4a97-982b-885b8ad885d0",
  			    "editingDate": 1396704279743
  			  },
  			  {
  			    "title": "Knöpfli mit Speck und Lauch",
  			    "id": "144aa734-727a-48b6-bcb4-225c563bddb3",
  			    "editingDate": 1396704367533
  			  },
  			  {
  			    "title": "Kürbissuppe",
  			    "id": "e05ede6c-30d4-4e5e-ac10-2430c4404abe",
  			    "editingDate": 1396704404147
  			  },
  			  {
  			    "title": "Maispoularde im Honigmantel",
  			    "id": "2c268064-33cf-4ad4-b358-7f988ec6a265",
  			    "editingDate": 1261416699648
  			  },
  			  {
  			    "title": "Netzmelonen-Salat",
  			    "id": "f072cee8-4526-4582-89d8-b1a7c52097b1",
  			    "editingDate": 1258806839998
  			  },
  			  {
  			    "title": "Omeletten-Gemüse-Gratin",
  			    "id": "d8d2fefb-e302-4d97-a43a-27cf54cdc8fe",
  			    "editingDate": 1396704513427
  			  },
  			  {
  			    "title": "Paprika-Poulet",
  			    "id": "6ca42ac1-82a2-41e4-a138-b4ebab186053",
  			    "editingDate": 1396704620465
  			  },
  			  {
  			    "title": "Pasta al Limone",
  			    "id": "8177f582-f5b7-4c27-9f92-80ed7f334ea0",
  			    "editingDate": 1340387490148
  			  },
  			  {
  			    "title": "Pasta mit Gemüse-Tomaten-Sauce",
  			    "id": "481ecb80-d922-43a1-907b-e5610a1fca70",
  			    "editingDate": 1396703623115
  			  },
  			  {
  			    "title": "Pasta mit Ofengemüse",
  			    "id": "8b339172-ab74-4bfc-906f-3f2327293f05",
  			    "editingDate": 1396704755794
  			  },
  			  {
  			    "title": "Pasta-Gemüse-Gratin mit Curry",
  			    "id": "86403fed-8019-4ee9-8295-8b9f74159d23",
  			    "editingDate": 1258806954378
  			  },
  			  {
  			    "title": "Penne mit Gorgonzola-Spinat Sauce",
  			    "id": "0c029f54-77a2-4da0-a32c-14c1d1c5a8f8",
  			    "editingDate": 1396704882376
  			  },
  			  {
  			    "title": "Pizza Grundrezept",
  			    "id": "06ea363f-cebe-4119-affa-442dc903552c",
  			    "editingDate": 1396704961230
  			  },
  			  {
  			    "title": "Rassiger Rahmsauce mit Getrockneten Tomaten und Pasta",
  			    "id": "254c3152-6d4a-4b2d-881a-5f6f3ede2b27",
  			    "editingDate": 1396705006691
  			  },
  			  {
  			    "title": "Risotto mit Spinat und Gorgonzola",
  			    "id": "2a8a37b7-5a3d-4f3d-a5d2-ba7fc741e8e4",
  			    "editingDate": 1396705166433
  			  },
  			  {
  			    "title": "Rüebli-Ingwer-Orangen Suppe",
  			    "id": "338befa1-86e4-4b4a-a1bb-1bdd71773ef5",
  			    "editingDate": 1396705211355
  			  },
  			  {
  			    "title": "Senf-Pouletschnitzel",
  			    "id": "9a194295-34e3-4f00-b2df-5e495fa61d9b",
  			    "editingDate": 1396705309732
  			  },
  			  {
  			    "title": "Spaghetti alla Carbonara",
  			    "id": "31a250c0-5331-4a45-afc8-c014e773d053",
  			    "editingDate": 1361051580948
  			  },
  			  {
  			    "title": "Spaghetti mit Poulet-Carbonara",
  			    "id": "7af66332-8964-4827-9d39-28130af2d8d8",
  			    "editingDate": 1249812458590
  			  },
  			  {
  			    "title": "Spaghetti mit Zucchini-Pesto",
  			    "id": "65bb80d6-c985-45af-8da4-f53ef6b121b1",
  			    "editingDate": 1396705445299
  			  },
  			  {
  			    "title": "Spaghetti mit roter Pesto",
  			    "id": "f84c8950-00d6-483d-98ca-42cfca7d1248",
  			    "editingDate": 1396705504586
  			  },
  			  {
  			    "title": "Tagliolini mit Trüffelbrie",
  			    "id": "6fc0a3bf-d86f-40ec-9c4d-b4b47007f8f1",
  			    "editingDate": 1396703286908
  			  },
  			  {
  			    "title": "Tiramisu",
  			    "id": "af8d0457-49d1-4452-99c6-82838fd4b991",
  			    "editingDate": 1396705609150
  			  },
  			  {
  			    "title": "Torroneparfait",
  			    "id": "51bc9bbf-69fe-42af-85bb-c419272e79bd",
  			    "editingDate": 1396705658587
  			  }
  			];
	recipeData = {
			  "id": "c0e5582e-252f-4e94-8a49-e12b4b047afb",
			  "noOfPeople": "2",
			  "ingredients": [
			    {
			      "description": "Spaghetti",
			      "portion": "250",
			      "comment": ""
			    },
			    {
			      "description": "Sultaninen",
			      "portion": "2 EL",
			      "comment": ""
			    },
			    {
			      "description": "Zwiebel",
			      "portion": "1",
			      "comment": ""
			    },
			    {
			      "description": "Knoblizehen",
			      "portion": "2",
			      "comment": ""
			    },
			    {
			      "description": "Kokosmilch",
			      "portion": "200 mL",
			      "comment": ""
			    },
			    {
			      "description": "Pouletfleisch",
			      "portion": "150 g",
			      "comment": ""
			    },
			    {
			      "description": "Curry",
			      "portion": "",
			      "comment": ""
			    },
			    {
			      "description": "Salz, Pfeffer und Olivenöl",
			      "portion": "",
			      "comment": ""
			    }
			  ],
			  "tags": [
			    "pasta",
			    "fleisch"
			  ],
			  "title": "Arabische Spaghetti",
			  "preparation": "Pouletfleisch in schmale Streifen schneiden und kurz anbraten. Aus der Pfanne nehmen und bei Seite stellen.<br><br>Wasser aufsetzen, salzen und Spaghetti al dente kochen.<br><br>In der Zwischenzeit die fein gehackte Zwiebel und den in Würfelchen geschnittenen Knobli andämpfen. Den Curry dazu geben und kurz mitdämpfen (nicht zu heiss). Anschliessend die Kokosmilch dazu geben, aufkochen lassen, Hitze reduzieren und ca. 10 Min köcheln lassen. Ca. 2 Minuten bevor die Pasta al dente sind das Pouletfleisch in die Sauce geben und nur noch warm werden lassen. Kurz bevor die Spaghetti mit der Sauce vermischt wird, die Sultaninen in die Sauce geben. Die Sauce und die Spaghetti nach dem mischen zugedeckt ziehen lassen.<br><br>Die Konsistenz kann -je nach Geschmack- etwas zu flüssig sein. Dann einfach mit einem Teelöffel Maisstärke (mit etwas kaltem Wasser anrühren) in die Sauce geben und kurz aufkochen.<br>",
			  "preample": "Da bei diesem Rezept das Scharfe (Curry) mit dem Süssen (Sultaninen) gemischt wird, habe ich diese Rezept \"Arabische Spaghetti\" benannt.<br>",
			  "rating": 5,
			  "addingDate": 1250959818424,
			  "editingDate": 1396703408657,
			  "image": ""
			};
});

storageModule.factory('storage', function ($http, notification) {
		
	    var getRecipes = function(callback) {
	    	callback(recipesData);
	    };
	    
	    var getRecipe = function(recipeId, callback) {
	    	if (recipeId === "c0e5582e-252f-4e94-8a49-e12b4b047afb") {
	    		callback(recipeData);
	    	} else {
	    		callback(undefined);
	    	}
	    };
	    
	    var updateRecipe = function(recipeId, recipe, callback, credentials) {
	    	if (recipeId === "c0e5582e-252f-4e94-8a49-e12b4b047afb") {
	    		callback(recipeId);
	    		notification.changeItem(recipeId);
	    	} else {
				if (console && console.log) {
					console.log("Update Recipe: POST Fehler " + 500);
				}
				callback(undefined);
			};
	    };
	    
	    var saveRecipe = function(recipe, callback, credentials) {
	    	if (recipeId === "c0e5582e-252f-4e94-8a49-e12b4b047afb") {
	    		callback(recipeId);
	    		notification.changeItem(recipeId);
	    	}  else {
				if (console && console.log) {
					console.log("Save Recipe: POST Fehler " + 500);
				}
				callback(undefined);
			};
	    };			    
	    
	    var deleteRecipe = function(recipeId, callback, credentials) {
	    	if (recipeId === "c0e5582e-252f-4e94-8a49-e12b4b047afb") {
	    		callback(recipeId);
	    		notification.deleteItem(recipeId);
	    		
	    	} else {
				if (console && console.log) {
					console.log("Delete Recipe: DELETE Fehler " + status);
				}
				callback("Fehler beim löschen des Rezepts.");
			};
	    };
	    
	    var getTags = function(callback) {
	    	data = [
	    	        {
	    	            "name": "dessert",
	    	            "quantity": 6
	    	          },
	    	          {
	    	            "name": "fleisch",
	    	            "quantity": 11
	    	          },
	    	          {
	    	            "name": "fleischlos",
	    	            "quantity": 16
	    	          },
	    	          {
	    	            "name": "gemüse",
	    	            "quantity": 5
	    	          },
	    	          {
	    	            "name": "kalt",
	    	            "quantity": 3
	    	          },
	    	          {
	    	            "name": "pasta",
	    	            "quantity": 15
	    	          },
	    	          {
	    	            "name": "pizza",
	    	            "quantity": 1
	    	          },
	    	          {
	    	            "name": "suppe",
	    	            "quantity": 2
	    	          },
	    	          {
	    	            "name": "vorspeise",
	    	            "quantity": 5
	    	          },
	    	          {
	    	            "name": "winter",
	    	            "quantity": 5
	    	          }
	    	        ];
	    	callback(data);
	    };
	    
	    var makeBackup = function(callback, credentials) {
	    	callback("Backup erfolgreich. Pfad: Kein physisches Backup - nur Test");
	    };
	    
	    /* ------------ Reveal public API. ------------- */
		return {
			getRecipes: getRecipes,
			getRecipe: getRecipe,
            updateRecipe: updateRecipe,
            saveRecipe: saveRecipe,
            deleteRecipe: deleteRecipe,
            getTags: getTags,
            makeBackup: makeBackup
        };
	});

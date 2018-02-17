/*
 * Konstanten für die Applikation "cookbook"
 */

angular.module('coobook.constant', [])
	.constant('cookbook', {
		getRecipeUrl: "http://localhost:8081/resources-api/recipes",
		getRecipesUrl: "http://localhost:8081/resources-api/recipes",
		getTagsUrl: "http://localhost:8081/resources-api/recipes/tags",
		backupUrl: "http://localhost:8081/resources-api/recipes/backup",
		systemInfosUrl: "http://localhost:8081/resources-api/infos",
		checkLogonData: "http://localhost:8081/resources-api/checkLogonData"
});

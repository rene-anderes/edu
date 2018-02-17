/*
 * Konstanten für die Applikation "cookbook"
 */

angular.module('coobook.constant', [])
	.constant('cookbook', {
		getRecipeUrl: "/services/recipes",
		getRecipesUrl: "/services/recipes",
		getTagsUrl: "/services/recipes/tags",
		backupUrl: "/services/recipes/backup",
		systemInfosUrl: "/services/infos",
		checkLogonData: "/services/checkLogonData"
});

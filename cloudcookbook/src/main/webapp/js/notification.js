/**
 * Message Bus für das Kochbuch
 */
angular.module('service.notification', [])
    .factory('notification', function ($rootScope) {
    	
        var ITEM_CREATED = 'itemCreated';
        var ITEM_DELETED = 'itemDeleted';
        var ITEM_CHANGED = 'itemChanged';

        /* ------------- ITEM_CREATED ------------------ */
        var createItem = function (item) {
            $rootScope.$broadcast(ITEM_CREATED, {item: item});
        };

        var onCreateItem = function($scope, handler) {
            $scope.$on(ITEM_CREATED, function(event, args) {
                handler(args.item);
            });
        };
        /* ------------/ ITEM_CREATED ------------------ */
        
        /* ------------- ITEM_CHANGED ------------------ */
        var changeItem = function (item) {
        	$rootScope.$broadcast(ITEM_CHANGED, {item: item});
        };

        var onChangeItem = function($scope, handler) {
            $scope.$on(ITEM_CHANGED, function(event, args) {
                handler(args.item);
            });
        };
        /* ------------/ ITEM_CHANGED ------------------ */
        
        /* ------------- ITEM_DELETED ------------------ */
        var deleteItem = function (item) {
            $rootScope.$broadcast(ITEM_DELETED, {item: item});
        };

        var onDeleteItem = function ($scope, handler) {
            $scope.$on(ITEM_DELETED, function (event, args) {
                handler(args.item);
            });
        };
        /* ------------/ ITEM_DELETED ------------------ */

        /* ------------ Reveal public API. ------------- */ 
        return {
            createItem: createItem,
            onCreateItem: onCreateItem,
            changeItem: changeItem,
            onChangeItem: onChangeItem,
            deleteItem: deleteItem,
            onDeleteItem: onDeleteItem
        };
    });


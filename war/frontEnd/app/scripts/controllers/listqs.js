'use strict';

/**
 * @ngdoc function
 * @name icreateApp.controller:ListqsCtrl
 * @description
 * # ListqsCtrl
 * Controller of the icreateApp
 */
angular.module('icreateApp')
  .controller('ListqsCtrl', function ($scope,$routeParams) {
    $scope.id = $routeParams.uid;
    $scope.user = {};
    $scope.qsName = "";

    $scope.getUser = function(){
		gapi.client.userendpoint.getUser({"id":$scope.id}).execute(function(resp) {
			$scope.user = resp;
			$scope.$apply();
			console.log(resp);
		});
    }

    $scope.addQS = function(){
    	var qs = {};
    	qs.name = $scope.qsName;
    	if(angular.isUndefined($scope.user.qs))
    		$scope.user.qs = [];	
    	$scope.user.qs.push(qs);
    	$scope.updateUser();
		$scope.qsName = "";
    }

    $scope.updateUser = function(){
		gapi.client.userendpoint.updateUser($scope.user).execute(function(resp) {
			$scope.getUser();
		});

    }
    $scope.removeQS = function(index){
    	console.log(index);
    	$scope.user.qs.splice(index, 1);
    	$scope.updateUser();
    }     

});

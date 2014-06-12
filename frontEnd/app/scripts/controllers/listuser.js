'use strict';

/**
 * @ngdoc function
 * @name icreateApp.controller:ListuserCtrl
 * @description
 * # ListuserCtrl
 * Controller of the icreateApp
 */
angular.module('icreateApp')
  .controller('ListuserCtrl', function ($scope) {
    $scope.users = [];
    $scope.nusId = "";

    $scope.listUser = function(){
		gapi.client.userendpoint.listUser().execute(function(resp) {
			$scope.users = resp.items;
			$scope.$apply();
			console.log(resp);
		});
    }

    $scope.addUser = function(){
    	var u = {};
    	u.nusId = $scope.nusId;
		gapi.client.userendpoint.insertUser(u).execute(function(resp) {
			$scope.listUser();
			console.log(resp);
		});
    }

    $scope.updateUser = function(){
		gapi.client.userendpoint.updateUser($scope.user).execute(function(resp) {
			$scope.getUser();
		});

    }
    $scope.removeQS = function(id){
    	console.log(id);
		gapi.client.userendpoint.removeUser({"id":id}).execute(function(resp) {
			$scope.listUser();
			console.log(resp);
		});
    }   
  });

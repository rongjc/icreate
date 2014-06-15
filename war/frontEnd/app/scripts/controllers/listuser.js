'use strict';

/**
 * @ngdoc function
 * @name icreateApp.controller:ListuserCtrl
 * @description
 * # ListuserCtrl
 * Controller of the icreateApp
 */
angular.module('icreateApp')
  .controller('ListuserCtrl', function ($scope, $window, $timeout) {
    $scope.users = [];
    $scope.nusId = "";
    $scope.isReady = true;
    $scope.isUserReady = false;
    $scope.isLoad = false;

    var countUp = function() {
        $scope.timeInMs+= 500;
        $timeout(countUp, 500);
    }

    $scope.init = function(){

        if(angular.isUndefined(gapi)){
            $timeout($scope.init, 1000);
        }
        else{
            gapi.client.load('userendpoint', 'v1', $scope.finishLoading, root);
        }
    }

    $scope.finishLoading = function(){
		$scope.isReady = true;
		$scope.listUser();
    }

    $scope.listUser = function(){
        $scope.isReady = false;
		gapi.client.userendpoint.listUser().execute(function(resp) {
			$scope.users = resp.items;
			$scope.isUserReady = true;
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
    //$scope.init();

  });

'use strict';

/**
 * @ngdoc function
 * @name icreateApp.controller:ListqnCtrl
 * @description
 * # ListqnCtrl
 * Controller of the icreateApp
 */
angular.module('icreateApp')
  .controller('ListqnCtrl', function ($scope,$routeParams) {
    $scope.keyString = $routeParams.keyString;
    $scope.qs = {};
    $scope.qn = "";
    $scope.answer = [''];

    $scope.getQuestions = function(){
    	
		gapi.client.questionsetendpoint.getQuestionSet({"id":$scope.keyString}).execute(function(resp) {
			$scope.qs = resp;
			$scope.$apply();
			console.log(resp);
		});
    }

    $scope.addQn = function(){
    	var qn = {};
    	qn.q = $scope.qn;
    	qn.a = $scope.answer;

    	if(angular.isUndefined($scope.qs.qList))
    		$scope.qs.qList = [];	
    	$scope.qs.qList.push(qn);
    	console.log($scope.qs);
    	$scope.updateQS();
		$scope.qn = "";
		$scope.answer = [''];
    }
    $scope.removeQn = function(index){
    	console.log(index);
    	$scope.qs.qList.splice(index,1);
    	console.log($scope.qs);
    	$scope.updateQS();
    }

    $scope.updateQS = function(){
		gapi.client.questionsetendpoint.updateQuestionSet($scope.qs).execute(function(resp) {
			$scope.getQuestions();
			console.log(resp);
		});

    }
    $scope.check = function(){
    	var temp = [];
    	var ti = 0;
    	for(var i = 0; i < $scope.answer.length; i++){
    		if($scope.answer[i] != ''){
    			temp[ti] = $scope.answer[i];
    			ti++;
    		}
    	}
    	$scope.answer = temp;
    	$scope.answer.push('');
    }  
  });

'use strict';

/**
 * @ngdoc function
 * @name icreateApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the icreateApp
 */
angular.module('icreateApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });

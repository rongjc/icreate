'use strict';

/**
 * @ngdoc function
 * @name icreateApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the icreateApp
 */
angular.module('icreateApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });

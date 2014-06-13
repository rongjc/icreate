'use strict';

/**
 * @ngdoc overview
 * @name icreateApp
 * @description
 * # icreateApp
 *
 * Main module of the application.
 */
angular
  .module('icreateApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .when('/listQn/:keyString', {
        templateUrl: 'views/listqn.html',
        controller: 'ListqnCtrl'
      })
      .when('/listQS/:uid', {
        templateUrl: 'views/listqs.html',
        controller: 'ListqsCtrl'
      })
      .when('/listUser', {
        templateUrl: 'views/listuser.html',
        controller: 'ListuserCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
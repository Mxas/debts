'use strict';

var loan = {};

var App = angular.module('loan', ['loan.filters', 'loan.services', 'loan.directives']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/home', {
        templateUrl: 'resources/views/home.html',
        controller: HomeController
    });

    $routeProvider.when('/new', {
        templateUrl: 'resources/views/new.html',
        controller: NewLoanController
    });
    
    $routeProvider.when('/my', {
        templateUrl: 'resources/views/my.html',
        controller: MyLoansController
    });

    $routeProvider.when('/contact', {
    	templateUrl: 'resources/views/about.html'
    });

    $routeProvider.otherwise({redirectTo: '/home'});
}]);

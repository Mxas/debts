'use strict';

/**
 * @constructor
 */
var HomeController = function($scope, $http) {


    $scope.fetchInfo = function() {
        $http.get('mvc/getLonaInterestRate.mvc').success(function(rate){
            $scope.rate = rate;
        });
    };

    $scope.fetchInfo();

};

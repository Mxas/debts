'use strict';

/**
 * @constructor
 */
var NewLoanController = function($scope, $http) {


    $scope.fetchInfo = function() {
        $http.get('cars/carlist.json').success(function(carList){
            $scope.cars = carList;
        });
    }



};

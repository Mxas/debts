'use strict';

/**
 * @constructor
 */
var NewLoanController = function($scope, $http) {

    $scope.evaluationCommand = {};
    $scope.error = false;
    $scope.errorMessage = '';

    $scope.resetForm = function() {
        $scope.resetError();
        $scope.evaluationCommand = {};
        $scope.editMode = false;
    };

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    };

    $scope.addNew = function(evaluationCommand) {
        $scope.resetError();
        evaluationCommand.messages = null;
        $http.post('mvc/evaluate.mvc', evaluationCommand).success(function(aaa) {
            $scope.evaluationCommand = aaa;            
        }).error(function() {
            alert("Something goes wrong, please try later...");
        });
    };
};

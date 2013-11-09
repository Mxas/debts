'use strict';

/**
 * @constructor
 */
var MyLoansController = function($scope, $http, $location) {

	$scope.evaluationCommand = {};

	$scope.extend = function(evalId) {
		$http.get('mvc/extend.mvc/' + evalId).success(function(aaa) {
			if ("OK" === aaa) {
				$scope.reftershHistory();
			} else {
				alert(aaa);
			}
		}).error(function() {
			alert("Something goes wrong, please try later...");
		});
		$scope.reftershHistory();
	};

	$scope.reftershHistory = function() {

		$http.post('mvc/getAll.mvc').success(function(aaa) {
			$scope.evaluationCommand = aaa;
		}).error(function() {
			alert("Something goes wrong, please try later...");
		});
	};

	$scope.reftershHistory();
};

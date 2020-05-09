var projectViewApp = angular.module('projectViewApp', []);
projectViewApp.controller('projectViewCtrl', function($scope) {
    $scope.summary = true;
    $scope.summaryTab = function() {       
        $scope.summary = true;
        $scope.samples = false;        
    }
    $scope.sampleTab = function() {         
        $scope.samples = true;
        $scope.summary = false;          
    }  
});
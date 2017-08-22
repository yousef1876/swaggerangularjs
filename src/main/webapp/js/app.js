'use strict';

var mainApp = angular.module("todoApp", ['ngRoute']);

mainApp.config(function($routeProvider) {
	$routeProvider
		.when('/seccode', {
			templateUrl: 'partial/secret.html',
			controller: 'SecretCtrl'
		})
		.when('/register', {
			templateUrl: 'partial/register.html',
			controller: 'RegisterCtrl'
		})
		.otherwise({template : 'partial/login.html'});
		
});

mainApp.controller('SecretCtrl', ['$scope','$routeParams', function($scope, $routeParams) {
	$scope.secretcode = function () {
		 
	     var  mobile = $('#mobile').val();
		

		    var url = "/ngapp/usr/secret";
		    var arr = {'mobile' : $('#mobile').val()}
		    $.ajax({
		        type: "POST",
		        url: url,
		        data:JSON.stringify(arr),
		        contentType: 'application/json; charset=utf-8',
		        dataType: "JSON",
		        success: function(resultData){
					
		            
					
					
					
   
                      $('#exampleModal').find('.modal-body').html(resultData.status + "\n" + resultData.code + "\n" + resultData.description);
                      
                 $("#exampleModal").modal();
		        }
		
		    });
	}
	
	
	$scope.redirect = function () {
		 
	     var  mobile = $('#mobile').val();
		

		   window.location.href = "/ngapp/#/register?mobile="+mobile;
	}
}]);


mainApp.controller('RegisterCtrl', function($scope) {
	 $scope.registration = function () {
		
   		     var mobile = $('#mobile').val();
   			 var secert = $('#code').val();
   			 
   			 
   			var url = "/ngapp/register/registration";
   	 	    var arr = {'mobile' : $('#mobile').val(),'code':$('#code').val()}
   	 	    $.ajax({
   	 	        type: "POST",
   	 	        url: url,
   	 	        data:JSON.stringify(arr),
   	 	        contentType: 'application/json; charset=utf-8',
   	 	        dataType: "JSON",
   	 	        success: function(resultData){
					
   	 	            $("#data-content").val(resultData.status + "\n" + resultData.code + "\n" + resultData.description)
					$("#exampleModal").modal();
					
   	 	        }
   	 	        
   	 	    });
     };


});

mainApp.controller('InitCtrl1', ['$scope','$routeParams', function($scope, $routeParams) {
	$scope.init = function () {
	  var param1 = $routeParams.mobile;
	 
	  $('#mobile').val(param1)
	}
	}]);
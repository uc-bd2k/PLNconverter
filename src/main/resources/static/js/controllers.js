/**
 * Created by chojnasm on 11/9/15.
 */
var appModule = angular.module('plnModule', []);

appModule.controller("MainCtrl", ['$http','$scope', function ($http, $scope) {

    var self = this;
    self.textArea = "IYQY[+80]IQSR";
    self.response = " ";
    self.waiting = false;
    self.showOutput = false;
    self.isPhosphorylation = 'No';
    self.motif;
    self.modification;
    self.pattern = /\[\+\d+]/;

    $scope.$watch(function() {return self.textArea}, function(newValue, oldValue){

        if(self.textArea.match(self.pattern)){
            self.isPhosphorylation = 'Yes';
            self.modification = self.textArea.match(self.pattern)[0];
            self.motif = self.textArea.replace(self.pattern,'');
        }else{
            self.isPhosphorylation = 'No';
        }
    });

    self.updateTextArea = function(value){
        self.textArea = value;
    }

    self.onSubmit = function(howToDisplay){

        console.log("Entered onSubmit");
        self.showInstruction = false;
        self.waiting = true;
        self.showOutput = true;


        //var url = "http://prosite.expasy.org/cgi-bin/prosite/PSScan.cgi?sig=IYQYIQSR&lineage=9606&db=sp&output=json";

        var url = 'api/convert/' + self.motif;
        $http.get(url)
            .success(function (data) {
                //console.log("NewValue: " + newValue);
                self.responseRaw = data.matchset;
                self.response = JSON.stringify(data.matchset);
                console.log("Response: " + self.response);
                self.waiting = false;

            })
            .error(function (data, status){
                console.log(data + ' Status: ' + status);
                self.waiting = false;
            });

        console.log("Done");

    }

}]);
/**
 * Created by chojnasm on 11/9/15.
 */
var appModule = angular.module('plnModule', []);

appModule.controller("MainCtrl", ['$http', function ($http) {

    var self = this;
    self.textArea = "IYQYIQSR";
    self.response = " ";
    self.waiting = false;
    self.showOutput = false;

    self.onSubmit = function(){

        console.log("Entered onSubmit");
        self.showInstruction = false;
        self.waiting = true;
        self.showOutput = true;


        //var url = "http://prosite.expasy.org/cgi-bin/prosite/PSScan.cgi?sig=IYQYIQSR&lineage=9606&db=sp&output=json";

        var url = 'api/convert/' + self.textArea;
        $http.get(url)
            .success(function (data) {
                //console.log("NewValue: " + newValue);
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
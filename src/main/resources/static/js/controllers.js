/**
 * Created by chojnasm on 11/9/15.
 */
var appModule = angular.module('plnModule', []);

appModule.controller("MainCtrl", ['$http', '$scope', function ($http, $scope) {

    var self = this;
    self.textArea = "IYQY[+80]IQSR\nY[+56]RPGTVALR\nK[+112.1]SAPATGGVK[+42]K[+56]PHR";
    self.response = " ";
    self.waiting = false;
    self.showOutput = false;
    self.isPhosphorylation = 'No';
    self.motif;
    self.modification;
    self.pattern = /\[\+[\d\.]+]/g;
    self.rowSplitPattern = /[,;\n]/;
    self.inputs = [];
    self.converted;

    $scope.$watch(function () {
        return self.textArea
    }, function (newValue, oldValue) {

        self.inputs = self.textArea
            .split(self.rowSplitPattern)
            .map(function (e) {
                return e.replace(self.pattern, '')
            });
    });

    self.updateTextArea = function (value) {
        self.textArea = value;
    }

    self.onSubmit = function () {

        console.log("Entered onSubmit");
        self.showInstruction = false;
        self.waiting = true;
        self.showOutput = true;
        self.converted = 0;
        self.responseRaw = [];
        self.response = [];


        //var url = "http://prosite.expasy.org/cgi-bin/prosite/PSScan.cgi?sig=IYQYIQSR&lineage=9606&db=sp&output=json";

        var url = 'api/convert/';

        for (var j = 0; j < self.inputs.length; j++) {

            var localMotif = self.inputs[j];

            (function (localMotif) {
                $http.get(url + self.inputs[j])
                    .success(function (data) {
                        //console.log("NewValue: " + newValue);
                        data.matchset.map(function (e) {
                            console.log(self.inputs[j] + ' ' + self.inputs[1] + ' ' + localMotif);
                            e.motif = localMotif;
                            console.log(e);
                            return e;
                        });

                        var matchset = data.matchset;

                        self.responseRaw = self.responseRaw.concat(matchset);
                        self.response = self.response.concat(JSON.stringify(matchset));

                        //console.log("ResponseRaw: " + self.responseRaw);

                        self.converted++;
                        if (self.converted == self.inputs.length) {
                            self.waiting = false;
                        }
                    })
                    .error(function (data, status) {
                        console.log(data + ' Status: ' + status);
                        self.waiting = false;
                    });
            })(localMotif);
        }

        console.log("Done");

    }

}]);
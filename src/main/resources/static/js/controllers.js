/**
 * Created by chojnasm on 11/9/15.
 */
var appModule = angular.module('plnModule', []);

appModule.controller("MainCtrl", ['$http', '$scope', function ($http, $scope) {

    var self = this;
    self.textArea = "IYQY[+80]IQSR\nK[+112.1]SAPATGGVK[+42]K[+56]PHR";
    self.response = " ";

    self.waiting = false;
    self.showOutput = false;

    self.modificationPattern = /[^A-Z]/g;
    self.modificationPatternWithLetter = /[A-Z]\[\+[\d\.]+]/g;
    self.rowSplitPattern = /[,;\n]/;
    self.cleanFormattedModifications = /\[/;

    self.parsedMotifs = [];
    self.parsedModifications = [];
    self.parsedModificationsFormatter = [];

    self.ontologyMappings = [];

    self.numResponsesFromProsite;

    // track changes in user input
    $scope.$watch(function () {
        return self.textArea
    }, function (newValue, oldValue) {


        // parse motifs
        self.parsedMotifs = self.textArea
            .split(self.rowSplitPattern)
            .map(function (e) {
                return e.replace(self.modificationPattern, '')
            });

        // parse modifications
        self.parsedModifications = self.textArea
            .split(self.rowSplitPattern)
            .map(function (e) {
                return e.match(self.modificationPatternWithLetter);
            });

        // format parsed modifiacations
        self.parsedModificationsFormatter = self.parsedModifications
            .map(function (e) {
                return e.join(" ");
        });

    });

    // track changes in parsed modifications and refresh psi-mod mapping
    $scope.$watch(function(){return self.parsedModifications},function(nV,oV){
        self.ontologyMappings = [];

        self.parsedModifications.forEach(function(e){
            e.forEach(function(el){
                (function (el) {
                    $http.get("api/psimod/" + el)
                        .success(function (data) {

                            var result = {};
                            result.identifier = data.string;
                            result.modification = el;
                            self.ontologyMappings.push(result);
                        })
                        .error(function (data, status) {
                            console.log(data + ' Status: ' + status);

                        });
                }(el));
            })

        });
    })

    self.onSubmit = function () {

        self.showInstruction = false;
        self.waiting = true;
        self.showOutput = true;
        self.numResponsesFromProsite = 0;
        self.responseRaw = [];
        self.response = [];

        var url = 'api/prosite/';

        for (var j = 0; j < self.parsedMotifs.length; j++) {

            var localMotif = self.parsedMotifs[j];

            (function (localMotif) {
                $http.get(url + self.parsedMotifs[j])
                    .success(function (data) {

                        data.matchset.map(function (e) {
                            e.motif = localMotif;
                            return e;
                        });

                        var matchset = data.matchset;

                        self.responseRaw = self.responseRaw.concat(matchset);
                        self.response = self.response.concat(JSON.stringify(matchset));

                        //console.log("ResponseRaw: " + self.responseRaw);

                        self.numResponsesFromProsite++;
                        if (self.numResponsesFromProsite >= self.parsedMotifs.length) {
                            self.waiting = false;
                        }
                    })
                    .error(function (data, status) {
                        console.log(data + ' Status: ' + status);
                        self.waiting = false;
                    });
            })(localMotif);
        }

    }

}]);
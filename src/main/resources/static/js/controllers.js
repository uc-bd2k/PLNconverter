/**
 * Created by chojnasm on 11/9/15.
 */
var appModule = angular.module('plnModule', []);

appModule.controller("MainCtrl", ['$http', '$scope', function ($http, $scope) {

    var self = this;

    self.formatAsJsonOrInline = false;
    self.allVsFirstPrositeHits = false;
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

    self.pln = [];
    self.plnFirstHit = [];

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
                if (e != null)
                    return e.join(" ");
            });

        self.pln = [];
        self.plnFirstHit = [];
    });

    // track changes in parsed modifications and refresh psi-mod mapping
    $scope.$watch(function () {
        return self.parsedModifications
    }, function (nV, oV) {
        self.ontologyMappings = [];

        self.parsedModifications.forEach(function (e) {
            if (e != null) {
                e.forEach(function (el) {
                    (function (el) {
                        $http.get("api/psimod/" + el)
                            .success(function (data) {

                                var result = {};
                                result.identifier = data.string;
                                result.modification = el;
                                result.diffavg = data.aDouble;
                                result.description = data.description;
                                result.similar = data.similar;
                                self.ontologyMappings.push(result);
                            })
                            .error(function (data, status) {
                                //console.log(data + ' Status: ' + status);
                                var result = {};
                                result.modification = el;
                                result.description = "Not found";
                                self.ontologyMappings.push(result);

                            });
                    }(el));
                })
            }
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

                        //console.log(data);
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
                            self.updatePln();
                        }
                    })
                    .error(function (data, status) {
                        //console.log(data + ' Status1: ' + status);
                        self.responseRaw = self.responseRaw.concat({
                            "motif": localMotif,
                            "sequence_db": "Not found in DB"
                        });
                        //self.response = self.response.concat(JSON.stringify(matchset));

                        //console.log("ResponseRaw: " + self.responseRaw);

                        self.numResponsesFromProsite++;
                        if (self.numResponsesFromProsite >= self.parsedMotifs.length) {
                            self.waiting = false;
                            self.updatePln();
                        }
                    });
            })(localMotif);
        }

    }

    self.updatePln = function () {
        self.pln = [];

        var motifs = self.parsedMotifs;
        var peptides = self.textArea
            .split(self.rowSplitPattern);

        for (var i = 0; i < peptides.length; i++) {
            var motif = motifs[i];
            var peptide = peptides[i];

            var firstPrositeResponse = self.responseRaw
                .filter(function (e) {
                    return e.motif == motif;
                });

            //firstPrositeResponse = firstPrositeResponse[0];
            var uniprotArray = [];
            var hugoArray = [];
            var ptmArray = [];

            firstPrositeResponse.map(function(e){
                uniprotArray.push(e.sequence_ac);
                hugoArray.push(e.sequence_id);
                ptmArray.push(e.start);
            });

            var uniprot = uniprotArray;
            var hugo = hugoArray;


            var ptm = [];

            //

            ptmArray.map(function(start, ptmArrayIndex){

                var ptmLocal = [];

                // for a given peptide iterate through modifications, calculate its offset and return to ptmLocal
                self.ontologyMappings
                    .filter(function (el) {
                        return (peptide.indexOf(el.modification) > -1);
                    })
                    .map(function (e) {
                        var offset = peptide.indexOf(e.modification);
                        var beforeOffset = peptide.substring(0,offset);
                        var decreaseOffset = beforeOffset.length - beforeOffset.replace(self.modificationPattern, '').length;

                        ptmLocal.push({"identifier": e.identifier, "location": offset - decreaseOffset + start});
                    });

                ptm.push(ptmLocal);
            });


            self.pln.push({
                "PLN": {"ver1": "all_hits"},
                "REF": {"uniprot": uniprot},
                "SYM": {"hugo": hugo},
                "DES": {},
                "VAR": {},
                "PTM": ptm
            });

        }
    }
}]);
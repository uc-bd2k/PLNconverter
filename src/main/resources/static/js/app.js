/**
 * Created by chojnasm on 11/9/15.
 */
var app = angular.module('plnApplication', ['plnModule']);

app.filter('treeJSON', function () {
    function prettyPrintJson(json) {
        return JSON ? '\n' + JSON.stringify(json, null, ' ') : 'your browser doesnt support JSON so cant pretty print';
    }

    return prettyPrintJson;
});

app.filter('inline', function () {
    function inlinePLN(plnArray) {
        var output = '\n';
        for (var i = 0; i < plnArray.length; i++) {
            var plnLocal = plnArray[i];

            var plnKey = Object.keys(plnLocal.PLN)[0];
            var plnValue = plnLocal.PLN[plnKey];

            var refKey = Object.keys(plnLocal.REF)[0];
            var refValue = plnLocal.REF[refKey];

            var symKey = Object.keys(plnLocal.SYM)[0];
            var symValue = plnLocal.SYM[symKey];

            var DES = '';
            var VAR = '';

            var PTM = '';

            plnLocal.PTM.map(function (e, i) {
                if (i > 0) {
                    PTM += "+";
                }
                PTM += e.offset + "," + e.identifier;
            });

            output = output +
                "PLN=" + plnKey + ":" + plnValue + "&" +
                "REF=" + refKey + ":" + refValue + "&" +
                "SYM=" + symKey + ":" + symValue + "&" +
                "DES=" + "&" +
                "VAR=" + "&" +
                "PTM=" + PTM + "#" + "\n";
        }
        return output;
    }

    return inlinePLN;
});
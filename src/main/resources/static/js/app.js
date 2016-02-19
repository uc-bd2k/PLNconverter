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

            var PTM = [];

            // Configuration
            var layerSep = ";";
            var groupSep = ",";
            var itemSep = "&";

            plnLocal.PTM.map(function (ptmGroup) {
                    //console.log(ptmGroup);

                    var ptmForHit = [];

                    for (var index = 0; index < ptmGroup.length; index++) {
                        ptmForHit.push(ptmGroup[index].identifier + "@" + ptmGroup[index].location);
                    }

                    PTM.push(ptmForHit.join(itemSep));
                }
            );



            output = output +
                "PLN=" + plnKey + ":" + plnValue + layerSep +
                "REF=" + refKey + ":" + refValue.join(groupSep + refKey + ":") + layerSep +
                "SYM=" + symKey + ":" + symValue.join(groupSep + symKey + ":") + layerSep +
                "DES=" + layerSep +
                "VAR=" + layerSep +
                "PTM=" + PTM.join(groupSep) + "#" + "\n";

            output = output + "\n";

            plnKey = "ver1:InChl_like";
            layerSep = "/";
            groupSep = ";";

            output = output +
                "PLN=" + plnKey + layerSep +
                "r;" + refKey + ":" + refValue.join(groupSep + refKey + ":") + layerSep +
                "s;" + symKey + ":" + symValue.join(groupSep + symKey + ":") + layerSep +
                "d;" + layerSep +
                "v;" + layerSep +
                "m;" + PTM.join(groupSep) + "#" + "\n\n";

        }
        return output;
    }

    return inlinePLN;
});
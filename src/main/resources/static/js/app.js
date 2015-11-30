/**
 * Created by chojnasm on 11/9/15.
 */
var app = angular.module('plnApplication', ['plnModule']);

app.filter('treeJSON', function () {
    function prettyPrintJson(json) {
        return JSON ? '\n'+JSON.stringify(json, null ,' ') : 'your browser doesnt support JSON so cant pretty print';
    }
    return prettyPrintJson;
});

app.filter('inlineJSON', function () {
    function prettyPrintJson(json) {
        return JSON ? '\n'+JSON.stringify(json) : 'your browser doesnt support JSON so cant pretty print';
    }
    return prettyPrintJson;
});
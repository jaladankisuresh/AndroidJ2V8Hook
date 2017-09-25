var bBrowserPromise = require('../node_modules/bluebird/js/browser/bluebird.js');
var bNodePromise = require('../node_modules/bluebird/js/release/bluebird.js');

var bPromise = typeof window === 'object' ? bBrowserPromise : bNodePromise;
module.exports = bPromise;

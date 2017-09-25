var models = require('../models');
// var bPromise = require('../config/bluebird');

var  launcherVM = {
  model : undefined,
  init : function(cb) {
    this.model = new models.Launcher();
    cb(null, this.model);
  }
};

module.exports = launcherVM;

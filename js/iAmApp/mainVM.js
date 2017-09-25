var models = require('../models');
// var bPromise = require('../config/bluebird');
var  mainVM = {
  model : undefined,
  init : function(cb) {
    // iAmApp.iAmAjax.getAsync('establishmentRegistry.json')
    // .then(response => {
    //   this.model = new models.Registry(response);
    //   return bPromise.resolve(this.model)
    //     .asCallback(cb);
    // });
    iAmApp.iAmAjax.get('establishmentRegistry.json',
      (response) => {
        this.model = new models.Registry(response);
        cb(null, this.model);
      },
      (error) => {
        cb(error);
      }
    );
  }
};

module.exports = mainVM;

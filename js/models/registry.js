var pathParser = require('../utils/pathParser');
var getEstablishmentsByType = function(path, data) {
  let models = require('./index');
  let establishmentsCollection = [];
  for (let entity of data) {
    entity.path =  path + 'establishments/' + data.indexOf(entity);

    if(models[entity.type]) {
      let establishment = new models[entity.type](entity);
      establishmentsCollection.push(establishment);
    }
  }
  return establishmentsCollection;
};
var registry = class Registry {

  constructor (data) {
    this.path = '/';
    this.name = data.name;
    this.description = data.description;
    this.establishments = getEstablishmentsByType(this.path, data.establishments);
    this.count = data.establishments.length;
  }

  execute() {
    let args = arguments;
    let path = args[0];
    let action = args[1];
    let actionObj = pathParser(this, path);
    switch(args.length) {
      case 3:
      case 4:
        if(typeof args[2] === 'function') {
          actionObj[action](args[2]);
        }
        else {
          actionObj[action](args[2], args[3]);
        }
        break;
      default:
        return;
    }
  }
};

module.exports = registry;

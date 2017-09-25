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
};

module.exports = registry;

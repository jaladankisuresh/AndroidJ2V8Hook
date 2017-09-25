var Establishment = require('./establishment');
var Restaurant = class Restaurant extends Establishment {

  constructor (data) {
    super(data);
    this.cuisineType = data.cuisineType;
    this.chefsCount = data.chefsCount;
    this.seatingCapacity = data.seatingCapacity;
  }
};

module.exports = Restaurant;

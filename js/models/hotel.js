var Establishment = require('./establishment');
var Hotel = class Hotel extends Establishment {

  constructor (data) {
    super(data);
    this.stars = data.stars;
    this.roomsCount = data.roomsCount;
    this.isAttachedRestaurant = data.isAttachedRestaurant;
  }
};

module.exports = Hotel;

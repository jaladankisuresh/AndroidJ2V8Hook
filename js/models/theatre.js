var Establishment = require('./establishment');
var Theatre = class Theatre extends Establishment {

  constructor (data) {
    super(data);
    this.screensCount = data.screensCount;
    this.seatingCapacity = data.seatingCapacity;
    this.showsPerScreen = data.showsPerScreen;
  }
};

module.exports = Theatre;

var getComments = function(path, data) {
  let models = require('./index');
  let comments = [];
  for (let item of data) {
    item.path =  path + 'comments/' + data.indexOf(item);

    let comment = new models.Comment(item);
    comments.push(comment);
  }
  return comments;
};

var Establishment = class Establishment {

  constructor (data) {
    this.path = data.path;
    this.name = data.name;
    this.type = data.type;
    this.location = data.location;
    this.rating = data.rating;
    this.comments = getComments(this.path, data.comments);
  }
  addComment(comment) {
    var shortid = require('shortid');
    let id = shortid.generate();
    this.comments.push({id : comment});
  }
  editComment(idx, comment) {
    this.comments[idx].text = comment;
  }
  removeComment(idx) {
    delete this.comments[idx];
  }
};

module.exports = Establishment;

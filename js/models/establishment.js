var getComments = function(path, data) {
  let models = require('./index');
  let comments = [];
  for (let item of data) {
    item.path =  path + '/comments/' + data.indexOf(item);

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
  addComment(commentStr, cb) {
    var shortid = require('shortid');
    let id = shortid.generate();
    let commentsLength = this.comments.length;
    let newLength = this.comments.push({path: this.path + '/comments/' + commentsLength, id : id, text : commentStr});
    let comment = this.comments[newLength - 1];
    cb(null, JSON.stringify(this), this);
  }
  editComment(idx, commentStr, cb) {
    this.comments[idx].text = commentStr;
    let comment = this.comments[idx];
    cb(null, JSON.stringify(comment), comment);
  }
  removeComment(idx, cb) {
    delete this.comments[idx];
    cb();
  }
};

module.exports = Establishment;

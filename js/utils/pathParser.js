var pathParser = function(obj, path) {
  let index = path.indexOf('/', 1);
  if(index === -1) {
    return obj[path.substring(1)];
  }
  let childObj = obj[path.substring(1, index)];
  return pathParser(childObj, path.substring(index));
};

module.exports = pathParser;

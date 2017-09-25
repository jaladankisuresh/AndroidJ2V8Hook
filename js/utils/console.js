var stringify = function(args) {
  let strArr = [];
  for(let arg of args) {
    let str = (() => {
      if(typeof arg === 'string') {
        return arg;
      }
      else if(typeof arg === 'function') {
        return arg.toString();
      }
      else if(typeof arg === 'object') {
        return JSON.stringify(arg);
      }
    })();
    strArr.push(str);
  }
  return strArr.join('\n');
};
var console = {
  log : function() {
    let str = stringify(arguments);
    jConsole.log(str);
  },
  error : function() {
    let str = stringify(arguments);
    jConsole.error(str);
  }
};

module.exports = console;

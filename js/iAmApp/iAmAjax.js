var shortid = require('shortid');
// var bPromise = require('../config/bluebird');

var httpRequestQueue = {};
var iAmAjax = {
  get : function() { //args = url, data, cb (POST) || url, cb (GET)
    let url, data, cb = {};
    let args = arguments;
    let token = shortid.generate();
    switch(args.length) {
      case 3:
        url = args[0];
        cb.onSuccessCallback = args[1];
        cb.onErrorCallback = args[2];
        this.httpGet(token, url);
        break;
      case 4:
        url = args[0];
        data = args[1];
        cb.onSuccessCallback = args[2];
        cb.onErrorCallback = args[3];
        this.httpGet(token, url, data);
        break;
      default:
        return;
    }
    httpRequestQueue[token] = cb;
  },
  httpGet : function(token, url, data) {
    if (typeof window === 'object') { // JavaScript running in the browser
      var axios = require('axios');
      axios.get('http://localhost:3000/' + url)
      .then(response => {
        this.onSuccessResponse(token, response);
      })
      .catch(error => {
        this.onErrorResponse(token, error);
      });
    }
    else { // JavaScript is NOT running in the browser
      console.log('JavaScript is NOT running in the browser');
    }
  },
  onSuccessResponse : function(token, response) {
    // typeof response is object then it is a browser HTTP request with headers. So, get response.data for getting HTTPResponse data
    // If response from Mobile HTTP client that response is JSON response data string
    let jsObject = (typeof response === 'string') ? JSON.parse(response) : response.data;
    let cb = httpRequestQueue[token];
    delete httpRequestQueue[token];
    if(typeof cb.onSuccessCallback === 'function') {
      cb.onSuccessCallback(jsObject);
    }
  },
  onErrorResponse : function(token, error) {
    console.log(error);

    let cb = httpRequestQueue[token];
    delete httpRequestQueue[token];
    if(typeof cb.onErrorResponse === 'function') {
      cb.onErrorResponse(error);
    }
  }
};
// iAmAjax.getAsync = bPromise.promisify(iAmAjax.get);

module.exports = iAmAjax;

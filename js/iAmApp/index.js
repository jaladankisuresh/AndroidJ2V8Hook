var iAmApp = {
  launcherVM : require('./launcherVM'),
  mainVM : require('./mainVM')
};

module.exports = {
  getViewModel : function(view, cb) {
    if(iAmApp[view]) {
      let vm = iAmApp[view];
      vm.init((err, data) => {
        if(err) {
          return cb(err);
        }
        console.log(JSON.stringify(vm.model), vm);
        cb(null, JSON.stringify(vm.model), vm);
      });
    }
    else {
      cb('Error : No View Model with the name ' + view);
    }
  },
  iAmAjax : require('./iAmAjax')
};

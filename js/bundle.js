const webpack = require('webpack');
var webpackConfig = require('./webpack.config.js');

webpack(webpackConfig, (err, stats) => {
  if (err) {
    console.error(err.stack || err);
    if (err.details) {
      console.log('err.details : Errors while Bundling: \n');
      console.error(err.details);
    }
    return;
  }
  const info = stats.toJson();
  if (stats.hasErrors()) {
    console.log('stats.hasErrors() : Errors while Bundling: \n');
    console.error(info.errors);
    return;
  }
  if (stats.hasWarnings()) {
    console.log('Warnings while Bundling: \n');
    console.warn(info.warnings);
  }

  console.log('Done Processing Webpack');
});

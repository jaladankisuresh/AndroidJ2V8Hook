var path = require('path');
var webpack = require('webpack');

module.exports = {
    entry: ['./iAmApp', './utils/console'],
    // entry: ['./iAmApp'],
    output: {
        path: path.resolve(__dirname, 'bin'),
        filename: 'app.bundle.js'
    },
     module: {
         rules: [
           /*{
             test: /\.js$/,
             exclude: /node_modules/,
             loader: 'babel-loader',
             options: {
               presets: ["es2015"]
             }
         },
         {
           test: /\.json$/,
           loader: 'json-loader'
         },*/
        {
         test: require.resolve('./iAmApp'),
         use: [{
             loader: 'expose-loader',
             options: 'iAmApp'
         }]
       },
       {
        test: require.resolve('./utils/console'),
        use: [{
            loader: 'expose-loader',
            options: 'console'
        }]
      }
        ]
     },
    //  plugins: [
    //     new webpack.ProvidePlugin({
    //         //Promise: "imports-loader?this=>global!exports-loader?global.Promise!bluebird"
    //         Promise: "bluebird"
    //     }),
    //  ],
    // plugins: [
    //   new webpack.IgnorePlugin(
    //     /\.\/(timers|any|race|call_get|filter|generators|map|nodeify|promisify|props|reduce|settle|some|progress|cancel)\.js/,
    //     /node_modules\/bluebird\/js\/main/
    //   ),
    // ],
    // plugins: [
    //   new webpack.IgnorePlugin(/bluebird$/)
    // ],
    // externals: ['bluebird'],
    target: 'webworker'
};

function fn() {
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev';
  }
  var config = {
    AppURl : "http://localhost:9191",
    token:"rahultoken",
    env: env,
    myVarName: 'someValue'
  }
  if (env == 'dev') {
    // customize
   config.AppUrl = "http://localhost:9191/"
    // e.g. config.foo = 'bar';
  } else if (env == 'e2e') {
    // customize
  }
  return config;
}
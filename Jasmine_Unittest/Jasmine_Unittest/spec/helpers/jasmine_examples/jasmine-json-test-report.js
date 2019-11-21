var JSONReporter = require('jasmine-json-test-reporter');
jasmine.getEnv().addReporter(new JSONReporter({
	file: 'jasmine-test-results.json',
	beautify: true,
	indentationLevel: 4 // used if beautify === true
}));
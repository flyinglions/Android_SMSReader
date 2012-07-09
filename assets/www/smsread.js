
var SMSReader = function () {};

SMSReader.prototype.getInbox = function(params, success, fail) {
        return PhoneGap.exec(function(args) {
        success(args);
    }, function(args) {
        fail(args);
    }, 'SMSReader', 'inbox', [params]);
};

SMSReader.prototype.getSent = function(params, success, fail) {
        return PhoneGap.exec(function(args) {
        success(args);
    }, function(args) {
        fail(args);
    }, 'SMSReader', 'sent', [params]);
};

PhoneGap.addConstructor(function() {
	PhoneGap.addPlugin("SMSReader", new SMSReader());
	PluginManager.addService("SMSReader", "org.flying.lions.SMSReader");
});
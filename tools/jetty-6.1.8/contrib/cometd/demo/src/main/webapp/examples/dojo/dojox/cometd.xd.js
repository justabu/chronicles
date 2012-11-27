/*
	Copyright (c) 2004-2007, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/

/*
	This is a compiled version of Dojo, built for deployment and not for
	development. To get an editable version, please visit:

		http://dojotoolkit.org

	for documentation and information on getting the source.
*/

dojo._xdResourceLoaded({depends:[["provide","dojo.AdapterRegistry"],["provide","dojo.io.script"],["provide","dojox.cometd._base"],["provide","dojox.cometd"]],defineResource:function(_1,_2,_3){if(!_1._hasResource["dojo.AdapterRegistry"]){_1._hasResource["dojo.AdapterRegistry"]=true;_1.provide("dojo.AdapterRegistry");_1.AdapterRegistry=function(_4){this.pairs=[];this.returnWrappers=_4||false;};_1.extend(_1.AdapterRegistry,{register:function(_5,_6,_7,_8,_9){this.pairs[((_9)?"unshift":"push")]([_5,_6,_7,_8]);},match:function(){for(var i=0;i<this.pairs.length;i++){var _b=this.pairs[i];if(_b[1].apply(this,arguments)){if((_b[3])||(this.returnWrappers)){return _b[2];}else{return _b[2].apply(this,arguments);}}}throw new Error("No match found");},unregister:function(_c){for(var i=0;i<this.pairs.length;i++){var _e=this.pairs[i];if(_e[0]==_c){this.pairs.splice(i,1);return true;}}return false;}});}if(!_1._hasResource["dojo.io.script"]){_1._hasResource["dojo.io.script"]=true;_1.provide("dojo.io.script");_1.io.script={get:function(_f){var dfd=this._makeScriptDeferred(_f);var _11=dfd.ioArgs;_1._ioAddQueryToUrl(_11);this.attach(_11.id,_11.url);_1._ioWatch(dfd,this._validCheck,this._ioCheck,this._resHandle);return dfd;},attach:function(id,url){var _14=_1.doc.createElement("script");_14.type="text/javascript";_14.src=url;_14.id=id;_1.doc.getElementsByTagName("head")[0].appendChild(_14);},remove:function(id){_1._destroyElement(_1.byId(id));if(this["jsonp_"+id]){delete this["jsonp_"+id];}},_makeScriptDeferred:function(_16){var dfd=_1._ioSetArgs(_16,this._deferredCancel,this._deferredOk,this._deferredError);var _18=dfd.ioArgs;_18.id=_1._scopeName+"IoScript"+(this._counter++);_18.canDelete=false;if(_16.callbackParamName){_18.query=_18.query||"";if(_18.query.length>0){_18.query+="&";}_18.query+=_16.callbackParamName+"=dojo.io.script.jsonp_"+_18.id+"._jsonpCallback";_18.canDelete=true;dfd._jsonpCallback=this._jsonpCallback;this["jsonp_"+_18.id]=dfd;}return dfd;},_deferredCancel:function(dfd){dfd.canceled=true;if(dfd.ioArgs.canDelete){_1.io.script._deadScripts.push(dfd.ioArgs.id);}},_deferredOk:function(dfd){if(dfd.ioArgs.canDelete){_1.io.script._deadScripts.push(dfd.ioArgs.id);}if(dfd.ioArgs.json){return dfd.ioArgs.json;}else{return dfd.ioArgs;}},_deferredError:function(_1b,dfd){if(dfd.ioArgs.canDelete){if(_1b.dojoType=="timeout"){_1.io.script.remove(dfd.ioArgs.id);}else{_1.io.script._deadScripts.push(dfd.ioArgs.id);}}console.debug("dojo.io.script error",_1b);return _1b;},_deadScripts:[],_counter:1,_validCheck:function(dfd){var _1e=_1.io.script;var _1f=_1e._deadScripts;if(_1f&&_1f.length>0){for(var i=0;i<_1f.length;i++){_1e.remove(_1f[i]);}_1.io.script._deadScripts=[];}return true;},_ioCheck:function(dfd){if(dfd.ioArgs.json){return true;}var _22=dfd.ioArgs.args.checkString;if(_22&&eval("typeof("+_22+") != 'undefined'")){return true;}return false;},_resHandle:function(dfd){if(_1.io.script._ioCheck(dfd)){dfd.callback(dfd);}else{dfd.errback(new Error("inconceivable dojo.io.script._resHandle error"));}},_jsonpCallback:function(_24){this.ioArgs.json=_24;}};}if(!_1._hasResource["dojox.cometd._base"]){_1._hasResource["dojox.cometd._base"]=true;_1.provide("dojox.cometd._base");_3.cometd=new function(){this.DISCONNECTED="DISCONNECTED";this.CONNECTING="CONNECTING";this.CONNECTED="CONNECTED";this.DISCONNECTING="DISCONNECING";this._initialized=false;this._connected=false;this._polling=false;this.connectionTypes=new _1.AdapterRegistry(true);this.version="1.0";this.minimumVersion="0.9";this.clientId=null;this.messageId=0;this.batch=0;this._isXD=false;this.handshakeReturn=null;this.currentTransport=null;this.url=null;this.lastMessage=null;this._messageQ=[];this.handleAs="json-comment-optional";this._advice={};this._backoffInterval=0;this._backoffIncrement=1000;this._backoffMax=60000;this._deferredSubscribes={};this._deferredUnsubscribes={};this._subscriptions=[];this._extendInList=[];this._extendOutList=[];this.state=function(){return this._initialized?(this._connected?this.CONNECTED:this.CONNECTING):(this._connected?this.DISCONNECTING:this.DISCONNECTED);};this.init=function(_25,_26,_27){_26=_26||{};_26.version=this.version;_26.minimumVersion=this.minimumVersion;_26.channel="/meta/handshake";_26.id=""+this.messageId++;this.url=_25||_1.config["cometdRoot"];if(!this.url){console.debug("no cometd root specified in djConfig and no root passed");return null;}var _28="^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?$";var _29=(""+window.location).match(new RegExp(_28));if(_29[4]){var tmp=_29[4].split(":");var _2b=tmp[0];var _2c=tmp[1]||"80";_29=this.url.match(new RegExp(_28));if(_29[4]){tmp=_29[4].split(":");var _2d=tmp[0];var _2e=tmp[1]||"80";this._isXD=((_2d!=_2b)||(_2e!=_2c));}}if(!this._isXD){if(_26.ext){if(_26.ext["json-comment-filtered"]!==true&&_26.ext["json-comment-filtered"]!==false){_26.ext["json-comment-filtered"]=true;}}else{_26.ext={"json-comment-filtered":true};}_26.supportedConnectionTypes=_1.map(this.connectionTypes.pairs,"return item[0]");}_26=this._extendOut(_26);var _2f={url:this.url,handleAs:this.handleAs,content:{"message":_1.toJson([_26])},load:_1.hitch(this,function(msg){this._backon();this._finishInit(msg);}),error:_1.hitch(this,function(e){console.debug("handshake error!:",e);this._backoff();this._finishInit([{}]);})};if(_27){_1.mixin(_2f,_27);}this._props=_26;for(var _32 in this._subscriptions){for(var sub in this._subscriptions[_32]){if(this._subscriptions[_32][sub].topic){_1.unsubscribe(this._subscriptions[_32][sub].topic);}}}this._messageQ=[];this._subscriptions=[];this._initialized=true;this.batch=0;this.startBatch();var r;if(this._isXD){_2f.callbackParamName="jsonp";r=_1.io.script.get(_2f);}else{r=_1.xhrPost(_2f);}_1.publish("/cometd/meta",[{cometd:this,action:"handshake",successful:true,state:this.state()}]);return r;};this.publish=function(_35,_36,_37){var _38={data:_36,channel:_35};if(_37){_1.mixin(_38,_37);}this._sendMessage(_38);};this.subscribe=function(_39,_3a,_3b,_3c){_3c=_3c||{};if(_3a){var _3d="/cometd"+_39;var _3e=this._subscriptions[_3d];if(!_3e||_3e.length==0){_3e=[];_3c.channel="/meta/subscribe";_3c.subscription=_39;this._sendMessage(_3c);var _ds=this._deferredSubscribes;_ds[_39]=new _1.Deferred();if(_ds[_39]){_ds[_39].cancel();delete _ds[_39];}}for(var i in _3e){if(_3e[i].objOrFunc===_3a&&(!_3e[i].funcName&&!_3b||_3e[i].funcName==_3b)){return null;}}var _41=_1.subscribe(_3d,_3a,_3b);_3e.push({topic:_41,objOrFunc:_3a,funcName:_3b});this._subscriptions[_3d]=_3e;}return this._deferredSubscribes[_39];};this.unsubscribe=function(_42,_43,_44,_45){var _46="/cometd"+_42;var _47=this._subscriptions[_46];if(!_47||_47.length==0){return null;}var s=0;for(var i in _47){var sb=_47[i];if((!_43)||(sb.objOrFunc===_43&&(!sb.funcName&&!_44||sb.funcName==_44))){_1.unsubscribe(_47[i].topic);delete _47[i];}else{s++;}}if(s==0){_45=_45||{};_45.channel="/meta/subscribe";_45.subscription=_42;delete this._subscriptions[_46];this._sendMessage(_45);this._deferredUnsubscribes[_42]=new _1.Deferred();if(this._deferredSubscribes[_42]){this._deferredSubscribes[_42].cancel();delete this._deferredSubscribes[_42];}}return this._deferredUnsubscribes[_42];};this.disconnect=function(){for(var _4b in this._subscriptions){for(var sub in this._subscriptions[_4b]){if(this._subscriptions[_4b][sub].topic){_1.unsubscribe(this._subscriptions[_4b][sub].topic);}}}this._subscriptions=[];this._messageQ=[];if(this._initialized&&this.currentTransport){this._initialized=false;this.currentTransport.disconnect();}if(!this._polling){this._connected=false;_1.publish("/cometd/meta",[{cometd:this,action:"connect",successful:false,state:this.state()}]);}this._initialized=false;_1.publish("/cometd/meta",[{cometd:this,action:"disconnect",successful:true,state:this.state()}]);};this.subscribed=function(_4d,_4e){};this.unsubscribed=function(_4f,_50){};this.tunnelInit=function(_51,_52){};this.tunnelCollapse=function(){};this._backoff=function(){if(!this._advice){this._advice={reconnect:"retry",interval:0};}else{if(!this._advice.interval){this._advice.interval=0;}}if(this._backoffInterval<this._backoffMax){this._backoffInterval+=this._backoffIncrement;}};this._backon=function(){this._backoffInterval=0;};this._interval=function(){var i=this._backoffInterval+(this._advice?(this._advice.interval?this._advice.interval:0):0);if(i>0){console.debug("Retry in interval+backoff="+this._advice.interval+"+"+this._backoffInterval+"="+i+"ms");}return i;};this._finishInit=function(_54){_54=_54[0];this.handshakeReturn=_54;if(_54["advice"]){this._advice=_54.advice;}var _55=_54.successful?_54.successful:false;if(_54.version<this.minimumVersion){console.debug("cometd protocol version mismatch. We wanted",this.minimumVersion,"but got",_54.version);_55=false;this._advice.reconnect="none";}if(_55){this.currentTransport=this.connectionTypes.match(_54.supportedConnectionTypes,_54.version,this._isXD);this.currentTransport._cometd=this;this.currentTransport.version=_54.version;this.clientId=_54.clientId;this.tunnelInit=_1.hitch(this.currentTransport,"tunnelInit");this.tunnelCollapse=_1.hitch(this.currentTransport,"tunnelCollapse");this.currentTransport.startup(_54);}_1.publish("/cometd/meta",[{cometd:this,action:"handshook",successful:_55,state:this.state()}]);if(!_55){console.debug("cometd init failed");if(this._advice&&this._advice["reconnect"]=="none"){console.debug("cometd reconnect: none");}else{setTimeout(_1.hitch(this,"init",this.url,this._props),this._interval());}}};this._extendIn=function(_56){_1.forEach(_3.cometd._extendInList,function(f){_56=f(_56)||_56;});return _56;};this._extendOut=function(_58){_1.forEach(_3.cometd._extendOutList,function(f){_58=f(_58)||_58;});return _58;};this.deliver=function(_5a){_1.forEach(_5a,this._deliver,this);return _5a;};this._deliver=function(_5b){_5b=this._extendIn(_5b);if(!_5b["channel"]){if(_5b["success"]!==true){console.debug("cometd error: no channel for message!",_5b);return;}}this.lastMessage=_5b;if(_5b.advice){this._advice=_5b.advice;}var _5c=null;if((_5b["channel"])&&(_5b.channel.length>5)&&(_5b.channel.substr(0,5)=="/meta")){switch(_5b.channel){case "/meta/connect":if(_5b.successful&&!this._connected){this._connected=this._initialized;this.endBatch();}else{if(!this._initialized){this._connected=false;}}_1.publish("/cometd/meta",[{cometd:this,action:"connect",successful:_5b.successful,state:this.state()}]);break;case "/meta/subscribe":_5c=this._deferredSubscribes[_5b.subscription];if(!_5b.successful){if(_5c){_5c.errback(new Error(_5b.error));}return;}_3.cometd.subscribed(_5b.subscription,_5b);if(_5c){_5c.callback(true);}break;case "/meta/unsubscribe":_5c=this._deferredUnsubscribes[_5b.subscription];if(!_5b.successful){if(_5c){_5c.errback(new Error(_5b.error));}return;}this.unsubscribed(_5b.subscription,_5b);if(_5c){_5c.callback(true);}break;}}this.currentTransport.deliver(_5b);if(_5b.data){try{var _5d="/cometd"+_5b.channel;_1.publish(_5d,[_5b]);}catch(e){console.debug(e);}}};this._sendMessage=function(_5e){if(this.currentTransport&&!this.batch){return this.currentTransport.sendMessages([_5e]);}else{this._messageQ.push(_5e);return null;}};this.startBatch=function(){this.batch++;};this.endBatch=function(){if(--this.batch<=0&&this.currentTransport&&this._connected){this.batch=0;var _5f=this._messageQ;this._messageQ=[];if(_5f.length>0){this.currentTransport.sendMessages(_5f);}}};this._onUnload=function(){_1.addOnUnload(_3.cometd,"disconnect");};};_3.cometd.longPollTransport=new function(){this._connectionType="long-polling";this._cometd=null;this.check=function(_60,_61,_62){return ((!_62)&&(_1.indexOf(_60,"long-polling")>=0));};this.tunnelInit=function(){var _63={channel:"/meta/connect",clientId:this._cometd.clientId,connectionType:this._connectionType,id:""+this._cometd.messageId++};_63=this._cometd._extendOut(_63);this.openTunnelWith({message:_1.toJson([_63])});};this.tunnelCollapse=function(){if(!this._cometd._initialized){return;}if(this._cometd._advice&&this._cometd._advice["reconnect"]=="none"){console.debug("cometd reconnect: none");return;}setTimeout(_1.hitch(this,function(){this._connect();}),this._cometd._interval());};this._connect=function(){if(!this._cometd._initialized){return;}if(this._cometd._polling){console.debug("wait for poll to complete or fail");return;}if((this._cometd._advice)&&(this._cometd._advice["reconnect"]=="handshake")){this._cometd._connected=false;this._initialized=false;this._cometd.init(this._cometd.url,this._cometd._props);}else{if(this._cometd._connected){var _64={channel:"/meta/connect",connectionType:this._connectionType,clientId:this._cometd.clientId,id:""+this._cometd.messageId++};_64=this._cometd._extendOut(_64);this.openTunnelWith({message:_1.toJson([_64])});}}};this.deliver=function(_65){};this.openTunnelWith=function(_66,url){this._cometd._polling=true;var d=_1.xhrPost({url:(url||this._cometd.url),content:_66,handleAs:this._cometd.handleAs,load:_1.hitch(this,function(_69){this._cometd._polling=false;this._cometd.deliver(_69);this._cometd._backon();this.tunnelCollapse();}),error:_1.hitch(this,function(err){this._cometd._polling=false;console.debug("tunnel opening failed:",err);_1.publish("/cometd/meta",[{cometd:this._cometd,action:"connect",successful:false,state:this._cometd.state()}]);this._cometd._backoff();this.tunnelCollapse();})});};this.sendMessages=function(_6b){for(var i=0;i<_6b.length;i++){_6b[i].clientId=this._cometd.clientId;_6b[i].id=""+this._cometd.messageId++;_6b[i]=this._cometd._extendOut(_6b[i]);}return _1.xhrPost({url:this._cometd.url||_1.config["cometdRoot"],handleAs:this._cometd.handleAs,load:_1.hitch(this._cometd,"deliver"),error:_1.hitch(this,function(err){console.debug("dropped messages: ",_6b);}),content:{message:_1.toJson(_6b)}});};this.startup=function(_6e){if(this._cometd._connected){return;}this.tunnelInit();};this.disconnect=function(){var _6f={channel:"/meta/disconnect",clientId:this._cometd.clientId,id:""+this._cometd.messageId++};_6f=this._cometd._extendOut(_6f);_1.xhrPost({url:this._cometd.url||_1.config["cometdRoot"],handleAs:this._cometd.handleAs,content:{message:_1.toJson([_6f])}});};};_3.cometd.callbackPollTransport=new function(){this._connectionType="callback-polling";this._cometd=null;this.check=function(_70,_71,_72){return (_1.indexOf(_70,"callback-polling")>=0);};this.tunnelInit=function(){var _73={channel:"/meta/connect",clientId:this._cometd.clientId,connectionType:this._connectionType,id:""+this._cometd.messageId++};_73=this._cometd._extendOut(_73);this.openTunnelWith({message:_1.toJson([_73])});};this.tunnelCollapse=_3.cometd.longPollTransport.tunnelCollapse;this._connect=_3.cometd.longPollTransport._connect;this.deliver=_3.cometd.longPollTransport.deliver;this.openTunnelWith=function(_74,url){this._cometd._polling=true;_1.io.script.get({load:_1.hitch(this,function(_76){this._cometd._polling=false;this._cometd.deliver(_76);this._cometd._backon();this.tunnelCollapse();}),error:_1.hitch(this,function(err){this._cometd._polling=false;console.debug("tunnel opening failed:",err);_1.publish("/cometd/meta",[{cometd:this._cometd,action:"connect",successful:false,state:this._cometd.state()}]);this._cometd._backoff();this.tunnelCollapse();}),url:(url||this._cometd.url),content:_74,callbackParamName:"jsonp"});};this.sendMessages=function(_78){for(var i=0;i<_78.length;i++){_78[i].clientId=this._cometd.clientId;_78[i].id=""+this._cometd.messageId++;_78[i]=this._cometd._extendOut(_78[i]);}var _7a={url:this._cometd.url||_1.config["cometdRoot"],load:_1.hitch(this._cometd,"deliver"),callbackParamName:"jsonp",content:{message:_1.toJson(_78)}};return _1.io.script.get(_7a);};this.startup=function(_7b){if(this._cometd._connected){return;}this.tunnelInit();};this.disconnect=_3.cometd.longPollTransport.disconnect;this.disconnect=function(){var _7c={channel:"/meta/disconnect",clientId:this._cometd.clientId,id:""+this._cometd.messageId++};_7c=this._cometd._extendOut(_7c);_1.io.script.get({url:this._cometd.url||_1.config["cometdRoot"],callbackParamName:"jsonp",content:{message:_1.toJson([_7c])}});};};_3.cometd.connectionTypes.register("long-polling",_3.cometd.longPollTransport.check,_3.cometd.longPollTransport);_3.cometd.connectionTypes.register("callback-polling",_3.cometd.callbackPollTransport.check,_3.cometd.callbackPollTransport);_1.addOnUnload(_3.cometd,"_onUnload");}if(!_1._hasResource["dojox.cometd"]){_1._hasResource["dojox.cometd"]=true;_1.provide("dojox.cometd");}}});

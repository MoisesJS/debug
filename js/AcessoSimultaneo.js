/*--------------------------------------------------------------------------
 *
 *  Ajax Acesso Simultaneo 
 *
/*--------------------------------------------------------------------------*/


var Class = {
  create: function() {
    return function() {
      this.initialize.apply(this, arguments);
    }
  }
}

var AcessoSimultaneo = Class.create();

AcessoSimultaneo.Prototype = {
  Version: '1.5.0',
  BrowserFeatures: {
    XPath: !!document.evaluate
  },

  ScriptFragment: '(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)',
  emptyFunction: function() {},
  K: function(x) { return x }
}

AcessoSimultaneo.extend = function(destination, source) {
  for (var property in source) {
    destination[property] = source[property];
  }
  return destination;
}

AcessoSimultaneo.extend(AcessoSimultaneo, {
  inspect: function(object) {
    try {
      if (object === undefined) return 'undefined';
      if (object === null) return 'null';
      return object.inspect ? object.inspect() : object.toString();
    } catch (e) {
      if (e instanceof RangeError) return '...';
      throw e;
    }
  }
});

AcessoSimultaneo.Try = {
  these: function() {
    var returnValue;

    for (var i = 0, length = arguments.length; i < length; i++) {
      var lambda = arguments[i];
      try {
        returnValue = lambda();
        break;
      } catch (e) {}
    }

    return returnValue;
  }
}

AcessoSimultaneo.interpret = function(value){
  return value == null ? '' : AcessoSimultaneo(value);
}

AcessoSimultaneo.strip = function(value) {
    return value.replace(/^\s+/, '').replace(/\s+$/, '');
}

AcessoSimultaneo.toQueryParams = function(value, separator) {
    var match = AcessoSimultaneo.strip(value).match(/([^?#]*)(#.*)?$/);
    if (!match) return {};

	var array = match[1].split(separator || '&');
	
	return AcessoSimultaneo.inject(array, {}, function(hash, pair) {
      if ((pair = pair.split('='))[0]) {
        var name = decodeURIComponent(pair[0]);
        var value = pair[1] ? decodeURIComponent(pair[1]) : undefined;

        if (hash[name] !== undefined) {
          if (hash[name].constructor != Array)
            hash[name] = [hash[name]];
          if (value) hash[name].push(value);
        }
        else hash[name] = value;
      }
      return hash;
    });
 }
 
AcessoSimultaneo.inject = function(array, memo, iterator) {
    AcessoSimultaneo.each(array, function(value, index) {
      memo = iterator(memo, value, index);
    });
    return memo;
}

AcessoSimultaneo.each = function(array, iterator) {
    var index = 0;
    try {
      AcessoSimultaneo._each(array, function(value) {
        try {
          iterator(value, index++);
        } catch (e) {
          if (e != $continue) throw e;
        }
      });
    } catch (e) {
      if (e != $break) throw e;
    }
    return array;
}

AcessoSimultaneo._each = function(array, iterator) {
    for (var i = 0, length = array.length; i < length; i++)
      iterator(array[i]);
}  

AcessoSimultaneo.include = function(array, object) {
    var found = false;
    AcessoSimultaneo.each(array, function(value) {
      if (value == object) {
        found = true;
        throw $break;
      }
    });
    return found;
}
  
var $break    = new Object();
var $continue = new Object();

AcessoSimultaneo.$A = Array.from = function(iterable) {
  if (!iterable) return [];
  if (iterable.toArray) {
    return iterable.toArray();
  } else {
    var results = [];
    for (var i = 0, length = iterable.length; i < length; i++)
      results.push(iterable[i]);
    return results;
  }
}

AcessoSimultaneo.Hash = function(obj) {
  AcessoSimultaneo.extend(this, obj || {});
};

AcessoSimultaneo.extend(AcessoSimultaneo.Hash, {
  toQueryString: function(obj) {
    var parts = [];

	  this.prototype._each.call(obj, function(pair) {
      if (!pair.key) return;

      if (pair.value && pair.value.constructor == Array) {
        var values = pair.value.compact();
        if (values.length < 2) pair.value = values.reduce();
        else {
        	key = encodeURIComponent(pair.key);
			AcessoSimultaneo.each(values, function(value) {
            value = value != undefined ? encodeURIComponent(value) : '';
            parts.push(key + '=' + encodeURIComponent(value));
          });          
          return;
        }
      }
      if (pair.value == undefined) pair[1] = '';
      parts.push(pair.map(encodeURIComponent).join('='));
	  });

    return parts.join('&');
  }
});

AcessoSimultaneo.extend(AcessoSimultaneo.Hash.prototype, AcessoSimultaneo.Enumerable);
AcessoSimultaneo.extend(AcessoSimultaneo.Hash.prototype, {
  _each: function(iterator) {
    for (var key in this) {
      var value = this[key];
      if (value && value == Hash.prototype[key]) continue;

      var pair = [key, value];
      pair.key = key;
      pair.value = value;
      iterator(pair);
    }
  },

  keys: function() {
    return this.pluck('key');
  },

  values: function() {
    return this.pluck('value');
  },

  merge: function(hash) {
    return AcessoSimultaneo.$H(hash).inject(this, function(mergedHash, pair) {
      mergedHash[pair.key] = pair.value;
      return mergedHash;
    });
  },

  remove: function() {
    var result;
    for(var i = 0, length = arguments.length; i < length; i++) {
      var value = this[arguments[i]];
      if (value !== undefined){
        if (result === undefined) result = value;
        else {
          if (result.constructor != Array) result = [result];
          result.push(value)
        }
      }
      delete this[arguments[i]];
    }
    return result;
  },

  toQueryString: function() {
    return AcessoSimultaneo.Hash.toQueryString(this);
  },

  inspect: function() {
    return '#<Hash:{' + this.map(function(pair) {
      return pair.map(Object.inspect).join(': ');
    }).join(', ') + '}>';
  }
});

AcessoSimultaneo.$H = function(object) {
  if (object && object.constructor == AcessoSimultaneo.Hash) return object;
  return new AcessoSimultaneo.Hash(object);
};

AcessoSimultaneo.Ajax = {
  getTransport: function() {
    return AcessoSimultaneo.Try.these(
      function() {return new XMLHttpRequest()},
      function() {return new ActiveXObject('Msxml2.XMLHTTP')},
      function() {return new ActiveXObject('Microsoft.XMLHTTP')}
    ) || false;
  },

  activeRequestCount: 0
}

AcessoSimultaneo.Ajax.Responders = {
  responders: [],

  _each: function(iterator) {
    this.responders._each(iterator);
  },

  register: function(responder) {
    if (!AcessoSimultaneo.include(this, responder))
      this.responders.push(responder);
  },

  unregister: function(responder) {
    this.responders = this.responders.without(responder);
  },

  dispatch: function(callback, request, transport, json) {
	  AcessoSimultaneo.each(this, function(responder) {
      if (typeof responder[callback] == 'function') {
        try {
          responder[callback].apply(responder, [request, transport, json]);
        } catch (e) {}
      }
    });
  }
};

AcessoSimultaneo.extend(AcessoSimultaneo.Ajax.Responders, AcessoSimultaneo.Enumerable);

AcessoSimultaneo.Ajax.Responders.register({
  onCreate: function() {
    AcessoSimultaneo.Ajax.activeRequestCount++;
  },
  onComplete: function() {
    AcessoSimultaneo.Ajax.activeRequestCount--;
  }
});

AcessoSimultaneo.Ajax.Base = function() {};
AcessoSimultaneo.Ajax.Base.prototype = {
  setOptions: function(options) {
    this.options = {
      method:       'post',
      asynchronous: true,
      contentType:  'application/x-www-form-urlencoded',
      encoding:     'UTF-8',
      parameters:   ''
    }
    AcessoSimultaneo.extend(this.options, options || {});

    this.options.method = this.options.method.toLowerCase();
    if (typeof this.options.parameters == 'string')
		this.options.parameters = AcessoSimultaneo.toQueryParams(this.options.parameters);
  }
}

AcessoSimultaneo.Ajax.Request = Class.create();
AcessoSimultaneo.Ajax.Request.Events =
  ['Uninitialized', 'Loading', 'Loaded', 'Interactive', 'Complete'];

  var _bind = function() {
 var __method = this, args = AcessoSimultaneo.$A(arguments), object = args.shift();
 return function() {
   return __method.apply(object, args.concat(AcessoSimultaneo.$A(arguments)));
 }
}
  
AcessoSimultaneo.Ajax.Request.prototype = AcessoSimultaneo.extend(new AcessoSimultaneo.Ajax.Base(), {
  _complete: false,

  initialize: function(url, options) {
    this.transport = AcessoSimultaneo.Ajax.getTransport();
    this.setOptions(options);
	this._respondToReadyState.bind = _bind;
	this.onStateChange.bind = _bind;
    this.request(url);
  },

  request: function(url) {
    this.url = url;
    this.method = this.options.method;
    var params = this.options.parameters;

    if (!AcessoSimultaneo.include(['get', 'post'], this.method)) {
      // simulate other verbs over post
      params['_method'] = this.method;
      this.method = 'post';
    }

    params = AcessoSimultaneo.Hash.toQueryString(params);
    if (params && /Konqueror|Safari|KHTML/.test(navigator.userAgent)) params += '&_='

    // when GET, append parameters to URL
    if (this.method == 'get' && params)
      this.url += (this.url.indexOf('?') > -1 ? '&' : '?') + params;

    try {
      AcessoSimultaneo.Ajax.Responders.dispatch('onCreate', this, this.transport);

      this.transport.open(this.method.toUpperCase(), this.url,
        this.options.asynchronous);

      if (this.options.asynchronous)	
		setTimeout(this._respondToReadyState.bind(this), 10);

      this.transport.onreadystatechange = this.onStateChange.bind(this);
      this.setRequestHeaders();

      var body = this.method == 'post' ? (this.options.postBody || params) : null;

      this.transport.send(body);

      /* Force Firefox to handle ready state 4 for synchronous requests */
      if (!this.options.asynchronous && this.transport.overrideMimeType)
        this.onStateChange();

    }
    catch (e) {
      this.dispatchException(e);
    }
  },

  onStateChange: function() {
    var readyState = this.transport.readyState;
    if (readyState > 1 && !((readyState == 4) && this._complete))
      this.respondToReadyState(this.transport.readyState);
  },

  setRequestHeaders: function() {
    var headers = {
      'X-Requested-With': 'XMLHttpRequest',
      'X-AcessoSimultaneo.Prototype-Version': AcessoSimultaneo.Prototype.Version,
      'Accept': 'text/javascript, text/html, application/xml, text/xml, */*'
    };

    if (this.method == 'post') {
      headers['Content-type'] = this.options.contentType +
        (this.options.encoding ? '; charset=' + this.options.encoding : '');

      /* Force "Connection: close" for older Mozilla browsers to work
       * around a bug where XMLHttpRequest sends an incorrect
       * Content-length header. See Mozilla Bugzilla #246651.
       */
      if (this.transport.overrideMimeType &&
          (navigator.userAgent.match(/Gecko\/(\d{4})/) || [0,2005])[1] < 2005)
            headers['Connection'] = 'close';
    }

    // user-defined headers
    if (typeof this.options.requestHeaders == 'object') {
      var extras = this.options.requestHeaders;

      if (typeof extras.push == 'function')
        for (var i = 0, length = extras.length; i < length; i += 2)
          headers[extras[i]] = extras[i+1];
      else
        AcessoSimultaneo.$H(extras).each(function(pair) { headers[pair.key] = pair.value });
    }

    for (var name in headers)
      this.transport.setRequestHeader(name, headers[name]);
  },

  success: function() {
    return !this.transport.status
        || (this.transport.status >= 200 && this.transport.status < 300);
  },

  respondToReadyState: function(readyState) {
    var state = AcessoSimultaneo.Ajax.Request.Events[readyState];
    var transport = this.transport, json = this.evalJSON();

    if (state == 'Complete') {
      try {
        this._complete = true;
        (this.options['on' + this.transport.status]
         || this.options['on' + (this.success() ? 'Success' : 'Failure')]
         || AcessoSimultaneo.Prototype.emptyFunction)(transport, json);
      } catch (e) {
        this.dispatchException(e);
      }

      if (AcessoSimultaneo.strip((this.getHeader('Content-type') || 'text/javascript')).
        match(/^(text|application)\/(x-)?(java|ecma)script(;.*)?$/i))
          this.evalResponse();
    }

    try {
      (this.options['on' + state] || AcessoSimultaneo.Prototype.emptyFunction)(transport, json);
      AcessoSimultaneo.Ajax.Responders.dispatch('on' + state, this, transport, json);
    } catch (e) {
      this.dispatchException(e);
    }

    if (state == 'Complete') {
      // avoid memory leak in MSIE: clean up
      this.transport.onreadystatechange = AcessoSimultaneo.Prototype.emptyFunction;
    }
  },

  _respondToReadyState :function() {
	  this.respondToReadyState(1);
  },
  
  getHeader: function(name) {
    try {
      return this.transport.getResponseHeader(name);
    } catch (e) { return null }
  },

  evalJSON: function() {
    try {
      var json = this.getHeader('X-JSON');
      return json ? eval('(' + json + ')') : null;
    } catch (e) { return null }
  },

  evalResponse: function() {
    try {
      return eval(this.transport.responseText);
    } catch (e) {
      this.dispatchException(e);
    }
  },

  dispatchException: function(exception) {
    (this.options.onException || AcessoSimultaneo.Prototype.emptyFunction)(this, exception);
    AcessoSimultaneo.Ajax.Responders.dispatch('onException', this, exception);
  }
});


/**
 * 
 */
restaurarSessao = function(){	
	var url = "/netcobranca/restoreSession.do";
	callFunction(url, true);
}

/**
 * 
 */
encerrarSessao = function() {
	var url = "/netcobranca/endSession.do";
	callFunction(url, false);
}

/**
 * 
 */
callFunction = function(url, asyn){
	try{	
		new AcessoSimultaneo.Ajax.Request(url,{asynchronous:asyn});
		  
	}catch(e) {}	
}

/**
 * 
 */
var intervalo;
addTempoRenovacao = function(){

	var MINUTES = document.getElementById('tempoRenovacao').value;
	var REFRESH = document.getElementById('ultimaRenovacao').value;

	if(MINUTES != "" && MINUTES > 0){
		var dataAtual = new Date();
		var timeDataAtual = dataAtual.getTime();
		var refreshSession = (Number(REFRESH) + (1000 * 60 * MINUTES));
		refreshSession = refreshSession - timeDataAtual;
		intervalo = setInterval( restaurarSessao, refreshSession);
	}	
}

/**
Verifica se a pagina esta sendo fechada.
 */
var keyPress = 0;

document.onkeydown = function(event) {
	try {
		event = event || window.event;
		//Get key unicode
		var unicode = event.keyCode ? event.keyCode : event.charCode;

		//Check it it's ALT and F4 (115)
		if (unicode == 115 && event.altKey == 1)
		{
			encerrarSessao();
		}

		keyPress = unicode;
	}
	catch(err) {}
}

/**
 * 
 */
window.onbeforeunload = function(event){
	clearInterval(intervalo);
	try {
		event = window.event || event;

		if(screen != null && event != null){

			var positionX = (event.screenX > (screen.availWidth/2)) ? true : false;
			var positionY = (8 > ((event.screenY * 100)/screen.availHeight)) ? true : false;

			if(event.altKey || keyPress != 116 && positionX && positionY && window.event != null && window.event.clientY != null && window.event.clientY < 0){                                       
				encerrarSessao();
			}                             
		}
	}catch(err) {

	}
	keyPress = 0;
}

addTempoRenovacao();
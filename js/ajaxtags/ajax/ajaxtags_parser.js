/**
 * Copyright 2005 Darren L. Spurgeon
 * Copyright 2007 Jens Kapitza
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
 
 
/**
 * Response Parsers
 */
AbstractResponseParser = function() {
  this.getArray = function () {return null;};
};

DefaultResponseParser = Class.create();
DefaultResponseParser.prototype = Object.extend(new AbstractResponseParser(), {
  initialize: function() {
    this.type = "xml";
  },
  getArray: function () {
    return this.itemList;
  },
  load: function(request) {
    this.content = request.responseXML;
    this.parse();
    this.prepareData( this.itemList);
  },
  // format <name><value><value><value>....<value>
  prepareData: function( dataarray ) {},
  
  parse: function() {
    var root = this.content.documentElement;
    var responseNodes = root.getElementsByTagName("response");
    this.itemList = new Array();
    if (responseNodes.length > 0) {
      var responseNode = responseNodes[0];
      var itemNodes = responseNode.getElementsByTagName("item");
      for (var i=0; i<itemNodes.length; i++) {
        var nameNodes = itemNodes[i].getElementsByTagName("name");
        var valueNodes = itemNodes[i].getElementsByTagName("value");
        if (nameNodes.length > 0 && valueNodes.length > 0) {
          var name = nameNodes[0].firstChild ? nameNodes[0].firstChild.nodeValue : "";
          var myData = new Array(name);
            for (var j=0; j <valueNodes.length; j++) {
              var value = valueNodes[j].firstChild ? valueNodes[j].firstChild.nodeValue: "";
       		  myData.push(value);
            }
          this.itemList.push(myData);
        }
      }
    }
  }
});


ResponseTextParser = Class.create();
ResponseTextParser.prototype = Object.extend(new AbstractResponseParser(), {
  initialize: function() {
    this.type = "text";
  },

  load: function(request) {
    this.content = request.responseText;
    this.split();
  },

  split: function() {
    this.itemList = new Array();
    var lines = this.content.split('\n');
    for (var i=0; i<lines.length; i++) {
      this.itemList.push(lines[i].split(','));
    }
  }
});

ResponseXmlParser = Class.create();
ResponseXmlParser.prototype = Object.extend(new DefaultResponseParser(), {
  prepareData: function(request,dataarray) {
  }
});




ResponseHtmlParser = Class.create();
ResponseHtmlParser.prototype = Object.extend(new AbstractResponseParser(), {
  initialize: function() {
    this.type = "html";
  },

  load: function(request) {
    this.content = request.responseText;
  }
});

ResponseXmlToHtmlParser = Class.create();
ResponseXmlToHtmlParser.prototype = Object.extend(new DefaultResponseParser(), {
  initialize: function() {
    this.type = "xmltohtml";
  	this.plaintext = false;
  },
  prepareData: function( dataarray) {
   this.contentdiv = document.createElement("div");
   for (i=0; i < dataarray.length; i++)
   {
     var h1 =  document.createElement("h1");
     if (!this.plaintext) {
       h1.innerHTML += dataarray[i][0];
     } else {
       h1.appendChild(document.createTextNode(dataarray[i][0]));
     }
     this.contentdiv.appendChild(h1);
     for (j=1; j < dataarray[i].length; j++) {
       var div =  document.createElement("div");
       if (!this.plaintext) {
         div.innerHTML += dataarray[i][j];
       } else {
         div.appendChild(document.createTextNode(dataarray[i][j]));
       }
       this.contentdiv.appendChild(div);
     }
   }
   this.content =  this.contentdiv.innerHTML;
  
  } 
});


ResponsePlainTextXmlToHtmlParser = Class.create();
ResponsePlainTextXmlToHtmlParser.prototype = Object.extend(new ResponseXmlToHtmlParser(), {
  initialize: function() {
    this.type = "xmltohtml";
  	this.plaintext = true;
  }
});



ResponseXmlToHtmlListParser = Class.create();
ResponseXmlToHtmlListParser.prototype = Object.extend(new DefaultResponseParser(), {
  initialize: function() {
    this.type = "xmltohtmllist";
    this.plaintext =  true;
  },
 

  prepareData: function( dataarray) {
    this.contentdiv = document.createElement("div");
    var ul = document.createElement("ul");
    for (i=0; i < dataarray.length; i++)
    {
      var liElement = document.createElement("li");
      liElement.id=dataarray[i][1];
      if (this.plaintext) {
        liElement.appendChild(document.createTextNode(dataarray[i][0]));
      } else {
        liElement.innerHTML = dataarray[i][0];
      }
      ul.appendChild(liElement);
    }
    this.contentdiv.appendChild(ul);
    this.content = this.contentdiv.innerHTML;
  }
});

ResponseXmlToHtmlLinkListParser = Class.create();
ResponseXmlToHtmlLinkListParser.prototype = Object.extend(new AbstractResponseParser(), {
  initialize: function() {
    this.type = "xmltohtmllinklist";
  },

  load: function(request) {
    this.xml = request.responseXML;
    this.collapsedClass = request.collapsedClass;
    this.treeClass = request.treeClass;
    this.nodeClass = request.nodeClass;
    this.expandedNodes = new Array();
    this.parse();
  },

  parse: function() {
    var ul = document.createElement('ul');
    ul.className = this.treeClass;
    var root = this.xml.documentElement;

    var responseNodes = root.getElementsByTagName("response");
    if (responseNodes.length > 0) {
      var responseNode = responseNodes[0];
      var itemNodes = responseNode.getElementsByTagName("item");
      for (var i=0; i<itemNodes.length; i++) {
        var nameNodes = itemNodes[i].getElementsByTagName("name");
        var valueNodes = itemNodes[i].getElementsByTagName("value");
        var urlNodes = itemNodes[i].getElementsByTagName("url");
        var collapsedNodes = itemNodes[i].getElementsByTagName("collapsed");
        
        if (nameNodes.length > 0 && valueNodes.length > 0) {
          var name = nameNodes[0].firstChild.nodeValue;
          var value = valueNodes[0].firstChild.nodeValue;
          var url = urlNodes[0].firstChild.nodeValue;
          var collapsed = collapsedNodes[0].firstChild.nodeValue.toLowerCase() == "true";
          
          var li = document.createElement('li');
          ul.appendChild(li);
          
          var img = document.createElement('img');
          li.appendChild(img);
          img.id = 'img_' + value;
          img.className = this.collapsedClass;
          
          var link  = document.createElement('a');
          li.appendChild(link);
          link.href = url;
          link.className = this.nodeClass;
          link.appendChild(document.createTextNode(name));
          
          var div = document.createElement('div');
          li.appendChild(div);
          div.id = value;
          
          if(!collapsed)
            this.expandedNodes.push(value);
        }
      }
    }
    this.content = ul;
  }
});

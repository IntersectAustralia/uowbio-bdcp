var Ajax;
if (Ajax && (Ajax != null)) {
	Ajax.Responders.register({
	  onCreate: function() {
        if($('spinner') && Ajax.activeRequestCount>0)
          Effect.Appear('spinner',{duration:0.5,queue:'end'});
	  },
	  onComplete: function() {
        if($('spinner') && Ajax.activeRequestCount==0)
          Effect.Fade('spinner',{duration:0.5,queue:'end'});
	  }
	});
}

var createFileTree = function(baseDir, nodeId, id_param, createMenu) 
{

    var $node = $(nodeId);
    var theme = baseDir + '/plugins/js-tree-0.2/js/jstree_pre1.0_stable/themes/classic/style.css';
    var imagesDir = baseDir + '/images';
    $node.jstree({
          'core': { },
          'json_data' : {
              'ajax' : {
                  'url' : function(node) {
                      var topId = id_param;
                      var folderPath = node == -1 ? '' : $(node).data('folderPath');
                      var sessionId = node == -1 ? '' : $(node).data('sessionId');
                      var str = "listFolder?folderPath=" + encodeURI(folderPath)
                      if (topId) {
                         str = str + '&id=' + topId;
                      }
                      if (sessionId) {
                         str = str + '&sessionId=' + sessionId;
                      }
                      return str;
                   },
                  'success': function(response) {
                      if (response != null && !(response.error)) { return response; }
                      alert('Error:' + response.error);
                      return null;
                  },
                  'error': function(request, textStatus, errorThrown) {
                      if ('success' == textStatus) return;
                      alert('Cannot access folder')
                  }
              }
          },
          'checkbox': { 
              'two_state': false,
              'real_checkboxes':true,
              'real_checkboxes_names': 
                        function(node) {
                             var folderPath = $(node).data('folderPath');
                             if(folderPath == null)
                             {
                                 folderPath = node[0].getAttribute('folderPath')
                             }

                             return['files', folderPath ];
                        }
          },
          "themes": {
              "url" : theme,
              "theme": "classic"
          },
          "types": {
              "valid_children" : "all",
              "type_attr" : "rel",
              "types" : {
                  "root" : {
                      "icon" : {"image": imagesDir + "/component.gif"},
                      "valid_children" : "all"
                  },
                  "file" : {
                      "icon" : {"image": imagesDir + "/leaf.gif"},
                      "valid_children" : ["none"]
                  },
                  "session" : {
                      "icon" : {"image": imagesDir + "/session.gif"},
                      "valid_children" : ["all"]
                  },
                  "analysed" : {
                      "icon" : {"image": imagesDir + "/analysed.gif"},
                      "valid_children" : "all"
                  },
              }
          },
          "contextmenu" : {
               "items" : createMenu
          }, 
          'plugins' : [ "themes", "json_data", "types", "checkbox", "ui", "contextmenu" ]
        });
	    $node.bind("loaded.jstree", function (e, data) {
	
	    	$node.jstree('open_all');
			
		});
		$node.bind("refresh.jstree", function (e, data) {
	
			$node.jstree('open_all');
			
		});
};

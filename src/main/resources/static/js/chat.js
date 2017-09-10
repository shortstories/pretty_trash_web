(function(root) {
  var quill = null, io = null,
    lastText = "",
    currentSocket = null,
    currentIndex = -1;

  function _onReceiveEdit(data) {
    console.log("Receive data");
    console.dir(data);

    if (data.length && data.length < 0) {
      quill.deleteText(data.startFrom, data.length * -1);
    } 
    
    if (data.contents) {
      quill.insertText(data.startFrom, data.contents);
    }

    currentIndex = data.index;
  }

  function _setEventHandlers() {
    quill.on('text-change', function(delta, oldContents, source) {
      if (source !== "user") {
        return;
      }

      console.log(JSON.stringify(delta));
      console.log("oldContents: " + JSON.stringify(oldContents));

      currentIndex++;

      var data = {
        name : nickName,
        index : currentIndex,
        startFrom: 0
      };

      var retainLength = 0;
      var newInsert = null;
      delta.forEach(function(item) {
        if (item.retain) {
          data.startFrom = item.retain;
          retainLength = item.retain;
        } else if (item.insert) {
          data.contents = item.insert;
          newInsert = item.insert;
          newInsert = newInsert.replace(/[^\\n]/g, "■");
        } else if (item.delete) {
          data.length = item.delete * -1;
        }
      });

      currentSocket.emit("edit", data);
     });
  }

  function _onConnectWebsocket() {
    _setWebsocketEventHandlers();
    _emitSubscribe();
  }

  function _emitSubscribe() {
    currentSocket.emit("subscribe", {
      name: nickName
    });
  }

  function _setWebsocketEventHandlers() {
    if (!currentSocket) {
      console.log("Socket.io is not initialized")
      return;
    }

    currentSocket.on("edit", _onReceiveEdit);
  }

  function _initWebsocket() {
    return fetch("/api/v1/chats/url")
      .then(function(res) {
        return res.json();
      })
      .then(function(json) {
        currentSocket = io(json.websocketUrl);
        currentSocket.on("connect", _onConnectWebsocket);
      })
      .catch(function(err) {
        console.log("Fail to get websocket url: " + err);
      });
  }

  function _initLastText() {
    return fetch("/api/v1/chats/snapshots/last")
      .then(function(res) {
        return res.json();
      })
      .then(function(json) {
        if (json) {
          quill.setText(json.text || "");
          currentIndex = json.index || 0;
        }
      })
      .catch(function(err) {
        console.log("Fail to get last snapshot: " + err);
      });
  }

  function init(quillDep, ioDep, nickNameDep) {
    quill = quillDep;
    io = ioDep;
    nickName = nickNameDep;

    _setEventHandlers();
    _initLastText();
    _initWebsocket();
  }

  root.Chat = {
    init: init
  };
})(window);
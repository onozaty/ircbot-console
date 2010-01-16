var config = null;

var loadConfig = function(_config) {
  config = _config;
  $('#serverName').val(config.serverName || '');
  $('#serverPort').val(config.serverPort || '');
  $('#serverPassword').val(config.serverPassword || '');
  $('#nick').val(config.nick || '');
  $('#encoding').val(config.encoding || '');

  // チャンネル情報更新
  // いったんすべて削除
  var table = $('#channelTable')[0];
  while(table.rows.length > 1) {
    table.deleteRow(1);
  }

  for(var i = 0, len = config.channels.length; i < len; i++) {
    addChannelRow(config.channels[i]);
  }

}

var addChannelRow = function(channel) {
  var table = $('#channelTable')[0];
  var tr = table.insertRow(table.rows.length);
  $(tr)
    .append('<td>').children()
      .append($('<span>').attr('class', 'icons')
        .append(
          $('<a href="javascript:void(0)"><img src="./images/comment.gif"></img>ログ</a>')
            .click(function() { goLogView(channel); return false; }))
        .append('&nbsp;')
        .append(
          $('<a href="javascript:void(0)"><img src="./images/page_edit.gif"></img>設定</a>')
            .click(function() { goChannelSetting(channel); return false; }))
        .append('&nbsp;')
        .append(
          $('<a href="javascript:void(0)"><img src="./images/page_cross.gif"></img>削除</a>')
            .click(function() { removeChannel(channel, this.parentNode.parentNode.parentNode); return false; })))
      .append($('<span>').text(channel));
}

var removeChannel = function(channel, elm) {
  if (!confirm(channel + ' を削除してよろしいですか？')) {
    return false;
  }

  elm.parentNode.removeChild(elm);

  for(var i = 0, len = config.channels.length; i < len; i++) {
    if(config.channels[i] == channel){
      config.channels.splice(i,1);
    }
  }

  IrcBotServer.removeChannel(channel);
}

var updateStatus = function(connected) {
  if (connected) {
    $('#status').text('接続中');
  } else {
    $('#status').text('未接続');
  }
}

var goChannelSetting = function(channel) {
  location.href = './channel_setting.html?' + encodeURIComponent(channel);
}

var goLogView = function(channel) {
  location.href = './log.html?' + encodeURIComponent(channel);
}

$('#updateConnectButton').click(
  function() {
    config.serverName = $('#serverName').val();
    config.serverPort = $('#serverPort').val();
    config.serverPassword = $('#serverPassword').val();
    config.nick = $('#nick').val();
    config.encoding = $('#encoding').val();

    var messages = [];
    if (config.serverName == '') {
      messages.push('ホスト名を入力してください。');
    }
    if (config.serverPort == '') {
      messages.push('ポート番号を入力してください。');
    }
    if (config.nick == '') {
      messages.push('ニックネームを入力してください。');
    }
    if (config.encoding == '') {
      messages.push('文字コードを入力してください。');
    }

    if (messages.length != 0) {
      alert(messages.join('\n'));
      return;
    }

    IrcBotServer.updateConnetInfo(
      config.serverName,
      config.serverPort,
      config.serverPassword,
      config.nick,
      config.encoding
    );
  }
);

$('#addChannelButton').click(
  function() {
    var channel = $('#channelName').val();

    if (channel == '') {
      alert('チャンネル名が未入力です。');
      return false;
    }
    if ($.inArray(channel, config.channels) != -1) {
      // 既に存在
      alert('既に登録されているチャンネルです。');
      return false;
    }
    addChannelRow(channel);
    config.channels.push(channel);
    $('#channelName').val('');

    IrcBotServer.addChannel(channel);
  }
);

// DWRのローディング表示用の設定
dwr.util._disabledZoneUseCount = 0;
DWRUtil.useLoadingMessage("Loading..");

IrcBotServer.getConfig(loadConfig);

// 状態表示
IrcBotServer.isConnected(updateStatus);
window.setTimeout(
  function() {
    IrcBotServer.isConnected(updateStatus);
  },
  1000 * 60
);

